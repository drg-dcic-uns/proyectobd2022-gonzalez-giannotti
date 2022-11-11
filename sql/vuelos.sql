
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

# Creacion de stored procedures

# Defino '!' como delimitador de sentencias
delimiter !

# Procedimiento para realizar una reserva de un vuelo de ida
CREATE PROCEDURE reservar_ida(IN numero VARCHAR(45), IN fecha DATE, IN clase VARCHAR(20),
                              IN tipo_doc VARCHAR(3), IN nro_doc INT, IN legajo_empleado INT)

BEGIN
    DECLARE cant_reservados SMALLINT;
    DECLARE estado_reserva VARCHAR(20);
    DECLARE id_reserva_vuelo INT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
		BEGIN
			SELECT 'Falla : SQLEXCEPTION!, transaccion abortada' AS resultado;
			ROLLBACK;
		END;

    START TRANSACTION;
        # Se verifica que los datos sean correctos
        IF  EXISTS (SELECT * FROM pasajeros WHERE doc_nro = nro_doc AND doc_tipo = tipo_doc)
        THEN
            IF EXISTS (SELECT * FROM empleados WHERE legajo = legajo_empleado)
            THEN
                IF  EXISTS (SELECT * FROM instancias_vuelo as iv WHERE iv.vuelo = numero AND iv.fecha = fecha) AND
                    EXISTS (SELECT * FROM instancias_vuelo as iv JOIN brinda as b WHERE (iv.fecha = fecha) AND
                        (iv.vuelo = numero) AND (iv.vuelo = b.vuelo) AND (iv.dia = b.dia) AND (b.clase = clase))

                THEN
                    SELECT cantidad INTO cant_reservados FROM asientos_reservados ar WHERE ar.vuelo = numero AND
                                                        ar.fecha = fecha AND ar.clase = clase FOR UPDATE;
                    # Se verifica que el vuelo seleccionado tenga asientos disponibles en una clase y fecha dada
                    IF EXISTS (SELECT * FROM vuelos_disponibles as vd WHERE vd.nro_vuelo = numero AND vd.fecha = fecha AND
                                                        vd.clase = clase AND vd.asientos_disponibles > 0)
                    THEN
                        # Si la capacidad fisica está colmada, la reserva queda en espera, en caso contrario queda confirmada

                        #Si no hay datos cargados en asientos_resevados, cant_reservados es empty set y da falso cuando tendria
                        # que dar verdadero
                        SELECT IF(cant_reservados < cant_asientos, 'confirmada', 'en espera') INTO estado_reserva
                    	FROM instancias_vuelo as iv JOIN brinda as b
                    	WHERE (iv.fecha = fecha) AND (iv.vuelo = numero) AND (iv.vuelo = b.vuelo) AND
                              (iv.dia = b.dia) AND (b.clase = clase);

                        # Se inserta la reserva y el vuelo en las tablas correspondientes
                        INSERT INTO reservas(fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) VALUES
                            (CURDATE(), DATE_SUB(fecha, INTERVAL 15 DAY), estado_reserva, tipo_doc, nro_doc, legajo_empleado);
			 SET id_reserva_vuelo:= LAST_INSERT_ID();
                        INSERT INTO reserva_vuelo_clase VALUES (LAST_INSERT_ID(), numero, fecha, clase);

                        UPDATE asientos_reservados as ar SET cantidad = cantidad + 1 WHERE (ar.vuelo = numero) AND
                                                                                (ar.fecha = fecha) AND (ar.clase = clase);

                        SELECT 'La reserva se ha realizado con exito' as resultado;
                    ELSE
        	            SELECT 'Falla : No hay lugares disponibles en el vuelo y clase solicitados' as resultado;
                    END IF;
                ELSE
                    SELECT 'Falla : No existe el vuelo en el dia y clase solicitados' as resultado;
                END IF;
            ELSE
                SELECT 'Falla : No se encuentra el empleado' as resultado;
            END IF;
        ELSE
			SELECT 'Falla : No existe el pasajero indicado' AS resultado;
		END IF;
	COMMIT;
