package top.kealine.netmusic.datagetter;

import org.json.JSONObject;
import top.kealine.netmusic.Settings;
import top.kealine.netmusic.model.*;
import top.kealine.netmusic.util.HTTPUtil;

public class SongGetter {

    private static BeanSong build(JSONObject data){
        BeanSong item = new BeanSong();

        item.setId(data.getLong("id"));
        item.setName(data.getString("name"));
        item.setSingers(SinggerGetter.run(data.getJSONArray("ar")));
        item.setLrc(LrcGetter.run(item.getId()));
        item.setComments(CommentsGetter.run(item.getId()));
        item.setComments_cnt(CommentsGetter.comment_cnt);
        item.setUrl(UrlGetter.run(item.getId()));
        item.setPublish_time(data.getLong("publishTime"));
        item.setJson(data.toString());

        return item;
    }

    public static BeanSong run(long song_id){
        String url = Settings.host + "/music/detail?id=" + song_id;
        String json = HTTPUtil.get(url);
        return build((new JSONObject(json)).getJSONArray("songs").getJSONObject(0));
    }

    public static void main(String[] args) {
        BeanSong testData = run(1338414110);
        System.out.println(testData);
    }
}
