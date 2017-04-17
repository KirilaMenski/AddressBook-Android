package by.softteco.addressbook.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirila on 17.4.17.
 */

public class DecodePolylines {

    public static List<LatLng> decodePolylines(final String polylines) {
        int len = polylines.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<>();
        double lat = 0.0;
        double lng = 0.0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;

            do {
                b = polylines.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;

            do {
                b = polylines.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(lat / 100000d, lng / 100000d));

        }
        return decoded;
    }

}
