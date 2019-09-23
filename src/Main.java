import top.kealine.netmusic.util.*;

public class Main {
    public static void main(String[] args) {
        String json = HTTPUtil.get("http://localhost:3000/v1/playlist?type=%E5%8F%A4%E9%A3%8E&offset=0&limit=35");
//        JSONObject object = new JSONObject(json);
//        JSONObject lrc = object.getJSONObject("lrc");
//        System.out.println(lrc.getString("lyric"));
        System.out.println(json);
    }
}
