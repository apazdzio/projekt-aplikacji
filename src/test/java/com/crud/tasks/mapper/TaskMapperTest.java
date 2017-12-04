package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {

    @InjectMocks
    private TaskMapper taskMapper;
    @Mock
    private List<Task> taskList;

    @Test
    public void shouldMapToTask() {
        //Given
        Task task = new Task((long) 1, "Task1", "Test task");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        Task task2 = taskMapper.mapToTask(taskDto);
        //Then
        Assert.assertEquals("Task1", taskDto.getTitle());
        Assert.assertEquals("Test task", taskDto.getContent());

        Assert.assertEquals("Task1", task2.getTitle());
        Assert.assertEquals("Test task", task2.getContent());
    }

    @Test
    public void shouldMapToTaskDtoList() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task((long) 1, "Task1", "Test task"));
        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //Then
        Assert.assertEquals(1, taskDtoList.size());
    }
}