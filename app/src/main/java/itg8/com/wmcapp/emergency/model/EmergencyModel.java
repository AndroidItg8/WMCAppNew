
package itg8.com.wmcapp.emergency.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class EmergencyModel implements Parcelable
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
    public final static Parcelable.Creator<EmergencyModel> CREATOR = new Creator<EmergencyModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public EmergencyModel createFromParcel(Parcel in) {
            EmergencyModel instance = new EmergencyModel();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.DeptName = ((String) in.readValue((String.class.getClassLoader())));
            instance.MobileNo = ((String) in.readValue((String.class.getClassLoader())));
            instance.Address = ((String) in.readValue((String.class.getClassLoader())));
            instance.Status = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public EmergencyModel[] newArray(int size) {
            return (new EmergencyModel[size]);
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(DeptName);
        dest.writeValue(MobileNo);
        dest.writeValue(Address);
        dest.writeValue(Status);
    }

    public int describeContents() {
        return  0;
    }

}
