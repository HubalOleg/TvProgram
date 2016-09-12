package oleg.hubal.com.tvprogram.list;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import oleg.hubal.com.tvprogram.R;

/**
 * Created by User on 12.09.2016.
 */
public class ProgramViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTime, tvShowName;

    public ProgramViewHolder(View itemView) {
        super(itemView);

        tvShowName = (TextView) itemView.findViewById(R.id.program_card_show_PC);
        tvTime = (TextView) itemView.findViewById(R.id.program_card_time_PC);
    }
}
