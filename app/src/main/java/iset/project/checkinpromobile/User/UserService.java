package iset.project.checkinpromobile.User;

import java.util.List;

import iset.project.checkinpromobile.Model.LoginRequest;
import iset.project.checkinpromobile.Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
        @POST("/users/login")
        Call<User> login(@Body LoginRequest loginRequest);
        @POST("/users")
        Call<User> addUser(@Body User user);

        //Display , Delete, Update
        @GET("users")
        Call<List<User>> getAllUser();
        @PUT("users/{id}")
        Call<User> updateUser(@Path("id") Long id, @Body User user);
        @DELETE("users/{id}")
        Call<Void> deleteUser(@Path("id") Long id);
        @GET("users/{id}")
        Call<User> getUserById(@Path("id") Long id);

}
