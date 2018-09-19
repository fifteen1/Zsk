package bwie.com.zsk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import bwie.com.zsk.adapter.CartAdapter;
import bwie.com.zsk.bean.CartBean;
import bwie.com.zsk.inter.CartAllCheckLinstener;
import bwie.com.zsk.presenter.CartPresenter;
import bwie.com.zsk.view.ICartView;

public class MainActivity extends AppCompatActivity implements ICartView,CartAllCheckLinstener{

    @BindView(R.id.xrecyc)
    XRecyclerView xrecyc;
    @BindView(R.id.allCheckBox)
    CheckBox allCheckBox;
    @BindView(R.id.sumprice)
    TextView sumprice;
    @BindView(R.id.buy)
    TextView buy;
    private CartPresenter cartPresenter;
    private CartAdapter cartAdapter;
    private List<CartBean.DataBean> list;
    private String uid = "71";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }
    private void initView() {
        list = new ArrayList<>();
        xrecyc.setLayoutManager(new LinearLayoutManager(this));
        allCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allCheckBox.isChecked()) {
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setSelected(true);
                            for (int i1 = 0; i1 < list.get(i).getList().size(); i1++) {
                                list.get(i).getList().get(i1).setSelected(true);
                            }
                        }
                    }
                } else {
                    if (list != null && list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setSelected(false);
                            for (int i1 = 0; i1 < list.get(i).getList().size(); i1++) {
                                list.get(i).getList().get(i1).setSelected(false);
                            }
                        }
                    }
                }
                cartAdapter.notifyDataSetChanged();
                totalPrice();
            }
        });
    }

    private void initData() {

        cartPresenter = new CartPresenter(this);
        cartPresenter.getCart(uid);
    }
    @Override
    public void Success(CartBean cartBean) {
        if (cartBean != null && cartBean.getData() != null) {
            list = cartBean.getData();

            cartAdapter = new CartAdapter(this, list);
            xrecyc.setAdapter(cartAdapter);
        }
        cartAdapter.setCartAllCheckLinstener(this);
    }

    @Override
    public void Failure(String msg) {

    }

    @Override
    public void notifyAllCheckboxStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        if (cartAdapter != null) {
            for (int i = 0; i < cartAdapter.getCartList().size(); i++) {
                stringBuilder.append(cartAdapter.getCartList().get(i).isSelected());
                for (int i1 = 0; i1 < cartAdapter.getCartList().get(i).getList().size(); i1++) {
                    stringBuilder.append(cartAdapter.getCartList().get(i).getList().get(i1).isSelected());
                }
            }
        }
        if (stringBuilder.toString().contains("false")) {
            allCheckBox.setChecked(false);
        } else {
            allCheckBox.setChecked(true);
        }
        totalPrice();
    }

    private void totalPrice() {
        double totalprice = 0;

        for (int i = 0; i < cartAdapter.getCartList().size(); i++) {
            for (int i1 = 0; i1 < cartAdapter.getCartList().get(i).getList().size(); i1++) {
                if (cartAdapter.getCartList().get(i).getList().get(i1).isSelected()) {
                    CartBean.DataBean.ListBean listBean = cartAdapter.getCartList().get(i).getList().get(i1);
                    totalprice += listBean.getBargainPrice() * listBean.getTotalNum();
                }
            }
        }
        sumprice.setText("" + totalprice);
    }

}
