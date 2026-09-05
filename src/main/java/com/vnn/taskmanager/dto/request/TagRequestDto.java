package com.vnn.taskmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDto {

    @NotBlank(message = "Tên nhãn không được để trống")
    @Size(max = 50, message = "Tên nhãn không vượt quá 50 ký tự")
    private String name;

    private String color; // Ví dụ: "#FF5733"
}