
var tools = canvas.getTools();
tools.fill(brush("water"));

/*
 * Contains following methods:
 * 
 * clear()
 * fill(element)
 * fill(brush)
 * fill(brush, controls)
 * replace(brush1, brush2)
 * replaceAll(predicate, brush)
 */


// Getting tools for a certain area defined by a shape

var circle = ToolFactory.circle(30);  // radius

var region1 = canvas.take(circle);
region1.getTools().fill(brush("wall"));

var region2 = canvas.take(circle, 70, 20);
region2.getTools().fill(brush("wall"));
