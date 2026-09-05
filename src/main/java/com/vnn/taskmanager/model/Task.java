package com.vnn.taskmanager.model;

import com.vnn.taskmanager.model.enums.TaskPriority;
import com.vnn.taskmanager.model.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskPriority priority;

    private LocalDate dueDate; // Hạn chót hoàn thành

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Quan hệ nhiều - nhiều: Một task có thể có nhiều Tag, và một Tag có thể gắn cho nhiều Task
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "task_tags",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

    // Tự động gán thời gian tạo khi thêm mới
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = TaskStatus.TODO;
        }
        if (this.priority == null) {
            this.priority = TaskPriority.MEDIUM;
        }
    }

    // Tự động cập nhật thời gian khi chỉnh sửa
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}