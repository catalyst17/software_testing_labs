import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class HeapTest {
    @Test
    void givenNotEmptyIntegerList_shouldPopTheLeastValue() {
        List<Integer> elements = Arrays.asList(9, -3, 5, 1, 0, -4, 2, 7);
        Heap<Integer> heap = Heap.of(elements);

        assertEquals(-4, heap.pop());
    }

    @Test
    void givenNotEmptyIntegerList_checkParentOf9() {
        List<Integer> elements = Arrays.asList(9, -3, 5, 1, 0, -4, 2, 7);
        Heap<Integer> heap = Heap.of(elements);

        assertEquals(7, heap.elementAt(heap.parentIndex(elements.size()-1)));
    }

    @Test
    void givenNotEmptyIntegerList_checkParentOfRoot() {
        List<Integer> elements = Arrays.asList(9, -3, 5, 1, 0, -4, 2, 7);
        Heap<Integer> heap = Heap.of(elements);

        assertThrows(IndexOutOfBoundsException.class, () -> heap.parentIndex(0));
    }

    @Test
    void givenNotEmptyIntegerList_whenOverPopping_throwsException() {
        List<Integer> elements = Arrays.asList(9, -3);
        Heap<Integer> heap = Heap.of(elements);
        heap.pop();
        heap.pop();

        assertThrows(IllegalStateException.class, () -> heap.pop());
    }

    @Test
    void givenNotEmptyIntegerList_whenAddingGreaterElement_checkNoTraversing() {
        List<Integer> elements = Arrays.asList(9, -3);
        Heap<Integer> heap = Heap.of(elements);
        heap.add(5);

        assertEquals(-3, heap.pop());
    }

    @Test
    void givenNotEmptyIntegerList_whenAddingSmallerElement_checkTraversing() {
        List<Integer> elements = Arrays.asList(9, -3);
        Heap<Integer> heap = Heap.of(elements);
        heap.add(-4);

        assertEquals(-4, heap.pop());
    }

    @Test
    void givenNotEmptyIntegerList_whenPopping_check9StayingLast() {
        List<Integer> elements = Arrays.asList(9, -3, 5, 1, 0, -4, 2, 7);
        Heap<Integer> heap = Heap.of(elements);
        heap.pop();

        assertEquals(9, heap.elementAt(heap.size()-1));
    }

    @Test
    void givenNotEmptyIntegerList_shouldReturnSorted() {
        List<Integer> elements = Arrays.asList(9, -3, 5, 1, 0, -4, 2, 7);
        List<Integer> sortedElements = Heap.sort(elements);

        assertEquals(Arrays.asList(-4, -3, 0, 1, 2, 5, 7, 9), sortedElements);
    }

    @Test
    void givenNotEmptyStringList_shouldReturnSorted() {
        List<String> elements = Arrays.asList("ddd", "ccc", "aaa", "bbb");
        List<String> sortedElements = Heap.sort(elements);

        assertEquals(Arrays.asList("aaa", "bbb", "ccc", "ddd"), sortedElements);
    }

    @Test
    void givenEmptyList_returnsEmptyResult() {
        List<String> elements = Arrays.asList();
        List<String> sortedElements = Heap.sort(elements);

        assertEquals(elements, sortedElements);
    }
}
