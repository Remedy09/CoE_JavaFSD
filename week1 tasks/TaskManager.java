import java.util.*;

class Task implements Comparable<Task> {
    private String id;
    private String description;
    private int priority;

    public Task(String id, String description, int priority) {
        this.id = id;
        this.description = description;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority); // Higher priority first
    }

    @Override
    public String toString() {
        return "Task{id='" + id + "', description='" + description + "', priority=" + priority + "}";
    }
}

class TaskManager {
    private PriorityQueue<Task> taskQueue;
    private Map<String, Task> taskMap;

    public TaskManager() {
        taskQueue = new PriorityQueue<>();
        taskMap = new HashMap<>();
    }

    public void addTask(String id, String description, int priority) {
        if (taskMap.containsKey(id)) {
            System.out.println("Task with ID " + id + " already exists.");
            return;
        }
        Task task = new Task(id, description, priority);
        taskQueue.add(task);
        taskMap.put(id, task);
    }

    public void removeTask(String id) {
        Task task = taskMap.remove(id);
        if (task != null) {
            taskQueue.remove(task);
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    public Task getHighestPriorityTask() {
        return taskQueue.peek();
    }

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.addTask("1", "Complete project report", 3);
        manager.addTask("2", "Fix critical bug", 5);
        manager.addTask("3", "Respond to emails", 2);
        
        System.out.println("Highest Priority Task: " + manager.getHighestPriorityTask());
        
        manager.removeTask("2");
        System.out.println("After removing task 2, Highest Priority Task: " + manager.getHighestPriorityTask());
    }
}
