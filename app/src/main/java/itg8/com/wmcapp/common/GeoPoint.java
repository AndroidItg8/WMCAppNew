package itg8.com.wmcapp.common;

/**
 * Created by Android itg 8 on 11/9/2017.
 */

public class GeoPoint {
    private final double lat;
    private final double lang;

    public double getLat() {
        return lat;
    }

    public double getLang() {
        return lang;
    }

    public GeoPoint(double lat, double lang) {
        this.lat = lat;
        this.lang = lang;
    }
}
