package com.sqh.market.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.sqh.market.MyApplication;
import com.sqh.market.R;
import com.sqh.market.adapter.HotGridViewAdapter;
import com.sqh.market.adapter.RecommendGridViewAdapter;
import com.sqh.market.constant.Constants;
import com.sqh.market.fragments.HomePage;
import com.sqh.market.models.CommodityModel;
import com.sqh.market.utils.NetUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.app.PendingIntent.getActivity;


public class PostActivity extends AppCompatActivity {

    private Button location;
    private Button destnation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication myApplication = (MyApplication) this.getApplicationContext();
        int key = myApplication.getNumber();
        setContentView(R.layout.activity_post);
        location = (Button) this.findViewById(R.id.postbutton);
        destnation = (Button) this.findViewById(R.id.postbutton2);
        if(key==0)
        {
            Toast.makeText(this,"您还未登录，请先登录",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PostActivity.this, TologinActivity.class);
            //将对应的产品id传到详情界面
            // intent.putExtra("id", listItemRecommend.get(position).getId() + "");
            startActivity(intent);
            Toast.makeText(this,"您还未登录，请先登录",Toast.LENGTH_SHORT).show();
        }
        initlistener();
        initlisteners();
    }

    public void initlistener() {
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               handle();
            }
        });

    }

    private void handle() {
        Intent intent = new Intent(this, locationActivity.class);
        startActivity(intent);
    }

    private void handles() {
        Intent intent = new Intent(this, destnationActivity.class);
        startActivity(intent);
    }

    public void initlisteners(){
        destnation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handles();
            }
        });
    }
}
