package cliente;

import java.util.UUID;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

/**
 * La clase GestorSesionCliente es un interceptor gRPC para gestionar la sesión del cliente.
 * <p>
 * Este interceptor añade una cabecera personalizada <code>x-client-id</code> a cada llamada gRPC,
 * permitiendo identificar de forma única la sesión del cliente mediante un UUID generado automáticamente.
 * </p>
 * <p>
 * Se debe utilizar en el cliente de la fase 3 de la práctica 1.
 * </p>
 * <p>
 * Uso:
 * </p>
 * <p>
 * Donde en la fase 2 se ha creado el stub a partir del canal directamente:</br></br>
 * <code><b>stub = SservicioCalculadoraGrpc.newBlockingStub(channel);</b></code>
 * </p>
 * <p>
 * En la fase 3 se debe crear el stub añadiendo el interceptor como sigue:</br></br>
 * <code><b>stub = ServicioCalculadoraGrpc.newBlockingStub(ClientInterceptors.intercept(channel, new GestorSesionCliente()));</b></code>
 * </p>
 * @author DMC, DTE, Software de Comunicaciones 2025/2026
 * @version 1.0.0
 */
class GestorSesionCliente implements ClientInterceptor {
    /** Identificador único de cliente generado para la sesión. */
    private final String CLIENT_ID;

    /**
     * Crea un nuevo interceptor de sesión, generando un identificador único para el cliente.
     */
    public GestorSesionCliente() {
        CLIENT_ID = UUID.randomUUID().toString();
    }

    /**
     * Intercepta la llamada gRPC y añade la cabecera <code>x-client-id</code> con el identificador de cliente.
     */
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                // Añade la cabecera personalizada
                Metadata.Key<String> customHeaderKey =
                        Metadata.Key.of("x-client-id", Metadata.ASCII_STRING_MARSHALLER);
                headers.put(customHeaderKey, CLIENT_ID);
                super.start(responseListener, headers);
            }
        };
    }
}