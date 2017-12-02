package itg8.com.wmcapp.setting.model;

/**
 * Created by Android itg 8 on 12/2/2017.
 */

public class SecurityModel {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String heading;
    private String content;

}
