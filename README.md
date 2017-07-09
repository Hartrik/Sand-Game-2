
[b-travis-img]: https://travis-ci.org/Hartrik/Sand-Game-2.svg?branch=master
[b-travis-link]: https://travis-ci.org/Hartrik/Sand-Game-2
[b-release-img]: https://img.shields.io/github/release/Hartrik/Sand-Game-2.svg
[b-release-link]: https://github.com/Hartrik/Sand-Game-2/releases
[prev-1]: .github/preview.png
[scripts]: /scripts
[releases]: https://github.com/Hartrik/Sand-Game-2/releases


# Sand Game 2

[![Build Status][b-travis-img]][b-travis-link]
[![GitHub Release][b-release-img]][b-release-link]

**Sand Game 2** is a *falling sand* game where you are given an empty canvas and
a wide range of various elements which allows you to place and mix them as you like.

**Features**
- many elements and tools
- gravity
- temperature
- in-game scripting
- loading / saving
- portable, multi-platform, with open sources

**Showcase**

![preview][prev-1]


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
Insertion can be canceled using *RMB*.

Saved canvases can also be inserted as a templates.

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

You can write scripts in JavaScript and manipulate with the canvas programmatically.

If you put directory named “scripts” into the working directory you will be able
to run all scripts from the menu. The second option is a build-in editor.

Get inspired by [examples][scripts].


## Performance

The canvas is divided into chunks – square areas with dimensions usually 20×20 points.
This allows to distinguish areas where “nothing moves” and these areas are then
“ignored”, thus saving performance. **The aim is therefore to have the least active chunks**.
Do you want a really big heap of sand? – no problem – until it moves.

Another factor affecting performance is the canvas size.
**Choose the canvas size with respect to performance of your computer.**

**You should follow these suggestions for better playing experience.**


## Download

Executables can be found in [releases][releases].

You will need at least **Java 8u40** for running this game.
