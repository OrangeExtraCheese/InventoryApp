package tomaszmarzec.udacity.android.inventoryapp.database;

import android.provider.BaseColumns;

public final class InventoryContract
{
    public final static class ProductEntry implements BaseColumns
    {
        public static String TABLE_NAME = "products";

        public static String _ID = BaseColumns._ID;
        public static String COLUMN_PRODUCT_NAME = "name";
        public static String COLUMN_PRICE = "price";
        public static String COLUMN_QUANTITY = "quantity";
        public static String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static String  COLUMN_SUPPLIER_PHONE = "supplier_phone";
    }
}
