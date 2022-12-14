package com.volunteer.springbootmongo.models.firebase;


import com.google.type.DateTime;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
public class Post {
    public enum type{TN, QG, HP}
    private String id;
    private String content;
    private String datecreated;
    private String title;
    private String subtitle;
    private String mainimage;
    private String address;
    private type type;
    private String timeago;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainimage() {
        return mainimage;
    }

    public void setMainimage(String mainimage) {
        this.mainimage = mainimage;
    }

    public Post() {}
}
