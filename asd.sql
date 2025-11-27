-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS pagosbd;

-- Usar la base de datos
USE pagosbd;

-- Crear la tabla 'empleado'
CREATE TABLE empleado (
    IdEmpleado INT PRIMARY KEY,
    nombreEmpleado VARCHAR(100) NOT NULL,
    fechaInicio DATE NOT NULL,                 
    fechaTermino DATE NOT NULL,                     
    tipoContrato VARCHAR(50) NOT NULL,        
    planSalud BOOLEAN DEFAULT FALSE,      
    afp BOOLEAN DEFAULT FALSE            
);