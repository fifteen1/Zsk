package bwie.com.zsk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import java.util.Collections;
import java.util.List;
import bwie.com.zsk.R;
import bwie.com.zsk.bean.CartBean;
import bwie.com.zsk.inter.CartAllCheckLinstener;
import bwie.com.zsk.inter.CheckListener;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements CheckListener{
    private Context mContext;
    private List<CartBean.DataBean> cartList;
    private CheckListener checkListener;
    private CartAllCheckLinstener cartAllCheckLinstener;

    public CartAdapter(Context context, List<CartBean.DataBean> list) {
        mContext = context;
        cartList = list;

    }

    public void setCartAllCheckLinstener(CartAllCheckLinstener cartAllCheckLinstener) {
        this.cartAllCheckLinstener = cartAllCheckLinstener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.cart_item_layout, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, int position){

        final CartBean.DataBean bean = cartList.get(position);
        holder.nameTv.setText(bean.getSellerName());
        Log.i("cartada",bean.getSellerName());
        holder.checkBox.setChecked(bean.isSelected());

        holder.productXRV.setLayoutManager(new LinearLayoutManager(mContext));
        final ProductAdapter productAdapter = new ProductAdapter(mContext, bean.getList());

        holder.productXRV.setAdapter(productAdapter);
        productAdapter.setCheckListener(this);

        for (int i = 0; i < bean.getList().size(); i++) {

            if(!bean.getList().get(i).isSelected()){
                holder.checkBox.setChecked(false);
                break;
            }else{
                holder.checkBox.setChecked(true);
            }
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox.isChecked()){
                    bean.setSelected(true);
                    for (int i = 0; i < bean.getList().size(); i++) {
                        bean.getList().get(i).setSelected(true);
                    }
                }else{
                    bean.setSelected(false);
                    for (int i = 0; i < bean.getList().size(); i++) {
                        bean.getList().get(i).setSelected(false);
                    }
                }
                notifyDataSetChanged();
                if(cartAllCheckLinstener!=null){
                    cartAllCheckLinstener.notifyAllCheckboxStatus();
                }

            }
        });
    }

    public List<CartBean.DataBean> getCartList(){
        return cartList;
    }
    @Override
    public int getItemCount() {
        return cartList.size()== 0 ? 0 : cartList.size();
    }

    @Override
    public void notifyParpen() {
        notifyDataSetChanged();
        if (cartAllCheckLinstener!=null){
            cartAllCheckLinstener.notifyAllCheckboxStatus();
        }

    }


    public class CartViewHolder extends RecyclerView.ViewHolder{
        private CheckBox checkBox;
        private TextView nameTv;
        private RecyclerView productXRV;
        public CartViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.sellerCheckbox);
            nameTv = itemView.findViewById(R.id.sellerNameTv);
            productXRV = itemView.findViewById(R.id.productXRV);

        }
    }
}
