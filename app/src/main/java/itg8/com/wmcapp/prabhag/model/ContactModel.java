package itg8.com.wmcapp.prabhag.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by swapnilmeshram on 02/11/17.
 */

public class ContactModel implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ContactModel() {
    }

    protected ContactModel(Parcel in) {
    }

    public static final Creator<ContactModel> CREATOR = new Creator<ContactModel>() {
        @Override
        public ContactModel createFromParcel(Parcel source) {
            return new ContactModel(source);
        }

        @Override
        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };
}
