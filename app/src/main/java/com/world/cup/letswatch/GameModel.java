package com.world.cup.letswatch;

import org.json.JSONArray;
import org.json.JSONObject;

public class GameModel {
    private JSONObject competition;
    private JSONArray videos;
    private String title, embed, url,thumbnail,date;

    public GameModel(JSONObject competition, JSONArray videos, String title, String embed, String url, String thumbnail, String date) {
        this.competition = competition;
        this.videos = videos;
        this.title = title;
        this.embed = embed;
        this.url = url;
        this.thumbnail = thumbnail;
        this.date = date;
    }

    public JSONObject getCompetition() {
        return competition;
    }

    public void setCompetition(JSONObject competition) {
        this.competition = competition;
    }

    public JSONArray getVideos() {
        return videos;
    }

    public void setVideos(JSONArray videos) {
        this.videos = videos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
