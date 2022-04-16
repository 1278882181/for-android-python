package com.sqh.market;

import android.app.Application;

public class MyApplication extends Application {
    private int number;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
