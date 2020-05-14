package br.com.daniel.healthycalculator.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.daniel.healthycalculator.model.Register;

public class SqlHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fitness.db";
    private static int DB_VERSION = 1;


    //Padrão singleton
    private static SqlHelper INSTANCE;
    public static String TYPE_IMC = "imc";
    public static String TYPE_TMB = "tmb";

    public static synchronized SqlHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SqlHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE calc (id INTEGER PRIMARY KEY, type_calc TEXT, res DECIMAL, created_date DATETIME)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteItem(Register register) {
        final SQLiteDatabase db = getReadableDatabase();
        db.beginTransaction();
        try {
            db.delete("calc",
                    "id = ?",
                    new String[]{String.valueOf(register.getId())});

            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Teste", e.getMessage(), e);
        } finally {
            db.endTransaction();
        }
    }


    public List<Register> getRegisters(String type) {
        List<Register> registers = new ArrayList<>();

        final SQLiteDatabase db = getReadableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * FROM calc WHERE type_calc = ?",
                new String[]{type});

        try {
            if (cursor.moveToFirst()) {
                do {
                    final Register register = new Register();
                    register.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    register.setType(cursor.getString(cursor.getColumnIndex("type_calc")));
                    register.setResponse(cursor.getDouble(cursor.getColumnIndex("res")));
                    register.setCreatedDate(cursor.getString(cursor.getColumnIndex("created_date")));
                    registers.add(register);

                } while (cursor.moveToNext());

                db.setTransactionSuccessful();

            }

        } catch (Exception e) {
            Log.e("Teste", e.getMessage(), e);
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return registers;
    }

    public long addItem(String type, double response) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        long calcId = 0;

        try {

            final ContentValues values = new ContentValues();
            values.put("type_calc", type);
            values.put("res", response);
            final String dateNow = getDateNow();
            values.put("created_date", dateNow);
            calcId = db.insertOrThrow("calc", null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("Teste", e.getMessage(), e);
        } finally {
            db.endTransaction();
        }
        return calcId;
    }

    private String getDateNow() {
        final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",
                new Locale("pt", "BR"));
        return format.format(Calendar.getInstance().getTime());
    }
}
