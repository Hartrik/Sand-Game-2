
// ToolFactory provides methods for creating tools - especially shapes

/*
 * square(int side)
 * centeredSquare(int side)
 * 
 * rectangle(int width, int height)
 * rectangle(int x1, int y1, int x2, int y2)
 * rectangle(Point p1, Point p2)
 * centeredRectangle(int width, int height)
 * centeredRectangle(int x1, int y1, int x2, int y2) 
 * centeredRectangle(Point p1, Point p2) 
 * 
 * triangle(double a, double b, double c) 
 * triangle(int x1, int y1, int x2, int y2, int x3, int y3) 
 * triangle(Point a, Point b, Point c) 
 * centeredTriangle(double a, double b, double c) 
 * centeredTriangle(int x1, int y1, int x2, int y2, int x3, int y3) 
 * centeredTriangle(Point a, Point b, Point c) 
 * 
 * circle(int radius)
 * centeredCircle(int radius)
 * 
 * line(int x1, int y1, int x2, int y2)
 * line(Point p1, Point p2)        
 * 
 * randomizer(Shape shape)
 *
 */


// example:

var triangle = ToolFactory.triangle(20, 20, 20);

// ... 