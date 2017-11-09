package itg8.com.wmcapp.news;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.widget.CustomFontTextView;


/**
 * Created by Android itg 8 on 11/3/2017.
 */

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    public interface NewsItemClickedListner {
        void onItemNewsClicked(int position, ImageView img);
    }

    private Context context;
    private NewsItemClickedListner listner;

    public NewsAdapter(Context context, NewsItemClickedListner listner) {
        this.context = context;
        this.listner = listner;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_news, parent, false);
        NewsViewHolder holder = new NewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.img.setTransitionName("MyItem" + position);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.lbl_heading)
        TextView lblHeading;
        @BindView(R.id.lbl_date)
        CustomFontTextView lblDate;
        @BindView(R.id.img_close)
        TextView imgClose;

        public NewsViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onItemNewsClicked(getAdapterPosition(), img);

                }
            });
        }
    }
}
