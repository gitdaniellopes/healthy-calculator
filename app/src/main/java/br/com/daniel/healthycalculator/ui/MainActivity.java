package br.com.daniel.healthycalculator.ui;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();
        ArrayList<MainItem> mainItems = new ArrayList<>();
        createListItems(mainItems);
        configureAdapterAndList(mainItems);
    }

    private void bind() {
        recyclerView = findViewById(R.id.recycle_view);
    }

    private void configureAdapterAndList(ArrayList<MainItem> mainItems) {
        MainAdapter adapter = new MainAdapter(mainItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void createListItems(ArrayList<MainItem> mainItems) {
        mainItems.add(new MainItem(1, R.drawable.baseline_announcement_black_24dp,
                R.string.imc, 0xFFFF00FF));
        mainItems.add(new MainItem(2, R.drawable.baseline_announcement_black_24dp,
                R.string.tmb, 0xFFFFFF00));
    }
}
