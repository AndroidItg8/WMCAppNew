
package itg8.com.wmcapp.torisum.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TorisumModel implements Parcelable
{

    @SerializedName("pkid")
    @Expose
    private int pkid;
    @SerializedName("Sub_fkid")
    @Expose
    private int SubFkid;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("ContactNumber")
    @Expose
    private String ContactNumber;
    @SerializedName("InchargeName")
    @Expose
    private String InchargeName;
    @SerializedName("Longitude")
    @Expose
    private double Longitude;
    @SerializedName("Lotitude")
    @Expose
    private double Lotitude;
    @SerializedName("AddedDate")
    @Expose
    private String AddedDate;
    @SerializedName("LastModifiedDate")
    @Expose
    private Object LastModifiedDate;
    @SerializedName("mid")
    @Expose
    private Object mid;
    @SerializedName("mdate")
    @Expose
    private Object mdate;
    @SerializedName("city_fkid")
    @Expose
    private int cityFkid;
    @SerializedName("fileupload")
    @Expose
    private List<Fileupload> fileupload = new ArrayList<Fileupload>();
    public final static Parcelable.Creator<TorisumModel> CREATOR = new Creator<TorisumModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TorisumModel createFromParcel(Parcel in) {
            TorisumModel instance = new TorisumModel();
            instance.pkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.SubFkid = ((int) in.readValue((int.class.getClassLoader())));
            instance.Name = ((String) in.readValue((String.class.getClassLoader())));
            instance.Description = ((String) in.readValue((String.class.getClassLoader())));
            instance.Address = ((String) in.readValue((String.class.getClassLoader())));
            instance.ContactNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.InchargeName = ((String) in.readValue((String.class.getClassLoader())));
            instance.Longitude = ((double) in.readValue((double.class.getClassLoader())));
            instance.Lotitude = ((double) in.readValue((double.class.getClassLoader())));
            instance.AddedDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.LastModifiedDate = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mid = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.mdate = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.cityFkid = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.fileupload, (itg8.com.wmcapp.torisum.model.Fileupload.class.getClassLoader()));
            return instance;
        }

        public TorisumModel[] newArray(int size) {
            return (new TorisumModel[size]);
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
     *     The SubFkid
     */
    public int getSubFkid() {
        return SubFkid;
    }

    /**
     * 
     * @param SubFkid
     *     The Sub_fkid
     */
    public void setSubFkid(int SubFkid) {
        this.SubFkid = SubFkid;
    }

    /**
     * 
     * @return
     *     The Name
     */
    public String getName() {
        return Name;
    }

    /**
     * 
     * @param Name
     *     The Name
     */
    public void setName(String Name) {
        this.Name = Name;
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
     *     The InchargeName
     */
    public String getInchargeName() {
        return InchargeName;
    }

    /**
     * 
     * @param InchargeName
     *     The InchargeName
     */
    public void setInchargeName(String InchargeName) {
        this.InchargeName = InchargeName;
    }

    /**
     * 
     * @return
     *     The Longitude
     */
    public double getLongitude() {
        return Longitude;
    }

    /**
     * 
     * @param Longitude
     *     The Longitude
     */
    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    /**
     * 
     * @return
     *     The Lotitude
     */
    public double getLotitude() {
        return Lotitude;
    }

    /**
     * 
     * @param Lotitude
     *     The Lotitude
     */
    public void setLotitude(double Lotitude) {
        this.Lotitude = Lotitude;
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
     *     The cityFkid
     */
    public int getCityFkid() {
        return cityFkid;
    }

    /**
     * 
     * @param cityFkid
     *     The city_fkid
     */
    public void setCityFkid(int cityFkid) {
        this.cityFkid = cityFkid;
    }

    /**
     * 
     * @return
     *     The fileupload
     */
    public List<Fileupload> getFileupload() {
        return fileupload;
    }

    /**
     * 
     * @param fileupload
     *     The fileupload
     */
    public void setFileupload(List<Fileupload> fileupload) {
        this.fileupload = fileupload;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pkid);
        dest.writeValue(SubFkid);
        dest.writeValue(Name);
        dest.writeValue(Description);
        dest.writeValue(Address);
        dest.writeValue(ContactNumber);
        dest.writeValue(InchargeName);
        dest.writeValue(Longitude);
        dest.writeValue(Lotitude);
        dest.writeValue(AddedDate);
        dest.writeValue(LastModifiedDate);
        dest.writeValue(mid);
        dest.writeValue(mdate);
        dest.writeValue(cityFkid);
        dest.writeList(fileupload);
    }

    public int describeContents() {
        return  0;
    }


}
