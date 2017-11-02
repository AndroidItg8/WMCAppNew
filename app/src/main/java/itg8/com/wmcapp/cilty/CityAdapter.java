package itg8.com.wmcapp.cilty;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import itg8.com.wmcapp.R;

/**
 * Created by Android itg 8 on 11/1/2017.
 */

public class CityAdapter extends BaseAbstractAdapter {

    private Context context;

    CityAdapter(Context context, List<String> data) {
        super(context, data);
        this.context = context;
//        this.data = data;
    }



    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_city, parent, false);
        MyViewHolder holder = new MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder holder, Object val) {
//        if (val instanceof NewsModel) {
//
//        }


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}



