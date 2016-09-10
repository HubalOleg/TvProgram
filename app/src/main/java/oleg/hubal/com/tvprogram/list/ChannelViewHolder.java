package oleg.hubal.com.tvprogram.list;

import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import oleg.hubal.com.tvprogram.ChannelManager;
import oleg.hubal.com.tvprogram.R;

/**
 * Created by User on 09.09.2016.
 */
public class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private CardView channelCard;
    private ChannelManager iChannelManager;

    public TextView channelName, channelCategory, channelURL;
    public ImageButton ibFavorite;
    public int isFavorite;
    public int channelId;

    public ChannelViewHolder(View itemView, ChannelManager iChannelManager) {
        super(itemView);
        this.iChannelManager = iChannelManager;

        channelCard = (CardView) itemView.findViewById(R.id.channel_card_CC);
        channelName = (TextView) itemView.findViewById(R.id.channel_card_title_CC);
        channelCategory = (TextView) itemView.findViewById(R.id.channel_card_category_CC);
        channelURL = (TextView) itemView.findViewById(R.id.channel_card_URL_CC);
        ibFavorite = (ImageButton) itemView.findViewById(R.id.img_btn_favorite);
        ibFavorite.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (isFavorite == 0) {
            ibFavorite.setImageDrawable(Resources.getSystem().getDrawable(android.R.drawable.star_on));
            isFavorite = 1;
            iChannelManager.setFavorite(channelId ,isFavorite);
        } else {
            ibFavorite.setImageDrawable(Resources.getSystem().getDrawable(android.R.drawable.star_off));
            isFavorite = 0;
            iChannelManager.setFavorite(channelId ,isFavorite);
        }
    }
}
