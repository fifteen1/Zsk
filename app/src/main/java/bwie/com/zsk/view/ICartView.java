package bwie.com.zsk.view;

import bwie.com.zsk.bean.CartBean;

public interface ICartView {
    void Success(CartBean cartBean);
    void Failure(String msg);
}
