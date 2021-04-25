package com.example.tema2.models;

import java.util.ArrayList;

public class Album extends Cell{
    private long userId;
    private long id;
    private String title;
    private ArrayList<Photo> photos;

    public Album(long userId, long id, String title) {
        super(CellType.ALBUM);
        this.userId = userId;
        this.id=id;
        this.title=title;
        photos = new ArrayList<Photo>();
    }

    public long getUserId() {
        return userId;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
