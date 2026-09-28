/* Software de Comunicaciones - Prácticas de la asignatura
 * DTE, ETSIST, UPM
 * Curso 2026/2027
 * Práctica 1
 * Archivo: Implementacion_ICalculadora_Test.java
 */

package calculadora;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculadoraGUI.ICalculadora;

/** Suite de pruebas para OperacionesCalculadora (JUnit 5).
 * Prueba todas las operaciones aritméticas, raíz cuadrada, excepciones,
 * último resultado y memoria acumuladora.
 */
public class Implementacion_ICalculadora_Test {

    private ICalculadora ops;

    @BeforeEach
    public void setUp() {
        ops = new AdaptadorOperacionesCalculadoraGUI();
    }

    @Test
    public void testInitialURAndMemoryZero() {
        assertEquals(0.0, ops.obtenerUltimoResultado(), 1e-9, "UR debe ser 0 antes de operaciones");
        assertEquals(0.0, ops.memoriaObtener(), 1e-9, "La memoria acumuladora debe ser 0 antes de operaciones");
    }

    @Test
    public void testSumUpdatesUR() {
        double res = ops.sumar(2.5, 3.5);
        assertEquals(6.0, res, 1e-9);
        assertEquals(res, ops.obtenerUltimoResultado(), 1e-9, "UR debe contener el resultado de la suma");
    }

    @Test
    public void testSubtractUpdatesUR() {
        double res = ops.restar(10.0, 4.25);
        assertEquals(5.75, res, 1e-9);
        assertEquals(res, ops.obtenerUltimoResultado(), 1e-9, "UR debe contener el resultado de la sustracción");
    }

    @Test
    public void testMultiplicacionYUltimoResultado() {
        double res = ops.multiplicar(3.0, 4.0);
        assertEquals(12.0, res, 1e-9);
        assertEquals(res, ops.obtenerUltimoResultado(), 1e-9, "UR debe contener el resultado de la multiplicación");
    }

    @Test
    public void testDivisionYUltimoResultado() throws Exception {
        double res = ops.dividir(9.0, 3.0);
        assertEquals(3.0, res, 1e-9);
        assertEquals(res, ops.obtenerUltimoResultado(), 1e-9, "UR debe contener el resultado de la división");
    }

    @Test
    public void testDivisionPorCeroArrojaExcepcionDivisionPorCero() {
        Exception e = assertThrows(Exception.class, () -> ops.dividir(5.0, 0.0), "Cuando el divisor es cero y el dividendo no lo es, debe arrojar Exception");
        assertEquals(Exception.class, e.getClass(), "La excepción arrojada debe ser de tipo Exception");
        assertEquals(e.getClass().getName(), "java.lang.Exception", "La excepción arrojada no debe ser subclase de Exception");
        assertEquals("División por cero", e.getMessage(), "El mensaje de la excepción arrojada debe ser 'División por cero'");
    }

    @Test
    public void testDivisionPorCeroArrojaExcepcionIndeterminacion() {
        Exception e = assertThrows(Exception.class, () -> ops.dividir(0.0, 0.0), "Cuando el divisor y el dividendo son cero, debe arrojar Exception");
        assertEquals("Indeterminacion", e.getMessage(), "El mensaje de la excepción arrojada debe ser 'Indeterminacion'");
    }

    @Test
    public void testSqrtUpdatesUR() throws Exception {
        double res = ops.raizCuadrada(16.0);
        assertEquals(4.0, res, 1e-9, "El resultado debe ser la raíz cuadrada del argumento del método");
        assertEquals(res, ops.obtenerUltimoResultado(), 1e-9, "UR debe contener el resultado de la raíz cuadrada");
    }

    @Test
    public void testSqrtNegativeThrows() {
        Exception e = assertThrows(Exception.class, () -> ops.raizCuadrada(-4.0));
        assertEquals("Raíz de número negativo", e.getMessage(), "Cuando se intenta obtener la raíz cuadrada de un número negativo, debe arrojar Exception con mensaje 'Raíz de número negativo'");
    }

    @Test
    public void testMemoryOperations() throws Exception {
        // memoria inicialmente 0
        assertEquals(0.0, ops.memoriaObtener(), 1e-9, "La memoria acumuladora debe contener el valor 0 antes de cualquier operación");

        // ML pone memoria a 0 (aunque ya es 0)
        ops.memoriaLimpiar();
        assertEquals(0.0, ops.memoriaObtener(), 1e-9, "La memoria acumuladora debe contener el valor 0 despúes de ser limpiada");

        // realizar una operación y añadir a memoria
        double r1 = ops.sumar(1.0, 2.0); // r1 = 3.0
        ops.memoriaAniadir(); // mem += ur (3.0)
        assertEquals(r1, ops.memoriaObtener(), 1e-9, "La memoria acumuladora debe contener el valor del último resultado ene ste caso.");

        // otra operación y añadir de nuevo
        double r2 = ops.multiplicar(5.0, 2.0); // r2 = 10.0
        ops.memoriaAniadir(); // mem = 3.0 + 10.0 = 13.0
        assertEquals(r1 + r2, ops.memoriaObtener(), 1e-9, "La memoria acumuladora debe contener la suma del valor anterior y el último resultado en este caso.");

        // limpiar memoria
        ops.memoriaLimpiar();
        assertEquals(0.0, ops.memoriaObtener(), 1e-9, "La memoria acumuladora debe contener el valor 0 despúes de ser limpiada");
    }
}
