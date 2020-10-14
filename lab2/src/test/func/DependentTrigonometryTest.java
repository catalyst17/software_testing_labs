package func;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.lang.Math.PI;
import static org.mockito.Mockito.*;

public class DependentTrigonometryTest {
    private static final double DELTA = 1e-3;
    private static final double EPSILON = 1e-6;
    private static BaseTrigonometry base;
    private static DependentTrigonometry dependent;

    @BeforeAll
    static void setUp() {
        base = mock(BaseTrigonometry.class);
        when(base.sin(0.0, EPSILON)).thenReturn(0.0);
        when(base.sin(Math.PI / 4, EPSILON)).thenReturn(Math.sqrt(2) / 2);
        when(base.sin(Math.PI / 2, EPSILON)).thenReturn(1.0);
        when(base.sin(Math.PI, EPSILON)).thenReturn(0.0);
        when(base.sin(Math.PI * 3 / 4, EPSILON)).thenReturn(Math.sin(Math.PI * 3 / 4));
        when(base.sin(Math.PI * 3 / 8, EPSILON)).thenReturn(Math.sin(Math.PI * 3 / 8));
        when(base.sin(Math.PI * 3 / 2, EPSILON)).thenReturn(-1.0);
        when(base.sin(Math.PI * 5 / 2, EPSILON)).thenReturn(Math.sin(PI * 5 / 2));
        when(base.sin(Math.PI * 5 / 4, EPSILON)).thenReturn(Math.sin(PI * 5 / 4));

        dependent = new DependentTrigonometry(base);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, PI/2, PI, PI*3/2})
    void testCos(double x) {
        assertEquals(Math.cos(x), dependent.cos(x, EPSILON), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, PI, PI*3/4})
    void testTanValidArgs(double x) {
        assertEquals(Math.tan(x), dependent.tan(x, EPSILON), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {PI/2, PI*3/2, PI*5/2})
    void testTanInvalidArgs(double x) {
        assertEquals(Double.NaN, dependent.tan(x, EPSILON), DELTA);
    }
}
