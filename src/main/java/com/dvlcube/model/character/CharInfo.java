package com.dvlcube.model.character;

import com.dvlcube.controller.Server;
import com.dvlcube.model.ItemGroup;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wonka
 */
public class CharInfo {

    private int maxHP;
    private int maxSP;

    public CharInfo(Char character) {
        try {
            readDB();
            statusCalcSigma();
            this.maxHP = getMaxHP(character);
            this.maxSP = getMaxSP(character);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static List<String[]> getSkillTree(int jobId) {
        try {
            FileReader skillTree = new FileReader(Server.get(Server.EATHENA_ROOT) + "/db/skill_tree.txt");
            BufferedReader reader = new BufferedReader(skillTree);
            String line;
            boolean matchFound = false;
            List<String[]> requirements = new ArrayList<String[]>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("//")) {
                    continue;
                }
                int id = Integer.parseInt(line.split(",")[0]);
                matchFound = id == jobId ? true : false;
                if (matchFound) {
                    requirements.add(line.split(","));
                } else {
                    if (requirements.size() > 0) {
                        break;
                    }
                }
            }
            reader.close();
            for (String[] columns : requirements) {
                columns[columns.length - 1] = columns[columns.length - 1].split(" ")[0]; //removing comments from the end of the line
            }
            return requirements;
        } catch (Exception e) {
            return null;
        }
    }
    static final int MAX_LEVEL = 99;
    static final int JOB_MAX_BASIC = 26;
    static final int JOB_NOVICE_HIGH = 4001;
    static final int JOB_MAX = 4049;
    static final int CLASS_COUNT = JOB_MAX - JOB_NOVICE_HIGH + JOB_MAX_BASIC;
    static int HP_SIGMA_VAL[][] = new int[CLASS_COUNT][MAX_LEVEL + 1];
    static int HP_FACTOR[] = new int[CLASS_COUNT];
    static int HP_MULTIPLICATOR[] = new int[CLASS_COUNT];
    static int MAX_WEIGHT_BASE[] = new int[CLASS_COUNT];
    static int SP_FACTOR[] = new int[CLASS_COUNT];
    static int ASPD_BASE[][] = new int[CLASS_COUNT][ItemGroup.WeaponType.MAX_WEAPON_TYPE.ordinal()];
    static int UPPERMASK = 0x0fff;
    static int JOBL_2_1 = 0x100; //256
    static int JOBL_2_2 = 0x200;
    static int JOBL_2 = 0x300;
    static int JOBL_UPPER = 0x1000; //4096
    static int JOBL_BABY = 0x2000;  //8192
    static int JOB_BONUS[][] = new int[CLASS_COUNT][MAX_LEVEL];

    public int getMaxHP() {
        return maxHP;
    }

    public int getMaxSP() {
        return maxSP;
    }

    public enum Job {

        NOVICE(0x0),
        SWORDSMAN(0x1),
        MAGE(0x2),
        ARCHER(0x3),
        ACOLYTE(0x4),
        MERCHANT(0x5),
        THIEF(0x6),
        TAEKWON(0x7),
        WEDDING(0x1000),
        GUNSLINGER(0x9),
        NINJA(0xA),
        XMAS(0x10000),
        SUMMER(0x40000),
        //2_1 classes
        SUPER_NOVICE(JOBL_2_1),
        KNIGHT(0x101),
        WIZARD(0x102),
        HUNTER(0x103),
        PRIEST(0x104),
        BLACKSMITH(0x105),
        ASSASSIN(0x106),
        STAR_GLADIATOR(0x107),
        //2_2 classes
        CRUSADER(JOBL_2_2 | 0x1),
        SAGE(0x202),
        BARDDANCER(0x203),
        MONK(0x204),
        ALCHEMIST(0x205),
        ROGUE(0x206),
        SOUL_LINKER(0x207),
        //1-1, advanced
        NOVICE_HIGH(JOBL_UPPER),
        SWORDMAN_HIGH(0x1001),
        MAGE_HIGH(0x1002),
        ARCHER_HIGH(0x1003),
        ACOLYTE_HIGH(0x1004),
        MERCHANT_HIGH(0x1005),
        THIEF_HIGH(0x1006),
        //2_1 advanced
        LORD_KNIGHT(JOBL_UPPER | JOBL_2_1 | 0x1),
        HIGH_WIZARD(0x1102),
        SNIPER(0x1103),
        HIGH_PRIEST(0x1104),
        WHITESMITH(0x1105),
        ASSASSIN_CROSS(0x1106),
        //2_2 advanced
        PALADIN(JOBL_UPPER | JOBL_2_2 | 0x1),
        PROFESSOR(0x1202),
        CLOWNGYPSY(0x1203),
        CHAMPION(0x1204),
        CREATOR(0x1205),
        STALKER(0x1206),
        //1-1 baby
        BABY(JOBL_BABY),
        BABY_SWORDMAN(0x2001),
        BABY_MAGE(0x2002),
        BABY_ARCHER(0x2003),
        BABY_ACOLYTE(0x2004),
        BABY_MERCHANT(0x2005),
        BABY_THIEF(0x2006),
        BABY_TAEKWON(0x2007),
        //2_1 baby
        SUPER_BABY(JOBL_BABY | JOBL_2_1),
        BABY_KNIGHT(0x2101),
        BABY_WIZARD(0x2102),
        BABY_HUNTER(0x2103),
        BABY_PRIEST(0x2104),
        BABY_BLACKSMITH(0x2105),
        BABY_ASSASSIN(0x2106),
        BABY_STAR_GLADIATOR(0x2107),
        //2_2 baby
        BABY_CRUSADER(JOBL_BABY | JOBL_2_2 | 0x1),
        BABY_SAGE(0x2202),
        BABY_BARDDANCER(0x2203),
        BABY_MONK(0x2204),
        BABY_ALCHEMIST(0x2205),
        BABY_ROGUE(0x2206),
        BABY_SOUL_LINKER(0x2207);
        private int hex;

