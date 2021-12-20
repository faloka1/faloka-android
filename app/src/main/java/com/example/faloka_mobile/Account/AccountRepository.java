package com.example.faloka_mobile.Account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Adapter.AddressAdapter;
import com.example.faloka_mobile.Adapter.AddressAddAdapter;
import com.example.faloka_mobile.InspireMe.InspireMeFragment;
import com.example.faloka_mobile.Login.LoginActivity;
import com.example.faloka_mobile.Login.TokenManager;
import com.example.faloka_mobile.MainActivity;
import com.example.faloka_mobile.Model.Brand;
import com.example.faloka_mobile.Model.Cart;
import com.example.faloka_mobile.Model.CartBrand;
import com.example.faloka_mobile.Model.Cost;
import com.example.faloka_mobile.Model.Courier;
import com.example.faloka_mobile.Model.CourierService;
import com.example.faloka_mobile.Model.Message;
import com.example.faloka_mobile.Model.Order;
import com.example.faloka_mobile.Model.OrderBrand;
import com.example.faloka_mobile.Model.OrderDetail;
import com.example.faloka_mobile.Model.OrderUser;
import com.example.faloka_mobile.Model.User;
import com.example.faloka_mobile.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepository {

    public static final void logoutUser(Context context){
        TokenManager tokenManager = TokenManager.getInstance(context.getSharedPreferences("Token",0));
        Call<Message> callLogout = ApiConfig.getApiService(tokenManager).getLogoutMessage(tokenManager.getTypeToken()+" "+tokenManager.getToken());
        callLogout.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()) {
                    Message logout = response.body();
                    tokenManager.deleteToken();

                    CoordinatorLayout coordinatorLayout = ((Activity) context).findViewById(R.id.coordinator_layout_top_account);
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Berhasil keluar", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((Activity)context).startActivity(new Intent(context, MainActivity.class));
                            ((Activity)context).finish();
                        }
                    });
                    snackbar.setActionTextColor(((Activity) context).getResources().getColor(R.color.primary_dark));
                    snackbar.setTextColor(((Activity) context).getResources().getColor(R.color.primary_dark));
                    snackbar.setBackgroundTint(((Activity) context).getResources().getColor(R.color.semantic_success));
                    snackbar.addCallback(new Snackbar.Callback(){
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            ((Activity)context).startActivity(new Intent(context, MainActivity.class));
                            ((Activity)context).finish();
                        }
                    });
                    snackbar.show();

                }
                else {
                    tokenManager.deleteToken();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }

    public static final void setUserProfile(View view, UserProfileListener userProfileListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<User> callUser = ApiConfig.getApiService(tokenManager).getUser("Bearer "+tokenManager.getToken());

        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    userProfileListener.onUserProfile(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public static final void getOrders(String status, View view, OrderUserListener orderUserListener){
        TokenManager tokenManager = TokenManager.getInstance(view.getContext().getSharedPreferences("Token",0));
        Call<List<OrderUser>> callOrderUser = ApiConfig.getApiService(tokenManager).getOrders("Bearer "+tokenManager.getToken(), status);

        callOrderUser.enqueue(new Callback<List<OrderUser>>() {
            @Override
            public void onResponse(Call<List<OrderUser>> call, Response<List<OrderUser>> response) {
                if(response.isSuccessful()){
                    List<OrderUser> orderUserList = response.body();
                    orderUserListener.onOrder(orderUserList);
                }
            }

            @Override
            public void onFailure(Call<List<OrderUser>> call, Throwable t) {
            }
        });
    }

    public static final Order getOrder(OrderUser orderUser){
        Order order = new Order();
        order.setId(orderUser.getId());
        order.setImagePaymentURL(orderUser.getImagePaymentURL());
        order.setAddress(orderUser.getAddress());
        order.setPayment(orderUser.getPayment());
        List<CartBrand> cartBrandList = new ArrayList<>();
        for(OrderBrand orderBrand : orderUser.getOrderBrandList()){
            CartBrand cartBrand = new CartBrand();
            cartBrand.setBrand(orderBrand.getBrand());
            Courier courier = new Courier();
            CourierService courierService = new CourierService();
            courier.setName(orderBrand.getOrderShipping().getExpeditionName());
            courierService.setName(orderBrand.getOrderShipping().getService());
            Cost cost = new Cost();
            cost.setValue(orderBrand.getOrderShipping().getShippingPrice());
            List<Cost> costList = new ArrayList<>();
            costList.add(cost);
            courierService.setCost(costList);
            cartBrand.setCourier(courier);
            cartBrand.setCourierService(courierService);
            List<Cart> cartList = new ArrayList<>();
            for(OrderDetail orderDetail : orderBrand.getOrderDetailList()){
                Cart cart = new Cart();
                cart.setVariant(orderDetail.getVariant());
                cart.setProduct(orderDetail.getProduct());
                cart.setVariantID(orderDetail.getVariantID());
                cart.setProductID(orderDetail.getProductID());
                cart.setQuantity(orderDetail.getQuantity());
                cartList.add(cart);
            }
            cartBrand.setCartList(cartList);
            cartBrandList.add(cartBrand);
        }
        order.setCartBrandList(cartBrandList);
        return order;
    }

}
