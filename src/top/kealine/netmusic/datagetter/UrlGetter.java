package top.kealine.netmusic.datagetter;

import top.kealine.netmusic.Settings;
import top.kealine.netmusic.util.HTTPUtil;

public class UrlGetter {
    public static String run(long song_id){
        String url = Settings.host + "/music/url?br=128000&id=" + song_id;
        Settings.log(url);
        return HTTPUtil.get(url);
    }
    public static void main(String[] args) {
        String testData = run(1365898499);
        System.out.println(testData);
    }
}
