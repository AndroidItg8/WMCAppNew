package itg8.com.wmcapp.cilty;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import itg8.com.wmcapp.common.CommonMethod;

/**
 * Created by Android itg 8 on 11/1/2017.
 */

public abstract class BaseAbstractAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<T> listofClass;
    public Typeface typefaceRegular;
    public Typeface typefaceMedium;


    public BaseAbstractAdapter(Context context, List<T> listofClass) {

        this.context = context;
        this.listofClass = listofClass;
        typefaceRegular = CommonMethod.setFontRobotoMedium(context);
        typefaceMedium = CommonMethod.setFontRobotoLight(context);

    }

    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

    public abstract void onBindData(RecyclerView.ViewHolder holder, T val);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return setViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindData(holder, listofClass.get(position));

    }

    @Override
    public int getItemCount() {
        return listofClass.size();
//        return 10;
    }

    public void setItems(ArrayList<T> savedCardItemz) {
        listofClass = savedCardItemz;
        this.notifyDataSetChanged();
    }

    public T getItem(int position) {
        return listofClass.get(position);
    }


}
