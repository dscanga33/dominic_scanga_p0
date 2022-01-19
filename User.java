package bankapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    Connection conn = JDBCConnection.getConnection();
    private static final int MAX_ACCOUNTS = 3; //Do we need?
    public static int nextAccountNum = 1;
    GenericLinkedList<BankAccount> accounts = new GenericLinkedList<BankAccount>();
    private String username;
    private String password;
    private int accountNum;
    private int bankCount;
    private int active; //Needed?

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        UserSQL.addUser(username,password);
        this.accountNum = nextAccountNum;
        nextAccountNum++;
    }

    public int getMaxAccounts() {
        return MAX_ACCOUNTS;
    }

    public int getAccountNum()
    {
        return this.accountNum;
    }

    public String getUsername() {
        return this.username;
    }

    public String getUsernameSQL() {
        String sql = "select * from users";

        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccountNum(int accountNum)
    {
        this.accountNum = accountNum;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GenericLinkedList<BankAccount> getAccounts() {
        return this.accounts;
    }

    public int getActive() {
        return this.active;
    }

    public int getBankCount() {
        return this.bankCount;
    }

    public void setAccounts(GenericLinkedList<BankAccount> accounts) {
        this.accounts = accounts;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setBankCount(int bankCount) {
        this.bankCount = bankCount;
    }

    public static User createUser(Scanner s) {
        System.out.print("Enter the username for your new account : ");
        String username = s.nextLine();
        System.out.print("Enter the password for your account : ");
        String password = s.nextLine();
        System.out.println();
        return new User(username, password);
    }

    public String toString() {
        return "User (username: " + this.username + "; password: " + this.password + ")";
    }

    public void newBanking(Scanner s) {
        if (this.active + 1 < 3) {
            this.accounts.add(BankAccount.createAccount(s, this));
            this.bankCount++;
        }

    }

    public void viewBalance() {
        System.out.println("I got called");

        for(int i = 0; i < this.bankCount; ++i) {
            System.out.println(i);
            System.out.println(this.accounts.get(i).toString());
        }

    }
}
