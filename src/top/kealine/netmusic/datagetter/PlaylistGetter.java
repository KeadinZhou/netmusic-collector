package top.kealine.netmusic.datagetter;
import top.kealine.netmusic.Settings;
import top.kealine.netmusic.util.*;
import org.json.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class PlaylistGetter {
    public static void run(String type){
        int count = 0;
        int maxCount = 1;
        final int limit = 35;

        Connection conn = null;
        String sql = "INSERT INTO playlist(playlist_id,playlist_name,json) VALUES(?,?,?)";

        try {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            while(count < maxCount) {
                String url = Settings.host + "/playlist?type=" + type + "&offset=" + count + "&limit=" + limit;
                String json = HTTPUtil.get(url);
                System.out.println("[System] - " + (new Date()).toString() + " - GET " + url); //LOG
                JSONObject data = new JSONObject(json);
                JSONArray array = data.getJSONArray("playlists");
                for(int index=0;index<array.length();index++){
                    JSONObject item = array.getJSONObject(index);
                    long id = item.getLong("id");
                    String name = item.getString("name");
                    ps.setLong(1,id);
                    ps.setString(2,name);
                    ps.setString(3,item.toString());
                    ps.execute();
                    count++;
                }
                maxCount = data.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        run("%E5%8F%A4%E9%A3%8E"); //古风
    }
}
