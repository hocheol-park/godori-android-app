package univ.ajou.godoriapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import univ.ajou.godoriapp.util.HttpCallback;
import univ.ajou.godoriapp.util.HttpConnector;

public class GalleryActivity extends AppCompatActivity {

    private static String S3_GET_LIST = "https://4n54wpk7kd.execute-api.us-east-1.amazonaws.com/dev/images";

    private GridView gridView;
    private JsonArrayAdapter adapter;
    private ArrayList<JSONObject> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Gallery");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView = (GridView) findViewById(R.id.gridview);
        array = new ArrayList<>();

        getS3List();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getS3List() {
        HttpConnector connector = new HttpConnector(this);
        connector.setEndpoint(S3_GET_LIST);
        connector.get(new HttpCallback() {
            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(GalleryActivity.this, "NOPE", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(int code, String msg, String data) {

                try {
                    JSONObject json = new JSONObject(data);
                    JSONArray dataArray = json.getJSONArray("list");

                    if(dataArray.length() > 0) {
                        for (int i = 0; i < dataArray.length(); i++) {
                            array.add(dataArray.getJSONObject(i));
                        }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = setAdapter();
                            gridView.setAdapter(adapter);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public JsonArrayAdapter setAdapter() {
        return new JsonArrayAdapter(this, array, new JsonArrayAdapter.CustomJsonListView() {
            @Override
            public View listLayout(int position, View convertView, ViewGroup parent) {

                JSONObject json = array.get(position);

                ViewHolder holder = null;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = getLayoutInflater().inflate(R.layout.list_gallery, null);
                    convertView.setLayoutParams(new GridView.LayoutParams(300, 500));
                    holder.name = (TextView) convertView.findViewById(R.id.file_name);
                    holder.image = (ImageView) convertView.findViewById(R.id.file_image);

                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                try {
                    final String name = json.getString("name");
                    final String fullurl = json.getString("url");

                    holder.name.setText(name);
                    Picasso.with(getApplicationContext()).load(json.getString("thumbnail")).into(holder.image);
                    holder.image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final ImageViewDialog dialog = new ImageViewDialog(GalleryActivity.this);
                            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialogInterface) {
                                    dialog.setImage(fullurl);
                                    dialog.setName(name);
                                }
                            });
                            dialog.show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return convertView;
            }

        });
    }

    private class ViewHolder {
        TextView name;
        ImageView image;
    }
}
