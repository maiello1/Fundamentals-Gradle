package com.maiello1;

import java.util.*;
import java.util.Collections;
import com.google.gson.Gson;

import java.io.*;

class Task implements Comparable<Task>{
    private List<Double> saveList;
    private String name, description;
    private int priority;

    public Task (String name, String description, int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    public String getName() {

        return name;
    }

    public String getDescription() {

        return description;
    }

    public int getPriority() {

        return priority;
    }

    public List<Double> getSaveList() {

        return new ArrayList<Double>(saveList);
    }
    public void setTask(List<Double> saveList) {

        this.saveList = saveList;
    }

    public int compareTo(Task other){

        if (!this.name.equals(other.name)) {
            return this.getName().compareTo(other.getName());
        } else {
            return this.getPriority() - other.getPriority();
        }
    }

    public void display() {
        System.out.println(name);

    }
}

class TaskList implements Iterable<Task> {
    private List<Task> tasks = new ArrayList<Task>();

    public void addNew(Task task) {

        tasks.add(task);
    }

    public void remove(int index) {

        tasks.remove(index);
    }

    public void update(int index, Task updateTask) {

        tasks.set(index, updateTask);
    }

    public void displayElement() {
        for (int i = 0; i < tasks.size(); i++) {
            Task aTask = tasks.get(i);
            System.out.println(i + ". " + aTask.getName());
        }
    }

    public void displayByPriority(int priority) {
        for (int i = 0; i < tasks.size(); i++) {
            Task aTask = tasks.get(i);
            if (aTask.getPriority() == priority) {
                System.out.println(i + ". " + aTask.getName());
            }
        }

    }

    public void sort() {
        Collections.sort(tasks);

        for (Task task : tasks) {
            task.display();
        }
    }

    public Iterator<Task> iterator() {

        return tasks.iterator();
    }
}


class UserInput {
    Scanner scan = new Scanner(System.in);

    public int promptInt(String message) {
        System.out.println(message);
        String userInput = scan.nextLine();

        int userInt = -1;
        while  (userInt < 0 || userInt > 7) {

            try {
                userInt = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println(userInput + " is not a valid choice. " + message);
                userInput = scan.nextLine();
            }
        }

        return userInt;
    }

    public String promptString(String message) {
        System.out.println(message);
        return scan.nextLine();
    }

}

public class Main {
    static final String FILENAME = "data.json";


    public static TaskList load(String filename) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(filename);
        try {
            return gson.fromJson(reader, TaskList.class);
        } finally {
            reader.close();
        }
    }

    public static void save(String filename, TaskList data) throws IOException {
        Gson gson = new Gson();
        FileWriter writer = new FileWriter(filename);
        try {
            gson.toJson(data, writer);
        } finally {
            writer.close();
        }
    }

    public static void main(String[] args) {

        UserInput userInput = new UserInput();

        TaskList taskList;
        try {
            taskList = load(FILENAME);
        } catch (IOException e) {
            taskList = new TaskList();
            e.printStackTrace();
        }

        int answer = 1;
        while (answer != 0) {
            System.out.println("Please choose an option:\n" +
                    "(1) Add a task.\n" +
                    "(2) Remove a task.\n" +
                    "(3) Update a task.\n" +
                    "(4) List all tasks.\n" +
                    "(5) List all tasks of a certain priority.\n" +
                    "(6) Sort tasks by priority.\n" +
                    "(7) Iterate through all tasks.\n" +
                    "(0) Exit.\n");

            int option = userInput.promptInt("Choose an option: ");

            if (option == 1) {
                String name = userInput.promptString("Enter the new task's name.");
                String description = userInput.promptString("Enter the new task's description.");
                int priority = userInput.promptInt("Enter the new task's priority, 0 (low) to 5 (high).");
                Task A = new Task(name, description, priority);
                taskList.addNew(A);
            } else if (option == 2) {
                int removeItem = userInput.promptInt("Enter the index of the item to remove.");
                taskList.remove(removeItem);
            } else if (option == 3) {
                int index = userInput.promptInt("Enter the index of the task to update.");
                String newTask = userInput.promptString("Enter the new task's name.");
                String newDescription = userInput.promptString("Enter the new task's description.");
                int newPriority = userInput.promptInt("Enter the new task's priority, 0 (low) to 5 (high).");
                Task B = new Task(newTask, newDescription, newPriority);
                taskList.update(index, B);
            } else if (option == 4) {
                taskList.displayElement();
            } else if (option == 5) {
                int priority = userInput.promptInt("Enter a priority, 0 (low) to 5 (high).");
                taskList.displayByPriority(priority);
            } else if (option == 6) {

                taskList.sort();
            } else if (option == 7) {
                for (Task entry : taskList) {
                    entry.display();
                }
            } else {
                answer = 0;
                try {
                    save(FILENAME, taskList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }
    }
}