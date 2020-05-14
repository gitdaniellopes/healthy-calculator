package br.com.daniel.healthycalculator.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.daniel.healthycalculator.R;
import br.com.daniel.healthycalculator.adapter.ListCalcAdapter;
import br.com.daniel.healthycalculator.dataBase.SqlHelper;
import br.com.daniel.healthycalculator.model.Register;

public class ListCalcActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Register> registers = new ArrayList<>();
    private ListCalcAdapter calcAdapter;
    private SqlHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        db = SqlHelper.getInstance(this);

        bind();
        getRegisterSend(db);
        configureAdapterAndList();
    }

    private void configureAdapterAndList() {
        calcAdapter = new ListCalcAdapter(registers, this);
        recyclerView.setAdapter(calcAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        eventsClickRecycleView();
    }

    private void eventsClickRecycleView() {
        calcAdapter.setOnClickListener(new ListCalcAdapter.OnClickListener() {
            @Override
            public void onItemClick(View view, Register register, int pos) {
            }

            @Override
            public void onItemLongClick(View view, Register register, int pos) {
                final Register register1 = registers.get(pos);
                createDialog(register1, pos);
            }
        });
    }

    private void createDialog(Register register1, int pos) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.delete)
                .setMessage("Deseja deletar: " + register1.getType() + " ?")
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    delete(register1);
                    calcAdapter.remove(pos);
                })
                .create();
        alertDialog.show();

    }

    private void delete(Register register1) {
        db.deleteItem(register1);
    }

    private void getRegisterSend(SqlHelper db) {
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String type = extras.getString("type");
            registers.addAll(db.getRegisters(type));
        }
    }

    private void bind() {
        recyclerView = findViewById(R.id.recycle_view_list);
    }
}
