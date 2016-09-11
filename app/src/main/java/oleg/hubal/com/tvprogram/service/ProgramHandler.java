package oleg.hubal.com.tvprogram.service;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import oleg.hubal.com.tvprogram.database.model.Program;

/**
 * Created by User on 11.09.2016.
 */
public class ProgramHandler {

    private Context context;

    public ProgramHandler(Context context) {
        this.context = context;
    }

    public void saveProgram(String resultJson) {
        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            Iterator<String> keys = jsonObject.keys();
            ArrayList<Program> programs = new ArrayList<>();


            while (keys.hasNext() && keys.next().equals("2016Jul01")) {
                JSONObject jsonDateProgram = jsonObject.getJSONObject("2016Jul01");
                Iterator<String> object_keys = jsonDateProgram.keys();

                while (object_keys.hasNext()) {
                    Program program = new Program();

                    String key = object_keys.next();
                    JSONObject jsonProgram = jsonDateProgram.getJSONObject(key);

                    program.setChannelName(jsonProgram.getString("showID"));
                    program.setDate(jsonProgram.getInt("date"));
                    program.setShowName(jsonProgram.getString("tvShowName"));

                    Log.d("log123", program.toString());

                    programs.add(program);
                }
            }

        } catch (JSONException e) {
            Log.e("log123", e.toString());
            e.printStackTrace();
        }
    }
}
