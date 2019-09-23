package top.kealine.netmusic.datagetter;

import top.kealine.netmusic.Settings;
import top.kealine.netmusic.util.HTTPUtil;

public class LrcGetter {
    public static String run(long song_id){
        String url = Settings.host + "/lyric?id=" + song_id;
        String json = HTTPUtil.get(url);
        return json;
    }

    public static void main(String[] args) {
        System.out.println(run(557407148));
    }
}
