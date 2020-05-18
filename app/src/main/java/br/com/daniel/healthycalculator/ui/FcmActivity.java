package br.com.daniel.healthycalculator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.com.daniel.healthycalculator.R;
import br.com.daniel.healthycalculator.dataBase.SqlHelper;

public class FcmActivity extends AppCompatActivity {

    private EditText editAge;
    private SqlHelper helper;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);

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
                final String sAge = editAge.getText().toString();
                final int age = Integer.parseInt(sAge);

                double fcm = calculateFcm(age);
                createDialog(fcm);
            }
        });
    }

    private void createDialog(double fcm) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.fcm_response, fcm))
                .setMessage(R.string.fcm_message)
                .setNegativeButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    save(fcm);
                })
                .create();
        alertDialog.show();

    }

    private void save(double fcm) {
        final long calcId = helper.addItem(SqlHelper.TYPE_FCM, fcm);
        verifyCalcId(calcId);
    }

    private void verifyCalcId(long calcId) {
        if (calcId > 0){
            Toast.makeText(this, R.string.calc_saved,
                    Toast.LENGTH_LONG).show();
            showListCalcActivity();
        }
    }

    private void showListCalcActivity() {
        Intent intent = new Intent(this, ListCalcActivity.class);
        intent.putExtra("type", SqlHelper.TYPE_FCM);
        startActivity(intent);
    }


    private double calculateFcm(int age) {
        return 208 - (0.7 * age);
    }

    private boolean validate() {
        return !editAge.getText().toString().equals("0")
                && !editAge.getText().toString().isEmpty();
    }

    private void bind() {
        editAge = findViewById(R.id.fcm_age);
        send = findViewById(R.id.fcm_send);
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
            showListCalcActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
