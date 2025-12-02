package com.matiasaravena.sistemareportes.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.matiasaravena.sistemareportes.R;
import com.matiasaravena.sistemareportes.network.MediaHelper;

public class GrabadoraAudioActivity extends AppCompatActivity {
	
	private MediaHelper mediaHelper;
	private Button btnGrabar, btnDetener;
	private String archivoSalida;
	private boolean estaGrabando = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grabadora);
		
		mediaHelper = new MediaHelper();
		// Definimos dónde se guardará el archivo
		archivoSalida = getExternalCacheDir().getAbsolutePath() + "/audio_" + System.currentTimeMillis() + ".3gp";
		
		btnGrabar = findViewById(R.id.btnGrabar);
		btnDetener = findViewById(R.id.btnDetener);
		
		btnGrabar.setOnClickListener(v -> gestionarGrabacion());
		
		btnDetener.setOnClickListener(v -> finalizarYVolver());
	}
	
	private void gestionarGrabacion() {
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 200);
			return;
		}
		
		mediaHelper.grabarAudio(archivoSalida);
		estaGrabando = true;
		
		btnGrabar.setEnabled(false); // Desactivar grabar
		btnGrabar.setText("GRABANDO...");
		btnDetener.setEnabled(true); // Activar detener
		btnDetener.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
		
		Toast.makeText(this, "🎙️ Grabando...", Toast.LENGTH_SHORT).show();
	}
	
	private void finalizarYVolver() {
		if (estaGrabando) {
			mediaHelper.detenerGrabacion();
			estaGrabando = false;
		}
		
		// --- DEVOLVER DATOS ---
		Intent resultIntent = new Intent();
		resultIntent.putExtra("AUDIO_PATH", archivoSalida); // Mandamos la ruta de vuelta
		setResult(Activity.RESULT_OK, resultIntent);
		
		Toast.makeText(this, "✅ Audio guardado", Toast.LENGTH_SHORT).show();
		finish(); // Cerramos esta pantalla y volvemos a CrearReporte
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaHelper.liberarRecursos();
	}
}
