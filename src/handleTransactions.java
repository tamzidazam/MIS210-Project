import java.util.Scanner;

import ATM.BankAccount;

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