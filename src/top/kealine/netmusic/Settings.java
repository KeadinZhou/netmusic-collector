package top.kealine.netmusic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Settings {
    public final static String host = "http://localhost:3000/v1";
    public static void log(String url){
        String name=Thread.currentThread().getStackTrace()[2].getClassName();
        System.out.println("["+name+"] - "+(new Date()).toString()+" - GET "+url);
    }
    public static void eLog(Exception e) throws IOException {
        String name=Thread.currentThread().getStackTrace()[2].getClassName();
        BufferedWriter out = new BufferedWriter(new FileWriter("D://CollectorError.log",true));
        out.write("["+name+"] - "+(new Date()).toString() +" - "+ e.toString()+"\n");
        out.close();
    }

    public static void main(String[] args) throws IOException {
        Exception e = new Exception("TEST");
        eLog(e);
    }
}
