package bankapp;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Runner {
    public static final int MAX_USERS = 10;
    public static int numUsers = 1;//?
    static Connection conn = JDBCConnection.getConnection();

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        run(s);
        System.out.println("Goodbye!");
    }

    public static void initialize()
    {
        String sql = "drop table if exists users cascade;\n" +
                "create table if not exists users(\n" +
                "\tuser_id serial primary key,\n" +
                "\tusername varchar(50) unique not null,\n" +
                "\tpassword varchar(50) not null\n" +
                ");\n" +
                "drop table if exists accounts;\n" +
                "create table if not exists accounts (\n" +
                "\taccount_id serial primary key,\n" +
                "\tbalance numeric(10,2),\n" +
                "\taccount_type varchar(20)\n" +
                "\t,user_id int references users\n" +
                ");";
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void run(Scanner s) {
        GenericLinkedList<User> users = new GenericLinkedList<User>();
        initialize();
        users.add(User.createUser(s));
        boolean loggedIn = true;
        User active = users.get(0);
        boolean exit = false;

        do {
            int choice = loggedInMenu(s, active);
            switch(choice) {
                case 1:
                    active.newBanking(s);
                    break;
                case 2:
                    logOutMenu(s);
                    break;
                case 3:
                    exit = true;
                case 4:
                default:
                    break;
                case 5:
                    active.getAccounts().get(active.getActive()).deposit(s);
                    break;
                case 6:
                    active.getAccounts().get(active.getActive()).withdraw(s);
                    break;
                case 7:
                    active.viewBalance();
            }
        } while(!exit);

    }

    public static User login(Scanner s) {
        return null;
    }

    public static BankAccount chooseAccount() {
        return null;
    }

    public static int loggedInMenu(Scanner s, User active) {
        int MAX_OPTIONS_NO_ACCOUNT = 3;
        int MAX_OPTIONS_ACCOUNT = 7;
        int choice = 0;
        System.out.println("Available actions:");
        if (active.getAccounts().getSize() == 0) {
            do {
                System.out.println("Enter 1 to create a new bank account.");
                System.out.println("Enter 2 to log out");
                System.out.println("Enter 3 to exit the program");

                try {
                    choice = s.nextInt();
                    System.out.println(choice);
                } catch (NullPointerException | NumberFormatException var6) {
                    choice = 0;
                    System.out.println("Error");
                }
            } while(choice < 1 || choice > 3);

            return choice;
        } else {
            do {
                System.out.println("Enter 1 to create a new bank account");
                System.out.println("Enter 2 to log out");
                System.out.println("Enter 3 to exit the program");
                System.out.println("Enter 4 to select a different active account");
                System.out.println("Enter 5 to deposit to your active account");
                System.out.println("Enter 6 to withdraw from your active account");
                System.out.println("Enter 7 to view the balance in your active account");

                try {
                    choice = s.nextInt();
                } catch (NullPointerException | NumberFormatException var7) {
                    choice = 0;
                }
            } while(choice < 1 || choice > 7);

            return choice;
        }
    }

    public static void logOutMenu(Scanner s) {
    }
}
