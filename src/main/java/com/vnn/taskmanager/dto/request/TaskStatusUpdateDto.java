package com.vnn.taskmanager.dto.request;

import com.vnn.taskmanager.model.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusUpdateDto {

    @NotNull(message = "Trạng thái không được để trống")
    private TaskStatus status;
}