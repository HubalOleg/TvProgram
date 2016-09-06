package oleg.hubal.com.tvprogram.database.model;

/**
 * Created by User on 05.09.2016.
 */
public class Channel {
    private int id;
    private String jsonId;
    private String name;
    private String tvURL;
    private String category;
    private int isFavorite;

    public Channel() {
    }

    public Channel(int id, String jsonId, String name, String tvURL, String category) {
        this.id = id;
        this.jsonId = jsonId;
        this.name = name;
        this.tvURL = tvURL;
        this.category = category;
        isFavorite = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getJsonId() {
        return jsonId;
    }

    public void setJsonId(String jsonId) {
        this.jsonId = jsonId;
    }
}