        private Job(int hex) {
            this.hex = hex;
        }

        public int getHex() {
            return hex;
        }
    };

    /**
     * @param jobId The job id.
     * @return Returns the hex value of a job id.
     */
    public static int toHex(int jobId) {
        return Job.values()[getJobIndex(jobId)].getHex();
    }

    /**
     * @param jobId The job id.
     * @return Returns the hex value of a job id.
     */
    @Deprecated
    public static int toHex2(int jobId) {
        switch (jobId) {
            case 0:
                return Job.NOVICE.getHex();
            case 1:
                return Job.SWORDSMAN.getHex();
            case 2:
                return Job.MAGE.getHex();
            case 3:
                return Job.ARCHER.getHex();
            case 4:
                return Job.ACOLYTE.getHex();
            case 5:
                return Job.MERCHANT.getHex();
            case 6:
                return Job.THIEF.getHex();
            case 7:
                return Job.KNIGHT.getHex();
            case 8:
                return Job.PRIEST.getHex();
            case 9:
                return Job.WIZARD.getHex();
            case 10:
                return Job.BLACKSMITH.getHex();
            case 11:
                return Job.HUNTER.getHex();
            case 12:
                return Job.ASSASSIN.getHex();
            case 13:
                return Job.KNIGHT.getHex();
            case 14:
                return Job.CRUSADER.getHex();
            case 15:
                return Job.MONK.getHex();
            case 16:
                return Job.SAGE.getHex();
            case 17:
                return Job.ROGUE.getHex();
            case 18:
                return Job.ALCHEMIST.getHex();
            case 19:
                return Job.BARDDANCER.getHex();
            case 20:
                return Job.BARDDANCER.getHex();
            case 21:
                return Job.CRUSADER.getHex();
            case 22:
                return Job.WEDDING.getHex();
            case 23:
                return Job.SUPER_NOVICE.getHex();
            case 24:
                return Job.GUNSLINGER.getHex();
            case 25:
                return Job.NINJA.getHex();
            case 26:
                return Job.XMAS.getHex();
            case 4001:
                return Job.NOVICE_HIGH.getHex();
            case 4002:
                return Job.SWORDMAN_HIGH.getHex();
            case 4003:
                return Job.MAGE_HIGH.getHex();
            case 4004:
                return Job.ARCHER_HIGH.getHex();
            case 4005:
                return Job.ACOLYTE_HIGH.getHex();
            case 4006:
                return Job.MERCHANT_HIGH.getHex();
            case 4007:
                return Job.THIEF_HIGH.getHex();
            case 4008:
                return Job.LORD_KNIGHT.getHex();
            case 4009:
                return Job.HIGH_PRIEST.getHex();
            case 4010:
                return Job.HIGH_WIZARD.getHex();
            case 4011:
                return Job.WHITESMITH.getHex();
            case 4012:
                return Job.SNIPER.getHex();
            case 4013:
                return Job.ASSASSIN_CROSS.getHex();
            case 4014:
                return Job.LORD_KNIGHT.getHex();
            case 4015:
                return Job.PALADIN.getHex();
            case 4016:
                return Job.CHAMPION.getHex();
            case 4017:
                return Job.PROFESSOR.getHex();
            case 4018:
                return Job.STALKER.getHex();
            case 4019:
                return Job.CREATOR.getHex();
            case 4020:
                return Job.CLOWNGYPSY.getHex();
            case 4021:
                return Job.CLOWNGYPSY.getHex();
            case 4022:
                return Job.PALADIN.getHex();
            case 4023:
                return Job.BABY.getHex();
            case 4024:
                return Job.BABY_SWORDMAN.getHex();
            case 4025:
                return Job.BABY_MAGE.getHex();
            case 4026:
                return Job.BABY_ARCHER.getHex();
            case 4027:
                return Job.BABY_ACOLYTE.getHex();
            case 4028:
                return Job.BABY_MERCHANT.getHex();
            case 4029:
                return Job.BABY_THIEF.getHex();
            case 4030:
                return Job.BABY_KNIGHT.getHex();
            case 4031:
                return Job.BABY_PRIEST.getHex();
            case 4032:
                return Job.BABY_WIZARD.getHex();
            case 4033:
                return Job.BABY_BLACKSMITH.getHex();
            case 4034:
                return Job.BABY_HUNTER.getHex();
            case 4035:
                return Job.BABY_ASSASSIN.getHex();
            case 4036:
                return Job.BABY_KNIGHT.getHex();
            case 4037:
                return Job.BABY_CRUSADER.getHex();
            case 4038:
                return Job.BABY_MONK.getHex();
            case 4039:
                return Job.BABY_SAGE.getHex();
            case 4040:
                return Job.BABY_ROGUE.getHex();
            case 4041:
                return Job.BABY_ALCHEMIST.getHex();
            case 4042:
                return Job.BABY_BARDDANCER.getHex();
            case 4043:
                return Job.BABY_BARDDANCER.getHex();
            case 4044:
                return Job.BABY_CRUSADER.getHex();
            case 4045:
                return Job.SUPER_BABY.getHex();
            case 4046:
                return Job.TAEKWON.getHex();
            case 4047:
                return Job.STAR_GLADIATOR.getHex();
            case 4048:
                return Job.STAR_GLADIATOR.getHex();
            case 4049:
                return Job.SOUL_LINKER.getHex();
            default:
                return Job.NOVICE.getHex();
        }
    }

