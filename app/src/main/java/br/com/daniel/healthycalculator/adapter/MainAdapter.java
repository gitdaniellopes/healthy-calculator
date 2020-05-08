package br.com.daniel.healthycalculator.adapter;

import android.content.Context;
import android.content.Intent;
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
import br.com.daniel.healthycalculator.ui.ImcActivity;
import br.com.daniel.healthycalculator.ui.TmbActivity;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.TesteAdapterViewHolder>
        implements OnItemClickListener{

    private final List<MainItem> mainItens;
    private final Context context;

    public MainAdapter(List<MainItem> mainItens, Context context) {
        this.mainItens = mainItens;
        this.context = context;
    }

    @NonNull
    @Override
    public TesteAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_main, parent, false);
        return new TesteAdapterViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull TesteAdapterViewHolder holder, int position) {
        MainItem mainItem = mainItens.get(position);
        holder.bind(mainItem, this);
    }

    @Override
    public int getItemCount() {
        return mainItens.size();
    }

    @Override
    public void onClick(int position) {
        final MainItem mainItem = this.mainItens.get(position);
        switch (mainItem.getId()) {
            case 1: {
                final Intent intent = new Intent(context, ImcActivity.class);
                context.startActivity(intent);
            }
            break;
            case 2: {
                final Intent intent = new Intent(context, TmbActivity.class);
                context.startActivity(intent);
            }
            break;
        }
    }

    static class TesteAdapterViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgViewMain;
        private final TextView textViewMain;

        TesteAdapterViewHolder(@NonNull View itemView) {
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

interface OnItemClickListener {
    void onClick(int position);
}
