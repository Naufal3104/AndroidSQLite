package com.example.androidsqlite;

import androidx.appcompat.app.AppCompatActivity;
import android.view.*;
import java.util.ArrayList;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    DatabaseManager dm;
    private EditText eNm, eTelp, ekode, eEmail, eJarak, ePoint;
    private Button bBaru, bSimpan, bUbah, bHapus;
    TableLayout tabel4data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dm = new DatabaseManager(this);
        tabel4data = (TableLayout) findViewById(R.id.table_data);
        ekode = (EditText) findViewById(R.id.edTextKode);
        eNm = (EditText) findViewById(R.id.edTextNama);
        eTelp = (EditText) findViewById(R.id.edTextPhone);
        eEmail = (EditText) findViewById(R.id.editTextEmail);
        eJarak = (EditText) findViewById(R.id.editTextJarak);
        ePoint = (EditText) findViewById(R.id.editTextPoint);
        bSimpan = (Button) findViewById(R.id.btnSimpan);
        bUbah = (Button) findViewById(R.id.btnUbah);
        bHapus = (Button) findViewById(R.id.btnHapus);
        bBaru = (Button) findViewById(R.id.btnBaru);

        bSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanTable();
            }
        });
        updateTable();
        bUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubahTable();
            }
        });
        updateTable();
        bHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusTable();
            }
        });
        updateTable();
        bBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kosongkanField();
            }
        });
        updateTable();
    }

    protected void simpanTable() {
        try {
            dm.addRow(Integer.parseInt(ekode.getText().toString()),
                    eNm.getText().toString(),
                    eTelp.getText().toString(),
                    eEmail.getText().toString(),
                    Integer.parseInt(eJarak.getText().toString()),
                    Integer.parseInt(ePoint.getText().toString()));

            Toast.makeText(getBaseContext(), eNm.getText().toString() + ", berhasil disimpan", Toast.LENGTH_SHORT).show();
            updateTable();
            kosongkanField();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "gagal simpan, " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void ubahTable() {
        try {
            dm.addRow(Integer.parseInt(ekode.getText().toString()),
                    eNm.getText().toString(),
                    eTelp.getText().toString(),
                    eEmail.getText().toString(),
                    Integer.parseInt(eJarak.getText().toString()),
                    Integer.parseInt(ePoint.getText().toString()));
            Toast.makeText(getBaseContext(), eNm.getText().toString() + ", berhasil diubah", Toast.LENGTH_SHORT).show();
            updateTable();
            kosongkanField();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "gagal ubah, " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void hapusTable() {
        try {
            dm.DeleteRecord(Integer.parseInt(ekode.getText().toString()));
            Toast.makeText(getBaseContext(), "Kode " + ekode.getText().toString() + ", Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
            updateTable();
            kosongkanField();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "gagal hapus, " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void kosongkanField() {
        eNm.setText("");
        eTelp.setText("");
        ekode.setText("");
    }

    protected void updateTable() {
        while (tabel4data.getChildCount() > 1) {
            tabel4data.removeViewAt(1);
        }
        ArrayList<ArrayList<Object>> data = dm.ambilSemuaBaris();
        for (int posisi = 0; posisi < data.size(); posisi++) {
            TableRow tabelBaris = new TableRow(this);
            ArrayList<Object> baris = data.get(posisi);
            TextView idTxt = new TextView(this);

            idTxt.setTextSize(18);
            idTxt.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            idTxt.setText(" " + baris.get(0).toString() + ".");
            tabelBaris.addView(idTxt);

            TextView namaTxt = new TextView(this);
            namaTxt.setTextSize(18);
            namaTxt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            namaTxt.setText(baris.get(1).toString());
            tabelBaris.addView(namaTxt);

            TextView TelpTxt = new TextView(this);
            TelpTxt.setTextSize(18);
            TelpTxt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TelpTxt.setText(baris.get(2).toString());
            tabelBaris.addView(TelpTxt);

            TextView emailTxt = new TextView(this);
            emailTxt.setTextSize(18);
            emailTxt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            emailTxt.setText(baris.get(3).toString()); // Email
            tabelBaris.addView(emailTxt);

            TextView jarakTxt = new TextView(this);
            jarakTxt.setTextSize(18);
            jarakTxt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            jarakTxt.setText(baris.get(4).toString()); // Jarak
            tabelBaris.addView(jarakTxt);

            TextView pointTxt = new TextView(this);
            pointTxt.setTextSize(18);
            pointTxt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            pointTxt.setText(baris.get(5).toString()); // Point
            tabelBaris.addView(pointTxt);

            tabel4data.addView(tabelBaris);
        }
    }
}