package com.vnn.taskmanager.exception;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int status;             // Mã HTTP, ví dụ: 400, 404, 500
    private String message;         // Thông báo lỗi ngắn gọn
    private List<String> details;   // Danh sách chi tiết các lỗi (nếu có)
    
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}