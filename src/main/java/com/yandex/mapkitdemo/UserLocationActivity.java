package com.yandex.mapkitdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

/**
 * This example shows how to display and customize user location arrow on the map.
 */
public class UserLocationActivity extends Activity implements UserLocationObjectListener {
    /**
     * Replace "your_api_key" with a valid developer key.
     * You can get it at the https://developer.tech.yandex.ru/ website.
     */
    private final String MAPKIT_API_KEY = "1036d540-0558-4d3f-9917-106cf8db8f7c";
//    private final String LOCATION_PERMISSION_NAME = "android.permission.ACCESS_FINE_LOCATION";
    private final int LOCATION_PERMISSION_REQUEST = 1;
    private final int MY_PERMISSION_REQUEST = 10;
    private MapView mapView;
    private UserLocationLayer userLocationLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.user_location);
        super.onCreate(savedInstanceState);

        mapView = findViewById(R.id.mapview);

        MapKit mapKit = MapKitFactory.getInstance();
        mapView.getMap().move(new CameraPosition(new Point(0, 0), 14, 0, 0));
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());

        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST);
        }
//        mapKit.createLocationManager().requestSingleUpdate(new LocationListener() {
//            @Override
//            public void onLocationUpdated(@NonNull Location location) {
//                mapView.getMap().move(
//                        new CameraPosition(location.getPosition(), 14.0f, 0.0f, 0.0f),
//                        new Animation(Animation.Type.SMOOTH, 1),
//                        null);
//            }
//
//            @Override
//            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
//
//            }
//        });

//        moveSmoothToTarget(userLocationLayer.cameraPosition().getTarget());
    }

    private void moveSmoothToTarget(Point target){
        if (target==null)return;
        mapView.getMap().move(
                new CameraPosition(target, 12f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0.7f),
                null
        );
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.user_arrow));

        userLocationLayer.setAnchor(
                new PointF((float) (mapView.getWidth() * 0.5), (float) (mapView.getHeight() * 0.5)),
                new PointF((float) (mapView.getWidth() * 0.5), (float) (mapView.getHeight() * 0.83)));
        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE & 0x99ffffff);
        Log.e("HERE",String.valueOf(userLocationView.getPin().getGeometry().getLongitude()));
    }

    @Override
    public void onObjectRemoved(UserLocationView view) {
    }

    @Override
    public void onObjectUpdated(UserLocationView view, ObjectEvent event) {
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == LOCATION_PERMISSION_REQUEST) {
//            addUserLocationLayer();
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//
//    private void addUserLocationLayer() {
//        userLocationLayer.setHeadingEnabled(true);
//        userLocationLayer.setObjectListener(this);
//    }
}
