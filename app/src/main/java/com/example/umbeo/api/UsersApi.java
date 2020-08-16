package com.example.umbeo.api;

import com.example.umbeo.response_data.CancelOrder;
import com.example.umbeo.response_data.CategoryResponse;
import com.example.umbeo.response_data.GetOrders.GetOrderResponse;
import com.example.umbeo.response_data.LoginResponse;
import com.example.umbeo.response_data.OrderResponse;
import com.example.umbeo.response_data.ProductResponse;
import com.example.umbeo.response_data.SignUpResponse;
import com.example.umbeo.response_data.SignUpResquest;
import com.example.umbeo.response_data.UserGetProfileResponse;
import com.example.umbeo.response_data.forgetpassword_response;
import com.example.umbeo.response_data.orderRequest.OrderRequest;
import com.example.umbeo.response_data.shop.ShopResponse;

import org.json.JSONArray;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UsersApi {

    @FormUrlEncoded
    @POST("signup")
    Call<ResponseBody> Signup(
            @Field("name") String name,
            @Field("phone") String number,
            @Field("email") String mail,
            @Field("password") String password,
            @Field("gps") String gps,
            @Field("latlng") String latlong,
            @Field("delivery_addresses") JSONArray address,
            @Field("shop") String shop,
            @Field("profile_pic") String profilePic
    );



    @POST("/api/v1/users/signup")
    Call<SignUpResponse> SignUp (@Header("Content-Type") String content_type,@Body SignUpResquest requestBody);

    @FormUrlEncoded
    @POST("/api/v1/users/login")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password,
            @Field("shopId") String shop
    );
    @FormUrlEncoded
    @POST("forgot-password")
    Call<forgetpassword_response> forgetPassword(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("reset-password")
    Call<forgetpassword_response> resetPassword(
            @Field("email") String email,
            @Field("password") String password
    );



    @POST("/api/v1/users/me")
    Call<UserGetProfileResponse> getProfile(@Header("Authorization")String token);

    @POST("/api/v1/users/update-me")
    Call<ResponseBody> updateUser(
            @Header("Authorization")String token,
            @Body SignUpResquest request
    );


    @POST("/api/v1/products/get-all-categories")
    Call<CategoryResponse> fetchAllCategory();


    @FormUrlEncoded
    @POST("/api/v1/products/get-products-by-category")
    Call<ProductResponse> fetchAllProducts(@Field("shopId") String shopId,
                                           @Field("categoryId") String categoryId);
    @FormUrlEncoded
    @POST("/api/v1/products/get-all-products")
    Call<ProductResponse> fetchAllProducts(@Field("shopId") String shopId);




    @FormUrlEncoded
    @POST("/api/v1/products/get-featured")
    Call<ProductResponse> fetchFeaturedProducts(@Field("shopId") String shopId);


    @FormUrlEncoded
    @POST("/api/v1/products/get-recommended")
    Call<ProductResponse> fetchRecommendedProducts(@Field("shopId") String shopId,
                                                   @Field("user") String user);


    @FormUrlEncoded
    @POST("/api/v1/products/get-trending")
    Call<ProductResponse> fetchTrendingProducts(@Field("shopId") String shopId);


    @POST("/api/v1/orders/create-order")
    Call<OrderResponse> CreateOrder(@Header("Authorization")String token,
                                    @Body OrderRequest orderRequest);



    @FormUrlEncoded
    @POST("/api/v1/orders/get-all-orders")
    Call<GetOrderResponse> GetOrder(@Header("Authorization")String token,
                                    @Field("shopId") String shopId);




    @FormUrlEncoded
    @POST("/api/v1/shops/get-shop")
    Call<ShopResponse> getShopProfile(@Field("shop") String shop);



    @FormUrlEncoded
    @POST("/api/v1/orders/cancel-order")
    Call<CancelOrder> CancelOrder (@Header("Authorization")String token, @Field("orderId") String orderId);
}
