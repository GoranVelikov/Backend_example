package com.example.demo.dto;

import java.util.List;

public class PageResponse <T>{

    private List<T> list;
    private int page;
    private int size;
    private Long totalElements;
    private int  totalPages;


    public PageResponse() {
    }

    public PageResponse(List<T> list, int page, int size, Long totalElements, int totalPages) {
        this.list = list;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
