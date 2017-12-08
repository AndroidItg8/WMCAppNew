package itg8.com.wmcapp.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import itg8.com.wmcapp.R;

public class ScrollingActivity extends AppCompatActivity {

    String jsonString = "{" +
            "\"Active\":0," +
            "\"AddedDate\":\"2017-12-07T11:09:25.767\"," +
            "\"Category_fkid\":0," +
            "\"City_fkid\":18266," +
            "\"ComplaintDescription\":\"The test2\"" +
            ",\"ComplaintName\":\"Cleaning\""+
            ",\"progress\":false" +
            "}";

    String jsonRestrigation = "{\n" +
            "    \"status\": \"suceess\",\n" +
            "    \"flag\": true\n" +
            "}";

    public class JsonModel{

        private String active;
        private String addDate;
        private String categody_Fkid;
        private String city_Fkid;
        private String description;
        private String complaintName;
        private boolean isProgress;

        public JsonModel() {
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getAddDate() {
            return addDate;
        }

        public void setAddDate(String addDate) {
            this.addDate = addDate;
        }

        public String getCategody_Fkid() {
            return categody_Fkid;
        }

        public void setCategody_Fkid(String categody_Fkid) {
            this.categody_Fkid = categody_Fkid;
        }

        public String getCity_Fkid() {
            return city_Fkid;
        }

        public void setCity_Fkid(String city_Fkid) {
            this.city_Fkid = city_Fkid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getComplaintName() {
            return complaintName;
        }

        public void setComplaintName(String complaintName) {
            this.complaintName = complaintName;
        }

        public boolean isProgress() {
            return isProgress;
        }

        public void setProgress(boolean progress) {
            isProgress = progress;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JsonModel model = new JsonModel();
            model.setActive(jsonObject.getString("Active"));
            model.setAddDate(jsonObject.getString("AddedDate"));
            model.setCategody_Fkid(jsonObject.getString("Category_fkid"));
            model.setCity_Fkid(jsonObject.getString("City_fkid"));
            model.setDescription(jsonObject.getString("ComplaintDescription"));
            model.setComplaintName(jsonObject.getString("ComplaintName"));
            model.setProgress(jsonObject.getBoolean("progress"));
            model.setProgress(jsonObject.getBoolean("flag"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonRestrigation);
            JsonModel jsonModel = new JsonModel();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }





}
