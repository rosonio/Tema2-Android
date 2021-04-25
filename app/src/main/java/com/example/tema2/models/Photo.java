package com.example.tema2.models;

public class Photo {
    private long albumId;
    private long id;
    private String title;
    private String url;

    public Photo(long albumId, long id, String title, String url, String thumbnailUrl){
        this.albumId=albumId;
        this.id=id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.url=url;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitile() {
        return title;
    }

    public void setTitile(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    private String thumbnailUrl;
}
