package bwie.com.zsk.model;

import bwie.com.zsk.api.ICartApi;
import bwie.com.zsk.bean.CartBean;
import bwie.com.zsk.util.RetrofitUtil;
import io.reactivex.Observable;

public class CartModel {
    public Observable<CartBean> cart(String url, String uid) {

        return RetrofitUtil.getDefault().create(ICartApi.class).cart(url, uid);
    }
}
