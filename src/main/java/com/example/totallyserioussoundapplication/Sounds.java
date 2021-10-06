package com.example.totallyserioussoundapplication;

import android.net.Uri;

public class Sounds {
    private String songName;
    private Uri songUri;
    private String songLink;
    private int imageId;
    private int position;
    private int soundId;
    private boolean isFav;



    public Sounds(String songName, Uri songUri, String songLink,int imageId,int soundId) {
        this.songName = songName;
        this.songUri = songUri;
        this.songLink = songLink;
        this.imageId = imageId;
        this.soundId=soundId;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Uri getSongUri() {
        return songUri;
    }

    public void setSongUri(Uri songUri) {
        this.songUri = songUri;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }
}
