package com.matiasaravena.sistemareportes.ui;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.matiasaravena.sistemareportes.R;
import com.matiasaravena.sistemareportes.modelo.AdminSQLiteOpenHelper;
import com.matiasaravena.sistemareportes.network.MediaHelper;

public class DetalleReporteActivity extends AppCompatActivity {
	
	private int reporteId;
	private EditText txtTitulo, txtDesc; // Ahora son EditText para poder editar
	private TextView lblGPS;
	private Button btnAudio;
	private MediaHelper mediaHelper;
	private String audioPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_reporte);
		
		// 1. Vincular Vistas (OJO CON LOS IDs AQUÍ)
		txtTitulo = findViewById(R.id.txtDetalleTitulo); // Corregido: txt en vez de lbl
		txtDesc = findViewById(R.id.txtDetalleDesc);     // Corregido: txt en vez de lbl
		lblGPS = findViewById(R.id.lblDetalleGPS);
		btnAudio = findViewById(R.id.btnEscucharAudio);
		
		Button btnActualizar = findViewById(R.id.btnActualizar);
		Button btnEliminar = findViewById(R.id.btnEliminar);
		
		mediaHelper = new MediaHelper();
		
		// 2. RECIBIR DATOS DEL ADAPTER
		if (getIntent() != null) {
			reporteId = getIntent().getIntExtra("ID", -1);
			txtTitulo.setText(getIntent().getStringExtra("TITULO"));
			txtDesc.setText(getIntent().getStringExtra("DESC"));
			
			double lat = getIntent().getDoubleExtra("LAT", 0);
			double lon = getIntent().getDoubleExtra("LON", 0);
			lblGPS.setText("Ubicación: " + lat + ", " + lon);
			
			// Recibimos la ruta del audio
			audioPath = getIntent().getStringExtra("AUDIO_PATH");
		}
		
		// 3. CONFIGURAR BOTÓN AUDIO
		if (audioPath != null && !audioPath.isEmpty()) {
			btnAudio.setEnabled(true);
			btnAudio.setText("🔊 REPRODUCIR AUDIO");
			btnAudio.setOnClickListener(v -> {
				try {
					mediaHelper.reproducirAudio(audioPath);
					Toast.makeText(this, "Reproduciendo...", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(this, "Error al reproducir", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			btnAudio.setText("SIN AUDIO");
			btnAudio.setEnabled(false);
		}
		
		// 4. BOTONES CRUD
		btnActualizar.setOnClickListener(v -> actualizarReporte());
		btnEliminar.setOnClickListener(v -> confirmarEliminacion());
	}
	
	private void actualizarReporte() {
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
		SQLiteDatabase bd = admin.getWritableDatabase();
		
		String nuevoTitulo = txtTitulo.getText().toString();
		String nuevaDesc = txtDesc.getText().toString();
		
		if (nuevoTitulo.isEmpty()) {
			Toast.makeText(this, "El título no puede estar vacío", Toast.LENGTH_SHORT).show();
			return;
		}
		
		ContentValues registro = new ContentValues();
		registro.put("titulo", nuevoTitulo);
		registro.put("descripcion", nuevaDesc);
		
		// UPDATE
		int cant = bd.update("reportes", registro, "id=" + reporteId, null);
		bd.close();
		
		if (cant == 1) {
			Toast.makeText(this, "✅ Reporte Actualizado", Toast.LENGTH_SHORT).show();
			finish();
		} else {
			Toast.makeText(this, "❌ Error al actualizar", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void confirmarEliminacion() {
		new AlertDialog.Builder(this)
				.setTitle("¿Eliminar?")
				.setMessage("Se borrará permanentemente.")
				.setPositiveButton("Sí", (dialog, which) -> {
					AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
					SQLiteDatabase bd = admin.getWritableDatabase();
					
					// DELETE
					int cant = bd.delete("reportes", "id=" + reporteId, null);
					bd.close();
					
					if (cant == 1) {
						Toast.makeText(this, "🗑️ Eliminado", Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
					}
				})
				.setNegativeButton("No", null)
				.show();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaHelper.liberarRecursos();
	}
}