    /**
     * @param character The Char.
     * @return The Max HP for the specified Char.
     */
    public static int getMaxHP(Char character) {
        int hp = getJobIndex(character.getClassId());
        hp = 35 + character.getBaseLevel() * HP_MULTIPLICATOR[hp] / 100 + HP_SIGMA_VAL[hp][character.getBaseLevel()];

        if ((character.getClassHex() & UPPERMASK) == Job.NINJA.getHex()
                || (character.getClassId() & UPPERMASK) == Job.GUNSLINGER.getHex()) {
            hp += 100; //Since their HP can't be approximated well enough without this.
        }
        if ((character.getClassHex() & UPPERMASK) == Job.TAEKWON.getHex()
                && character.getBaseLevel() >= 90 && isRanker(character.getId(), Job.TAEKWON.getHex())) {
            hp *= 3; //Triple max HP for top ranking Taekwons over level 90.
        }
        if ((character.getClassHex() & UPPERMASK) == Job.SUPER_NOVICE.getHex() && character.getBaseLevel() >= 99) {
            hp += 2000; //Supernovice lvl99 hp bonus.
        }
        hp += hp * character.getVit() / 100; // +1% per each point of VIT

        if ((character.getClassHex() & JOBL_UPPER) != 0) {
            hp += hp * 25 / 100; //Trans classes get a 25% hp bonus
        } else if ((character.getClassHex() & JOBL_BABY) != 0) {
            hp -= hp * 30 / 100; //Baby classes get a 30% hp penalty
        }
        return hp;
    }

    /**
     * @param character
     * @return The mx sp value for the specified Char.
     */
    public static int getMaxSP(Char character) {
        int sp;

        sp = 10 + character.getBaseLevel() * SP_FACTOR[getJobIndex(character.getClassId())] / 100;
        sp += sp * character.getInt() / 100;

        if ((character.getClassHex() & JOBL_UPPER) != 0) {
            sp += sp * 25 / 100;
        } else if ((character.getClassHex() & JOBL_BABY) != 0) {
            sp -= sp * 30 / 100;
        }
        if ((character.getClassHex() & UPPERMASK) == Job.TAEKWON.getHex() && character.getBaseLevel() >= 90 && isRanker(character.getId(), Job.TAEKWON.getHex())) {
            sp *= 3; //Triple max SP for top ranking Taekwons over level 90.
        }
        return sp;
    }

