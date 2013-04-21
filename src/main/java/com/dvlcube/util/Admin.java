package com.dvlcube.util;

import com.dvlcube.model.Group;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Wonka
 */
public class Admin {

    public static int set(int points, int stat) {
        if (stat < 99) {
            int boost = (int) (Math.random() * 9);
            if (points > 9) {
                boost = (int) (Math.random() * (points / 3));
            }
            if ((int) (Math.random() * 25) > 10) {
                boost *= 2;
            }
            if (boost > points) {
                boost = points;
            }
            if (stat + boost > 99) {
                boost = 99 - stat;
            }
            return boost;
        }
        return 0;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("## Admin.java: Welcome! ##\n");
        System.out.println("$ Choose an action: ");
        String option = scanner.next();

        while (!option.equals("exit")) {
            if (option == null) {
                System.out.println("oiq");
            } else if (option.equals("bitmask")) {
                int HEAD_BOTTOM = 1;
                int RIGHT_HAND = 2;
                int GARMENT = 4;
                int ACC_RIGHT = 8;
                int ARMOR = 16;
                int LEFT_HAND = 32;
                int FOOTGEAR = 64;
                int ACC_LEFT = 128;
                int HEAD_TOP = 256;
                int HEAD_MID = 512;

                int equippedItems = HEAD_BOTTOM | GARMENT | ARMOR;
                System.out.printf("## Equipped items: %d\n", equippedItems);

                int i = 1;
                while (i < 1024) {
                    if ((equippedItems & i) != 0) {
                        System.out.printf("\n## %d is equipped!", i);
                    } else {
                        System.out.printf("\n## %d is NOT equipped!", i);
                    }
                    i <<= 1;
                }
            } else if (option.equals("cas")) {
                int points = 50000 / 99;
                int STR, AGI, LUK, DEX, VIT, INT;
                STR = AGI = LUK = DEX = VIT = INT = 1;
                while (points > 0) {
                    int boost = set(points, STR);
                    STR += boost;
                    points -= boost;
                    boost = set(points, AGI);
                    AGI += boost;
                    points -= boost;
                    boost = set(points, DEX);
                    DEX += boost;
                    points -= boost;
                    boost = set(points, VIT);
                    VIT += boost;
                    points -= boost;
                    boost = set(points, INT);
                    INT += boost;
                    points -= boost;
                    boost = set(points, LUK);
                    LUK += boost;
                    points -= boost;
                    System.out.println("## points left: " + points);
                }
                System.out.println("## STR: " + STR);
                System.out.println("## AGI: " + AGI);
                System.out.println("## DEX: " + DEX);
                System.out.println("## VIT: " + VIT);
                System.out.println("## INT: " + INT);
                System.out.println("## LUK: " + LUK);
            } else if (option.equals("clipboard")) {
                String string = scanner.next();
                CubeString builder = new CubeString();
                int i = 0;
                while (!string.equals("done")) {
                    builder.append(string, "@", i, ";");
                    i++;
                    string = scanner.next();
                }
                System.out.println("$$ Your string: " + builder.toString());

                System.out.println("## New String: " + (new CubeString("oia", " q", " coisa", " d", " loco!")).toString());

                DAO dao = new Query();
                dao.open();
                List<Group> groups = dao.getList(Group.class);
                dao.close();
                System.out.println("## CSV: " + CubeString.asCSV(groups));

                /*http://www.exampledepot.com/egs/java.awt.datatransfer/FromClip.html
                Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

                try {
                if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String s = (String)t.getTransferData(DataFlavor.stringFlavor);
                process(s);
                }
                } catch (UnsupportedFlavorException e) {
                } catch (IOException e) {
                }
                 */
                System.out.println("Bring back clippy!");
            } else if (option.equals("backup")) {
                try {
                    String[] folders = {"I:/projects/java/RO/ptah/src", "I:/projects/java/RO/ptah/web"};
                    ZipArchive backup = new ZipArchive(folders, "I:/projects/java/backups/ptah");
                    backup.copyTo("\\\\DVLCUBE\\doc\\projects\\java\\backups");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (option.equals("check")) {
                System.out.println("##$$ Transformation process start...");
                int i = 0;
                int maxRange = 0;
                int maxElement = 0;
                int maxNk = 0;
                int maxSplash = 0;
                int maxListNum = 0;
                int maxCastCancel = 0;
                int maxInf2 = 0;
                int maxMaxCount = 0;
                int maxSkillType = 0;
                int maxBlowCount = 0;
                int maxName = 0;
                int maxDesc = 0;
                try {
                    String line;
                    FileReader skill_db = new FileReader("D:/skill.txt");
                    BufferedReader reader = new BufferedReader(skill_db);
                    while ((line = reader.readLine()) != null) {
                        String[] row = line.split(",");
                        long id = Long.parseLong(row[0]);
                        String range = row[1];
                        int hit = Integer.parseInt(row[2]);
                        int inf = Integer.parseInt(row[3]);
                        String element = row[4];
                        String nk = row[5];
                        String splash = row[6];
                        int maxLevel = Integer.parseInt(row[7]);
                        String listNum = row[8];
                        String castCancel = row[9];
                        int castDefense = Integer.parseInt(row[10]);
                        String inf2 = row[11];
                        String maxCount = row[12];
                        String skillType = row[13];
                        String blowCount = row[14];
                        String name = row[15];
                        String desc = row[16];

                        if (range.length() > maxRange) {
                            maxRange = range.length();
                        }
                        if (element.length() > maxElement) {
                            maxElement = element.length();
                        }
                        if (nk.length() > maxNk) {
                            maxNk = nk.length();
                        }
                        if (splash.length() > maxSplash) {
                            maxSplash = splash.length();
                        }
                        if (listNum.length() > maxListNum) {
                            maxListNum = listNum.length();
                        }
                        if (castCancel.length() > maxCastCancel) {
                            maxCastCancel = castCancel.length();
                        }
                        if (inf2.length() > maxInf2) {
                            maxInf2 = inf2.length();
                        }
                        if (maxCount.length() > maxMaxCount) {
                            maxMaxCount = maxCount.length();
                        }
                        if (skillType.length() > maxSkillType) {
                            maxSkillType = skillType.length();
                        }
                        if (blowCount.length() > maxBlowCount) {
                            maxBlowCount = blowCount.length();
                        }
                        if (name.length() > maxName) {
                            maxName = name.length();
                            System.out.println("##$$ Replacing maxName: " + name);
                        }
                        if (desc.length() > maxDesc) {
                            maxDesc = desc.length();
                            System.out.println("## Replacing maxDesc: " + desc);
                        }
                        i++;
                    }
                } catch (Exception e) {
                    System.err.println("##!! Error reading line " + i);
                    e.printStackTrace();
                } finally {
                    System.out.println("## Reports:\nrange:........" + maxRange
                            + "\nelement:......" + maxElement
                            + "\nnk:..........." + maxNk
                            + "\nsplash:......." + maxSplash
                            + "\nlistNum:......" + maxListNum
                            + "\ncastCancel:..." + maxCastCancel
                            + "\ninf2:........." + maxInf2
                            + "\nmaxCount:....." + maxMaxCount
                            + "\nskill type:..." + maxSkillType
                            + "\nblow count:..." + maxBlowCount
                            + "\nname:........." + maxName
                            + "\ndesc:........." + maxDesc);
                }
            }
            System.out.print("\n$ Choose an option: ");
            option = scanner.next();
        }
    }
}
