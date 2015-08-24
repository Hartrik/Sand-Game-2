
// zjištění rozměrů plátna

var width  = canvas.getWidth();
var height = canvas.getHeight();

var area = width * height;

print("Na plátně je celkem " + area + " elementů!");


// P.S. pokud chceme zjistit, jestli se určitá pozice nachází na
//      plátně, můžeme použít metodu valid(x, y)
//
//      např.: canvas.valid(-10, 50)  -> vrátí false