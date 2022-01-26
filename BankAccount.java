

package bankapp;

import bankapp.SQL.BankSQL;

import java.sql.Connection;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankAccount {
    Connection conn = JDBCConnection.getConnection();
    public static Scanner s = new Scanner(System.in);
    private double balance;
    private int accountNum;
    private int userNum;

    public BankAccount()
    {

    }
    public BankAccount(double balance,User u) {
        this.balance = balance;
        this.userNum = u.getAccountNum();
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public static BankAccount createAccount(User u) {
        System.out.println("How much would you like to initially put into your account?: ");

        try {
            double balance = Double.parseDouble(s.next());
            if(balance < 0)
            {
                throw new NumberFormatException();
            }
            System.out.println();
            return BankSQL.addBank(balance,u);
        } catch (NullPointerException | InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid balance, account created with a starting balance of 0.");
            System.out.println();
            return BankSQL.addBank(0,u);
        }
    }


    public double getBalance() {
        return this.balance;
    }
    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String viewBalance() {
        NumberFormat f = NumberFormat.getCurrencyInstance();
        return f.format(this.balance);
    }

    public void deposit() {
        try {
            System.out.println("How much would you like to deposit?: ");
            double deposit = s.nextDouble();
            if (deposit <= 0.0D) {
                System.out.println("Invalid amount, deposit attempt failed");
            } else {
                this.balance += deposit;
                BankSQL.update(this);
            }
        } catch (NullPointerException | InputMismatchException | NumberFormatException e) {
            System.out.println("Invalid input, deposit attempt failed");
        }

    }

    public void withdraw() {
        try {
            System.out.println("How much would you like to withdraw?");
            double withdraw = s.nextDouble();
            if (withdraw > this.balance) {
                System.out.println("Unable to withdraw that amount, withdrawal attempt failed");
            } else if (withdraw <= 0.0D) {
                System.out.println("Invalid amount, withdrawal attempt failed");
            } else {
                this.balance -= withdraw;
                BankSQL.update(this);
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
        return "Account number: "+this.accountNum+" | Account balance: "+this.viewBalance();
    }
}
