package oleg.hubal.com.tvprogram.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import oleg.hubal.com.tvprogram.database.model.Channel;
import oleg.hubal.com.tvprogram.database.model.Program;


/**
 * Created by User on 05.09.2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION =       1;
    private static final String DB_NAME =       "ProgramDatabase.db";

    private static final String CHANNEL_TABLE_NAME =    "channel_table";
    private static final String KEY_ID_CHANNEL =        "_id";
    private static final String KEY_JSON_ID_CHANNEL =   "_json_id";
    private static final String KEY_NAME_CHANNEL =      "_name";
    private static final String KEY_TVURL_CHANNEL =     "_tvurl";
    private static final String KEY_CATEGORY_CHANNEL =  "_category";
    private static final String KEY_FAVORITE_CHANNEL =  "_favorite";

    private static final String PROGRAM_TABLE_NAME =    "program_table";
    private static final String KEY_ID_PROGRAM =        "_id";
    private static final String KEY_DAY_PROGRAM =       "_day";
    private static final String KEY_DATE_PROGRAM =      "_date";
    private static final String KEY_CHANNEL_PROGRAM =   "_channel";
    private static final String KEY_SHOW_NAME =         "_show_name";


    String CREATE_TABLE_CHANNEL =
                            "CREATE TABLE " + CHANNEL_TABLE_NAME + " (" + KEY_ID_CHANNEL +
                            " INTEGER PRIMARY KEY," + KEY_JSON_ID_CHANNEL + " TEXT," + KEY_NAME_CHANNEL +
                            " TEXT,"+ KEY_TVURL_CHANNEL + " TEXT,"+ KEY_CATEGORY_CHANNEL +
                            " TEXT," + KEY_FAVORITE_CHANNEL + " INTEGER)";

    String CREATE_TABLE_PROGRAM =
                            "CREATE TABLE " + PROGRAM_TABLE_NAME + " (" + KEY_DAY_PROGRAM +
                            " TEXT," + KEY_DATE_PROGRAM + " INTEGER," + KEY_CHANNEL_PROGRAM +
                            " TEXT," + KEY_SHOW_NAME + " TEXT)";


    String DROP_TABLE_CHANNEL = "DROP TABLE IF EXISTS " + CHANNEL_TABLE_NAME;
    String DROP_TABLE_PROGRAM = "DROP TALBE IF EXISTS " + PROGRAM_TABLE_NAME;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHANNEL);
        db.execSQL(CREATE_TABLE_PROGRAM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_CHANNEL);
        db.execSQL(DROP_TABLE_PROGRAM);
        onCreate(db);
    }

    public void addPrograms(ArrayList<Program> programs) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            for(Program program : programs) {
                ContentValues values = new ContentValues();
                values.put(KEY_DAY_PROGRAM, program.getDay());
                values.put(KEY_DATE_PROGRAM, program.getDate());
                values.put(KEY_CHANNEL_PROGRAM, program.getChannelName());
                values.put(KEY_SHOW_NAME, program.getShowName());
                db.insert(PROGRAM_TABLE_NAME, null, values);
            }
            Log.d("log123", "data inserted");
        } catch (Exception e) {
            Log.e("problem", e + "");
        }
    }

    public ArrayList<Program> getProgramByDay(String day) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Program> programList = null;
        try {
            programList = new ArrayList<>();
            String QUERY = "SELECT * FROM " + PROGRAM_TABLE_NAME + " WHERE "
                    + KEY_DAY_PROGRAM + " = '" + day + "'";
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast()) {
                while (cursor.moveToNext()) {
                    Program program = new Program();
                    program.setChannelName(cursor.getString(2));
                    program.setDate(cursor.getInt(1));
                    program.setShowName(cursor.getString(3));
                    program.setDay(day);
                    Log.d("log123", program.toString());
                    programList.add(program);
                }
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return programList;
    }

    public void addChannels(ArrayList<Channel> channels) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            for(Channel channel : channels) {
                ContentValues values = new ContentValues();
                values.put(KEY_JSON_ID_CHANNEL, channel.getJsonId());
                values.put(KEY_NAME_CHANNEL, channel.getName());
                values.put(KEY_TVURL_CHANNEL, channel.getTvURL());
                values.put(KEY_CATEGORY_CHANNEL, channel.getCategory());
                values.put(KEY_FAVORITE_CHANNEL, channel.getIsFavorite());
                db.insert(CHANNEL_TABLE_NAME, null, values);
            }
            db.close();
        } catch (Exception e) {
            Log.e("problem", e + "");
        }
    }

    public ArrayList<Channel> getAllChannels() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Channel> channelList = null;
        try {
            channelList = new ArrayList<>();
            String QUERY = "SELECT * FROM " + CHANNEL_TABLE_NAME;
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
            Log.e("error", e + "");
        }
        return channelList;
    }

    public void setFavoriteChannel(int id, int isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String where = KEY_ID_CHANNEL + "=" + id;
            ContentValues cv = new ContentValues();
            cv.put(KEY_FAVORITE_CHANNEL, isFavorite);
            db.update(CHANNEL_TABLE_NAME, cv, where, null);
            db.close();
        } catch (Exception e) {
            Log.e("error", e + "");
        }
    }
}
