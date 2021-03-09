package com.example.shoppingapp.model;

public class Categories {

    public Integer getCat_Id() {
        return cat_Id;
    }

    public void setCat_Id(Integer cat_Id) {
        this.cat_Id = cat_Id;
    }

    Integer cat_Id;
    String cat_Name;
    Integer img_URL;

    public Categories(Integer cat_Id, String cat_Name, Integer img_URL) {
        this.cat_Id = cat_Id;
        this.cat_Name = cat_Name;
        this.img_URL = img_URL;
    }


    public String getCat_Name() {
        return cat_Name;
    }

    public void setCat_Name(String cat_Name) {
        this.cat_Name = cat_Name;
    }

    public Integer getImg_URL() {
        return img_URL;
    }

    public void setImg_URL(Integer img_URL) {
        this.img_URL = img_URL;
    }
}
