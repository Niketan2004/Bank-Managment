import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Withdraw {
     public void withdraw(Connection con, Scanner sc, Long accountNumber) {
          System.out.println("Enter the amount to withdraw: ");
          Double amount = sc.nextDouble();
          System.out.println("Enter Your PIN: ");
          int pin = sc.nextInt();

          try {
               // Verify PIN
               if (!isPinCorrect(con, accountNumber, pin)) {
                    System.out.println("Incorrect PIN. Withdrawal failed.");
                    return;
               }
               // Prepare the UPDATE query to withdraw the amount
               PreparedStatement ps = con.prepareStatement(Query.sendQuery);
               ps.setDouble(1, amount);
               ps.setLong(2, accountNumber);

               // Execute the UPDATE query
               int rowsAffected = ps.executeUpdate();
               if (rowsAffected > 0) {
                    // Query executed successfully, retrieve the updated balance
                    double balance = getUpdatedBalance(con, accountNumber);

                    // Check if the PIN is correct and the withdrawal amount is valid
                    if (balance >= amount) {
                         System.out.println(
                                   "Amount of Rs. " + amount + "/- has been successfully withdrawn from your account!");
                         System.out.println("Your Available Balance is: " + balance);
                         con.commit();
                    } else {
                         System.out.println("Insufficient balance. Withdrawal failed.");
                         con.rollback();
                    }
               } else {
                    System.out.println("Failed to withdraw amount.");
                    con.rollback();
               }
          } catch (SQLException e) {
               System.out.println("Error: " + e.getMessage());
          }
     }

     private boolean isPinCorrect(Connection con, Long accountNumber, int pin) throws SQLException {

          try (PreparedStatement ps = con.prepareStatement(Query.pin)) {
               ps.setLong(1, accountNumber);
               ps.setInt(2, pin);
               try (ResultSet rs = ps.executeQuery()) {
                    return rs.next(); // If there is a row, PIN is correct
               }
          }
     }

     private double getUpdatedBalance(Connection con, Long accountNumber) throws SQLException {
          // Prepare the SELECT query to retrieve the updated balance

          try (PreparedStatement ps = con.prepareStatement(Query.balance)) {
               ps.setLong(1, accountNumber);

               // Execute the SELECT query
               try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                         return rs.getDouble("Balance");
                    } else {
                         throw new SQLException(
                                   "Failed to retrieve updated balance for account number: " + accountNumber);
                    }
               }
          }
     }
}
