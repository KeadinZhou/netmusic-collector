package top.kealine.netmusic.datagetter;

import org.json.JSONArray;
import org.json.JSONObject;
import top.kealine.netmusic.model.BeanSinger;

import java.util.ArrayList;

public class SinggerGetter {
    public static BeanSinger build(JSONObject data){
        BeanSinger item = new BeanSinger();
        item.setId(data.getLong("id"));
        item.setName(data.getString("name"));
        return item;
    }
    public static ArrayList<BeanSinger> run(JSONArray data){
        ArrayList<BeanSinger> list = new ArrayList<>();

        for(int index=0;index<data.length();index++){
            list.add(build(data.getJSONObject(index)));
        }

        return list;
    }
    public static void main(String[] args) {
        ArrayList<BeanSinger> testData=run(new JSONArray("[{id: 29999237, name: \"丫蛋蛋\", tns: [], alias: []}, {id: 12275389, name: \"沈虫虫\", tns: [], alias: []}]"));
        System.out.println(testData);
    }
}
