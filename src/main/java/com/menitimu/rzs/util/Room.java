package com.menitimu.rzs.util;

import com.menitimu.rzs.config.RZSConfig;

public class Room {
    public static boolean[] rooms = new boolean[8];
    /*
    0: Alley, Courtyard, Park Entrance
    1: Office, Mansion, Roller Coaster
    2: Hotel, Library, Ferris Wheel
    3: Apartment, Dungeon, Bumper Car
    4: Power Station, Crypts
    5: Garden, Balcony
    6: Roof Top, Graveyard
    7: Gallery, Great Hall
     */
    public static void init(){
        rooms = new boolean[8];
        rooms[0] = true;
    }
    public static void toggleDeRoom(){
        if(RandomStuff.map == RandomStuff.MapType.DE)
            rooms[RZSConfig.DeRoomId] = !rooms[RZSConfig.DeRoomId];
    }
    public static void toggleBbRoom(){
        if(RandomStuff.map == RandomStuff.MapType.BB)
            rooms[RZSConfig.BbRoomId] = !rooms[RZSConfig.BbRoomId];
    }
    public static void toggleAaRoom(){
        if(RandomStuff.map == RandomStuff.MapType.AA)
            rooms[RZSConfig.AaRoomId] = !rooms[RZSConfig.AaRoomId];
    }
    public static void onRoomOpen(String text){
        if(text.contains("Office") || text.contains("Mansion") || text.contains("Roller Coaster"))
            rooms[1] = true;
        if(text.contains("Hotel") || text.contains("Library") || text.contains("Ferris Wheel"))
            rooms[2] = true;
        if(text.contains("Apartment") || text.contains("Dungeon") || text.contains("Bumper Car"))
            rooms[3] = true;
        if(text.contains("Power Station") || text.contains("Crypts"))
            rooms[4] = true;
        if(text.contains("Garden") || text.contains("Balcony"))
            rooms[5] = true;
        if(text.contains("Roof Top") || text.contains("Graveyard"))
            rooms[6] = true;
        if(text.contains("Gallery") || text.contains("Great Hall"))
            rooms[7] = true;
    }
}