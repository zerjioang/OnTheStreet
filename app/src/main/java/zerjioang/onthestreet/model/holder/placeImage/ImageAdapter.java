package zerjioang.onthestreet.model.holder.placeImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import zerjioang.onthestreet.model.pojox.Place;

/**
 * Created by .local on 22/04/2017.
 */

public class ImageAdapter extends BaseAdapter {

    private static final int DEFAULT_PADDING = 2;
    private final ArrayList<File> imageList;
    private Context mContext;
    private final Place place;

    public ImageAdapter(Context c, Place place) {
        mContext = c;
        this.place = place;
        this.imageList = place.getImagelist();
    }

    public int getCount() {
        return imageList.size();
    }

    public String getItem(int position) {
        return imageList.get(position).getAbsolutePath();
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            imageView.setLayoutParams(new GridView.LayoutParams(width/2, 300));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap bitmap = loadBitmap(getItem(position));

        imageView.setImageBitmap(bitmap);
        return imageView;
    }

    private Bitmap loadBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }
}
