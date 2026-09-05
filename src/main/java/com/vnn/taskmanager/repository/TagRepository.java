package com.vnn.taskmanager.repository;

import com.vnn.taskmanager.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Spring tự hiểu câu truy vấn: SELECT * FROM tags WHERE name = ?
    Optional<Tag> findByName(String name);

    // Kiểm tra xem tên tag đã tồn tại chưa để tránh trùng lặp: SELECT COUNT(*) > 0 FROM tags WHERE name = ?
    boolean existsByName(String name);
}