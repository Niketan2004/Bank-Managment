public class Query {
     // SQL query to insert a new user
     static final String insertUser = "INSERT INTO BankUsers (AccountHolderName, Pin, Balance) VALUES (?, ?, ?)";
     // Query to fetch Balance
     static final String balance = "SELECT balance from BankUsers where AccountNumber = ?";
     // Query for the user to send monery
     static final String sendQuery = " Update BankUsers Set balance = balance- ? Where AccountNumber = ? ";
     // Query to recieve money from User
     static final String recieveQuery = " Update BankUsers Set balance = balance + ? Where AccountNumber = ? ";
     // Query to verify PIN
     static final String pin = "SELECT Pin FROM BankUsers WHERE AccountNumber = ? AND Pin = ?";

}
