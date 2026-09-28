/* Software de Comunicaciones - Prácticas de la asignatura
 * DTE, ETSIST, UPM
 * Curso 2026/2027
 * Práctica 1, fase 3
 * Archivo: CalculadoraDAO_Mapa.java
 */

package persistencia;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import calculadora.OperacionesCalculadora;

/**
 * Implementación de {@link ICalculadoraDAO} basada en un mapa concurrente en memoria.
 */
public class CalculadoraDAO_Mapa implements ICalculadoraDAO {

    private final ConcurrentMap<Integer, OperacionesCalculadora> almacenDeObjetos = new ConcurrentHashMap<>();
    private static final int MINIMO_IDENTIFICADOR_DE_SESION = 1;
    private final AtomicInteger generadorDeIdentificadoresDeSesion = new AtomicInteger(MINIMO_IDENTIFICADOR_DE_SESION);

    public CalculadoraDAO_Mapa() {
    }

    @Override
    public Integer create(OperacionesCalculadora entidad) throws Exception {
        // Generar una clave y almacenar la entidad, devolviendo la clave generada
        if (entidad == null) throw new IllegalArgumentException("Entidad nula");
        int identificadorSesion = generadorDeIdentificadoresDeSesion.getAndIncrement();
        almacenDeObjetos.put(identificadorSesion, entidad);
        return identificadorSesion;
    }

    @Override
    public OperacionesCalculadora find(Integer identificadorSesion) throws Exception {
        if (identificadorSesion < MINIMO_IDENTIFICADOR_DE_SESION) throw new IllegalArgumentException("Identificador de sesión inválido");
        return almacenDeObjetos.get(identificadorSesion);
    }

    @Override
    public void update(Integer identificadorSesion, OperacionesCalculadora entidad) throws Exception {
        if (identificadorSesion < MINIMO_IDENTIFICADOR_DE_SESION) throw new IllegalArgumentException("Identificador de sesión inválido");
        if (entidad == null) throw new IllegalArgumentException("Entidad nula");
        if (!almacenDeObjetos.containsKey(identificadorSesion)) throw new Exception("Entidad a actualizar no encontrada");
        almacenDeObjetos.put(identificadorSesion, entidad);
    }

    @Override
    public void delete(Integer identificadorSesion) throws Exception {
        if (identificadorSesion < MINIMO_IDENTIFICADOR_DE_SESION) throw new IllegalArgumentException("Identificador de sesión inválido");
        almacenDeObjetos.remove(identificadorSesion);
    }

    @Override
    public Collection<OperacionesCalculadora> findAll() throws Exception {
        return almacenDeObjetos.values();
    }
}
