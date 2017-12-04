
package itg8.com.wmcapp.torisum.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TourismFilterModel implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("Category_Name")
    @Expose
    private String CategoryName;
    @SerializedName("Category_Description")
    @Expose
    private String CategoryDescription;
    @SerializedName("LastModifiedDate")
    @Expose
    private String LastModifiedDate;
    @SerializedName("mid")
    @Expose
    private Object mid;
    @SerializedName("mdate")
    @Expose
    private Object mdate;
    @SerializedName("subCatList")
    @Expose
    private List<SubCatList> subCatList = new ArrayList<SubCatList>();
    public final static Parcelable.Creator<TourismFilterModel> CREATOR = new Creator<TourismFilterModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TourismFilterModel createFromParcel(Parcel in) {
            TourismFilterModel instance = new TourismFilterModel();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.CategoryName = ((String) in.readValue((String.class.getClassLoader())));
            instance.CategoryDescription = ((String) in.readValue((String.class.getClassLoader())));
            instance.LastModifiedDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.mid = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mdate = ((Object) in.readValue((Object.class.getClassLoader())));
            in.readList(instance.subCatList, (itg8.com.wmcapp.torisum.model.SubCatList.class.getClassLoader()));
            return instance;
        }

        public TourismFilterModel[] newArray(int size) {
            return (new TourismFilterModel[size]);
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
     *     The Category_Name
     */
    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }

    /**
     * 
     * @return
     *     The CategoryDescription
     */
    public String getCategoryDescription() {
        return CategoryDescription;
    }

    /**
     * 
     * @param CategoryDescription
     *     The Category_Description
     */
    public void setCategoryDescription(String CategoryDescription) {
        this.CategoryDescription = CategoryDescription;
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

    /**
     * 
     * @return
     *     The subCatList
     */
    public List<SubCatList> getSubCatList() {
        return subCatList;
    }

    /**
     * 
     * @param subCatList
     *     The subCatList
     */
    public void setSubCatList(List<SubCatList> subCatList) {
        this.subCatList = subCatList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(CategoryName);
        dest.writeValue(CategoryDescription);
        dest.writeValue(LastModifiedDate);
        dest.writeValue(mid);
        dest.writeValue(mdate);
        dest.writeList(subCatList);
    }

    public int describeContents() {
        return  0;
    }

}
