package top.kealine.netmusic;

import java.util.Date;

public class Settings {
    public final static String host = "http://localhost:3000/v1";
    public static void log(String url){
        String name=Thread.currentThread().getStackTrace()[2].getClassName();
        System.out.println("["+name+"] - "+(new Date()).toString()+" - GET "+url);
    }
}
