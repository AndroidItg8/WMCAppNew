
package itg8.com.wmcapp.complaint.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
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
    private Object AddedDate;
    @SerializedName("Active")
    @Expose
    private Object Active;
    @SerializedName("Longitutde")
    @Expose
    private String Longitutde;
    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("City_fkid")
    @Expose
    private int CityFkid;
    @SerializedName("mid")
    @Expose
    private Object mid;
    @SerializedName("mdate")
    @Expose
    private Object mdate;
    @SerializedName("ImagePath")
    @Expose
    private Object ImagePath;
    public final static Parcelable.Creator<ComplaintModel> CREATOR = new Creator<ComplaintModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ComplaintModel createFromParcel(Parcel in) {
            ComplaintModel instance = new ComplaintModel();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.UserFkid = ((String) in.readValue((String.class.getClassLoader())));
            instance.CategoryFkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.ComplaintName = ((String) in.readValue((String.class.getClassLoader())));
            instance.ComplaintDescription = ((String) in.readValue((String.class.getClassLoader())));
            instance.LastModifiedDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.AddedDate = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.Active = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.Longitutde = ((String) in.readValue((String.class.getClassLoader())));
            instance.Latitude = ((String) in.readValue((String.class.getClassLoader())));
            instance.CityFkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.mid = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mdate = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.ImagePath = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public ComplaintModel[] newArray(int size) {
            return (new ComplaintModel[size]);
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
    public Object getAddedDate() {
        return AddedDate;
    }

    /**
     * 
     * @param AddedDate
     *     The AddedDate
     */
    public void setAddedDate(Object AddedDate) {
        this.AddedDate = AddedDate;
    }

    /**
     * 
     * @return
     *     The Active
     */
    public Object getActive() {
        return Active;
    }

    /**
     * 
     * @param Active
     *     The Active
     */
    public void setActive(Object Active) {
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

    /**
     * 
     * @return
     *     The ImagePath
     */
    public Object getImagePath() {
        return ImagePath;
    }

    /**
     * 
     * @param ImagePath
     *     The ImagePath
     */
    public void setImagePath(Object ImagePath) {
        this.ImagePath = ImagePath;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(UserFkid);
        dest.writeValue(CategoryFkid);
        dest.writeValue(ComplaintName);
        dest.writeValue(ComplaintDescription);
        dest.writeValue(LastModifiedDate);
        dest.writeValue(AddedDate);
        dest.writeValue(Active);
        dest.writeValue(Longitutde);
        dest.writeValue(Latitude);
        dest.writeValue(CityFkid);
        dest.writeValue(mid);
        dest.writeValue(mdate);
        dest.writeValue(ImagePath);
    }

    public int describeContents() {
        return  0;
    }

}
