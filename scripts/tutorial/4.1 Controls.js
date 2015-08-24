
// získání štětců

var pBrush = controls.getPrimaryBrush()
var sBrush = controls.getSecondaryBrush()

// nastavení štětců (prohození stávajících)

controls.setPrimaryBrush(sBrush)
controls.setSecondaryBrush(pBrush)


// získání nástrojů
// - obvykle jsou nastaveny stejné nástroje pro obě tlačítka

var pTool = controls.getPrimaryTool()
var sTool = controls.getSecondaryTool()

// nastavení nového štětce

var newTool = ToolFactory.centeredTriangle(15, 15, 15)

controls.setPrimaryTool(newTool)
controls.setSecondaryTool(newTool)