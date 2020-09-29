import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SeriesExpansionTest {
    private static final double DELTA = 1e-6;
    private static final double EPSILON = 1e-3;

    @Test
    void testMinusTwoPI() {
        assertEquals(Math.cos(-2*Math.PI), SeriesExpansion.expanse(-2*Math.PI), DELTA);
    }

    @Test
    void testMinusPIMinusEpsilon() {
        assertEquals(Math.cos(-Math.PI-EPSILON), SeriesExpansion.expanse(-Math.PI-EPSILON), DELTA);
    }

    @Test
    void testMinusPI() {
        assertEquals(Math.cos(-Math.PI), SeriesExpansion.expanse(-Math.PI), DELTA);
    }

    @Test
    void testMinusPIPlusEpsilon() {
        assertEquals(Math.cos(-Math.PI+EPSILON), SeriesExpansion.expanse(-Math.PI+EPSILON), DELTA);
    }

    @Test
    void testIncreaseStart() {
        assertEquals(Math.cos(-2.8), SeriesExpansion.expanse(-2.8), DELTA);
    }

    @Test
    void testMonoIncrease() {
        assertEquals(Math.cos(-1.3), SeriesExpansion.expanse(-1.3), DELTA);
    }

    @Test
    void testIncreaseEnd() {
        assertEquals(Math.cos(-0.3), SeriesExpansion.expanse(-0.3), DELTA);
    }

    @Test
    void testZeroMinusEpsilon() {
        assertEquals(Math.cos(-EPSILON), SeriesExpansion.expanse(-EPSILON), DELTA);
    }

    @Test
    void testZero() {
        assertEquals(Math.cos(0), SeriesExpansion.expanse(0), DELTA);
    }

    @Test
    void testZeroPlusEpsilon() {
        assertEquals(Math.cos(EPSILON), SeriesExpansion.expanse(EPSILON), DELTA);
    }

    @Test
    void testDecreaseStart() {
        assertEquals(Math.cos(0.2), SeriesExpansion.expanse(0.2), DELTA);
    }

    @Test
    void testMonoDecrease() {
        assertEquals(Math.cos(1.8), SeriesExpansion.expanse(1.8), DELTA);
    }

    @Test
    void testDecreaseEnd() {
        assertEquals(Math.cos(2.9), SeriesExpansion.expanse(2.9), DELTA);
    }


    @Test
    void testPIMinusEpsilon() {
        assertEquals(Math.cos(Math.PI-EPSILON), SeriesExpansion.expanse(Math.PI-EPSILON), DELTA);
    }

    @Test
    void testPI() {
        assertEquals(Math.cos(Math.PI), SeriesExpansion.expanse(Math.PI), DELTA);
    }

    @Test
    void testPIPlusEpsilon() {
        assertEquals(Math.cos(Math.PI+EPSILON), SeriesExpansion.expanse(Math.PI+EPSILON), DELTA);
    }

    @Test
    void testOnePointHalfPI() {
        assertEquals(Math.cos(3*Math.PI/2), SeriesExpansion.expanse(3*Math.PI/2), DELTA);
    }
}