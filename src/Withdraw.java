import java.sql.Connection;
import java.sql.PreparedStatement;
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
               if (!ispinCorrect.isPinCorrect(con, accountNumber, pin)) {
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
                    double balance = Balance.getUpdatedBalance(con, accountNumber);

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
}
