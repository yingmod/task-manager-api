package com.vnn.taskmanager.repository;

import com.vnn.taskmanager.model.Task;
import com.vnn.taskmanager.model.enums.TaskPriority;
import com.vnn.taskmanager.model.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Tìm kiếm và lọc Task linh hoạt có phân trang:
     * - Nếu keyword rỗng -> bỏ qua điều kiện keyword.
     * - Nếu status null -> bỏ qua điều kiện status.
     * - Nếu priority null -> bỏ qua điều kiện priority.
     */
    @Query("SELECT t FROM Task t WHERE " +
           "(:keyword IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           " OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:status IS NULL OR t.status = :status) AND " +
           "(:priority IS NULL OR t.priority = :priority)")
    Page<Task> searchTasks(
            @Param("keyword") String keyword,
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            Pageable pageable
    );
}