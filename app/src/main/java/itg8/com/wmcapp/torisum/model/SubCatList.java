
package itg8.com.wmcapp.torisum.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class SubCatList implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("Category_fkid")
    @Expose
    private int CategoryFkid;
    @SerializedName("SubCategory_Name")
    @Expose
    private String SubCategoryName;
    @SerializedName("SubCategory_Description")
    @Expose
    private String SubCategoryDescription;
    @SerializedName("LastModifiedDate")
    @Expose
    private String LastModifiedDate;
    @SerializedName("Lid")
    @Expose
    private Object Lid;
    @SerializedName("mid")
    @Expose
    private Object mid;
    @SerializedName("mdate")
    @Expose
    private Object mdate;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;
    public final static Parcelable.Creator<SubCatList> CREATOR = new Creator<SubCatList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SubCatList createFromParcel(Parcel in) {
            SubCatList instance = new SubCatList();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.CategoryFkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.SubCategoryName = ((String) in.readValue((String.class.getClassLoader())));
            instance.SubCategoryDescription = ((String) in.readValue((String.class.getClassLoader())));
            instance.LastModifiedDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.Lid = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mid = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mdate = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public SubCatList[] newArray(int size) {
            return (new SubCatList[size]);
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
     *     The SubCategoryName
     */
    public String getSubCategoryName() {
        return SubCategoryName;
    }

    /**
     * 
     * @param SubCategoryName
     *     The SubCategory_Name
     */
    public void setSubCategoryName(String SubCategoryName) {
        this.SubCategoryName = SubCategoryName;
    }

    /**
     * 
     * @return
     *     The SubCategoryDescription
     */
    public String getSubCategoryDescription() {
        return SubCategoryDescription;
    }

    /**
     * 
     * @param SubCategoryDescription
     *     The SubCategory_Description
     */
    public void setSubCategoryDescription(String SubCategoryDescription) {
        this.SubCategoryDescription = SubCategoryDescription;
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
     *     The Lid
     */
    public Object getLid() {
        return Lid;
    }

    /**
     * 
     * @param Lid
     *     The Lid
     */
    public void setLid(Object Lid) {
        this.Lid = Lid;
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
        dest.writeValue(CategoryFkid);
        dest.writeValue(SubCategoryName);
        dest.writeValue(SubCategoryDescription);
        dest.writeValue(LastModifiedDate);
        dest.writeValue(Lid);
        dest.writeValue(mid);
        dest.writeValue(mdate);
    }

    public int describeContents() {
        return  0;
    }

}
