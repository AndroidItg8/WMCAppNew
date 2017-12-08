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
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.prabhag.model.PrabhagModel;
import itg8.com.wmcapp.prabhag.model.WardList;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class PrabhagItemRecyclerViewAdapter extends RecyclerView.Adapter<PrabhagItemRecyclerViewAdapter.ViewHolder> {


    private final List<PrabhagModel> list;
    private final Context context;
    private List<WardList> wradList;
    public ItemClickedListener listener;
    private int isFrom;


    public PrabhagItemRecyclerViewAdapter(Context context, List<PrabhagModel> list, List<WardList> wradList, ItemClickedListener listener, int isFrom) {

        this.context = context;
        this.list = list;
        this.wradList = wradList;
        this.listener = listener;
        this.isFrom = isFrom;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_prabhagitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (isFrom == CommonMethod.WARD) {

            holder.content.setText(wradList.get(position).getWardName());
                holder.txtId.setText((String.valueOf(wradList.get(position).getPkid())));
        } else {
            holder.content.setText(list.get(position).getPrabhagName());
                holder.txtId.setText(String.valueOf(list.get(position).getPkid()));
        }


    }

    @Override
    public int getItemCount() {
        if (isFrom == CommonMethod.WARD)
            return wradList.size();
        else
            return list.size();
    }

    public interface ItemClickedListener {
        void onItemPrabhagClicked(int position, PrabhagModel model);

        void onItemWardClicked(int position, WardList model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_id)
        TextView txtId;
        @BindView(R.id.content)
        TextView content;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isFrom == CommonMethod.WARD)
                        listener.onItemWardClicked(getAdapterPosition(), wradList.get(getAdapterPosition()));

                    else
                        listener.onItemPrabhagClicked(getAdapterPosition(), list.get(getAdapterPosition()));

                }
            });


        }


    }
}
