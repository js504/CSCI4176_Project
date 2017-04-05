package in.geobullet.csci_4176_project.CustomAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import in.geobullet.csci_4176_project.Database.Classes.Poster;
import in.geobullet.csci_4176_project.R;
import in.geobullet.csci_4176_project.Utils.PhotoPicker;

/**
 * Created by jt on 2017-04-04.
 */

public class PhotoPickerBaseAdapter extends BaseAdapter {

    private List<String> posters;

    private PhotoPicker context;

    private LayoutInflater layoutInflater;


    public PhotoPickerBaseAdapter(PhotoPicker context, List<String> posters){
        this.posters = posters;
        this.context = context;
        this.layoutInflater = (LayoutInflater)this.context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return posters.size();
    }

    @Override
    public Object getItem(int position) {
        return posters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.photo_picker_cell, null);
        }

        ImageView posterImg = (ImageView)convertView.findViewById(R.id.photo_picker_img);

        String name =posters.get(position);
        name = name.substring(0, name.lastIndexOf("."));
        int id = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        Log.i("name", Integer.toString(id));

        posterImg.setImageResource(id);

        return convertView;
    }
}
