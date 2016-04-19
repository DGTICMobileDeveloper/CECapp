package docencia.tic.unam.mx.cecapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import docencia.tic.unam.mx.cecapp.AsyncHttpRetriever;
import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.R;
import docencia.tic.unam.mx.cecapp.models.Evento;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, List<String>> mapCategoryInfo;
    private List<String> categoryList;
    private byte origen;

    public CustomExpandableListAdapter(Activity context, List<String> categories,
                                       Map<String, List<String>> mapCategoryInfo, byte origen) {
        this.context = context;
        this.mapCategoryInfo = mapCategoryInfo;
        this.categoryList = categories;
        this.origen = origen;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return mapCategoryInfo.get(categoryList.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if(origen == Constants.SOURCE_EVENTO_INFO) {
            final String info = (String) getChild(groupPosition, childPosition);
            final LayoutInflater inflater = context.getLayoutInflater();
            Button button;
            ImageView img;

            switch (groupPosition){
                case 1: // Ubicación
                    convertView = inflater.inflate(R.layout.child_item_boton, null);
                    button = (Button) convertView.findViewById(R.id.evento_item_button);
                    img = (ImageView) convertView.findViewById(R.id.evento_item_img);
                    button.setText(Constants.BUTTON_LOCATION);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_loc,context.getTheme()));
                    }else {
                        img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_loc));
                    }
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intentMap = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(Constants.LOCATION_EVENT_LAT_LON));
                            context.startActivity(intentMap);
                        }
                    });
                    break;
                case 2: // Fecha
                    convertView = inflater.inflate(R.layout.child_item_boton, null);
                    button = (Button) convertView.findViewById(R.id.evento_item_button);
                    img = (ImageView) convertView.findViewById(R.id.evento_item_img);
                    button.setText(Constants.BUTTON_LOCATION);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_calendar,context.getTheme()));
                    }else {
                        img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_calendar));
                    }
                    button.setText(Constants.BUTTON_AGENDA);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Evento evento = AsyncHttpRetriever.evento;
                            /** TODO registrar el id del evento en una base de datos (relacionandolo con el
                             *  id del evento en el calendario) esto para poder modificar el evento en el
                             *  calendario (y no dejarle la chamba de eliminarlo al usuario)
                             */
                            Intent intent = new Intent(Intent.ACTION_INSERT)
                                    .setData(Events.CONTENT_URI)
                                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, evento.getStartDate().getTime())
                                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, evento.getEndDate().getTime())
                                    .putExtra(Events.TITLE, evento.getName())
                                    .putExtra(Events.DESCRIPTION, evento.getDescription())
                                    .putExtra(Events.EVENT_LOCATION, Constants.LOCATION_EVENT);
                            // Para que no se pueda 'encimar/overlap' con otros eventos
                            //.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
                            // Para poner correos electrónicos
                            //.putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
                            context.startActivity(intent);
                        }
                    });
                    break;
                case 5: // Links
                    convertView = inflater.inflate(R.layout.child_item_link, null);
                    ImageView icon = (ImageView) convertView.findViewById(R.id.evento_item_icon);
                    icon.setImageDrawable(getIcon(childPosition));
                    break;
                default : // Otros
                    convertView = inflater.inflate(R.layout.child_item_simple, null);
                    break;
            }
            /*
            if (groupPosition == 1 || groupPosition == 2) {
                convertView = inflater.inflate(R.layout.child_item_boton, null);
                Button bItem = (Button) convertView.findViewById(R.id.evento_item_button);
                if (groupPosition == 1) {
                    bItem.setText(Constants.BUTTON_LOCATION);
                    bItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intentMap = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(Constants.LOCATION_EVENT_LAT_LON));
                            context.startActivity(intentMap);
                        }
                    });
                } else {
                    bItem.setText(Constants.BUTTON_AGENDA);
                    bItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Evento evento = AsyncHttpRetriever.evento;
//                            * TODO registrar el id del evento en una base de datos (relacionandolo con el
//                             *  id del evento en el calendario) esto para poder modificar el evento en el
//                             *  calendario (y no dejarle la chamba de eliminarlo al usuario)
                            Intent intent = new Intent(Intent.ACTION_INSERT)
                                    .setData(Events.CONTENT_URI)
                                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, evento.getStartDate().getTime())
                                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, evento.getEndDate().getTime())
                                    .putExtra(Events.TITLE, evento.getName())
                                    .putExtra(Events.DESCRIPTION, evento.getDescription())
                                    .putExtra(Events.EVENT_LOCATION, Constants.LOCATION_EVENT);
                                    // Para que no se pueda 'encimar/overlap' con otros eventos
                                    //.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
                                    // Para poner correos electrónicos
                                    //.putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
                            context.startActivity(intent);
                        }
                    });
                }
            } else {
                convertView = inflater.inflate(R.layout.child_item_simple, null);
            }*/
            TextView item = (TextView) convertView.findViewById(R.id.evento_item_info);
            item.setText(info);
        }
        else if(origen == Constants.SOURCE_EVENTO_EXPOS) {
            final String info = (String) getChild(groupPosition, childPosition);
            LayoutInflater inflater = context.getLayoutInflater();

            convertView = inflater.inflate(R.layout.child_item_simple, null);

            TextView item = (TextView) convertView.findViewById(R.id.evento_item_info);
            item.setText(info);
        }

        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return mapCategoryInfo.get(categoryList.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return categoryList.get(groupPosition);
    }

    public int getGroupCount() {
        return categoryList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String category = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.evento_category);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(category);
        /*
        if(groupPosition == 0) {
            ExpandableListView mExpandableListView = (ExpandableListView) parent;
            mExpandableListView.expandGroup(groupPosition);
        }*/
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public Drawable getIcon(int position) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (position) {
                case 0: // Dominio web
                    drawable = context.getResources().getDrawable(R.drawable.ic_domain, context.getTheme());
                    break;
                case 1: // Facebook
                    drawable = context.getResources().getDrawable(R.drawable.ic_fb, context.getTheme());
                    break;
                case 2: // Twitter
                    drawable = context.getResources().getDrawable(R.drawable.ic_tw, context.getTheme());
                    break;
                case 3: // YouTube
                    drawable = context.getResources().getDrawable(R.drawable.ic_yt, context.getTheme());
                    break;
                default:
                    drawable = context.getResources().getDrawable(R.drawable.ic_3, context.getTheme());
                    break;
            }
        }else {
            switch (position) {
                case 0: // Dominio web
                    drawable =  context.getResources().getDrawable(R.drawable.ic_domain);
                    break;
                case 1: // Facebook
                    drawable =  context.getResources().getDrawable(R.drawable.ic_fb);
                    break;
                case 2: // Twitter
                    drawable =  context.getResources().getDrawable(R.drawable.ic_tw);
                    break;
                case 3: // YouTube
                    drawable =  context.getResources().getDrawable(R.drawable.ic_yt);
                    break;
                default:
                    drawable =  context.getResources().getDrawable(R.drawable.ic_3);
                    break;
            }
        }
        return drawable;
    }
}