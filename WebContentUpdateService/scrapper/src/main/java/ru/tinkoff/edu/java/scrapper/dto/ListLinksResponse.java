package ru.tinkoff.edu.java.scrapper.dto;

import java.util.List;

public class ListLinksResponse {
    private List<LinkResponse> links;
    private Integer size;

    public ListLinksResponse() {

    }

    public ListLinksResponse(List<LinkResponse> links, Integer size) {
        this.links = links;
        this.size = size;
    }

    public List<LinkResponse> getLinks() {
        return links;
    }

    public void setLinks(List<LinkResponse> links) {
        this.links = links;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
