package com.studycase.vanard.vianrasyiddiyatma_1202154186_modul4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CariGambar extends AppCompatActivity {
    //declare variable
    private EditText mLink;
    private Button mBtnCari;
    private ImageView mGambar;
    private ProgressDialog mPgD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_gambar);
        //binding data
        mLink = findViewById(R.id.et1);
        mBtnCari = findViewById(R.id.klik_cari_btn);
        mGambar = findViewById(R.id.gambar);
        //set tombol ketika diklik
        mBtnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menjalankan method
                loadImage();
            }
        });
    }

    private void loadImage()
    {
        //mengambil text dari EditText
        String imgUrl = mLink.getText().toString();
        //menjalankan class dan asynctask
        new loadGambar().execute(imgUrl);
    }

    class loadGambar extends AsyncTask<String,Void,Bitmap>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //inisiasi progress dialog
            mPgD = new ProgressDialog(CariGambar.this);
            //setting progress dialog
            mPgD.setCancelable(false);
            mPgD.setTitle("Loading Gambar");
            //menampilkan progress dialog
            mPgD.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                //inisiai url gambar
                URL uri = new URL(strings[0]);
                //mengambil gambar dan mengubah menjadi bitmap
                bitmap = BitmapFactory.decodeStream((InputStream)uri.getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //menset gambar ke imageview
            mGambar.setImageBitmap(bitmap);
            //menghilangkan progress dialog
            mPgD.dismiss();
            Toast.makeText(getApplicationContext(), "Image are Successfully loaded", Toast.LENGTH_SHORT).show();
        }
    }
}
