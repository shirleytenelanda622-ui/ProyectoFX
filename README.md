# Sistema de Gestión para Veterinaria — ProyectoFX

Aplicación de escritorio desarrollada en **JavaFX** aplicando los principios de la **Programación Orientada a Objetos (POO)**, con persistencia en **PostgreSQL**. Permite administrar propietarios, mascotas, consultas y usuarios, con acceso diferenciado según el rol del usuario.
Proyecto Final — **Programación Orientada a Objetos**
Período académico **2026-A**

---

## Descripción del proyecto

El sistema resuelve la gestión administrativa y clínica de una veterinaria: registro de propietarios y sus mascotas, historial de consultas (diagnóstico, tratamiento y costo) y control de acceso mediante usuarios con distintos roles. La aplicación implementa los cuatro pilares de la POO (encapsulamiento, herencia, polimorfismo y abstracción) mediante una arquitectura organizada en paquetes `model`, `dao`, `controller`, `view`, `db` y `app`, siguiendo el patrón DAO para el acceso a datos y FXML/Scene Builder para las interfaces gráficas.

### Roles del sistema

| Rol | Permisos |
|---|---|
| **ADMIN** | Acceso completo: gestión de usuarios, propietarios, mascotas, consultas y reportes. |
| **CAJERO** | Registro y gestión de propietarios, mascotas y consultas. |
| **REPORTES** | Acceso de solo lectura a los módulos de reportes y consultas. |

---

## Integrantes

| Estudiante |
|---|
| _Alisson Quiguango_ |
| _Shirley Tenelanda_ | 

**Docente:** Ing. Yadira Gissela Franco Rocha, Mgs.

---

## Herramientas y tecnologías usadas

- **Lenguaje:** Java 21
- **Framework UI:** JavaFX 21 (FXML + CSS)
- **Base de datos:** PostgreSQL
- **Gestor de dependencias / build:** Apache Maven
- **Driver JDBC:** PostgreSQL JDBC 42.7.11
- **Testing:** JUnit 5 (Jupiter)
- **Control de versiones:** Git y GitHub
- **IDE recomendado:** IntelliJ IDEA
- **Generación de ejecutable:** Maven / Launch4j (según corresponda)

---

##  Estructura del proyecto

```
ProyectoFX/
├── src/main/java/org/example/proyectofx/
│   ├── app/            # Punto de entrada (Main, Launcher)
│   ├── model/           # Entidades: Persona, Usuario, Propietario, Mascota, Consulta con atributos privados y metodos get/set
│   ├── dao/             # Acceso a datos: CRUD, UsuarioDAO, PropietarioDAO, MascotaDAO, ConsultaDAO
│   ├── controller/       # Controladores FXML: Login, Dashboard, Usuarios, Propietarios, Mascotas, Reportes
│   └── db/              # Conexion.java (conexión JDBC a PostgreSQL)
├── src/main/resources/org/example/proyectofx/
│   ├── view/             # Vistas FXML (login, dashboard, usuarios, propietarios, mascotas, reportes)
│   ├── estilos/          # Hojas de estilo CSS
│   └── Imagenes/         # Recursos gráficos e íconos
├── sql/
│   └── veterinaria.sql   # Script de creación de base de datos, tablas y datos de prueba
├── pom.xml               # Configuración de Maven
└── README.md
```

---

##  Requisitos previos

- **JDK 21** o superior instalado y configurado.
- **PostgreSQL** instalado y en ejecución (puerto por defecto `5432`).
- **Apache Maven** (o usar el wrapper `mvnw` incluido).
- IDE compatible con Maven y JavaFX (recomendado: IntelliJ IDEA).

---

##  Pasos de instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone <URL-del-repositorio>
cd ProyectoFX
```

### 2. Crear la base de datos

Abrir una terminal con `psql` o un cliente como pgAdmin y crear la base de datos:

```sql
CREATE DATABASE veterinaria;
```

Luego ejecutar el script incluido en el proyecto para crear las tablas y los datos de prueba:

```bash
psql -U postgres -d veterinaria -f sql/veterinaria.sql
```

### 3. Configurar la conexión

Verificar/editar las credenciales de conexión en:

`src/main/java/org/example/proyectofx/db/Conexion.java`

```java
private static final String URL = "jdbc:postgresql://localhost:5432/veterinaria";
private static final String USUARIO = "postgres";
private static final String PASSWORD = "123";
```

Ajustar `USUARIO` y `PASSWORD` según la configuración local de PostgreSQL.

### 4. Compilar y ejecutar con Maven

```bash
./mvnw clean javafx:run
```

En Windows:

```bash
mvnw.cmd clean javafx:run
```

### 5. Iniciar sesión

Usar alguno de los usuarios de prueba creados por el script SQL:

| Correo | Contraseña | Rol |
|---|---|---|
| admin@veterinaria.com | 123456 | ADMIN |
| cajero@veterinaria.com | 123456 | CAJERO |
| reportes@veterinaria.com | 123456 | REPORTES |

---

##  Capturas de pantalla
Login

<img width="497" height="708" alt="image" src="https://github.com/user-attachments/assets/171c16d4-652a-4131-b441-43b46204f46d" />
Administrador - Gestion de Mascotas
<img width="1249" height="842" alt="image" src="https://github.com/user-attachments/assets/51d902a8-c424-43af-9fd3-27b3423beedc" />
Gestion de Propietarios
<img width="1244" height="842" alt="image" src="https://github.com/user-attachments/assets/b398b682-7761-49e9-af8c-76405868676f" />
Gestion de Usuarios
<img width="1245" height="843" alt="image" src="https://github.com/user-attachments/assets/c84bba0c-c09f-476f-93b3-6cc0a7bb8f3e" />
Reportes
<img width="1245" height="848" alt="image" src="https://github.com/user-attachments/assets/e550504a-576e-47d6-920f-e0bb9df2a086" />
Cajero - Gestion de Mascotas
<img width="1245" height="841" alt="image" src="https://github.com/user-attachments/assets/8f8935ed-84cd-49be-bec3-2b8469c06f70" />
Gestion de Propietarios
<img width="1243" height="847" alt="image" src="https://github.com/user-attachments/assets/d343e67f-c891-4bc1-b765-042678fd7e21" />
Reportes
<img width="1242" height="845" alt="image" src="https://github.com/user-attachments/assets/fa1f21f7-2b1d-42bb-96e2-40e3234f6cbd" />

## Funcionalidades principales

- Inicio de sesión con control de acceso por rol (ADMIN, CAJERO, REPORTES).
- CRUD completo de propietarios y mascotas.
- Registro de consultas veterinarias (diagnóstico, tratamiento y costo).
- Módulo de reportes.
- Validaciones de datos (campos vacíos, duplicados, formatos numéricos).
- Persistencia en PostgreSQL mediante el patrón DAO.

---

##  Licencia

Proyecto desarrollado con fines académicos para la asignatura de Programación Orientada a Objetos.
