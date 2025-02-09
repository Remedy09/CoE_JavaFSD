import java.util.Scanner;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount, String user) {
        if (amount > 0) {
            balance += amount;
            System.out.println(user + " deposited " + amount + ". New balance: " + balance);
        }
    }

    public synchronized void withdraw(double amount, String user) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println(user + " withdrew " + amount + ". New balance: " + balance);
        } else {
            System.out.println(user + " attempted to withdraw " + amount + " but insufficient balance.");
        }
    }

    public double getBalance() {
        return balance;
    }
}

class BankingDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        BankAccount account = new BankAccount(initialBalance);

        System.out.print("Enter number of users: ");
        int userCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Thread[] users = new Thread[userCount * 2];
        
        for (int i = 0; i < userCount; i++) {
            System.out.print("Enter name of User" + (i + 1) + ": ");
            String userName = scanner.nextLine();

            System.out.print(userName + " enter deposit amount: ");
            double depositAmount = scanner.nextDouble();
            
            System.out.print(userName + " enter withdrawal amount: ");
            double withdrawAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            
            users[i * 2] = new Thread(() -> account.deposit(depositAmount, userName), userName + "-Deposit");
            users[i * 2 + 1] = new Thread(() -> account.withdraw(withdrawAmount, userName), userName + "-Withdraw");
        }

        for (Thread user : users) {
            user.start();
        }

        for (Thread user : users) {
            try {
                user.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Final balance: " + account.getBalance());
        scanner.close();
    }
}