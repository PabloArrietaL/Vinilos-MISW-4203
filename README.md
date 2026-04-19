# Vinilos - MISW-4203

Aplicación móvil para la gestión y consulta de catálogos de álbumes musicales, artistas y coleccionistas. Este proyecto forma parte del desarrollo académico de la maestría MISO.

## 👥 Integrantes
* **Pablo Arrieta** - p.arrieta@uniandes.edu.co
* **Tales Losada** - ta.losada@uniandes.edu.co

---

## 🛠 Tecnologías y Herramientas
* **Lenguaje:** Kotlin / Java (Android)
* **Arquitectura:** MVVM (Model-View-ViewModel)
* **Gestión de Versiones:** Git con flujo **Gitflow**
* **Contenedores:** Docker (para el despliegue del Backend local)
* **Documentación:** [Wiki del Proyecto](https://github.com/PabloArrietaL/Vinilos-MISW-4203/wiki)

---

## 🏗 Arquitectura y Patrones
El proyecto se basa en una arquitectura limpia y desacoplada para garantizar la escalabilidad y facilidad de pruebas:

1.  **MVVM (Model-View-ViewModel):** Separación de la lógica de negocio de la interfaz de usuario.
2.  **Patrón Repository:** Actúa como única fuente de verdad para el acceso a datos, coordinando la obtención de información entre fuentes locales o remotas.
3.  **Service Adapter:** Patrón utilizado para encapsular la comunicación con la API REST de Vinilos, aislando las librerías de red (como Retrofit o Volley) del resto de la aplicación.

---

## 🔄 Flujo de Trabajo (Gitflow)
Para el desarrollo del proyecto seguimos el modelo de ramificación **Gitflow**:
* `main`: Rama de producción con código estable.
* `release`: Preparación de versiones para producción.
* `develop`: Rama principal de integración y desarrollo.
* `feature/*`: Ramas específicas para el desarrollo de nuevas Historias de Usuario (ej: `feature/HU01-catalogo`).

---

## ⚙️ Ejecución del Proyecto

### 1. Requisitos Previos
* Android Studio.
* Docker Desktop (para el backend).

### 2. Levantar el Backend (Local)
El backend del proyecto ya está pre-construido y se debe ejecutar mediante Docker para habilitar las APIs:

1.  Clonar el repositorio del backend:
    ```bash
    git clone http://github.com/TheSoftwareDesignLab/BackVynils
    ```
2.  Navegar a la carpeta del proyecto y ejecutar Docker Compose:
    ```bash
    docker-compose up
    ```
    *El backend estará disponible en `http://localhost:3000`*

### 3. Ejecutar la Aplicación Móvil
1.  Clonar este repositorio:
    ```bash
    git clone https://github.com/PabloArrietaL/Vinilos-MISW-4203
    ```
2.  Abrir el proyecto en **Android Studio**.
3.  Sincronizar el proyecto con Gradle.
4.  Ejecutar en un emulador o dispositivo físico.

---

## 📖 Documentación Adicional
Para más detalles sobre la planeación, acuerdos de equipo, wireframes y bitácoras de seguimiento, consulte nuestra **[Wiki oficial](https://github.com/PabloArrietaL/Vinilos-MISW-4203/wiki)**.
