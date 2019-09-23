package top.kealine.netmusic.datagetter;
import top.kealine.netmusic.Settings;
import top.kealine.netmusic.util.*;
import org.json.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SongListGetter {
    static Connection conn = null;
    static final String GET_SQL = "SELECT DISTINCT playlist_id FROM playlist";
    static final String PUT_SQL = "INSERT INTO songs_belong(playlist_id,song_id) VALUES(?,?)";
    static PreparedStatement psGet = null;
    static PreparedStatement psPut = null;

    private static void saveSongBelong(long playlistId,long songId){
        try{
            psPut.setLong(1,playlistId);
            psPut.setLong(2,songId);
            psPut.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void getSongs(long playlistId) {
        String url = Settings.host + "/playlist/detail?id=" + playlistId;
        Settings.log(url);
        String json = HTTPUtil.get(url);
        System.out.println("[System] - " + (new Date()).toString() + " - GET " + url); //LOG
        JSONObject data = (new JSONObject(json)).getJSONObject("playlist");
        JSONArray songsIds = data.getJSONArray("trackIds");
        for(int index=0;index<songsIds.length();index++){
            JSONObject item = songsIds.getJSONObject(index);
            long songId = item.getLong("id");
            saveSongBelong(playlistId,songId);
        }
    }
    public static void run(){
        try {
            conn = DBUtil.getConnection();
            psGet = conn.prepareStatement(GET_SQL);
            psPut = conn.prepareStatement(PUT_SQL);
            ResultSet rs = psGet.executeQuery();
            while(rs.next()){
                long id = rs.getLong(1);
                getSongs(id);
                // break; // Limit
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        run();
    }
}
