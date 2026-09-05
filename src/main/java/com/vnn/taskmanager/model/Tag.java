package com.vnn.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name; // Ví dụ: "Học tập", "Công việc", "Khẩn cấp"

    @Column(length = 20)
    private String color; // Mã màu hex để hiển thị trên giao diện, ví dụ: "#FF5733"
}