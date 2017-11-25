package itg8.com.wmcapp.torisum;


import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.torisum.model.Fileupload;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageZoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageZoomFragment extends Fragment  {
    //implements View.OnTouchListener
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final float TEMP = 15f;
    @BindView(R.id.img)
    ImageView img;

    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Fileupload model;
    private Context mContext;
    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 80f,MAX_ZOOM = 1f;



    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    public ImageZoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageZoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImageZoomFragment newInstance(Fileupload param1, String param2) {
        ImageZoomFragment fragment = new ImageZoomFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_zoom, container, false);
        unbinder = ButterKnife.bind(this, view);
         init();
        return view;
    }

    private void init() {

        Picasso.with(mContext)
                .load(CommonMethod.BASE_URL +model.getFilepath())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {
//                        Drawable d = img.getDrawable();
//                        if(d!= null)
//                        {
//                            RectF imgRecF = new RectF(0,0,d.getIntrinsicWidth(), d.getIntrinsicHeight());
//                            RectF viewRectF = new RectF(0,0,700,1600);
//                            matrix.setRectToRect(imgRecF, viewRectF,);
//                            img.setImageMatrix(matrix);
//                        }

//                        scaleImage(2);

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(mContext)
                                .load(CommonMethod.BASE_URL +model.getFilepath())
                                .into((img));
                    }
                });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
         mContext = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mContext!= null)
        {
            mContext= null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//
//        ImageView view = (ImageView) v;
//        view.setScaleType(ImageView.ScaleType.MATRIX);
//        float scale;
//
//        dumpEvent(event);
//        // Handle touch events here...
//
//        switch (event.getAction() & MotionEvent.ACTION_MASK)
//        {
//            case MotionEvent.ACTION_DOWN:   // first finger down only
////                savedMatrix.set(matrix);
////                start.set(event.getX(), event.getY());
////               Logs.d(TAG, "mode=DRAG"); // write to LogCat
////                mode = DRAG;
//                break;
//
//            case MotionEvent.ACTION_UP: // first finger lifted
//                Log.d(TAG," mode =UP");
//                break;
//
//            case MotionEvent.ACTION_POINTER_UP: // second finger lifted
//
//                mode = NONE;
//                Logs.d(TAG, "mode=NONE");
//                break;
//
//            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
//
//                oldDist = spacing(event);
//                Logs.d(TAG, "oldDist=" + oldDist);
//                if (oldDist > 50f) {
//                    savedMatrix.set(matrix);
//                    midPoint(mid, event);
//                    mode = ZOOM;
//                    Logs.d(TAG, "mode=ZOOM");
//
//                }
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//
//                if (mode == DRAG)
//                {
////                    matrix.set(savedMatrix);
////                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
//
////                    matrix.postTranslate(event.getX(), event.getY()); // create the transformation in the matrix  of points
//                }
//                else if (mode == ZOOM)
//                {
//                    // pinch zooming
//                    float newDist = spacing(event);
//                    Logs.d(TAG, "newDist=" + newDist);
//                    if (newDist > TEMP)
//                    {
//
//
//                        matrix.set(savedMatrix);
//                        scale = newDist / oldDist;// setting the scaling of the
//                        // matrix...if scale > 1 means
//                        // zoom in...if scale < 1 means
//                        // zoom out
//                        Log.d(TAG,"scale= "+scale+" Mid.x="+mid.x+" mid.y="+mid.y);
////                        matrix.postScale(scale, scale, mid.x, mid.y);
//                        matrix.postScale(scale, scale, mid.x, mid.y);
//                    }else
//                    {
//                        Log.d(TAG,"less than temp distance ");
//                    }
//                }
//                break;
//        }
//
//
//        view.setImageMatrix(matrix); // display the transformation on screen
//
//        return true;
//    }
//
//    private float spacing(MotionEvent event)
//    {
//        float x = event.getX(0) - event.getX(1);
////        float x = event.getX(0);
////        float y = event.getY(0);
//        float y = event.getY(0) - event.getY(1);
//        return (float) Math.sqrt(x * x + y * y);
////        return (float) Math.abs(x + y );
//    }
//
//    /*
//     * --------------------------------------------------------------------------
//     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
//     * Description: calculates the midpoint between the two fingers
//     * ------------------------------------------------------------
//     */
//
//    private void midPoint(PointF point, MotionEvent event)
//    {
//        float x = event.getX(0) + event.getX(1);
//        float y = event.getY(0) + event.getY(1);
//        point.set(x / 2, y / 2);
////        point.set(x , y );
//    }
//
//    /** Show an event in the LogCat view, for debugging */
//    private void dumpEvent(MotionEvent event)
//    {
//        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
//        StringBuilder sb = new StringBuilder();
//        int action = event.getAction();
//        int actionCode = action & MotionEvent.ACTION_MASK;
//        sb.append("event ACTION_").append(names[actionCode]);
//
//        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
//        {
//            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
//            sb.append(")");
//        }
//
//        sb.append("[");
//        for (int i = 0; i < event.getPointerCount(); i++)
//        {
//            sb.append("#").append(i);
//            sb.append("(pid ").append(event.getPointerId(i));
//            sb.append(")=").append((int) event.getX(i));
//            sb.append(",").append((int) event.getY(i));
//            if (i + 1 < event.getPointerCount())
//                sb.append(";");
//        }
//
//        sb.append("]");
//        Logs.d("Touch Events ---------", sb.toString());
//    }
//
//    private void scaleImage(float scaleFactor) {
//        Matrix displayMatrix = new Matrix();
//        matrix = img.getImageMatrix();
//
////        float x = (img.getWidth() - img.getDrawable().getIntrinsicWidth() * scaleFactor) / 2;
////        float y = (img.getHeight() - img.getDrawable().getIntrinsicHeight() * scaleFactor) / 2;
//float x = (700 - img.getDrawable().getIntrinsicWidth() * scaleFactor) / 2;
//        float y = (1600 - img.getDrawable().getIntrinsicHeight() * scaleFactor) / 2;
//
//        matrix.postScale(scaleFactor, scaleFactor);
//        matrix.postTranslate(x, y);
//
//        displayMatrix.set(matrix);
//        img.setImageMatrix(displayMatrix);
//    }
}
