package com.finalproject.seniordesignproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.finalproject.seniordesignproject.Adapter.CatAdapter;
import com.finalproject.seniordesignproject.Cat;
import com.finalproject.seniordesignproject.CatDetails;
import com.finalproject.seniordesignproject.R;


public class HomePage extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private CatAdapter adapter;
    static public CatDetails catDetail;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_menu_add_cat){
            //Intent Geçiş
            Intent reportCat = new Intent(this, CatReportActivity.class);
            finish();
            startActivity(reportCat);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mRecycleView = (RecyclerView) findViewById(R.id.rv);
        adapter = new CatAdapter(Cat.getData(this),this);

        mRecycleView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this,1);
        mRecycleView.setLayoutManager(manager);
        mRecycleView.addItemDecoration(new GridManagerDecoration());
        mRecycleView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Cat cat) {
                catDetail = new CatDetails(cat.getKediİsmi(), cat.getKediIrki(), cat.getKediRengi(), cat.getKediResim(),cat.getKediSehir(),cat.getKediAciklama());


                Intent detayIntent = new Intent(HomePage.this,DetailsActivity.class);
                startActivity(detayIntent);
            }
        });


    }

    private class GridManagerDecoration extends RecyclerView.ItemDecoration{

        public void getItemOffsets(Rect outRect, View view,RecyclerView parent,RecyclerView.State state){
            outRect.bottom = 25;
        }
    }

}