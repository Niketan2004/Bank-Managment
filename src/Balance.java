import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Balance {
     static double getUpdatedBalance(Connection con, Long accountNumber) throws SQLException {

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
