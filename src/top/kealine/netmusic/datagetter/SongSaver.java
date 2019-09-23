package top.kealine.netmusic.datagetter;

import top.kealine.netmusic.Settings;
import top.kealine.netmusic.model.BeanComment;
import top.kealine.netmusic.model.BeanSinger;
import top.kealine.netmusic.model.BeanSong;
import top.kealine.netmusic.model.BeanUser;
import top.kealine.netmusic.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SongSaver {
    private static final String GET_SONG_LIST_SQL = "SELECT DISTINCT song_id FROM songs_belong";
    private static final String CHECK_SONG_SQL = "SELECT song_id FROM song WHERE song_id=?";
    private static final String CHECK_SINGER_SQL = "SELECT singer_id FROM singer WHERE singer_id=?";
    private static final String CHECK_USER_SQL = "SELECT user_id FROM user WHERE user_id=?";
    private static final String CHECK_COMMENT_SQL = "SELECT comment_id FROM comment WHERE comment_id=?";
    private static final String SAVE_SONG_SQL = "INSERT INTO song(song_id,song_name,song_lrc,comments_cnt,song_url,publish_time,json) VALUES (?,?,?,?,?,?,?)";
    private static final String SAVE_SINGER_SQL = "INSERT INTO singer(singer_id,singer_name) VALUES (?,?)";
    private static final String SAVE_COMMENT_SQL = "INSERT INTO comment(comment_id,content,like_cnt,time,user_id,song_id,json) VALUES (?,?,?,?,?,?,?)";
    private static final String SAVE_USER_SQL = "INSERT INTO user(user_id,nickname,birthday,city,followeds,follows,gender,province,json) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SAVE_SINGERS_SING_SQL = "INSERT INTO singers_sing(song_id,singer_id) VALUES (?,?)";
    private static PreparedStatement psSongChecker = null;
    private static PreparedStatement psSingerChecker = null;
    private static PreparedStatement psUserChecker = null;
    private static PreparedStatement psCommentChecker = null;
    private static PreparedStatement psSongSaver = null;
    private static PreparedStatement psSingerSaver = null;
    private static PreparedStatement psCommentSaver = null;
    private static PreparedStatement psUserSaver = null;
    private static PreparedStatement psSingerSingSaver = null;
    private static void saveUser(long user_id) throws SQLException {
        psUserChecker.setLong(1,user_id);
        ResultSet checker = psUserChecker.executeQuery();
        if(checker.next()) return;

        BeanUser user = UserGetter.run(user_id);
        if(user.getId() == 0) return;
        psUserSaver.setLong(1,user_id);
        psUserSaver.setString(2,user.getNickname());
        psUserSaver.setLong(3,user.getBirthday());
        psUserSaver.setLong(4,user.getCity());
        psUserSaver.setLong(5,user.getFolloweds());
        psUserSaver.setLong(6,user.getFollows());
        psUserSaver.setLong(7,user.getGender());
        psUserSaver.setLong(8,user.getProvince());
        psUserSaver.setString(9,user.getJson());
        psUserSaver.execute();
    }
    private static void saveComment(long song_id,BeanComment comment) throws SQLException{
        psCommentSaver.setLong(1,comment.getId());
        psCommentSaver.setString(2,comment.getContent());
        psCommentSaver.setLong(3,comment.getLike());
        psCommentSaver.setLong(4,comment.getTime());
        psCommentSaver.setLong(5,comment.getUser_id());
        psCommentSaver.setLong(6,song_id);
        psCommentSaver.setString(7,comment.getJson());
        // saveUser(comment.getUser_id());

        psCommentChecker.setLong(1,comment.getId());
        ResultSet checker = psCommentChecker.executeQuery();
        if (checker.next()) return;
        psCommentSaver.execute();
    }
    private static void saveSinger(BeanSinger singer) throws SQLException {
        psSingerChecker.setLong(1,singer.getId());
        ResultSet checker = psSingerChecker.executeQuery();
        if(checker.next()) return;
        psSingerSaver.setLong(1,singer.getId());
        psSingerSaver.setString(2,singer.getName());
        psSingerSaver.execute();
    }

    private static void saveSingerSing(long song_id,long singer_id) throws SQLException {
        psSingerSingSaver.setLong(1,song_id);
        psSingerSingSaver.setLong(2,singer_id);
        psSingerSingSaver.execute();
    }

    private static void saveSong(BeanSong song) throws SQLException {
        psSongSaver.setLong(1,song.getId());
        psSongSaver.setString(2,song.getName());
        psSongSaver.setString(3,song.getLrc());
        psSongSaver.setLong(4,song.getComments_cnt());
        psSongSaver.setString(5,song.getUrl());
        psSongSaver.setLong(6,song.getPublish_time());
        psSongSaver.setString(7,song.getJson());

        //Singers
        ArrayList<BeanSinger> singers = song.getSingers();
        for (BeanSinger singer : singers) {
            saveSinger(singer);
            saveSingerSing(song.getId(),singer.getId());
        }

        ArrayList<BeanComment> comments = song.getComments();
        for (BeanComment comment : comments) {
            saveComment(song.getId(), comment);
        }
        psSongSaver.execute();
    }
    public static void run(){
        Connection conn = null;

        try{
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(GET_SONG_LIST_SQL);
            psSongChecker = conn.prepareStatement(CHECK_SONG_SQL);
            psSingerChecker = conn.prepareStatement(CHECK_SINGER_SQL);
            psUserChecker = conn.prepareStatement(CHECK_USER_SQL);
            psCommentChecker = conn.prepareStatement(CHECK_COMMENT_SQL);
            psSongSaver = conn.prepareStatement(SAVE_SONG_SQL);
            psSingerSaver = conn.prepareStatement(SAVE_SINGER_SQL);
            psCommentSaver = conn.prepareStatement(SAVE_COMMENT_SQL);
            psUserSaver = conn.prepareStatement(SAVE_USER_SQL);
            psSingerSingSaver = conn.prepareStatement(SAVE_SINGERS_SING_SQL);
            ResultSet rs = ps.executeQuery();
            int cnt = 0;
            while(rs.next()){
                cnt++;
                long id = rs.getLong(1);

                //Continue if had yet.
                psSongChecker.setLong(1,id);
                ResultSet checker = psSongChecker.executeQuery();
                if(checker.next()) continue;

                BeanSong song = SongGetter.run(id);
                if(song.getId() == 0) continue;
                saveSong(song);
                // if(cnt>=10) break; //Limit
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        while(true){
            try{
                run();
            }catch (Exception e){
                try {
                    Settings.eLog(e);
                } catch (Exception ee){
                    e.printStackTrace();
                    ee.printStackTrace();
                }
            }
        }
    }
}
