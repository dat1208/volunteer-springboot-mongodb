package com.volunteer.springbootmongo.models.firebase;


import lombok.Data;

import java.util.Date;

@Data
public class ImageModel {

    private String userName;
    private String url;
    private Date dateCreated;
    private Date dateUpdated;

    public ImageModel(String userName, String url, Date dateCreated, Date dateUpdated) {
        this.userName = userName;
        this.url = url;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }
}
