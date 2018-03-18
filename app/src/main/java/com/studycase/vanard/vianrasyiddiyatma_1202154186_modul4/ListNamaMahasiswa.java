package com.studycase.vanard.vianrasyiddiyatma_1202154186_modul4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class ListNamaMahasiswa extends AppCompatActivity {
    //declare variable
    private ListView mList;
    private Button mSync;
    private ProgressBar mPgBar;
    private ProgressDialog mProgressD;
    private String[] mNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nama_mahasiswa);
        //bind data
        mSync = findViewById(R.id.list_sync_btn);
        mList = findViewById(R.id.list_nama_mh);
        mPgBar = findViewById(R.id.pgBar);
        //mengambil data string
        mNames = getResources().getStringArray(R.array.names);
        //set adapter menggunakan design drop down default
        mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                new ArrayList<String>()));
        //set tombol ketika diklik
        mSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menjalankan class dan asynctask
                new SyncTask().execute();

            }
        });
    }

    class SyncTask extends AsyncTask<Void,String,Void>
    {
        //declare variable
        private ArrayAdapter<String> adapter;
        int i = 1;

        @Override
        protected void onPreExecute() {
            adapter = (ArrayAdapter<String>)mList.getAdapter();
            //setting progress dialog
            mProgressD = new ProgressDialog(ListNamaMahasiswa.this);
            mProgressD.setCancelable(false);
            mProgressD.setTitle("Loading Data");
            mProgressD.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressD.setMax(100);
            mProgressD.setProgress(0);
            //setting button cancel
            mProgressD.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new SyncTask().cancel(true);
                    mPgBar.setVisibility(View.VISIBLE);
                    dialogInterface.dismiss();
                }
            });
            //menampilkan progress dialog
            mProgressD.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (String mName : mNames)
            {
                publishProgress(mName);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isCancelled()){
                    new SyncTask().cancel(true);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //menambahkan data ke listview
            adapter.add(values[0]);
            //formula
            Integer ps = (int)((i/(float)mNames.length)*100);
            //setting progress sesuai hitungan formula
            mPgBar.setProgress(ps);
            mProgressD.setProgress(ps);
            //setting pesan sesuai hitungan formula
            mProgressD.setMessage(String.valueOf(ps + "%"));
            //increament
            i++;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //menghilangkan progressbar
            mPgBar.setVisibility(View.GONE);
            //menghilangkan progress dialog
            mProgressD.dismiss();
            Toast.makeText(getApplicationContext(), "All the names are were add Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
