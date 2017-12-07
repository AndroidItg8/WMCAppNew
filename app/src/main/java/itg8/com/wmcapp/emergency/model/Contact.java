
package itg8.com.wmcapp.emergency.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Contact implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("DeptName")
    @Expose
    private String DeptName;
    @SerializedName("MobileNo")
    @Expose
    private String MobileNo;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("Status")
    @Expose
    private int Status;
    @SerializedName("Category_fkid")
    @Expose
    private int CategoryFkid;
    public final static Parcelable.Creator<Contact> CREATOR = new Creator<Contact>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Contact createFromParcel(Parcel in) {
            Contact instance = new Contact();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.DeptName = ((String) in.readValue((String.class.getClassLoader())));
            instance.MobileNo = ((String) in.readValue((String.class.getClassLoader())));
            instance.Address = ((String) in.readValue((String.class.getClassLoader())));
            instance.Status = ((int) in.readValue((int.class.getClassLoader())));
            instance.CategoryFkid = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public Contact[] newArray(int size) {
            return (new Contact[size]);
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
     *     The DeptName
     */
    public String getDeptName() {
        return DeptName;
    }

    /**
     * 
     * @param DeptName
     *     The DeptName
     */
    public void setDeptName(String DeptName) {
        this.DeptName = DeptName;
    }

    /**
     * 
     * @return
     *     The MobileNo
     */
    public String getMobileNo() {
        return MobileNo;
    }

    /**
     * 
     * @param MobileNo
     *     The MobileNo
     */
    public void setMobileNo(String MobileNo) {
        this.MobileNo = MobileNo;
    }

    /**
     * 
     * @return
     *     The Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * 
     * @param Address
     *     The Address
     */
    public void setAddress(String Address) {
        this.Address = Address;
    }

    /**
     * 
     * @return
     *     The Status
     */
    public int getStatus() {
        return Status;
    }

    /**
     * 
     * @param Status
     *     The Status
     */
    public void setStatus(int Status) {
        this.Status = Status;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(DeptName);
        dest.writeValue(MobileNo);
        dest.writeValue(Address);
        dest.writeValue(Status);
        dest.writeValue(CategoryFkid);
    }

    public int describeContents() {
        return  0;
    }

    public void setCalling(boolean b) {

    }
}
