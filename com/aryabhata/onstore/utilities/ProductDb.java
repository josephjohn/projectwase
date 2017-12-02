package com.aryabhata.onstore.utilities;

/**
 * Created by admin on 3/6/2015.
 */
public class ProductDb {

    public static String ProductBarCode = "";
    public static String ProductName = "";
    public static String ProductRating = "";
    public static String ProductPrice = "";
    public static String ProductCategory = "";
    public static String ProductFavorite="";

    public static String getProductBarCode() {
        return ProductName;
    }

    public void setProductName(String prod_name) {
        this.ProductName = prod_name;
    }

    public String getProductRating() {
        return ProductRating;
    }

    public void setProductRating(String prod_rating) {
        this.ProductRating = prod_rating;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String prod_price) {
        this.ProductPrice = prod_price;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String prod_category) {
        this.ProductCategory = prod_category;
    }

    public String getProductFavorite() {
        return ProductFavorite;
    }

    public void setProductFavorite(String prod_favorite) {
        this.ProductFavorite = prod_favorite;
    }

    // constructor
    public ProductDb(String prod_barcode, String prod_name, String prod_rating, String prod_price, String prod_category,String prod_favorite){
        this.ProductBarCode = prod_barcode;
        this.ProductName = prod_name;
        this.ProductRating = prod_rating;
        this.ProductPrice = prod_price;
        this.ProductCategory = prod_category;
        this.ProductFavorite = prod_favorite;
    }

    public ProductDb(){

    }

}
