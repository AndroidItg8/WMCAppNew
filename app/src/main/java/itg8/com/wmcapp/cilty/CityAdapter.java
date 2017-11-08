package itg8.com.wmcapp.cilty;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Prefs;

/**
 * Created by Android itg 8 on 11/1/2017.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {

    private final List<CityModel> list;

    private CityItemClickedListener listener;
    private final Context context;


    public interface CityItemClickedListener {
        void onCityItemClicked(int position, CityModel cityModel);
    }


    public CityAdapter(Context context, List<CityModel> data, CityItemClickedListener listener) {
        this.context = context;
        this.list = data;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_city, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.rgbCityName.setText(list.get(position).getName());
        if(Prefs.getInt(CommonMethod.SELECTED_CITY)== list.get(position).getID())
        {
            holder.rgbCityName.setChecked(true);
        }else
        {
            holder.rgbCityName.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rgb_cityName)
        RadioButton rgbCityName;



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCityItemClicked(getAdapterPosition(), list.get(getAdapterPosition()));

                }
            });


        }
    }
}



