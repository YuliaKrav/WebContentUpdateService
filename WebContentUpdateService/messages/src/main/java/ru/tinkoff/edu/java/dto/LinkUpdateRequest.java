package ru.tinkoff.edu.java.dto;

import java.io.Serializable;
import java.util.List;

public class LinkUpdateRequest implements Serializable {
    private Integer id;

    private String url;

    private String description;

    private List<Long> tgChatIds;

    public LinkUpdateRequest() {
    }

    public LinkUpdateRequest(int id, String url, String description, List<Long> tgChatIds) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.tgChatIds = tgChatIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getTgChatIds() {
        return tgChatIds;
    }

    public void setTgChatIds(List<Long> tgChatIds) {
        this.tgChatIds = tgChatIds;
    }
}
