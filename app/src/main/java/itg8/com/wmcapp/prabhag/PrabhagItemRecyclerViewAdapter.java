package itg8.com.wmcapp.prabhag;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.BaseViewHolder;
import itg8.com.wmcapp.common.OnRecyclerviewClickListener;
import itg8.com.wmcapp.prabhag.model.PrabhagModel;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class PrabhagItemRecyclerViewAdapter extends RecyclerView.Adapter<PrabhagItemRecyclerViewAdapter.ViewHolder> {


    private final List<PrabhagModel> list;
    private final Context context;
    public OnRecyclerviewClickListener<PrabhagModel> listener;



    public PrabhagItemRecyclerViewAdapter(Context context, List<PrabhagModel> list, OnRecyclerviewClickListener<PrabhagModel>listener) {

        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_prabhagitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (list.get(position).isPragbhagSelected()) {
            holder.content.setText(list.get(position).getWardList().get(position).getWardName());
            holder.id.setText(list.get(position).getWardList().get(position).getAddress());

        } else {
            holder.content.setText(list.get(position).getPrabhagName());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends BaseViewHolder<PrabhagModel> {

        @BindView(R.id.id)
        TextView id;
        @BindView(R.id.content)
        TextView content;
        public ViewHolder(View view) {
            super(view, listener);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getAdapterPosition(),list.get(getAdapterPosition()));
                }
            });


        }


    }
}
