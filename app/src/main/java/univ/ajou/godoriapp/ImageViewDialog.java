package univ.ajou.godoriapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by SUNGMIN on 2017-05-24.
 */

public class ImageViewDialog extends Dialog {

    Context mContext;
    ImageView imageView;
    TextView textView;

    public ImageViewDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_image);

        imageView = (ImageView) findViewById(R.id.zoomed_image);
        textView = (TextView) findViewById(R.id.image_name);
    }

    public void setImage(String url) {
        Picasso.with(mContext).load(url).into(imageView);
    }

    public void setName(String name) {
        textView.setText(name);
    }
}
