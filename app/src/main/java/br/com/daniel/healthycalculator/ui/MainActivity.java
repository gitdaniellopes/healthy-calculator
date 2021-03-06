package br.com.daniel.healthycalculator.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.daniel.healthycalculator.R;
import br.com.daniel.healthycalculator.adapter.MainAdapter;
import br.com.daniel.healthycalculator.model.MainItem;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MainItem> mainItems;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();
        mainItems = new ArrayList<>();
        createListItems(mainItems);
        configureAdapterAndList(mainItems);
    }

    private void bind() {
        recyclerView = findViewById(R.id.recycle_view);
    }

    private void configureAdapterAndList(ArrayList<MainItem> mainItems) {
        adapter = new MainAdapter(mainItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapterOnClick();
    }

    private void createListItems(ArrayList<MainItem> mainItems) {
        mainItems.add(new MainItem(1, R.drawable.ic_esportes_e_competicao,
                R.string.imc, 0xFF53AAEF));
        mainItems.add(new MainItem(2, R.drawable.ic_esportes,
                R.string.tmb, 0xFF7BC37E));
        mainItems.add(new MainItem(3, R.drawable.ic_cuidados_de_saude_e_medicos,
                R.string.fcm, 0xFFF4C9D8));
    }

    public void adapterOnClick() {
        adapter.setOnItemClickListener(position -> {
            final MainItem mainItem = mainItems.get(position);
            switch (mainItem.getId()) {
                case 1: {
                    final Intent intent = new Intent(this, ImcActivity.class);
                    this.startActivity(intent);
                }
                break;
                case 2: {
                    final Intent intent = new Intent(this, TmbActivity.class);
                    this.startActivity(intent);
                }
                break;
                case 3: {
                    final Intent intent = new Intent(this, FcmActivity.class);
                    this.startActivity(intent);
                }
                break;
            }
        });
    }
}
