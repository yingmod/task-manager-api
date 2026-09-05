package com.vnn.taskmanager.service;


import com.vnn.taskmanager.dto.request.TagRequestDto;
import com.vnn.taskmanager.dto.response.TagResponseDto;

import java.util.List;

public interface TagService {
    TagResponseDto createTag(TagRequestDto request);
    List<TagResponseDto> getAllTags();
    TagResponseDto getTagById(Long id);
    void deleteTag(Long id);
}