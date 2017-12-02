package com.aryabhata.onstore.history;


import android.app.Activity;
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
import com.aryabhata.onstore.utilities.PurchaseDb;
import com.aryabhata.onstore.utilities.SqlLiteDbHelper;
import com.daimajia.swipe.util.Attributes;

import java.sql.SQLException;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

/**
 * Created by Sainath on 2/27/2015.
 */
public class History extends Fragment{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    public static ArrayList<String> mDataSet = new ArrayList<String>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.history, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.shoppinglist_view);
        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Item Decorator:
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInLeftAnimator());
        /* Database Extraction */
        /*final FloatingActionButtonAryabhata fab_refresh = (FloatingActionButtonAryabhata) rootView.findViewById(R.id.fabrefresh);
        fab_refresh.setDrawableIcon(getResources().getDrawable(R.drawable.refresh));

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        }); */

        GetHistory();

        recyclerView.setOnScrollListener(onScrollListener);
        return rootView;
    }

    private void GetHistory() {

        SqlLiteDbHelper dbHelper = new SqlLiteDbHelper(getActivity());
        PurchaseDb purchasedatabase;
        try {
            dbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        purchasedatabase = new PurchaseDb();
        purchasedatabase =dbHelper.Get_HistoryDetails();

        if(PurchaseDb.Purchase_amount != null)
            mDataSet.add(PurchaseDb.Purchase_amount);
        mAdapter = new HistoryViewAdapter(getActivity(), mDataSet);
        ((HistoryViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(mAdapter);
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

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
