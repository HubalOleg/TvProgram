package oleg.hubal.com.tvprogram.database.model;

/**
 * Created by User on 11.09.2016.
 */
public class Program {

    private long date;
    private String day;
    private String channelName;
    private String showName;

    public Program() {

    }

    public Program(String day, long date, String channelName, String showName) {
        this.day = day;
        this.date = date;
        this.channelName = channelName;
        this.showName = showName;
    }

    @Override
    public String toString() {
        return  "\nname: " + channelName +
                "\ndate: " + date +
                "\nshowName " + showName +
                "\n";
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
