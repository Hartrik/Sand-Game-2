// Swaps primary and secondary brush

var primaryBrush = controls.getPrimaryBrush();
var secondaryBrush = controls.getSecondaryBrush();

controls.setPrimaryBrush(secondaryBrush);
controls.setSecondaryBrush(primaryBrush);
