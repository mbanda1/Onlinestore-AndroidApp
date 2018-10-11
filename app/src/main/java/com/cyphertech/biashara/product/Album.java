package com.cyphertech.biashara.product;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

// @Parcel
public class Album implements Parcelable{

    protected Album(Parcel in) {
        brandId = in.readString();
        brandCategory = in.readString();
        name = in.readString();
        productImage = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        if (in.readByte() == 0) {
            finalPrice = null;
        } else {
            finalPrice = in.readDouble();
        }
        productSpecification = in.readString();
        productDescription = in.readString();
        quantity = in.readInt();
    }

    public static final Creator <Album> CREATOR = new Creator <Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brandId);
        dest.writeString(brandCategory);
        dest.writeString(name);
        dest.writeString(productImage);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        if (finalPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(finalPrice);
        }
        dest.writeString(productSpecification);
        dest.writeString(productDescription);
        dest.writeInt(quantity);
    }

    private String brandId;
    private String brandCategory;
    private String name;
    private String productImage;
    private Double price;
    private Double finalPrice;
    private String productSpecification;
    private String productDescription;
    private int quantity;



   public Album(String brandId, String brandCategory, String name, String productImage, Double price, String productSpecification, String productDescription ) {
        this.brandId = brandId;
       this.brandCategory = brandCategory;
        this.name = name;
        this.productImage = productImage;
        this.price = price;
        this.productSpecification = productSpecification;
        this.productDescription = productDescription;

     }

    public Album(String brandId, String brandCategory, String name, String productImage, Double price,
                 String productSpecification, String productDescription, int quantity, Double finalPrice) {
        this.brandId = brandId;
        this.brandCategory = brandCategory;
        this.name = name;
        this.productImage = productImage;
        this.price = price;
        this.productSpecification = productSpecification;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.finalPrice = finalPrice;

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    //88888888888888888888888888888//
  /*   public Album(int quantity, Double price1) {
       this.quantity = quantity;
       this.price = price1;
     }

    public Double getPrice1() {
        return price1 * quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
     */


    //************************//


    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandCategory() {
        return brandCategory;
    }

    public void setBrandCategory(String brandCategory) {
        this.brandCategory = brandCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price ;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }


    //method to get order List for posting
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("brand_id", brandId);
            obj.put("category", brandCategory);
            obj.put("name", name);
            obj.put("image", productImage);
            obj.put("pricePerItem", price);
            obj.put("quantityPerItem", quantity);
            obj.put("totalPriceperItem", finalPrice);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
