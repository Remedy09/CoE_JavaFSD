import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}

class InvalidLocationException extends Exception {
    public InvalidLocationException(String message) {
        super(message);
    }
}


class Location {
    private int aisle, shelf, bin;

    public Location(int aisle, int shelf, int bin) {
        this.aisle = aisle;
        this.shelf = shelf;
        this.bin = bin;
    }

    @Override
    public String toString() {
        return "Aisle: " + aisle + ", Shelf: " + shelf + ", Bin: " + bin;
    }
}

class Product {
    private String productID, name;
    private int quantity;
    private Location location;

    public Product(String productID, String name, int quantity, Location location) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public String getProductID() { return productID; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Location getLocation() { return location; }

    @Override
    public String toString() {
        return productID + " - " + name + " (Qty: " + quantity + ") @ " + location;
    }
}

class Order implements Comparable<Order> {
    private String orderID;
    private List<String> productIDs;
    private Priority priority;

    public enum Priority {
        STANDARD, EXPEDITED
    }

    public Order(String orderID, List<String> productIDs, Priority priority) {
        this.orderID = orderID;
        this.productIDs = productIDs;
        this.priority = priority;
    }

    public String getOrderID() { return orderID; }
    public List<String> getProductIDs() { return productIDs; }
    public Priority getPriority() { return priority; }

    @Override
    public int compareTo(Order o) {
        return this.priority.compareTo(o.priority);
    }
}

class InventoryManager {
    private final ConcurrentHashMap<String, Product> products = new ConcurrentHashMap<>();
    private final PriorityQueue<Order> orderQueue = new PriorityQueue<>(Collections.reverseOrder());
    private final ExecutorService orderProcessor = Executors.newFixedThreadPool(3); // 3 parallel threads
    
    public synchronized void addProduct(Product product) {
        products.put(product.getProductID(), product);
        System.out.println("Product added: " + product);
    }

    public void processOrders() {
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            orderProcessor.execute(() -> processOrder(order));
        }
    }

    private void processOrder(Order order) {
        System.out.println("Processing Order: " + order.getOrderID());
        for (String productID : order.getProductIDs()) {
            try {
                fulfillProduct(productID);
            } catch (OutOfStockException e) {
                System.out.println("Order " + order.getOrderID() + " failed: " + e.getMessage());
                return;
            }
        }
        System.out.println("Order " + order.getOrderID() + " fulfilled successfully.");
    }

    private synchronized void fulfillProduct(String productID) throws OutOfStockException {
        Product product = products.get(productID);
        if (product == null || product.getQuantity() == 0) {
            throw new OutOfStockException("Product " + productID + " is out of stock!");
        }
        product.setQuantity(product.getQuantity() - 1);
    }

    public void addOrder(Order order) {
        orderQueue.add(order);
        System.out.println("Order added: " + order.getOrderID() + " [" + order.getPriority() + "]");
    }

    public void shutdown() {
        orderProcessor.shutdown();
    }
}

public class task2 {
    public static void main(String[] args) {
        InventoryManager inventory = new InventoryManager();
        inventory.addProduct(new Product("P001", "Laptop", 10, new Location(1, 2, 3)));
        inventory.addProduct(new Product("P002", "Phone", 5, new Location(1, 3, 2)));
        inventory.addProduct(new Product("P003", "Headphones", 20, new Location(2, 1, 5)));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Order\n2. Process Orders\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice == 3) break;
            
            if (choice == 1) {
                System.out.print("Enter Order ID: ");
                String orderID = scanner.nextLine();
                
                System.out.print("Enter Product IDs (comma-separated): ");
                String[] productIDs = scanner.nextLine().split(",");
                
                System.out.print("Enter Priority (STANDARD/EXPEDITED): ");
                Order.Priority priority = Order.Priority.valueOf(scanner.nextLine().toUpperCase());
                
                inventory.addOrder(new Order(orderID, Arrays.asList(productIDs), priority));
            }
            else if (choice == 2) {
                inventory.processOrders();
            }
        }
        
        inventory.shutdown();
        scanner.close();
    }
}