package com.dvlcube.model.character;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Wonka
 */
public class CharHP {

    static final int MAX_LEVEL = 99;
    static final int JOB_MAX_BASIC = 26;
    static final int JOB_NOVICE_HIGH = 4001;
    static final int JOB_MAX = 4049;
    static final int CLASS_COUNT = JOB_MAX - JOB_NOVICE_HIGH + JOB_MAX_BASIC;
    static int hp_sigma_val[][] = new int[CLASS_COUNT][MAX_LEVEL + 1];
    static int hp_coefficient[] = new int[CLASS_COUNT];
    static int hp_coefficient2[] = new int[CLASS_COUNT];
    static int max_weight_base[] = new int[CLASS_COUNT];
    static int sp_coefficient[] = new int[CLASS_COUNT];
    static int aspd_base[][] = new int[CLASS_COUNT][weapon_type.MAX_WEAPON_TYPE.ordinal()];
    static int MAPID_UPPERMASK = 0x0fff;
    static int JOBL_2_1 = 0x100; //256
    static int JOBL_2_2 = 0x200;
    static int JOBL_2 = 0x300;
    static int JOBL_UPPER = 0x1000; //4096
    static int JOBL_BABY = 0x2000;  //8192
    static int job_bonus[][] = new int[CLASS_COUNT][MAX_LEVEL];

    public static enum JobHex {

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
        SUPER_NOVICE(JOBL_2_1 | 0x0),
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
        NOVICE_HIGH(JOBL_UPPER | 0x0),
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
        MAPID_PROFESSOR(0x1202),
        CLOWNGYPSY(0x1203),
        CHAMPION(0x1204),
        CREATOR(0x1205),
        STALKER(0x1206),
        //1-1 baby
        BABY(JOBL_BABY | 0x0),
        BABY_SWORDMAN(0x2001),
        BABY_MAGE(0x2002),
        BABY_ARCHER(0x2003),
        BABY_ACOLYTE(0x2004),
        BABY_MERCHANT(0x2005),
        BABY_THIEF(0x2006),
        BABY_TAEKWON(0x2007),
        //2_1 baby
        SUPER_BABY(JOBL_BABY | JOBL_2_1 | 0x0),
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
        private int id;

        private JobHex(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    };

    enum weapon_type {

        W_FIST, //Bare hands
        W_DAGGER, //1
        W_1HSWORD, //2
        W_2HSWORD, //3
        W_1HSPEAR, //4
        W_2HSPEAR, //5
        W_1HAXE, //6
        W_2HAXE, //7
        W_MACE, //8
        W_2HMACE, //9 (unused)
        W_STAFF, //10
        W_BOW, //11
        W_KNUCKLE, //12
        W_MUSICAL, //13
        W_WHIP, //14
        W_BOOK, //15
        W_KATAR, //16
        W_REVOLVER, //17
        W_RIFLE, //18
        W_GATLING, //19
        W_SHOTGUN, //20
        W_GRENADE, //21
        W_HUUMA, //22
        W_2HSTAFF, //23
        MAX_WEAPON_TYPE,
        // dual-wield constants
        W_DOUBLE_DD, // 2 daggers
        W_DOUBLE_SS, // 2 swords
        W_DOUBLE_AA, // 2 axes
        W_DOUBLE_DS, // dagger + sword
        W_DOUBLE_DA, // dagger + axe
        W_DOUBLE_SA // sword + axe
    };

