package com.vnn.taskmanager.service.impl;

import com.vnn.taskmanager.dto.request.TaskRequestDto;
import com.vnn.taskmanager.dto.request.TaskStatusUpdateDto;
import com.vnn.taskmanager.dto.response.PageResponse;
import com.vnn.taskmanager.dto.response.TagResponseDto;
import com.vnn.taskmanager.dto.response.TaskResponseDto;
import com.vnn.taskmanager.exception.ResourceNotFoundException;
import com.vnn.taskmanager.model.Tag;
import com.vnn.taskmanager.model.Task;
import com.vnn.taskmanager.model.enums.TaskPriority;
import com.vnn.taskmanager.model.enums.TaskStatus;
import com.vnn.taskmanager.repository.TagRepository;
import com.vnn.taskmanager.repository.TaskRepository;
import com.vnn.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public TaskResponseDto createTask(TaskRequestDto request) {
        // 1. Tạo đối tượng Task từ DTO
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority() != null ? request.getPriority() : TaskPriority.MEDIUM)
                .dueDate(request.getDueDate())
                .status(TaskStatus.TODO)
                .build();

        // 2. Gán các Tag nếu người dùng có truyền danh sách tagIds
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
            task.setTags(tags);
        }

        // 3. Lưu vào database
        Task savedTask = taskRepository.save(task);
        return mapToResponseDto(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TaskResponseDto> searchTasks(String keyword, TaskStatus status, TaskPriority priority, Pageable pageable) {
        // Chuẩn hóa từ khóa tìm kiếm
        String searchKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;

        Page<Task> taskPage = taskRepository.searchTasks(searchKeyword, status, priority, pageable);

        // Chuyển đổi từng Task Entity sang TaskResponseDto
        Page<TaskResponseDto> dtoPage = taskPage.map(this::mapToResponseDto);
        return PageResponse.from(dtoPage);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công việc với ID: " + id));
        return mapToResponseDto(task);
    }

    @Override
    @Transactional
    public TaskResponseDto updateTask(Long id, TaskRequestDto request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công việc với ID: " + id));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        task.setDueDate(request.getDueDate());

        // Cập nhật lại danh sách nhãn (Tags)
        if (request.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
            task.setTags(tags);
        }

        Task updatedTask = taskRepository.save(task);
        return mapToResponseDto(updatedTask);
    }

    @Override
    @Transactional
    public TaskResponseDto updateTaskStatus(Long id, TaskStatusUpdateDto request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy công việc với ID: " + id));

        task.setStatus(request.getStatus());
        Task updatedTask = taskRepository.save(task);
        return mapToResponseDto(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy công việc với ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    // Chuyển đổi Task Entity sang TaskResponseDto kèm danh sách Tag
    private TaskResponseDto mapToResponseDto(Task task) {
        Set<TagResponseDto> tagDtos = task.getTags() == null ? Set.of() :
                task.getTags().stream()
                        .map(tag -> TagResponseDto.builder()
                                .id(tag.getId())
                                .name(tag.getName())
                                .color(tag.getColor())
                                .build())
                        .collect(Collectors.toSet());

        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .tags(tagDtos)
                .build();
    }
}