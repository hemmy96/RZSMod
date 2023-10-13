package com.menitimu.rzs.util;

public class RoundData {

    public static Round[] DeadEnd;
    public static Round[] BadBlood;
    public static Round[] AlienArcadium;
    private static final int OLD = 1;
    private static final int GIANT = 1 << 1;
    private static final int CUBE = 1 << 2;
    private static final int SLIME = 1 << 3;
    private static final int BLAZE = 1 << 4;
    private static final int GOLEM = 1 << 5;
    private static final int BOSS = 1 << 6;
    private static final int GHAST = 1 << 7;
    private static final int NOITEM = 1 << 8;
    private static final int NO_UFO = 1 << 9;
    private static final int UFO_ONLY = 1 << 10;
    private static final int NO_WINDOW = 1 << 11;
    private static final int WE = 1 << 12;
    private static final int MINION = 1 << 13;
    public static void initRoundData(){
        DeadEnd = new Round[] {
                //Round1
                new Round(new Wave[] {new Wave(200), new Wave(400)}),
                new Round(new Wave[] {new Wave(200), new Wave(400)}),
                new Round(new Wave[] {new Wave(200), new Wave(400), new Wave(700)}),
                new Round(new Wave[] {new Wave(200), new Wave(400), new Wave(700)}),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(740)}, NOITEM),
                //Round6
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(880)}),
                new Round(new Wave[] {new Wave(200), new Wave(500), new Wave(940)}),
                new Round(new Wave[] {new Wave(200), new Wave(500), new Wave(1000)}),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(760)}),
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(900)}, NOITEM),
                //Round11
                new Round(new Wave[] {new Wave(200), new Wave(500), new Wave(960)}),
                new Round(new Wave[] {new Wave(200), new Wave(500), new Wave(1000)}),
                new Round(new Wave[] {new Wave(200), new Wave(500), new Wave(1000)}),
                new Round(new Wave[] {new Wave(200), new Wave(500), new Wave(900)}),
                new Round(new Wave[] {new Wave(200, BLAZE), new Wave(500), new Wave(920)}, NOITEM | BLAZE),
                //Round16
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(940)}),
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(940)}),
                new Round(new Wave[] {new Wave(200, BLAZE), new Wave(480), new Wave(940)}, BLAZE),
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(940)}),
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(980)}, NOITEM),
                //Round21
                new Round(new Wave[] {new Wave(200), new Wave(460), new Wave(880)}),
                new Round(new Wave[] {new Wave(200), new Wave(460), new Wave(900)}),
                new Round(new Wave[] {new Wave(200), new Wave(460), new Wave(840)}),
                new Round(new Wave[] {new Wave(200), new Wave(460), new Wave(860)}),
                new Round(new Wave[] {new Wave(200), new Wave(460), new Wave(860)}, NOITEM),
                //Round26
                new Round(new Wave[] {new Wave(200), new Wave(460), new Wave(720)}),
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(880)}),
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(840)}),
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(840)}),
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(900)} ,NOITEM)
        };
        BadBlood = new Round[] {
                //Round1
                new Round(new Wave[] {new Wave(200), new Wave(440)}),
                new Round(new Wave[] {new Wave(200), new Wave(440)}),
                new Round(new Wave[] {new Wave(200), new Wave(440)}, SLIME),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, SLIME),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, NOITEM),
                //Round6
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, SLIME),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, NOITEM),
                //Round11
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, WE),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, WE),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, WE),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, NOITEM),
                //Round16
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, WE),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, WE),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, WE),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, WE | NOITEM),
                //Round21
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, MINION),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, NOITEM),
                //Round26
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(760)}, MINION),
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(760)}, MINION),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}),
                new Round(new Wave[] {new Wave(200), new Wave(480), new Wave(760)}, MINION),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}, NOITEM | MINION)
        };
        AlienArcadium =  new Round[] {
                //Round1
                new Round(new Wave[] {new Wave(200), new Wave(260), new Wave(320), new Wave(380)}, NO_UFO),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440)}, NO_UFO),
                new Round(new Wave[] {new Wave(200), new Wave(260), new Wave(320), new Wave(380)}, NO_UFO),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(340), new Wave(420), new Wave(500), new Wave(560)}, NO_UFO),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520), new Wave(600)}, NO_UFO),
                //Round6
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(380), new Wave(460), new Wave(560), new Wave(640)}, NO_UFO),
                new Round(new Wave[] {new Wave(200), new Wave(300), new Wave(380), new Wave(460), new Wave(540), new Wave(620)}, NO_UFO),
                new Round(new Wave[] {new Wave(200), new Wave(300), new Wave(400), new Wave(500), new Wave(600), new Wave(700)}, NO_UFO),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(340), new Wave(460), new Wave(560), new Wave(640)}, NO_UFO),
                new Round(new Wave[] {new Wave(200, SLIME), new Wave(320, SLIME), new Wave(440, SLIME), new Wave(540, SLIME), new Wave(660, SLIME), new Wave(760, SLIME)}, SLIME),
                //Round11
                new Round(new Wave[] {new Wave(200), new Wave(320), new Wave(420), new Wave(540), new Wave(640), new Wave(760)}),
                new Round(new Wave[] {new Wave(200), new Wave(320), new Wave(440), new Wave(560), new Wave(680), new Wave(800)}),
                new Round(new Wave[] {new Wave(200), new Wave(320), new Wave(440), new Wave(560), new Wave(680), new Wave(800)}),
                new Round(new Wave[] {new Wave(200), new Wave(320), new Wave(420), new Wave(520), new Wave(620), new Wave(720)}),
                new Round(new Wave[] {new Wave(200, SLIME), new Wave(340, SLIME), new Wave(480, SLIME), new Wave(620, SLIME), new Wave(760, SLIME), new Wave(920, SLIME | GIANT)}, SLIME),
                //Round16
                new Round(new Wave[] {new Wave(200, SLIME), new Wave(320, SLIME), new Wave(440, SLIME), new Wave(540, SLIME), new Wave(660, SLIME), new Wave(760, SLIME)}, SLIME),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(380), new Wave(460), new Wave(560), new Wave(640)}),
                new Round(new Wave[] {new Wave(200, SLIME | GHAST), new Wave(280, SLIME | GHAST), new Wave(380, SLIME | GHAST), new Wave(460, SLIME | GHAST), new Wave(560, SLIME | GHAST), new Wave(640, SLIME | GHAST)}, SLIME),
                new Round(new Wave[] {new Wave(200, GHAST), new Wave(280, GHAST), new Wave(360, GHAST), new Wave(440, GHAST), new Wave(520, GHAST), new Wave(600, GHAST)}),
                new Round(new Wave[] {new Wave(200), new Wave(300, GOLEM), new Wave(420, GIANT), new Wave(520), new Wave(620, GIANT), new Wave(720)}, GOLEM),
                //Round21
                new Round(new Wave[] {new Wave(200, GHAST), new Wave(280, GHAST), new Wave(380, GHAST), new Wave(460, GHAST), new Wave(560, GHAST), new Wave(640, GHAST)}, UFO_ONLY),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(380), new Wave(460, GIANT), new Wave(560), new Wave(680, GIANT)}),
                new Round(new Wave[] {new Wave(200, SLIME), new Wave(280, SLIME), new Wave(360, SLIME), new Wave(440, SLIME), new Wave(520, SLIME), new Wave(600, SLIME)}, SLIME),
                new Round(new Wave[] {new Wave(200, GHAST), new Wave(280, GIANT | GHAST), new Wave(380, GHAST), new Wave(460, GIANT | GHAST), new Wave(560, GHAST), new Wave(640, GIANT | GHAST)}, NOITEM | UFO_ONLY),
                new Round(new Wave[] {new Wave(200, BOSS)}, NO_UFO | NO_WINDOW | NOITEM),
                //Round26
                new Round(new Wave[] {new Wave(200, CUBE), new Wave(460, CUBE), new Wave(720, CUBE)}, CUBE | NO_UFO | NO_WINDOW),
                new Round(new Wave[] {new Wave(200), new Wave(440), new Wave(680)}),
                new Round(new Wave[] {new Wave(200, GHAST), new Wave(400, GHAST), new Wave(600, GHAST)}),
                new Round(new Wave[] {new Wave(200, CUBE), new Wave(480, CUBE), new Wave(760, CUBE)}, CUBE),
                new Round(new Wave[] {new Wave(200, GIANT | GHAST), new Wave(440, GIANT | GHAST), new Wave(680, GIANT | GHAST)}),
                //Round31
                new Round(new Wave[] {new Wave(200, CUBE), new Wave(440, CUBE | GOLEM), new Wave(680, CUBE | GOLEM)}, CUBE | GOLEM),
                new Round(new Wave[] {new Wave(200), new Wave(420, GOLEM), new Wave(640, GOLEM)}, GOLEM),
                new Round(new Wave[] {new Wave(200, CUBE | GHAST), new Wave(440, CUBE | GOLEM | GHAST), new Wave(680, CUBE | GOLEM | GHAST)}, CUBE | GOLEM),
                new Round(new Wave[] {new Wave(200, CUBE), new Wave(440, CUBE | GOLEM), new Wave(680, CUBE | GOLEM)}, CUBE | GOLEM),
                new Round(new Wave[] {new Wave(200, BOSS)}, NO_UFO | NO_WINDOW | NOITEM),
                //Round36
                new Round(new Wave[] {new Wave(200), new Wave(440, GIANT), new Wave(680, GIANT)}),
                new Round(new Wave[] {new Wave(200, GHAST), new Wave(400, GIANT | GHAST), new Wave(620, GIANT | GHAST)}, UFO_ONLY),
                new Round(new Wave[] {new Wave(200), new Wave(440, GIANT), new Wave(680, GIANT)}),
                new Round(new Wave[] {new Wave(200, CUBE), new Wave(440, GIANT | CUBE), new Wave(680, GIANT | CUBE)}, CUBE | NO_WINDOW | NO_UFO),
                new Round(new Wave[] {new Wave(200, GHAST), new Wave(440, GIANT | GHAST), new Wave(680, GIANT | GHAST), new Wave(740), new Wave(900, OLD)}),
                //Round41
                new Round(new Wave[] {new Wave(200, GOLEM | GHAST), new Wave(420, GIANT | GOLEM | GHAST), new Wave(640, GIANT | GOLEM | GHAST)}, GOLEM),
                new Round(new Wave[] {new Wave(200, GIANT | GOLEM), new Wave(440, GIANT | GOLEM), new Wave(680, GIANT | GOLEM)}, GOLEM),
                new Round(new Wave[] {new Wave(200, SLIME | CUBE), new Wave(260, GIANT), new Wave(440, SLIME | CUBE), new Wave(500, GIANT), new Wave(680, SLIME | CUBE), new Wave(740, GIANT)}, NO_UFO | NO_WINDOW | SLIME),
                new Round(new Wave[] {new Wave(200, GIANT | GOLEM), new Wave(440, GIANT | GOLEM), new Wave(680, GIANT | GOLEM)}, GOLEM),
                new Round(new Wave[] {new Wave(200), new Wave(440, GIANT | GOLEM), new Wave(680, GHAST | OLD), new Wave(700, OLD)}, GOLEM),
                //Round46
                new Round(new Wave[] {new Wave(200, GOLEM), new Wave(420, GOLEM), new Wave(640), new Wave(700, GOLEM | OLD)}, NO_UFO | NO_WINDOW | GOLEM),
                new Round(new Wave[] {new Wave(200, SLIME | GOLEM | GHAST), new Wave(400, SLIME | GOLEM | GHAST), new Wave(600, GIANT | GOLEM | GHAST)}, SLIME | GOLEM | NO_WINDOW | NO_UFO),
                new Round(new Wave[] {new Wave(200, GOLEM), new Wave(400, GOLEM), new Wave(600, GOLEM), new Wave(660, OLD)}, GOLEM),
                new Round(new Wave[] {new Wave(200), new Wave(420), new Wave(640)}),
                new Round(new Wave[] {new Wave(200, GOLEM), new Wave(440, GIANT | GOLEM), new Wave(680, GOLEM), new Wave(740, GIANT)}, GOLEM),
                //Round51
                new Round(new Wave[] {new Wave(200, GOLEM), new Wave(400, GIANT | GOLEM), new Wave(600, GOLEM), new Wave(660, GIANT)}, GOLEM),
                new Round(new Wave[] {new Wave(200, GOLEM | CUBE), new Wave(440, GIANT | GOLEM | CUBE), new Wave(680, GOLEM | CUBE), new Wave(740, GIANT)}, GOLEM | CUBE | NO_UFO | NO_WINDOW),
                new Round(new Wave[] {new Wave(200, GOLEM), new Wave(440, GIANT | GOLEM), new Wave(680, GOLEM), new Wave(740, GIANT)}, GOLEM),
                new Round(new Wave[] {new Wave(200, GOLEM), new Wave(400, GIANT | GOLEM | OLD), new Wave(640, GOLEM), new Wave(700, GIANT), new Wave(780, OLD)}, GOLEM),
                new Round(new Wave[] {new Wave(200, GIANT | GOLEM | CUBE), new Wave(320, GIANT | GOLEM | CUBE), new Wave(440, GIANT | GOLEM | CUBE), new Wave(560, GIANT | GOLEM | CUBE), new Wave(680, GIANT | GOLEM | CUBE | OLD), new Wave(800, OLD)}, GOLEM | CUBE),
                //Round56
                new Round(new Wave[] {new Wave(200, GOLEM | BOSS), new Wave(280, GOLEM), new Wave(360, GOLEM)}, NOITEM | NO_WINDOW | NO_UFO | GOLEM),
                new Round(new Wave[] {new Wave(200, GOLEM | BOSS), new Wave(280, GOLEM), new Wave(360, GOLEM)}, NOITEM | NO_WINDOW | NO_UFO | GOLEM),
                new Round(new Wave[] {new Wave(200, GOLEM), new Wave(440, GIANT | GOLEM | OLD), new Wave(680, GOLEM), new Wave(740, GIANT), new Wave(760, OLD)}, GOLEM),
                new Round(new Wave[] {new Wave(200, OLD), new Wave(280, OLD), new Wave(360, OLD), new Wave(440, OLD), new Wave(520, OLD), new Wave(600, OLD)}),
                new Round(new Wave[] {new Wave(200), new Wave(400), new Wave(600, OLD), new Wave(660, OLD)}),
                //Round61
                new Round(new Wave[] {new Wave(200), new Wave(280, CUBE), new Wave(360, CUBE), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE)}, CUBE),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520), new Wave(600)}),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520), new Wave(600)}),
                new Round(new Wave[] {new Wave(200), new Wave(280, BLAZE), new Wave(360, BLAZE), new Wave(440, BLAZE), new Wave(520, OLD| BLAZE), new Wave(600, OLD | BLAZE)}, BLAZE),
                new Round(new Wave[] {new Wave(200), new Wave(280, BLAZE), new Wave(360), new Wave(440, GIANT), new Wave(520, GIANT), new Wave(600, GIANT)}, BLAZE),
                //Round66
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE)}, CUBE),
                new Round(new Wave[] {new Wave(200, CUBE), new Wave(280, CUBE), new Wave(360, CUBE), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE | OLD)}, CUBE),
                new Round(new Wave[] {new Wave(200), new Wave(280, GOLEM), new Wave(360, GOLEM), new Wave(440, GOLEM), new Wave(520, GOLEM | OLD), new Wave(600, GOLEM | OLD)}, GOLEM),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520, OLD), new Wave(600, OLD)}),
                new Round(new Wave[] {new Wave(200), new Wave(280, GOLEM | OLD), new Wave(360, GOLEM | OLD), new Wave(440, GIANT | GOLEM | OLD), new Wave(520, GIANT | GOLEM | OLD), new Wave(600, GIANT | GOLEM | OLD)}, GOLEM),
                //Round71
                new Round(new Wave[] {new Wave(200), new Wave(280, CUBE), new Wave(360, CUBE), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE)}, CUBE),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520), new Wave(600)}),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520), new Wave(600)}),
                new Round(new Wave[] {new Wave(200), new Wave(280, BLAZE), new Wave(360, BLAZE), new Wave(440, OLD| BLAZE), new Wave(520, OLD| BLAZE), new Wave(600, OLD| BLAZE)}, BLAZE),
                new Round(new Wave[] {new Wave(200), new Wave(280, BLAZE), new Wave(360), new Wave(440, GIANT), new Wave(520, GIANT), new Wave(600, GIANT)}, BLAZE),
                //Round76
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE)}, CUBE),
                new Round(new Wave[] {new Wave(200, CUBE), new Wave(280, CUBE), new Wave(360, CUBE), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE | OLD)}, CUBE),
                new Round(new Wave[] {new Wave(200), new Wave(280, GOLEM), new Wave(360, GOLEM), new Wave(440, GOLEM), new Wave(520, GOLEM | OLD), new Wave(600, GOLEM | OLD)}, GOLEM),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520, OLD), new Wave(600, OLD)}),
                new Round(new Wave[] {new Wave(200), new Wave(280, GOLEM | OLD), new Wave(360, GOLEM | OLD), new Wave(440, GIANT | GOLEM | OLD), new Wave(520, GIANT | GOLEM | OLD), new Wave(600, GIANT | GOLEM | OLD)}, GOLEM),
                //Round81
                new Round(new Wave[] {new Wave(200), new Wave(280, CUBE), new Wave(360, CUBE), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE)}, CUBE),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520), new Wave(600)}),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520), new Wave(600)}),
                new Round(new Wave[] {new Wave(200), new Wave(280, BLAZE), new Wave(360, BLAZE), new Wave(440, OLD| BLAZE), new Wave(520, OLD| BLAZE), new Wave(600, OLD| BLAZE)}, BLAZE),
                new Round(new Wave[] {new Wave(200), new Wave(280, BLAZE), new Wave(360), new Wave(440, GIANT), new Wave(520, GIANT), new Wave(600, GIANT)}, BLAZE),
                //Round86
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE)}, CUBE),
                new Round(new Wave[] {new Wave(200, CUBE), new Wave(280, CUBE), new Wave(360, CUBE), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE | OLD)}, CUBE),
                new Round(new Wave[] {new Wave(200), new Wave(280, GOLEM), new Wave(360, GOLEM), new Wave(440, GOLEM), new Wave(520, GOLEM | OLD), new Wave(600, GOLEM | OLD)}, GOLEM),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520, OLD), new Wave(600, OLD)}),
                new Round(new Wave[] {new Wave(200), new Wave(280, GOLEM | OLD), new Wave(360, GOLEM | OLD), new Wave(440, GIANT | GOLEM | OLD), new Wave(520, GIANT | GOLEM | OLD), new Wave(600, GIANT | GOLEM | OLD)}, GOLEM),
                //Round91
                new Round(new Wave[] {new Wave(200), new Wave(280, CUBE), new Wave(360, CUBE), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE)}, CUBE),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520), new Wave(600)}),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520), new Wave(600)}),
                new Round(new Wave[] {new Wave(200), new Wave(280, BLAZE), new Wave(360, BLAZE), new Wave(440, OLD| BLAZE), new Wave(520, OLD| BLAZE), new Wave(600, OLD| BLAZE)}, BLAZE),
                new Round(new Wave[] {new Wave(200), new Wave(280, BLAZE), new Wave(360), new Wave(440, GIANT), new Wave(520, GIANT), new Wave(600, GIANT)}, BLAZE),
                //Round96
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE)}, CUBE),
                new Round(new Wave[] {new Wave(200, CUBE), new Wave(280, CUBE), new Wave(360, CUBE), new Wave(440, CUBE), new Wave(520, CUBE), new Wave(600, CUBE | OLD)}, CUBE),
                new Round(new Wave[] {new Wave(200), new Wave(280, GOLEM), new Wave(360, GOLEM), new Wave(440, GOLEM), new Wave(520, GOLEM | OLD), new Wave(600, GOLEM | OLD)}, GOLEM),
                new Round(new Wave[] {new Wave(200), new Wave(280), new Wave(360), new Wave(440), new Wave(520), new Wave(600, OLD)}),
                new Round(new Wave[] {new Wave(200), new Wave(280, GOLEM | OLD), new Wave(360, GOLEM | OLD), new Wave(440, GIANT | GOLEM | OLD), new Wave(520, GIANT | GOLEM | OLD), new Wave(600, GIANT | GOLEM | OLD)}, GOLEM),
                //Round101
                new Round(new Wave[] {new Wave(100, BOSS)}, NOITEM | NO_UFO | NO_WINDOW | WE),
                new Round(new Wave[] {new Wave(100, OLD)}, NO_UFO),
                new Round(new Wave[] {new Wave(100, OLD)}, NO_UFO),
                new Round(new Wave[] {new Wave(100, OLD)}, NO_UFO),
                new Round(new Wave[] {new Wave(100, OLD)}, NO_UFO),
                //いや多すぎ 頭痛いわ
        };
    }
}
