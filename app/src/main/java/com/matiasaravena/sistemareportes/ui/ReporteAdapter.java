package com.matiasaravena.sistemareportes.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.matiasaravena.sistemareportes.R;
import com.matiasaravena.sistemareportes.modelo.Reporte;
import com.matiasaravena.sistemareportes.network.MediaHelper;

import java.io.File;
import java.util.List;

public class ReporteAdapter extends RecyclerView.Adapter<ReporteAdapter.ViewHolder> {
	
	private List<Reporte> lista;
	private Context context;
	
	public ReporteAdapter(List<Reporte> lista) {
		this.lista = lista;
	}
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		context = parent.getContext();
		View view = LayoutInflater.from(context).inflate(R.layout.item_reporte, parent, false);
		return new ViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Reporte reporte = lista.get(position);
		
		// 1. Poner Textos
		holder.titulo.setText(reporte.getTitulo());
		holder.ubicacion.setText("Lat: " + String.format("%.4f", reporte.getLatitud()));
		
		// 2. Cargar Foto con Glide
		// Verificamos si hay una ruta guardada y si no está vacía
		if (reporte.getUrlFoto() != null && !reporte.getUrlFoto().isEmpty()) {
			Glide.with(context)
					.load(new File(reporte.getUrlFoto())) // Carga desde la ruta del archivo
					.placeholder(android.R.drawable.ic_menu_camera) // Mientras carga
					.error(android.R.drawable.ic_delete) // Si falla la carga
					.centerCrop()
					.into(holder.foto);
		} else {
			// Si no hay foto, ponemos icono por defecto
			holder.foto.setImageResource(android.R.drawable.ic_menu_camera);
		}
		
		// 3. Configurar Botón de Audio
		if (reporte.getUrlAudio() != null && !reporte.getUrlAudio().isEmpty()) {
			holder.btnPlay.setVisibility(View.VISIBLE); // ¡Mostrar botón!
			
			holder.btnPlay.setOnClickListener(v -> {
				try {
					MediaHelper mediaHelper = new MediaHelper();
					mediaHelper.reproducirAudio(reporte.getUrlAudio());
					Toast.makeText(context, "Reproduciendo nota de voz...", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(context, "Error al reproducir audio", Toast.LENGTH_SHORT).show();
				}
			});
		} else {
			holder.btnPlay.setVisibility(View.GONE); // Ocultar si no hay audio
		}
		
		// 4. Click en la tarjeta para ir al Detalle
		holder.itemView.setOnClickListener(v -> {
			Intent intent = new Intent(context, DetalleReporteActivity.class);
			intent.putExtra("ID", reporte.getId());
			intent.putExtra("TITULO", reporte.getTitulo());
			intent.putExtra("DESC", reporte.getDescripcion());
			intent.putExtra("LAT", reporte.getLatitud());
			intent.putExtra("LON", reporte.getLongitud());
			intent.putExtra("FOTO_PATH", reporte.getUrlFoto());
			intent.putExtra("AUDIO_PATH", reporte.getUrlAudio());
			context.startActivity(intent);
		});
	}
	
	@Override
	public int getItemCount() {
		return lista.size();
	}
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		TextView titulo, ubicacion;
		ImageView foto;
		ImageButton btnPlay;
		
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			titulo = itemView.findViewById(R.id.lblTituloItem);
			ubicacion = itemView.findViewById(R.id.lblUbicacionItem);
			foto = itemView.findViewById(R.id.imgFotoItem);
			btnPlay = itemView.findViewById(R.id.btnPlayItem);
		}
	}
}
