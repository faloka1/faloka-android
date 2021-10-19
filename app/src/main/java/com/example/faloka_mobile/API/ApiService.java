package com.example.faloka_mobile.API;

import com.example.faloka_mobile.Login.LoginResponse;
import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.Model.Category;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.District;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.Payment;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.Profile;
import com.example.faloka_mobile.Model.Province;
import com.example.faloka_mobile.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<Message> getLogoutMessage(@Header("Authorization") String auth);

    @GET("auth/profile")
    Call<Profile> getProfile(@Header("Authorization") String auth);

    @GET("user")
    Call<User> getUser(@Header("Authorization") String auth);

    @GET("expeditions")
    Call<List<Courier>> getExpeditions();

    @GET("ongkir")
    Call<List<Courier>> getCostExpeditions(
            @Query("origin") int originDistrictID,
            @Query("destination") int destDistrictID,
            @Query("weight") int weight,
            @Query("courier") String courierCode
    );

    @POST("user/address")
    Call<Message> addAddress(@Header("Authorization") String auth, @Body Address address);

    @PUT("user/address/{address_id}")
    Call<Message> putAddress(@Header("Authorization") String auth, @Path("address_id") int addressID, @Body Address address);

    @DELETE("user/address/{address_id}")
    Call<Message> deleteAddress(@Header("Authorization") String auth, @Path("address_id") int addressID);

    @GET("province")
    Call<List<Province>> getAllProvince();

    @GET("province/{province_id}")
    Call<List<Province>> getProvinceByID(@Path("province_id") int provinceID);

    @GET("district")
    Call<List<District>> getDistrictByProvince(@Query("province") int provinceID);

    @GET("payment")
    Call<List<Payment>> getPayments();
}

