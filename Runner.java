package bankapp;

import bankapp.SQL.UserSQL;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Runner {

    public static Scanner s = new Scanner(System.in);
    public static GenericLinkedList<User> users = new GenericLinkedList<User>();
    public static User active;


    public static void main(String[] args) {
        run();
        System.out.println("Goodbye!");
    }








    /**
     * Initializes the database
     */
    public static void initialize()
    {
        String sql = "drop table if exists users cascade;\n" +
                "create table if not exists users(\n" +
                "\tuser_id serial primary key,\n" +
                "\tusername varchar(50) not null,\n" +
                "\tpassword varchar(50) not null\n" +
                ");\n" +
                "drop table if exists accounts;\n" +
                "create table if not exists accounts (\n" +
                "\taccount_id serial primary key,\n" +
                "\tbalance numeric(10,2),\n" +
                "\tuser_id int references users\n" +
                ");";
        Connection conn = JDBCConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            users.add(UserSQL.firstUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the program
     */
    public static void run() {
        System.out.println("Welcome to the application, let's create an account to get started.");
        initialize();
        users.add(User.createUser());
        active = users.get(0);
        boolean exit = false;

        do {
            int choice = loggedInMenu(active);
            switch(choice) {
                case 1:
                    active.newBank(); //Create new account
                    break;
                case 2:
                    exit = logOutMenu(); //Log out
                    break;
                case 3:
                    exit = true; //Close program
                    break;
                case 4:
                    int bankChoice = -1;
                    do {

                        int selection = active.selectBank();
                        if (selection != -1) {
                            BankAccount b = active.getBankAccounts().get(selection);
                            bankChoice = bankMenu(b);
                            switch (bankChoice) {
                                case (1): //deposit
                                    b.deposit();
                                    break;
                                case (2): //withdraw
                                    b.withdraw();
                                    break;
                                case (3): //switch accounts
                                    break;
                                case (4): //new account
                                    active.newBank();
                                    break;
                                case (5): //log out
                                    exit=logOutMenu();
                                    break;

                            }
                        }
                    }while (bankChoice == 3); //If switch account start over
                    break;
            }

        } while(!exit);

    }

    /**
     * Method used to log in to a new account
     * @return returns the account that was logged in to
     */
    public static User login() {
        s = new Scanner(System.in);
        System.out.println("Username: ");
        String user = s.nextLine();
        for (int i =0;i<users.getSize();i++)
        {
            if(users.get(i).getUsername().equals(user))
            {
                User found = users.get(i);
                System.out.println("Password: ");
                String password = s.nextLine();
                if(found.getPassword().equals(password))
                {
                    return found;
                }
            }
        }
        System.out.println("No such account exists");
        return null;
    }

    public static int loggedInMenu(User active) {
        s = new Scanner(System.in);
        int choice = 0;
        System.out.println("\nAvailable actions:");
        //performed if the user has no bank accounts
        if (active.getBankAccounts().getSize() == 0) {
            do {
                System.out.println("Enter 1 to create a new bank account.");
                System.out.println("Enter 2 to log out");
                System.out.println("Enter 3 to exit the program");

                try {
                    choice = s.nextInt();
                } catch (NullPointerException | InputMismatchException e) {
                    choice = 0;
                    System.out.println("Invalid input, try again");
                    s = new Scanner(System.in);
                }
            } while(choice < 1 || choice > 3);

            return choice;
        }//performed when first logged in
        else {
            do {
                System.out.println("\nEnter 1 to create a new bank account");
                System.out.println("Enter 2 to log out");
                System.out.println("Enter 3 to exit the program");
                System.out.println("Enter 4 to access a bank account");

                try {
                    choice = s.nextInt();
                } catch (InputMismatchException | NumberFormatException e) {
                    choice = 0;
                    System.out.println("Invalid input, try again");
                    s = new Scanner(System.in);
                }
            } while(choice < 1 || choice > 4);

            return choice;
        }
    }

    public static boolean logOutMenu()
    {


            int choice = 0;
            System.out.println("Successfully logged out!\n");
            do {

                do {

                    System.out.println("Enter 1 to log in");
                    System.out.println("Enter 2 to create a new account");
                    System.out.println("Enter 3 to exit the program");

                    try {
                        choice = s.nextInt();
                    } catch (InputMismatchException | NumberFormatException e) {
                        choice = 0;
                        System.out.println("Invalid input, try again");
                        s = new Scanner(System.in);
                    }
                } while (choice <= 0 || choice > 2);
                switch (choice) {
                    case (1):
                        active = login();
                        break;
                    case (2):
                        User newUser = User.createUser();
                        users.add(newUser);
                        active = newUser;
                        break;
                    case (3):
                        return true;
                }
            }while (active == null);
            return false;

    }

    public static int bankMenu(BankAccount bankChoice)
    {
        int choice =-1;
        do {
            System.out.println("Enter 1 to make a deposit");
            System.out.println("Enter 2 to make a withdrawal");
            System.out.println("Enter 3 to switch bank accounts");
            System.out.println("Enter 4 to create a new bank account");
            System.out.println("Enter 5 to log out");

            try {
                choice = s.nextInt();
            } catch (NullPointerException | InputMismatchException e) {
                choice = 0;
                System.out.println("Invalid input, try again \n");
                s = new Scanner(System.in);
            }
        } while(choice < 1 || choice > 5);

        return choice;
    }
}
