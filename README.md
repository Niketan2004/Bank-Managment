# 🏦 Banking Management Project

## Overview

The Banking Management project is a Java application that simulates basic banking operations such as account creation, deposit, withdrawal, and fund transfer. It provides a command-line interface (CLI) for users to interact with the banking system.

## Features

- **New User Registration**: Allows new users to create bank accounts with a unique account number, name, and PIN.
- **Deposit**: Enables users to deposit funds into their accounts.
- **Withdrawal**: Allows users to withdraw funds from their accounts, provided they have sufficient balance and enter the correct PIN.
- **Fund Transfer**: Facilitates transfer of funds between two existing accounts within the bank.
- **PIN Verification**: Ensures security by verifying the PIN entered by the user before performing transactions.
- **Balance Inquiry**: Allows users to check their account balances.

## Technologies Used

- **Java**: The core programming language used for developing the application logic.
- **MySQL**: The relational database management system used for storing user account information and transaction records.

## Project Structure

- **App.java**: Main class containing the application entry point and user interaction logic.
- **Deposit.java**: Class handling the deposit functionality.
- **Withdraw.java**: Class handling the withdrawal functionality.
- **Transfer.java**: Class handling the fund transfer functionality.
- **Query.java**: Class containing SQL queries used by the application.
- **ispinCorrect.java**: Class that checks if the PIN entered by user is Correct.
- **Balance.java**: Class that retrieves the balance from user account.

## Database Schema

```sql
CREATE TABLE BankUsers (
    AccountNumber BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    AccountHolderName VARCHAR(255) NOT NULL,
    Pin VARCHAR(4) NOT NULL,
    Balance DECIMAL(15,2) NOT NULL
);

```

## Usage

1. **Clone the Repository:** Clone this repository to your local machine.
2. **Set Up Database:** Execute the provided SQL script to create the necessary database table.
3. **Compile and Run:** Compile the Java files and run the `App.java` file to start the application.

## Future Enhancements

- Implement additional security measures such as two-factor authentication.
- Enhance error handling and user input validation.
- Add support for internationalization (multiple languages).
