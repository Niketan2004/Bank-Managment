import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ispinCorrect {
     public static  boolean isPinCorrect(Connection con, Long accountNumber, int pin){
          try (PreparedStatement ps = con.prepareStatement(Query.pin)) {
               ps.setLong(1, accountNumber);
               ps.setInt(2, pin);
               try (ResultSet rs = ps.executeQuery()) {
                    return rs.next(); // If there is a row, PIN is correct
               }
          } catch (Exception e) {
               return false;
          }
     }
}
