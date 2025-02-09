import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;

class Book implements Serializable {
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable = true;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }
    
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getISBN() { return ISBN; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}

class User implements Serializable {
    private String name;
    private String userID;
    private List<String> borrowedBooks = new ArrayList<>();
    private static final int MAX_BOOKS_ALLOWED = 3;

    public User(String name, String userID) {
        this.name = name;
        this.userID = userID;
    }
    
    public String getUserID() { return userID; }
    public List<String> getBorrowedBooks() { return borrowedBooks; }
    public boolean canBorrow() { return borrowedBooks.size() < MAX_BOOKS_ALLOWED; }
    public void borrowBook(String ISBN) { borrowedBooks.add(ISBN); }
    public void returnBook(String ISBN) { borrowedBooks.remove(ISBN); }
}


class BookNotFoundException extends Exception {
    public BookNotFoundException(String message ) { super(message); }
}
class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) { super(message); }
}
class MaxBooksAllowedException extends Exception {
    public MaxBooksAllowedException(String message) { super(message); }
}

interface ILibrary {
    void borrowBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException;
    void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException;
    void reserveBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException;
    Book searchBook(String title);
}


abstract class LibrarySystem implements ILibrary {
    protected List<Book> books = new ArrayList<>();
    protected List<User> users = new ArrayList<>();
    protected final Lock lock = new ReentrantLock();
    
    public abstract void addBook(Book book);
    public abstract void addUser(User user);
}


class LibraryManager extends LibrarySystem {
    public void addBook(Book book) { books.add(book); }
    public void addUser(User user) { users.add(user); }
    
    public void borrowBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException, MaxBooksAllowedException {
        lock.lock();
        try {
            User user = findUser(userID);
            if (!user.canBorrow()) throw new MaxBooksAllowedException("User has reached borrowing limit.");
            Book book = findBook(ISBN);
            if (!book.isAvailable()) throw new BookNotFoundException("Book is already borrowed.");
            
            book.setAvailable(false);
            user.borrowBook(ISBN);
            System.out.println(userID + " borrowed " + book.getTitle());
        } finally {
            lock.unlock();
        }
    }
    

    public void returnBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
        lock.lock();
        try {
            User user = findUser(userID);
            Book book = findBook(ISBN);
            
            book.setAvailable(true);
            user.returnBook(ISBN);
            System.out.println(userID + " returned " + book.getTitle());
        } finally {
            lock.unlock();
        }
    }
    
    public void reserveBook(String ISBN, String userID) throws BookNotFoundException, UserNotFoundException {
        System.out.println(userID + " reserved " + ISBN);
    }
    
    public Book searchBook(String title) {
        return books.stream().filter(b -> b.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
    }
    
    private Book findBook(String ISBN) throws BookNotFoundException {
        return books.stream().filter(b -> b.getISBN().equals(ISBN)).findFirst().orElseThrow(() -> new BookNotFoundException("Book not found"));
    }
    
    private User findUser(String userID) throws UserNotFoundException {
        return users.stream().filter(u -> u.getUserID().equals(userID)).findFirst().orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}

// Main_Class
public class Task1 {
    public static void main(String[] args) {
        LibraryManager library = new LibraryManager();
        library.addBook(new Book("Java Programming" , "Author A" , "ISBN123"));
        library.addBook(new Book("Python Basics" , "Author B" , "ISBN456"));
        library.addUser(new User("Alice", "U001"));
        library.addUser(new User("Bob", "U002"));

        ExecutorService executor = Executors.newFixedThreadPool(3); 
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Borrow Book \n2. Return Book \n3. Search Book \n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 4) break;

            System.out.print("Enter User ID: ");
            String userID = scanner.nextLine();
            
            System.out.print("Enter Book ISBN: ");
            String ISBN = scanner.nextLine();

            Runnable task = () -> {
                try {
                    if (choice == 1) {
                        library.borrowBook(ISBN, userID);
                    } else if (choice == 2) {
                        library.returnBook(ISBN, userID);
                    } else if (choice == 3) {
                        Book book = library.searchBook(ISBN);
                        System.out.println(book != null ? "Book found: " + book.getTitle() : "Book not found");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            };

            executor.execute(task); 
        }

        executor.shutdown(); 
        scanner.close();
    }
}
