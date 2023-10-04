import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean validatePin(String enteredPin) {
        return userPin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            return true;
        } else {
            return false;
        }
    }

    public void transfer(Account recipient, double amount) {
        if (withdraw(amount)) {
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.getUserId(), amount));
        }
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public String getUserId() {
        return userId;
    }
}

class ATM {
    private Account account;
    private Scanner scanner;

    public ATM(Account account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        if (account.validatePin(pin)) {
            System.out.println("Welcome, " + userId + "!");
            boolean quit = false;

            while (!quit) {
                System.out.println("\nATM Menu:");
                System.out.println("1. View Balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer");
                System.out.println("5. View Transaction History");
                System.out.println("6. Quit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Balance: $" + account.getBalance());
                        break;
                    case 2:
                        System.out.print("Enter deposit amount: $");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        System.out.println("Deposited $" + depositAmount + " successfully.");
                        break;
                    case 3:
                        System.out.print("Enter withdrawal amount: $");
                        double withdrawalAmount = scanner.nextDouble();
                        if (account.withdraw(withdrawalAmount)) {
                            System.out.println("Withdrawn $" + withdrawalAmount + " successfully.");
                        } else {
                            System.out.println("Insufficient funds.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter recipient's User ID: ");
                        String recipientUserId = scanner.next();
                        System.out.print("Enter transfer amount: $");
                        double transferAmount = scanner.nextDouble();
                        Account recipientAccount = new Account(recipientUserId, ""); // Dummy PIN for recipient
                        account.transfer(recipientAccount, transferAmount);
                        System.out.println("Transferred $" + transferAmount + " to " + recipientUserId + " successfully.");
                        break;
                    case 5:
                        System.out.println("Transaction History:");
                        List<Transaction> transactions = account.getTransactionHistory();
                        for (Transaction transaction : transactions) {
                            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
                        }
                        break;
                    case 6:
                        quit = true;
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid User ID or PIN. Exiting...");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Dummy account data (for demonstration purposes)
        Account account = new Account("user123", "1234");
        ATM atm = new ATM(account);
        atm.start();
    }
}
