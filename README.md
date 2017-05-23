# JLinq

## Introduction #

Java LINQ is a fully featured C#-extension-style [LINQ](https://msdn.microsoft.com/en-us/library/system.linq.enumerable.aspx) implementation.

## Features

* Query iterable objects in a simple, functional syntax.

## How do I get set up?

* To be determined...
* Summary of set up
* Configuration
  * Code Style Preferences
    > Import the Eclipse preferences file `Preferences/CodeStyle.epf`.
    > 1. Open the import menu (`File -> Import`).
    > 1. Select `Preferences` and click `Next` (`General -> Preferences`).
    > 1. Click `Browse`, navigate and select the `Preferences/CodeStyle.epf` file within this repository, and click `Open`.
    > 1. Ensure `Import all` is checked.
    > 1. Click `Finish`.
* Dependencies
  * Plug-Ins
    * EditorConfig ([Eclipse](https://marketplace.eclipse.org/node/2506426)) ([Visual Studio Code](https://marketplace.visualstudio.com/items?itemName=EditorConfig.EditorConfig))
    * EclipseFolding ([Eclipse](https://github.com/stefaneidelloth/EclipseFolding/raw/master/com.cb.platsupp.site))
      > ### How to Install
      > 1. In Eclipse, open the install software window (`Help -> Install New Software...`).
      > 1. Paste the following into the `Work with:` box and press **Enter**:
      > ```https://github.com/stefaneidelloth/EclipseFolding/raw/master/com.cb.platsupp.site```
      > 1. In the box below, check the box next to `com.cb.eclipse.folding` and click `Next`.
      > 1. On the `Install Details` page, click `Next`.
      > 1. On the `Review Licenses` page, select the `I accept the4 terms of the license agreement` radio button and click `Finish`.
      > 1. If prompted with a security warning, click `OK`.
      > 1. Wait for installation to complete.
      > 1. When prompted to restart, click `Yes`.
      >
      > ### How to Enable
      > 1. In Eclipse, open the preferences window (`Window -> Preferences`).
      > 1. Expand `Java` and `Editor` and select `Folding`.
      > 1. Ensure `Enable folding` is checked.
      > 1. Click the `Select folding to use:` combo box and select `Coffee Bytes Java Folding`.
      > 1. Check the `Enable Folding` check box for `User Defined Regions`.
      > 1. Select the `User Defined Regions` tab.
      > 1. Set `Start Identifier` to `region` and `End Identifier` to `endregion`.
* Database configuration
* How to run tests
* Deployment instructions
