package bwie.com.zsk.util;

import com.google.gson.Gson;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static String BASE_URL = "https://www.zhaoapi.cn/";
    private final Retrofit retrofit;

    public static class SingleHolder{
        private static final RetrofitUtil _INSTACE =  new RetrofitUtil(BASE_URL);
    }
    public static RetrofitUtil getDefault(){

        return SingleHolder._INSTACE;
    }

    public RetrofitUtil(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(bulidokhttpclient())
                .build();
    }

    private OkHttpClient bulidokhttpclient(){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        return client;
    }
    public <T> T create(Class<T> Clazz){
        return retrofit.create(Clazz);
    }
}
