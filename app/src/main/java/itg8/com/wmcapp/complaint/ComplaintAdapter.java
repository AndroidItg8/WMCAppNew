package itg8.com.wmcapp.complaint;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.widget.CircularImageView;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * Created by Android itg 8 on 11/1/2017.
 */

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {


    private Context mContext;

    public ComplaintAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ComplaintViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_complaint, parent, false);
        ComplaintViewHolder holder = new ComplaintViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ComplaintViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(R.drawable.garbage)
                .placeholder(R.drawable.garbage)
                .error(R.drawable.ic_menu_gallery)
                .into(holder.imgGarbage);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ComplaintViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        CircularImageView img;
        @BindView(R.id.lbl_name_value)
        CustomFontTextView lblNameValue;
        @BindView(R.id.lbl_days_value)
        CustomFontTextView lblDaysValue;
        @BindView(R.id.img_garbage)
        ImageView imgGarbage;

        public ComplaintViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
