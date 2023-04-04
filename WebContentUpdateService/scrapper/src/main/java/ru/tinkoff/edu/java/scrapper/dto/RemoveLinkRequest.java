package ru.tinkoff.edu.java.scrapper.dto;

public class RemoveLinkRequest {
    String link;

    public RemoveLinkRequest() {

    }

    public RemoveLinkRequest(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
