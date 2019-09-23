package top.kealine.netmusic.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtil {
    private static final String jdbcUrl="jdbc:mysql://localhost:3306/netmusic?useUnicode=true&characterEncoding=UTF-8&verifyServerCertificate=false&useSSL=false&serverTimezone=CTT";
    private static final String dbUser="root";
    private static final String dbPwd="Keadin";
    static{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws java.sql.SQLException{
        return java.sql.DriverManager.getConnection(jdbcUrl, dbUser, dbPwd);
    }

    public static void main(String[] args) {
        Connection conn = null;
        String sql = "SELECT * FROM netmusic.system";
        try {
            conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1)+" "+rs.getString(2));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
