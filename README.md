
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

The canvas is divided into chunks – square areas with dimensions usually 20×20 points.
This allows to distinguish areas where “nothing moves” and these areas are then
“ignored”, thus saving performance. **The aim is therefore to have the least active chunks**.
Do you want a huge heap of sand? – no problem – until it all moves.

Another factor affecting performance is the canvas size.
**Choose the canvas size with respect to performance of your computer.**

**You should follow these suggestions for better playing experience.**

![Preview - chunks][prev-chunks]


## Development
Most of the codebase comes from 2014 and 2015.
In 2016 version 2.03 Beta was released as the last bigger release, introducing new elements and behaviour.
Version 2.04 Beta was released in 2017 with English localization and performance improvements.

Sand Game 2 is now in maintenance mode. No new features are planned in the future.
Currently, it has over 29,000 source lines of code (Java only).

In the past, there have always been issues with Java backwards compatibility:
* wild changes in JavaFX between Java 7 and Java 8 (APIs removed, rendering changes),
* JavaFX was removed from Java distributions since Java 11,
* JavaScript engine was removed from Java distributions since Java 15.


## Download
### Distribution with Java (Windows only)
The easiest way. No need to have Java installed...

1) Download [SandGame2_2-04-bundle.zip](https://github.com/Hartrik/Sand-Game-2/releases/download/2.04-J11-hotfix/SandGame2_2-04-bundle.zip)
2) **Unzip the file**
3) Double-click on **SandGame2_2-04.exe**

### Jar distribution
Correct version and distribution of Java must be installed.

* **Java 8** build – [Sand-Game-2_2-04.zip](https://github.com/Hartrik/Sand-Game-2/releases/download/2.04/Sand-Game-2_2-04.zip)
  * :heavy_check_mark: Oracle JRE 8 &emsp; :x: 9, 10 – resource loading broken, 11 and later – it lacks JavaFX
  * :heavy_check_mark: BellSoft Liberica **Full** JRE 8 &emsp; :x: 11 and later – resource loading broken
  * :x: Amazon Corretto JRE 8 and later – it lacks some JavaFX features
  * :x: Eclipse Temurin/Adoptium JRE 8 and later – it lacks JavaFX
* **Java 11** build – [SandGame2_2-04-J11.zip](https://github.com/Hartrik/Sand-Game-2/releases/download/2.04-J11-hotfix/SandGame2_2-04-J11.zip) (**Windows only**)
  * :heavy_check_mark: Oracle JRE 11, 12, 13, 14 &emsp; :x: 15 and later – it lacks JavaScript engine
  * :heavy_check_mark: BellSoft Liberica JRE 11 &emsp; :x: 17 and later – it lacks JavaScript engine
  * :heavy_check_mark: Amazon Corretto JRE 11 &emsp; :x: 17 and later – it lacks JavaScript engine
  * :heavy_check_mark: Eclipse Temurin/Adoptium JRE 11 &emsp; :x: 17 and later – it lacks JavaScript engine
