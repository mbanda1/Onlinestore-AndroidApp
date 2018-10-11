package com.cyphertech.biashara.Utility;


public class Categories_ItemObject {
    private String categoryName;
    private String categoryPicture;


    public Categories_ItemObject(String categoryName, String categoryPicture)
    {

        this.categoryName = categoryName;
        this.categoryPicture = categoryPicture;

    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryPicture(String categoryPicture) {
        this.categoryPicture = categoryPicture;
    }

    public String getCategoryPicture() {
        return categoryPicture;
    }


}
