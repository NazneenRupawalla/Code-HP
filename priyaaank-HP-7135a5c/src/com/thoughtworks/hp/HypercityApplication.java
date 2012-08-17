package com.thoughtworks.hp;

import android.app.Application;

import com.thoughtworks.hp.datastore.HpDatabase;

public class HypercityApplication extends Application {

    private static HpDatabase database;

    @Override
    public void onCreate() {
        if(HypercityApplication.database == null) database = HpDatabase.database(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        if(database != null) database.close();
    }

    public HpDatabase database() {
        return database;
    }
}
