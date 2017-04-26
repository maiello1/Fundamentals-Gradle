package com.maiello1;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.List;

class Task {
    private String body;
    private boolean done;
    private int id;
    private int priority;
    private String title;

    public String toString() {
        return title + ": " + body;
    }
}

class TaskCollection implements Iterable<Task>{
    // should be private; public for simplicity
    private List<Task> todos;

    public Iterator<Task> iterator() {
        return todos.iterator();
    }
}

public class Main {
    public static void main(String[] args) {
        String jsonData = "{\n" +
                "  \"todos\": [\n" +
                "    {\n" +
                "      \"body\": \"Walk the dog\",\n" +
                "      \"done\": false,\n" +
                "      \"id\": 0,\n" +
                "      \"priority\": 3,\n" +
                "      \"title\": \"dog\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"body\": \"Pay the bills\",\n" +
                "      \"done\": false,\n" +
                "      \"id\": 1,\n" +
                "      \"priority\": 1,\n" +
                "      \"title\": \"bills\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Gson gson = new Gson();
        TaskCollection tasks = gson.fromJson(jsonData, TaskCollection.class);
        for (Task t: tasks) {
            System.out.println(t);
        }

        System.out.println(gson.toJson(tasks));

    }
}