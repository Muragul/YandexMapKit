package com.yandex.mapkitdemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.gesture.GestureOverlayView;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Circle;
import com.yandex.mapkit.geometry.Geo;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CircleMapObject;
import com.yandex.mapkit.map.Cluster;
import com.yandex.mapkit.map.ClusterListener;
import com.yandex.mapkit.map.ClusterTapListener;
import com.yandex.mapkit.map.GeoObjectSelectionMetadata;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.AnimatedImageProvider;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.ui_view.ViewProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This example shows how to add a collection of clusterized placemarks to the map.
 */
public class ClusteringActivity extends Activity implements ClusterListener, ClusterTapListener, MapObjectTapListener, UserLocationObjectListener {
    /**
     * Replace "your_api_key" with a valid developer key.
     * You can get it at the https://developer.tech.yandex.ru/ website.
     */
    private final String MAPKIT_API_KEY = "1036d540-0558-4d3f-9917-106cf8db8f7c";
    private final int MY_PERMISSION_REQUEST = 10;

    private MapView mapView;
    private static final float FONT_SIZE = 15;
    private static final float MARGIN_SIZE = 3;
    private static final float STROKE_SIZE = 3;
    private PlacemarkMapObject selectedPlaceMark = null;
    private UserLocationLayer userLocationLayer;

    @Override
    public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
        ImageProvider imageProviderSelected = ImageProvider.fromResource(
                ClusteringActivity.this, R.drawable.bonus);
        ImageProvider imageProvider = ImageProvider.fromResource(
                ClusteringActivity.this, R.drawable.search_result);
        if (selectedPlaceMark != null)
            selectedPlaceMark.setIcon(imageProvider);
        DeviceObject device = (DeviceObject) mapObject.getUserData();
        ((PlacemarkMapObject) mapObject).setIcon(imageProviderSelected);
        selectedPlaceMark = (PlacemarkMapObject) mapObject;
        return false;
    }

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.user_arrow));
        userLocationLayer.setAnchor(
                new PointF((float) (mapView.getWidth() * 0.5), (float) (mapView.getHeight() * 0.5)),
                new PointF((float) (mapView.getWidth() * 0.5), (float) (mapView.getHeight() * 0.83)));
        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE & 0x99ffffff);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }

    public class TextImageProvider extends ImageProvider {
        @Override
        public String getId() {
            return "text_" + text;
        }

        private final String text;

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public Bitmap getImage() {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            manager.getDefaultDisplay().getMetrics(metrics);

            Paint textPaint = new Paint();
            textPaint.setTextSize(FONT_SIZE * metrics.density);
            textPaint.setTextAlign(Align.CENTER);
            textPaint.setStyle(Style.FILL);
            textPaint.setColor(Color.WHITE);
            textPaint.setAntiAlias(true);

            float widthF = textPaint.measureText(text);
            FontMetrics textMetrics = textPaint.getFontMetrics();
            float heightF = Math.abs(textMetrics.bottom) + Math.abs(textMetrics.top);
            float textRadius = (float) Math.sqrt(widthF * widthF + heightF * heightF) / 2;
            float internalRadius = textRadius + MARGIN_SIZE * metrics.density;
            float externalRadius = internalRadius + STROKE_SIZE * metrics.density;

            int width = (int) (2 * externalRadius + 0.5);

            Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);

            Paint backgroundPaint = new Paint();
            backgroundPaint.setAntiAlias(true);
            backgroundPaint.setColor(Color.WHITE);
            canvas.drawCircle(width / 2, width / 2, externalRadius, backgroundPaint);

            backgroundPaint.setColor(getColor(R.color.orange));
            canvas.drawCircle(width / 2, width / 2, internalRadius, backgroundPaint);

            canvas.drawText(
                    text,
                    width / 2,
                    width / 2 - (textMetrics.ascent + textMetrics.descent) / 2,
                    textPaint);

            return bitmap;
        }

        public TextImageProvider(String text) {
            this.text = text;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.clustering);
        super.onCreate(savedInstanceState);

        mapView = (MapView) findViewById(R.id.mapview);

        //todo set zoom to 20 when API is ready
        mapView.getMap().move(new CameraPosition(
                new Point(55.756, 37.618), 10, 0, 0));

        ImageProvider imageProvider = ImageProvider.fromResource(
                ClusteringActivity.this, R.drawable.search_result);

        ClusterizedPlacemarkCollection clusterizedCollection =
                mapView.getMap().getMapObjects().addClusterizedPlacemarkCollection(this);

        clusterizedCollection.addPlacemark(
                new Point(55.756, 37.618),
                imageProvider,
                new IconStyle()
        ).setUserData(new DeviceObject(55.756, 37.618, "Object 1", 1));

        clusterizedCollection.addPlacemark(
                new Point(55.756, 37.718),
                imageProvider,
                new IconStyle()
        ).setUserData(new DeviceObject(55.756, 37.618, "Object 2", 2));

        clusterizedCollection.addPlacemark(
                new Point(55.756, 37.818),
                imageProvider,
                new IconStyle()
        ).setUserData(new DeviceObject(55.756, 37.618, "Object 3", 3));

        clusterizedCollection.addPlacemark(
                new Point(55.756, 37.918),
                imageProvider,
                new IconStyle()
        ).setUserData(new DeviceObject(55.756, 37.618, "Object 4", 4));

        clusterizedCollection.addPlacemark(
                new Point(55.756, 37.998),
                imageProvider,
                new IconStyle()
        ).setUserData(new DeviceObject(55.756, 37.618, "Object 5", 5));

        clusterizedCollection.addTapListener(this);
        mapView.getMap().getMapObjects().addTapListener(this);
        clusterizedCollection.clusterPlacemarks(60, 15);

        initLocation();

    }

    private void initLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST);
        }

        MapKit mapKit = MapKitFactory.getInstance();

        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);

        mapKit.createLocationManager().requestSingleUpdate(new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                Log.e("HERE", String.valueOf(location.getPosition().getLongitude()));
            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
            }
        });
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
    public void onClusterAdded(Cluster cluster) {
        // We setup cluster appearance and tap handler in this method
        cluster.getAppearance().setIcon(
                new TextImageProvider(Integer.toString(cluster.getSize())));
        cluster.addClusterTapListener(this);
    }

    @Override
    public boolean onClusterTap(Cluster cluster) {
        // We return true to notify map that the tap was handled and shouldn't be
        // propagated further.
        return true;
    }

}
