import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                double balance = getUpdatedBalance(con, accountNumber);
                System.out.println("Amount of Rs. " + amount + "/- has been successfully deposited in your account! Your current balance is: " + balance);
            } else {
                System.out.println("Failed to deposit amount of Rs. " + amount + "/- to your account!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private double getUpdatedBalance(Connection con, Long accountNumber) throws SQLException {
        // Prepare the SELECT query to retrieve the updated balance
       // String selectQuery = "SELECT Balance FROM BankUsers WHERE AccountNumber = ?";
        PreparedStatement ps = con.prepareStatement(Query.balance);
        ps.setLong(1, accountNumber);

        // Execute the SELECT query
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getDouble("Balance");
        } else {
            throw new SQLException("Failed to retrieve updated balance for account number: " + accountNumber);
        }
    }
}
