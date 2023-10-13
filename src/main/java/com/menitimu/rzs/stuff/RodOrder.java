package com.menitimu.rzs.stuff;

import com.menitimu.rzs.util.RandomStuff;

import java.util.List;

public class RodOrder {
    public static int lrTimes = 0;
    public static int players = 4;
    public static int displayTime = 0;
    public static int lrTimer = 0;
    public static void tickProcess(){
        List<String> lines;
        if(lrTimer > 0){
            lrTimer--;
            if(lrTimer == 0)
                lrTimes = 0;
        }
        if(displayTime > 100)
            displayTime--;
        else if (displayTime > 0)
            displayTime-=5;
        if(RandomStuff.isSBLoaded) {
            lines = RandomStuff.getScoreBoard();
            players = 4;
            for (int i = 5; i < 9; i++) {
                if (lines.get(i).endsWith("DEAD") || lines.get(i).endsWith("T"))
                    players--;
            }
            if(lrTimes > players && players != 0)
                lrTimes = lrTimes % players;
        }
    }
    public static void onRod(){
        if(lrTimes != -1) {
            displayTime = 320;
            lrTimer = 200;
            lrTimes++;
            if (lrTimes > players && players != 0)
                lrTimes = lrTimes % players;
        } else {
            displayTime = 0;
            lrTimes = 0;
        }
    }
    public static void onPlayer(){
        displayTime = 320;
        lrTimer = 200;
        players++;
        if(players > 4)
            players = 1;
        if(lrTimes >= players && players != 0)
            lrTimes = lrTimes % players;
    }
}
