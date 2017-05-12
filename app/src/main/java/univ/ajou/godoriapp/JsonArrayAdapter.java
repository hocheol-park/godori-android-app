package univ.ajou.godoriapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

public class JsonArrayAdapter extends BaseAdapter implements View.OnClickListener {

    ArrayList<JSONObject> jsonArray;

    public interface CustomJsonListView {

        public View listLayout(int position, View convertView, ViewGroup parent);
    }

    protected final CustomJsonListView customListViewLayout;

    public JsonArrayAdapter(Context context, ArrayList<JSONObject> array,
                            CustomJsonListView list) {

        jsonArray = array;
        this.customListViewLayout = list;

    }

    @Override
    public View getView(final int position, View convertView,
                        final ViewGroup parent) {

        convertView = this.customListViewLayout.listLayout(position, convertView, parent);
        return convertView;
    }

    @Override
    public int getCount() {
        return jsonArray.size();
    }

    @Override
    public Object getItem(int position) {
        return jsonArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {

    }

    public void clear(){
        jsonArray.clear();
        notifyDataSetInvalidated();
    }

}
