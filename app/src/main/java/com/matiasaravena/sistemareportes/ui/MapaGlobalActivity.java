package com.matiasaravena.sistemareportes.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.matiasaravena.sistemareportes.R;
import com.matiasaravena.sistemareportes.modelo.Reporte;
import com.matiasaravena.sistemareportes.network.ApiService;
import com.matiasaravena.sistemareportes.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapaGlobalActivity extends AppCompatActivity implements OnMapReadyCallback {
	
	private GoogleMap mMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapa_global);
		
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		if (mapFragment != null) {
			mapFragment.getMapAsync(this);
		}
	}
	
	@Override
	public void onMapReady(@NonNull GoogleMap googleMap) {
		mMap = googleMap;
		cargarMarcadores();
	}
	
	private void cargarMarcadores() {
		ApiService api = RetrofitClient.getApiService();
		api.getReportes().enqueue(new Callback<List<Reporte>>() {
			@Override
			public void onResponse(Call<List<Reporte>> call, Response<List<Reporte>> response) {
				if (response.isSuccessful() && response.body() != null) {
					for (Reporte r : response.body()) {
						// Solo ponemos marcadores si hay coordenadas válidas
						if (r.getLatitud() != 0) {
							LatLng pos = new LatLng(r.getLatitud(), r.getLongitud());
							mMap.addMarker(new MarkerOptions().position(pos).title(r.getTitulo()));
							mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 10));
						}
					}
				}
			}
			
			@Override
			public void onFailure(Call<List<Reporte>> call, Throwable t) {
				// Si falla la red, ponemos un marcador de prueba en Santiago
				LatLng santiago = new LatLng(-33.4489, -70.6693);
				mMap.addMarker(new MarkerOptions().position(santiago).title("Marcador de Prueba"));
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santiago, 10));
			}
		});
	}
}