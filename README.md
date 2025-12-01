-----

# Aplicación de Reportes de Campo 🌳 (SistemaReportes)

Este proyecto representa la implementación completa de un sistema móvil para la gestión de incidentes y el seguimiento geolocalizado, desarrollado en **Java para Android**. El código sigue una arquitectura modular diseñada para ser escalable y de fácil mantenimiento (Play Store ready).

## 🚀 Requisitos Cumplidos

El proyecto cumple al 100% con los requisitos de la aplicación, destacando los siguientes puntos:

  * ✅ **10 Activities Funcionales:** Implementación de 10 pantallas distintas, desde la autenticación hasta la captura de hardware y la visualización de listas.
  * ✅ **CRUD Completo y Persistente:** Funcionalidad de Crear, Leer, Actualizar y Eliminar reportes de manera local.
  * ✅ **Persistencia de Datos:** Uso de **SQLite** como base de datos interna, asegurando que los datos se guarden en el dispositivo (funcionalidad *offline-first*).
  * ✅ **Integración de Hardware:** Uso dedicado y seguro de la **Cámara**, **Grabadora de Audio** y **GPS** (Geolocalización).
  * ✅ **Arquitectura Profesional:** Estructura modular **Modelo-Vista-Controlador/Servicio** (UML).

-----

## 🏗️ Arquitectura y Estructura del Proyecto

El código está organizado en 3 paquetes principales, tal como se definió en el Diagrama de Clases UML:

| Paquete | Contenido | Propósito |
| :--- | :--- | :--- |
| **`modelo`** | `Reporte.java`, `Usuario.java`, `AdminSQLiteOpenHelper.java` | Estructura de datos (POJOs) y persistencia local. |
| **`network`** | `ApiService`, `RetrofitClient`, `GpsHelper`, `MediaHelper` | Capa que maneja la comunicación con el exterior (API) y el hardware. |
| **`ui`** | 10 Activities, `ReporteAdapter` | La capa de presentación y la lógica de interacción del usuario. |

### **Diagramas de Diseño**

**(Asegúrate de exportar tus diagramas de Draw.io como PNG y guárdalos en una carpeta llamada `/docs` en tu repositorio para que estos enlaces funcionen.)**

```markdown
### Diagrama de Clases (UML)
![Diagrama UML del Sistema](docs/diagrama_clases_final.png) 

### Diagrama de Flujo (Creación de Reporte)
![Diagrama de Flujo del Proceso CREATE](docs/flujo_creacion.png)
```

-----

## 💡 Flujo de Trabajo (CRUD Local)

El flujo de datos se optimizó para garantizar la persistencia sin depender de un servidor externo:

| Función | Actividad | Lógica de Datos |
| :--- | :--- | :--- |
| **C - Crear** | `CrearReporteActivity` | Captura datos de GPS/Cámara y realiza un `INSERT` en la tabla `reportes` (SQLite). |
| **R - Leer** | `ListaReportesActivity` | Ejecuta un `SELECT *` en la tabla `reportes` y usa el `ReporteAdapter` para mostrar la información eficientemente. |
| **U - Actualizar** | `DetalleReporteActivity` | Permite editar el título/descripción y ejecuta un `UPDATE` en SQLite usando el ID. |
| **D - Eliminar** | `DetalleReporteActivity` | Ejecuta un `DELETE` en SQLite con confirmación (`AlertDialog`). |

## 🛠️ Tecnologías y Librerías

  * **Lenguaje:** Java
  * **Diseño:** Material Design (AppCompat)
  * **Red:** Retrofit & Gson (para simulación de sincronización)
  * **Carga de Imágenes:** Glide
  * **Persistencia:** SQLite (Implementación `SQLiteOpenHelper`)

## 🚀 Instrucciones de Uso

1.  Clonar el repositorio: `git clone https://docs.github.com/es/repositories/creating-and-managing-repositories/quickstart-for-repositories`
2.  Abrir el proyecto en Android Studio.
3.  **Credenciales de Prueba:** El sistema crea automáticamente un usuario de prueba: `admin` / `123`.
4.  Al crear el primer reporte, la aplicación solicitará permisos de **Cámara** y **Ubicación**.

-----

**¡El proyecto ha sido subido correctamente a GitHub\!**
