import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ATM {

    // BankAccount is now an inner class, simplifying the overall structure.
    static class BankAccount {
        private String accountNumber;
        private String accountHolderName;
        private double balance;
        private int pin;
        private List<String> transactions;

        public BankAccount(String accountNumber, String accountHolderName, double initialBalance, int pin) {
            this.accountNumber = accountNumber;
            this.accountHolderName = accountHolderName;
            this.balance = (initialBalance >= 0) ? initialBalance : 0;
            this.pin = pin;
            this.transactions = new ArrayList<>();
            if (initialBalance > 0) {
                addTransaction("Initial Deposit", initialBalance);
            }
        }
        
        // Overloaded constructor for loading from file
        public BankAccount(String accountNumber, String accountHolderName, double balance, int pin, List<String> transactions) {
            this.accountNumber = accountNumber;
            this.accountHolderName = accountHolderName;
            this.balance = balance;
            this.pin = pin;
            this.transactions = transactions;
        }

        private void addTransaction(String type, double amount) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String transactionRecord = String.format("%s | %s: $%.2f", dtf.format(now), type, amount);
            transactions.add(transactionRecord);
        }

        public boolean validatePin(int pinAttempt) {
            return this.pin == pinAttempt;
        }

        public void setPin(int newPin) {
            this.pin = newPin;
        }

        public String getAccountNumber() { return this.accountNumber; }
        public String getAccountHolderName() { return this.accountHolderName; }
        public int getPin() { return this.pin; }
        public double getBalance() { return this.balance; }
        public List<String> getTransactions() { return this.transactions; }

        public void deposit(double amount, String source) {
            if (amount > 0) {
                this.balance += amount;
                System.out.println("Successfully deposited: $" + amount);
                addTransaction(source, amount);
            } else {
                System.out.println("Deposit amount must be positive.");
            }
        }

        public void withdraw(double amount, String source) {
            if (amount > 0 && amount <= this.balance) {
                this.balance -= amount;
                System.out.println("Successfully withdrew: $" + amount);
                addTransaction(source, -amount);
            } else if (amount > this.balance) {
                System.out.println("Insufficient funds. Your balance is only $" + this.balance);
            } else {
                System.out.println("Withdrawal amount must be positive.");
            }
        }

        public void displayMiniStatement() {
            System.out.println("\n--- Mini Statement for Account: " + this.accountNumber + " ---");
            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                int start = Math.max(0, transactions.size() - 5);
                for (int i = start; i < transactions.size(); i++) {
                    System.out.println(transactions.get(i));
                }
            }
            System.out.println("----------------------------------------");
            System.out.printf("Current Balance: $%.2f\n", this.balance);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Welcome to Priyo's ATM Machine ---");

        BankAccount currentAccount = loginAndAuthenticate(scanner);
        
        if (currentAccount != null) {
            handleTransactions(scanner, currentAccount);
        }
        
        System.out.println("Thank you for using Priyo's ATM. Goodbye!");
        scanner.close();
    }
    
    private static void handleTransactions(Scanner scanner, BankAccount currentAccount) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Account Menu ---");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Change PIN");
            System.out.println("5. Mini Statement");
            System.out.println("6. MFS Services");
            System.out.println("7. Exit");
            System.out.print("Please choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    currentAccount.withdraw(withdrawAmount, "ATM Withdrawal");
                    saveAccount(currentAccount);
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    currentAccount.deposit(depositAmount, "ATM Deposit");
                    saveAccount(currentAccount);
                    break;
                case 3:
                    System.out.printf("Your current balance is: $%.2f\n", currentAccount.getBalance());
                    break;
                case 4:
                    System.out.print("Enter your current PIN: ");
                    int currentPin = scanner.nextInt();
                    if (currentAccount.validatePin(currentPin)) {
                        System.out.print("Enter your new PIN: ");
                        int newPin = scanner.nextInt();
                        currentAccount.setPin(newPin);
                        saveAccount(currentAccount);
                        System.out.println("PIN changed successfully.");
                    } else {
                        System.out.println("Incorrect current PIN. PIN change failed.");
                    }
                    break;
                case 5:
                    currentAccount.displayMiniStatement();
                    break;
                case 6:
                    handleMfsServices(scanner, currentAccount);
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void handleMfsServices(Scanner scanner, BankAccount currentAccount) {
        System.out.println("\n--- Mobile Financial Services ---");
        System.out.println("1. Bkash");
        System.out.println("2. Nagad");
        System.out.print("Choose MFS Provider: ");
        int mfsChoice = scanner.nextInt();

        String provider;
        if (mfsChoice == 1) {
            provider = "Bkash";
        } else if (mfsChoice == 2) {
            provider = "Nagad";
        } else {
            System.out.println("Invalid provider.");
            return;
        }

        System.out.println("\n--- " + provider + " Services ---");
        System.out.println("1. Deposit (from " + provider + " to Bank)");
        System.out.println("2. Withdraw (from Bank to " + provider + ")");
        System.out.print("Choose an option: ");
        int transactionChoice = scanner.nextInt();
            
        System.out.print("Enter your " + provider + " phone number: ");
        String phoneNumber = scanner.next();
            
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        int otp = (int) (Math.random() * 9000) + 1000;
        System.out.println("An OTP has been sent to " + phoneNumber + ".");
        System.out.println("(For demo purposes, your OTP is: " + otp + ")");
        System.out.print("Please enter the OTP to confirm: ");
        int enteredOtp = scanner.nextInt();

        if (enteredOtp == otp) {
            System.out.println("OTP Verified.");
            if (transactionChoice == 1) {
                currentAccount.deposit(amount, provider + " Deposit");
                saveAccount(currentAccount);
            } else if (transactionChoice == 2) {
                currentAccount.withdraw(amount, provider + " Withdrawal");
                saveAccount(currentAccount);
            } else {
                System.out.println("Invalid transaction type.");
            }
        } else {
            System.out.println("Incorrect OTP. Transaction cancelled.");
        }
    }

    private static BankAccount loginAndAuthenticate(Scanner scanner) {
        while (true) {
            System.out.print("\nEnter your account number (or type 'create' or 'exit'): ");
            String input = scanner.next();

            if (input.equalsIgnoreCase("exit")) return null;

            if (input.equalsIgnoreCase("create")) {
                return createNewAccount(scanner);
            }

            BankAccount account = loadAccount(input);
            if (account != null) {
                final int MAX_ATTEMPTS = 3;
                for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
                    System.out.print("Hello, " + account.getAccountHolderName() + ". Please enter your PIN: ");
                    int enteredPin = scanner.nextInt();
                    if (account.validatePin(enteredPin)) {
                        return account; // Successful login
                    } else {
                        System.out.println("Incorrect PIN. You have " + (MAX_ATTEMPTS - 1 - attempts) + " attempts remaining.");
                    }
                }
                System.out.println("Too many incorrect attempts. Your account is locked for security reasons.");
                return null; // Failed login
            } else {
                System.out.println("Account not found.");
            }
        }
    }

    private static BankAccount loadAccount(String accountNumber) {
        File file = new File(accountNumber + ".txt");
        if (!file.exists()) return null;
        
        try (Scanner fileReader = new Scanner(file)) {
            String name = fileReader.nextLine();
            double balance = Double.parseDouble(fileReader.nextLine());
            int pin = Integer.parseInt(fileReader.nextLine());
            List<String> transactions = new ArrayList<>();
            while(fileReader.hasNextLine()) {
                transactions.add(fileReader.nextLine());
            }
            return new BankAccount(accountNumber, name, balance, pin, transactions);
        } catch (Exception e) {
            System.out.println("Error reading account file. File might be corrupted.");
            return null;
        }
    }
    
    private static BankAccount createNewAccount(Scanner scanner) {
        System.out.print("Enter a new account number: ");
        String accountNumber = scanner.next();
        if (new File(accountNumber + ".txt").exists()) {
            System.out.println("An account with this number already exists.");
            return null;
        }

        System.out.print("Enter your full name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();
        
        System.out.print("Create a 4-digit PIN: ");
        int pin = scanner.nextInt();
        
        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = scanner.nextDouble();

        BankAccount newAccount = new BankAccount(accountNumber, name, initialDeposit, pin);
        saveAccount(newAccount);
        System.out.println("Account created successfully!");
        return newAccount;
    }

    private static void saveAccount(BankAccount account) {
        try (PrintWriter writer = new PrintWriter(account.getAccountNumber() + ".txt")) {
            writer.println(account.getAccountHolderName());
            writer.println(account.getBalance());
            writer.println(account.getPin());
            for(String transaction : account.getTransactions()) {
                writer.println(transaction);
            }
        } catch (IOException e) {
            System.out.println("Error: Could not save account data.");
            e.printStackTrace();
        }
    }
}
