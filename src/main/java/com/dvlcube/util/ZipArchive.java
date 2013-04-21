package com.dvlcube.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Credits: http://www.roseindia.net/java/example/java/io/ZipFolderExample.shtml
 * @author Wonka
 */
public class ZipArchive {

    private static final int BUFFER = 2048;
    private File zipFile;
    private BufferedInputStream in = null;
    private ZipOutputStream out;
    private byte[] data;
    private int filesZipped = 0;

    public void copyTo(String targetFolder) throws IOException {
        FileCopy.copy(zipFile, targetFolder);
    }

    public ZipArchive(String[] targetFolders, String zipName) {
        try {
            this.zipFile = new File(zipName + getDate() + ".zip");
            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
            out.setLevel(9);
            data = new byte[BUFFER];
            for (String folderName : targetFolders) {
                File folder = new File(folderName);
                traverse(folder);
            }
            cleanUp(out);
            cleanUp(in);
            System.out.println("# Compressed " + filesZipped + " into " + zipFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ZipArchive(String folder, String zipName) {
        try {
            File targetFolder = new File(folder);
            this.zipFile = new File(zipName + getDate() + ".zip");
            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
            out.setLevel(9);
            data = new byte[BUFFER];
            traverse(targetFolder);
            cleanUp(out);
            cleanUp(in);
            System.out.println("# Compressed " + filesZipped + " into " + zipFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void traverse(File dir) throws FileNotFoundException, IOException {
        if (dir.isDirectory()) {
            String[] files = dir.list();
            for (String file : files) {
                File child = new File(dir.getPath() + File.separatorChar + file);
                String dirName = dir.getPath().replaceAll("[A-Z]:\\\\", "");
                System.out.println("# Preparing: " + dirName + File.separatorChar + file);
                if (child.isFile()) {
                    in = new BufferedInputStream(new FileInputStream(child), BUFFER);
                    out.putNextEntry(new ZipEntry(dirName + File.separatorChar + file));
                    int count;
                    while ((count = in.read(data, 0, BUFFER)) != -1) {
                        out.write(data, 0, count);
                    }
                    out.closeEntry();
                }
                traverse(new File(dir, file));
            }
        }
        filesZipped++;
    }

    private void cleanUp(InputStream in) throws Exception {
        in.close();
    }

    private void cleanUp(OutputStream out) throws Exception {
        out.flush();
        out.close();
    }

    private String getDate() {
        Calendar archiveDate = Calendar.getInstance();
        StringBuilder builder = new StringBuilder();
        builder.append("_");
        builder.append(archiveDate.get(Calendar.DAY_OF_MONTH)).append("-");
        builder.append(archiveDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)).append("-");
        builder.append(archiveDate.get(Calendar.YEAR)).append("_");
        builder.append(archiveDate.get(Calendar.HOUR_OF_DAY)).append("h");
        builder.append(archiveDate.get(Calendar.MINUTE)).append("min");
        return builder.toString();
    }

    /**
     * Copies all files under srcDir to dstDir.
     * If dstDir does not exist, it will be created.
     * @param srcDir Source Directory;
     * @param dstDir Destination Directory.
     * @throws IOException
     */
    public void copyDirectory(File srcDir, File dstDir) throws IOException {
        if (srcDir.isDirectory()) {
            if (!dstDir.exists()) {
                dstDir.mkdir();
            }

            String[] children = srcDir.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(srcDir, children[i]),
                        new File(dstDir, children[i]));
            }
        } else {
            copyFile(srcDir, dstDir);
        }
    }

    /**
     * Copies src file to dst file.
     * If the dst file does not exist, it is created
     * @param src Source File.
     * @param dst Destination Directory.
     * @throws IOException
     */
    public static void copyFile(File src, File dst) throws IOException {
        InputStream inputStream = new FileInputStream(src);
        OutputStream outputStream = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, len);
        }
        inputStream.close();
        outputStream.close();
    }

    /**
     * Process all files and directories under dir.
     * @param dir Directory.
     */
    public static void visitAllDirsAndFiles(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                System.out.println("## Read " + children[i]);
                visitAllDirsAndFiles(new File(dir, children[i]));
            }
        }
    }

    /**
     * Process only directories under dir
     * @param dir Directory.
     */
    public static void visitAllDirs(File dir) {
        if (dir.isDirectory()) {
            //  process(dir);

            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                visitAllDirs(new File(dir, children[i]));
            }
        }
    }

    /**
     * Process only files under dir.
     * @param dir Directory.
     */
    public static void visitAllFiles(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                visitAllFiles(new File(dir, children[i]));
            }
        }
    }

    /**
     * Unzip a zip file to the destination directory.
     * @param zipName Specify file to decompress;
     * @param destinationDirectory Specify destination where file will be unzipped.
     */
    public static void unzip(String zipName, String destinationDirectory) {
        try {
            File sourceZipFile = new File(zipName);
            File unzipDestinationDirectory = new File(destinationDirectory);

            // Open Zip file for reading
            ZipFile zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);

            // Create an enumeration of the entries in the zip file
            Enumeration zipFileEntries = zipFile.entries();

            // Process each entry
            while (zipFileEntries.hasMoreElements()) {
                // grab a zip file entry
                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();

                String currentEntry = entry.getName();
                System.out.println("## Extracting: " + entry);

                File destFile = new File(unzipDestinationDirectory, currentEntry);

                // grab file's parent directory structure
                File destinationParent = destFile.getParentFile();

                // create the parent directory structure if needed
                destinationParent.mkdirs();

                // extract file if not a directory
                if (!entry.isDirectory()) {
                    BufferedInputStream is =
                            new BufferedInputStream(zipFile.getInputStream(entry));
                    int currentByte;
                    // establish buffer for writing file
                    byte data[] = new byte[BUFFER];

                    // write the current file to disk
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest =
                            new BufferedOutputStream(fos, BUFFER);

                    // read and write until last byte is encountered
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
            }
            zipFile.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
