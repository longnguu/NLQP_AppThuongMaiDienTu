package com.example.appthuongmaidientu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appthuongmaidientu.R;
import com.example.appthuongmaidientu.model.CartList;
import com.example.appthuongmaidientu.model.ChatList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<CartList> cartLists;
    Context context;

    public CartAdapter(List<CartList> cartLists, Context context) {
        this.cartLists = cartLists;
        this.context = context;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartList cartList = cartLists.get(position);
        Picasso.get().load(cartList.getHinhanh()).into(holder.imgCart);
        holder.tvGia.setText(cartList.getTenSP());
        holder.tvName.setText(cartList.getGia());
        holder.solg.setText(cartList.getSoLuongMua());
        System.out.println(cartList.getHinhanh());
    }
    public void updateCart(List<CartList> cartLists){
        this.cartLists=cartLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cartLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCart;
        TextView tvName,tvGia,solg;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkboxCartt);
            imgCart=itemView.findViewById(R.id.imgCartt);
            tvGia=itemView.findViewById(R.id.giaCartt);
            tvName=itemView.findViewById(R.id.nameCartt);
            solg = itemView.findViewById(R.id.edt_slCartt);
        }
    }
}
