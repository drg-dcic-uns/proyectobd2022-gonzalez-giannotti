
/*ubicaciones*/

INSERT INTO ubicaciones (pais, estado, ciudad, huso) VALUES ('Argentina', 'Buenos Aires', 'C.A.B.A.', -3);
INSERT INTO ubicaciones (pais, estado, ciudad, huso) VALUES ('Argentina', 'Buenos Aires', 'Bahia Blanca', -3);
INSERT INTO ubicaciones (pais, estado, ciudad, huso) VALUES ('Argentina', 'Cordoba', 'Cordoba', -3);
INSERT INTO ubicaciones (pais, estado, ciudad, huso) VALUES ('Argentina', 'Salta', 'Salta', -3);
INSERT INTO ubicaciones (pais, estado, ciudad, huso) VALUES ('Argentina', 'Neuquen', 'Neuquen', -3);
INSERT INTO ubicaciones (pais, estado, ciudad, huso) VALUES ('Chile', 'Santiago de Chile', 'Santiago', -3);
INSERT INTO ubicaciones (pais, estado, ciudad, huso) VALUES ('Uruguay', 'Maldonado', 'Punta del Este', -3);
INSERT INTO ubicaciones (pais, estado, ciudad, huso) VALUES ('Uruguay', 'Colonia', 'Col del Sacramento', -3);
INSERT INTO ubicaciones (pais, estado, ciudad, huso) VALUES ('Espanya', 'Madrid', 'Madrid', 2);
INSERT INTO ubicaciones (pais, estado, ciudad, huso) VALUES ('Marruecos', 'Menara', 'Marrakech', 1);

/*aeropuertos*/

INSERT INTO aeropuertos (codigo, nombre, telefono, direccion, pais, estado, ciudad) VALUES ('AEP', 'Aeroparque Metropolitano Jorge Newbery', '011 5480-6111', 'Av. Costanera Rafael Obligado', 'Argentina', 'Buenos Aires', 'C.A.B.A.');
INSERT INTO aeropuertos (codigo, nombre, telefono, direccion, pais, estado, ciudad) VALUES ('BHI', 'Aeroestacion Civil Comandante Espora', '0291 486-0319', 'Ex Ruta 3 Norte KM 675', 'Argentina', 'Buenos Aires', 'Bahia Blanca');
INSERT INTO aeropuertos (codigo, nombre, telefono, direccion, pais, estado, ciudad) VALUES ('COR', 'Ing Aeronautico Ambrosio Taravella', '0351 475-0881', 'Av. La Voz del Interior 8500', 'Argentina', 'Cordoba', 'Cordoba');
INSERT INTO aeropuertos (codigo, nombre, telefono, direccion, pais, estado, ciudad) VALUES ('NQN', 'Presidente Peron', '0299 444-0448', 'Q8300 Neuquen', 'Argentina', 'Neuquen', 'Neuquen');
INSERT INTO aeropuertos (codigo, nombre, telefono, direccion, pais, estado, ciudad) VALUES ('SLA', 'Martin Miguel de Guemes', '0387 424-3115', 'RN51 5 Salta', 'Argentina', 'Salta', 'Salta');
INSERT INTO aeropuertos (codigo, nombre, telefono, direccion, pais, estado, ciudad) VALUES ('SCL', 'Arturo Merino Benitez', '+56 2 2690 1796', 'Armando Cortinez Ote. 1704', 'Chile', 'Santiago de Chile', 'Santiago');
INSERT INTO aeropuertos (codigo, nombre, telefono, direccion, pais, estado, ciudad) VALUES ('MAD', 'Adolfo Suarez Madrid-Barajas', '+34 913 211000', 'Av de la Hispanidad 28042', 'Espanya', 'Madrid', 'Madrid');
INSERT INTO aeropuertos (codigo, nombre, telefono, direccion, pais, estado, ciudad) VALUES ('RAK', 'Marrakech-Menara', '+212 5244-47910', 'RAK Mhamid saada n209', 'Marruecos', 'Menara', 'Marrakech');
INSERT INTO aeropuertos (codigo, nombre, telefono, direccion, pais, estado, ciudad) VALUES ('CYR', 'Laguna de los Patos', '+598 4522 4853', 'G6XM+XGG', 'Uruguay', 'Colonia', 'Col del Sacramento');
INSERT INTO aeropuertos (codigo, nombre, telefono, direccion, pais, estado, ciudad) VALUES ('PDP', 'Laguna del Sauce C/C Carlos Curbelo', '+598 4255 9777', '4WQ2+HP3', 'Uruguay', 'Maldonado', 'Punta del Este');

