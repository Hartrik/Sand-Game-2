
// V následujících ukázkách používáme třídu Elements, která poskytuje statické
// metody pro práci s elementy.


// klasický for each
// poznámka: canvas implementuje rozhraní Iterable<Element>

var solids = 0;

canvas.forEach(function(element) {
    if (Elements.isSolid(element))
        solids++;
});

print("Našli jsme " + solids + " pevných elementů.");


// použití Stream API

var fluid = canvas.stream()
                  .filter(function(e) { return Elements.isFluid(e) })
                  .count();

print("Našli jsme " + fluid + " kapalných elementů.");


// Můžeme také použít funkcionální verzi for each, ale nesmíme zapomenout
// na imutabilitu primitivních datových typů.

canvas.forEach(function(element) {
    // ...
});

// včetně pozice
canvas.forEach(function(element, x, y) {
    // ...
});

// pouze pozice
canvas.forEachPoint(function(x, y) {
    // ...
});


// Stejným způsobem lze iterovat i určitou oblast vymezenou
// libovolným tvarem.

var circle = ToolFactory.circle(30);

                               // x   y  (volitelné)
var region = canvas.take(circle, 10, 10);

region.stream()  // ...
