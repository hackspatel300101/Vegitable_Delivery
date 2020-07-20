package com.example.vegitable_delivery.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegitable_delivery.R;
import com.example.vegitable_delivery.model.Product_details;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;


public class Vegetable_list extends RecyclerView.Adapter<Vegetable_list.Myholder>{
    Context context;
    List<Product_details> lits;

    public Vegetable_list(Context context, List<Product_details> lits) {
        this.context = context;
        this.lits = lits;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.price_list_view, parent, false);

        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        Picasso.get().load(lits.get(position).getImageurl()).into(holder.iv);
        holder.name.setText(lits.get(position).getName());
        holder.weight.setText(lits.get(position).getWeight());
        holder.weight1.setText(lits.get(position).getWwight1());
        holder.price.setText(lits.get(position).getPrice());
        holder.price1.setText(lits.get(position).getPrice1());
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY");
        String dateString = sdf.format(date);
        holder.time.setText(dateString);



    }

    @Override
    public int getItemCount() {
        return lits.size();
    }

    class Myholder extends RecyclerView.ViewHolder{
            TextView name,weight,weight1,price,price1,time;
            ImageView iv;
        public Myholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.veg_name);
            weight=itemView.findViewById(R.id.veg_unit);
            weight1=itemView.findViewById(R.id.veg_unit1);
            price=itemView.findViewById(R.id.veg_price);
            price1=itemView.findViewById(R.id.veg_price1);
            iv=itemView.findViewById(R.id.veg_iv);
            time=itemView.findViewById(R.id.time);
        }

    }


}
