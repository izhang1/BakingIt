package nanodegree.izhang.bakingit.Model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by ivanzhang on 11/2/17.
 *
 * Model of the step by step instructions for each recipe.
 *
 */

public class Step extends RealmObject implements Serializable {
    private String id;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;

    public Step(){};

    public Step(String id, String shortDescription, String description, String videoUrl, String thumbnailUrl){
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }



}
