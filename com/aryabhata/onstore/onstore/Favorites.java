package com.aryabhata.onstore.onstore;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aryabhata.onstore.R;
import com.aryabhata.onstore.utilities.DividerItemDecoration;
import com.aryabhata.onstore.utilities.FloatingActionButtonAryabhata;
import com.aryabhata.onstore.utilities.ProductDb;
import com.aryabhata.onstore.utilities.SqlLiteDbHelper;
import com.daimajia.swipe.util.Attributes;

import java.sql.SQLException;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

/**
 * Created by Sainath on 2/27/2015.
 */
public class Favorites extends Fragment{


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    public static ArrayList<String> mDataSet = new ArrayList<String>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.favorites, container, false);
        FloatingActionButtonAryabhata fab = (FloatingActionButtonAryabhata) rootView.findViewById(R.id.fabButton);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.shoppinglist_view);
        fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Item Decorator:
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInLeftAnimator());

        recyclerView.setOnScrollListener(onScrollListener);
        return rootView;
    }

    /**
     * Substitute for our onScrollListener for RecyclerView
     */
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };


    @Override
    public void onResume() {
        super.onResume();

            /* Database Extraction */
        SqlLiteDbHelper dbHelper = new SqlLiteDbHelper(getActivity());
        ProductDb productdatabase;
        try {
            dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        productdatabase = new ProductDb();
        productdatabase = dbHelper.Get_FavoriteProductDetails();
        if(ProductDb.ProductBarCode != null)
            mDataSet.add(ProductDb.ProductBarCode);

        mAdapter = new FavoritesViewAdapter(getActivity(), mDataSet);
        ((FavoritesViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(mAdapter);
    }

}
