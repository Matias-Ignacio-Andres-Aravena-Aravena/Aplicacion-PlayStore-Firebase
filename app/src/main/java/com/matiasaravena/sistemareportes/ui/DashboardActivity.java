package com.matiasaravena.sistemareportes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.matiasaravena.sistemareportes.R;

public class DashboardActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		Button btnCrear = findViewById(R.id.btnCrearReporte);
		Button btnLista = findViewById(R.id.btnVerLista);
		Button btnPerfil = findViewById(R.id.btnPerfil);
		
		// Navegación según diagrama
		btnCrear.setOnClickListener(v -> startActivity(new Intent(this, CrearReporteActivity.class)));
		btnLista.setOnClickListener(v -> startActivity(new Intent(this, ListaReportesActivity.class)));
		btnPerfil.setOnClickListener(v -> startActivity(new Intent(this, PerfilUsuarioActivity.class)));
	}
}