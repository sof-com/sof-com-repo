package servidor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import calculadora.OperacionesCalculadora;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.stub.StreamObserver;
import stubCalculadora.Calculadora.Operandos;
import stubCalculadora.Calculadora.Resultado;

/**
 * La clase GestorSesionServidor sirve para asociar una instancia de tipo OperacionesCalculadora
 * a cada sesión de cliente.
 * <p>
 * Contiene un interceptor gRPC que guarda en el contexto las cabeceras enviadas por cada cliente.
 * La cabecera personalizada <code>x-client-id</code> permite identificar de forma única la sesión 
 * del cliente.
 * </p>
 * <p>
 * Se debe utilizar en el servicio de la fase 3 de la práctica 1. Es necesario realizar dos modificaciones respecto de la fase 2:
 * una en <code>ServidorCalculadora</code> y otra en <code>AdaptadorOperacionesCalculadora</code>.
 * </p>
 * <p>
 * Uso:
 * </p>
 * <p>
 * 1) En la fase 3 se debe crear el servidor añadiendo el interceptor de contexto además del servicio:</br></br>
 * <code><b><pre>
 * Server server = ServerBuilder.forPort(port)
 *	.addService(new AdaptadorOperacionesCalculadoraContext())
 *	.intercept(new GestorSesionServidor.ContextInterceptor())    <---- ESTO ES LO QUE HAY QUE AÑADIR.
 *	.build()
 *	.start();
</pre></b></code>
 * </p>
 * <p>
 * 2) En cada método asociado a un procedimiento remoto, es necesario obtener la instancia adecuada de <code>OperacionesCalculadora</code> usando el método
 * <code>GestorSesionServidor.obtenerCalculadoraDeCliente()</code>. Por ejemplo, en el método <code>suma()</code> de la clase
 * <code><b><pre>
 * public void sumar(Operandos request, StreamObserver<Resultado> responseObserver) {
 *	...
 *	OperacionesCalculadora implementacionCalculadora = GestorSesionServidor.obtenerCalculadoraDeCliente();
 *	...
 * }
</pre></b></code>
 * </p>
 * @author DMC, DTE, Software de Comunicaciones 2025/2026
 * @version 1.0.0
 */
public class GestorSesionServidor {

	private static ConcurrentMap<String, OperacionesCalculadora> calculadorasPorCliente = new ConcurrentHashMap<>();
	
	public static OperacionesCalculadora obtenerCalculadoraDeCliente() {
		Metadata clientMetadata = GestorSesionServidor.CLIENT_METADATA.get();
		String clientID = clientMetadata.get(Metadata.Key.of(CLIENT_ID_HEADER, Metadata.ASCII_STRING_MARSHALLER));
		calculadorasPorCliente.putIfAbsent(clientID, new OperacionesCalculadora());
		return calculadorasPorCliente.get(clientID);
	}
	
	private static final Context.Key<Metadata> CLIENT_METADATA = Context.key("client-headers");
	private static final String CLIENT_ID_HEADER = "x-client-id";

	// Interceptor para obtener los metadatos del cliente.
	static class ContextInterceptor implements ServerInterceptor {

		@Override
		public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
				ServerCall<ReqT, RespT> call,
				Metadata headers,
				ServerCallHandler<ReqT, RespT> next) {

			// Attach client metadata to the context
			final Context ctx = Context.current().withValue(CLIENT_METADATA, headers);
			// Continue call with the new context
			return Contexts.interceptCall(ctx, call, headers, next);
		}
	}
}