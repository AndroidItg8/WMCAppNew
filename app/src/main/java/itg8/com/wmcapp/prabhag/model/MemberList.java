
package itg8.com.wmcapp.prabhag.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class MemberList implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("Member_Name")
    @Expose
    private String MemberName;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("adddate")
    @Expose
    private String adddate;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("Address")
    @Expose
    private String Address;



    @SerializedName("MobileNo")
    @Expose
    private String ProfilePic;
    @SerializedName("ProfilePic")
    @Expose
    private String MobileNo;
    @SerializedName("Ward_fkid")
    @Expose
    private int WardFkid;
    public final static Parcelable.Creator<MemberList> CREATOR = new Creator<MemberList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MemberList createFromParcel(Parcel in) {
            MemberList instance = new MemberList();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.MemberName = ((String) in.readValue((String.class.getClassLoader())));
            instance.Description = ((String) in.readValue((String.class.getClassLoader())));
            instance.adddate = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((int) in.readValue((int.class.getClassLoader())));
            instance.Address = ((String) in.readValue((String.class.getClassLoader())));
            instance.MobileNo = ((String) in.readValue((String.class.getClassLoader())));
            instance.ProfilePic = ((String) in.readValue((String.class.getClassLoader())));
            instance.WardFkid = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public MemberList[] newArray(int size) {
            return (new MemberList[size]);
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
     *     The MemberName
     */
    public String getMemberName() {
        return MemberName;
    }

    /**
     * 
     * @param MemberName
     *     The Member_Name
     */
    public void setMemberName(String MemberName) {
        this.MemberName = MemberName;
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
     *     The adddate
     */
    public String getAdddate() {
        return adddate;
    }

    /**
     * 
     * @param adddate
     *     The adddate
     */
    public void setAdddate(String adddate) {
        this.adddate = adddate;
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
     *     The WardFkid
     */
    public int getWardFkid() {
        return WardFkid;
    }

    /**
     * 
     * @param WardFkid
     *     The Ward_fkid
     */

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }
    public void setWardFkid(int WardFkid) {
        this.WardFkid = WardFkid;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(MemberName);
        dest.writeValue(Description);
        dest.writeValue(adddate);
        dest.writeValue(status);
        dest.writeValue(Address);
        dest.writeValue(MobileNo);
        dest.writeValue(WardFkid);
        dest.writeValue(ProfilePic);
    }

    public int describeContents() {
        return  0;
    }

}
