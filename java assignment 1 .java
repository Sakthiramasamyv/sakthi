package javaproject;
import java.util.*;
import java.text.SimpleDateFormat;


class User {
    private String id;
    private String name;
    private String role;
    private String email;

    public User(String id, String name, String role, String email) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setRole(String role) { this.role = role; }
    public void setEmail(String email) { this.email = email; }

    
    public String display() {
        return String.format("User: %s | Role: %s | Email: %s", name, role, email);
    }
}

class Manager extends User {
    private String department;

    public Manager(String id, String name, String email, String department) {
        super(id, name, "Manager", email);
        this.department = department;
    }

    @Override
    public String display() {
        return String.format("Manager: %s | Dept: %s | Email: %s", 
                             getName(), department, getEmail());
    }

    public String getDepartment() { return department; }
}

enum TaskStatus { TODO, IN_PROGRESS, DONE }

class Task {
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private User assignedUser;
    private Date deadline;

    public Task(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TaskStatus.TODO;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public User getAssignedUser() { return assignedUser; }
    public Date getDeadline() { return deadline; }

        public void setStatus(TaskStatus status) { this.status = status; }
    public void setAssignedUser(User user) { this.assignedUser = user; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public String display() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dl = (deadline == null) ? "N/A" : sdf.format(deadline);
        String assigned = (assignedUser == null) ? "Unassigned" : assignedUser.getName();
        return String.format("Task[%s]: %s | Status: %s | Assigned: %s | Deadline: %s",
                             id, title, status, assigned, dl);
    }
}

class Project {
    private String id;
    private String name;
    private Date deadline;
    private List<User> teamMembers;
    private List<Task> tasks;

    public Project(String id, String name, Date deadline) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.teamMembers = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) { tasks.add(task); }

    public void addMember(User u) { teamMembers.add(u); }

    public List<Task> getTasks() { return tasks; }
    public String getName() { return name; }

    public void listTasks() {
        System.out.println("Tasks for project: " + name);
        for (Task t : tasks) {
            System.out.println("  " + t.display());
        }
    }
}

class TaskManager {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Project> projects = new HashMap<>();

    public void addUser(User u) { users.put(u.getId(), u); }

        public void addProject(Project p) { projects.put(p.getName(), p); }

    // Method overloading: assign task with/without deadline
    public void assignTask(Task task, User user) {
        task.setAssignedUser(user);
    }
    public void assignTask(Task task, User user, Date deadline) {
        task.setAssignedUser(user);
        task.setDeadline(deadline);
    }

    public void updateStatus(Task task, TaskStatus status) {
        task.setStatus(status);
    }
    public void reportCompletedByUser(String userId) {
        User u = users.get(userId);
        if (u == null) return;

        System.out.println("Tasks completed by " + u.getName() + ":");
        for (Project p : projects.values()) {
            for (Task t : p.getTasks()) {
                if (t.getAssignedUser() != null && t.getAssignedUser().getId().equals(userId)
                        && t.getStatus() == TaskStatus.DONE) {
                    System.out.println("  " + t.display());
                }
            }
        }
    }

   
    public void showProjectTasks(String projectName) {
        Project p = projects.get(projectName);
        if (p != null) {
            p.listTasks();
        }
    }
}

public class TaskAppMain {
    public static void main(String[] args) throws Exception {
        TaskManager manager = new TaskManager();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        User emp1 = new User("U1", "Alice", "Developer", "alice@company.com");
        User emp2 = new User("U2", "Bob", "Tester", "bob@company.com");
        Manager mgr = new Manager("M1", "Charlie", "charlie@company.com", "IT");

        manager.addUser(emp1);
        manager.addUser(emp2);
        manager.addUser(mgr);

                Project proj = new Project("P1", "Website Revamp", sdf.parse("2025-12-31"));
        proj.addMember(emp1);
        proj.addMember(emp2);
        proj.addMember(mgr);
        manager.addProject(proj);

        Task t1 = new Task("T1", "Design Homepage", "Create modern responsive homepage");
        Task t2 = new Task("T2", "Write Unit Tests", "Ensure code coverage for backend");

        proj.addTask(t1);
        proj.addTask(t2);

        manager.assignTask(t1, emp1, sdf.parse("2025-09-20")); // overloaded method
        manager.assignTask(t2, emp2);

        manager.updateStatus(t1, TaskStatus.IN_PROGRESS);
        manager.updateStatus(t1, TaskStatus.DONE);

        manager.showProjectTasks("Website Revamp");
        manager.reportCompletedByUser("U1");

        User polyUser = mgr;  // base type reference
        System.out.println("Polymorphism check -> " + polyUser.display());
    }
}
