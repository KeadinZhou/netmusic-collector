package top.kealine.netmusic.model;

import java.util.ArrayList;

public class BeanSong {
    private long id;
    private String name;
    private ArrayList<BeanSinger> singers;
    private String lrc;
    private long comments_cnt;
    private ArrayList<BeanComment> comments;
    private String json;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BeanSinger> getSingers() {
        return singers;
    }

    public void setSingers(ArrayList<BeanSinger> singers) {
        this.singers = singers;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public long getComments_cnt() {
        return comments_cnt;
    }

    public void setComments_cnt(long comments_cnt) {
        this.comments_cnt = comments_cnt;
    }

    public ArrayList<BeanComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<BeanComment> comments) {
        this.comments = comments;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
