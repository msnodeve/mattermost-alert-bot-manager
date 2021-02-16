package kr.co.seok.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.seok.retrofit.service.SendMessageService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://meeting.ssafy.com/hooks/";

    public static SendMessageService getSendMessageService(){
        return getInstance().create(SendMessageService.class);
    }

    public static Retrofit getInstance(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
