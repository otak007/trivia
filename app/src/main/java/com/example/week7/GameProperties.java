package com.example.week7;

import java.io.Serializable;

public class GameProperties implements Serializable {
    String name, url;
    int difficulty;

    // constructor
    public GameProperties(String name, String url, int difficulty) {
        this.name = name;
        this.url = url;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
