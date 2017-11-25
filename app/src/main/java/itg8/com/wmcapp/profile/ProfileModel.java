
package itg8.com.wmcapp.profile;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ProfileModel implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("User_fkid")
    @Expose
    private String UserFkid;
    @SerializedName("FullName")
    @Expose
    private String FullName;
    @SerializedName("UserName")
    @Expose
    private String UserName;
    @SerializedName("RoleName")
    @Expose
    private String RoleName;
    @SerializedName("AddressLine1")
    @Expose
    private String AddressLine1;
    @SerializedName("AddressLine2")
    @Expose
    private String AddressLine2;
    @SerializedName("ContactNumber")
    @Expose
    private String ContactNumber;
    @SerializedName("EmailId")
    @Expose
    private String EmailId;
    @SerializedName("RegisteredDate")
    @Expose
    private String RegisteredDate;
    @SerializedName("LastModifiedDate")
    @Expose
    private String LastModifiedDate;
    @SerializedName("cid")
    @Expose
    private String cid;
    @SerializedName("Lid")
    @Expose
    private Object Lid;
    @SerializedName("mid")
    @Expose
    private Object mid;
    @SerializedName("mdate")
    @Expose
    private Object mdate;


    @SerializedName("ProdilePic")
    @Expose
    private String picProfle;

    public final static Parcelable.Creator<ProfileModel> CREATOR = new Creator<ProfileModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ProfileModel createFromParcel(Parcel in) {
            ProfileModel instance = new ProfileModel();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.UserFkid = ((String) in.readValue((String.class.getClassLoader())));
            instance.FullName = ((String) in.readValue((String.class.getClassLoader())));
            instance.UserName = ((String) in.readValue((String.class.getClassLoader())));
            instance.RoleName = ((String) in.readValue((String.class.getClassLoader())));
            instance.AddressLine1 = ((String) in.readValue((String.class.getClassLoader())));
            instance.AddressLine2 = ((String) in.readValue((String.class.getClassLoader())));
            instance.ContactNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.EmailId = ((String) in.readValue((String.class.getClassLoader())));
            instance.RegisteredDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.LastModifiedDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.picProfle = ((String) in.readValue((String.class.getClassLoader())));
            instance.cid = ((String) in.readValue((String.class.getClassLoader())));
            instance.Lid = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mid = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mdate = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public ProfileModel[] newArray(int size) {
            return (new ProfileModel[size]);
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
     *     The FullName
     */
    public String getFullName() {
        return FullName;
    }

    /**
     * 
     * @param FullName
     *     The FullName
     */
    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    /**
     * 
     * @return
     *     The UserName
     */
    public String getUserName() {
        return UserName;
    }

    /**
     * 
     * @param UserName
     *     The UserName
     */
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    /**
     * 
     * @return
     *     The RoleName
     */
    public String getRoleName() {
        return RoleName;
    }

    /**
     * 
     * @param RoleName
     *     The RoleName
     */
    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }

    /**
     * 
     * @return
     *     The AddressLine1
     */
    public String getAddressLine1() {
        return AddressLine1;
    }

    /**
     * 
     * @param AddressLine1
     *     The AddressLine1
     */
    public void setAddressLine1(String AddressLine1) {
        this.AddressLine1 = AddressLine1;
    }

    /**
     * 
     * @return
     *     The AddressLine2
     */
    public String getAddressLine2() {
        return AddressLine2;
    }

    /**
     * 
     * @param AddressLine2
     *     The AddressLine2
     */
    public void setAddressLine2(String AddressLine2) {
        this.AddressLine2 = AddressLine2;
    }

    /**
     * 
     * @return
     *     The ContactNumber
     */
    public String getContactNumber() {
        return ContactNumber;
    }

    /**
     * 
     * @param ContactNumber
     *     The ContactNumber
     */
    public void setContactNumber(String ContactNumber) {
        this.ContactNumber = ContactNumber;
    }

    /**
     * 
     * @return
     *     The EmailId
     */
    public String getEmailId() {
        return EmailId;
    }

    /**
     * 
     * @param EmailId
     *     The EmailId
     */
    public void setEmailId(String EmailId) {
        this.EmailId = EmailId;
    }

    /**
     * 
     * @return
     *     The RegisteredDate
     */
    public String getRegisteredDate() {
        return RegisteredDate;
    }

    /**
     * 
     * @param RegisteredDate
     *     The RegisteredDate
     */
    public void setRegisteredDate(String RegisteredDate) {
        this.RegisteredDate = RegisteredDate;
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
     *     The cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * 
     * @param cid
     *     The cid
     */
    public void setCid(String cid) {
        this.cid = cid;
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


    public String getPicProfle() {
        return picProfle;
    }

    public void setPicProfle(String picProfle) {
        this.picProfle = picProfle;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(UserFkid);
        dest.writeValue(FullName);
        dest.writeValue(UserName);
        dest.writeValue(RoleName);
        dest.writeValue(AddressLine1);
        dest.writeValue(AddressLine2);
        dest.writeValue(ContactNumber);
        dest.writeValue(EmailId);
        dest.writeValue(RegisteredDate);
        dest.writeValue(LastModifiedDate);
        dest.writeValue(cid);
        dest.writeValue(Lid);
        dest.writeValue(mid);
        dest.writeValue(mdate);
        dest.writeValue(picProfle);
    }

    public int describeContents() {
        return  0;
    }

}
