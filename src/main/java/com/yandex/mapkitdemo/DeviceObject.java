package com.yandex.mapkitdemo;

import android.graphics.PointF;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.Callback;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectDragListener;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.ModelStyle;
import com.yandex.mapkit.map.PlacemarkAnimation;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.model.ModelProvider;
import com.yandex.runtime.ui_view.ViewProvider;

import java.io.Serializable;
import java.util.List;

public class DeviceObject extends Point implements PlacemarkMapObject {
    private String title;
    private int id;

    public DeviceObject(double latitude, double longitude, String title, int id) {
        super(latitude, longitude);
        this.title = title;
        this.id = id;
    }

    public DeviceObject() {
    }

    @Override
    public String toString() {
        return "DeviceObject{" +
                "title='" + title + '\'' +
                ", id=" + id +
                '}';
    }

    @NonNull
    @Override
    public Point getGeometry() {
        return null;
    }

    @Override
    public void setGeometry(@NonNull Point point) {

    }

    @Override
    public float getDirection() {
        return 0;
    }

    @Override
    public void setDirection(float v) {

    }

    @Override
    public float getOpacity() {
        return 0;
    }

    @Override
    public void setOpacity(float v) {

    }

    @Override
    public void setIcon(@NonNull ImageProvider imageProvider) {

    }

    @Override
    public void setIcon(@NonNull ImageProvider imageProvider, @NonNull IconStyle iconStyle) {

    }

    @Override
    public void setIcon(@NonNull ImageProvider imageProvider, @NonNull Callback callback) {

    }

    @Override
    public void setIcon(@NonNull ImageProvider imageProvider, @NonNull IconStyle iconStyle, @NonNull Callback callback) {
    }

    @Override
    public void setIconStyle(@NonNull IconStyle iconStyle) {

    }

    @NonNull
    @Override
    public CompositeIcon useCompositeIcon() {
        return null;
    }

    @NonNull
    @Override
    public PlacemarkAnimation useAnimation() {
        return null;
    }

    @Override
    public void setModelStyle(@NonNull ModelStyle modelStyle) {

    }

    @Override
    public void setModel(@NonNull ModelProvider modelProvider, @NonNull ModelStyle modelStyle) {

    }

    @Override
    public void setModel(@NonNull ModelProvider modelProvider, @NonNull ModelStyle modelStyle, @NonNull Callback callback) {

    }

    @Override
    public void setView(@NonNull ViewProvider viewProvider) {

    }

    @Override
    public void setView(@NonNull ViewProvider viewProvider, @NonNull IconStyle iconStyle) {

    }

    @Override
    public void setView(@NonNull ViewProvider viewProvider, @NonNull Callback callback) {

    }

    @Override
    public void setView(@NonNull ViewProvider viewProvider, @NonNull IconStyle iconStyle, @NonNull Callback callback) {

    }

    @Override
    public void setScaleFunction(@NonNull List<PointF> list) {

    }

    @NonNull
    @Override
    public MapObjectCollection getParent() {
        return null;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public void setVisible(boolean b) {

    }

    @Override
    public void setVisible(boolean b, @NonNull Animation animation, @Nullable Callback callback) {

    }

    @Override
    public float getZIndex() {
        return 0;
    }

    @Override
    public void setZIndex(float v) {

    }

    @Override
    public boolean isDraggable() {
        return false;
    }

    @Override
    public void setDraggable(boolean b) {

    }

    @Nullable
    @Override
    public Object getUserData() {
        return null;
    }

    @Override
    public void setUserData(@Nullable Object o) {

    }

    @Override
    public void addTapListener(@NonNull MapObjectTapListener mapObjectTapListener) {

    }

    @Override
    public void removeTapListener(@NonNull MapObjectTapListener mapObjectTapListener) {

    }

    @Override
    public void setDragListener(@Nullable MapObjectDragListener mapObjectDragListener) {

    }

    @Override
    public boolean isValid() {
        return false;
    }
}