package com.matiasaravena.sistemareportes.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.matiasaravena.sistemareportes.R;
import com.matiasaravena.sistemareportes.modelo.AdminSQLiteOpenHelper;
import com.matiasaravena.sistemareportes.modelo.Reporte;
import java.util.ArrayList;
import java.util.List;

public class ListaReportesActivity extends AppCompatActivity {
	
	RecyclerView recycler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_reportes);
		
		recycler = findViewById(R.id.recyclerReportes);
		recycler.setLayoutManager(new LinearLayoutManager(this));
		
		cargarDatosSQLite();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		cargarDatosSQLite();
	}
	
	private void cargarDatosSQLite() {
		AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
		SQLiteDatabase bd = admin.getReadableDatabase();
		
		List<Reporte> listaReportes = new ArrayList<>();
		
		// SELECT COMPLETO: ID, Titulo, Desc, Lat, Lon, Foto, Audio
		Cursor fila = bd.rawQuery("SELECT id, titulo, descripcion, latitud, longitud, fotoUrl, audioUrl FROM reportes", null);
		
		if (fila.moveToFirst()) {
			do {
				int id = fila.getInt(0);
				String tit = fila.getString(1);
				String desc = fila.getString(2);
				double lat = fila.getDouble(3);
				double lon = fila.getDouble(4);
				String foto = fila.getString(5); // Columna 5
				String audio = fila.getString(6); // Columna 6
				
				Reporte r = new Reporte(tit, desc, lat, lon);
				r.setId(id);
				r.setUrlFoto(foto);   // Seteamos la ruta para que el Adapter la use
				r.setUrlAudio(audio); // Seteamos el audio para el botón Play
				
				listaReportes.add(r);
			} while (fila.moveToNext());
		}
		
		bd.close();
		fila.close();
		
		ReporteAdapter adapter = new ReporteAdapter(listaReportes);
		recycler.setAdapter(adapter);
	}
}