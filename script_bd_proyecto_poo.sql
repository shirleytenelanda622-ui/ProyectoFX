-- PROYECTO FINAL POO
-- Sistema de Gestión para Veterinaria


-- TABLA USUARIOS

CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('ADMIN','CAJERO','REPORTES'))
);

-- TABLA PROPIETARIOS

CREATE TABLE propietarios (
    id_propietario SERIAL PRIMARY KEY,
    cedula VARCHAR(10) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(150),
    correo VARCHAR(100)
);

-- TABLA MASCOTAS

CREATE TABLE mascotas (
    id_mascota SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raza VARCHAR(50),
    edad INT,
    sexo VARCHAR(10),
    peso DECIMAL(5,2),

    id_propietario INT NOT NULL,

    CONSTRAINT fk_propietario
        FOREIGN KEY (id_propietario)
        REFERENCES propietarios(id_propietario)
);

-- TABLA CONSULTAS

CREATE TABLE consultas (
    id_consulta SERIAL PRIMARY KEY,

    id_mascota INT NOT NULL,
    id_usuario INT NOT NULL,

    fecha DATE NOT NULL,
    diagnostico TEXT,
    tratamiento TEXT,
    costo DECIMAL(10,2),

    CONSTRAINT fk_mascota
        FOREIGN KEY (id_mascota)
        REFERENCES mascotas(id_mascota),

    CONSTRAINT fk_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
);

-- USUARIOS DE PRUEBA

INSERT INTO usuarios (nombre, correo, contrasena, rol)
VALUES
('Administrador','admin@veterinaria.com','123456','ADMIN'),
('Cajero','cajero@veterinaria.com','123456','CAJERO'),
('Reportes','reportes@veterinaria.com','123456','REPORTES');

-- PROPIETARIO DE PRUEBA

INSERT INTO propietarios (cedula,nombre,telefono,direccion,correo)
VALUES
('0102030405','Juan Pérez','0999999999','Quito','juan@gmail.com');

-- MASCOTA DE PRUEBA

INSERT INTO mascotas
(nombre,especie,raza,edad,sexo,peso,id_propietario)
VALUES
('Max','Perro','Labrador',5,'Macho',24.50,1);

-- CONSULTA DE PRUEBA

INSERT INTO consultas
(id_mascota,id_usuario,fecha,diagnostico,tratamiento,costo)
VALUES
(1,1,CURRENT_DATE,'Control general','Vitaminas',25.00);

