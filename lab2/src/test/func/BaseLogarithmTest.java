package func;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseLogarithmTest {
    private static final double DELTA = 1e-3;
    private static final double EPSILON = 1e-6;
    private static BaseLogarithm base;

    @BeforeAll
    static void setUp() {
        base = new BaseLogarithm();
    }

    @Test
    void testZero(){
        assertThrows(IllegalArgumentException.class, () -> {
            base.ln(0., EPSILON);
        });
    }

    @Test
    void testNearZero(){
        assertEquals(Math.log(0.08), base.ln(0.08, EPSILON), DELTA);
    }

    @Test
    void testHalf(){
        assertEquals(Math.log(0.5), base.ln(0.5, EPSILON), DELTA);
    }

    @Test
    void testOne(){
        assertEquals(Math.log(1), base.ln(1., EPSILON), DELTA);
    }

    @Test
    void testTen(){
        assertEquals(Math.log(10), base.ln(10., EPSILON), DELTA);
    }
}
