
var tools = canvas.getTools();

/* Obsahuje následující metody:
 * 
 * clear()
 * fill(element)
 * fill(brush)
 * fill(brush, controls)
 * replace(brush1, brush2)
 * replaceAll(predicate, brush)
 */


// instanci Tools lze získat i na určitou oblast:

var circle = ToolFactory.circle(30);
var region = canvas.take(circle);

region.getTools().fill(brush("dřevo"));