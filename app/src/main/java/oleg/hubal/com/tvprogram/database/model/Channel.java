package oleg.hubal.com.tvprogram.database.model;

/**
 * Created by User on 05.09.2016.
 */
public class Channel {
    private String id;
    private String name;
    private String tvURL;
    private String category;

    public Channel() {
    }

    public Channel(String id, String name, String tvURL, String category) {
        this.id = id;
        this.name = name;
        this.tvURL = tvURL;
        this.category = category;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTvURL() {
        return tvURL;
    }

    public void setTvURL(String tvURL) {
        this.tvURL = tvURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
