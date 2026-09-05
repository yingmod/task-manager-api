package com.vnn.taskmanager.controller;
import com.vnn.taskmanager.dto.request.TaskRequestDto;
import com.vnn.taskmanager.dto.request.TaskStatusUpdateDto;
import com.vnn.taskmanager.dto.response.ApiResponse;
import com.vnn.taskmanager.dto.response.PageResponse;
import com.vnn.taskmanager.dto.response.TaskResponseDto;
import com.vnn.taskmanager.model.enums.TaskPriority;
import com.vnn.taskmanager.model.enums.TaskStatus;
import com.vnn.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "Các API quản lý Công việc")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Tạo công việc mới")
    public ResponseEntity<ApiResponse<TaskResponseDto>> createTask(@Valid @RequestBody TaskRequestDto request) {
        TaskResponseDto data = taskService.createTask(request);
        return new ResponseEntity<>(ApiResponse.ok(data, "Tạo công việc thành công!"), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Tìm kiếm và lọc danh sách công việc (có phân trang & sắp xếp)")
    public ResponseEntity<ApiResponse<PageResponse<TaskResponseDto>>> getTasks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        PageResponse<TaskResponseDto> data = taskService.searchTasks(keyword, status, priority, pageable);
        return ResponseEntity.ok(ApiResponse.ok(data, "Lấy danh sách công việc thành công!"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Xem chi tiết công việc theo ID")
    public ResponseEntity<ApiResponse<TaskResponseDto>> getTaskById(@PathVariable Long id) {
        TaskResponseDto data = taskService.getTaskById(id);
        return ResponseEntity.ok(ApiResponse.ok(data, "Lấy chi tiết công việc thành công!"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật toàn bộ thông tin công việc")
    public ResponseEntity<ApiResponse<TaskResponseDto>> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDto request
    ) {
        TaskResponseDto data = taskService.updateTask(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Cập nhật công việc thành công!"));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Đổi nhanh trạng thái công việc (TODO -> IN_PROGRESS -> DONE)")
    public ResponseEntity<ApiResponse<TaskResponseDto>> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody TaskStatusUpdateDto request
    ) {
        TaskResponseDto data = taskService.updateTaskStatus(id, request);
        return ResponseEntity.ok(ApiResponse.ok(data, "Cập nhật trạng thái thành công!"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa công việc theo ID")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Xóa công việc thành công!"));
    }
}