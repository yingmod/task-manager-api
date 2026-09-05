package com.vnn.taskmanager.dto.request;

import com.vnn.taskmanager.model.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestDto {

    @NotBlank(message = "Tiêu đề công việc không được để trống")
    @Size(max = 150, message = "Tiêu đề không được vượt quá 150 ký tự")
    private String title;

    private String description;

    private TaskPriority priority; // LOW, MEDIUM, HIGH

    private LocalDate dueDate;     // Hạn chót hoàn thành

    private Set<Long> tagIds;      // Danh sách ID các Tag muốn gắn cho Task (ví dụ: [1, 2])
}