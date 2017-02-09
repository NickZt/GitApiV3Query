package ua.zt.mezon.gitapiv3query.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MezM on 08.02.2017.
 */
public class RepositoryItem implements Parcelable {

    private String name;
    @SerializedName("stargazers_count")
    private int stargazersCount;
    @SerializedName("created_at")
    private String createdAt;

    private String language;

    private Contributor owner;
    @SerializedName("forks_count")
    private int forksCount;
    private String  description;

    protected RepositoryItem(Parcel in) {
        name = in.readString();
        stargazersCount = in.readInt();
        createdAt = in.readString();
        language = in.readString();
        owner = in.readParcelable(Contributor.class.getClassLoader());
        forksCount = in.readInt();
        description = in.readString();
    }

    public static final Creator<RepositoryItem> CREATOR = new Creator<RepositoryItem>() {
        @Override
        public RepositoryItem createFromParcel(Parcel in) {
            return new RepositoryItem(in);
        }

        @Override
        public RepositoryItem[] newArray(int size) {
            return new RepositoryItem[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Contributor getOwner() {
        return owner;
    }

    public void setOwner(Contributor owner) {
        this.owner = owner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(stargazersCount);
        dest.writeString(createdAt);
        dest.writeString(language);
        dest.writeParcelable(owner, flags);
        dest.writeInt(forksCount);
        dest.writeString(description);
    }
}
