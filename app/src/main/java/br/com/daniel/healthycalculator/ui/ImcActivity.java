package br.com.daniel.healthycalculator.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.com.daniel.healthycalculator.R;
import br.com.daniel.healthycalculator.dataBase.SqlHelper;

public class ImcActivity extends AppCompatActivity {

    private EditText editWeight;
    private EditText editHeight;
    private Button send;
    private SqlHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        helper = SqlHelper.getInstance(this);
        bind();
        configureButton();
    }

    private void configureButton() {
        send.setOnClickListener(v -> {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_message,
                        Toast.LENGTH_LONG).show();
            } else {

                final String sWeight = editWeight.getText().toString();
                final String sHeight = editHeight.getText().toString();
                final double weight = Double.parseDouble(sWeight);
                final int height = Integer.parseInt(sHeight);

                double imc = calculateImc(weight, height);
                int respId = imcResponse(imc);
                createDialog(imc, respId);
                hiddenButton();
            }
        });
    }

    private void createDialog(double imc, int respId) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, imc))
                .setMessage(respId)
                .setNegativeButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    save(imc);
                })
                .create();
        alertDialog.show();

    }

    private void openListCalcActivity() {
        Intent intent = new Intent(ImcActivity.this, ListCalcActivity.class);
        intent.putExtra("type", SqlHelper.TYPE_IMC);
        ImcActivity.this.startActivity(intent);
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

    private void save(double imc) {
        final long calcId = helper.addItem(SqlHelper.TYPE_IMC, imc);
        verifyCalcId(calcId);
    }

    private void verifyCalcId(long calcId) {
        if (calcId > 0) {
            Toast.makeText(this, R.string.calc_saved,
                    Toast.LENGTH_LONG).show();
            showListCalcActivity();
        }
    }

    private void showListCalcActivity() {
        Intent intent = new Intent(this, ListCalcActivity.class);
        intent.putExtra("type", SqlHelper.TYPE_IMC);
        startActivity(intent);
    }

    private void hiddenButton() {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert im != null;
        im.hideSoftInputFromWindow(editHeight.getWindowToken(), 0);
        im.hideSoftInputFromWindow(editWeight.getWindowToken(), 0);
    }

    @StringRes
    private int imcResponse(double imc) {
        if (imc < 15)
            return R.string.imc_severely_low_wight;
        else if (imc < 16)
            return R.string.imc_very_low_weight;
        else if (imc < 18.5)
            return R.string.imc_low_wight;
        else if (imc < 25)
            return R.string.normal;
        else if (imc < 30)
            return R.string.imc_high_wight;
        else if (imc < 35)
            return R.string.imc_so_high_wight;
        else if (imc < 40)
            return R.string.imc_severely_high_wight;
        else
            return R.string.imc_extreme_wight;
    }

    private double calculateImc(double weight, int height) {
        return weight / (((double) height / 100) * ((double) height / 100));
    }

    private boolean validate() {
        return !editHeight.getText().toString().startsWith("0")
                && !editWeight.getText().toString().startsWith("0")
                && !editHeight.getText().toString().isEmpty()
                && !editWeight.getText().toString().isEmpty();
    }

    private void bind() {
        editWeight = findViewById(R.id.imc_weight);
        editHeight = findViewById(R.id.imc_height);
        send = findViewById(R.id.imc_send);
    }
}
