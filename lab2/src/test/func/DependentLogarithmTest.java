package func;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class DependentLogarithmTest {
    private static final double DELTA = 1e-3;
    private static final double EPSILON = 1e-6;
    private static BaseLogarithm base;
    private static DependentLogarithm dependent;

    @BeforeAll
    static void setUp() {
        base = mock(BaseLogarithm.class);
        when(base.ln(0., EPSILON)).thenReturn(Double.NaN);
        when(base.ln(0.4, EPSILON)).thenReturn(Math.log(0.4));
        when(base.ln(1., EPSILON)).thenReturn(Math.log(1));
        when(base.ln(2., EPSILON)).thenReturn(Math.log(2));
        when(base.ln(4., EPSILON)).thenReturn(Math.log(4));
        when(base.ln(10., EPSILON)).thenReturn(Math.log(10));

        dependent = new DependentLogarithm(base);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.4, 1., 2., 10.})
    void testLogTen(double x){
        assertEquals(Math.log10(x), dependent.log(x, 10, EPSILON), DELTA);
    }

    @Test
    void testLogTwo(){
        assertEquals(0, dependent.log(1., 2, EPSILON), DELTA);
        assertEquals(1, dependent.log(2., 2, EPSILON), DELTA);
        assertEquals(2, dependent.log(4., 2, EPSILON), DELTA);
    }

    @Test
    void testLogInvalidValues(){
        assertEquals(Double.NaN, dependent.log(2., 1, EPSILON), DELTA);
    }
}
