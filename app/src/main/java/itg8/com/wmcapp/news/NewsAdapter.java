package itg8.com.wmcapp.news;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import itg8.com.wmcapp.R;


/**
 * Created by Android itg 8 on 11/3/2017.
 */

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
     public interface NewsItemClickedListner{
         void onItemNewsClicked();
    }
    private Context context;
    private NewsItemClickedListner listner;

    public NewsAdapter(Context context,  NewsItemClickedListner listner) {
        this.context = context;
        this.listner = listner;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_news, parent, false);
        NewsAdapter.NewsViewHolder holder = new NewsAdapter.NewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     listner.onItemNewsClicked();

                }
            });
        }
    }
}
