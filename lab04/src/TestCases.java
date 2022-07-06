import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.awt.Color;

import edu.calpoly.testy.Testy;
import static edu.calpoly.testy.Assert.assertEquals;
import static edu.calpoly.testy.Assert.assertTrue;
import static edu.calpoly.testy.Assert.fail;


public class TestCases
{
    public static final double DELTA = 0.00001;


    // some sample tests but you must write more! see lab write up

    public void testCircleGetArea()
    {
        Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

        assertEquals(101.2839543, c.getArea(), DELTA);
    }

    public void testCircleGetPerimeter()
    {
        Circle c = new Circle(5.678, new Point(2, 3), Color.BLACK);

        assertEquals(35.6759261, c.getPerimeter(), DELTA);
    }

    public void testCircleTrans(){

        Circle c = new Circle(5.00, new Point(2, 3), Color.BLACK);
        Point p = new Point(4.0, 5.0);
        c.translate(2.0,2.0);
        assertEquals(p.x, c.getCenter().x);
        assertEquals(p.y, c.getCenter().y);

    }

    public void testCol(){
        WorkSpace ws = new WorkSpace();
        List<Shape> expected = new LinkedList<>();

        // Have to make sure the same objects go into the WorkSpace as
        // into the expected List since we haven't overriden equals in Circle.
        Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
        Rectangle r = new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK);

        ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLUE));
        ws.add(c1);
        ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
                      Color.RED));
        ws.add(r);

        expected.add(c1);
        expected.add(r);

        // Doesn't matter if the "type" of lists are different (e.g Linked vs
        // Array).  List equals only looks at the objects in the List.
        assertEquals(expected,ws.getShapesByColor(Color.BLACK));

    }

    public void testRectangleGetArea()
    {
        Rectangle r = new Rectangle(3.0,3.0, new Point(0.0,3.0), Color.BLACK);
        assertEquals(9.0, r.getArea(), DELTA);

    }

    public void testRectangleGetPerimeter()
    {
        Rectangle r = new Rectangle(3.5,3.5, new Point(0.0,3.0), Color.BLACK);

        assertEquals(14.0, r.getPerimeter(), DELTA);
    }

    public void testRecTrans(){

        Rectangle r = new Rectangle(3.0,3.0, new Point(0.0,3.0), Color.BLACK);
        Point p = new Point(2.0, 5.0);
        r.translate(2.0,2.0);
        assertEquals(p.x, r.getTopLeft().x);
        assertEquals(p.y, r.getTopLeft().y);

    }


    public void testTriangleGetArea()
    {
        Triangle t = new Triangle(new Point(0.0,0.0), new Point(0.0,3.0), new Point(3.0,0.0), Color.BLACK);
        assertEquals(9.0/2, t.getArea(), DELTA);

    }

    public void testTriangleGetPerimeter()
    {
        Triangle t = new Triangle(new Point(0.0,0.0), new Point(0.0,3.0), new Point(3.0,0.0), Color.BLACK);
        assertEquals(10.242640687119284, t.getPerimeter(), DELTA);
    }

    public void testTriTrans(){

        Triangle t = new Triangle(new Point(0.0,0.0), new Point(0.0,3.0), new Point(3.0,0.0), Color.BLACK);
        Point p1 = new Point(2.0, 2.0);
        Point p2 = new Point(2.0, 5.0);
        Point p3 = new Point(5.0, 2.0);
        t.translate(2.0,2.0);
        assertEquals(p1.x, t.getVertexA().x);
        assertEquals(p1.y, t.getVertexA().y);
        assertEquals(p2.x, t.getVertexB().x);
        assertEquals(p2.y, t.getVertexB().y);
        assertEquals(p3.x, t.getVertexC().x);
        assertEquals(p3.y, t.getVertexC().y);

    }



    public void testPolygonGetArea()
    {
        Point[] p = {new Point(0.0,0.0), new Point(0.0,3.0), new Point(3.0,3.0), new Point(3.0,0.0)};
        

        ConvexPolygon cp = new ConvexPolygon(p, Color.BLACK);
        assertEquals(9.0, cp.getArea(), DELTA);

        Point[] p2 = {new Point(11.0,2.0), new Point(9.0,7.0), new Point(4.0,10.0), new Point(2.0,2.0)};

        ConvexPolygon cp2 = new ConvexPolygon(p2, Color.BLACK);
        assertEquals(45.5, cp2.getArea(), DELTA);

    }

    public void testPolygonGetPerimeter()
    {
        Point[] p = new Point[4];
        p[3] = new Point(0.0,0.0);
        p[2] = new Point(0.0,3.5);
        p[1] = new Point(3.5,3.5);
        p[0] = new Point(3.5,0.0);
    

        ConvexPolygon cp = new ConvexPolygon(p, Color.BLACK);
        assertEquals(14.0, cp.getPerimeter(), DELTA);
    }

    public void testPolygontranslate()
    {
        Point[] p = {new Point(0.0,0.0), new Point(0.0,3.0), new Point(3.0,3.0), new Point(3.0,0.0)};
        Point[] p1 = {new Point(1.0,1.0), new Point(1.0,4.0), new Point(4.0,4.0), new Point(4.0,1.0)};
        ConvexPolygon poly = new ConvexPolygon(p, Color.BLACK);
        poly.translate(1,1);

        assertEquals(poly.getVertex(0).x, p1[0].x);
	assertEquals(poly.getVertex(0).y, p1[0].y);

        assertEquals(poly.getVertex(1).x, p1[1].x);
	assertEquals(poly.getVertex(1).y, p1[1].y);

        assertEquals(poly.getVertex(2).x, p1[2].x);
	assertEquals(poly.getVertex(2).y, p1[2].y);

        assertEquals(poly.getVertex(3).x, p1[3].x);
	assertEquals(poly.getVertex(3).y, p1[3].y);

    }

    public void testWorkSpaceAreaOfAllShapes()
    {
        WorkSpace ws = new WorkSpace();

        ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
        ws.add(new Circle(5.678, new Point(2, 3), Color.BLACK));
        ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
                      Color.BLACK));

        assertEquals(114.2906063, ws.getAreaOfAllShapes(), DELTA);
    }

    public void testWorkSpaceGetCircles()
    {
        WorkSpace ws = new WorkSpace();
        List<Circle> expected = new LinkedList<>();

        // Have to make sure the same objects go into the WorkSpace as
        // into the expected List since we haven't overriden equals in Circle.
        Circle c1 = new Circle(5.678, new Point(2, 3), Color.BLACK);
        Circle c2 = new Circle(1.11, new Point(-5, -3), Color.RED);

        ws.add(new Rectangle(1.234, 5.678, new Point(2, 3), Color.BLACK));
        ws.add(c1);
        ws.add(new Triangle(new Point(0,0), new Point(2,-4), new Point(3, 0),
                      Color.BLACK));
        ws.add(c2);

        expected.add(c1);
        expected.add(c2);

        // Doesn't matter if the "type" of lists are different (e.g Linked vs
        // Array).  List equals only looks at the objects in the List.
        assertEquals(expected, ws.getCircles());
    }

    public void testCircleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getRadius", "setRadius", "getCenter");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            double.class, void.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {double.class, double.class},
            new Class[0], new Class[] {double.class}, new Class[0]);

        verifyImplSpecifics(Circle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    public void testRectangleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getWidth", "setWidth", "getHeight", "setHeight", "getTopLeft");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            double.class, void.class, double.class, void.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {double.class, double.class},
            new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, new Class[0]);

        verifyImplSpecifics(Rectangle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    public void testTriangleImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getVertexA", "getVertexB", "getVertexC");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            Point.class, Point.class, Point.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {double.class, double.class},
            new Class[0], new Class[0], new Class[0]);

        verifyImplSpecifics(Triangle.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    public void testConvexPolygonImplSpecifics()
        throws NoSuchMethodException
    {
        final List<String> expectedMethodNames = Arrays.asList(
            "getColor", "setColor", "getArea", "getPerimeter", "translate",
            "getVertex", "getNumVertices");

        final List<Class> expectedMethodReturns = Arrays.asList(
            Color.class, void.class, double.class, double.class, void.class,
            Point.class, int.class);

        final List<Class[]> expectedMethodParameters = Arrays.asList(
            new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {double.class, double.class},
            new Class[] {int.class}, new Class[0]);

        verifyImplSpecifics(ConvexPolygon.class, expectedMethodNames,
            expectedMethodReturns, expectedMethodParameters);
    }

    private static void verifyImplSpecifics(
        final Class<?> clazz,
        final List<String> expectedMethodNames,
        final List<Class> expectedMethodReturns,
        final List<Class[]> expectedMethodParameters)
        throws NoSuchMethodException
    {
        assertEquals("Unexpected number of public fields",
            0, clazz.getFields().length);

        final List<Method> publicMethods = Arrays.stream(
            clazz.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
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
    }
    

    public void runTests()
    {
        Testy.run(
                () -> testCircleGetArea(),
                () -> testCircleGetPerimeter(),
                () -> testCircleTrans(),
                () -> testCol(),
                () -> testRectangleGetArea(),
                () -> testRectangleGetPerimeter(),
                () -> testRecTrans(),
                () -> testTriangleGetArea(),
                () -> testTriangleGetPerimeter(),
                () -> testTriTrans(),
                () -> testPolygonGetArea(),
                () -> testPolygonGetPerimeter(),
                () -> testPolygontranslate(),
                () -> testWorkSpaceAreaOfAllShapes(),
                () -> testWorkSpaceGetCircles(),
                () -> testCircleImplSpecifics(),
                () -> testRectangleImplSpecifics(),
                () -> testTriangleImplSpecifics(),
                () -> testConvexPolygonImplSpecifics()
                
        );
    }
}


