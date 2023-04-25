/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.10"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Links implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String url;
    private OffsetDateTime lastUpdateDate;
    private Integer idChat;

    public Links() {}

    public Links(Links value) {
        this.id = value.id;
        this.url = value.url;
        this.lastUpdateDate = value.lastUpdateDate;
        this.idChat = value.idChat;
    }

    @ConstructorProperties({ "id", "url", "lastUpdateDate", "idChat" })
    public Links(
        @NotNull Integer id,
        @NotNull String url,
        @NotNull OffsetDateTime lastUpdateDate,
        @Nullable Integer idChat
    ) {
        this.id = id;
        this.url = url;
        this.lastUpdateDate = lastUpdateDate;
        this.idChat = idChat;
    }

    /**
     * Getter for <code>LINKS.ID</code>.
     */
    @NotNull
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>LINKS.ID</code>.
     */
    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    /**
     * Getter for <code>LINKS.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 1000)
    @NotNull
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>LINKS.URL</code>.
     */
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    /**
     * Getter for <code>LINKS.LAST_UPDATE_DATE</code>.
     */
    @NotNull
    public OffsetDateTime getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
     * Setter for <code>LINKS.LAST_UPDATE_DATE</code>.
     */
    public void setLastUpdateDate(@NotNull OffsetDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Getter for <code>LINKS.ID_CHAT</code>.
     */
    @Nullable
    public Integer getIdChat() {
        return this.idChat;
    }

    /**
     * Setter for <code>LINKS.ID_CHAT</code>.
     */
    public void setIdChat(@Nullable Integer idChat) {
        this.idChat = idChat;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Links other = (Links) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.url == null) {
            if (other.url != null)
                return false;
        }
        else if (!this.url.equals(other.url))
            return false;
        if (this.lastUpdateDate == null) {
            if (other.lastUpdateDate != null)
                return false;
        }
        else if (!this.lastUpdateDate.equals(other.lastUpdateDate))
            return false;
        if (this.idChat == null) {
            if (other.idChat != null)
                return false;
        }
        else if (!this.idChat.equals(other.idChat))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.lastUpdateDate == null) ? 0 : this.lastUpdateDate.hashCode());
        result = prime * result + ((this.idChat == null) ? 0 : this.idChat.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Links (");

        sb.append(id);
        sb.append(", ").append(url);
        sb.append(", ").append(lastUpdateDate);
        sb.append(", ").append(idChat);

        sb.append(")");
        return sb.toString();
    }
}
