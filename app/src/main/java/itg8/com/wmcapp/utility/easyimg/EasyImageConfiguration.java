package itg8.com.wmcapp.utility.easyimg;

import android.content.Context;

import itg8.com.wmcapp.common.Prefs;

import static itg8.com.wmcapp.utility.easyimg.Constants.DEFAULT_FOLDER_NAME;


/**
 * Created by swapnilmeshram on 31/10/17.
 */

public class EasyImageConfiguration {
    private Context context;

    EasyImageConfiguration(Context context) {
        this.context = context;
    }

    public EasyImageConfiguration setImagesFolderName(String folderName) {

                Prefs.putString(Constants.BundleKeys.FOLDER_NAME, folderName);
        return this;
    }

    public EasyImageConfiguration setAllowMultiplePickInGallery(boolean allowMultiple) {
        Prefs.putBoolean(Constants.BundleKeys.ALLOW_MULTIPLE, allowMultiple);
        return this;
    }

    public EasyImageConfiguration setCopyTakenPhotosToPublicGalleryAppFolder(boolean copy) {
                Prefs.putBoolean(Constants.BundleKeys.COPY_TAKEN_PHOTOS, copy);
        return this;
    }

    public EasyImageConfiguration setCopyPickedImagesToPublicGalleryAppFolder(boolean copy) {
                Prefs.putBoolean(Constants.BundleKeys.COPY_PICKED_IMAGES, copy);
        return this;
    }

    public String getFolderName() {
        return Prefs.getString(Constants.BundleKeys.FOLDER_NAME, DEFAULT_FOLDER_NAME);
    }

    public boolean allowsMultiplePickingInGallery() {
        return Prefs.getBoolean(Constants.BundleKeys.ALLOW_MULTIPLE, false);
    }

    public boolean shouldCopyTakenPhotosToPublicGalleryAppFolder() {
        return Prefs.getBoolean(Constants.BundleKeys.COPY_TAKEN_PHOTOS, false);
    }

    public boolean shouldCopyPickedImagesToPublicGalleryAppFolder() {
        return Prefs.getBoolean(Constants.BundleKeys.COPY_PICKED_IMAGES, false);
    }

}
