
CREATE DATABASE vuelos;

USE vuelos;

CREATE TABLE ubicaciones(
	pais VARCHAR(20) NOT NULL,
	estado VARCHAR(20) NOT NULL,
    ciudad VARCHAR(20) NOT NULL,
    huso TINYINT NOT NULL,
    
    CONSTRAINT CHK_ubicaciones
    CHECK(huso >=-12 AND huso<=12),
    
    CONSTRAINT PK_ubicaciones 
    PRIMARY KEY(pais,estado,ciudad)
    
) ENGINE = InnoDB;

CREATE TABLE aeropuertos(
	codigo VARCHAR(50) NOT NULL,
    nombre VARCHAR(40) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    direccion VARCHAR(30) NOT NULL,
    pais VARCHAR(20) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    ciudad VARCHAR(20) NOT NULL,
    
    CONSTRAINT PK_aeropuertos
    PRIMARY KEY(codigo),
    
    CONSTRAINT FK_aeropuertos
    FOREIGN KEY(pais,estado,ciudad) REFERENCES ubicaciones(pais,estado,ciudad)
    
) ENGINE = InnoDB;

CREATE TABLE modelos_avion(
    modelo VARCHAR(20) NOT NULL,
    fabricante VARCHAR(20) NOT NULL,
    cabinas SMALLINT UNSIGNED NOT NULL,
    cant_asientos SMALLINT UNSIGNED NOT NULL,

    CONSTRAINT PK_modelos_avion
	PRIMARY KEY(modelo)

) ENGINE = InnoDB;

CREATE TABLE vuelos_programados(
	numero VARCHAR(10) NOT NULL,
	aeropuerto_salida VARCHAR(50) NOT NULL,
	aeropuerto_llegada VARCHAR(50) NOT NULL,
  
	CONSTRAINT PK_vuelos_programados
	PRIMARY KEY(numero),

	CONSTRAINT FK_vuelos_programados_aSalida
	FOREIGN KEY(aeropuerto_salida) REFERENCES aeropuertos(codigo),

	CONSTRAINT FK_vuelos_programados_aLlegada
	FOREIGN KEY(aeropuerto_llegada) REFERENCES aeropuertos(codigo)

) ENGINE = InnoDB;


CREATE TABLE salidas(
    vuelo VARCHAR(10) NOT NULL,
    dia ENUM('do','lu','ma','mi','ju','vi','sa') NOT NULL,
    hora_sale TIME NOT NULL,
    hora_llega TIME NOT NULL,
    modelo_avion VARCHAR(20) NOT NULL,

    CONSTRAINT PK_salidas
	PRIMARY KEY(vuelo,dia),
     
    CONSTRAINT FK_salidas_vuelo_programado
	FOREIGN KEY(vuelo) REFERENCES vuelos_programados(numero),

	CONSTRAINT FK_salidas_modelo_avion
	FOREIGN KEY(modelo_avion) REFERENCES modelos_avion(modelo)

) ENGINE = InnoDB;


CREATE TABLE instancias_vuelo(
	vuelo VARCHAR(10) NOT NULL,
	fecha DATE NOT NULL,
	dia ENUM('do','lu','ma','mi','ju','vi','sa') NOT NULL,
	estado VARCHAR(15) NULL,

	CONSTRAINT PK_instancias_vuelos 
	PRIMARY KEY(vuelo,fecha),

	CONSTRAINT FK_instancias_vuelos 
	FOREIGN KEY(vuelo,dia) REFERENCES salidas(vuelo,dia)

) ENGINE = InnoDB;

CREATE TABLE clases(
	nombre VARCHAR(20) NOT NULL,
	porcentaje DECIMAL(2,2) UNSIGNED NOT NULL,

	CONSTRAINT PK_clases
	PRIMARY KEY(nombre)

) ENGINE = InnoDB;

CREATE TABLE comodidades(
	codigo SMALLINT UNSIGNED NOT NULL,
	descripcion TINYTEXT NOT NULL,

	CONSTRAINT PK_comodidades 
	PRIMARY KEY(codigo)

) ENGINE = InnoDB;

