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
 * <p>
 * En la fase 3 de la práctica, se debe incorporar el uso de <code>GestorSesionServidor</code> de acuerdo a
 * la documentación contenida en la misma clase.
 * </p>
 * @author DMC, DTE, Software de Comunicaciones 2025/2026
 * @version 1.0.0
 */
public class ServidorCalculadora {
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