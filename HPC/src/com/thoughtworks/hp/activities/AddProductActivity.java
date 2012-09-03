package com.thoughtworks.hp.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.thoughtworks.hp.R;
import com.thoughtworks.hp.adapters.BuyListAdapter;
import com.thoughtworks.hp.adapters.ProductListAdapter;
import com.thoughtworks.hp.datastore.ProductTable;
import com.thoughtworks.hp.models.Product;
import com.thoughtworks.hp.models.ShoppingList;
import com.thoughtworks.hp.models.ShoppingListProduct;
import com.thoughtworks.hp.presenters.ShoppingListPresenter;
import com.thoughtworks.hp.services.ImageProcessing;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddProductActivity extends Activity implements TextWatcher {

    private ShoppingListPresenter shoppingListScreen;

    private ProductListAdapter autoSuggestAdapter;
    private ListView autoSuggestListView;
    private List<Product> autoSuggestedProductList = new ArrayList<Product>();

    private BuyListAdapter shoppingListProductAdapter;
    private List<ShoppingListProduct> toBuyProductList = new ArrayList<ShoppingListProduct>();
    private long shoppingListId;

	private BarcodeScanner barcodeScanner;

    private TextView costOfShoppingList;
    private TextView distinctProductCountLabel;
    private TextView totalProductUnitCountLabel;

	private ImageCapturer imageCapturer;

	private static int count=0;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.shopping_list_detail_listing);

        this.shoppingListId = getIntent().getLongExtra(ShoppingList.SHOPPING_LIST_ID, 1);
        shoppingListScreen = new ShoppingListPresenter(ShoppingList.findById(shoppingListId));

        bindBarcodeScanner();
        bindToolBarComponents();
        initToBuyListView();
        initAutoSuggestListView();
        refreshProductRelatedUIElements();
        attachSelfAsTextWatcherToSearchBox();
        bindImageCapturer();
        if(shoppingListScreen.totalDistinctProductsCount() == 0) makeVisible(this.findViewById(R.id.search_product_box));
    }

    private void bindImageCapturer() {
			imageCapturer =new ImageCapturer(this, (ImageView) this.findViewById(R.id.capture_button));
		
	}

	private void bindToolBarComponents() {
        bindBackButtonOnToolBar();
        bindToggleButtonOnToolBar();
    }

    private void bindToggleButtonOnToolBar() {
        ImageView backButton = (ImageView) this.findViewById(R.id.add_product_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            
        	@Override
            public void onClick(View view) {
                toggleSearchBoxVisibility();
            }
        });
    }

    private void bindBackButtonOnToolBar() {
        ImageView backButton = (ImageView) this.findViewById(R.id.back_to_shopping_listing_button);
        backButton.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View view) {
                AddProductActivity.this.finish();
            }
        });
    }

    private void bindBarcodeScanner() {
        barcodeScanner = new BarcodeScanner(this, (ImageView) this.findViewById(R.id.scan_upc_button));
    }

    private void toggleSearchBoxVisibility() {
        View searchBox = this.findViewById(R.id.search_product_box);
        int visibility = searchBox.getVisibility();
        if(visibility == View.VISIBLE)
            makeViewBeGone(searchBox);
        else
            makeVisible(searchBox);
    }

    private void makeVisible(View viewComponent) {
        viewComponent.setVisibility(View.VISIBLE);
    }

    private void makeViewBeGone(View viewComponent) {
        viewComponent.setVisibility(View.GONE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	 if(requestCode==49374)
         {
         	Product productFromBarcode = barcodeScanner.fetchProductFromBarcodeData(requestCode, resultCode, intent);
             if(productFromBarcode != null) {
                 addAndPersistProductInShoppingList(productFromBarcode);
             }
         }
         if(resultCode==RESULT_CANCELED){
         	resetCompleteView();
         }
         
         if(requestCode==100 && resultCode == RESULT_OK)
         {
        	deleteImageFromGallery(getLastImageId());
        	String fileName= "Picture_" + count++ + ".jpg";
         	resetCompleteView();
         	showMessageToUser(fileName);
         	bindImageCapturer();
         	Intent serviceIntent=new Intent(this,ImageProcessing.class);
     	    String filePath = Environment.getExternalStorageDirectory()+"/"+fileName;
 			serviceIntent.putExtra("filename",filePath);
 			serviceIntent.putExtra("handler", new Messenger(this.handler));
 			this.startService(serviceIntent);
         }
         

    }


	private void showMessageToUser(String fileName) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Identifying the product...", Toast.LENGTH_LONG).show();

		
	}

	private void initToBuyListView() {
        this.toBuyProductList.addAll(shoppingListScreen.shoppingListProducts());
        this.shoppingListProductAdapter = new BuyListAdapter(this, R.layout.product_line_item, toBuyProductList);
        ListView toBuyProductListView = (ListView) this.findViewById(R.id.shopping_list_product_listing);
        toBuyProductListView.setAdapter(this.shoppingListProductAdapter);
//        toBuyProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                toggleItemsCompletenessByChangingColor(position);
//                shoppingListProductAdapter.notifyDataSetChanged();
//            }
//        });
    }

    private void toggleItemsCompletenessByChangingColor(long position) {
    	
        shoppingListScreen.toggleCompletenessForItemAt(position);
    }

    private void initAutoSuggestListView() {
        this.autoSuggestAdapter = new ProductListAdapter(this, R.layout.product_auto_suggest_line_item, autoSuggestedProductList);
        this.autoSuggestListView = (ListView) this.findViewById(R.id.auto_suggest_list);
        autoSuggestListView.setAdapter(this.autoSuggestAdapter);
        autoSuggestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
        	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Product product = autoSuggestedProductList.get(position);
                addAndPersistProductInShoppingList(product);
            }
        });
    }

    private void addAndPersistProductInShoppingList(Product product) {
        shoppingListScreen.addSelectedProductToShoppingList(product);
        resetCompleteView();
    }

    private void refreshProductRelatedUIElements() {
        costOfShoppingList = (TextView) findViewById(R.id.total_amount_value_text);
        costOfShoppingList.setText(shoppingListScreen.totalShoppingListCostLabel());

        distinctProductCountLabel = (TextView) findViewById(R.id.list_detail_item_list_count_text);
        distinctProductCountLabel.setText(shoppingListScreen.totalDistinctProductsLabel());

        totalProductUnitCountLabel = (TextView) findViewById(R.id.list_detail_total_item_unit_value);
        totalProductUnitCountLabel.setText(shoppingListScreen.totalProductsUnitCountLabel());
    }

    private void resetCompleteView() {
        resetToBuyList();
        refreshProductRelatedUIElements();
        resetAutoSuggestList();
    }

    private void resetAutoSuggestList() {
        autoSuggestedProductList.clear();
        autoSuggestAdapter.notifyDataSetChanged();
        ((EditText)AddProductActivity.this.findViewById(R.id.search_product_box)).setText("");
        autoSuggestListView.setVisibility(View.INVISIBLE);
    }

    private void resetToBuyList() {
        toBuyProductList.clear();
        toBuyProductList.addAll(shoppingListScreen.shoppingListProducts());
        shoppingListProductAdapter.notifyDataSetChanged();
    }

    private void attachSelfAsTextWatcherToSearchBox() {
        ((EditText)this.findViewById(R.id.search_product_box)).addTextChangedListener(this);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (charSequence == null || charSequence.length() < 1) return;

        autoSuggestListView.setVisibility(View.VISIBLE);
        autoSuggestedProductList.clear();
        autoSuggestedProductList.addAll(Product.findProductsByMatchingName(charSequence.toString()));
        clearAlreadyAddedProductsFrom(autoSuggestedProductList);
        autoSuggestAdapter.notifyDataSetChanged();
    }

    private void clearAlreadyAddedProductsFrom(List<Product> autoSuggestedProductList) {
        for(ShoppingListProduct shoppingListProduct : toBuyProductList) {
            autoSuggestedProductList.remove(shoppingListProduct.product());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) { }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) { }

    Handler handler = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    		String barcodeId= msg.getData().getString("barcodeID");
    		if(findByBarcodeID(barcodeId)== null)
    		{
    			Toast.makeText(getApplicationContext(), "Could not find the matching product", Toast.LENGTH_LONG).show();
    			return;
    		}
    		addAndPersistProductInShoppingList(findByBarcodeID(barcodeId));
    		
    	}
    };
    private Product findByBarcodeID(String barcodeId) {
    	if(barcodeId == null) return null;
		Product product = new ProductTable().findByBarcodeId(barcodeId);
		return product;
	}
    
    public void deleteImageFromGallery(String captureimageid){
    	 Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    	 
    	 getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, BaseColumns._ID+"=?", new String[] {captureimageid});
    	 
    	 
    	 Log.i("InfoLog", "on activityresult Uri u " + u.toString());
    	 
    	}
    
    private String getLastImageId(){
        final String[] imageColumns = { MediaStore.Images.Media._ID };
        final String imageOrderBy = MediaStore.Images.Media._ID+" DESC";
        final String imageWhere = null;
        final String[] imageArguments = null;
        Cursor imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, imageWhere, imageArguments, imageOrderBy);
        if(imageCursor.moveToFirst()){
            String id = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
            imageCursor.close();
            return id;
        }else{
            return null;
        }
    }


}