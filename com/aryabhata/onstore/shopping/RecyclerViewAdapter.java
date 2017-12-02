package com.aryabhata.onstore.shopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aryabhata.onstore.R;
import com.aryabhata.onstore.utilities.ProductDb;
import com.aryabhata.onstore.utilities.SqlLiteDbHelper;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by admin on 2/27/2015.
 */
public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder> {

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView textViewPos;
        TextView  productData, priceData, ratingData, categoryData;
        ImageView trash;
        ImageView favorite;
        ImageView magnifier;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            textViewPos = (TextView) itemView.findViewById(R.id.position);
            productData = (TextView) itemView.findViewById(R.id.product);
            priceData = (TextView) itemView.findViewById(R.id.price);
            ratingData = (TextView) itemView.findViewById(R.id.rating);
            categoryData = (TextView) itemView.findViewById(R.id.category);
            trash = (ImageView) itemView.findViewById(R.id.trash);
            favorite = (ImageView) itemView.findViewById(R.id.star);
            magnifier = (ImageView) itemView.findViewById(R.id.magnifier);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /* Do Something Specific related to Item */


                    //Toast.makeText(view.getContext(), "onItemSelected: " + productData.getText().toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(view.getContext(), "onItemSelected: " + priceData.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);
    private Context mContext;
    private ArrayList<String> mDataset;

    public RecyclerViewAdapter(Context context, ArrayList<String> objects) {
        this.mContext = context;
        this.mDataset = objects;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        String item = mDataset.get(position);

         /* Database test */
        SqlLiteDbHelper dbHelper = new SqlLiteDbHelper(mContext);
        ProductDb productdatabase;
        try {
            dbHelper.createDatabase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        productdatabase = new ProductDb();
        productdatabase = dbHelper.Get_ProductDetails(item);

        if(productdatabase == null)
        {
            productdatabase = dbHelper.Get_ProductDetails("1234");
        }

        /* Database Extraction */
        String productInfo = ProductDb.ProductName;
        String priceInfo = ProductDb.ProductPrice;
        String ratingInfo = ProductDb.ProductRating;
        String categoryInfo = ProductDb.ProductCategory;

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {

            @Override
            public void onStartOpen(SwipeLayout layout) {
            }

            @Override
            public void onOpen(SwipeLayout layout) {
               // YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
            }

            @Override
            public void onClose(SwipeLayout layout) {
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
            }

        });
        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);

                /* Database test */
                SqlLiteDbHelper dbHelper = new SqlLiteDbHelper(mContext);
                ProductDb productdatabase;
                try {
                    dbHelper.openDataBase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                productdatabase = new ProductDb();
                dbHelper.Delete_ProductDetails(position+1);

                mDataset.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mDataset.size());
                mItemManger.closeAllItems();
                Toast.makeText(view.getContext(), "Deleted " + viewHolder.productData.getText().toString() + "!"+position, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SqlLiteDbHelper dbHelper = new SqlLiteDbHelper(mContext);
                ProductDb productdatabase;
                try {
                    dbHelper.openDataBase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                productdatabase = new ProductDb();
                String UpdateResult = viewHolder.productData.getText().toString();
                dbHelper.Set_ProductFavoriteDetails(UpdateResult);
                productdatabase = dbHelper.Get_ProductDetails(UpdateResult);
                String ResultofFavorite = "Product Added as : " + ProductDb.ProductFavorite;
                Toast.makeText(view.getContext(), "Added to Favorites " + viewHolder.productData.getText().toString() + "!" + ResultofFavorite, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.magnifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "Search for Product " + viewHolder.productData.getText().toString() + "!", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.textViewPos.setText((position + 1) + ".");
        viewHolder.productData.setText(productInfo);
        viewHolder.priceData.setText(priceInfo);
        viewHolder.ratingData.setText(ratingInfo);
        viewHolder.categoryData.setText(categoryInfo);
        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
}