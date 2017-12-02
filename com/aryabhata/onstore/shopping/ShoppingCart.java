package com.aryabhata.onstore.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aryabhata.onstore.R;
import com.aryabhata.onstore.utilities.DividerItemDecoration;
import com.aryabhata.onstore.utilities.FloatingActionButtonAryabhata;
import com.aryabhata.onstore.utilities.ProductDb;
import com.aryabhata.onstore.utilities.SqlLiteDbHelper;
import com.daimajia.swipe.util.Attributes;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

/**
 * Created by Team-Aryabhata on 2/27/2015.
 */

public class ShoppingCart extends Fragment {

    String ProductData;
    private String toast;
    /**
     * RecyclerView: The new recycler view replaces the list view. Its more modular and therefore we
     * must implement some of the functionality ourselves and attach it to our recyclerview.
     * <p/>
     * 1) Position items on the screen: This is done with LayoutManagers
     * 2) Animate & Decorate views: This is done with ItemAnimators & ItemDecorators
     * 3) Handle any touch events apart from scrolling: This is now done in our adapter's ViewHolder
     */

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;


    public static ArrayList<String> mDataSet = new ArrayList<String>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.shoppingcart, container, false);
        FloatingActionButtonAryabhata fab = (FloatingActionButtonAryabhata) rootView.findViewById(R.id.fabButton);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.shoppinglist_view);
        fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));

        final FloatingActionButtonAryabhata checkout = (FloatingActionButtonAryabhata) rootView.findViewById(R.id.checkout);
        checkout.setDrawableIcon(getResources().getDrawable(R.drawable.cart));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanFromFragment();
            }
        });

        // Layout Managers:
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Item Decorator:
        recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        recyclerView.setItemAnimator(new FadeInLeftAnimator());

        /* Total View - TextView */
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SqlLiteDbHelper dbHelper = new SqlLiteDbHelper(getActivity());
                //Toast.makeText(getActivity(), "Checkout" + dbHelper.TotalSum() , Toast.LENGTH_SHORT).show();

                new MaterialDialog.Builder(getActivity())
                        .title(R.string.app_name)
                        .content("Would you like to Checkout? \n Your total amount is Rs:" + dbHelper.TotalSum())
                        .positiveText(R.string.DialogSucess)
                        .negativeText(R.string.DialogFailure)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {


                                if((dbHelper.TotalSum()) > 0){
                                    String CheckoutTotal = String.valueOf(dbHelper.TotalSum());
                                    dbHelper.History_created(dbHelper.TotalSum());
                                    dbHelper.deleteRecords();
                                    Intent CheckOut = new Intent(getActivity(), CheckOut.class);
                                    CheckOut.putExtra("CheckoutKey", CheckoutTotal);
                                    startActivity(CheckOut);
                                    getActivity().finish();
                                }
                                else
                                {
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(), "You need to Shop something to Checkout" , Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

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


    public void scanFromFragment() {
        IntentIntegrator.forSupportFragment(this).initiateScan();
    }

    private void displayToast() {
        if(getActivity() != null && toast != null) {
            //Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
            toast = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                toast = "Cancelled from fragment";
            } else {
                toast = "Scanned from fragment: " + result.getContents();
                mDataSet.add(result.getContents());
                      /* Database test */
                SqlLiteDbHelper dbHelper = new SqlLiteDbHelper(getActivity());
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
                productdatabase = dbHelper.Get_ProductDetails(result.getContents());
                //Toast.makeText(getActivity(),"Total Count" + mAdapter.getItemCount(), Toast.LENGTH_SHORT).show();
                dbHelper.Set_ProductDetails(productdatabase);
            }
            // At this point we may or may not have a reference to the activity
            displayToast();
        }
    }


    public void setRecyclerView(RecyclerView recyclerView) {

        this.recyclerView = recyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = new RecyclerViewAdapter(getActivity(), mDataSet);
        ((RecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(mAdapter);



    }

    public String GetProductData(String QrData) {

        return ProductData;

    }
}
