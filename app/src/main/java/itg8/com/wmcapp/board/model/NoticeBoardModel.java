
package itg8.com.wmcapp.board.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import itg8.com.wmcapp.cilty.model.CityModel;

@Generated("org.jsonschema2pojo")

public class NoticeBoardModel implements Parcelable
{




    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("NoticeName")
    @Expose
    private String NoticeName;
    @SerializedName("NoticeDescription")
    @Expose
    private String NoticeDescription;
    @SerializedName("Image_Path")
    @Expose
    private String ImagePath;
    @SerializedName("AddedDate")
    @Expose
    private String AddedDate;
    @SerializedName("RequiredDays")
    @Expose
    private int RequiredDays;
    @SerializedName("Active")
    @Expose
    private int Active;
    @SerializedName("LastModifiedDate")
    @Expose
    private String LastModifiedDate;
    @SerializedName("mid")
    @Expose
    private Object mid;
    @SerializedName("mdate")
    @Expose
    private Object mdate;

    public boolean isItemDelete() {
        return isItemDelete;
    }

    public void setItemDelete(boolean itemDelete) {
        isItemDelete = itemDelete;
    }

    private boolean isItemDelete;
    public final static Parcelable.Creator<NoticeBoardModel> CREATOR = new Creator<NoticeBoardModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NoticeBoardModel createFromParcel(Parcel in) {
            NoticeBoardModel instance = new NoticeBoardModel();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.NoticeName = ((String) in.readValue((String.class.getClassLoader())));
            instance.NoticeDescription = ((String) in.readValue((String.class.getClassLoader())));
            instance.ImagePath = ((String) in.readValue((String.class.getClassLoader())));
            instance.AddedDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.RequiredDays = ((int) in.readValue((int.class.getClassLoader())));
            instance.Active = ((int) in.readValue((int.class.getClassLoader())));
            instance.LastModifiedDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.mid = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mdate = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public NoticeBoardModel[] newArray(int size) {
            return (new NoticeBoardModel[size]);
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
     *     The NoticeName
     */
    public String getNoticeName() {
        return NoticeName;
    }

    /**
     * 
     * @param NoticeName
     *     The NoticeName
     */
    public void setNoticeName(String NoticeName) {
        this.NoticeName = NoticeName;
    }

    /**
     * 
     * @return
     *     The NoticeDescription
     */
    public String getNoticeDescription() {
        return NoticeDescription;
    }

    /**
     * 
     * @param NoticeDescription
     *     The NoticeDescription
     */
    public void setNoticeDescription(String NoticeDescription) {
        this.NoticeDescription = NoticeDescription;
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
     *     The Image_Path
     */
    public void setImagePath(String ImagePath) {
        this.ImagePath = ImagePath;
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
     *     The RequiredDays
     */
    public int getRequiredDays() {
        return RequiredDays;
    }

    /**
     * 
     * @param RequiredDays
     *     The RequiredDays
     */
    public void setRequiredDays(int RequiredDays) {
        this.RequiredDays = RequiredDays;
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
     *     The mid
     */
    public Object getMid() {
        return mid;
    }

    /**
     * 
     * @param mid
     *     The mid
     */
    public void setMid(Object mid) {
        this.mid = mid;
    }

    /**
     * 
     * @return
     *     The mdate
     */
    public Object getMdate() {
        return mdate;
    }

    /**
     * 
     * @param mdate
     *     The mdate
     */
    public void setMdate(Object mdate) {
        this.mdate = mdate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(NoticeName);
        dest.writeValue(NoticeDescription);
        dest.writeValue(ImagePath);
        dest.writeValue(AddedDate);
        dest.writeValue(RequiredDays);
        dest.writeValue(Active);
        dest.writeValue(LastModifiedDate);
        dest.writeValue(mid);
        dest.writeValue(mdate);
    }

    public int describeContents() {
        return  0;
    }


}
