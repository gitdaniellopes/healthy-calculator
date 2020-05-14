package br.com.daniel.healthycalculator.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import br.com.daniel.healthycalculator.R;
import br.com.daniel.healthycalculator.dataBase.SqlHelper;

public class TmbActivity extends AppCompatActivity {

    private EditText editWeight;
    private EditText editHeight;
    private EditText editAge;
    private Spinner spinner;
    private SqlHelper helper;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmb);

        helper = SqlHelper.getInstance(this);
        bind();
        configuraBotao();
    }

    private void configuraBotao() {
        send.setOnClickListener(v -> {
            if (!validate()) {
                Toast.makeText(TmbActivity.this, R.string.fields_message,
                        Toast.LENGTH_LONG).show();
            } else {
                final String sHeight = editHeight.getText().toString();
                final String sWeight = editWeight.getText().toString();
                final String sAge = editAge.getText().toString();

                final int height = Integer.parseInt(sHeight);
                final double weight = Double.parseDouble(sWeight);
                final int age = Integer.parseInt(sAge);

                double tmb = calculateTmb(height, weight, age);
                double cal = tmbResponse(tmb);
                criaDialog(tmb, cal);
                escondeBotao();
            }
        });
    }

    private void escondeBotao() {
        //teclado esconde quando clicamos no botao calcular
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert im != null;
        im.hideSoftInputFromWindow(editHeight.getWindowToken(), 0);
        im.hideSoftInputFromWindow(editWeight.getWindowToken(), 0);
        im.hideSoftInputFromWindow(editAge.getWindowToken(), 0);
    }

    private void criaDialog(double tmb, double cal) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.tmb_response, tmb))
                .setMessage(String.format(new Locale("pt", "BR"), "cal: %.2f", cal))
                .setNegativeButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(R.string.save, (dialog, which) -> salvaTmb(tmb))
                .create();
        alertDialog.show();
    }

    private void salvaTmb(double tmb) {
        long calcId = helper.addItem(SqlHelper.TYPE_TMB, tmb);
        verificaId(calcId);
    }

    private void verificaId(long calcId) {
        if (calcId > 0) {
            Toast.makeText(this, R.string.calc_saved,
                    Toast.LENGTH_SHORT).show();
            openListCalcActivity();
        }
    }

    private void openListCalcActivity() {
        Intent intent = new Intent(TmbActivity.this, ListCalcActivity.class);
        intent.putExtra("type", SqlHelper.TYPE_TMB);
        TmbActivity.this.startActivity(intent);
    }

    private double tmbResponse(double tmb) {
        final int selectedItemPosition = spinner.getSelectedItemPosition();
        switch (selectedItemPosition) {
            case 0:
                return tmb * 1.2;
            case 1:
                return tmb * 3.75;
            case 2:
                return tmb * 1.55;
            case 3:
                return tmb * 1.725;
            case 4:
                return tmb * 1.9;
            default:
                return 0;
        }
    }

    private double calculateTmb(int height, double weight, int age) {
        return 66 + (weight * 13.8) + (5 * height) - (6.8 * age);
    }

    private boolean validate() {
        return !editHeight.getText().toString().startsWith("0")
                && !editWeight.getText().toString().startsWith("0")
                && !editAge.getText().toString().startsWith("0")
                && !editAge.getText().toString().isEmpty()
                && !editHeight.getText().toString().isEmpty()
                && !editWeight.getText().toString().isEmpty();
    }

    private void bind() {
        editWeight = findViewById(R.id.tmb_weight);
        editHeight = findViewById(R.id.tmb_height);
        editAge = findViewById(R.id.tmb_age);
        spinner = findViewById(R.id.spinner_form);
        send = findViewById(R.id.tmb_send);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.menu_list) {
            openListCalcActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
