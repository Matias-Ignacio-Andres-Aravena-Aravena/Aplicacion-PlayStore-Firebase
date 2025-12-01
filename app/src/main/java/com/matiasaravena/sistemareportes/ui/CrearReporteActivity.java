package com.matiasaravena.sistemareportes.ui;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.matiasaravena.sistemareportes.R;
import com.matiasaravena.sistemareportes.modelo.AdminSQLiteOpenHelper;
import com.matiasaravena.sistemareportes.modelo.Reporte;
import com.matiasaravena.sistemareportes.network.ApiService;
import com.matiasaravena.sistemareportes.network.GpsHelper;
import com.matiasaravena.sistemareportes.network.RetrofitClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearReporteActivity extends AppCompatActivity {
	
	private EditText txtTitulo, txtDesc;
	private ImageView imgPreview;
	private TextView lblUbicacion;
	private Button btnFoto, btnAudio, btnGps, btnGuardar;
	
	private GpsHelper gpsHelper;
	// Quitamos MediaHelper de aquí porque ahora lo usa la otra actividad
	
	private double latitud = 0.0, longitud = 0.0;
	private String rutaFoto = "";
	private String rutaAudio = "";
	
	// Códigos para saber quién responde
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int REQUEST_AUDIO_CAPTURE = 2; // Nuevo código para audio
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crear_reporte);
		
		txtTitulo = findViewById(R.id.txtTituloReporte);
		txtDesc = findViewById(R.id.txtDescReporte);
		imgPreview = findViewById(R.id.imgPreview);
		lblUbicacion = findViewById(R.id.lblUbicacion);
		
		btnFoto = findViewById(R.id.btnTomarFoto);
		btnAudio = findViewById(R.id.btnGrabarAudio);
		btnGps = findViewById(R.id.btnActualizarGps);
		btnGuardar = findViewById(R.id.btnGuardarReporte);
		
		gpsHelper = new GpsHelper(this);
		
		// BOTÓN FOTO
		btnFoto.setOnClickListener(v -> {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
			} else {
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			}
		});
		
		// BOTÓN AUDIO (Ahora abre la otra pantalla)
		btnAudio.setOnClickListener(v -> {
			Intent intent = new Intent(CrearReporteActivity.this, GrabadoraAudioActivity.class);
			startActivityForResult(intent, REQUEST_AUDIO_CAPTURE);
		});
		
		// BOTÓN GPS
		btnGps.setOnClickListener(v -> obtenerCoordenadas());
		obtenerCoordenadas();
		
		// BOTÓN GUARDAR
		btnGuardar.setOnClickListener(v -> enviarReporte());
	}
	
	// Recibimos los resultados (Foto o Audio)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		// 1. SI VUELVE DE LA CÁMARA
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			imgPreview.setImageBitmap(imageBitmap);
			rutaFoto = guardarImagenLocal(imageBitmap);
		}
		
		// 2. SI VUELVE DE LA GRABADORA (NUEVO)
		else if (requestCode == REQUEST_AUDIO_CAPTURE && resultCode == Activity.RESULT_OK) {
			if (data != null) {
				rutaAudio = data.getStringExtra("AUDIO_PATH"); // Recibimos la ruta
				// Cambiamos el botón a verde para confirmar
				btnAudio.setText("AUDIO OK");
				btnAudio.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
				Toast.makeText(this, "Audio adjuntado correctamente", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private void obtenerCoordenadas() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
			return;
		}
		Location loc = gpsHelper.obtenerUbicacion();
		if (loc != null) {
			latitud = loc.getLatitude();
			longitud = loc.getLongitude();
			lblUbicacion.setText("Lat: " + latitud + " Lon: " + longitud);
		} else {
			lblUbicacion.setText("Buscando GPS...");
		}
	}
	
	private String guardarImagenLocal(Bitmap bitmap) {
		try {
			File archivo = new File(getFilesDir(), "foto_" + System.currentTimeMillis() + ".jpg");
			FileOutputStream out = new FileOutputStream(archivo);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			return archivo.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private void enviarReporte() {
		String titulo = txtTitulo.getText().toString();
		String desc = txtDesc.getText().toString();
		
		if (titulo.isEmpty()) {
			Toast.makeText(this, "Falta el título", Toast.LENGTH_SHORT).show();
			return;
		}
		
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
		SQLiteDatabase bd = admin.getWritableDatabase();
		
		ContentValues registro = new ContentValues();
		registro.put("titulo", titulo);
		registro.put("descripcion", desc);
		registro.put("latitud", latitud);
		registro.put("longitud", longitud);
		registro.put("fotoUrl", rutaFoto);
		registro.put("audioUrl", rutaAudio);
		
		long result = bd.insert("reportes", null, registro);
		bd.close();
		
		if (result == -1) {
			Toast.makeText(this, "Error al guardar en SQLite", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Toast.makeText(this, "Guardado localmente", Toast.LENGTH_SHORT).show();
		
		Reporte reporte = new Reporte(titulo, desc, latitud, longitud);
		ApiService api = RetrofitClient.getApiService();
		api.postReporte(reporte).enqueue(new Callback<Reporte>() {
			@Override
			public void onResponse(Call<Reporte> call, Response<Reporte> response) {
				if (response.isSuccessful()) {
					Toast.makeText(CrearReporteActivity.this, "Subido al servidor", Toast.LENGTH_SHORT).show();
				}
				finish();
			}
			
			@Override
			public void onFailure(Call<Reporte> call, Throwable t) {
				Toast.makeText(CrearReporteActivity.this, "Guardado offline", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}
}