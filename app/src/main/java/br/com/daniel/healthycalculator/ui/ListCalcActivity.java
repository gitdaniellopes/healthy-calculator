package br.com.daniel.healthycalculator.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        SqlHelper db = SqlHelper.getInstance(this);

        bind();
        getRegisterSend(db);
        configureAdapterAndList();
    }

    private void configureAdapterAndList() {
        ListCalcAdapter adapter = new ListCalcAdapter(registers, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
