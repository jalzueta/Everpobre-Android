package com.fillingapps.everpobre.utils.map;

import com.fillingapps.everpobre.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapUtil {

    public static void centerMap(GoogleMap map, double latitude, double longitude, int zoomLevel) {

        LatLng coordinate = new LatLng(latitude, longitude);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(coordinate).zoom(zoomLevel)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public static void addMarker(GoogleMap map, double latitude, double longitude, String title) {
        LatLng coordinate = new LatLng(latitude, longitude);
        MarkerOptions marker = new MarkerOptions().position(coordinate).title(title);
        // Color del icono por defecto
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

        // Icono personalizado
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.note));
        map.addMarker(marker);
    }

}
