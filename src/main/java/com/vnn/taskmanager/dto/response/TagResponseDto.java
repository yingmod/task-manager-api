package com.vnn.taskmanager.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagResponseDto {
    private Long id;
    private String name;
    private String color;
}