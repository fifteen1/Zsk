package bwie.com.zsk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.List;


import bwie.com.zsk.R;
import bwie.com.zsk.bean.CartBean;
import bwie.com.zsk.inter.CartAllCheckLinstener;
import bwie.com.zsk.inter.CheckListener;
import bwie.com.zsk.myview.My_add_reduce;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.CartViewHolder>{
    private Context mcontext;
    private List<CartBean.DataBean.ListBean> listBeanList;
    private CheckListener checkListener;
    private CartAllCheckLinstener cartAllCheckLinstener;
    public ProductAdapter(Context context, List<CartBean.DataBean.ListBean> list) {
        mcontext = context;
        listBeanList = list;

    }

    public void setCheckListener(CheckListener checkListener) {
        this.checkListener = checkListener;
    }

    public void setCartAllCheckLinstener(CartAllCheckLinstener cartAllCheckLinstener) {
        this.cartAllCheckLinstener = cartAllCheckLinstener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mcontext).inflate(R.layout.item_product_layout, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        final CartBean.DataBean.ListBean bean = listBeanList.get(position);
        holder.priceTv.setText("优惠价:¥"+bean.getBargainPrice());
        Log.i("ssss",bean.getTitle());
        holder.titleTv.setText(bean.getTitle());
        String[] imgs = bean.getImages().split("\\|");
        if(imgs!=null&&imgs.length>0){
            holder.productIv.setImageURI(imgs[0]);
        }else{
            holder.productIv.setImageResource(R.mipmap.ic_launcher);
        }

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listBeanList.remove(position);
                notifyDataSetChanged();
                holder.swipeMenuLayout.quickClose();
            }
        });
        holder.checkBox.setChecked(bean.isSelected());

        holder.myAddReduce.setNumEt(bean.getTotalNum());

        holder.myAddReduce.setJiaJianLinstener(new My_add_reduce.JiaJianLinstener() {
            @Override
            public void getNum(int num) {
                bean.setTotalNum(num);
                if(checkListener!=null){
                    checkListener.notifyParpen();
                }
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox.isChecked()){
                    bean.setSelected(true);
                }else{
                    bean.setSelected(false);
                }
                if (checkListener!=null){
                    checkListener.notifyParpen();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeanList.size()==0 ? 0 : listBeanList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView titleTv,priceTv;
        private SimpleDraweeView productIv;
        private My_add_reduce myAddReduce;
        private Button delBtn;
        private SwipeMenuLayout swipeMenuLayout;
        public CartViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.productCheckBox);
            titleTv = itemView.findViewById(R.id.title);
            priceTv = itemView.findViewById(R.id.price);
            productIv = itemView.findViewById(R.id.product_icon);
            myAddReduce = itemView.findViewById(R.id.jiajianqi);
            delBtn = itemView.findViewById(R.id.btnDelete);
            swipeMenuLayout = itemView.findViewById(R.id.scrollView);
        }
    }
}
