package com.menitimu.rzs.util;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class YourPB {
    @SerializedName("DENormalPB")
    private int[] DENormalPB;
    @SerializedName("DENRoundPB")
    private int[] DENRoundPB;
    @SerializedName("DEHardPB")
    private int[] DEHardPB;
    @SerializedName("DEHRoundPB")
    private int[] DEHRoundPB;
    @SerializedName("DERipPB")
    private int[] DERipPB;
    @SerializedName("DERRoundPB")
    private int[] DERRoundPB;
    @SerializedName("BBNormalPB")
    private int[] BBNormalPB;
    @SerializedName("BBNRoundPB")
    private int[] BBNRoundPB;
    @SerializedName("BBHardPB")
    private int[] BBHardPB;
    @SerializedName("BBHRoundPB")
    private int[] BBHRoundPB;
    @SerializedName("BBRipPB")
    private int[] BBRipPB;
    @SerializedName("BBRRoundPB")
    private int[] BBRRoundPB;
    @SerializedName("AAQuadPB")
    private int[] AAQuadPB;
    @SerializedName("AAQRoundPB")
    private int[] AAQRoundPB;
    @SerializedName("AATrioPB")
    private int[] AATrioPB;
    @SerializedName("AATRoundPB")
    private int[] AATRoundPB;
    @SerializedName("AADuoPB")
    private int[] AADuoPB;
    @SerializedName("AADRoundPB")
    private int[] AADRoundPB;
    @SerializedName("AASoloPB")
    private int[] AASoloPB;
    @SerializedName("AASRoundPB")
    private int[] AASRoundPB;
    public YourPB(){
        DENormalPB = new int[30];
        DENRoundPB = new int[30];
        DEHardPB = new int[30];
        DEHRoundPB = new int[30];
        DERipPB = new int[30];
        DERRoundPB = new int[30];
        BBNormalPB = new int[30];
        BBNRoundPB = new int[30];
        BBHardPB = new int[30];
        BBHRoundPB = new int[30];
        BBRipPB = new int[30];
        BBRRoundPB = new int[30];
        AAQuadPB = new int[105];
        AAQRoundPB = new int[105];
        AATrioPB = new int[105];
        AATRoundPB = new int[105];
        AADuoPB = new int[105];
        AADRoundPB = new int[105];
        AASoloPB = new int[105];
        AASRoundPB = new int[105];
    }
    public void init(){
        Arrays.fill(DENormalPB, -1);
        Arrays.fill(DENRoundPB, -1);
        Arrays.fill(DEHardPB, -1);
        Arrays.fill(DEHRoundPB, -1);
        Arrays.fill(DERipPB, -1);
        Arrays.fill(DERRoundPB, -1);
        Arrays.fill(BBNormalPB, -1);
        Arrays.fill(BBHRoundPB, -1);
        Arrays.fill(BBHardPB, -1);
        Arrays.fill(BBHRoundPB, -1);
        Arrays.fill(BBRipPB, -1);
        Arrays.fill(BBRRoundPB, -1);
        Arrays.fill(AAQuadPB, -1);
        Arrays.fill(AAQRoundPB, -1);
        Arrays.fill(AATrioPB, -1);
        Arrays.fill(AATRoundPB, -1);
        Arrays.fill(AADuoPB, -1);
        Arrays.fill(AADRoundPB, -1);
        Arrays.fill(AASoloPB, -1);
        Arrays.fill(AASRoundPB, -1);
    }
    public int setPB(int value, int round, RandomStuff.MapType mapType, boolean isRound, RandomStuff.Difficulty difficulty){
        int result = -1;
        switch (mapType) {
            case DE:
                switch (difficulty) {
                    case NORMAL:
                        if(isRound) {
                            result = DENRoundPB[round];
                            if(DENRoundPB[round] == -1 || DENRoundPB[round] > value)
                                DENRoundPB[round] = value;
                        } else {
                            result = DENormalPB[round];
                            if(DENormalPB[round] == -1 || DENormalPB[round] > value)
                                DENormalPB[round] = value;
                        }
                        break;
                    case HARD:
                        if(isRound) {
                            result = DEHRoundPB[round];
                            if(DEHRoundPB[round] == -1 || DEHRoundPB[round] > value)
                                DEHRoundPB[round] = value;
                        } else {
                            result = DEHardPB[round];
                            if(DEHardPB[round] == -1 || DEHardPB[round] > value)
                                DEHardPB[round] = value;
                        }
                        break;
                    case RIP:
                        if(isRound) {
                            result = DERRoundPB[round];
                            if(DERRoundPB[round] == -1 || DERRoundPB[round] > value)
                                DERRoundPB[round] = value;
                        } else {
                            result = DERipPB[round];
                            if(DERipPB[round] == -1 || DERipPB[round] > value)
                                DERipPB[round] = value;
                        }
                        break;
                }
                break;
            case BB:
                switch (difficulty) {
                    case NORMAL:
                        if(isRound) {
                            result = BBNRoundPB[round];
                            if(BBNRoundPB[round] == -1 || BBNRoundPB[round] > value)
                                BBNRoundPB[round] = value;
                        } else {
                            result = DENormalPB[round];
                            if(BBNormalPB[round] == -1 || BBNormalPB[round] > value)
                                BBNormalPB[round] = value;
                        }
                        break;
                    case HARD:
                        if(isRound) {
                            result = BBHRoundPB[round];
                            if(BBHRoundPB[round] == -1 || BBHRoundPB[round] > value)
                                BBHRoundPB[round] = value;
                        } else {
                            result = BBHardPB[round];
                            if(BBHardPB[round] == -1 || BBHardPB[round] > value)
                                BBHardPB[round] = value;
                        }
                        break;
                    case RIP:
                        if(isRound) {
                            result = BBRRoundPB[round];
                            if(BBRRoundPB[round] == -1 || BBRRoundPB[round] > value)
                                BBRRoundPB[round] = value;
                        } else {
                            result = BBRipPB[round];
                            if(BBRipPB[round] == -1 || BBRipPB[round] > value)
                                BBRipPB[round] = value;
                        }
                        break;
                }
                break;
        }
        return result;
    }
    public int setAAPB(int value, int round, boolean isRound, int player){
        int result = -1;
        switch (player) {
            case 4:
                if(isRound){
                    result = AAQRoundPB[round];
                    if(AAQRoundPB[round] == -1 || AAQRoundPB[round] > value)
                        AAQRoundPB[round] = value;
                } else {
                    result = AAQuadPB[round];
                    if(AAQuadPB[round] == -1 || AAQuadPB[round] > value)
                        AAQuadPB[round] = value;
                }
                break;
            case 3:
                if(isRound){
                    result = AATRoundPB[round];
                    if(AATRoundPB[round] == -1 || AATRoundPB[round] > value)
                        AATRoundPB[round] = value;
                } else {
                    result = AATrioPB[round];
                    if(AATrioPB[round] == -1 || AATrioPB[round] > value)
                        AATrioPB[round] = value;
                }
                break;
            case 2:
                if(isRound){
                    result = AADRoundPB[round];
                    if(AADRoundPB[round] == -1 || AADRoundPB[round] > value)
                        AADRoundPB[round] = value;
                } else {
                    result = AADuoPB[round];
                    if(AADuoPB[round] == -1 || AADuoPB[round] > value)
                        AADuoPB[round] = value;
                }
                break;
            case 1:
                if(isRound){
                    result = AASRoundPB[round];
                    if(AASRoundPB[round] == -1 || AASRoundPB[round] > value)
                        AASRoundPB[round] = value;
                } else {
                    result = AASoloPB[round];
                    if(AASoloPB[round] == -1 || AASoloPB[round] > value)
                        AASoloPB[round] = value;
                }
                break;
        }
        return result;
    }
}
