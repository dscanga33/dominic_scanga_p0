package bankapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserSQL
{
    public static void addUser(String user,String password)
    {

        String sql = "INSERT INTO users VALUES (default,?,?) RETURNING *";
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            //set values for all the placeholders: ?
            ps.setString(1, user);
            ps.setString(2,password);

            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
