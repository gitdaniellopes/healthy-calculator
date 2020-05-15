package br.com.daniel.healthycalculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.daniel.healthycalculator.R;
import br.com.daniel.healthycalculator.model.Register;

import static br.com.daniel.healthycalculator.helper.formatResponse.responseFormat;

public class ListCalcAdapter extends RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder> {

    private List<Register> registers;
    private Context context;
    private OnClickListener onClickListener = null;


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onItemClick(View view, Register register, int pos);

        void onItemLongClick(View view, Register register, int pos);
    }

    public ListCalcAdapter(List<Register> registers, Context context) {
        this.registers = registers;
        this.context = context;
    }

    @NonNull
    @Override
    public ListCalcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View viewCreated = LayoutInflater.from(context)
                .inflate(R.layout.item_registers,
                        parent, false);
        return new ListCalcViewHolder(viewCreated);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCalcViewHolder holder, int position) {
        final Register register = registers.get(position);
        holder.bind(register);

        holder.viewConstraint.setOnClickListener(v -> {
            if (onClickListener == null) return;
            onClickListener.onItemClick(v, register, position);
        });

        holder.viewConstraint.setOnLongClickListener(v -> {
            if (onClickListener == null) return false;
            onClickListener.onItemLongClick(v, register, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return registers.size();
    }

    static class ListCalcViewHolder extends RecyclerView.ViewHolder {

        private final View viewConstraint;
        private final TextView response;
        private final TextView created_date;

        ListCalcViewHolder(@NonNull View itemView) {
            super(itemView);
            viewConstraint = itemView.findViewById(R.id.constraint_view);
            response = itemView.findViewById(R.id.response);
            created_date = itemView.findViewById(R.id.created_date);
        }

        void bind(Register register) {
            response.setText(responseFormat(register.getResponse()));
            created_date.setText(register.getCreatedDate());

//            ((TextView) itemView).setText(String.format(new Locale("pt", "BR"),
//                    "Resultado: %.2f, data: %s", register.getResponse(), register.getCreatedDate()));
        }
    }

    public void remove(int posicao) {
        registers.remove(posicao);
        notifyItemRemoved(posicao);
    }

}
