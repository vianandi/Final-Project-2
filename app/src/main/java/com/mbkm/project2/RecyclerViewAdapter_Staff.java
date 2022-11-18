package com.mbkm.project2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mbkm.project2.Model.Staff;

import java.util.ArrayList;

public class RecyclerViewAdapter_Staff extends RecyclerView.Adapter<RecyclerViewAdapter_Staff.ViewHolder>{
    Context context;
    ArrayList<Staff> list;


    public RecyclerViewAdapter_Staff(ArrayList<Staff> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.staff_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Staff staff = list.get(position);
        holder.txtNamaStaff.setText(staff.getName());
        holder.txtTitleStaff.setText(staff.getPhone());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgStaff;
        TextView txtNamaStaff;
        TextView txtTitleStaff;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStaff = itemView.findViewById(R.id.imgStaff);
            txtNamaStaff = itemView.findViewById(R.id.txtNamaStaff);
            txtTitleStaff = itemView.findViewById(R.id.txtTitleStaff);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
