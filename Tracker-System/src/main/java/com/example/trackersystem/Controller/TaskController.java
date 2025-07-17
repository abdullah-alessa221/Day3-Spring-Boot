package com.example.trackersystem.Controller;

import com.example.trackersystem.Model.Task;
import org.springframework.web.bind.annotation.*;
import com.example.trackersystem.ApiResponse.ApiResponse;




import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    ArrayList<Task> tasks = new ArrayList<>();

    int currentId = 1;

    @GetMapping("/get")
    public ArrayList<Task> getTask(){
        return tasks;
    }

    @PostMapping("/add")
    public ApiResponse addTask(@RequestBody Task task) {
        task.setId(currentId++);
        tasks.add(task);
        return new ApiResponse("Task added successfully", 200);
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateTask(@PathVariable int id, @RequestBody Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                updatedTask.setId(id);
                tasks.set(i, updatedTask);
                return new ApiResponse("Task updated successfully", 200);
            }
        }
        return new ApiResponse("Task not found", 404);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteTask(@PathVariable int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                tasks.remove(task);
                return new ApiResponse("Task deleted successfully", 200);
            }
        }
        return new ApiResponse("Task not found", 404);
    }

    @PutMapping("/change-status/{id}")
    public ApiResponse changeStatus(@PathVariable int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setStatus(!task.isStatus());
                return new ApiResponse("Task status changed", 200);
            }
        }
        return new ApiResponse("Task not found", 404);
    }

    @GetMapping("/search/{title}")
    public ArrayList<Task> searchTask(@PathVariable String title) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTitle().equalsIgnoreCase(title)) {
                result.add(task);
            }
        }
        return result;
    }

}
