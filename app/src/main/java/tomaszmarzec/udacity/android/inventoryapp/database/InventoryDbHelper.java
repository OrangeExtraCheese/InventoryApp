package tomaszmarzec.udacity.android.inventoryapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tomaszmarzec.udacity.android.inventoryapp.database.InventoryContract.ProductEntry;

public class InventoryDbHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "inventory.db";
    private static final int DB_VERSION = 1;

    public InventoryDbHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductEntry.TABLE_NAME + "("
                + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_PRICE + " INTEGER NOT NULL DEFAULT 0, "
                + ProductEntry.COLUMN_QUANTITY+ " REAL NOT NULL DEFAULT 0, "
                + ProductEntry.COLUMN_SUPPLIER_NAME+ " TEXT NOT NULL, "
                + ProductEntry.COLUMN_SUPPLIER_PHONE+ " TEXT NOT NULL DEFAULT 'unknown' "
                + ");";


        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
