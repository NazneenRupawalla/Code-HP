package com.thoughtworks.hp.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import com.thoughtworks.hp.datastore.ProductTable;
import com.thoughtworks.hp.models.Product;
import com.thoughtworks.hp.zxing.integration.android.IntentIntegrator;
import com.thoughtworks.hp.zxing.integration.android.IntentResult;

public class BarcodeScanner {

    private Activity activity;
    private static final String TAG = BarcodeScanner.class.getSimpleName();

    public BarcodeScanner(Activity activity, ImageView scanButton) {
        this.activity = activity;
        bindBarcodeScanningTo(scanButton);
    }

    public Product fetchProductFromBarcodeData(int requestCode, int resultCode, Intent intent) {
        Product productToReturn = null;
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult.getContents() != null) {
            String barcodeId = scanResult.getContents();
            productToReturn = findProductFromBarcodeId(barcodeId);
        }

        return productToReturn;
    }

    private Product findProductFromBarcodeId(String barcodeId) {
        Product product = new ProductTable().findByBarcodeId(barcodeId);
        return product;
    }

    private void bindBarcodeScanningTo(ImageView scanButton) {
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanBarcode();
            }
        });
    }

    private void scanBarcode() {
        IntentIntegrator scanIntent = new IntentIntegrator(activity);
        scanIntent.initiateScan();
    }
}
