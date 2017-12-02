package com.aryabhata.onstore.shopping;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aryabhata.onstore.R;
import com.aryabhata.onstore.utilities.BabushkaText;
import com.aryabhata.onstore.utilities.FloatingActionButtonAryabhata;
import com.aryabhata.onstore.utilities.ProgressGenerator;
import com.dd.processbutton.iml.ActionProcessButton;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 * Created by Sainath on 3/10/2015.
 */
public class CheckOut extends Activity implements ProgressGenerator.OnCompleteListener{

        final String TAG = getClass().getName();

        private TextView resultTextView;

        private int MY_SCAN_REQUEST_CODE = 100; // arbitrary int

        ProgressGenerator progressGenerator;
        ActionProcessButton btnPay;

        /**
         * Called when the activity is first created.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.checkout);

            FloatingActionButtonAryabhata cardscan = (FloatingActionButtonAryabhata) findViewById(R.id.cardscan);
            cardscan.setDrawableIcon(getResources().getDrawable(R.drawable.creditcard));
            String CheckoutKeydata = getIntent().getStringExtra("CheckoutKey");


            BabushkaText babushka = (BabushkaText) findViewById(R.id.resultTextView);

            babushka.addPiece(new BabushkaText.Piece.Builder("Total Amount is Rs: ")
                    .textColor(Color.parseColor("#000000"))
                    .textSize(40)
                    .build());

            babushka.addPiece(new BabushkaText.Piece.Builder(CheckoutKeydata)
                    .textColor(Color.parseColor("#000000"))
                    .textSize(40)
                    .textSizeRelative(0.9f)
                    .build());

            babushka.addPiece(new BabushkaText.Piece.Builder("\nThank you for Shopping")
                    .textColor(Color.parseColor("#000000"))
                    .textSize(30)
                    .textSizeRelative(0.9f)
                    .build());

            babushka.display();

            progressGenerator = new ProgressGenerator(this);
            btnPay = (ActionProcessButton) findViewById(R.id.btnCheckout);
            btnPay.setMode(ActionProcessButton.Mode.ENDLESS);
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressGenerator.start(btnPay);
                    btnPay.setEnabled(false);
                }
            });





          //  resultTextView.setText("card.io library version: " + CardIOActivity.sdkVersion() + "\nBuilt: " + CardIOActivity.sdkBuildDate());
        }

        @Override
        protected void onResume() {
            super.onResume();

        }

        public void onScanPress(View v) {
            // This method is set up as an onClick handler in the layout xml
            // e.g. android:onClick="onScanPress"

            Intent scanIntent = new Intent(this, CardIOActivity.class);

            // customize these values to suit your needs.
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
            scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

            // hides the manual entry button
            // if set, developers should provide their own manual entry mechanism in the app
            scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default: false

            // matches the theme of your application
            scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, false); // default: false

            // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
            startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            String resultStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            } else {
                resultStr = "Scan was canceled.";
            }
           // resultTextView.setText(resultStr);

        }

    @Override
    public void onComplete() {

        btnPay.setEnabled(true);

        new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(R.string.CheckoutThanks)
                .positiveText(R.string.QRCode)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        // ImageView to display the QR code in.  This should be defined in
                        // your Activity's XML layout file
                        GenerateQR();
                    }
                })
                .show();

    }

    public void GenerateQR()
    {
        Intent QrCode = new Intent(CheckOut.this,QRCodeActivity.class);
        startActivity(QrCode);
        finish();
    }
}
