package com.narwal.parvesh.best100tweetsandstatustogainfollowers;

/**
 * Created by Parvesh on 24-Dec-16.
 */

public class SingleCategory {

    private int imageId;
    private String categoryName;

    public SingleCategory(int imageId, String categoryName){
        this.imageId = imageId;
        this.categoryName = categoryName;
    }

    public int getImageId() {
        return imageId;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
