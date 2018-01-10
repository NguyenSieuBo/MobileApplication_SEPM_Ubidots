package com.ubidots.bo.ubidotsapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Bo on 11/23/17.
 */
public class CustomListAdapter extends BaseAdapter {

    private List<ViewOptions> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context aContext, List<ViewOptions> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    // Count how many items in the list
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Converts an xml layout files - (list_layout_options) in to the corresponding ViewGroup
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_layout_options, null);
            holder = new ViewHolder();
            holder.pictureView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.itemView = (TextView) convertView.findViewById(R.id.itemView);
            holder.descriptionView = (TextView) convertView.findViewById(R.id.descriptionView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ViewOptions item = this.listData.get(position);
        holder.itemView.setText(item.getItemName());
        holder.descriptionView.setText("Description: " + item.getDescription());

        int imageId = this.getMipmapResIdByName(item.getPicture());

        holder.pictureView.setImageResource(imageId);

        return convertView;
    }

    // Find Id of a image which equivalent with its name in the mipmap folder
    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();

        // Return to 0 if cannot find Image
        int resID = context.getResources().getIdentifier(resName, "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

    static class ViewHolder {
        ImageView pictureView;
        TextView itemView;
        TextView descriptionView;
    }
}
