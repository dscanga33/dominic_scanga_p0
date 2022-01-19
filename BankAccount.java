

package bankapp;

import java.sql.Connection;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankAccount {
    Connection conn = JDBCConnection.getConnection();
    private static int totalAccounts = 0;// Do we really need this?
    private String accountType;
    private double balance;
    private int accountNum;

    public BankAccount(String accountType, double balance) {
        this.accountType = accountType;
        this.balance = balance;
        this.accountNum = totalAccounts+1;
    }

    public static BankAccount createAccount(Scanner s, User u) {
        System.out.print("Is this a savings, checking, or combination account?: ");
        String accountType = s.next();
        System.out.println("How much would you like to initially put into your account?: ");

        try {
            double balance = Double.parseDouble(s.next());
            System.out.println();
            BankSQL.addBank(balance,accountType,u);
            return new BankAccount(accountType, balance);
        } catch (NullPointerException | InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid balance, account created with a starting balance of 0.");
            System.out.println();
            BankSQL.addBank(0,accountType,u);
            return new BankAccount(accountType, 0.0D);
        }
    }

    public String getAccountType()
    {
        return this.accountType;
    }

    public double getBalance() {
        return this.balance;
    }

    public String viewBalance() {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return f.format(this.balance);
    }

    public void deposit(Scanner s) {
        try {
            System.out.println("How much would you like to deposit?: ");
            double deposit = s.nextDouble();
            if (deposit <= 0.0D) {
                System.out.println("Invalid amount, deposit attempt failed");
            } else {
                this.balance += deposit;
            }
        } catch (NullPointerException | InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid input, deposit attempt failed");
        }

    }

    public void withdraw(Scanner s) {
        try {
            System.out.println("How much would you like to withdraw?");
            double withdraw = s.nextDouble();
            if (withdraw > this.balance) {
                System.out.println("Unable to withdraw that amount, withdrawal attempt failed");
            } else if (withdraw <= 0.0D) {
                System.out.println("Invalid amount, withdrawal attempt failed");
            } else {
                this.balance -= withdraw;
            }
        } catch (NullPointerException | NumberFormatException e) {
            System.out.println("Invalid input, withdrawal attempt failed");
        }

    }

    public int getAccountNum() {
        return this.accountNum;
    }

    public void setAccountNum(int accountNum) {
        this.accountNum = accountNum;
    }

    public String toString() {
        return "BankAccount (accountType: " + this.accountType + "; balance: " + this.viewBalance() + "; accountNum: " + this.accountNum;
    }
}
