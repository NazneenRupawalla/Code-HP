package com.thoughtworks.hp.datastore;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.thoughtworks.hp.R;

public class HpDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HP";

    private static final int DATABASE_VERSION = 3;
    private static final String TAG = HpDatabase.class.getSimpleName();
    private Context mContext;

    private static HpDatabase singleInstanceOfDatabase;

    private HpDatabase(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = mContext;
    }

    public static HpDatabase database(Context mContext) {
        if(singleInstanceOfDatabase == null) singleInstanceOfDatabase = new HpDatabase(mContext);
        return singleInstanceOfDatabase;
    }

    @Override
    public void finalize() {
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "Creating HP database for first time");
        String[] creationSqls = mContext.getString(R.string.create_sqls).split(";");
        String[] dataSqls = mContext.getString(R.string.seed_data).split(";");
        db.beginTransaction();
        try {
            executeManySqlStatements(db, combinedSqls(creationSqls, dataSqls));
            db.setTransactionSuccessful();
        } catch(SQLException e) {
            Log.e(TAG, "Error occurred while creating database. Error is " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    private String[] combinedSqls(String[] creationSqls, String[] dataSqls) {
        String[] combinedSqls = new String[creationSqls.length + dataSqls.length];
        System.arraycopy(creationSqls, 0, combinedSqls, 0, creationSqls.length);
        System.arraycopy(dataSqls, 0, combinedSqls, creationSqls.length, dataSqls.length);
        return combinedSqls;
    }

    private void executeManySqlStatements(SQLiteDatabase db, String[] sqls) {
        for (String eachStatement : sqls) {
            if(eachStatement.trim().length() > 0)
                db.execSQL(eachStatement);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        // recreate database from scratch once again.
        onCreate(db);
    }
}
