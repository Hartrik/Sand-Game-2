
// Iterating over canvas using functional for each

var solids = 0;

canvas.forEach(function(element) {
    if (Elements.isSolid(element))
        solids++;
});

print("Solid elements: " + solids);


// Including positions...
canvas.forEach(function(element, x, y) {
    // ...
});


// Only positions...
canvas.forEachPoint(function(x, y) {
    // ...
});


// Using Stream API (Java 8)

var fluid = canvas.stream()
                  .filter(function(e) { return Elements.isFluid(e) })
                  .count();

print("Fluid elements: " + fluid);


// Iterating over a certain area defined by a shape

var circle = ToolFactory.circle(30);

                               // x   y
var region = canvas.take(circle, 10, 10);
// region provides same methods as above...
