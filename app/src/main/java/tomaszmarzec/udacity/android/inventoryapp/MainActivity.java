package tomaszmarzec.udacity.android.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tomaszmarzec.udacity.android.inventoryapp.database.InventoryDbHelper;
import tomaszmarzec.udacity.android.inventoryapp.database.InventoryContract.ProductEntry;

public class MainActivity extends AppCompatActivity {

    private InventoryDbHelper mDbHelper;
    private final static String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new InventoryDbHelper(this);


        Product product = new Product("Anti Mosqito Spray", "TopFezz", null);
        putProductToDb(product);
         product = new Product("Wheelbarrow", 43.56f, "SoilWorks", "444444444");
        putProductToDb(product);
        product = new Product("Rodent Deterrent", 24.00f,45, "SoilWorks", "1224444444");
        putProductToDb(product);

        displayProducts();
    }

    private void displayProducts()
    {
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRICE,
                ProductEntry.COLUMN_QUANTITY,
                ProductEntry.COLUMN_SUPPLIER_NAME,
                ProductEntry.COLUMN_SUPPLIER_PHONE
        };


        TextView textView = findViewById(R.id.text_view);

        List<Product> products = getProductsFromDb(projection);

        for (Product product:products)
        {
            textView.append("*PRODUCT* name: " + product.getName() + "\n- price: " + product.getPrice()
            + "\n-quantity: " + product.getQuantity() + "\n-supplier name: " + product.getSupplierName()
            + "\n-supplier phone: " + product.getSupplierPhone() + "\n\n");
        }

    }

    private void handleWrongInsert(String msg)
    {
        // handle problem with insert query
    }

    // Returns true if successfully inserted new row to SQLite database.
    private void putProductToDb(Product product)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String productName = product.getName();
        float productPrice =  product.getPrice();
        int productQuantity = product.getQuantity();
        String productSupplierName = product.getSupplierName();
        String productSupplierPhone = product.getSupplierPhone();

        ContentValues values = new ContentValues();
        /* This two values are mandatory (have NOT NULL and lack DEFAULT property in SQLite data schema)
            If they are null or empty (""), method returns false */
        if(TextUtils.isEmpty(productName))
        {
            handleWrongInsert(getString(R.string.waring_product_name_empty));
        }
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, product.getName());
        if(TextUtils.isEmpty(productSupplierName))
        {
            handleWrongInsert(getString(R.string.waring_product_supplier_name_empty));
        }
        values.put(ProductEntry.COLUMN_SUPPLIER_NAME, product.getSupplierName());
        // Following values are not mandatory as they have DEFAULT property in SQLite data schema
        if(productPrice != -1)
        {
            values.put(ProductEntry.COLUMN_PRICE, productPrice);
        }
        if(productQuantity != 1)
        {
            values.put(ProductEntry.COLUMN_QUANTITY, productQuantity);
        }
        if(!TextUtils.isEmpty(productSupplierPhone))
        {
            values.put(ProductEntry.COLUMN_SUPPLIER_PHONE, product.getSupplierPhone());
        }


        long newRowId = db.insert(ProductEntry.TABLE_NAME, null, values);

        if(newRowId == -1)
        {
            Log.v(LOG_TAG, "Error adding new row to database.");
            handleWrongInsert(getString(R.string.waring_db_insert_failure));

        }
        else
        {
            Log.v(LOG_TAG, "Successfully added new row with id: " + newRowId + " to database.");
        }
    }

    private List<Product> getProductsFromDb(String[] projection)
    {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                ProductEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try{
            int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
            int productNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SUPPLIER_PHONE);

            while (cursor.moveToNext())
            {
                int currentID = cursor.getInt(idColumnIndex);
                String productName = cursor.getString(productNameColumnIndex);
                float price = cursor.getFloat(priceColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                String supplierName = cursor.getString(supplierNameColumnIndex);
                String supplierPhone= cursor.getString(supplierPhoneColumnIndex);

                products.add(new Product(productName, price, quantity, supplierName, supplierPhone));
            }

        }
        finally {
            cursor.close();
            return products;
        }
    }
}
