package com.matiasaravena.sistemareportes.network;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import androidx.core.app.ActivityCompat;

public class GpsHelper {
	
	private Context context;
	private LocationManager locationManager;
	
	public GpsHelper(Context context) {
		this.context = context;
		this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}
	
	public Location obtenerUbicacion() {
		// Verificamos permisos antes de pedir la ubicación
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED) {
			return null; // No hay permisos, devolvemos nulo
		}
		
		// Intentamos obtener la última ubicación conocida por GPS
		Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		// Si el GPS falla, intentamos por Red (Wifi/Datos)
		if (loc == null) {
			loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		return loc;
	}
}