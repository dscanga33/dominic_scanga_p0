package bankapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankSQL
{
    public static void addBank(double bal,String accountType, User u)
    {

        String sql = "INSERT INTO accounts VALUES (default,?,?,?) RETURNING *";
        Connection conn = JDBCConnection.getConnection();
            try {
            PreparedStatement ps = conn.prepareStatement(sql);

            //set values for all the placeholders: ?
            ps.setDouble(1, bal);
            ps.setString(2,accountType);
            ps.setInt(3,u.getAccountNum());

            ps.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