    public static void main(String[] args) {
        try {
            status_readdb();
            status_calc_sigma();
            Char dummy = new Char();
            dummy.setBaseLevel(MAX_LEVEL);
            dummy.setClass(4016);
            dummy.setVit(99);
            dummy.setAgi(5);
            dummy.setStr(5);
            dummy.setDex(5);
            dummy.setInt(5);
            dummy.setLuk(5);
            dummy.setJobLevel(70);
            dummy.setMaxHP(100);
            dummy.setHP(100);
            System.out.println("## (13227) Calculated max hp: " + status_base_pc_maxhp(dummy));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void status_calc_sigma() {
        int i, j;

        for (i = 0; i < CLASS_COUNT; i++) {
            int k = 0;
            hp_sigma_val[i][0] = hp_sigma_val[i][1] = 0;
            for (j = 2; j <= MAX_LEVEL; j++) {
                k += (hp_coefficient[i] * j + 50) / 100;
                hp_sigma_val[i][j] = k;
                if (k >= Integer.MAX_VALUE) {
                    break; //Overflow protection. [Skotlex]
                }
            }
            for (; j <= MAX_LEVEL; j++) {
                hp_sigma_val[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    static int status_base_pc_maxhp(Char character) {
        System.out.println("## Char base level: " + character.getBaseLevel());
        int val = pc_class2idx(character.getClassId());
        val = 35 + character.getBaseLevel() * hp_coefficient2[val] / 100 + hp_sigma_val[val][character.getBaseLevel()];
        System.out.println("## First val: " + val);

        if ((character.getClassId() & MAPID_UPPERMASK) == JobHex.NINJA.getId()
                || (character.getClassId() & MAPID_UPPERMASK) == JobHex.GUNSLINGER.getId()) {
            val += 100; //Since their HP can't be approximated well enough without this.
        }
        if ((character.getClassId() & MAPID_UPPERMASK) == JobHex.TAEKWON.getId()
                && character.getBaseLevel() >= 90 && pc_famerank(character.getId(), JobHex.TAEKWON.getId())) {
            val *= 3; //Triple max HP for top ranking Taekwons over level 90.
        }
        if ((character.getClassId() & MAPID_UPPERMASK) == JobHex.SUPER_NOVICE.getId() && character.getBaseLevel() >= 99) {
            val += 2000; //Supernovice lvl99 hp bonus.
        }
        val += val * character.getVit() / 100; // +1% per each point of VIT
        System.out.println("## Second val: " + val);

        if ((character.getClassId() & JOBL_UPPER) != 0) {
            System.out.println("## Char is advanced!");
            val += val * 25 / 100; //Trans classes get a 25% hp bonus
        } else if ((character.getClassId() & JOBL_BABY) != 0) {
            System.out.println("## Char is a baby!");
            val -= val * 30 / 100; //Baby classes get a 30% hp penalty
        }
        System.out.println("## Final val: " + val);
        return val;
    }

    static int pc_class2idx(int class_) {
        if (class_ >= JOB_NOVICE_HIGH) {
            return class_ - JOB_NOVICE_HIGH + JOB_MAX_BASIC;
        }
        return class_;
    }

    /**
     * Check whether a player ID is in the fame rankers' list of its job, returns his/her position if so, 0 else
     * @param char_id
     * @param job
     * @return
     */
    static boolean pc_famerank(long char_id, int job) {
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

    static int status_readdb()
            throws FileNotFoundException, IOException {
        int i, j, class_;
        FileReader job_db1 = new FileReader("I:/projects/job_db1.txt");
        BufferedReader reader_db1 = new BufferedReader(job_db1);

        i = 0;
        String line;
        while ((line = reader_db1.readLine()) != null) {
            //NOTE: entry MAX_WEAPON_TYPE is not counted
            String split[] = new String[5 + weapon_type.MAX_WEAPON_TYPE.ordinal()];
            i++;
            if (line.startsWith("//")) {
                continue;
            }
            split = line.split(",");

            class_ = Integer.parseInt(split[0].trim());
            if (!pcdb_checkid(class_)) {
                continue;
            }
            class_ = pc_class2idx(class_);
            max_weight_base[class_] = Integer.parseInt(split[1].trim());
            hp_coefficient[class_] = Integer.parseInt(split[2].trim());
            hp_coefficient2[class_] = Integer.parseInt(split[3].trim());
            sp_coefficient[class_] = Integer.parseInt(split[4].trim());
            for (j = 0; j < weapon_type.MAX_WEAPON_TYPE.ordinal(); j++) {
                aspd_base[class_][j] = Integer.parseInt(split[j + 5].trim());
            }
        }
        reader_db1.close();
        System.out.println("## Done reading job bonuses file");
        //memset(job_bonus,0,sizeof(job_bonus)); // JobHex-specific stats bonus
        System.out.println("## Opening job_db2.txt");//sprintf(path, "%s/job_db2.txt", db_path);

        FileReader job_db2 = new FileReader("I:/projects/job_db1.txt");
        BufferedReader reader_db2 = new BufferedReader(job_db2);

        while ((line = reader_db2.readLine()) != null) {
            String[] split = new String[MAX_LEVEL + 1]; //JobHex Level is limited to MAX_LEVEL, so the bonuses should likewise be limited to it. [Skotlex]
            if (line.startsWith("//")) {
                continue;
            }
            split = line.split(",");
            class_ = Integer.parseInt(split[0].trim());
            if (!pcdb_checkid(class_)) {
                continue;
            }
            class_ = pc_class2idx(class_);
            for (i = 1; i < split.length; i++) {
                job_bonus[class_][i - 1] = Integer.parseInt(split[i].trim());
            }
        }
        job_db2.close();
        System.out.println("## Done reading job_db2");
        return 0;
    }

    /**
     * Checks if the given class value corresponds to a player class. [Skotlex]
     * @param class_
     * @return
     */
    static boolean pcdb_checkid(int class_) {
        if (class_ < JOB_MAX_BASIC || (class_ >= JOB_NOVICE_HIGH && class_ < JOB_MAX)) {
            return true;

        }
        return false;
    }
}
