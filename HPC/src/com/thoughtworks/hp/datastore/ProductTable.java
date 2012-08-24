package com.thoughtworks.hp.datastore;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.util.Log;
import com.thoughtworks.hp.HypercityApplication;
import com.thoughtworks.hp.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductTable implements Table<Product> {

    private static String TABLE_NAME = "PRODUCTS";
    private SQLiteOpenHelper database;
    private static String TAG = ProductTable.class.getSimpleName();

    public ProductTable() {
        this.database = new HypercityApplication().database();
    }

    private static class ProductCursor extends SQLiteCursor {

        private static final String FIELD_LIST = " id, name, barcode_id, category, price, uom ";
        private static final String ALL_QUERY = "SELECT "+ FIELD_LIST +" FROM "+ TABLE_NAME;
        private static final String ID_QUERY = "SELECT "+ FIELD_LIST +" FROM "+ TABLE_NAME +" WHERE id = ?";
        private static final String NAME_LIKE_QUERY = "SELECT "+ FIELD_LIST +" FROM "+ TABLE_NAME +" WHERE name like ? ";
        private static final String SHOPPING_LIST_QUERY = "SELECT P.* FROM "+ TABLE_NAME + " as P,"
                                                          + ShoppingListProductTable.TABLE_NAME
                                                          + " AS SLP WHERE SLP.shopping_list_id = ? AND SLP.product_id = P.id ";
        public static final String BARCODE_ID_QUERY = "SELECT "+ FIELD_LIST +" FROM "+ TABLE_NAME +" WHERE barcode_id = ? ";

        public ProductCursor (SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
            super(db, driver, editTable, query);
        }

        private static class Factory implements SQLiteDatabase.CursorFactory {
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver driver, String editTable, SQLiteQuery query) {
                return new ProductCursor(db, driver, editTable, query);
            }
        }

        private long getProductId() {
            return getLong(getColumnIndexOrThrow("id"));
        }

        private String getName() {
            return getString(getColumnIndexOrThrow("name"));
        }

        private String getBarcodeId() {
            return getString(getColumnIndexOrThrow("barcode_id"));
        }

        private String getCategory() {
            return getString(getColumnIndexOrThrow("category"));
        }

        private double getCost() {
            return getDouble(getColumnIndexOrThrow("price"));
        }

        private String getUOM() {
			return getString(getColumnIndexOrThrow("uom"));
        }

        public Product getProduct() {
            return new Product(getProductId(), getName(), getBarcodeId(), getCategory(), getCost(), getUOM());
        }
    }

    protected List<Product> findProduct(String query, String[] params) {
        Cursor productCursor = null;
        List<Product> productList = new ArrayList<Product>();
        try {
            productCursor = database.getReadableDatabase().rawQueryWithFactory(new ProductCursor.Factory(), query, params, null);
            if(productCursor != null && productCursor.moveToFirst()) {
                do {
                    productList.add(((ProductCursor) productCursor).getProduct());
                } while(productCursor.moveToNext());
            }
        } catch(SQLException sqle) {
            Log.e(TAG, "Could not look up the products with params " + params + ". The error is: " + sqle.getMessage());
        }
        finally {
            if(productCursor != null && !productCursor.isClosed()) {
                productCursor.close();
            }
        }
        return productList;
    }

    public List<Product> findAll() {
        return findProduct(ProductCursor.ALL_QUERY, null);
    }

    public Product findById(long id) {
        String id_string = Long.toString(id);
        List<Product> productList = findProduct(ProductCursor.ID_QUERY, new String[] {id_string});
        return (productList == null || productList.isEmpty()) ? null : productList.get(0);
    }

    public List<Product> findByMatchingName(String nameFragment) {
        return findProduct(ProductCursor.NAME_LIKE_QUERY, new String[] {"%" + nameFragment + "%"});
    }

    public List<Product> findByShoppingList(long shoppingListId) {
        return findProduct(ProductCursor.SHOPPING_LIST_QUERY, new String[] {Long.toString(shoppingListId)});
    }

    public Product findByBarcodeId(String barcodeId) {
        List<Product> products = findProduct(ProductCursor.BARCODE_ID_QUERY, new String[]{barcodeId});
        return ((products != null && products.size() > 0) ? products.get(0) : null );
    }

}
