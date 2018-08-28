package tomaszmarzec.udacity.android.inventoryapp;

import android.support.annotation.NonNull;

public class Product
{
    private String mName, mSupplierName, mSupplierPhone;
    private int mQuantity;
    private float mPrice;

    public Product(@NonNull String name, float price, int quantity, @NonNull String supplierName,
                   String supplierPhone)
    {
        mName = name;
        mSupplierName = supplierName;
        mSupplierPhone = supplierPhone;
        mQuantity = quantity;
        mPrice = price;
    }

    /* Constructors chained with the constructor above. Thanks to them person creating instance of
        this class, who doesn't know product price and/or quantity, doesn't have to pass -1 value for
        them, but can omit them in constructor */

    public Product(@NonNull String name, int quantity, @NonNull String supplierName, String supplierPhone)
    {
        this(name, -1f, quantity, supplierName, supplierPhone);
    }

    public Product(@NonNull String name, float price, @NonNull String supplierName, String supplierPhone)
    {
        this(name,  price,  -1, supplierName, supplierPhone);
    }

    public Product(@NonNull String name, @NonNull String supplierName, String supplierPhone)
    {
        this(name, -1f, -1, supplierName, supplierPhone);
    }


    public String getName() {
        return mName;
    }

    public String getSupplierName() {
        return mSupplierName;
    }

    public String getSupplierPhone() {
        return mSupplierPhone;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public float getPrice() {
        return mPrice;
    }
}