/*vuelos_programados*/

INSERT INTO vuelos_programados (numero, aeropuerto_salida, aeropuerto_llegada) VALUES ('1', 'MAD', 'RAK');
INSERT INTO vuelos_programados (numero, aeropuerto_salida, aeropuerto_llegada) VALUES ('2', 'AEP', 'COR');
INSERT INTO vuelos_programados (numero, aeropuerto_salida, aeropuerto_llegada) VALUES ('3', 'CYR', 'AEP');
INSERT INTO vuelos_programados (numero, aeropuerto_salida, aeropuerto_llegada) VALUES ('4', 'BHI', 'AEP');
INSERT INTO vuelos_programados (numero, aeropuerto_salida, aeropuerto_llegada) VALUES ('5', 'NQN', 'SLA');
INSERT INTO vuelos_programados (numero, aeropuerto_salida, aeropuerto_llegada) VALUES ('6', 'SCL', 'AEP');
INSERT INTO vuelos_programados (numero, aeropuerto_salida, aeropuerto_llegada) VALUES ('7', 'BHI', 'COR');
INSERT INTO vuelos_programados (numero, aeropuerto_salida, aeropuerto_llegada) VALUES ('8', 'MAD', 'AEP');
INSERT INTO vuelos_programados (numero, aeropuerto_salida, aeropuerto_llegada) VALUES ('9', 'AEP', 'MAD');

/*modelos avion*/

INSERT INTO modelos_avion (modelo, fabricante, cabinas, cant_asientos) VALUES ('Boeing 737', 'Boeing', 6, 220);
INSERT INTO modelos_avion (modelo, fabricante, cabinas, cant_asientos) VALUES ('Bristol Britannia', 'Bristol Co.', 6, 139);
INSERT INTO modelos_avion (modelo, fabricante, cabinas, cant_asientos) VALUES ('Douglas DC-9', 'Douglas Aircraft Co.', 5, 172);
INSERT INTO modelos_avion (modelo, fabricante, cabinas, cant_asientos) VALUES ('Short 360', 'Short Brothers', 3, 36);
INSERT INTO modelos_avion (modelo, fabricante, cabinas, cant_asientos) VALUES ('Embraer EMB 110', 'Embraer', 2, 19);
INSERT INTO modelos_avion (modelo, fabricante, cabinas, cant_asientos) VALUES ('de Havilland Comet', 'de Havilland', 5, 81);
INSERT INTO modelos_avion (modelo, fabricante, cabinas, cant_asientos) VALUES ('Tupolev Tu-144', 'Tupolev', 5, 140);
INSERT INTO modelos_avion (modelo, fabricante, cabinas, cant_asientos) VALUES ('Ilyushin Il-18', 'Moscow Machinery', 5, 120);
INSERT INTO modelos_avion (modelo, fabricante, cabinas, cant_asientos) VALUES ('Mitsubishi Jet', 'Mitsubishi Corp.', 4, 92);
INSERT INTO modelos_avion (modelo, fabricante, cabinas, cant_asientos) VALUES ('Dornier 328', 'Dornier Luftfahrt', 3, 32);

/*salidas*/

