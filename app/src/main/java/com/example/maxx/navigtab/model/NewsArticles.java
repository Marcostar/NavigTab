package com.example.maxx.navigtab.model;

/**
 * Created by maxx on 22/5/15.
 */
public class NewsArticles {

    private String title;
    private String thumbnailUrl;
    private String content;
    private String newspaperName;
    private String articleLink;

    public NewsArticles()
    {

    }
    public NewsArticles(String title, String thumbnailUrl, String content, String newspaperName, String articleLink)
    {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        //this.content = content;
        this.newspaperName = newspaperName;
        //this.articleLink = articleLink;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setThumbnailUrl(String thumbnailUrl)
    {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

    public void setcontent(String content)
    {
        this.content = content;
    }

    public String getcontent()
    {
        return content;
    }

    public void setNewspaperName(String newspaperName)
    {
        this.newspaperName = newspaperName;
    }

    public String getNewspaperName()
    {
        return newspaperName;
    }

    public void setArticleLink(String articleLink)
    {
        this.articleLink = articleLink;
    }

    public String getArticleLink()
    {
        return articleLink;
    }

}
