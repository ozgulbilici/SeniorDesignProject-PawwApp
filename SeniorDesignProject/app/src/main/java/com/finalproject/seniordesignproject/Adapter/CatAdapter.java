package com.finalproject.seniordesignproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.seniordesignproject.Cat;
import com.finalproject.seniordesignproject.R;

import java.util.ArrayList;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatHolder> {
    private ArrayList<Cat> catList;
    private Context context;
    private OnItemClickListener listener;

    public CatAdapter(ArrayList<Cat> catList, Context context) {
        this.catList = catList;
        this.context = context;
    }

    @NonNull
    @Override
    public CatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cat_item,parent,false);
        return new CatHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CatHolder holder, int position) {
        Cat cat = catList.get(position);
        holder.setData(cat);
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }
    class CatHolder extends RecyclerView.ViewHolder{

        TextView txtKediIsmi,txtKediIrki,txtKediRengi,txtKediSehir,txtCatAciklama;
        ImageView imgKediResim;
        public CatHolder(View itemView){
            super(itemView);

            txtKediIsmi = (TextView)itemView.findViewById(R.id.cat_item_textViewKediAdi);
            txtKediIrki = (TextView)itemView.findViewById(R.id.cat_item_textViewKediIrki);
            txtKediRengi = (TextView)itemView.findViewById(R.id.cat_item_textViewKediRengi);
            imgKediResim= (ImageView) itemView.findViewById(R.id.cat_item_imgViewResim);
            txtKediSehir = (TextView)itemView.findViewById(R.id.cat_item_textViewKediSehir);
            txtCatAciklama = (TextView)itemView.findViewById(R.id.cat_item_textViewKediAciklama);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(catList.get(position));
                    }
                }
            });
        }

        public void setData(Cat cat){
            this.txtKediIsmi.setText(cat.getKediİsmi());
            this.txtKediIrki.setText(cat.getKediIrki());
            this.txtKediRengi.setText(cat.getKediRengi());
            this.imgKediResim.setImageBitmap(cat.getKediResim());
            this.txtKediSehir.setText(cat.getKediSehir());
            this.txtCatAciklama.setText(cat.getKediAciklama());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Cat cat);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
