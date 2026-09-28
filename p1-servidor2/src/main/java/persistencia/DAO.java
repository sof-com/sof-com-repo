/* Software de Comunicaciones - Prácticas de la asignatura
 * DTE, ETSIST, UPM
 * Curso 2026/2027
 * Práctica 1, fase 3
 * Archivo: DAO.java
 */

package persistencia;

/**
 * Interfaz DAO genérica parametrizada por tipo de clave (K) y tipo de entidad (T).
 * Define las operaciones CRUD básicas que deben implementar los DAOs concretos.
 *
 * @param <K> tipo de la clave (por ejemplo Integer, Long, String)
 * @param <T> tipo de la entidad gestionada por el DAO
 */
public interface DAO<K, T> {

    /**
     * Crea/almacena una nueva entidad y devuelve la clave generada.
     *
     * @param entidad la entidad a crear
     * @return la clave (tipo K) asociada a la entidad creada
     * @throws Exception en caso de error de persistencia
     */
    K create(T entidad) throws Exception;

    /**
     * Busca una entidad por su clave.
     *
     * @param clave la clave de la entidad
     * @return la entidad encontrada o null si no existe
     * @throws Exception en caso de error de persistencia
     */
    T find(K clave) throws Exception;

    /**
     * Actualiza una entidad existente identificada por su clave.
     *
     * @param clave la clave de la entidad a actualizar
     * @param entidad la entidad con los cambios a persistir
     * @throws Exception en caso de error de persistencia
     */
    void update(K clave, T entidad) throws Exception;

    /**
     * Elimina una entidad por su clave.
     *
     * @param clave la clave de la entidad a eliminar
     * @throws Exception en caso de error de persistencia
     */
    void delete(K clave) throws Exception;

    /**
     * Devuelve una colección con todas las entidades gestionadas por el DAO.
     *
     * @return colección con todas las entidades
     * @throws Exception en caso de error de persistencia
     */
    java.util.Collection<T> findAll() throws Exception;

}
