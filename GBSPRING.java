import java.util.ArrayList;
import java.util.List;

// 1. Паттерн Singleton:
class TaskManagerSingleton {
    private static TaskManagerSingleton instance;
    private List<Task> tasks;

    private TaskManagerSingleton() {
        tasks = new ArrayList<>();
    }

    public static TaskManagerSingleton getInstance() {
        if (instance == null) {
            instance = new TaskManagerSingleton();
        }
        return instance;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<String> getTasks() {
        List<String> details = new ArrayList<>();
        for (Task task : tasks) {
            details.add(task.getDetails());
        }
        return details;
    }
}

// 2. Паттерн Factory:
abstract class Task {
    protected String name;
    protected String description;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract String getDetails();
}

class NormalTask extends Task {
    public NormalTask(String name, String description) {
        super(name, description);
    }

    @Override
    public String getDetails() {
        return "Normal Task: " + name + " - " + description;
    }
}

class UrgentTask extends Task {
    public UrgentTask(String name, String description) {
        super(name, description);
    }

    @Override
    public String getDetails() {
        return "Urgent Task: " + name + " - " + description;
    }
}

class RepeatingTask extends Task {
    private String frequency;

    public RepeatingTask(String name, String description, String frequency) {
        super(name, description);
        this.frequency = frequency;
    }

    @Override
    public String getDetails() {
        return "Repeating Task: " + name + " - " + description + ", Frequency: " + frequency;
    }
}

class TaskFactory {
    public static Task createTask(String type, String name, String description, String frequency) {
        switch (type.toLowerCase()) {
            case "normal":
                return new NormalTask(name, description);
            case "urgent":
                return new UrgentTask(name, description);
            case "repeating":
                return new RepeatingTask(name, description, frequency);
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }
    }
}

// 3. Паттерн Decorator:
abstract class TaskDecorator extends Task {
    protected Task task;

    public TaskDecorator(Task task) {
        super(task.name, task.description);
        this.task = task;
    }

    public abstract String getDetails();
}

class ImportantTaskDecorator extends TaskDecorator {
    public ImportantTaskDecorator(Task task) {
        super(task);
    }

    @Override
    public String getDetails() {
        return "[IMPORTANT] " + task.getDetails();
    }
}

class CompletedTaskDecorator extends TaskDecorator {
    public CompletedTaskDecorator(Task task) {
        super(task);
    }

    @Override
    public String getDetails() {
        return "[COMPLETED] " + task.getDetails();
    }
}


public class GBSPRING {
    public static void main(String[] args) {
        // Получаем экземпляр менеджера задач (Singleton)
        TaskManagerSingleton taskManager = TaskManagerSingleton.getInstance();

        // Создаем задачи через Factory
        Task task1 = TaskFactory.createTask("normal", "Buy groceries", "Buy milk and eggs", null);
        Task task2 = TaskFactory.createTask("urgent", "Finish report", "Complete financial report by evening", null);
        Task task3 = TaskFactory.createTask("repeating", "Water plants", "Water the plants daily", "daily");

        // Добавляем задачи в менеджер задач
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        // Decorator отфильтрует задачи как "важные" или "выполненные"
        task1 = new ImportantTaskDecorator(task1);
        task2 = new CompletedTaskDecorator(task2);

        // Получаем список всех задач и выводим их детали
        List<String> tasksDetails = taskManager.getTasks();
        for (String taskDetails : tasksDetails) {
            System.out.println(taskDetails);
        }
    }
}
//Singletoon используется как менеджер, Factory как создатель , Decorator как создатель дополнительных функций для задач