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

import com.google.android.gms.maps.model.Marker;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;



/**
 * A simple {@link Fragment} subclass.
 */

public class Fragmento2 extends Fragment {

    private GoogleMap mMap2; // Might be null if Google Play services APK is not available.
    private MapView mapView2;

    private ClusterManager<MyItem> mClusterManager;
    private LatLng[] arrayLatLng = new LatLng[13];
    private Marker mPositionMarker2;


    public Fragmento2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Localización De Camaras");
        ((MainActivity) getActivity()).getSupportActionBar().setLogo(R.drawable.security_camera);

        View v = inflater.inflate(R.layout.fragmento2, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView2 = (MapView) v.findViewById(R.id.map2);
        mapView2.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mMap2 = mapView2.getMap();
        mMap2.getUiSettings().setMyLocationButtonEnabled(false);
        mPositionMarker2 = mMap2.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.my_position))
                .anchor(0.5f, 0.7f)
                .position(new LatLng(25.432999, -100.928671)));


        mMap2.setMyLocationEnabled(true);

        setUpClusterer();

        setUpposition();

        return v;
    }

    /*private void setUpClustering() {
        mMap2.setMyLocationEnabled(true);
        //mMap2.getUiSettings().setZoomControlsEnabled(true);
        mMap2.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(25.426604, -100.995362), 12));

    }//set up map*/
    private void setUpposition() {

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
                    mPositionMarker2.setPosition(latLng);
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
    }

    public class MyItem implements ClusterItem {
        private final LatLng mPosition;

        public MyItem(double lat, double lng) {
            mPosition = new LatLng(lat, lng);
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }
    }


    private void setUpClusterer() {
        // Declare a variable for the cluster manager.

        // Position the map.
        mMap2.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(25.426604, -100.995362), 9));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(getActivity(), mMap2);

        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
                //put your code here
                mMap2.getUiSettings().setMapToolbarEnabled(false);
                return false;
            }
        });

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap2.setOnCameraChangeListener(mClusterManager);
        mMap2.setOnMarkerClickListener(mClusterManager);

        arrayLatLng[0] = new LatLng(25.450903, -100.998538);

        arrayLatLng[1] = new LatLng(25.47202, -100.962274);
        arrayLatLng[2] = new LatLng(25.466014, -100.967295);
        arrayLatLng[3] = new LatLng(25.458286, -100.983496);
        arrayLatLng[4] = new LatLng(25.457726, -100.953895);
        arrayLatLng[5] = new LatLng(25.454778, -101.008751);

        arrayLatLng[6] = new LatLng(25.471206, -100.977681);

        arrayLatLng[7] = new LatLng(25.452724, -100.974119);
        arrayLatLng[8] = new LatLng(25.444686, -100.958465);
        arrayLatLng[9] = new LatLng(25.435595, -100.912578);
        arrayLatLng[10] = new LatLng(25.430596, -100.945065);
        arrayLatLng[11] = new LatLng(25.433037, -100.996735);
        arrayLatLng[12] = new LatLng(25.437914, -101.018472);


        // Add cluster items (markers) to the cluster manager.
        addItems();
    }

    private void addItems() {

        for (int i = 0; i < arrayLatLng.length; i++) {
            MyItem offsetItem = new MyItem(arrayLatLng[i].latitude, arrayLatLng[i].longitude);
            mClusterManager.addItem(offsetItem);
        }
    }


    @Override
    public void onResume() {

        super.onResume();
        mapView2.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView2.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView2.onLowMemory();
    }

}