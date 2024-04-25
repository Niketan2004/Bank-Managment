import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Deposit {
    public void deposit(Connection con, Scanner sc, Long accountNumber, int pin) {
        System.out.println("Enter Amount to Deposit: ");
        double amount = sc.nextDouble();
        try {
            // Prepare the UPDATE query to deposit the amount
            PreparedStatement ps = con.prepareStatement(Query.recieveQuery);
            ps.setDouble(1, amount);
            ps.setLong(2, accountNumber);

            // Execute the UPDATE query
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Query executed successfully, update the balance
                double balance = Balance.getUpdatedBalance(con, accountNumber);
                System.out.println("Amount of Rs. " + amount + "/- has been successfully deposited in your account! Your current balance is: " + balance);
            } else {
                System.out.println("Failed to deposit amount of Rs. " + amount + "/- to your account!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
