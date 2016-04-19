package net.ismail.apptest;

import android.location.Location;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ismail on 2016-04-16.
 */
public class HelperUtils {

    public HelperUtils(){

    }

    // convert InputStream to String
    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    //count the number of product in a venue
    public static int countProduct(Venue v,ArrayList<Product> allProduct) {

        //set of product ids
        Set<Integer> productIds = new HashSet<Integer>();
        for (int i = 0; i < allProduct.size(); i++) {
            for (int j = 0; j < allProduct.get(i).getCategoryIds().length; j++) {
                productIds.add(allProduct.get(i).getCategoryIds()[j]);
            }
        }

        //removing null value from set
        productIds.remove(null);

        int count=0;

        //all product categories ids
        Integer pids[]=productIds.toArray(new Integer[productIds.size()]);

        //categories ids for the venue
        int vids[]=v.getCategoryIds();

        for(int i=0;i<vids.length;i++){

            for(int j=0;j<pids.length;j++){
                //matching product categories ids with venues categories ids
                if(pids[j]==vids[i]){
                    count++;

                }
            }

        }

        return count;

    }

    //downlod the json data as string
    public static String downloadUrl(String myurl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(20000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();
            // Convert the InputStream into a string
            String contentAsString = getStringFromInputStream(is);
            return contentAsString;

        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    //calculate distance between two gps location
    public static float calculateDistence(float latitudeA, float longitudeA, Double latitudeB, Double longitudeB) {

        Location locationA = new Location("locationA");
        locationA.setLatitude(latitudeA);
        locationA.setLongitude(longitudeA);

        Location locationB = new Location("locationB");
        locationB.setLatitude(latitudeB);
        locationB.setLongitude(longitudeB);
        float distance = locationA.distanceTo(locationB);

        return distance;

    }

}
