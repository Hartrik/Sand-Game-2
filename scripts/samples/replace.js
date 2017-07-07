// Replaces elements placed using primary brush by elements placed
// using secondary brush

var primaryBrush = controls.getPrimaryBrush();
var secondaryBrush = controls.getSecondaryBrush();

canvas.getTools().replace(primaryBrush, secondaryBrush);
