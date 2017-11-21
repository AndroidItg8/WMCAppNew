package itg8.com.wmcapp.emergency.model;

/**
 * Created by Android itg 8 on 11/20/2017.
 */

public class ContactModel {
     private String number;

    public boolean isCalling() {
        return isCalling;
    }

    public void setCalling(boolean calling) {
        isCalling = calling;
    }

    private boolean isCalling;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
