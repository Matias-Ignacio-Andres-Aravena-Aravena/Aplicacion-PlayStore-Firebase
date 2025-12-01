package com.matiasaravena.sistemareportes.network;

import com.matiasaravena.sistemareportes.modelo.Reporte;
import com.matiasaravena.sistemareportes.modelo.Usuario;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
	
	// 1. Obtener lista de reportes (READ)
	@GET("reportes")
	Call<List<Reporte>> getReportes();
	
	// 2. Enviar un nuevo reporte (CREATE)
	@POST("reportes")
	Call<Reporte> postReporte(@Body Reporte reporte);
	
	// 3. Login de usuario (AUTH)
	@POST("login")
	Call<Usuario> login(@Body Usuario usuario);
}