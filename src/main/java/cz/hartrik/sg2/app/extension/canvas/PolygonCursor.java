package cz.hartrik.sg2.app.extension.canvas;

import cz.hartrik.common.Checker;
import cz.hartrik.common.Point;
import cz.hartrik.common.Streams;
import cz.hartrik.sg2.tool.Triangle;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Kurzor pro polygon.
 *
 * @version 2015-03-20
 * @author Patrik Harag
 */
public class PolygonCursor extends ANodeCursor<Polygon> {

    private final Point[] points;

    public PolygonCursor(CanvasWithCursor canvas, Point... points) {
        super(canvas, createCursor(
                Checker.checkArgument(points, points.length > 2)));

        this.points = Triangle.trim(points);
    }

    private static Polygon createCursor(Point... points) {
        Polygon polygon = new Polygon(toDoubleArray(points));
        polygon.setFill(new Color(0, 0, 0, 0.3));
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(0.5);
        return polygon;
    }

    private static double[] toDoubleArray(Point... points) {
        return Streams.stream(points)
                .flatMapToInt(p -> IntStream.of(p.toIntArray()))
                .asDoubleStream()
                .toArray();
    }

    // Cursor

    @Override
    public void onMove(double mX, double mY) {
        final double x = mX + canvas.xLocation;
        final double y = mY + canvas.yLocation;

        // pravá, dolní
        if (x < canvas.xLocation || y < canvas.yLocation
                || mX > canvas.fxcanvas.getWidth()
                || mY > canvas.fxcanvas.getHeight()) {

            cursor.setVisible(false);

        } else {
            cursor.getPoints().setAll(countPoints(x, y, points));
            cursor.setVisible(true);
        }
    }

    protected Double[] countPoints(double x, double y, Point[] points) {
        return Stream.of(points)
                .flatMapToDouble((p) -> DoubleStream.of(
                        Math.floor(x) + p.getX() + .5,
                        Math.floor(y) + p.getY() + .5))
                .boxed()
                .toArray(Double[]::new);
    }

    public int countWidth() {
        return Stream.of(points).mapToInt(Point::getX).max().getAsInt();
    }

    public int countHeight() {
        return Stream.of(points).mapToInt(Point::getY).max().getAsInt();
    }

    public Point[] getPoints() {
        return points;
    }

}