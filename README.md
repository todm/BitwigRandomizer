# Bitwig Parameter Randomizer

![workflow_status](https://github.com/todm/BitwigRandomizer/actions/workflows/release.yaml/badge.svg)

A simple Bitwig Controller Extension to randomize device parameters. You can change up to 1024 values at a time completely at random or deviate from the current one by a specific amount.

## Installation

1. Download the latest [release version](https://github.com/todm/BitwigRandomizer/releases)
2. Move the `BitwigParameterRandomizer.bwextension` file to your Bitwig Extensions directory.
    - On Windows it's `~\Documents\Bitwig Studio\Extensions\`
    - On Mac it's `~/Documents/Bitwig Studio/Extensions/`
    - On Linux it's `~/Documents/Bitwig Studio/Extensions/`
    - You can also Drag and Drop the file directly into your Bitwig window wich will copy the file automatically
3. Activate the Extension in `Settings > Controllers`
    - Hardware Vendor: `todm`
    - Product: `BitwigRandomizer`

## How to use

When activated correctly you should have a new controller icon at the top right of your bitwig window.
Clicking the `Go` button will randomize the _remote control pages_ of the currently selected Device **that contain the `$randomN` tag**.

![open_settings](https://i.imgur.com/xhdhIVL.png)

### Setting up the pages

Open the _Remote Controls Editor_

![edit_remotes](https://i.imgur.com/4tNEUY1.png)

Create Pages and add remote controls for all your parameters.

Than add a tag to the pages that should be randomized by clicking on the lower part of the page. The extension will randomize all pages that contain a `$randomN` tag.

The `N` represents a consecutive number: `$random1`, `$random2`, `$random3`... up until `$random128` giving you 1024 parameters per device.

![page_tag](https://i.imgur.com/JruiR8y.png)
![multiple_tags](https://i.imgur.com/InPMVy6.png)

Clicking the `Go` button will now randomize all qualifying pages of the selected device.

## Deviation Amount

You can change the deviation amount to mutate the current state instead of completely randomizing all parameters.

A value of 100% will completely randomize the parameters. Anything lower will ad or substract from the current value.

```
100% -> newValue = random(0,1)
1% -> newValue = currentValue + 0.01 * random(-1, 1)
30% -> newValue = currentValue + 0.3 * random(-1, 1)
```