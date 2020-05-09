package br.com.daniel.healthycalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.daniel.healthycalculator.R;
import br.com.daniel.healthycalculator.model.MainItem;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private final List<MainItem> mainItens;
    private final Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public MainAdapter(List<MainItem> mainItens, Context context) {
        this.mainItens = mainItens;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_main, parent, false);
        return new MainViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        MainItem mainItem = mainItens.get(position);
        holder.bind(mainItem, listener);
    }

    @Override
    public int getItemCount() {
        return mainItens.size();
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgViewMain;
        private final TextView textViewMain;

        MainViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewMain = itemView.findViewById(R.id.item_main_img);
            textViewMain = itemView.findViewById(R.id.item_main_text);
        }

        void bind(MainItem mainItem, OnItemClickListener listener) {
            fillInFields(mainItem, listener);
        }

        private void fillInFields(MainItem mainItem, OnItemClickListener listener) {
            itemView.setBackgroundColor(mainItem.getColorValueId());
            imgViewMain.setImageResource(mainItem.getImgId());
            textViewMain.setText(mainItem.getTextId());

            itemView.setOnClickListener(v -> {
                listener.onClick(getAdapterPosition());
            });

        }
    }

}


