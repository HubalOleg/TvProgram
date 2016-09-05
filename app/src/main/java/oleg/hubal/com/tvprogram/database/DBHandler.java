package oleg.hubal.com.tvprogram.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import oleg.hubal.com.tvprogram.database.model.Channel;


/**
 * Created by User on 05.09.2016.
 */
public class DBHandler extends SQLiteOpenHelper implements ChannelListener {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "ProgramDatabase.db";
    private static final String TABLE_NAME = "channel_table";
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "_name";
    private static final String KEY_TVURL = "_tvurl";
    private static final String KEY_CATEGORY = "_category";

    String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID +
            " INTEGER PRIMARY KEY," + KEY_NAME +" TEXT,"+ KEY_TVURL +
            " TEXT,"+ KEY_CATEGORY+" TEXT)";
    String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void addChannel(Channel chanel) {

    }

    @Override
    public ArrayList<Channel> getAllChannel() {
        return null;
    }

    @Override
    public int getCHannelCount() {
        return 0;
    }
}
