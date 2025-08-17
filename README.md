Java Console ATM Simulation
A comprehensive, console-based ATM simulation built with Java. This project was developed for the MIS 210: Introduction to Computer Programming course and demonstrates core Java programming concepts, including Object-Oriented Programming (OOP), file I/O for data persistence, and interactive user menu systems.

🚀 About The Project
This application simulates the complete functionality of an Automated Teller Machine (ATM) through a command-line interface. Users can create multiple bank accounts, each secured by an account number and a PIN. All account information, including balance and transaction history, is saved to local text files, making the data persistent across sessions.

The project is built entirely in Java and serves as a practical example of applying fundamental programming principles to create a functional and interactive application.

✨ Features
Multi-Account System: Create and access multiple unique user accounts.

Persistent Data Storage: Each account's details (name, balance, PIN, transaction history) are saved to a separate .txt file.

Secure Authentication: Users must log in with a valid account number and a 3-attempt PIN verification.

Core Banking Operations:

💵 Withdraw: Withdraw funds from an account.

💰 Deposit: Deposit funds into an account.

📊 Check Balance: View the current account balance.

📜 Mini Statement: View the last 5 transactions.

🔒 Change PIN: Securely update the account PIN.

Mobile Financial Services (MFS):

Simulate transactions with services like Bkash and Nagad.

Includes a demo OTP verification step for added security.

🛠️ Technologies Used
Java: The core programming language used for the entire application.

Core Java Concepts:

Object-Oriented Programming (OOP) with inner classes

File I/O (java.io.File, java.io.PrintWriter, java.util.Scanner)

Data Structures (java.util.ArrayList)

Date and Time API (java.time.LocalDateTime)

Loops, Arrays, and Conditional Statements

⚙️ How To Run
Prerequisites:

Java Development Kit (JDK) installed and configured.

Clone the repository:

git clone https://github.com/your-username/java-atm-project.git 

Navigate to the project directory:

cd java-atm-project

Compile the Java file:

javac ATM.java

Run the application:

java ATM

Follow the on-screen prompts to create an account or log in. Account data will be saved as .txt files in the same directory.
