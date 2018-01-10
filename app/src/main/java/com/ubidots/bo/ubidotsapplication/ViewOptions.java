package com.ubidots.bo.ubidotsapplication;

public class ViewOptions {

    private String itemName;
    // Image name without capture file extension
    private String picture;
    private String description;

    public ViewOptions(String itemName, String picture, String description) {
        this.itemName= itemName;
        this.picture= picture;
        this.description = description;
    }
    // Get and Set methods
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString()  {
        return this.itemName+" (Description: "+ this.description +")";
    }

}
