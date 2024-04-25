import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Transfer {
     public void transfer(Connection con, Scanner sc, Long accountNumber) {
          System.out.println("Enter the Account Number of Receiver: ");
          Long receiverAccount = sc.nextLong();
          System.out.println("Enter the amount to transfer: ");
          Double amount = sc.nextDouble();
          System.out.println("Enter Your PIN: ");
          int pin = sc.nextInt();
          try {
               con.setAutoCommit(false);
          } catch (SQLException e) {
               System.out.println(e.getMessage());
          }

          try {
               // Check if sender's PIN is correct
               if (!ispinCorrect.isPinCorrect(con, accountNumber, pin)) {
                    System.out.println("Incorrect PIN. Transfer failed.");
                    return;
               }

               // Check sender's balance
               double senderBalance = Balance.getUpdatedBalance(con, accountNumber);
               if (senderBalance < amount) {
                    System.out.println("Insufficient balance. Transfer failed!");
                    return;
               }

               // Update sender's balance (debit)
               PreparedStatement debit = con.prepareStatement(Query.sendQuery);
               debit.setDouble(1, amount);
               debit.setLong(2, accountNumber);
               int debitRowsAffected = debit.executeUpdate();

               // Update receiver's balance (credit)
               PreparedStatement credit = con.prepareStatement(Query.recieveQuery);
               credit.setDouble(1, amount);
               credit.setLong(2, receiverAccount);
               int creditRowsAffected = credit.executeUpdate();

               if (debitRowsAffected > 0 && creditRowsAffected > 0) {
                    con.commit();
                    System.out.println("Amount of Rs. " + amount + "/-. transferred successfully from account number "
                              + accountNumber + " to "
                              + receiverAccount);
               } else {
                    con.rollback();
                    System.out.println("Failed to transfer amount!");
               }
          } catch (SQLException e) {
               System.out.println("Error: " + e.getMessage());
               try {
                    con.rollback();
               } catch (SQLException ex) {
                    System.out.println("Rollback failed: " + ex.getMessage());
               }
          }
     }
}
