package com.vnn.taskmanager.service.impl;

import com.vnn.taskmanager.dto.request.TagRequestDto;
import com.vnn.taskmanager.dto.response.TagResponseDto;
import com.vnn.taskmanager.exception.BadRequestException;
import com.vnn.taskmanager.exception.ResourceNotFoundException;
import com.vnn.taskmanager.model.Tag;
import com.vnn.taskmanager.repository.TagRepository;
import com.vnn.taskmanager.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    @Transactional
    public TagResponseDto createTag(TagRequestDto request) {
        // Kiểm tra xem tên tag đã có chưa
        if (tagRepository.existsByName(request.getName())) {
            throw new BadRequestException("Nhãn '" + request.getName() + "' đã tồn tại!");
        }

        Tag tag = Tag.builder()
                .name(request.getName().trim())
                .color(request.getColor())
                .build();

        Tag savedTag = tagRepository.save(tag);
        return mapToResponseDto(savedTag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponseDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponseDto getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhãn với ID: " + id));
        return mapToResponseDto(tag);
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy nhãn với ID: " + id);
        }
        tagRepository.deleteById(id);
    }

    // Hàm chuyển đổi Entity sang DTO
    private TagResponseDto mapToResponseDto(Tag tag) {
        return TagResponseDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .color(tag.getColor())
                .build();
    }
}