package com.studycase.vanard.vianrasyiddiyatma_1202154186_modul4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toListNama(View view)
    {
        Intent toList = new Intent(this, ListNamaMahasiswa.class);
        startActivity(toList);
    }

    public void toCariGambar(View view)
    {
        Intent toSearch = new Intent(this, CariGambar.class);
        startActivity(toSearch);
    }
}