END;!

# Procedimiento para realizar una reserva de un vuelo de ida y uno de vuelta
CREATE PROCEDURE reservar_ida_vuelta(IN nro_ida VARCHAR(45),IN nro_vuelta VARCHAR(45), IN fecha_ida DATE,
                        IN fecha_vuelta DATE, IN clase_ida VARCHAR(20),IN clase_vuelta VARCHAR(20),
                        IN tipo_doc VARCHAR(3), IN nro_doc INT, IN legajo_empleado INT)

BEGIN

    DECLARE estado_reserva VARCHAR(20);
    DECLARE cant_reservados_ida SMALLINT;
    DECLARE cant_reservados_vuelta SMALLINT;
    DECLARE id_reserva_vuelo INT;

        START TRANSACTION;
            # Se verifica que los datos sean correctos
            IF  EXISTS (SELECT * FROM pasajeros WHERE doc_nro = nro_doc AND doc_tipo = tipo_doc)
            THEN
                IF EXISTS (SELECT * FROM empleados WHERE legajo = legajo_empleado)
                THEN
                    IF  EXISTS (SELECT * FROM instancias_vuelo as iv WHERE iv.vuelo = nro_ida AND iv.fecha = fecha_ida) AND
        			    EXISTS (SELECT * FROM instancias_vuelo as iv WHERE iv.vuelo = nro_vuelta AND iv.fecha = fecha_vuelta) AND
                        EXISTS (SELECT * FROM instancias_vuelo as iv, brinda as b WHERE iv.fecha = fecha_ida AND
                                    iv.vuelo = nro_ida AND iv.vuelo = b.vuelo AND iv.dia = b.dia AND b.clase = clase_ida) AND
            			EXISTS (SELECT * FROM instancias_vuelo as iv, brinda as b WHERE iv.fecha = fecha_vuelta AND
                                    iv.vuelo = nro_vuelta AND iv.vuelo = b.vuelo AND iv.dia = b.dia AND b.clase = clase_vuelta)
                    THEN
                        SELECT cantidad INTO cant_reservados_ida FROM asientos_reservados as ar WHERE ar.vuelo = nro_ida AND
                                                                            ar.fecha = fecha_ida AND ar.clase = clase_ida FOR UPDATE;
                        SELECT cantidad INTO cant_reservados_vuelta FROM asientos_reservados as ar WHERE ar.vuelo = nro_vuelta AND
                                                                        ar.fecha = fecha_vuelta AND ar.clase = clase_vuelta FOR UPDATE;

                        # Se verifica que el vuelo de ida tenga asientos disponibles
                        IF EXISTS (SELECT * FROM vuelos_disponibles as vd WHERE vd.nro_vuelo = nro_ida AND vd.fecha = fecha_ida AND
                                                                        vd.clase = clase_ida AND asientos_disponibles > 0)
                        THEN
                            # Se verifica que el vuelo de vuelta tenga asientos disponibles
                            IF EXISTS (SELECT * FROM vuelos_disponibles as vd WHERE vd.nro_vuelo = nro_vuelta AND vd.fecha = fecha_vuelta AND
                                                                        vd.clase = clase_vuelta AND asientos_disponibles > 0)
                            THEN
                                SELECT IF(cant_reservados_ida < cant_asientos, 'confirmada', 'en espera') INTO estado_reserva
                                FROM instancias_vuelo NATURAL JOIN brinda
                                WHERE (vuelo = nro_ida) AND (fecha = fecha_ida) AND (clase = clase_ida);

                                IF (estado_reserva = 'confirmada')
                                THEN
                                    SELECT IF(cant_reservados_vuelta < cant_asientos, 'confirmada', 'en espera') INTO estado_reserva
                                    FROM instancias_vuelo NATURAL JOIN brinda
                                    WHERE (vuelo = nro_vuelta) AND (fecha = fecha_vuelta) AND (clase = clase_vuelta);
                                END IF;

                                # Se inserta la reserva y los vuelos en las tablas correspondientes
                                INSERT INTO reservas(fecha, vencimiento, estado, doc_tipo, doc_nro, legajo) VALUES
                                    (CURDATE(), DATE_SUB(fecha_ida, INTERVAL 15 DAY), estado_reserva, tipo_doc, nro_doc, legajo_empleado);
                                SET id_reserva_vuelo:= LAST_INSERT_ID();
                                INSERT INTO reserva_vuelo_clase VALUES (LAST_INSERT_ID(), nro_ida, fecha_ida, clase_ida);
                                INSERT INTO reserva_vuelo_clase VALUES (LAST_INSERT_ID(), nro_vuelta, fecha_vuelta, clase_vuelta);

                                UPDATE asientos_reservados SET cantidad = cantidad + 1 WHERE vuelo = nro_ida AND
                                                                            fecha = fecha_ida AND clase = clase_ida;
                                UPDATE asientos_reservados SET cantidad = cantidad + 1 WHERE vuelo = nro_vuelta AND
                                                                            fecha = fecha_vuelta AND clase = clase_vuelta;

                                SELECT 'La reserva se ha realizado con exito' as resultado;
                            ELSE
                                SELECT 'Falla : No hay lugares disponibles para el vuelo de vuelta en la clase solicitada' as resultado;
                            END IF;
                        ELSE
                            SELECT 'Falla : No hay lugares disponibles para el vuelo de ida en la clase solicitada' as resultado;
                        END IF;
                    ELSE
                        SELECT 'Falla : Datos de vuelos incorrectos' as resultado;
                    END IF;
                ELSE
                    SELECT 'Falla : No se encuentra el empleado' as resultado;
                END IF;
            ELSE
    			SELECT 'Falla : No existe el pasajero indicado' AS resultado;
    		END IF;
    	COMMIT;
    END;!
	
