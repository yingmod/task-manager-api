package com.vnn.taskmanager.service;

import com.vnn.taskmanager.dto.request.TaskRequestDto;
import com.vnn.taskmanager.dto.request.TaskStatusUpdateDto;
import com.vnn.taskmanager.dto.response.PageResponse;
import com.vnn.taskmanager.dto.response.TaskResponseDto;
import com.vnn.taskmanager.model.enums.TaskPriority;
import com.vnn.taskmanager.model.enums.TaskStatus;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponseDto createTask(TaskRequestDto request);
    PageResponse<TaskResponseDto> searchTasks(String keyword, TaskStatus status, TaskPriority priority, Pageable pageable);
    TaskResponseDto getTaskById(Long id);
    TaskResponseDto updateTask(Long id, TaskRequestDto request);
    TaskResponseDto updateTaskStatus(Long id, TaskStatusUpdateDto request);
    void deleteTask(Long id);
}