import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class App {
   
    static Deposit dp = new Deposit();
    static Transfer tf = new Transfer();
    static Withdraw wd = new Withdraw();

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Connection con = null;
        try {
            Properties prop = new Properties();
            InputStream ip = new FileInputStream(new File("src//database.properties"));
            prop.load(ip);
            final String username = prop.getProperty("db.uname");
            final String password = prop.getProperty("db.password");
            final String url = prop.getProperty("db.url");
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            int c = 0;
            while (c != 3) {

                System.out.println("----------------Welcome to Bank -----------------");
                System.out.println("1.New User \n 2.Existing User \n 3.Exit");
                System.out.println("Enter Your Choice:- ");

                c = sc.nextInt();
                switch (c) {
                    case 1 -> newUser(sc, con);
                    case 2 -> existingUser(con, sc);
                    case 3 -> System.out.println("Thanks For Using....!!!");
                    default -> System.out.println("Enter Valid Choice!!!");

                }

            }
            sc.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void newUser(Scanner sc, Connection con) {

        System.out.println("Enter Your Name :- ");
        String name = sc.next();
        System.out.println("Enter 4 digit PIN :- ");
        int pin = sc.nextInt();
        System.out.println("Enter the Amount to Deposit:- ");
        double amount = sc.nextDouble();
        try {
            PreparedStatement ps = con.prepareStatement(Query.insertUser, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, pin);
            ps.setDouble(3, amount);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Acoount Has been Successfully Created !");
                // Retrieve the generated account number
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    long accountNumber = rs.getLong(1);
                    System.out.println("Your Account Number is: " + accountNumber);
                    System.out.println("Please Note your Account Number !!!");
                    System.out.println("Now you can make Transaction from existing User option!");
                } else {
                    System.out.println("Failed to retrieve Account Number.");
                }
            } else {
                System.out.println("Failed to Create Account!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void existingUser(Connection con, Scanner sc) throws SQLException {
        System.out.println("------------------- Welcome User-------------------");
        System.out.println("Enter Your account Number :- ");
        long accountNumber = sc.nextLong();
        System.out.println("Enter Your PIN :-");
        int pin = sc.nextInt();
        int c = 0;
        boolean result =ispinCorrect.isPinCorrect(con,accountNumber,pin);
        if (result) {

            while (c != 5) {
                System.out.println("--------------------------------------------------------");
                System.out.println();
                System.out.println("---------------  Select Your Transaction! ------------------");
                System.out
                        .println(
                                "1.Deposit Amount \n 2.Transfer Amount \n 3.Withdraw Amount \n 4.Check Balance \n 5.Exit");

                System.out.println("Enter Your Choice :- ");

                c = sc.nextInt();
                switch (c) {
                    case 1 -> dp.deposit(con, sc, accountNumber, pin);
                    case 2 -> tf.transfer(con, sc, accountNumber);
                    case 3 -> wd.withdraw(con, sc, accountNumber);
                    case 4 -> {
                        try {
                            PreparedStatement ps = con.prepareStatement(Query.balance,
                                    PreparedStatement.RETURN_GENERATED_KEYS);
                            ps.setLong(1, accountNumber);
                            ResultSet rs = ps.executeQuery();
                            if (rs.next()) {
                                double balance = rs.getDouble("Balance");
                                System.out.println("Your Balance is:- " + balance);
                            } else {
                                System.out.println("Failed to retrieve balance for account number: " + accountNumber);
                            }

                        } catch (Exception e) {
                            System.out.println("Failed to check Balance !!Please try agian Later !");
                        }
                    }
                    case 5 -> System.out.println("Thanks for Using Our Service!!!");
                    default -> System.out.println("Enter Valid Choice !");

                }
            }
        }else{
            System.out.println("Your Have Enter Wrong PIN!");
        }
    }
}
