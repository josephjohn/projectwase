package com.aryabhata.onstore.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by admin on 3/6/2015.
 */
public class SqlLiteDbHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_PATH = "/data/data/com.aryabhata.onstore/databases/";
    // Database Name
    private static final String DATABASE_NAME = "OnStore";
    // Product table name
    private static final String TABLE_PRODUCTS = "OnStoreProducts";
    private static final String TABLE_CART = "ShoppingCart";
    private static final String HISTORY = "History";

    private SQLiteDatabase db;
    // Product Table Columns names
    private static final String ProductBarCode = "ProductBarCode";
    private static final String ProductName = "ProductName";
    private static final String ProductRating = "ProductRating";
    private static final String ProductPrice = "ProductPrice";
    private static final String ProductCategory = "ProductCategory";
    private static final  String ProductFavorite="ProductFavorite";
    private static final  String HISTORY_ID="Purchase_id";
    private static final  String PURCHASE_AMOUNT="Purchase_amount";
    Context ctx;

    public SqlLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
        Log.i("SqlLiteDbHelper", "DataBase:"+ DATABASE_PATH + DATABASE_NAME);
    }

    // Getting single contact
    public ProductDb Get_ProductDetails(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTS, new String[] { ProductBarCode,
                        ProductName, ProductRating, ProductPrice,ProductCategory,ProductFavorite }, ProductBarCode + "=?",
                new String[] { name }, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()){
            ProductDb item = new ProductDb(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));

            // Return contact
            cursor.close();
            db.close();
            return item;
        }
        return null;
    }

    public ProductDb Get_FavoriteProductDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        int favorite_set = 1;
        /* SELECT * FROM OnStoreProducts WHERE ProductFavorite = 1 */
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[] { ProductBarCode,
                        ProductName, ProductRating, ProductPrice,ProductCategory,ProductFavorite }, ProductFavorite + "=?",
                new String[] { String.valueOf(favorite_set) }, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            ProductDb item = new ProductDb(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            // Return contact
            cursor.close();
            db.close();
            return item;
        }
        return null;
    }

    public void Delete_ProductDetails(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_CART,  +  "=" + id, null)
        db.execSQL("delete from "+TABLE_CART+" where ProductIndex="+id);
        //Toast.makeText(this, "Deleted id from databse!"+ id, Toast.LENGTH_SHORT).show();
        Toast.makeText(ctx,"Deleted id from database:"+id,Toast.LENGTH_SHORT);
        db.close();
    }


    public int TotalSum(){
        SQLiteDatabase db = this.getReadableDatabase();
        int Total = 0;
        Cursor cursor = db.rawQuery(
                "SELECT SUM(ProductPrice) FROM ShoppingCart", null);

        if(cursor.moveToFirst()) {
            Total = cursor.getInt(0);
        }

            cursor.close();
            db.close();
        return Total;
    }

    public void createDatabase() throws IOException {
        Log.i("SqlLiteDbHelper", "In CreateDatabase:"+ DATABASE_PATH + DATABASE_NAME);
        createDB();
    }

    private void createDB() throws IOException {

        boolean dbExist = DBExists();
        if (!dbExist) {
            Log.i("SqlLiteDbHelper", "Copy Resource:"+ DATABASE_PATH + DATABASE_NAME);
            this.getReadableDatabase();
            copyDBFromResource();
        }
        Log.i("SqlLiteDbHelper", "DBExists:"+ DATABASE_PATH + DATABASE_NAME);
    }

    private boolean DBExists() {
        return ctx.getDatabasePath(DATABASE_PATH+DATABASE_NAME).exists();
    }

    private void copyDBFromResource() throws IOException {
        File dbFile = new File(DATABASE_PATH+DATABASE_NAME);
        Log.i("SqlLiteDbHelper", "File -->:"+ dbFile.getAbsolutePath());
        InputStream inputStream = null;
        OutputStream outStream = null;
        File dbFilePath = new File(DATABASE_PATH);

        // Check if databases folder exists, if not create one and its subfolders
        if (!dbFilePath.exists()){
            dbFilePath.mkdir();
        }

        try {
            Log.i("SqlLiteDbHelper", "CopyDataBase :"+ ctx.getDatabasePath(DATABASE_NAME));
            inputStream = ctx.getAssets().open(DATABASE_NAME);
            outStream = new FileOutputStream(ctx.getDatabasePath(DATABASE_NAME));
            Log.i("SqlLiteDbHelper", "CopyDataBase :"+ ctx.getDatabasePath(DATABASE_NAME));
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }

            outStream.flush();
            outStream.close();
            inputStream.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void openDataBase () throws SQLException{
        String path = DATABASE_PATH+DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    public void Set_ProductFavoriteDetails(String updateResult) {

        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE OnStoreProducts SET ProductFavorite = 1 WHERE ProductName ='" + updateResult + "'";
        db.execSQL(strSQL);
        db.close();
    }

    public void Set_ProductDetails(ProductDb cart_item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues item = new ContentValues();

        item.put("ProductBarCode", cart_item.ProductBarCode);
        item.put("ProductName", cart_item.ProductName);
        item.put("ProductRating", cart_item.ProductRating);
        item.put("ProductPrice", cart_item.ProductPrice);
        item.put("ProductCategory", cart_item.ProductCategory);
        item.put("ProductFavorite", cart_item.ProductFavorite);

        db.insert(TABLE_CART, null, item);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }


    public void deleteRecords() {

        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_CART,  +  "=" + id, null)
        db.execSQL("delete from "+TABLE_CART);

        //Toast.makeText(ctx, "All entries are deleted from Shopping Cart", Toast.LENGTH_SHORT);
        db.close();
    }

    public void History_created(int Purcahse_amount) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues Purcahse_item = new ContentValues();

        Purcahse_item.put("Purchase_amount", Purcahse_amount);

        db.insert(HISTORY, null, Purcahse_item);
        //Toast.makeText(ctx, "All Purchase entries are inserted in History", Toast.LENGTH_SHORT);
        db.close();
    }

    public PurchaseDb Get_HistoryDetails() {
        SQLiteDatabase db = this.getReadableDatabase();

        /* SELECT * FROM OnStoreProducts WHERE ProductFavorite = 1 */
        Cursor cursor = db.query(HISTORY, new String[] { HISTORY_ID,
                        PURCHASE_AMOUNT }, null,
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            PurchaseDb item = new PurchaseDb(cursor.getString(0), cursor.getString(1));
            // Return contact
            cursor.close();
            db.close();
            return item;
        }
        return null;
    }

}

