package layout;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uranga_pc.menudrawer.MainActivity;
import com.example.uranga_pc.menudrawer.R;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;

import android.util.Log;
import android.widget.TextView;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;


import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;

import android.util.Log;
import android.widget.TextView;

import com.google.maps.android.PolyUtil;

import com.google.maps.android.SphericalUtil;

import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import android.content.Intent;

//local 4D:4B:FE:2F:49:3C:00:4D:FA:1D:47:88:CE:3E:D3:71:FC:8D:C0:B5

// repartir 66:4E:90:F0:F6:CD:28:9C:8C:A3:AA:1D:0A:A4:C1:3C:04:A6:B6:74

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmento1 extends Fragment {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MapView mapView;

    private TextView msg;
    private Context context;
    private MediaPlayer mediaPlayer;
    private String velocidad;

    private Polygon polygono;
    private Polygon[] ArrayPolygonos = new Polygon[3];
    private PolygonOptions[] ArraypolygonOptions = new PolygonOptions[3];

    private boolean bandera = true;

    public Fragmento1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Navegar");

        ((MainActivity) getActivity()).getSupportActionBar().setLogo(R.drawable.mundo);

        View v = inflater.inflate(R.layout.fragmento1, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mMap = mapView.getMap();
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.industrial_alarm);
        mMap.setMyLocationEnabled(true);
        setUpMap();

        //return inflater.inflate(R.layout.fragmento1, container, false);

        return v;
        //return inflater.inflate(R.layout.fragmento1, container, false);
    }

    private void setUpMap() {

        //UiSettings.setZoomControlsEnabled(true);
        //mMap.getUiSettings().setZoomControlsEnabled(true);

        //http://itouchmap.com/latlong.html

        ArraypolygonOptions[0] = CrearPoligono(new LatLng(25.424354, -101.004678));//casa
        ArraypolygonOptions[1] = CrearPoligono(new LatLng(25.429123, -100.95588));
        ArraypolygonOptions[2] = CrearPoligono(new LatLng(25.432999, -100.928671));


        for (int i = 0; i < ArrayPolygonos.length; i++) {

            ArraypolygonOptions[i].strokeColor(0x9900695C)
                    .strokeWidth(3)
                    .fillColor(0x504DB6AC);

            ArrayPolygonos[i] = mMap.addPolygon(ArraypolygonOptions[i]);

        }

        //android.Manifest

        int status = getActivity().getPackageManager().checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, getActivity().getPackageName());

        if (status == getActivity().getPackageManager().PERMISSION_GRANTED) {
            // Acquire a reference to the system
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            // Define a listener that responds to location updates
            android.location.LocationListener ll = new android.location.LocationListener() {
                public void onLocationChanged(Location location) {

                    double currentLatitude = location.getLatitude();
                    double currentLongitude = location.getLongitude();

                    LatLng latLng = new LatLng(currentLatitude, currentLongitude);

                    /*CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(13)
                .bearing(90)
                .build();*/

                    Log.i("myTag", latLng.toString());

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

                    for (int i = 0; i < ArraypolygonOptions.length; i++) {

                        if (PolyUtil.containsLocation(latLng, ArraypolygonOptions[i].getPoints(), true) && bandera) {
                            mediaPlayer.start();
                            Log.i("myTag", "entroal if");
                            bandera = false;
                            ArrayPolygonos[i].setStrokeColor(Color.RED);
                            ArrayPolygonos[i].setFillColor(0x40ff0000);

                        } else {
                            //mediaPlayer.stop();
                            Log.i("myTag", "else");
                            bandera = true;
                            ArrayPolygonos[i].setStrokeColor(0x9900695C);
                            ArrayPolygonos[i].setFillColor(0x504DB6AC);
                        }

                    }

                }

                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                public void onProviderEnabled(String provider) {

                }

                public void onProviderDisabled(String provider) {

                }

            };

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, ll);
        }//cierre if permiso

    }//set up map


    private PolygonOptions CrearPoligono(LatLng centro) {

        Log.i("myTag", "CrearPoligono");
        int steps = 30;
        int degreeStep = 360 / steps;

        mMap.addMarker(new MarkerOptions().position(centro).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.icono)).anchor(0.5f, 0.5f));

        PolygonOptions poly = new PolygonOptions();

        for (int i = 0; i < steps; i++) {

            LatLng p = SphericalUtil.computeOffset(centro,300, degreeStep * i);
            poly.add(p);

        }
        return poly;
    }

    @Override
    public void onResume() {

        super.onResume();
        mapView.onResume();
        Log.i("myTag", "onResume()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        Log.i("myTag", "onDestroy()");
    }

    @Override
    public void onPause() {
        super.onPause();
        //mediaPlayer.onPause();
        mediaPlayer.stop();
        Log.i("myTag", "onPause()");
        /*if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }*/
        /*if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }*/

    }

    @Override
    public void onStop() {
        super.onStop();
        mediaPlayer.stop();
        Log.i("myTag", "onStop()");
    }


}
