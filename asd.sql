-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS pagosbd;

-- Usar la base de datos
USE pagosbd;

-- Crear la tabla 'empleado'
CREATE TABLE empleado (
    IdEmpleado INT PRIMARY KEY,
    nombreEmpleado VARCHAR(100),
    fechaInicio DATE,                 
    fechaTermino DATE,                     
    tipoContrato VARCHAR(50),        
    planSalud BOOLEAN,      
    afp BOOLEAN            
);