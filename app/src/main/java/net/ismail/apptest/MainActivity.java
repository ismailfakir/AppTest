package net.ismail.apptest;

import android.content.Context;
import android.content.DialogInterface;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TableLayout tableLayout;

    private ArrayList<Venue> allVenue;
    private ArrayList<Product> allProduct;

    private Double lon;
    private Double lad;
    GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //table layout
        tableLayout = (TableLayout) findViewById(R.id.vanue_table);

        // adding table heading
        tableLayout.addView(tableChild(getApplicationContext(),"VENUE","NUMBER OF PRODUCT","DISTANCE"));

        allVenue = new ArrayList<Venue>();
        allProduct = new ArrayList<Product>();

        gps = new GPSTracker(this);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //check network
        if (networkInfo != null && networkInfo.isConnected()) {
            //get all the product
            new GetAllProductJsonTask().execute("http://private-anon-548a7a015-mobile35.apiary-mock.com/products");
            //get all the venue
            new GetAllVenueJsonTask().execute("http://private-anon-548a7a015-mobile35.apiary-mock.com/venues");


        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Network connection Error")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }

    }

    // add a new Row to table view
    private View tableChild(Context context, String venueName, String noOfproduct, String distance) {
        TableRow tr = new TableRow(this);
        View v = LayoutInflater.from(context).inflate(R.layout.table_row, tr, false);

        //Venue
        TextView vn = (TextView) v.findViewById(R.id.vanue_name);
        vn.setText(venueName);

        //Product
        TextView pn = (TextView) v.findViewById(R.id.product_name);
        pn.setText(noOfproduct);

        //GPS Distance
        TextView gpsDistance = (TextView) v.findViewById(R.id.distance_from_venue);
        gpsDistance.setText(distance);

        return v;
    }


    // ading new row to table
    private void addRow(Venue v) {

        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();

        //calculate distance from the venue in meter
        float distance = HelperUtils.calculateDistence(v.getLatitude(), v.getLongitude(), latitude, longitude);

        tableLayout.addView(tableChild(getApplicationContext(), v.getName(), HelperUtils.countProduct(v, allProduct) + "", distance + " Meter"));

    }

    // get all product JSON request
    private class GetAllProductJsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return HelperUtils.downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Gson gson = new GsonBuilder().create();
            Type productType = new TypeToken<List<Product>>() {
            }.getType();

            List<Product> productListTemp = gson.fromJson(result, productType);
            //add all product to product array list
            for (int i = 0; i < productListTemp.size(); i++) {
                allProduct.add(productListTemp.get(i));

            }

        }
    }


    // get all venue JSON request
    private class GetAllVenueJsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return HelperUtils.downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Gson gson = new GsonBuilder().create();
            Type venueType = new TypeToken<List<Venue>>() {
            }.getType();

            //add all venue to venue array list
            List<Venue> venueListTemp = gson.fromJson(result, venueType);
            for (int i = 0; i < venueListTemp.size(); i++) {
                allVenue.add(venueListTemp.get(i));
            }

            // ading all venue row to table
            for (int i = 0; i < allVenue.size(); i++) {
                addRow(allVenue.get(i));
            }

        }
    }

}




