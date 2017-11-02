package itg8.com.wmcapp.torisum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import itg8.com.wmcapp.R;

/**
 * Created by Android itg 8 on 11/2/2017.
 */

public class TorisumAdapter extends RecyclerView.Adapter<TorisumAdapter.TorisumViewHolder> {
     public interface  TorisumItemClickecListener{
         void onItemClicked();
     }


    private Context mContext;
    private TorisumItemClickecListener listener;

    public TorisumAdapter(Context mContext, TorisumItemClickecListener listener) {

        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public TorisumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_torisum, parent, false);
        TorisumViewHolder holder = new TorisumViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TorisumViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class TorisumViewHolder extends RecyclerView.ViewHolder {
        public TorisumViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     listener.onItemClicked();

                }
            });
        }
    }
}
