
package itg8.com.wmcapp.complaint.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class LikeModel implements Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("flag")
    @Expose
    private boolean flag;
    @SerializedName("count")
    @Expose
    private int count;
    public final static Parcelable.Creator<LikeModel> CREATOR = new Creator<LikeModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public LikeModel createFromParcel(Parcel in) {
            LikeModel instance = new LikeModel();
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.flag = ((boolean) in.readValue((boolean.class.getClassLoader())));
            instance.count = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public LikeModel[] newArray(int size) {
            return (new LikeModel[size]);
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

    /**
     * 
     * @return
     *     The count
     */
    public int getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(int count) {
        this.count = count;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(flag);
        dest.writeValue(count);
    }

    public int describeContents() {
        return  0;
    }

}
