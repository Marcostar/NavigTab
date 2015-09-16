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

    public void setTopStoriesURL(String TopStoriesURL) {
        this.TopStoriesURL = TopStoriesURL;
    }

    public String getNationalURL() {
        return NationalURL;
    }

    public void setNationalURL(String NationalURL) {
        this.NationalURL = NationalURL;
    }

    public String getWorldURL() {
        return WorldURL;
    }

    public void setWorldURL(String WorldURL) {
        this.WorldURL = WorldURL;
    }

    public String getSportURL() {
        return SportURL;
    }

    public void setSportURL(String SportURL) {
        this.SportURL = SportURL;
    }

    public String getEntertainmentURL() {
        return EntertainmentURL;
    }

    public void setEntertainmentURL(String EntertainmentURL) {
        this.EntertainmentURL = EntertainmentURL;
    }

    public String getBusinessURL() {
        return BusinessURL;
    }

    public void setBusinessURL(String BusinessURL) {
        this.BusinessURL = BusinessURL;
    }
}
