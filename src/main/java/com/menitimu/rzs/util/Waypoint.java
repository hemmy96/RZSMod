package com.menitimu.rzs.util;

import cc.polyfrost.oneconfig.config.core.OneColor;

public class Waypoint {
    private String name;
    private double[] point;
    private boolean isActive;
    private boolean showName;
    private boolean showHead;
    private int type;
    private int showRound;
    private int stopRound;
    private int[] color;
    private int room;
    public Waypoint(){
        this.name = "Waypoint";
        this.point = new double[]{0, 0, 0};
        this.isActive = true;
        this.showName = false;
        this.showHead = true;
        this.type = 0;
        this.showRound = 1;
        this.stopRound = 105;
        this.color = new int[]{255, 255, 255};
        this.room = 0;
    }
    public Waypoint(String name, int room, double[] point){
        this.name = name;
        this.point = point;
        this.isActive = true;
        this.showName = false;
        this.showHead = true;
        this.type = 0;
        this.showRound = 1;
        this.stopRound = 105;
        this.color = new int[]{255, 255, 255};
        this.room = room;
    }
    public Waypoint(String name, int room, double[] point,  int type){
        this.name = name;
        this.point = point;
        this.isActive = true;
        this.showName = false;
        this.showHead = true;
        this.type = type;
        this.showRound = 1;
        this.stopRound = 105;
        this.color = new int[]{255, 255, 255};
        this.room = room;
    }
    public Waypoint(String name, int room, double[] point,  int type, int[] color){
        this.name = name;
        this.point = point;
        this.isActive = true;
        this.showName = false;
        this.showHead = true;
        this.type = type;
        this.showRound = 1;
        this.stopRound = 105;
        this.color = color;
        this.room = room;
    }
    public String getName(){
        return this.name;
    }
    public Point getPoint(){
        return new Point(point[0], point[1], point[2]);
    }
    public boolean isActive(){
        return this.isActive;
    }
    public boolean showName(){
        return showName;
    }
    public boolean showHead(){
        return this.showHead;
    }
    public int getType(){
        return this.type;
    }
    public int getShowRound(){
        return this.showRound;
    }
    public int getStopRound(){
        return this.stopRound;
    }
    public OneColor getColor(){
        return new OneColor(color[0], color[1], color[2]);
    }
    public int getRoom(){
        return room;
    }
}
