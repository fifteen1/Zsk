package bwie.com.zsk.api;

import bwie.com.zsk.bean.CartBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ICartApi {
    @GET()
    Observable<CartBean> cart(@Url String url, @Query("uid") String uid);
}
