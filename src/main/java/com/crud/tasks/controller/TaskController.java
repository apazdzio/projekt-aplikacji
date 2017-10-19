package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper taskMapper;

    @GetMapping(value = "getTasks")
    public List<TaskDto> getTasks(){
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @GetMapping(value = "getTask/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId){
        return taskMapper.mapToTaskDto(service.getTaskById(taskId));
    }

    @DeleteMapping(value = "deleteTask/{taskId}")
    public void deleteTask(@PathVariable String taskId){
    }

    @PutMapping(value = "updateTask")
    public TaskDto updateTask(TaskDto taskDto){
        return new TaskDto((long)1, "Edited test title", "test content");
    }

    @PostMapping(value = "createTask")
    public void createTask(TaskDto taskDto){
    }
}
