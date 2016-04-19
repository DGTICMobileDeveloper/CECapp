package docencia.tic.unam.mx.cecapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import docencia.tic.unam.mx.cecapp.R;
import docencia.tic.unam.mx.cecapp.models.ItemListaInteres;

public class InterestListArrayAdapter extends ArrayAdapter<ItemListaInteres> {

    // View lookup cache
    public static class ViewHolder {
        public ImageView icon;
        public TextView subject;
        public CheckBox status;
        public int id;
    }

    public InterestListArrayAdapter(Context context, ArrayList<ItemListaInteres> items) {
        super(context, R.layout.item_evento, items);
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.i(">>>InterestListArray", "getView pos "+position+ " -> "+ convertView+" -> PG "+ parent);
        // Get the data item for this position
        ItemListaInteres item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_interes, parent, false);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.item_interes_iv);
            viewHolder.subject = (TextView) convertView.findViewById(R.id.item_interes_tv);
            viewHolder.status = (CheckBox) convertView.findViewById(R.id.item_interes_cb);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.id = item.getId();
        viewHolder.subject.setText(item.getSubject());
        viewHolder.status.setChecked(item.isChecked()); // Esto no da error aunque no esté inicializado check
        // Return the completed view to render on screen
        return convertView;
    }

    public ItemListaInteres getInteres(int position){
        return getItem(position);
    }
}