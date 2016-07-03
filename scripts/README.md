Složka obsahuje ukázkové scripty ke hře Sand Game 2 verze <b>2.03 Beta</b>.

## Popis API

### Globální proměnné, třídy

Název            | Popis
---------------- | ------------------------------
`canvas`         | Umožňuje manipulaci s plátnem
`brushManager`   | Správce "štětců"
`serviceManager` | Spravuje dostupné služby
`controls`       | Spravuje vybrané štětce a nástroje
`ToolFactory`    | Poskytuje statické metody pro tvorbu nástrojů, zejména tvarů
`Turtle`         | Poskytuje statické metody pro tvorbu instancí kreslítka želví grafiky

#### Canvas
Návratová hodnota  | Název metody
------------------ | ---------------
`int`      | `getWidth()`
`int`      | `getHeight()`
`boolean`  | `valid(x, y)`
`Element`  | `getBackground()`
`Element`  | `get(x, y)`
`void`     | `set(x, y, element)`
`void`     | `set(x, y, brush)`
`float`    | `getTemperature(x, y)`
`void`     | `setTemperature(x, y, temp)`
`Tools`    | `getTools()`
`Iterator` | `iterator()`
`Stream`   | `stream()`
`Stream`   | `streamLabeled()`
`Stream`   | `streamPoint()`
`void`     | `forEach(consumer)`
`void`     | `forEachPoint(consumer)`
`Region`   | `take(shape)`

#### Tools
Instanci objektu lze získat následovně: `canvas.getTools()` nebo `canvas.take(shape).getTools()`

Návratová hodnota  | Název metody
------------------ | ----------------
`void` | `clear()`
`void` | `fill(element)`
`void` | `fill(brush)`
`void` | `fill(brush, controls)`
`void` | `replace(brush1, brush2)`
`void` | `replaceAll(predicate, brush)`

### Globální metody

Název           | Popis
--------------- | ----------------------------------------------------------------
`brush(name)`   | Vrátí štětec podle jména, odpovídá `brushManager.getBrush(name)`
`brush(id)`     | Vrátí štětec podle ID, odpovídá `brushManager.getBrush(id)`
`service(name)` | Zavolá službu, odpovídá `serviceManager.run(name)`

*Tento popis API zatím není úplný, chybí zde některé třídy.*
