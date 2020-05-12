package br.com.daniel.healthycalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import br.com.daniel.healthycalculator.model.Register;

public class ListCalcAdapter extends RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder> {

    private List<Register> registers;
    private Context context;

    public ListCalcAdapter(List<Register> registers, Context context) {
        this.registers = registers;
        this.context = context;
    }

    @NonNull
    @Override
    public ListCalcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View viewCreated = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1,
                        parent, false);
        return new ListCalcViewHolder(viewCreated);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCalcViewHolder holder, int position) {
        final Register register = registers.get(position);
        holder.bind(register);
    }

    @Override
    public int getItemCount() {
        return registers.size();
    }

    static class ListCalcViewHolder extends RecyclerView.ViewHolder {

        ListCalcViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Register register) {
            ((TextView) itemView).setText(String.format(new Locale("pt", "BR"),
                    "Resultado: %.2f, data: %s", register.getResponse(), register.getCreatedDate()));
        }
    }
}
