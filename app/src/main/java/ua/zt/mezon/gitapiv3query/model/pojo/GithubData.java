package ua.zt.mezon.gitapiv3query.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by MezM on 08.02.2017.
 */
public class GithubData {

    @SerializedName("total_count")
    private int totalCount;
    private ArrayList<RepositoryItem> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<RepositoryItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<RepositoryItem> items) {
        this.items = items;
    }
}
