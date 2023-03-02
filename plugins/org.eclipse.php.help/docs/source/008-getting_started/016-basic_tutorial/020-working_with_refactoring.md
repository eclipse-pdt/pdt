# Working with Refactoring

<!--context:working_with_refactoring-->

The purpose of this tutorial is to teach you how to refactor your code to easily rename and move files and elements without damaging your projects or severing the link between referenced items, as well as creating the necessary links between PHP elements in separate files.

#### Contents:

 * [Purpose and Usage](#purpose-and-usage)
 * [Renaming a File](#renaming-a-file)
 * [Moving a File](#moving-a-file)
 * [Renaming Elements within a File](#renaming-elements-within-a-file)

## Purpose and Usage

The Refactoring feature allows you to:

* Rename and move files and elements within those files, while maintaining the links between the items. Once an element or file has been renamed or moved, all instances of that item within the project will be automatically updated to reflect its new name / location.
* Organize Includes so that elements from one file can be referenced in another file.

Refactoring should be used when you are reorganizing your project and changing names and locations of files and elements.

<!--note-start-->

#### Note:

Refactoring will only work from within Project Explorer view.

<!--note-end-->

## Renaming a File

Renaming a file will result in the automatic renaming of all instances where that file is referenced within the project.

<!--ref-start-->

This procedure demonstrates how to rename a file:

1. Create a PHP file, called 'RenFile1', with the following code:

    <?php
    $a = 5;
    ?>
 
2. In the same project, create a second PHP file, called RenFile2, with the following code:

    <?php
    require_once("RenFile1.php");
    $a = 8;
    ?>

3. Save both files.
4. In Project Explorer view, right-click RenFile1 and select **Refactor | Rename** -or- select it and go to **Refactor | Rename** from the Main Menu.  
   A Rename File dialog will appear.
5. In the Rename File dialog box, rename RenFile1 to RenFile3.
6. Check the "Update references" box and click **Preview**.
7. In the Preview window, scroll through the changes and note that, as well as the name of the file itself being changed, the reference to the file in RenFile2 will also have been changed.
8. Press **OK** to apply changes.

The reference to RenFile1 in RenFile2 will have been updated to:

    require_once("RenFile3.php");

in order to reflect the changes in the file name.

<!--ref-end-->

## Moving a File

Moving a file will result in the automatic updating of all instances where that file is referenced within the project to reflect its change of location.

<!--ref-start-->

This procedure demonstrates how to move a file:

1. Create files 'RenFile1' and 'RenFile2' as described in steps 1 to 3 under "Renaming a File", above.
2. Within the same project, create an additional folder called 'RenFolder'.
3. In Project Explorer view, right-click RenFile1 and select **Refactor | Move** -or- select it and go to **Refactor | Move** from the Main Menu.
4. In the Move dialog, select RenFolder.
5. Click **Preview**.
   The Preview windows will display the changes that the move will apply to your script. Note that RenFile1's new location will automatically be updated in the reference to it in RenFile2.
6. Click **OK** to execute the Move.

<!--ref-end-->

## Renaming Elements within a File

All PHP Elements (e.g. classes, interfaces, functions, methods) can also be renamed and refactored from within the editor window.

<!--ref-start-->

This procedure demonstrates how to use the Rename Elements feature:

1. Create files 'RenFile1' and 'RenFile2' as described in steps 1 to 3 under "Renaming a File", above.
2. In the editor window of RenFile2, highlight the variable "a" on line 3.
3. Right-click and select **Refactor | Rename** -or- click **Alt+Shift+R**.
4. In the Rename Global Variable dialog box, rename the variable to b.
5. Check the "Update textual occurrences" box and click **Preview**.
6. In the Preview window, scroll through the changes and note that occurrences of variable "$a" will be changed in RenFile1 as well as in RenFile2.
7. Press **OK** to apply changes.

<!--ref-end-->

<!--links-start-->

#### Related Links

 * [Refactoring](../../016-concepts/076-refactoring.md)
 * [Using Refactoring](../../024-tasks/116-using_refactoring/000-index.md)
 * [Renaming Files](../../024-tasks/116-using_refactoring/008-renaming_files.md)
 * [Renaming Elements](../../024-tasks/116-using_refactoring/016-renaming_elements.md)
 * [Refactor Menu](../../032-reference/016-menus/032-refactor.md)

<!--links-end-->
