package itg8.com.wmcapp.torisum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import itg8.com.wmcapp.R;


public class ItemPagerAdapter extends android.support.v4.view.PagerAdapter {

    Context mContext;
    int[] mItems;


    public ItemPagerAdapter(Context context, int[] items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item_torisum, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Picasso.with(mContext)
                .load(mItems[position])
                .placeholder(R.drawable.bpkuti)
                .error(R.drawable.bpkuti)
                .into(imageView);

        container.addView(itemView);
        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}