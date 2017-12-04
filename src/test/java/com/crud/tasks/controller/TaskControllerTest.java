package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService service;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchAllTasks() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto((long)1, "Task1", "First task"));
        taskDtoList.add(new TaskDto((long)2, "Task2", "Second task"));
        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(taskDtoList);
        // When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldFetchOneTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto((long)1, "Task1", "First task");
        when(taskMapper.mapToTaskDto(service.getTask((long)1).orElseThrow(TaskNotFoundException::new))).thenReturn(taskDto);
        // When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task1")))
                .andExpect(jsonPath("$.content", is("First task")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto((long) 1, "Task1", "First task"));
        taskDtoList.add(new TaskDto((long) 2, "Task2", "Second task"));
        // When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=1"))
                .andExpect(status().is(200));
        verify(service, times(1)).deleteTask((long) 1);
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto((long) 1, "Task1", "First task");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Then
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
        verify(service, times(1)).saveTask(taskMapper.mapToTask(taskDto));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto((long) 1, "Task1", "First task");
        TaskDto updatedTask = new TaskDto((long) 1, "Task100", "Task100");
        when(taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(updatedTask);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedTask);

        // When & Then
        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task100")))
                .andExpect(jsonPath("$.content", is("Task100")));
    }
}