
package itg8.com.wmcapp.emergency.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class EmergencyModel implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("CatgoryName")
    @Expose
    private String CatgoryName;
    @SerializedName("categoryDescription")
    @Expose
    private String categoryDescription;
    @SerializedName("Lid")
    @Expose
    private Object Lid;
    @SerializedName("md")
    @Expose
    private Object md;
    @SerializedName("mdate")
    @Expose
    private Object mdate;
    @SerializedName("LastModifiedDate")
    @Expose
    private Object LastModifiedDate;
    @SerializedName("Contact")
    @Expose
    private List<itg8.com.wmcapp.emergency.model.Contact> Contact = new ArrayList<itg8.com.wmcapp.emergency.model.Contact>();
    public final static Parcelable.Creator<EmergencyModel> CREATOR = new Creator<EmergencyModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public EmergencyModel createFromParcel(Parcel in) {
            EmergencyModel instance = new EmergencyModel();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.CatgoryName = ((String) in.readValue((String.class.getClassLoader())));
            instance.categoryDescription = ((String) in.readValue((String.class.getClassLoader())));
            instance.Lid = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.md = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mdate = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.LastModifiedDate = ((Object) in.readValue((Object.class.getClassLoader())));
            in.readList(instance.Contact, (itg8.com.wmcapp.emergency.model.Contact.class.getClassLoader()));
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
     *     The CatgoryName
     */
    public String getCatgoryName() {
        return CatgoryName;
    }

    /**
     * 
     * @param CatgoryName
     *     The CatgoryName
     */
    public void setCatgoryName(String CatgoryName) {
        this.CatgoryName = CatgoryName;
    }

    /**
     * 
     * @return
     *     The categoryDescription
     */
    public String getCategoryDescription() {
        return categoryDescription;
    }

    /**
     * 
     * @param categoryDescription
     *     The categoryDescription
     */
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
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
     *     The md
     */
    public Object getMd() {
        return md;
    }

    /**
     * 
     * @param md
     *     The md
     */
    public void setMd(Object md) {
        this.md = md;
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
     *     The LastModifiedDate
     */
    public Object getLastModifiedDate() {
        return LastModifiedDate;
    }

    /**
     * 
     * @param LastModifiedDate
     *     The LastModifiedDate
     */
    public void setLastModifiedDate(Object LastModifiedDate) {
        this.LastModifiedDate = LastModifiedDate;
    }

    /**
     * 
     * @return
     *     The Contact
     */
    public List<itg8.com.wmcapp.emergency.model.Contact> getContact() {
        return Contact;
    }

    /**
     * 
     * @param Contact
     *     The Contact
     */
    public void setContact(List<itg8.com.wmcapp.emergency.model.Contact> Contact) {
        this.Contact = Contact;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(CatgoryName);
        dest.writeValue(categoryDescription);
        dest.writeValue(Lid);
        dest.writeValue(md);
        dest.writeValue(mdate);
        dest.writeValue(LastModifiedDate);
        dest.writeList(Contact);
    }

    public int describeContents() {
        return  0;
    }

}
