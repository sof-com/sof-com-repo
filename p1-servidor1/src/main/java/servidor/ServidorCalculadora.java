/* Software de Comunicaciones - Prácticas de la asignatura
 * DTE, ETSIST, UPM
 * Curso 2026/2027
 * Práctica 1, fases 2 y 3
 * Archivo: ServidorCalculadora.java
 */

package servidor;

import java.io.IOException;
import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * Clase principal para el servidor gRPC de la calculadora de la práctica 1.
 * <p>
 * Esta clase se encarga de inicializar y arrancar el servidor gRPC en el puerto especificado,
 * añadiendo el servicio de la calculadora. Además, gestiona el ciclo de vida del servidor,
 * incluyendo el apagado ordenado cuando la máquina virtual termina su ejecución.
 * @author DMC, DTE, Software de Comunicaciones 2026/2027
 * @version 2.0.0
 */
public class ServidorCalculadora {

	/** Este es el método inicial del servidor.
	 */
	 public static void main(String[] args) throws IOException, InterruptedException {
	       int port = 50051;
	       Server server = ServerBuilder.forPort(port) 
	               .addService(new AdaptadorOperacionesCalculadora())
	               .build()
	               .start();
	       System.out.println("Servidor arrancado, escucha en puerto TCP " + port);
	       Runtime.getRuntime().addShutdownHook(new Thread(() -> { 
	           System.out.println("Terminando el servidor gRPC porque " +
	        		   "la máquina virtual va a terminar su ejecución.");
	           if (server != null) {
	               server.shutdown();
	           }
	           System.err.print("Terminando el servidor...");
	       }));
	       server.awaitTermination(); 
           System.err.println("Terminado.");
	   }
}