package ru.tinkoff.edu.java.scrapper.dto;

public class AddLinkRequest {
    String link;

    public AddLinkRequest() {

    }

    public AddLinkRequest(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
