package com.sqh.market.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.sqh.market.MyApplication;
import com.sqh.market.R;

import static android.app.PendingIntent.getActivity;

public class locationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication myApplication = (MyApplication) this.getApplicationContext();
        int key= myApplication.getNumber();
        if(key==1)
        {
            setContentView(R.layout.activity_location);
        }
        if(key==0)
        {
            Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(locationActivity.this, TologinActivity.class);
            //将对应的产品id传到详情界面
            // intent.putExtra("id", listItemRecommend.get(position).getId() + "");
            startActivity(intent);
        }
    }

}