/* Creación del trigger que se activa cuando se inserta una instancia de vuelo. */
/* Su propósito es inicializar automáticamente en cero la cantidad de asientos 
reservados asociados a cada clase correspondiente a cada instancia de vuelo insertada. */
CREATE TRIGGER asientos_reservados_init
AFTER INSERT ON instancias_vuelo
FOR EACH ROW
BEGIN
	# declaro la variable que va a almacenar cada clase que existe en la base de datos.
	DECLARE clase_it VARCHAR(20);
	# declaro un flag para cortar el loop.
	DECLARE fin BOOLEAN DEFAULT FALSE;
	DECLARE C CURSOR for SELECT nombre FROM clases;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET fin=TRUE;
	OPEN C;
	FETCH C INTO clase_it;
	WHILE NOT fin DO
		INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad)
		VALUES (NEW.vuelo, NEW.fecha, clase_it, 0);
		FETCH C INTO clase_it;
	END WHILE;
END; !

delimiter ;


/* usuario admin */
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON vuelos.* TO 'admin'@'localhost' WITH GRANT OPTION;

/* usuario empleado */
CREATE USER 'empleado'@'%' IDENTIFIED BY 'empleado';
GRANT SELECT ON vuelos.* TO 'empleado'@'%';

GRANT INSERT, UPDATE, DELETE ON vuelos.reservas  TO 'empleado'@'%';
GRANT INSERT, UPDATE, DELETE ON vuelos.pasajeros TO 'empleado'@'%';
GRANT INSERT, UPDATE, DELETE ON vuelos.reserva_vuelo_clase TO 'empleado'@'%';
GRANT EXECUTE ON PROCEDURE vuelos.reservar_ida TO 'empleado'@'%';
GRANT EXECUTE ON PROCEDURE vuelos.reservar_ida_vuelta TO 'empleado'@'%';

/* usuario cliente */ 
CREATE USER 'cliente'@'%' IDENTIFIED BY 'cliente';
GRANT SELECT ON vuelos.vuelos_disponibles TO 'cliente'@'%';


