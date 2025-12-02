# Aplicación de Reportes de Campo 🌳 (SistemaReportes)

Este proyecto representa la implementación completa de un sistema móvil para la gestión de incidentes y el seguimiento geolocalizado, desarrollado en **Java para Android**. El código sigue una arquitectura modular diseñada para ser escalable y de fácil mantenimiento (Play Store ready).

## 🚀 Requisitos Cumplidos

* ✅ **10 Activities Funcionales:** Implementación de 10 pantallas distintas (Login, Dashboard, Crear Reporte, Lista, Detalle, etc.).
* ✅ **CRUD Completo y Persistente:** Funcionalidad de Crear, Leer, Actualizar y Eliminar (`C.R.U.D.`) reportes utilizando **SQLite** interna.
* ✅ **Integración de Hardware:** Uso dedicado y seguro de la **Cámara**, **Grabadora de Audio** y **GPS**.
* ✅ **Arquitectura Profesional:** Estructura modular **Modelo-Vista-Controlador/Servicio** (UML).

---

## ⚙️ Estructura y Funcionamiento Técnico

### 1. Persistencia de Datos (SQLite)

* **Base de Datos Offline:** La lógica principal del CRUD se maneja a través de **`AdminSQLiteOpenHelper`** (ver **Diagrama de Clases**). Esto garantiza que la aplicación funcione y guarde datos de forma persistente aunque el dispositivo no tenga conexión a Internet.
* **Sincronización:** Se mantiene la capa **Retrofit/ApiService** para simular un intento de sincronización en segundo plano con un servidor externo, cumpliendo el modelo de aplicación moderna (local-first).

### 2. Integración de Hardware

* **GPS:** La `CrearReporteActivity` utiliza el **`GpsHelper`** para verificar permisos y obtener las coordenadas de ubicación.
* **Multimedia (Cámara y Audio):** El sistema utiliza Intents para la captura de fotos y el **`MediaHelper`** para iniciar la grabación de audio. Las rutas de los archivos generados se almacenan directamente en la base de datos (`urlFoto`, `urlAudio`).

---

## 📐 Diagramas de Diseño (Documentación)

**Nota:** Los archivos de imagen deben estar en la **raíz del repositorio** (junto a este README).

### Diagrama de Clases (UML)
![Diagrama UML del Sistema](Diagrama-de-clases-fondo.png)

### Diagrama de Secuencia
![Diagrama de Secuencia de las pantallas](Diagrama%20de%20Secuencia%20Activities.png)

### Diagrama de Flujo (Creación de Reporte)
![Diagrama de Flujo del Proceso CREATE](Diagrama-flujo.png)

---

**Autor:** Matías Aravena
**Asignatura:** Programación Android
