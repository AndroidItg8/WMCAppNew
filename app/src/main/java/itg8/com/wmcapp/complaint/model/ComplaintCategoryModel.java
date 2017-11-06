
package itg8.com.wmcapp.complaint.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ComplaintCategoryModel implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("CategoryName")
    @Expose
    private String CategoryName;
    @SerializedName("LastModifedDate")
    @Expose
    private String LastModifedDate;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("mid")
    @Expose
    private Object mid;
    @SerializedName("mdate")
    @Expose
    private Object mdate;
    public final static Parcelable.Creator<ComplaintCategoryModel> CREATOR = new Creator<ComplaintCategoryModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ComplaintCategoryModel createFromParcel(Parcel in) {
            ComplaintCategoryModel instance = new ComplaintCategoryModel();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.CategoryName = ((String) in.readValue((String.class.getClassLoader())));
            instance.LastModifedDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.Description = ((String) in.readValue((String.class.getClassLoader())));
            instance.mid = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mdate = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public ComplaintCategoryModel[] newArray(int size) {
            return (new ComplaintCategoryModel[size]);
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
     *     The CategoryName
     */
    public String getCategoryName() {
        return CategoryName;
    }

    /**
     * 
     * @param CategoryName
     *     The CategoryName
     */
    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    /**
     * 
     * @return
     *     The LastModifedDate
     */
    public String getLastModifedDate() {
        return LastModifedDate;
    }

    /**
     * 
     * @param LastModifedDate
     *     The LastModifedDate
     */
    public void setLastModifedDate(String LastModifedDate) {
        this.LastModifedDate = LastModifedDate;
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
        dest.writeValue(CategoryName);
        dest.writeValue(LastModifedDate);
        dest.writeValue(Description);
        dest.writeValue(mid);
        dest.writeValue(mdate);
    }

    public int describeContents() {
        return  0;
    }

}