    /**
     * Converts a class to its array index for CLASS_COUNT defined arrays.
     * Note that it does not do a validity check for speed purposes, where parsing
     * player input make sure to use a jobIdExists first!
     * @param jobIndex
     * @return
     */
    public static int getJobIndex(int jobIndex) {
        if (jobIndex >= JOB_NOVICE_HIGH) {
            return jobIndex - JOB_NOVICE_HIGH + JOB_MAX_BASIC;
        }
        return jobIndex;
    }

    /**
     * Check whether a player ID is in the fame rankers' list of its job, returns his/her position if so, 0 else
     * @param charId
     * @param job
     * @return
     */
    static boolean isRanker(long charId, int job) {
        /*int i;
        switch(job){
        case BLACKSMITH: // Blacksmith
        for(i = 0; i < MAX_FAME_LIST; i++){
        if(smith_fame_list[i].id == char_id)
        return i + 1;
        }
        break;
        case ALCHEMIST: // Alchemist
        for(i = 0; i < MAX_FAME_LIST; i++){
        if(chemist_fame_list[i].id == char_id)
        return i + 1;
        }
        break;
        case TAEKWON: // Taekwon
        for(i = 0; i < MAX_FAME_LIST; i++){
        if(taekwon_fame_list[i].id == char_id)
        return i + 1;
        }
        break;
        }*/
        return true;
    }

    /**
     * Reads job_db1 and job_db2.txt
     * Must be executed prior to statusCalcSigma().
     * @throws FileNotFoundException
     * @throws IOException
     */
    public final void readDB()
            throws FileNotFoundException, IOException {
        int i, j, job;
        FileReader job_db1 = new FileReader(Server.get(Server.EATHENA_ROOT) + "/db/job_db1.txt");
        BufferedReader db1Reader = new BufferedReader(job_db1);
        i = 0;
        String line;
        while ((line = db1Reader.readLine()) != null) {
            //NOTE: entry MAX_WEAPON_TYPE is not counted
            String columns[] = new String[5 + ItemGroup.WeaponType.MAX_WEAPON_TYPE.ordinal()];
            i++;
            if (line.startsWith("//")) {
                continue;
            }
            columns = line.split(",");

            job = Integer.parseInt(columns[0].trim());
            if (!jobIdExists(job)) {
                continue;
            }
            job = getJobIndex(job);
            MAX_WEIGHT_BASE[job] = Integer.parseInt(columns[1].trim());
            HP_FACTOR[job] = Integer.parseInt(columns[2].trim());
            HP_MULTIPLICATOR[job] = Integer.parseInt(columns[3].trim());
            SP_FACTOR[job] = Integer.parseInt(columns[4].trim());
            for (j = 0; j < ItemGroup.WeaponType.MAX_WEAPON_TYPE.ordinal(); j++) {
                ASPD_BASE[job][j] = Integer.parseInt(columns[j + 5].trim());
            }
        }
        db1Reader.close();

        FileReader job_db2 = new FileReader(Server.get(Server.EATHENA_ROOT) + "/db/job_db1.txt");
        BufferedReader db2Reader = new BufferedReader(job_db2);
        while ((line = db2Reader.readLine()) != null) {
            String[] columns = new String[MAX_LEVEL + 1]; //Job Level is limited to MAX_LEVEL, so the bonuses should likewise be limited to it. [Skotlex]
            if (line.startsWith("//")) {
                continue;
            }
            columns = line.split(",");
            job = Integer.parseInt(columns[0].trim());
            if (!jobIdExists(job)) {
                continue;
            }
            job = getJobIndex(job);
            for (i = 1; i < columns.length; i++) {
                JOB_BONUS[job][i - 1] = Integer.parseInt(columns[i].trim());
            }
        }
        job_db2.close();
    }

    /**
     * Calculates the status sigma. Must be executed after readDB().
     */
    public final void statusCalcSigma() {
        int i, j;

        for (i = 0; i < CLASS_COUNT; i++) {
            int k = 0;
            HP_SIGMA_VAL[i][0] = HP_SIGMA_VAL[i][1] = 0;
            for (j = 2; j <= MAX_LEVEL; j++) {
                k += (HP_FACTOR[i] * j + 50) / 100;
                HP_SIGMA_VAL[i][j] = k;
                if (k >= Integer.MAX_VALUE) {
                    break; //Overflow protection. [Skotlex]
                }
            }
            for (; j <= MAX_LEVEL; j++) {
                HP_SIGMA_VAL[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    /**
     * @param jobId
     * @return whether the given class value corresponds to a player class. [Skotlex]
     */
    static boolean jobIdExists(int jobId) {
        if (jobId < JOB_MAX_BASIC || (jobId >= JOB_NOVICE_HIGH && jobId < JOB_MAX)) {
            return true;
        }
        return false;
    }
}
