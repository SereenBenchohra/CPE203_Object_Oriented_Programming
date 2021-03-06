import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static edu.calpoly.testy.Assert.assertEquals;
import static edu.calpoly.testy.Assert.assertTrue;
import static edu.calpoly.testy.Assert.fail;
import edu.calpoly.testy.Testy;

public class TestCases
{
    public static final double DELTA = 0.00001;

    /*
     * This test is just to get you started.
     */
    private void testGetX()
    {
        assertEquals(1.0, new Point(1.0, 2.0).getX(), DELTA);
    }
    
    private void testGetY()
    {
	assertEquals(2.0, new Point(1.0, 2.0).getY(),DELTA);
    }
    private void testGetRadius()
    {
	assertEquals(Math.sqrt(50.0), new Point(5.0, 5.0).getRadius(),DELTA);
    }
    
    private void testGetAngle()
    {
	assertEquals((0.25*Math.PI), new Point(6.0, 6.0).getAngle(),DELTA);
    }

    private void testRotate90()
    {
	assertEquals(-2.0, new Point(1.0, 2.0).rotate90().getX(),DELTA);
	assertEquals(1.0, new Point(1.0, 2.0).rotate90().getY(),DELTA);
    }
    /*
     * The tests below here are to verify the basic requirements regarding
     * the class and method names of your class.  These are to remain unchanged.
     */

    private void testImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getX",
            "getY",
            "getRadius",
            "getAngle",
            "rotate90"
            );

        final List<Class> expectedMethodReturns = Arrays.asList(
            double.class,
            double.class,
            double.class,
            double.class,
            Point.class
            );

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0],
            new Class[0],
            new Class[0],
            new Class[0],
            new Class[0]
            );

        verifyImplSpecifics(Point.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    private void verifyImplSpecifics(
        final Class<?> clazz,
        final List<String> expectedMethodNames,
        final List<Class> expectedMethodReturns,
        final List<Class[]> expectedMethodParameters)
        throws NoSuchMethodException
    {
        assertEquals("Unexpected number of public fields",
            0, Point.class.getFields().length);

        final List<Method> publicMethods = Arrays.stream(
            clazz.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(m ->    !m.getName().equals("equals")
                             && !m.getName().equals("toString"))
                .collect(Collectors.toList());

        assertEquals("Unexpected number of public methods",
            expectedMethodNames.size(), publicMethods.size());

        assertTrue("Invalid test configuration",
            expectedMethodNames.size() == expectedMethodReturns.size());
        assertTrue("Invalid test configuration",
            expectedMethodNames.size() == expectedMethodParameters.size());

        for (int i = 0; i < expectedMethodNames.size(); i++)
        {
            Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
                expectedMethodParameters.get(i));
            assertEquals(expectedMethodReturns.get(i), method.getReturnType());
        }

        // verify that fields are final
        final List<Field> nonFinalFields = Arrays.stream(
            clazz.getDeclaredFields())
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
                .collect(Collectors.toList());

        assertEquals("Unexpected non-final fields", 0, nonFinalFields.size());
    }


    public void runTests() 
    {
        Testy.run(
                () -> testGetX(),
                () -> testImplSpecifics(),
		() -> testGetY(),
		() -> testGetRadius(),
		() -> testGetAngle(),
		() -> testRotate90()

        );
    }
}