CREATE TABLE pasajeros(
	doc_tipo VARCHAR(50) NOT NULL,
	doc_nro INT UNSIGNED NOT NULL,
	apellido VARCHAR(20) NOT NULL,
	nombre VARCHAR(20) NOT NULL,
	direccion VARCHAR(40) NOT NULL,
	telefono VARCHAR(15) NOT NULL,
	nacionalidad VARCHAR(20) NOT NULL,

	CONSTRAINT PK_pasajeros 
	PRIMARY KEY(doc_tipo, doc_nro)

)ENGINE = InnoDB;

CREATE TABLE empleados(
	legajo INT UNSIGNED NOT NULL,
	password CHAR(32) NOT NULL,
	doc_tipo VARCHAR(50) NOT NULL,
	doc_nro INT UNSIGNED NOT NULL,
	apellido VARCHAR(20) NOT NULL,
	nombre VARCHAR(20) NOT NULL,
	direccion VARCHAR(40) NOT NULL,
	telefono VARCHAR(15) NOT NULL,

	CONSTRAINT PK_empleados 
	PRIMARY KEY(legajo)

)ENGINE = InnoDB;


CREATE TABLE reservas(
	numero INT UNSIGNED NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,	
    vencimiento DATE NOT NULL,
	estado VARCHAR(15) NOT NULL,
	doc_tipo VARCHAR(50) NOT NULL,
	doc_nro INT UNSIGNED NOT NULL,
	legajo INT UNSIGNED NOT NULL,

	CONSTRAINT PK_reservas 
	PRIMARY KEY(numero),

	CONSTRAINT FK_reservas_doc 
	FOREIGN KEY (doc_tipo,doc_nro) REFERENCES pasajeros(doc_tipo,doc_nro),

	CONSTRAINT FK_reservas_legajo 
	FOREIGN KEY (legajo) REFERENCES empleados(legajo)

) ENGINE = InnoDB;

CREATE TABLE brinda (
	vuelo VARCHAR(10) NOT NULL,
	dia ENUM('do','lu','ma','mi','ju','vi','sa') NOT NULL,
	clase VARCHAR(20) NOT NULL,
	precio DECIMAL(7,2) UNSIGNED NOT NULL,
	cant_asientos  SMALLINT UNSIGNED NOT NULL,

	CONSTRAINT PK_brinda 
	PRIMARY KEY(vuelo,dia,clase),

	CONSTRAINT FK_brinda_vuelo_dia
	FOREIGN KEY(vuelo,dia) REFERENCES salidas(vuelo,dia),

	CONSTRAINT FK_brinda_clase 
	FOREIGN KEY(clase) REFERENCES clases(nombre)

) ENGINE = InnoDB;

CREATE TABLE posee(
	clase VARCHAR(20) NOT NULL,
	comodidad SMALLINT UNSIGNED NOT NULL,

	CONSTRAINT PK_posee 
	PRIMARY KEY(clase,comodidad),

	CONSTRAINT FK_posee_clase 
	FOREIGN KEY(clase) REFERENCES clases(nombre),

	CONSTRAINT FK_posee_comodidad 
	FOREIGN KEY(comodidad) REFERENCES comodidades(codigo)
) ENGINE = InnoDB;

CREATE TABLE reserva_vuelo_clase(
	numero INT UNSIGNED NOT NULL,
	vuelo VARCHAR(10) NOT NULL,
	fecha_vuelo DATE NOT NULL,
	clase VARCHAR(20) NOT NULL,

	CONSTRAINT PK_reserva_vuelo_clase 
	PRIMARY KEY (numero,vuelo,fecha_vuelo),

	CONSTRAINT FK_reserva_vuelo_clase_numero
	FOREIGN KEY(numero) REFERENCES reservas(numero),

	CONSTRAINT FK_reserva_vuelo_clase_instancia_vuelo 
	FOREIGN KEY (vuelo,fecha_vuelo) REFERENCES instancias_vuelo(vuelo,fecha),

	CONSTRAINT FK_reserva_vuelo_clase_clase
	FOREIGN KEY(clase) REFERENCES clases(nombre)

) ENGINE = InnoDB;

