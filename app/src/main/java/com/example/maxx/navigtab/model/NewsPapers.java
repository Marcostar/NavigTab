package com.example.maxx.navigtab.model;

/**
 * Created by maxx on 23/6/15.
 */
public class NewsPapers {
    private String newsPaperName;
    private String TopStoriesURL;
    private String NationalURL;
    private String WorldURL;
    private String SportURL;
    private String EntertainmentURL;
    private String BusinessURL;

    public NewsPapers()
    {

    }

    public NewsPapers(String newsPaperName, String topStoriesURL, String nationalURL, String worldURL, String sportURL, String entertainmentURL, String businessURL) {
        this.newsPaperName = newsPaperName;
        TopStoriesURL = topStoriesURL;
        NationalURL = nationalURL;
        WorldURL = worldURL;
        SportURL = sportURL;
        EntertainmentURL = entertainmentURL;
        BusinessURL = businessURL;
    }


    public String getNewsPaperName() {
        return newsPaperName;
    }

    public void setNewsPaperName(String newsPaperName) {
        this.newsPaperName = newsPaperName;
    }

    public String getTopStoriesURL() {
        return TopStoriesURL;
    }

    public void setTopStoriesURL(String topStoriesURL) {
        TopStoriesURL = topStoriesURL;
    }

    public String getNationalURL() {
        return NationalURL;
    }

    public void setNationalURL(String nationalURL) {
        NationalURL = nationalURL;
    }

    public String getWorldURL() {
        return WorldURL;
    }

    public void setWorldURL(String worldURL) {
        WorldURL = worldURL;
    }

    public String getSportURL() {
        return SportURL;
    }

    public void setSportURL(String sportURL) {
        SportURL = sportURL;
    }

    public String getEntertainmentURL() {
        return EntertainmentURL;
    }

    public void setEntertainmentURL(String entertainmentURL) {
        EntertainmentURL = entertainmentURL;
    }

    public String getBusinessURL() {
        return BusinessURL;
    }

    public void setBusinessURL(String businessURL) {
        BusinessURL = businessURL;
    }
}
