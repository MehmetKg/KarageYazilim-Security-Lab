package com.example.karageyazilimsecuritylab;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import java.util.function.Consumer;

public class SpyTools {

    // 1. BLE (BLUETOOTH LOW ENERGY) RADAR
    public static void scanBleDevices(Context context, Consumer<String> callback) {
        if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            callback.accept("Hata: Konum izni gerekli (Android BLE kuralı).");
            return;
        }

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null || !adapter.isEnabled()) {
            callback.accept("Hata: Bluetooth kapalı.");
            return;
        }

        callback.accept("--- BLE RADAR BAŞLADI (10 Saniye) ---");

        // Tarama Callback'i
        BluetoothAdapter.LeScanCallback leScanCallback = (device, rssi, scanRecord) -> {
            String name = device.getName() == null ? "Bilinmeyen Cihaz" : device.getName();
            String type = "Bilinmiyor";
            if(name.contains("Watch") || name.contains("Mi")) type = "Giyilebilir";
            if(name.contains("TV") || name.contains("Samsung")) type = "Smart TV";

            // Sinyal gücüne göre mesafe tahmini
            String mes = rssi > -50 ? "Çok Yakın" : (rssi > -70 ? "Yakın" : "Uzak");

            callback.accept("BULUNDU: [" + mes + "] " + name + " (" + device.getAddress() + ") " + rssi + "dBm");
        };

        adapter.startLeScan(leScanCallback);

        // 10 saniye sonra durdur
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            adapter.stopLeScan(leScanCallback);
            callback.accept("--- TARAMA BİTTİ ---");
        }, 10000);
    }

    // 2. EMF DETECTOR (Gizli Kamera/Mikrofon Bulucu)
    public static void startEMFDetector(Context context, Consumer<String> callback) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (magSensor == null) {
            callback.accept("Hata: Manyetik sensör yok.");
            return;
        }

        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                // Manyetik Alan Gücü (Microtesla)
                double tesla = Math.sqrt((x*x) + (y*y) + (z*z));

                // Normal arkaplan radyasyonu genelde 30-60 arasıdır.
                // 100 üzeri elektronik bir cihaz (kamera, dinleyici) işaretidir.
                if (tesla > 100) {
                    callback.accept("[!!!] YÜKSEK MANYETİK ALAN: " + (int)tesla + "µT (METAL/CİHAZ TESPİT EDİLDİ)");
                    sensorManager.unregisterListener(this); // Bulunca dur
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager.registerListener(listener, magSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // 15 saniye sonra otomatik durdur
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            sensorManager.unregisterListener(listener);
            callback.accept("Sensör kapatıldı.");
        }, 15000);
    }

    // 3. SOCIAL ENGINEERING LINK MASKER
    public static String maskLink(String originalUrl, String maskDomain) {
        // Basit URL gizleme (Eğitim amaçlı)
        // @ işareti tarayıcıları kandırmak için eskiden kullanılırdı, şimdi ise
        // kullanıcıyı görsel olarak kandırmak için kullanılır.
        if(!originalUrl.startsWith("http")) originalUrl = "http://" + originalUrl;
        return "MASKELİ LINK:\nhttp://" + maskDomain + "-" + "secure-login@bit.ly/3kXy9Z\n(Not: Bu link kısaltma servisleriyle birleştirilmelidir)";
    }
}