package itg8.com.wmcapp.board;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import itg8.com.wmcapp.R;

/**
 * Created by Android itg 8 on 11/3/2017.
 */

class NoticeBoardAdater extends RecyclerView.Adapter<NoticeBoardAdater.NoticeBoardViewHolder> {
    private Context context;

    public NoticeBoardAdater(Context context) {
        this.context = context;
    }

    @Override
    public NoticeBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_notice_board, parent, false);
        NoticeBoardViewHolder holder = new NoticeBoardViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoticeBoardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void addItems() {

    }

    public class NoticeBoardViewHolder extends RecyclerView.ViewHolder {
        public NoticeBoardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
