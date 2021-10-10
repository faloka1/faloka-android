package com.example.faloka_mobile.API;

import com.example.faloka_mobile.Login.LoginResponse;
import com.example.faloka_mobile.Model.Category;
import com.example.faloka_mobile.Model.Logout;
import com.example.faloka_mobile.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> getSession(@Field("email") String email, @Field("password") String password);

    @GET("home")
    Call<List<Category>> getCategories();

    @GET("products")
    Call<List<Product>> getProductSubCategories(
            @Query("category") String slugCategory,
            @Query("subcategory") String slugSubCategory);

    @GET("products/{slug}/related")
    Call<List<Product>> getRelatedProducts(
        @Path(value = "slug", encoded = true) String slug
    );

    @POST("auth/logout")
    Call<Logout> getLogoutMessage(@Header("Authorization") String auth);
}

