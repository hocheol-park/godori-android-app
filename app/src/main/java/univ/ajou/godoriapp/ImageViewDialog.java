package univ.ajou.godoriapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pl.polidea.view.ZoomView;

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

        RelativeLayout rl = new RelativeLayout(mContext);
        rl.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(rl);

        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dialog_image, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(mContext);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        //zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(10f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        //zoomView.setMiniMapCaption(""); //미니 맵 내용
        //zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정

        imageView = (ImageView) v.findViewById(R.id.zoomed_image);
        textView = (TextView) v.findViewById(R.id.image_name);

        rl.addView(zoomView);
    }

    public void setImage(String url) {
        Picasso.with(mContext).load(url).into(imageView);
    }

    public void setName(String name) {
        textView.setText(name);
    }
}
