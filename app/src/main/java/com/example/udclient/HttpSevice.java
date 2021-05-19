package com.example.udclient;

import com.example.udclient.classes.LoginDto;
import com.example.udclient.classes.MeetingDetailsDto;
import com.example.udclient.classes.MeetingListDto;
import com.example.udclient.classes.PaymentDto;
import com.example.udclient.classes.PaymentGetDto;
import com.example.udclient.classes.PaymentListDto;
import com.example.udclient.classes.ProductListDto;
import com.example.udclient.classes.RegisterDto;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HttpSevice {

    @POST ("ud-server/login")
    Call<Map<String,String>> login(@Body LoginDto loginDto);

    @POST("ud-server/register")
    Call<Void> register(@Body RegisterDto registerDto);

    @POST("ud-server/create_meeting")
    Call<String> createMeeting(@Query("name") String name , @Query("password") String password);

    @POST("ud-server/join_meeting")
    Call<Void> joinMeeting(@Query("name") String name , @Query("password") String password);

    @GET("ud-server/meeting_details_code")
    Call<MeetingDetailsDto> getMeetingDetails(@Query("code") String code);

    @GET("ud-server/person_meetings")
    Call<MeetingListDto> getPersonsMeetingList(@Query("id_person") String idPerson);

    @GET("ud-server/products")
    Call<ProductListDto> getMeetingsProducts(@Query("id_meeting") String id_meeting);

    @POST("ud-server/delete_product")
    Call<Void> deleteProduct(@Query("id_product") String id_product);

    @POST("ud-server/add_person")
    Call<Void> addPerson(@Query("id_meeting") String id_meeting,@Query("nick") String nick);

    @POST("ud-server/product")
    Call<Void> addProduct(@Query("name") String name,@Query("price") String price,@Query("id_person") String id_person,@Query("id_meeting") String id_meeting);

    @GET("ud-server/payments_meeting/{id_meeting}")
    Call<PaymentListDto> getMeetingsPayments(@Path("id_meeting") String id_meeting);

    @POST("ud-server/payment")
    Call<Void> insertPayment(@Body PaymentDto paymentDto);

    @GET("ud-server/payments_sum_meeting/{id_meeting}")
    Call<Double> getMeetingsPayment(@Path("id_meeting") String id_meeting);

    @GET("ud-server/payments_sum_person/{id_person}")
    Call<Double> getSumPersonPayments(@Path("id_person") String id_person);

    @GET("ud-server/payments_person/{id_person}")
    Call<PaymentListDto> getPersonPayments(@Path("id_person") String id_person);


}
