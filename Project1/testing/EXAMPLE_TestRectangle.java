import org.junit.*;
import org.junit.runners.MethodSorters;

import java.awt.Color;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRectangle {

    private final double TOLERANCE = 0.00001;
    private static final ScoringRule SCORING_RULE = new ScoringRule(TestRectangle.class);

    @Rule
    public ScoringRule scoringTestRule = SCORING_RULE;

    private Rectangle rectangle;
    private final double WIDTH = 10.1;
    private final double HEIGHT = 20.1;

    @Before
    public void setUp() {
        rectangle = new Rectangle(10, 20, WIDTH, HEIGHT);
    }

    @AfterClass
    public static void printScore() {
        System.out.println();
        System.out.print(SCORING_RULE);
        System.out.println();
    }

    @Test
    @WorthPoints(points = 1)
    @Category(category = "Constructor")
    public void TestConstructorOrderX() {
        double expectedX = 1;
        double expectedY = 2;
        double expectedW = 3;
        double expectedH = 4;
        Rectangle rectangle = new Rectangle(expectedX, expectedY, expectedW, expectedH);

        double actualX = rectangle.getXPos();
        assertEquals("X parameter is in a different position", expectedX, actualX, TOLERANCE);
    }

    @Test
    @WorthPoints(points = 1)
    @Category(category = "Constructor")
    public void TestConstructorOrderY() {
        double expectedX = 1;
        double expectedY = 2;
        double expectedW = 3;
        double expectedH = 4;
        Rectangle rectangle = new Rectangle(expectedX, expectedY, expectedW, expectedH);

        double actualY = rectangle.getYPos();
        assertEquals("Y parameter is in a different position", expectedY, actualY, TOLERANCE);
    }

    @Test
    @WorthPoints(points = 1)
    @Category(category = "Constructor")
    public void TestConstructorOrderW() {
        double expectedX = 1;
        double expectedY = 2;
        double expectedW = 3;
        double expectedH = 4;
        Rectangle rectangle = new Rectangle(expectedX, expectedY, expectedW, expectedH);

        double actualW = rectangle.getWidth();
        assertEquals("Width parameter is in a different position", expectedW, actualW, TOLERANCE);
    }

    @Test
    @WorthPoints(points = 1)
    @Category(category = "Constructor")
    public void TestConstructorOrderH() {
        double expectedX = 1;
        double expectedY = 2;
        double expectedW = 3;
        double expectedH = 4;
        Rectangle rectangle = new Rectangle(expectedX, expectedY, expectedW, expectedH);

        double actualH = rectangle.getHeight();
        assertEquals("Height parameter is in a different position", expectedH, actualH, TOLERANCE);
    }

    @Test
    @WorthPoints(points = 2)
    @Category(category = "Constructor")
    public void TestStatic() {
        rectangle.setColor(Color.blue);

        Rectangle other = new Rectangle(0, 0, 0, 0);
        other.setColor(Color.black);

        assertNotEquals("Improper use of static", 0, rectangle.getXPos(), TOLERANCE);
        assertNotEquals("Improper use of static", 0, rectangle.getYPos(), TOLERANCE);
        assertNotEquals("Improper use of static", 0, rectangle.getWidth(), TOLERANCE);
        assertNotEquals("Improper use of static", 0, rectangle.getHeight(), TOLERANCE);
        assertNotEquals("Improper use of static", Color.black, rectangle.getColor());
    }

    @Test
    @WorthPoints(points = 3)
    @Category(category = "Calculations")
    public void TestCalculatePerimeter() {
        double expected = 2*WIDTH + 2*HEIGHT;
        double actual = rectangle.calculatePerimeter();
        assertEquals(expected, actual, TOLERANCE);
    }

    @Test
    @WorthPoints(points = 3)
    @Category(category = "Calculations")
    public void TestCalculateArea() {
        double expected = WIDTH * HEIGHT;
        double actual = rectangle.calculateArea();
        assertEquals(expected, actual, TOLERANCE);
    }

    @Test
    @WorthPoints(points = 3)
    @Category(category = "Calculations")
    public void TestCalculatePerimeterAndAreaChange() {
        double unexpectedPerimeter = rectangle.calculatePerimeter();
        double unexpectedArea = rectangle.calculateArea();

        rectangle.setWidth(2.0);
        rectangle.setHeight(2.0);

        double actualPerimeter = rectangle.calculatePerimeter();
        double actualArea = rectangle.calculateArea();

        assertNotEquals("Did not recalculate after changing width and height", unexpectedPerimeter, actualPerimeter, TOLERANCE);
        assertNotEquals("Did not recalculate after changing width and height", unexpectedArea, actualArea, TOLERANCE);
    }

    @Test
    @WorthPoints(points = 1)
    @Category(category = "Setters and Getters")
    public void TestSetAndColor() {
        Color expected = new Color(200, 100, 150);
        rectangle.setColor(expected);
        Color actual = rectangle.getColor();
        assertEquals(expected, actual);
    }

    @Test
    @WorthPoints(points = 1)
    @Category(category = "Setters and Getters")
    public void TestSetAndGetPos() {
        double expectedX = 10.5;
        double expectedY = 20.5;
        rectangle.setPos(expectedX, expectedY);

        double actualX = rectangle.getXPos();
        double actualY = rectangle.getYPos();
        assertEquals(expectedX, actualX, TOLERANCE);
        assertEquals(expectedY, actualY, TOLERANCE);
    }

    @Test
    @WorthPoints(points = 1)
    @Category(category = "Setters and Getters")
    public void TestSetAndGetHeight() {
        double expected = 10.5;
        rectangle.setHeight(expected);

        double actual = rectangle.getHeight();
        assertEquals(expected, actual, TOLERANCE);
    }

    @Test
    @WorthPoints(points = 1)
    @Category(category = "Setters and Getters")
    public void TestSetAndGetWidth() {
        double expected = 10.5;
        rectangle.setWidth(expected);

        double actual = rectangle.getWidth();
        assertEquals(expected, actual, TOLERANCE);
    }
}
