
package itg8.com.wmcapp.news.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class NewsModel implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("Title")
    @Expose
    private String Title;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("ProfilePic")
    @Expose
    private String ProfilePic;
    @SerializedName("Addate")
    @Expose
    private String Addate;
    @SerializedName("lastModified")
    @Expose
    private String lastModified;
    @SerializedName("status")
    @Expose
    private int status;
    public final static Parcelable.Creator<NewsModel> CREATOR = new Creator<NewsModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NewsModel createFromParcel(Parcel in) {
            NewsModel instance = new NewsModel();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.Title = ((String) in.readValue((String.class.getClassLoader())));
            instance.Description = ((String) in.readValue((String.class.getClassLoader())));
            instance.ProfilePic = ((String) in.readValue((String.class.getClassLoader())));
            instance.Addate = ((String) in.readValue((String.class.getClassLoader())));
            instance.lastModified = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public NewsModel[] newArray(int size) {
            return (new NewsModel[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The pkid
     */
    public int getPkid() {
        return pkid;
    }

    /**
     * 
     * @param pkid
     *     The pkid
     */
    public void setPkid(int pkid) {
        this.pkid = pkid;
    }

    /**
     * 
     * @return
     *     The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * 
     * @param Title
     *     The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * 
     * @return
     *     The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * 
     * @param Description
     *     The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * 
     * @return
     *     The ProfilePic
     */
    public String getProfilePic() {
        return ProfilePic;
    }

    /**
     * 
     * @param ProfilePic
     *     The ProfilePic
     */
    public void setProfilePic(String ProfilePic) {
        this.ProfilePic = ProfilePic;
    }

    /**
     * 
     * @return
     *     The Addate
     */
    public String getAddate() {
        return Addate;
    }

    /**
     * 
     * @param Addate
     *     The Addate
     */
    public void setAddate(String Addate) {
        this.Addate = Addate;
    }

    /**
     * 
     * @return
     *     The lastModified
     */
    public String getLastModified() {
        return lastModified;
    }

    /**
     * 
     * @param lastModified
     *     The lastModified
     */
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * 
     * @return
     *     The status
     */
    public int getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(Title);
        dest.writeValue(Description);
        dest.writeValue(ProfilePic);
        dest.writeValue(Addate);
        dest.writeValue(lastModified);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
