package example.radmila.cdninjaqrcodescanner;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.List;

import example.radmila.cdninjaqrcodescanner.adapter.NinjaPresenceAdapter;
import example.radmila.cdninjaqrcodescanner.data.NinjaPresence;
import example.radmila.cdninjaqrcodescanner.data.NinjaPresenceRepository;

//TODO: implement functionality to upload all not uploaded scans (delete them upon upload)
//TODO: allow the user to delete all not uploaded scans (even if not uploaded)
//TODO: allow specific scans to be deleted (maybe a swipe gesture) with "undo" option
public class DisplayNotUploadedScansActivity extends ListActivity {

    private NinjaPresenceRepository repository;
    private ArrayAdapter<NinjaPresence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = new NinjaPresenceRepository(this);
        repository.open();

        List<NinjaPresence> ninjas = repository.findAll();
        adapter = new NinjaPresenceAdapter(this, ninjas);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_not_uploaded_scans, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        repository.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        repository.close();
        super.onPause();
    }

}
