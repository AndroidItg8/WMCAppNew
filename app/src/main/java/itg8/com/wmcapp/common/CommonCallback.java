package itg8.com.wmcapp.common;

/**
 * Created by swapnilmeshram on 01/11/17.
 */

public interface CommonCallback {

    public interface OnImagePickListener{
        void onImagePickClick();
    }

    public interface OnDialogClickListner{
        void onOpenCamera();
        void onSelectFromFileManager();
    }
}
