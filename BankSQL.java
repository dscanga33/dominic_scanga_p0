package bankapp.SQL;

import bankapp.BankAccount;
import bankapp.JDBCConnection;
import bankapp.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BankSQL
{
    public static BankAccount addBank(double bal, User u)
    {

        String sql = "INSERT INTO accounts VALUES (default,?,?) RETURNING *";
        Connection conn = JDBCConnection.getConnection();
            try {
            PreparedStatement ps = conn.prepareStatement(sql);

            //set values for all the placeholders: ?
            ps.setDouble(1, bal);
            ps.setInt(2,u.getAccountNum());

                ResultSet rs = ps.executeQuery();
                return buildBank(rs);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    public static BankAccount update(BankAccount b)
    {
        String sql = "UPDATE accounts SET balance = ? WHERE  account_id = ? RETURNING *";
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            //set values for all the placeholders: ?
            ps.setDouble(1, b.getBalance());
            ps.setInt(2,b.getAccountNum());

            ResultSet rs = ps.executeQuery();
            return buildBank(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


        private static BankAccount buildBank(ResultSet rs) throws SQLException {
        BankAccount b = new BankAccount();
        if(rs.next()) {
            b.setAccountNum(rs.getInt("account_id"));
            b.setBalance(rs.getDouble("balance"));
            b.setUserNum((rs.getInt("user_id")));
        }
        return b;
    }
}
