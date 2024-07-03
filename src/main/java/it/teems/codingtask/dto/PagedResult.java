package it.teems.codingtask.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PagedResult<T>(List<T> content, int page, int totalPages, int size, int numberOfElements) {}
