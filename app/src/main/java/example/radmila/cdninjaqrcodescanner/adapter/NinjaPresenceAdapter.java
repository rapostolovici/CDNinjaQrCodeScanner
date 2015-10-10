package example.radmila.cdninjaqrcodescanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import example.radmila.cdninjaqrcodescanner.R;
import example.radmila.cdninjaqrcodescanner.data.NinjaPresence;

public class  NinjaPresenceAdapter extends ArrayAdapter<NinjaPresence> {
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public NinjaPresenceAdapter(Context context, List<NinjaPresence> ninjas) {
        super(context, 0, ninjas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        NinjaPresence ninja = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ninja_presence_layout, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        // Populate the data into the template view using the data object
        name.setText(ninja.getName());
        date.setText(DATE_FORMAT.format(ninja.getDate()));
        // Return the completed view to render on screen
        return convertView;
    }
}