INSERT INTO salidas (vuelo, dia, hora_sale, hora_llega, modelo_avion) VALUES (1, 'Do', '18:00', '22:00', 'Boeing 737');
INSERT INTO salidas (vuelo, dia, hora_sale, hora_llega, modelo_avion) VALUES (3, 'Mi', '09:00', '17:30', 'Douglas DC-9');
INSERT INTO salidas (vuelo, dia, hora_sale, hora_llega, modelo_avion) VALUES (9, 'Ju', '18:00', '22:00', 'Ilyushin Il-18');
INSERT INTO salidas (vuelo, dia, hora_sale, hora_llega, modelo_avion) VALUES (4, 'Lu', '10:00', '23:00', 'Mitsubishi Jet');
INSERT INTO salidas (vuelo, dia, hora_sale, hora_llega, modelo_avion) VALUES (2, 'Ma', '13:00', '22:00', 'Boeing 737');
INSERT INTO salidas (vuelo, dia, hora_sale, hora_llega, modelo_avion) VALUES (5, 'Ma', '12:00', '20:00', 'Short 360');
INSERT INTO salidas (vuelo, dia, hora_sale, hora_llega, modelo_avion) VALUES (6, 'Mi', '12:00', '22:00', 'Short 360');

/*clases*/

INSERT INTO clases (nombre, porcentaje) VALUES ('turista', 0.15);
INSERT INTO clases (nombre, porcentaje) VALUES ('vip', 0.55);
INSERT INTO clases (nombre, porcentaje) VALUES ('bussiness', 0.40);

/*brinda*/

INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('1', 'do', 'turista', 50000.00, 100);
INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('1', 'do', 'vip', 90000.00, 20);
INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('1', 'do', 'bussiness', 70000.00, 100);

INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('2', 'ma', 'turista', 36000.00, 120);
INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('2', 'ma', 'vip', 96000.00, 20);
INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('2', 'ma', 'bussiness', 56000.00, 80);

INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('3', 'mi', 'turista', 70000.00, 90);
INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('3', 'mi', 'vip', 98000.00, 12);
INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('3', 'mi', 'bussiness', 83000.00, 70);

INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('4', 'lu', 'turista', 30000.00, 80);
INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('4', 'lu', 'vip', 85000.00, 2);
INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('4', 'lu', 'bussiness', 51000.00, 10);

INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('5', 'ma', 'turista', 60000.00, 36);

INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('6', 'mi', 'turista', 55000.00, 36);

INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('9', 'ju', 'turista', 50000.00, 100);
INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('9', 'ju', 'vip', 97000.00, 5);
INSERT INTO brinda (vuelo, dia, clase, precio, cant_asientos) VALUES ('9', 'ju', 'bussiness', 77000.00, 15);

/*instancias_vuelo*/

INSERT INTO instancias_vuelo (vuelo, fecha, dia, estado) VALUES ('1', '2000/01/02', 'do', 'Cancelado');
INSERT INTO instancias_vuelo (vuelo, fecha, dia, estado) VALUES ('2', '2002/06/20', 'ma', 'Aterrizado');
INSERT INTO instancias_vuelo (vuelo, fecha, dia, estado) VALUES ('3', '2000/01/02', 'mi', 'Cancelado');
INSERT INTO instancias_vuelo (vuelo, fecha, dia, estado) VALUES ('4', '2010/04/05', 'lu', 'Aterrizado');
INSERT INTO instancias_vuelo (vuelo, fecha, dia, estado) VALUES ('5', '2020/01/07', 'ma', 'Cancelado');
INSERT INTO instancias_vuelo (vuelo, fecha, dia, estado) VALUES ('6', '2003/01/04', 'mi', 'Aterrizado');
INSERT INTO instancias_vuelo (vuelo, fecha, dia, estado) VALUES ('9', '2000/01/30', 'ju', 'Cancelado');

/*asientos_reservados*/

INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('1', '2000/01/02', 'turista', 30);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('1', '2000/01/02', 'bussiness', 15);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('1', '2000/01/02', 'vip', 2);

INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('2', '2002/06/20', 'turista', 20);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('2', '2002/06/20', 'bussiness', 15);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('2', '2002/06/20', 'vip', 0);	

INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('3', '2000/01/02', 'turista', 10);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('3', '2000/01/02', 'bussiness', 10);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('3', '2000/01/02', 'vip', 0);

INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('4', '2010/04/05', 'turista', 15);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('4', '2010/04/05', 'bussiness', 0);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('4', '2010/04/05', 'vip', 0);

INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('5', '2020/01/07', 'turista', 5);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('5', '2020/01/07', 'bussiness', 0);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('5', '2020/01/07', 'vip', 0);

INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('6', '2003/01/04', 'turista', 0);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('6', '2003/01/04', 'bussiness', 0);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('6', '2003/01/04', 'vip', 0);

INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('9', '2000/01/30', 'turista', 10);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('9', '2000/01/30', 'bussiness', 0);
INSERT INTO asientos_reservados (vuelo, fecha, clase, cantidad) VALUES ('9', '2000/01/30', 'vip', 0);

/*comodidades*/

INSERT INTO comodidades VALUES(0,'banyo');
INSERT INTO comodidades VALUES(1,'Television');
INSERT INTO comodidades VALUES(2,'Internet');

/*pasajeros*/

INSERT INTO pasajeros VALUES('dni','23488653','Gutierrez','Pablo','San Juan 232','2913249543','Argentino');
INSERT INTO pasajeros VALUES('dni','42433954','Sosa','Carolina','Peru 2502','2915293432','Argentino');
INSERT INTO pasajeros VALUES('dni','35499375','Torres','Agustina','Jose Hernandez 105','2914823954','Argentino');

/*empleados*/

INSERT INTO empleados VALUES(1032,MD5('1234'),'dni','38433854','Hernandez','Maria','Alem 2323','2914823475');
INSERT INTO empleados VALUES(1033,MD5('1235'),'dni','35594321','Garcia','Hernan','Vieytes 221','2914943721');
INSERT INTO empleados VALUES(1034,MD5('1236'),'dni','24599432','Perez','Claudio','Paraguay 2560','2914823943');

/*reservas*/

INSERT INTO reservas(fecha,vencimiento,estado,doc_tipo,doc_nro,legajo) VALUES ('2022/10/22','2022/12/22','Pagada','dni','23488653',1032);
INSERT INTO reservas(fecha,vencimiento,estado,doc_tipo,doc_nro,legajo) VALUES ('2022/10/27','2022/12/27','Pagada','dni','23488653',1033);
INSERT INTO reservas(fecha,vencimiento,estado,doc_tipo,doc_nro,legajo) VALUES ('2022/02/10','2022/04/10','Pagada','dni','42433954',1033);
INSERT INTO reservas(fecha,vencimiento,estado,doc_tipo,doc_nro,legajo) VALUES ('2022/02/17','2022/04/17','Pagada','dni','42433954',1034);
INSERT INTO reservas(fecha,vencimiento,estado,doc_tipo,doc_nro,legajo) VALUES ('2022/07/12','2022/09/12','Pagada','dni','35499375',1033);
INSERT INTO reservas(fecha,vencimiento,estado,doc_tipo,doc_nro,legajo) VALUES ('2022/07/20','2022/09/20','Pagada','dni','35499375',1034);

/*reserva_vuelo_Clase*/
INSERT INTO reserva_vuelo_clase VALUES (1, '1', '2000/01/02', 'bussiness');
INSERT INTO reserva_vuelo_clase VALUES (2, '2', '2002/06/20', 'vip');
INSERT INTO reserva_vuelo_clase VALUES (3, '3', '2000/01/02', 'turista');
INSERT INTO reserva_vuelo_clase VALUES (4, '4', '2010/04/05', 'turista');
 
/* posee */

INSERT INTO posee VALUES ('Bussiness',0);
INSERT INTO posee VALUES ('Bussiness',1);
INSERT INTO posee VALUES ('Turista',0);
INSERT INTO posee VALUES ('Turista',1);
INSERT INTO posee VALUES ('vip',0);
INSERT INTO posee VALUES ('vip',1);
INSERT INTO posee VALUES ('vip',2);