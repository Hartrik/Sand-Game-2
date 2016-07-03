# Sand Game 2

**Sand Game 2** je tvořivá hra typu *falling-sand*, ve které máte k dispozici elementy a prázdné plátno, a je jen na Vás, jak si s nimi pohrajete.

![preview](/preview.png)

## Ovládání

### Nanášení elementů

Jednotlivé elementy (popř. efekty ovlivňující elementy) jsou na plátno nanášeny pomocí štětců. Štětce jsou vybírány příslušným tlačítkem v seznamu po levé straně plátna. Tvar a rozměry štětce je možné nastavit v *menu > nástroje*.

- **levé tlačítko myši** – aplikuje primární štětec
- **pravé tlačítko myši** – aplikuje sekundární štětec
- **shift** – současně s levým nebo pravým tlačítkem myši nakreslí přímku
- **ctrl** – současně s levým nebo pravým tlačítkem myši nakreslí obdélník
- **alt** – zabraňuje překreslování stávajících elementů; funguje i v kombinaci se **shift** nebo **ctrl** (alt gr)
- **prostřední tlačítko myši** – funkce kapátka – na levé tlačítko myši vybere štětec, kterým byl nanesen element na plátně (je-li k dispozici)
- **prostřední tlačítko myši + shift** nebo **ctrl** – plechovka

### Šablony

Několik připravených šablon je v *menu > nástroje*. Levým tlačítkem se vybraná šablona umístí, pravým se proces umístění zruší.

Stejně jako šablony lze vkládat i uložené pozice.

### Další nástroje

Různé nástroje pro úpravu plátna jsou k nalezení v *menu > úpravy*. Často používané nástroje mají přidělené klávesové zkratky.


## Ukládání a načítání

Plátno je možné uložit jako:

- **soubor s příponou .sgs** – ukládání je řešeno formou serializace. Toto řešení s sebou ovšem nese nevýhody, jako větší paměťovou náročnost a občasnou nekompatibilitu mezi jednotlivými verzemi. Výhodou je, že je plátno zachováno přesně ve stavu, v jakém bylo uloženo. 

- **soubor s příponou .sgb** – ukládají se pouze ID štětců, kterými byly jednotlivé elementy naneseny – vzniká tak jakási šablona, podle které se elementy příště nanesou. Kompatibilita s dalšími verzemi je zaručena.

## Scriptování

Ke scriptování může být použit jednoduchý vestavěný editor. Tlačítko vyvolávající dialog s editorem naleznete v toolbaru.

Druhou možností je vytvoření složky <code>scripts</code> uvnitř složky, ve které byl program spuštěn.
V případě jejího nalezení program zobrazí celý její obsah v menu, odkud mohou být jednotlivé scripty pohodlně spouštěny.

Nechte se inspirovat [ukázkovými scripty](/scripts).

## Výkon

Plátno je rozdělené do tzv. chunků – čtvercových oblastí o rozměrech obvykle 20×20 bodů. Ty umožňují rozlišit oblasti, ve kterých se nic nehýbe / neděje a tyto oblasti jsou poté uspány a tak dochází k šetření výkonu. Cílem je tedy mít **co nejméně aktivních chunků**.

Díky tomuto rozdělení je možné mít např. obrovskou plochu písku, která v klidovém stavu nevyžaduje téměř žádný výkon.

Dalším faktorem ovlivňujícím výkon jsou **rozměry plátna**. Pokud budete provádět nějaké složitější operace nebo máte slabší počítač, tak je nezapomeňte **zmenšit**.

**Při dodržení těchto zásad by hra měla být hratelná i na slabších PC.**

## Dodatečné informace

Hra se neinstaluje, ani v počítači samovolně nezanechává žádné soubory.

Ke spuštění je vyžadovaná **Java 8u40**.
