package com.mylink.mylinkgenerator.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity Class for storing URL info
 */
public class TinyUrl {
    private String url;

    @JsonCreator
    public TinyUrl(){

    }

    @JsonCreator
    public TinyUrl(@JsonProperty("url") String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
