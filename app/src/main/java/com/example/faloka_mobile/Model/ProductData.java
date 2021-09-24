package com.example.faloka_mobile.Model;

import com.example.faloka_mobile.R;

import java.util.ArrayList;

public class ProductData {

    private static String[] productName = {
            "Baju bagus",
            "Baju bagus",
            "Baju bagus",
            "Baju bagus",
            "Baju bagus",
            "Baju bagus",
            "Baju bagus",
            "Baju bagus"
    };

    private static String[] brand = {
           "Surabaya - Brand",
            "Surabaya - Brand",
            "Surabaya - Brand",
            "Surabaya - Brand",
            "Surabaya - Brand",
            "Surabaya - Brand",
            "Surabaya - Brand",
            "Surabaya - Brand",
    };

    private static String[] price = {
            "80000",
            "80000",
            "80000",
            "80000",
            "80000",
            "80000",
            "80000",
            "80000"
    };

    private static int[] image = {
            R.drawable.product_image,
            R.drawable.product_image,
            R.drawable.product_image,
            R.drawable.product_image,
            R.drawable.product_image,
            R.drawable.product_image,
            R.drawable.product_image,
            R.drawable.product_image
    };

    public static ArrayList<Product> getListData() {
        ArrayList<Product> list = new ArrayList<>();
        for (int position = 0; position < productName.length; position++) {
            Product product = new Product();
            product.setName(productName[position]);
            product.setBrand(brand[position]);
            product.setPrice(price[position]);
            product.setImage(image[position]);
            list.add(product);
        }
        return list;
    }
}