CREATE TABLE asientos_reservados(
	vuelo VARCHAR(10) NOT NULL,
	fecha DATE NOT NULL,
	clase VARCHAR(20) NOT NULL,
	cantidad INT UNSIGNED NOT NULL,

	CONSTRAINT PK_asientos_reservados 
	PRIMARY KEY (vuelo,fecha,clase),

	CONSTRAINT FK_asientos_reservados_vuelo 
	FOREIGN KEY (vuelo,fecha) REFERENCES instancias_vuelo(vuelo,fecha),

	CONSTRAINT FK_asientos_reservados_clase 
	FOREIGN KEY(clase) REFERENCES clases(nombre)

) ENGINE = InnoDB;

/*creacion de la vista*/
	
CREATE VIEW vuelos_disponibles AS 
	SELECT 
		vuelo AS nro_vuelo,
		dia AS dia_sale,
		modelo_avion AS modelo,
		fecha,
		hora_sale,
		codigo_aero_sale,
		nombre_aero_sale,
		ciudad_sale,
		estado_sale,
		pais_sale,
		hora_llega,
		codigo_aero_llega,
		nombre_aero_llega,
		ciudad_llega,
		estado_llega,
		pais_llega,
		TIME(TIMEDIFF(hora_llega, hora_sale) + IF(hora_llega < hora_sale, TIME('24:00:00'), TIME('00:00:00'))) AS tiempo_estimado,
		precio,
		asientos_disponibles,
		clase
	FROM
		instancias_vuelo NATURAL JOIN salidas
		NATURAL JOIN 
		(
			SELECT
				numero AS vuelo,
				aeropuerto_salida AS codigo_aero_sale,
				aeropuerto_llegada AS codigo_aero_llega
			FROM
				vuelos_programados
		) AS vuelos_programados
		NATURAL JOIN 
		(
			SELECT
				codigo AS codigo_aero_llega,
				nombre AS nombre_aero_llega,
				ciudad AS ciudad_llega,
				estado AS estado_llega,
				pais AS pais_llega
			FROM 
				aeropuertos
		) AS aeropuertos_llegada
		NATURAL JOIN
		(
			SELECT
				codigo AS codigo_aero_sale,
				nombre AS nombre_aero_sale,
				ciudad AS ciudad_sale,
				estado AS estado_sale,
				pais AS pais_sale
			FROM 
				aeropuertos
		) AS aeropuertos_salida
		NATURAL JOIN
		(
			SELECT 
				instancias_vuelo.vuelo AS vuelo,
				instancias_vuelo.dia AS dia,
				instancias_vuelo.fecha AS fecha,
				brinda.clase AS clase,
				round(cant_asientos + cant_asientos*porcentaje - IF(reserva_vuelo_clase.clase IS NULL, 0, COUNT(*)), 0) AS asientos_disponibles 
			FROM
				instancias_vuelo
				LEFT JOIN
				brinda
				ON
					instancias_vuelo.vuelo = brinda.vuelo AND
					instancias_vuelo.dia = brinda.dia
				LEFT JOIN
				clases
				ON
					brinda.clase = clases.nombre
				LEFT JOIN
				reserva_vuelo_clase
				ON
					instancias_vuelo.vuelo = reserva_vuelo_clase.vuelo AND
					instancias_vuelo.fecha = reserva_vuelo_clase.fecha_vuelo AND
					brinda.clase = reserva_vuelo_clase.clase
			GROUP BY instancias_vuelo.vuelo, instancias_vuelo.dia, instancias_vuelo.fecha, brinda.clase
		) AS asientos_disp_por_clase
		NATURAL JOIN 
		brinda
;


/* usuario admin */
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON vuelos.* TO 'admin'@'localhost' WITH GRANT OPTION;

/* usuario empleado */
CREATE USER 'empleado'@'%' IDENTIFIED BY 'empleado';
GRANT SELECT ON vuelos.* TO 'empleado'@'%';

GRANT INSERT, UPDATE, DELETE ON vuelos.reservas  TO 'empleado'@'%';
GRANT INSERT, UPDATE, DELETE ON vuelos.pasajeros TO 'empleado'@'%';
GRANT INSERT, UPDATE, DELETE ON vuelos.reserva_vuelo_clase TO 'empleado'@'%';

/* usuario cliente */ 
CREATE USER 'cliente'@'%' IDENTIFIED BY 'cliente';
GRANT SELECT ON vuelos.vuelos_disponibles TO 'cliente'@'%';


