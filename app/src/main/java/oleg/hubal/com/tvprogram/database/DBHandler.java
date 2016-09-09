package oleg.hubal.com.tvprogram.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import oleg.hubal.com.tvprogram.database.model.Channel;


/**
 * Created by User on 05.09.2016.
 */
public class DBHandler extends SQLiteOpenHelper implements ChannelListener {

    private static final int DB_VERSION =       1;
    private static final String DB_NAME =       "ProgramDatabase.db";
    private static final String TABLE_NAME =    "channel_table";
    private static final String KEY_ID =        "_id";
    private static final String KEY_JSON_ID =   "_json_id";
    private static final String KEY_NAME =      "_name";
    private static final String KEY_TVURL =     "_tvurl";
    private static final String KEY_CATEGORY =  "_category";
    private static final String KEY_FAVORITE =  "_favorite";

    String CREATE_TABLE =   "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID +
                            " INTEGER PRIMARY KEY," + KEY_JSON_ID + " TEXT," + KEY_NAME +
                            " TEXT,"+ KEY_TVURL + " TEXT,"+ KEY_CATEGORY +
                            " TEXT," + KEY_FAVORITE + " INTEGER)";
    String DROP_TABLE =     "DROP TABLE IF EXISTS " + TABLE_NAME;

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
    public void addChannels(ArrayList<Channel> channels) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            for(Channel channel : channels) {
                ContentValues values = new ContentValues();
                values.put(KEY_JSON_ID, channel.getJsonId());
                values.put(KEY_NAME, channel.getName());
                values.put(KEY_TVURL, channel.getTvURL());
                values.put(KEY_CATEGORY, channel.getCategory());
                values.put(KEY_FAVORITE, channel.getIsFavorite());
//                Log.d("log123", channel.getName() + " was added to database");
                db.insert(TABLE_NAME, null, values);
            }
            db.close();
        } catch (Exception e) {
            Log.e("problem", e + "");
        }
    }

    @Override
    public ArrayList<Channel> getAllChannels() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Channel> channelList = null;
        try{
            channelList = new ArrayList<>();
            String QUERY = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    Channel channel = new Channel();
                    channel.setId(cursor.getInt(0));
                    channel.setJsonId(cursor.getString(1));
                    channel.setName(cursor.getString(2));
                    channel.setTvURL(cursor.getString(3));
                    channel.setCategory(cursor.getString(4));
                    channel.setIsFavorite(cursor.getInt(5));
                    channelList.add(channel);
                }
            }
            db.close();
        } catch (Exception e){
            Log.e("error", e+"");
        }
        return channelList;
    }

    @Override
    public int getChannelCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String QUERY = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            return num;
        } catch (Exception e) {
            Log.e("error", e + "");
        }
        return 0;
    }
}
