package com.aryabhata.onstore.shopping;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aryabhata.onstore.R;
import com.aryabhata.onstore.feedback.FeedBackWebActivity;
import com.aryabhata.onstore.utilities.FloatingActionButtonAryabhata;
import com.aryabhata.onstore.utilities.ProgressGenerator;
import com.aryabhata.onstore.utilities.QRCodeEncoder;
import com.dd.processbutton.iml.ActionProcessButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.androidlegacy.Contents;

/**
 * Created by Sainath on 3/11/2015.
 */
public class QRCodeActivity extends Activity implements ProgressGenerator.OnCompleteListener {


    ProgressGenerator progressGenerator;
    ActionProcessButton btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.qrcode);

        ImageView imageView = (ImageView) findViewById(R.id.qrCode);

        String qrData = "UniqueOrderID";
        int qrCodeDimention = 500;

        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrData, null,
                Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        FloatingActionButtonAryabhata feedback = (FloatingActionButtonAryabhata) findViewById(R.id.feedback);
        feedback.setDrawableIcon(getResources().getDrawable(R.drawable.feedback));



        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedbackweb = new Intent(QRCodeActivity.this, FeedBackWebActivity.class);
                startActivity(feedbackweb);
            }
        });

        progressGenerator = new ProgressGenerator(this);
        btnPay = (ActionProcessButton) findViewById(R.id.btnDismiss);
        btnPay.setMode(ActionProcessButton.Mode.ENDLESS);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(btnPay);
                btnPay.setEnabled(false);
            }
        });


    }

    @Override
    public void onComplete() {
        finish();
    }
}
