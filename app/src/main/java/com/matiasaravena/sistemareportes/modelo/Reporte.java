package com.matiasaravena.sistemareportes.modelo;

import com.google.gson.Gson;

public class Reporte {
	private int id;
	private int usuarioId;
	private String titulo;
	private String descripcion;
	private double latitud;
	private double longitud;
	
	// VARIABLES NUEVAS PARA MULTIMEDIA
	private String urlFoto;
	private String urlAudio;
	private String fecha;
	
	// Constructor principal
	public Reporte(String titulo, String descripcion, double lat, double lon) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.latitud = lat;
		this.longitud = lon;
	}
	
	// --- GETTERS Y SETTERS OBLIGATORIOS ---
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public int getUsuarioId() { return usuarioId; }
	public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
	
	public String getTitulo() { return titulo; }
	public void setTitulo(String titulo) { this.titulo = titulo; }
	
	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
	
	public double getLatitud() { return latitud; }
	public void setLatitud(double latitud) { this.latitud = latitud; }
	
	public double getLongitud() { return longitud; }
	public void setLongitud(double longitud) { this.longitud = longitud; }
	
	public String getUrlFoto() { return urlFoto; }
	public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }
	
	public String getUrlAudio() { return urlAudio; }
	public void setUrlAudio(String urlAudio) { this.urlAudio = urlAudio; }
	
	public String getFecha() { return fecha; }
	public void setFecha(String fecha) { this.fecha = fecha; }
	
	// Convertir a JSON (para la API)
	public String toJson() {
		return new Gson().toJson(this);
	}
}