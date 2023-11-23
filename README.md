
[prev-ui]: .github/preview-ui.png
[prev-creations]: .github/preview-creations.png
[prev-scripting]: .github/preview-scripting.png
[prev-templates]: .github/preview-templates.png
[prev-chunks]: .github/preview-chunks.png
[scripts]: /scripts
[sand-game-js]: https://github.com/Hartrik/sand-game-js

:bulb: *Note: there is a successor called [Sand Game JS][sand-game-js]. It's not as feature-rich, but it's playable in the browser.*

# Sand Game 2
**Sand Game 2** is a *falling sand* game that presents an empty canvas and a wide range of various elements.
The player can create landscapes out of dirt, sand and water, then plant grass and watch it grow.
Or build metal structures and then melt them with thermite, or perhaps set fire to an oil pond.
Or mix water and salt to make brine – that can be distilled back to pure salt.
And many other possibilities.

**Features**
- many elements and tools
- gravity
- fire and temperature
- in-game scripting
- loading / saving
- portable, multi-platform, with open sources

**Preview**

![Preview - GUI][prev-ui]

![Preview - creations][prev-creations]

## Controls

### Inserting elements

Elements are inserted into the canvas using brushes. You can select primary
(left mouse button) and secondary (right mouse button) brush.
Shape and size can be changed in `menu > tools`.

- **left mouse button (*LMB*)** – drawing with the primary brush
- **right mouse button (*RMB*)** – drawing with the secondary brush
- **shift** + ***LMB*** or ***RMB*** – drawing a line
- **ctrl** + ***LMB*** or ***RMB*** – drawing a rectangle
- **alt** – preventing from rewriting elements (while holding)
- **middle mouse button (*MMB*)** – picker tool
- **MMB + shift** or **ctrl** – can tool

### Templates

Several prepared templates can be found in `menus > tools`.
Insertion can be cancelled using *RMB*.

Saved canvases can also be inserted as templates.

![Preview - templates][prev-templates]

### Editing tools

Canvas editing tools (fill, clear, flip, rotate...) can be found in `menus > edit`.

### Debugging

Press F12 to show the console.

## Saving and loading

There are two file formats:

- **`*.sgs`** – saving is done by object serialization.
  After loading, the canvas is exactly the same as it was.
  However, this solution has disadvantages such as greater memory difficulty,
  and occasional incompatibility between versions.

- **`*.sgb`** – only IDs of brushes that were used to insert elements are stored.
  Some information such as (exact) colors or internal state may be lost.
  This format should be **backward compatible**.


## Scripting

You can write scripts in JavaScript and manipulate the canvas programmatically.

If you put directory named “scripts” into the working directory you will be able
to run all scripts from the menu. The second option is a build-in editor.

Get inspired by [examples][scripts].

![Preview - scripting][prev-scripting]


## Performance

Significant effort has been invested in optimizing the game for efficiency.
If, however, the simulation speed on your machine is not sufficient, try the following tips to enhance your gaming experience.

The canvas is divided into chunks – square areas with dimensions usually 20×20 points.
This allows to distinguish areas where “nothing moves” and these areas are then
“ignored”, thus saving performance. **The aim is therefore to have the least active chunks**.
Do you want a huge heap of sand? – no problem – until it all moves.

Another factor affecting performance is the canvas size.
**Choose the canvas size with respect to performance of your computer.**
Use scaling if needed.

![Preview - chunks][prev-chunks]


## Development
Most of the codebase comes from 2014 and 2015.
In 2016 version 2.03 Beta was released as the last bigger release, introducing new elements and behaviour.
Version 2.04 Beta was released in 2017 with English localization and performance improvements.
It currently has around **30,000 source lines of Java code**.

Sand Game 2 is now in maintenance mode. No new features are planned in the future.
Check [Sand Game JS](https://github.com/Hartrik/sand-game-js).

In the past, there have always been issues with Java backwards compatibility:
* wild changes in JavaFX between Java 7 and Java 8 (APIs removed, rendering changes),
* JavaFX was removed from Java distributions since Java 11,
* JavaScript engine was removed from Java distributions since Java 15.


## Download
### EXE distribution (Windows only)
The easiest way. Java is included.

1) Download [SandGame2_2-04-bundle-win.zip](https://github.com/Hartrik/Sand-Game-2/releases/download/2.04/SandGame2_2-04-bundle-win.zip) (71.1 MB)
2) **Unzip the file**
3) Double-click on **SandGame2_2-04.exe**

### JAR distribution
Correct version and distribution of Java must be installed. Tested on Windows only.

* **Java 8** build – [SandGame2_2-04.zip](https://github.com/Hartrik/Sand-Game-2/releases/download/2.04/SandGame2_2-04.zip) (1.4 MB)
  * :heavy_check_mark: Oracle JRE 8 &emsp; :x: 9, 10 – resource loading broken, 11 and later – it lacks JavaFX
  * :heavy_check_mark: BellSoft Liberica **Full** JRE 8 &emsp; :x: 11 and later – resource loading broken
  * :x: Amazon Corretto JRE 8 and later – it lacks some JavaFX features
  * :x: Eclipse Temurin/Adoptium JRE 8 and later – it lacks JavaFX
* **Java 11** build with JavaFX Windows binaries included – [SandGame2_2-04-J11-win.zip](https://github.com/Hartrik/Sand-Game-2/releases/download/2.04-J11-hotfix-2/SandGame2_2-04-J11-win.zip) (34.6 MB)
  * :heavy_check_mark: Oracle JRE 11, 12, 13, 14 &emsp; :x: 15 and later – it lacks JavaScript engine
  * :heavy_check_mark: BellSoft Liberica JRE 11 &emsp; :x: 17 and later – it lacks JavaScript engine
  * :heavy_check_mark: Amazon Corretto JRE 11 &emsp; :x: 17 and later – it lacks JavaScript engine
  * :heavy_check_mark: Eclipse Temurin/Adoptium JRE 11 &emsp; :x: 17 and later – it lacks JavaScript engine
