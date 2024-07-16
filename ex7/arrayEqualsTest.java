import org.junit.Test;
import static org.junit.Assert.*;

public clsss arrayEqualsTest {
    public boolean deepEquals (Object[] arr1, Object[] arr2) {
        if (arr1 == null && arr2 == null) {
            return true;
        }

        if (arr1 == null || arr2 == null) {
            return false;
        }

        if (arr1,length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            Object elme1 = arr1[i];
            Object elme2 = arr2[i];

            if (elem1 instanceof Object[] && elme2 instanceof Object[]) {
                if (!deepEquals((Object[]) elme1, (Object[]) elem2)) {
                    return false;
                }
            }
            else {
                if (elem1 == null ? elem2 != null : !elem1.equals(elem2)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void testDeeoEquals() {
        Object[] array1 = {1, 2, new Object[]{3, 4, new Object[]{5, 6}}, 7};
        Object[] array2 = {1, 2, new Object[]{3, 4, new Object[]{5, 6}}, 7};
        Object[] array3 = {1, 2, new Object[]{3, 4, new Object[]{5, 8}}, 7};
        Object[] array4 = null;
        Object[] array5 = null;

        // 测试相等的数组
        assertTrue(deepEquals(array1, array2));

        // 测试不相等的数组
        assertFalse(deepEquals(array1, array3));

        // 测试两个都是null的数组
        assertTrue(deepEquals(array4, array5));

        // 测试一个为null另一个不为null的数组
        assertFalse(deepEquals(array1, array4));
    }
}