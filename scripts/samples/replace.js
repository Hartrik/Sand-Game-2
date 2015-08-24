// nahradí všechny elemeny nanesené primárním štětcem elementy
// sekundárního štětce

var primaryBrush = controls.getPrimaryBrush()
var secondaryBrush = controls.getSecondaryBrush()

canvas.getTools().replace(primaryBrush, secondaryBrush)
