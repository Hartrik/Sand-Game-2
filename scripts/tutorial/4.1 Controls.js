
// Getting primary and secondary brush

var pBrush = controls.getPrimaryBrush();
var sBrush = controls.getSecondaryBrush();

// Setting brushes (swap)

controls.setPrimaryBrush(sBrush);
controls.setSecondaryBrush(pBrush);


// Getting tools
// - usually the same for primary and secondary brush

var pTool = controls.getPrimaryTool();
var sTool = controls.getSecondaryTool();


// Example of setting a different tool

var newTool = ToolFactory.centeredTriangle(15, 15, 15);

controls.setPrimaryTool(newTool);
controls.setSecondaryTool(newTool);
