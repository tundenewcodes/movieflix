package com.movieflix.movieflix.dto;


import java.util.List;

public class MoviePageResponse {

    private List<MovieDto> movieDtos;
    private Integer pageNumber;
    private Integer pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;

    public MoviePageResponse(List<MovieDto> movieDtos, Integer pageNumber, Integer pageSize, long totalElements, int totalPages, boolean isLast) {
        this.movieDtos = movieDtos;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLast = isLast;
    }

    // Getters
    public List<MovieDto> getMovieDtos() {
        return movieDtos;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return isLast;
    }
}

