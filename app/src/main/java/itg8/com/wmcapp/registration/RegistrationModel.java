
package itg8.com.wmcapp.registration;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class RegistrationModel implements Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("flag")
    @Expose
    private boolean flag;
    public final static Parcelable.Creator<RegistrationModel> CREATOR = new Creator<RegistrationModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RegistrationModel createFromParcel(Parcel in) {
            RegistrationModel instance = new RegistrationModel();
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.flag = ((boolean) in.readValue((boolean.class.getClassLoader())));
            return instance;
        }

        public RegistrationModel[] newArray(int size) {
            return (new RegistrationModel[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The flag
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * 
     * @param flag
     *     The flag
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(flag);
    }

    public int describeContents() {
        return  0;
    }

}
