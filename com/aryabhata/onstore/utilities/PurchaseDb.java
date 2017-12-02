package com.aryabhata.onstore.utilities;

/**
 * Created by admin on 3/11/2015.
 */
public class PurchaseDb {

    public static String Purchase_id="";
    public static String Purchase_amount="";

    public static String getPurchase_id() {
        return Purchase_id;
    }

    public void setPurchase_id(String PurchaseId) {
        this.Purchase_id = PurchaseId;
    }

    public String getPurchase_amount() {
        return Purchase_amount;
    }

    public void setPurchase_amount(String purchaseAmount) {
        this.Purchase_amount = purchaseAmount;
    }


    // constructor
    public PurchaseDb(String prod_barcode, String prod_name){
        this.Purchase_id = prod_barcode;
        this.Purchase_amount = prod_name;

    }

    public PurchaseDb(){

    }
}
