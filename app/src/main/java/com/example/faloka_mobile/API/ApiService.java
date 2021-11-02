package com.example.faloka_mobile.API;

import android.database.Observable;

import com.example.faloka_mobile.Login.LoginResponse;
import com.example.faloka_mobile.Model.Address;
import com.example.faloka_mobile.Model.BodyCart;
import com.example.faloka_mobile.Model.BodyCheckout;
import com.example.faloka_mobile.Model.BodyOrderDetail;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.Category;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.District;
import com.example.faloka_mobile.Model.Login;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.OrderDetail;
import com.example.faloka_mobile.Model.OrderResponse;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.Model.Payment;
import com.example.faloka_mobile.Model.Product;
import com.example.faloka_mobile.Model.ProductListResponse;
import com.example.faloka_mobile.Model.ProductMixMatch;
import com.example.faloka_mobile.Model.Profile;
import com.example.faloka_mobile.Model.Province;
import com.example.faloka_mobile.Model.User;
import com.example.faloka_mobile.Model.UserRegister;
import com.example.faloka_mobile.Register.RegisterResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> getSession(@Body Login login);

    @POST("auth/register")
    Call<RegisterResponse> addUser(@Body UserRegister userRegister);

    @GET("home")
    Call<List<Category>> getCategories();

    @GET("products")
    Call<ProductListResponse> getProductSubCategories(
            @Query("category") String slugCategory,
            @Query("subcategory") String slugSubCategory);

    @GET("products/{slug}/related")
    Call<List<Product>> getRelatedProducts(
        @Path(value = "slug", encoded = true) String slug
    );

    @GET("products")
    Call<ProductListResponse> getProducts();

    @GET("products/{slug}")
    Call<Product> getProductSlug(@Path(value = "slug", encoded = true) String slug);

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

//    @POST("checkout")
//    Call<OrderResponse> isCheckout(
//            @Header("Authorization") String auth,
//            @Field("shipping_price") int shippingPrice,
//            @Field("expedition_name") String expeditionName,
//            @Field("service") String serviceExpedition,
//            @Field("payment_id") int paymentID,
//            @Field("address_id") int addressID,
////            @Field("quantity") int quantity,
////            @Field("variant_id") int variantID,
////            @Field("product_id") int productID,
//            @Field("orderDetails") List<BodyOrderDetail> bodyOrderDetails
//    );
    @POST("checkout")
    Call<OrderResponse> isCheckout(
            @Header("Authorization") String auth, @Body BodyCheckout bodyCheckout
    );

    @Multipart
    @POST("uploadpayment/{order_id}")
    Call<Message> uploadPhotoMultipart(
            @Part("_method") RequestBody method,
            @Part MultipartBody.Part image,
            @Path("order_id") int orderID);

    @GET("user/orders")
    Call<List<OrderUser>> getOrders(@Header("Authorization") String auth, @Query("status") String status);

    @GET("mix-and-match/items")
    Call<List<ProductMixMatch>> getProductsMixMatch();

    @GET("cart")
    Call<List<Cart>> getCarts(@Header("Authorization") String auth);

    @POST("cart")
    Call<Message> addCart(@Header("Authorization") String auth, @Body BodyCart bodyCart);

    @DELETE("cart-user")
    Call<Message> deleteAllCart(@Header("Authorization") String auth);

    @PATCH("cart/{cart_id}")
    Call<Message> editCartQuantity(
            @Header("Authorization") String auth,
            @Path("cart_id") int cartID,
            @Query("quantity") int quantity
    );

    @GET("cart-related")
    Call<List<Product>> getCartProductsRelated(@Header("Authorization") String auth);
}

