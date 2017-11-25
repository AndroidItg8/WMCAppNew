
package itg8.com.wmcapp.complaint.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
    import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ComplaintModel implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("User_fkid")
    @Expose
    private String UserFkid;
    @SerializedName("Category_fkid")
    @Expose
    private int CategoryFkid;
    @SerializedName("ComplaintName")
    @Expose
    private String ComplaintName;
    @SerializedName("ComplaintDescription")
    @Expose
    private String ComplaintDescription;
    @SerializedName("LastModifiedDate")
    @Expose
    private String LastModifiedDate;
    @SerializedName("AddedDate")
    @Expose
    private String AddedDate;
    @SerializedName("Active")
    @Expose
    private int Active;
    @SerializedName("Longitutde")
    @Expose
    private String Longitutde;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("City_fkid")
    @Expose
    private int CityFkid;
    @SerializedName("ShowIdentity")
    @Expose
    private String ShowIdentity;


    @SerializedName("ImagePath")
    @Expose
    private String ImagePath;

    @SerializedName("UserFullename")
    @Expose
    private String UserFullename;
    @SerializedName("UserProfilepic")
    @Expose
    private String UserProfilepic;
    @SerializedName("Lid")
    @Expose
    private String Lid;
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("mdate")
    @Expose
    private String mdate;
    @SerializedName("Likestatus")
    @Expose
    private int Likestatus;
    @SerializedName("LikeList")
    @Expose
    private List<LikeModel> LikeList = new ArrayList<>();
    private boolean progress;

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    private boolean isClickable;



    private String cityName;

    public boolean isVoted() {
        return isVoted;
    }


    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    public String getUserFullename() {
        return UserFullename;
    }

    public void setUserFullename(String userFullename) {
        UserFullename = userFullename;
    }

    public String getUserProfilepic() {
        return UserProfilepic;
    }

    public void setUserProfilepic(String userProfilepic) {
        UserProfilepic = userProfilepic;
    }

    private boolean isVoted;


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
     *     The UserFkid
     */
    public String getUserFkid() {
        return UserFkid;
    }

    /**
     * 
     * @param UserFkid
     *     The User_fkid
     */
    public void setUserFkid(String UserFkid) {
        this.UserFkid = UserFkid;
    }

    /**
     * 
     * @return
     *     The CategoryFkid
     */
    public int getCategoryFkid() {
        return CategoryFkid;
    }

    /**
     * 
     * @param CategoryFkid
     *     The Category_fkid
     */
    public void setCategoryFkid(int CategoryFkid) {
        this.CategoryFkid = CategoryFkid;
    }

    /**
     * 
     * @return
     *     The ComplaintName
     */
    public String getComplaintName() {
        return ComplaintName;
    }

    /**
     * 
     * @param ComplaintName
     *     The ComplaintName
     */
    public void setComplaintName(String ComplaintName) {
        this.ComplaintName = ComplaintName;
    }

    /**
     * 
     * @return
     *     The ComplaintDescription
     */
    public String getComplaintDescription() {
        return ComplaintDescription;
    }

    /**
     * 
     * @param ComplaintDescription
     *     The ComplaintDescription
     */
    public void setComplaintDescription(String ComplaintDescription) {
        this.ComplaintDescription = ComplaintDescription;
    }

    /**
     * 
     * @return
     *     The LastModifiedDate
     */
    public String getLastModifiedDate() {
        return LastModifiedDate;
    }

    /**
     * 
     * @param LastModifiedDate
     *     The LastModifiedDate
     */
    public void setLastModifiedDate(String LastModifiedDate) {
        this.LastModifiedDate = LastModifiedDate;
    }

    /**
     * 
     * @return
     *     The AddedDate
     */
    public String getAddedDate() {
        return AddedDate;
    }

    /**
     * 
     * @param AddedDate
     *     The AddedDate
     */
    public void setAddedDate(String AddedDate) {
        this.AddedDate = AddedDate;
    }

    /**
     * 
     * @return
     *     The Active
     */
    public int getActive() {
        return Active;
    }

    /**
     * 
     * @param Active
     *     The Active
     */
    public void setActive(int Active) {
        this.Active = Active;
    }

    /**
     * 
     * @return
     *     The Longitutde
     */
    public String getLongitutde() {
        return Longitutde;
    }

    /**
     * 
     * @param Longitutde
     *     The Longitutde
     */
    public void setLongitutde(String Longitutde) {
        this.Longitutde = Longitutde;
    }

    /**
     * 
     * @return
     *     The Latitude
     */
    public String getLatitude() {
        return Latitude;
    }

    /**
     * 
     * @param Latitude
     *     The Latitude
     */
    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    /**
     * 
     * @return
     *     The CityFkid
     */
    public int getCityFkid() {
        return CityFkid;
    }

    /**
     * 
     * @param CityFkid
     *     The City_fkid
     */
    public void setCityFkid(int CityFkid) {
        this.CityFkid = CityFkid;
    }

    /**
     * 
     * @return
     *     The ShowIdentity
     */
    public String getShowIdentity() {
        return ShowIdentity;
    }

    /**
     * 
     * @param ShowIdentity
     *     The ShowIdentity
     */
    public void setShowIdentity(String ShowIdentity) {
        this.ShowIdentity = ShowIdentity;
    }

    /**
     * 
     * @return
     *     The ImagePath
     */
    public String getImagePath() {
        return ImagePath;
    }

    /**
     * 
     * @param ImagePath
     *     The ImagePath
     */
    public void setImagePath(String ImagePath) {
        this.ImagePath = ImagePath;
    }

    /**
     * 
     * @return
     *     The Lid
     */
    public String getLid() {
        return Lid;
    }

    /**
     * 
     * @param Lid
     *     The Lid
     */
    public void setLid(String Lid) {
        this.Lid = Lid;
    }

    /**
     * 
     * @return
     *     The mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * 
     * @param mid
     *     The mid
     */
    public void setMid(String mid) {
        this.mid = mid;
    }

    /**
     * 
     * @return
     *     The mdate
     */
    public String getMdate() {
        return mdate;
    }

    /**
     * 
     * @param mdate
     *     The mdate
     */
    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    /**
     * 
     * @return
     *     The Likestatus
     */
    public int getLikestatus() {
        return Likestatus;
    }

    /**
     * 
     * @param Likestatus
     *     The Likestatus
     */
    public void setLikestatus(int Likestatus) {
        this.Likestatus = Likestatus;
    }

    /**
     * 
     * @return
     *     The LikeList
     */
    public List<LikeModel> getLikeList() {
        return LikeList;
    }

    /**
     *
     * @param LikeList
     *     The LikeList
     */
    public void setLikeList(List<LikeModel> LikeList) {
        this.LikeList = LikeList;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }

    public boolean isProgress() {
        return progress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pkid);
        dest.writeString(this.UserFkid);
        dest.writeInt(this.CategoryFkid);
        dest.writeString(this.ComplaintName);
        dest.writeString(this.ComplaintDescription);
        dest.writeString(this.LastModifiedDate);
        dest.writeString(this.AddedDate);
        dest.writeInt(this.Active);
        dest.writeString(this.Longitutde);
        dest.writeString(this.Latitude);
        dest.writeInt(this.CityFkid);
        dest.writeString(this.ShowIdentity);
        dest.writeString(this.ImagePath);
        dest.writeString(this.UserFullename);
        dest.writeString(this.UserProfilepic);
        dest.writeString(this.Lid);
        dest.writeString(this.mid);
        dest.writeString(this.mdate);
        dest.writeInt(this.Likestatus);
        dest.writeTypedList(this.LikeList);
        dest.writeByte(this.progress ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isClickable ? (byte) 1 : (byte) 0);
        dest.writeString(this.cityName);
        dest.writeByte(this.isVoted ? (byte) 1 : (byte) 0);
    }

    public ComplaintModel() {
    }

    protected ComplaintModel(Parcel in) {
        this.pkid = in.readInt();
        this.UserFkid = in.readParcelable(String.class.getClassLoader());
        this.CategoryFkid = in.readInt();
        this.ComplaintName = in.readString();
        this.ComplaintDescription = in.readString();
        this.LastModifiedDate = in.readString();
        this.AddedDate = in.readString();
        this.Active = in.readInt();
        this.Longitutde = in.readString();
        this.Latitude = in.readString();
        this.CityFkid = in.readInt();
        this.ShowIdentity = in.readParcelable(String.class.getClassLoader());
        this.ImagePath = in.readString();
        this.UserFullename = in.readString();
        this.UserProfilepic = in.readString();
        this.Lid = in.readParcelable(String.class.getClassLoader());
        this.mid = in.readParcelable(String.class.getClassLoader());
        this.mdate = in.readParcelable(String.class.getClassLoader());
        this.Likestatus = in.readInt();
        this.LikeList = in.createTypedArrayList(LikeModel.CREATOR);
        this.progress = in.readByte() != 0;
        this.isClickable = in.readByte() != 0;
        this.cityName = in.readString();
        this.isVoted = in.readByte() != 0;
    }

    public static final Creator<ComplaintModel> CREATOR = new Creator<ComplaintModel>() {
        @Override
        public ComplaintModel createFromParcel(Parcel source) {
            return new ComplaintModel(source);
        }

        @Override
        public ComplaintModel[] newArray(int size) {
            return new ComplaintModel[size];
        }
    };
}
