package com.waiphyoaungitamn.recyclerviewloadmore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> loadingList;
    private List<String> datalist;
    private MyAdapter myAdapter;
    private ProgressBar progressBar;
    private boolean isLoad;
    int currentState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        loadingList = new ArrayList<>();
        initData();
        firstTimeLoad();
        myAdapter = new MyAdapter(loadingList);
        recyclerView.setAdapter(myAdapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
               // Toast.makeText(getApplicationContext(),((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() +" " + loadingList.size() ,Toast.LENGTH_SHORT).show();
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == loadingList.size()-1) {


                        Toast.makeText(getApplicationContext()," start Loading " ,Toast.LENGTH_SHORT).show();
                        isLoad = false;

                        currentState = loadingList.size();
                       new Loading().execute();



                }
            }
        });

    }
    public void initData(){
        for(int i=0;i<100;i++){
            datalist.add("Item " + i);
        }

    }
    public void firstTimeLoad(){
        for(int i=0;i<15;i++){
            loadingList.add(datalist.get(i));
        }
    }



    class Loading extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(!isLoad)
                for(int i=loadingList.size();i<currentState + 10;i++){
                    if(i<99)

                    loadingList.add(datalist.get(i));
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            isLoad = true;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"Load Successful",Toast.LENGTH_SHORT).show();
        }
    }

}
