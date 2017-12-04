
package itg8.com.wmcapp.torisum.model;

import javax.annotation.Generated;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TourismFilterCategoryModel implements Parcelable
{

    @SerializedName("SubcategoryId")
    @Expose
    private int SubcategoryId;
    public final static Parcelable.Creator<TourismFilterCategoryModel> CREATOR = new Creator<TourismFilterCategoryModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TourismFilterCategoryModel createFromParcel(Parcel in) {
            TourismFilterCategoryModel instance = new TourismFilterCategoryModel();
            instance.SubcategoryId = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public TourismFilterCategoryModel[] newArray(int size) {
            return (new TourismFilterCategoryModel[size]);
        }

    }
    ;

    /**
     * 
     * @return
     *     The SubcategoryId
     */
    public int getSubcategoryId() {
        return SubcategoryId;
    }

    /**
     * 
     * @param SubcategoryId
     *     The SubcategoryId
     */
    public void setSubcategoryId(int SubcategoryId) {
        this.SubcategoryId = SubcategoryId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(SubcategoryId);
    }

    public int describeContents() {
        return  0;
    }

}
