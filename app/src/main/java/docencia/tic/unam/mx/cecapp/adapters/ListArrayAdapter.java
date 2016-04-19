package docencia.tic.unam.mx.cecapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import docencia.tic.unam.mx.cecapp.R;
import docencia.tic.unam.mx.cecapp.models.EventoDeLista;

public class ListArrayAdapter extends ArrayAdapter<EventoDeLista> {
    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView home;
    }

    public ListArrayAdapter(Context context, ArrayList<EventoDeLista> mEventos) {
        super(context, R.layout.item_evento, mEventos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        EventoDeLista mEvento = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_evento, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.home = (TextView) convertView.findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(mEvento.getName());
        viewHolder.home.setText(mEvento.getStartDate());
        // Return the completed view to render on screen
        return convertView;
    }

    public EventoDeLista getEvento(int position){
        return getItem(position);
    }
}