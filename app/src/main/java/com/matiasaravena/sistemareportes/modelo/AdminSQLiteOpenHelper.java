package com.matiasaravena.sistemareportes.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "reportes.db";
	private static final int DATABASE_VERSION = 2; // Subimos versión por si acaso
	
	public AdminSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Tabla Usuarios
		db.execSQL("CREATE TABLE usuarios (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"email TEXT, " +
				"password TEXT, " +
				"nombre TEXT)");
		
		// Tabla Reportes (AHORA CON FOTO Y AUDIO)
		db.execSQL("CREATE TABLE reportes (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"titulo TEXT, " +
				"descripcion TEXT, " +
				"latitud REAL, " +
				"longitud REAL, " +
				"fotoUrl TEXT, " +   // <--- Agregado
				"audioUrl TEXT)");   // <--- Agregado
		
		// Usuario admin por defecto
		db.execSQL("INSERT INTO usuarios (email, password, nombre) VALUES ('admin', '123', 'Administrador')");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS reportes");
		db.execSQL("DROP TABLE IF EXISTS usuarios");
		onCreate(db);
	}
}