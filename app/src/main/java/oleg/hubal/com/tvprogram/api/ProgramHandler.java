package oleg.hubal.com.tvprogram.api;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import oleg.hubal.com.tvprogram.database.model.Program;

/**
 * Created by User on 12.09.2016.
 */
public class ProgramHandler {

    HashMap<String, ArrayList<Program>> sortedPrograms;

    public ProgramHandler(ArrayList<Program> programs) {
        sortedPrograms = new HashMap<>();
        sortData(programs);
    }

    private void sortData(ArrayList<Program> programs) {
        for (Program program : programs) {
            String channelName = program.getChannelName();
            if (sortedPrograms.containsKey(channelName)) {
                sortedPrograms.get(channelName).add(program);
            } else {
                ArrayList<Program> programList = new ArrayList<>();
                programList.add(program);
                sortedPrograms.put(channelName, programList);
            }
        }
    }

    public ArrayList<Program> getProgramFromChannel(String channelName) {
        return sortedPrograms.get(channelName);
    }

    public String[] getChannels() {
        return sortedPrograms.keySet().toArray(new String[sortedPrograms.size()]);
    }
}
