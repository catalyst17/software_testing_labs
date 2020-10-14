package func;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseTrigonometryTest {
    private static final double DELTA = 1e-3;
    private static final double EPSILON = 1e-6;
    private static BaseTrigonometry base;

    @BeforeAll
    static void setUp() {
        base = new BaseTrigonometry();
    }

    @Test
    void testMinusTwoPI() {
        assertEquals(Math.sin(-2*Math.PI), base.sin(-2*Math.PI, EPSILON), DELTA);
    }

    @Test
    void testMinusPI() {
        assertEquals(Math.sin(-Math.PI), base.sin(-Math.PI, EPSILON), DELTA);
    }
    
    @Test
    void testIncreaseStart() {
        assertEquals(Math.sin(-2.8), base.sin(-2.8, EPSILON), DELTA);
    }

    @Test
    void testMonoIncrease() {
        assertEquals(Math.sin(-1.3), base.sin(-1.3, EPSILON), DELTA);
    }

    @Test
    void testIncreaseEnd() {
        assertEquals(Math.sin(-0.3), base.sin(-0.3, EPSILON), DELTA);
    }

    @Test
    void testZero() {
        assertEquals(Math.sin(0), base.sin(0., EPSILON), DELTA);
    }
    
    @Test
    void testDecreaseStart() {
        assertEquals(Math.sin(0.2), base.sin(0.2, EPSILON), DELTA);
    }

    @Test
    void testMonoDecrease() {
        assertEquals(Math.sin(1.8), base.sin(1.8, EPSILON), DELTA);
    }

    @Test
    void testDecreaseEnd() {
        assertEquals(Math.sin(2.9), base.sin(2.9, EPSILON), DELTA);
    }

    @Test
    void testPI() {
        assertEquals(Math.sin(Math.PI), base.sin(Math.PI, EPSILON), DELTA);
    }
    
    @Test
    void testOnePointHalfPI() {
        assertEquals(Math.sin(3*Math.PI/2), base.sin(3*Math.PI/2, EPSILON), DELTA);
    }
}