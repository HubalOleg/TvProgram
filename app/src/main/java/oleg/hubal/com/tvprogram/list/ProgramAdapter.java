package oleg.hubal.com.tvprogram.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import oleg.hubal.com.tvprogram.R;
import oleg.hubal.com.tvprogram.database.model.Program;

/**
 * Created by User on 12.09.2016.
 */
public class ProgramAdapter extends RecyclerView.Adapter<ProgramViewHolder> {

    private List<Program> programs;

    public ProgramAdapter(ArrayList<Program> programs) {
        this.programs = programs;
    }

    @Override
    public ProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_card, parent,
                false);
        return new ProgramViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProgramViewHolder holder, int position) {
        Date date = new Date();
        DateFormat converter = new SimpleDateFormat("HH:mm");
        converter.setTimeZone(TimeZone.getTimeZone("GMT"));

        holder.tvShowName.setText("\"" + programs.get(position).getShowName() + "\"");
        holder.tvTime.setText(converter.format(programs.get(position).getDate()) + ": ");
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }
}
