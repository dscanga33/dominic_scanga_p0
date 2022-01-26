package bankapp;

import bankapp.SQL.UserSQL;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;


public class User {
    static Connection conn = JDBCConnection.getConnection();
    static Scanner s = new Scanner(System.in);

    private String username;
    private String password;
    private int accountNum;
    private GenericLinkedList<BankAccount> bankAccounts = new GenericLinkedList<BankAccount>();

    /**
     * Constructor that inserts a new user with the passed in values to the db
     * constructor then sets values equal to those found in the db
     * @param username username to instantiate class with
     * @param password password to instantiate class with
     */
    public User(String username, String password) {
        User u = UserSQL.addUser(username,password);
        this.setAccountNum(u.getAccountNum());
        this.setUsername(u.getUsername());
        this.setPassword(u.getPassword());
    }

    /**
     * empty constructor
     */
    public User()
    {

    }

    /**
     * returns linked list of bank accounts
     * @return bank accounts
     */
    public GenericLinkedList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    /**
     * sets the bank account linked list to a new linked list of bank accounts
     * @param bankAccounts new list of accounts
     */
    public void setBankAccounts(GenericLinkedList<BankAccount> bankAccounts)
    {
        this.bankAccounts = bankAccounts;
    }

    /**
     * returns account number
     * @return account number
     */
    public int getAccountNum()
    {
        return this.accountNum;
    }

    /**
     * returns username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * sets username to new username
     * @param username new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * sets account number to new account number
     * @param accountNum new account number
     */
    public void setAccountNum(int accountNum)
    {
        this.accountNum = accountNum;
    }

    /**
     * returns the password
     * @return returns password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * sets the objects password to the new password
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Prompts the user to input the username and password for the new account and inserts it into the db
     * @return returns the new account
     */
    public static User createUser() {
        s = new Scanner(System.in);
        System.out.print("Enter the username for your new account : ");
        String username = s.nextLine();
        System.out.print("Enter the password for your account : ");
        String password = s.nextLine();
        System.out.println();
        return new User(username, password);
    }

    /**
     * Creates a new bank account and inserts it into the linked list of bank accounts
     * @return returns the new bank account
     */
    public BankAccount newBank()
    {
        BankAccount newAccount = BankAccount.createAccount(this);
        bankAccounts.add(newAccount);
        return newAccount;
    }

    /**
     * Prints a list of all banks associated with the user and requests the user to select the account they would like
     * to perform operations on
     * @return returns the index of the requested BankAccount
     */
    public int selectBank()
    {
        for (int i = 0; i<bankAccounts.getSize();i++)
        {
            System.out.print("Enter "+(i+1)+" to select this account: ");
            System.out.println(bankAccounts.get(i));
        }
        System.out.println("Selection: ");
        try {
            int response = s.nextInt();
            if(response <= bankAccounts.getSize() && response > 0)
            {
                return response-1;
            }
            else throw new InputMismatchException();
        } catch (InputMismatchException e)
        {
            s = new Scanner(System.in);
            System.out.println("Invalid entry");
            return -1;
        }
    }

    /**
     * Overrides Object toString
     * @return Returns a formatted string representation of User
     */
    public String toString() {
        return "User (username: " + this.username + "; password: " + this.password + ")";
    }
}
