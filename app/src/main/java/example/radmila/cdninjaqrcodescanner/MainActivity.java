package example.radmila.cdninjaqrcodescanner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import example.radmila.cdninjaqrcodescanner.data.NinjaPresence;
import example.radmila.cdninjaqrcodescanner.data.NinjaPresenceRepository;
import example.radmila.cdninjaqrcodescanner.util.Constants;


public class MainActivity extends AppCompatActivity {
    private NinjaPresenceRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = new NinjaPresenceRepository(this);
        repository.open();
    }

    //TODO: add a settings activity (to input form URL and entries - configure post request)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.viewScans) {
            Intent intent = new Intent(this, DisplayNotUploadedScansActivity.class);
            startActivity(intent);
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

    /**
     * Called when the user clicks the Scan button
     */
    public void sendMessage(View view) {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                Log.i("xZing", "contents: " + contents); // Handle successful scan
                Toast.makeText(this, "contents: " + contents, Toast.LENGTH_LONG).show();
                if (contents != null && !contents.isEmpty()) performUpload(contents);
            } else if (resultCode == RESULT_CANCELED) { // Handle cancel
                Log.i("xZing", "Cancelled");
                Toast.makeText(this, "Cancelled ", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void performUpload(String ninja) {
        SendNinjaPresenceTask postDataTask = new SendNinjaPresenceTask();
        postDataTask.execute(Constants.URL, ninja);
    }

    //example from: http://codesmith.in/post-data-google-drive-sheet-through-mobile-app/
    //TODO: Move task to own file to avoid implicit reference to activity (possible memory leak) and use events to notify activity of task execution end
    private class SendNinjaPresenceTask extends AsyncTask<String, Void, Boolean> {
        private String ninjaName;

        @Override
        protected Boolean doInBackground(String... data) {
            Boolean result = true;
            String url = data[0];
            ninjaName = data[1];
            String postBody = "";

            try {
                postBody = Constants.NAME + "=" + URLEncoder.encode(ninjaName, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result = false;
            }
            result = sendRequest(url, postBody);

            return result;
        }


        private Boolean sendRequest(String url, String postBody) {
            Boolean result = true;
            try {
                //Create OkHttpClient for sending request
                OkHttpClient client = new OkHttpClient();
                //Create the request body with the help of Media Type
                RequestBody body = RequestBody.create(Constants.FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                //Send the request
                Response response = client.newCall(request).execute();
            } catch (IOException exception) {
                result = false;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //Print message upon execution result
            Toast.makeText(getApplicationContext(), result ? "Message successfully sent!" : "There was some error in sending message. Please try again after some time.", Toast.LENGTH_LONG).show();
            if (!result) {
                saveNinja(ninjaName);
            }
        }
    }

    private void saveNinja(String ninjaName) {
        //create ninja presence
        NinjaPresence ninja = new NinjaPresence();
        ninja.setName(ninjaName);
        ninja.setDate(new Date());
        repository.save(ninja);
    }
}