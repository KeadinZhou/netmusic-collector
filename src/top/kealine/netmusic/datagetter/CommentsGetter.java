package top.kealine.netmusic.datagetter;

import org.json.JSONArray;
import org.json.JSONObject;
import top.kealine.netmusic.Settings;
import top.kealine.netmusic.model.BeanComment;
import top.kealine.netmusic.util.HTTPUtil;

import java.util.ArrayList;

public class CommentsGetter {
    public static long comment_cnt = 0;
    private static BeanComment build(JSONObject data){
        BeanComment item = new BeanComment();
        item.setId(data.getLong("commentId"));
        item.setContent(data.getString("content"));
        item.setLike(data.getLong("likedCount"));
        item.setTime(data.getLong("time"));
        item.setUser_id(data.getJSONObject("user").getLong("userId"));
        item.setJson(data.toString());
        return item;
    }

    public static ArrayList<BeanComment> run(long song_id){
        ArrayList<BeanComment> list = new ArrayList<>();

        String url = Settings.host + "/music/comments?id=" + song_id;
        Settings.log(url);
        String json = HTTPUtil.get(url);

        JSONObject data = new JSONObject(json);
        JSONArray hot = data.getJSONArray("hotComments");
        for(int index=0;index<hot.length();index++){
            BeanComment item = build(hot.getJSONObject(index));
            list.add(item);
        }
        comment_cnt = data.getLong("total");

        return list;
    }
    public static void main(String[] args) {
        ArrayList<BeanComment> testData = run(1338414110);
        System.out.println(comment_cnt);
    }
}
