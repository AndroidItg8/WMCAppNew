package itg8.com.wmcapp.common;

import java.io.IOException;

/**
 * Created by Android itg 8 on 11/6/2017.
 */

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return "No connectivity exception";
    }
}
