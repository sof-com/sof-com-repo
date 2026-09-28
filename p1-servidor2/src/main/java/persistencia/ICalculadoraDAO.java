/* Software de Comunicaciones - Prácticas de la asignatura
 * DTE, ETSIST, UPM
 * Curso 2026/2027
 * Práctica 1, fase 3
 * Archivo: ICalculadoraDAO.java
 */

package persistencia;

import calculadora.OperacionesCalculadora;

/**
 * DAO específico para la entidad calculadora. Extiende la interfaz {@link DAO} genérica
 * parametrizada por Integer como clave y OperacionesCalculadora como entidad.
 */
public interface ICalculadoraDAO extends DAO<Integer, OperacionesCalculadora> {

    // Aquí se pueden añadir métodos específicos del DAO de calculadora si se requieren,
	// además de los métodos CRUD básicos heredados de la interfaz DAO genérica.
	
}
