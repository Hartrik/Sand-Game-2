
// Determining canvas dimensions

var width  = canvas.getWidth();
var height = canvas.getHeight();

var area = width * height;

print("In the canvas there are " + area + " elements!");


// You can use canvas#valid to check if a position is in the bounds

canvas.valid(10, 50);   // -> true
canvas.valid(-10, 50);  // -> false (out of the bounds)


// Getting elements from the canvas

var element_1 = canvas.get(0, 0);                      // left upper corner
var element_2 = canvas.get(canvas.getWidth() - 1, 0);  // right upper corner


// Swapping these elements

canvas.set(0, 0, element_2);
canvas.set(canvas.getWidth() - 1, 0, element_1);


// There cannot be nulls inside canvas - everything is an element.
// To delete an element you need to replace it with the air element.

var bg = canvas.getBackground();
canvas.set(1, 0, bg);


// Setting elements using brushes

var brush = brush("sand");
canvas.set(2, 0, brush);

// More about these later...
