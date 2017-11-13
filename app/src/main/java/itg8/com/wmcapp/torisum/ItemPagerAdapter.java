package itg8.com.wmcapp.torisum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.torisum.model.Fileupload;


public class ItemPagerAdapter extends android.support.v4.view.PagerAdapter {

    private final List<Fileupload> mItems;
    private ImageClickedListener listener;
    Context mContext;
     public interface ImageClickedListener{
         void onItemClicked(int position,Fileupload fileupload );
     }


    public  ItemPagerAdapter(Context context, List<Fileupload> items, ImageClickedListener listener) {
        this.mContext = context;
        this.mItems = items;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item_torisum, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//        if(mItems instanceof Fileupload)
//        {
//            Fileupload fileupload = (Fileupload) mItems;
//        }
        Picasso.with(mContext)
                .load(CommonMethod.BASE_URL + mItems.get(position).getFilepath())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(mContext)
                                .load(CommonMethod.BASE_URL + mItems.get(position).getFilepath())
                                .into((imageView));
                    }
                });

        container.addView(itemView);
         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                  listener.onItemClicked(position, mItems.get(position));

             }
         });
        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}