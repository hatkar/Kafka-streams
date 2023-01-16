package org.hatmani.Models;

import com.google.gson.annotations.SerializedName;

public class Tweetenriched {
    @SerializedName("CreatedAt")
    private Long createdAt;
    @SerializedName("Id")
    private Long id;
    @SerializedName("entity")
    private String entity;

    @SerializedName("Text")
    private String text;
    @SerializedName("sentiment_score")
    private Double sentimentscore;


    public Tweetenriched(Long createdAt, Long id, String entity, String text, Double sentimentscore) {
        this.createdAt = createdAt;
        this.id = id;
        this.entity = entity;
        this.text = text;
        this.sentimentscore = sentimentscore;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Double getSentimentscore() {
        return sentimentscore;
    }

    public void setSentimentscore(Double sentimentscore) {
        this.sentimentscore = sentimentscore;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
