package com.example.maxx.navigtab.model;

/**
 * Created by Dzeko on 8/22/2015.
 */
public class Quotes {
    private int ID;
    private String Quote;
    private String TimeStamp;

    public Quotes() {
    }

    public Quotes(String quote, String timeStamp) {
        Quote = quote;
        TimeStamp = timeStamp;
    }

    public Quotes(int ID, String quote) {
        this.ID = ID;
        Quote = quote;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getQuote() {
        return Quote;
    }

    public void setQuote(String quote) {
        Quote = quote;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }
}
