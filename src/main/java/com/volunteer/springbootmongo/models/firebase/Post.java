package com.volunteer.springbootmongo.models.firebase;


import com.google.type.DateTime;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Post {
    public enum type{TN, QG, HP}

    private String Owner;

    private String nameOwner;
    private String avtOwner;
    private String id;
    private String content;
    private String datecreated;
    private String title;
    private String subtitle;
    private String mainimage;
    private String address;
    private type type;

    private String timeago;

    private int totalMoney;

    private int currentMoney;

    private int totalUsers;

    private int currentUsers;

    private List<String> avtCurrentUsers;

    public String getNameOwner() {
        return nameOwner;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getAvtOwner() {
        return avtOwner;
    }

    public void setAvtOwner(String avtOwner) {
        this.avtOwner = avtOwner;
    }

    public List<String> getAvtCurrentUsers() {
        return avtCurrentUsers;
    }

    public void setAvtCurrentUsers(List<String> avtCurrentUsers) {
        this.avtCurrentUsers = avtCurrentUsers;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Post.type getType() {
        return type;
    }

    public void setType(Post.type type) {
        this.type = type;
    }

    public String getTimeago() {
        return timeago;
    }

    public void setTimeago(String timeago) {
        this.timeago = timeago;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(int currentMoney) {
        this.currentMoney = currentMoney;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(int currentUsers) {
        this.currentUsers = currentUsers;
    }

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

    public Post(String content, String title, String subtitle, String address ) {
        this.content = content;
        this.title = title;
        this.subtitle = subtitle;
        this.address = address;
        this.type = type;
    }

    public Post(String content, String title, String subtitle, String address, Post.type type, int totalMoney, int totalUsers) {
        this.content = content;
        this.title = title;
        this.subtitle = subtitle;
        this.address = address;
        this.type = type;
        this.totalMoney = totalMoney;
        this.totalUsers = totalUsers;
    }

    public Post() {}
}
