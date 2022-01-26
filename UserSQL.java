package bankapp.SQL;

import bankapp.JDBCConnection;
import bankapp.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSQL
{
    /**
     * Adds user to the db
     * @param user username
     * @param password password
     * @return returns the user that was inserted into the db
     */
    public static User addUser(String user, String password)
    {

        String sql = "INSERT INTO users VALUES (default,?,?) RETURNING *";
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            //set values for all the placeholders: ?
            ps.setString(1, user);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();
            return buildUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a user from the new user inserted into the db in addUser
     * @param rs ResultSet from addUser
     * @return returns a created user
     * @throws SQLException in case of SQL failure an empty User is returned
     */
    private static User buildUser(ResultSet rs) throws SQLException {
        User u = new User();
        if(rs.next()) {
            u.setAccountNum(rs.getInt("user_id"));
            u.setUsername(rs.getString("username"));
            u.setPassword((rs.getString("password")));
        }
        return u;
    }

    public static User firstUser()
    {

        String sql = "INSERT INTO users VALUES (default,?,?) RETURNING *";
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            //set values for all the placeholders: ?
            ps.setString(1, "user0");
            ps.setString(2,"pass0");

            ResultSet rs = ps.executeQuery();
            return buildUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
