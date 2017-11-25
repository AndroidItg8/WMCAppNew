package itg8.com.wmcapp.complaint.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeModel implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private Integer pkid;
    @SerializedName("Master_fkid")
    @Expose
    private Integer masterFkid;
    @SerializedName("SubMaster_fkid")
    @Expose
    private Integer subMasterFkid;
    @SerializedName("User_fkid")
    @Expose
    private String userFkid;
    @SerializedName("Likes")
    @Expose
    private Integer likes;
    @SerializedName("LatModifieddatetime")
    @Expose
    private String latModifieddatetime;
    @SerializedName("customername")
    @Expose
    private String customername;
    @SerializedName("mid")
    @Expose
    private Object mid;
    @SerializedName("mdate")
    @Expose
    private Object mdate;
    public final static Parcelable.Creator<LikeModel> CREATOR = new Creator<LikeModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LikeModel createFromParcel(Parcel in) {
            return new LikeModel(in);
        }

        public LikeModel[] newArray(int size) {
            return (new LikeModel[size]);
        }

    }
            ;

    protected LikeModel(Parcel in) {
        this.pkid = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.masterFkid = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.subMasterFkid = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.userFkid = ((String) in.readValue((String.class.getClassLoader())));
        this.likes = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.latModifieddatetime = ((String) in.readValue((String.class.getClassLoader())));
        this.customername = ((String) in.readValue((String.class.getClassLoader())));
        this.mid = ((Object) in.readValue((Object.class.getClassLoader())));
        this.mdate = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public LikeModel() {
    }

    public Integer getPkid() {
        return pkid;
    }

    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    public Integer getMasterFkid() {
        return masterFkid;
    }

    public void setMasterFkid(Integer masterFkid) {
        this.masterFkid = masterFkid;
    }

    public Integer getSubMasterFkid() {
        return subMasterFkid;
    }

    public void setSubMasterFkid(Integer subMasterFkid) {
        this.subMasterFkid = subMasterFkid;
    }

    public String getUserFkid() {
        return userFkid;
    }

    public void setUserFkid(String userFkid) {
        this.userFkid = userFkid;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getLatModifieddatetime() {
        return latModifieddatetime;
    }

    public void setLatModifieddatetime(String latModifieddatetime) {
        this.latModifieddatetime = latModifieddatetime;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public Object getMid() {
        return mid;
    }

    public void setMid(Object mid) {
        this.mid = mid;
    }

    public Object getMdate() {
        return mdate;
    }

    public void setMdate(Object mdate) {
        this.mdate = mdate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(masterFkid);
        dest.writeValue(subMasterFkid);
        dest.writeValue(userFkid);
        dest.writeValue(likes);
        dest.writeValue(latModifieddatetime);
        dest.writeValue(customername);
        dest.writeValue(mid);
        dest.writeValue(mdate);
    }

    public int describeContents() {
        return 0;
    }

}


