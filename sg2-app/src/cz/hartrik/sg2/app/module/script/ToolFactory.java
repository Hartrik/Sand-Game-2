package cz.hartrik.sg2.app.module.script;

import cz.hartrik.common.Point;
import cz.hartrik.sg2.app.extension.canvas.ANodeCursor;
import cz.hartrik.sg2.app.extension.canvas.CanvasWithCursor;
import cz.hartrik.sg2.app.extension.canvas.CircleCursor;
import cz.hartrik.sg2.app.extension.canvas.CircleCursorCentered;
import cz.hartrik.sg2.app.extension.canvas.Cursor;
import cz.hartrik.sg2.app.extension.canvas.Cursorable;
import cz.hartrik.sg2.app.extension.canvas.RectangleCursor;
import cz.hartrik.sg2.app.extension.canvas.RectangleCursorCentered;
import cz.hartrik.sg2.app.extension.canvas.PolygonCursor;
import cz.hartrik.sg2.app.extension.canvas.PolygonCursorCentered;
import cz.hartrik.sg2.tool.CenteredCircle;
import cz.hartrik.sg2.tool.CenteredRectangle;
import cz.hartrik.sg2.tool.CenteredTriangle;
import cz.hartrik.sg2.tool.Circle;
import cz.hartrik.sg2.tool.Line;
import cz.hartrik.sg2.tool.Randomizer;
import cz.hartrik.sg2.tool.Rectangle;
import cz.hartrik.sg2.tool.Shape;
import cz.hartrik.sg2.tool.Tool;
import cz.hartrik.sg2.tool.Triangle;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * @version 2015-04-07
 * @author Patrik Harag
 */
public class ToolFactory {

    private static class ToolFactoryHolder {
        private static final ToolFactory INSTANCE = new ToolFactory();
    }
    
    private ToolFactory() {}
    
    public static ToolFactory getInstance() {
        return ToolFactoryHolder.INSTANCE;
    }
    
    // obdélník
    
    public Rectangle rectangle(int width, int height) {
        class CursorableRectangle extends Rectangle implements Cursorable {
            
            public CursorableRectangle(int width, int height) {
                super(width, height);
            }

            @Override
            public Cursor createCursor(CanvasWithCursor canvas) {
                return new RectangleCursor(canvas, width, height);
            }
        }
        
        return new CursorableRectangle(width, height);
    }
    
    public Rectangle rectangle(int x1, int y1, int x2, int y2) {
        return rectangle(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }
    
    public Rectangle rectangle(Point p1, Point p2) {
        return rectangle(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    
    public Rectangle square(int side) {
        return rectangle(side, side);
    }
    
    // obdélník - centrovaný
    
    public Rectangle centeredRectangle(int width, int height) {
        class CursorableRectangle extends CenteredRectangle implements Cursorable {
            
            public CursorableRectangle(int width, int height) {
                super(width, height);
            }

            @Override
            public Cursor createCursor(CanvasWithCursor canvas) {
                return new RectangleCursorCentered(canvas, width, height);
            }
        }
        
        return new CursorableRectangle(width, height);
    }
    
    public Rectangle centeredRectangle(int x1, int y1, int x2, int y2) {
        return centeredRectangle(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }
    
    public Rectangle centeredRectangle(Point p1, Point p2) {
        return centeredRectangle(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    
    public Rectangle centeredSquare(int side) {
        return centeredRectangle(side, side);
    }
    
    // kruh
    
    public Circle circle(int radius) {
        class CursorableCircle extends Circle implements Cursorable {
            
            public CursorableCircle(int radius) {
                super(radius);
            }

            @Override
            public Cursor createCursor(CanvasWithCursor canvas) {
                return new CircleCursor(canvas, radius);
            }
        }
        
        return new CursorableCircle(radius);
    }
    
    // kruh - centerovaný
    
    public Circle centeredCircle(int radius) {
        class CursorableCircle extends CenteredCircle implements Cursorable {
            
            public CursorableCircle(int radius) {
                super(radius);
            }

            @Override
            public Cursor createCursor(CanvasWithCursor canvas) {
                return new CircleCursorCentered(canvas, radius);
            }
        }
        
        return new CursorableCircle(radius);
    }
    
    // trojúhelník
    
    public Triangle triangle(Point a, Point b, Point c) {
        class CursorableTriangle extends Triangle implements Cursorable {

            public CursorableTriangle(Point a, Point b, Point c) {
                super(a, b, c);
            }

            @Override
            public Cursor createCursor(CanvasWithCursor canvas) {
                return new PolygonCursor(canvas, a, b, c);
            }
        }
        
        return new CursorableTriangle(a, b, c);
    }
    
    public Triangle triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        return triangle(Point.of(x1, y1), Point.of(x2, y2), Point.of(x3, y3));
    }
    
    public Triangle triangle(double a, double b, double c) {
        Point[] points = Triangle.countPoints(a, b, c, n -> (int) Math.round(n));
        return triangle(points[0], points[1], points[2]);
    }
    
    // trojúhelník - centerovaný
    
    public Triangle centeredTriangle(Point a, Point b, Point c) {
        class CursorableTriangle extends CenteredTriangle implements Cursorable {

            public CursorableTriangle(Point a, Point b, Point c) {
                super(a, b, c);
            }

            @Override
            public Cursor createCursor(CanvasWithCursor canvas) {
                return new PolygonCursorCentered(canvas, a, b, c);
            }
        }
        
        return new CursorableTriangle(a, b, c);
    }
    
    public Triangle centeredTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        return centeredTriangle(Point.of(x1, y1), Point.of(x2, y2), Point.of(x3, y3));
    }
    
    public Triangle centeredTriangle(double a, double b, double c) {
        Point[] points = Triangle.countPoints(a, b, c, n -> (int) Math.round(n));
        return centeredTriangle(points[0], points[1], points[2]);
    }
    
    // line
    
    public Line line(int x1, int y1, int x2, int y2) {
        return new Line(x1, y1, x2, y2);
    }
    
    public Line line(Point p1, Point p2) {
        return new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    
    // randomizer
    
    public Tool randomizer(Shape shape) {
        class CursorableRandomizer extends Randomizer implements Cursorable {
            
            public CursorableRandomizer(Shape shape) {
                super(shape);
            }

            @Override
            public Cursor createCursor(CanvasWithCursor canvas) {
                Cursorable cursorable = (Cursorable) getShape();
                Cursor cursor = cursorable.createCursor(canvas);
                if (cursor instanceof ANodeCursor) {
                    Node node = ((ANodeCursor) cursor).getCursor();
                    if (node instanceof javafx.scene.shape.Shape)
                        ((javafx.scene.shape.Shape) node)
                                .setFill(new Color(1, 0.5, 0, 0.3));
                }
                return cursor;
            }
        }

        return (shape instanceof Cursorable)
                ? new CursorableRandomizer(shape)
                : new Randomizer(shape);
    }
    
}