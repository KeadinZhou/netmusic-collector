package top.kealine.netmusic.datagetter;

import org.json.JSONObject;
import top.kealine.netmusic.Settings;
import top.kealine.netmusic.model.BeanUser;
import top.kealine.netmusic.util.HTTPUtil;

public class UserGetter {
    private static BeanUser build(JSONObject user){
        BeanUser data = new BeanUser();
        data.setJson(user.toString());
        user = user.getJSONObject("profile");
        data.setId(user.getLong("userId"));
        data.setNickname(user.getString("nickname"));
        data.setBirthday(user.getLong("birthday"));
        data.setCity(user.getLong("city"));
        data.setFolloweds(user.getLong("followeds"));
        data.setFollows(user.getLong("follows"));
        data.setGender(user.getInt("gender"));
        data.setProvince(user.getLong("province"));
        return data;
    }

    public static BeanUser run(long user_id){
        String url = Settings.host + "/user/detail?id=" + user_id;
        Settings.log(url);
        String json = HTTPUtil.get(url);

//        try {
//            Thread.sleep(300);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        return build(new JSONObject(json));
    }

    public static void main(String[] args) {
        BeanUser user = run(134549238);
        System.out.println(user);
    }
}
