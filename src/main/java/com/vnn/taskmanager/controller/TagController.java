package com.vnn.taskmanager.controller;
import com.vnn.taskmanager.dto.request.TagRequestDto;
import com.vnn.taskmanager.dto.response.ApiResponse;
import com.vnn.taskmanager.dto.response.TagResponseDto;
import com.vnn.taskmanager.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@Tag(name = "Tag Management", description = "Các API quản lý Nhãn công việc")
public class TagController {

    private final TagService tagService;

    @PostMapping
    @Operation(summary = "Tạo nhãn mới")
    public ResponseEntity<ApiResponse<TagResponseDto>> createTag(@Valid @RequestBody TagRequestDto request) {
        TagResponseDto data = tagService.createTag(request);
        return new ResponseEntity<>(ApiResponse.ok(data, "Tạo nhãn thành công!"), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả các nhãn")
    public ResponseEntity<ApiResponse<List<TagResponseDto>>> getAllTags() {
        List<TagResponseDto> data = tagService.getAllTags();
        return ResponseEntity.ok(ApiResponse.ok(data, "Lấy danh sách nhãn thành công!"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy chi tiết nhãn theo ID")
    public ResponseEntity<ApiResponse<TagResponseDto>> getTagById(@PathVariable Long id) {
        TagResponseDto data = tagService.getTagById(id);
        return ResponseEntity.ok(ApiResponse.ok(data, "Lấy chi tiết nhãn thành công!"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa nhãn theo ID")
    public ResponseEntity<ApiResponse<Void>> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Xóa nhãn thành công!"));
    }
}