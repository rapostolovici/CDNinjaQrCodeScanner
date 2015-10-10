package example.radmila.cdninjaqrcodescanner.util;


import com.squareup.okhttp.MediaType;

//TODO: put your form data
public class Constants {
    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //URL derived from form URL
    public static final String URL = "form_URL";
    //input element ids found from the live form page
    public static final String NAME = "entry_name";
    //TODO: add a timestamp element to the form (the timestamp of the scan)
}
