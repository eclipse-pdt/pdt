# Quick Start

<!--context:quick_start-->

The following Quick Start page will help newcomers and (even) our veteran users familiarize themselves with this new version.

The Features covered in the Quick Start are:

 * [Workspaces](#workbench)
 * [Creating a PHP Project](#creating-a-php-project)
 * [Creating a PHP File](#creating-a-php-file)
 * [PHP Debugging](#php-debugging)

### Workbench

The Workbench is a window that displays [Perspectives](PLUGINS_ROOT/org.eclipse.platform.doc.user/concepts/concepts-4.htm), [Views](PLUGINS_ROOT/org.eclipse.platform.doc.user/concepts/concepts-5.htm?cp=0_2_4) and menu bars through which different operations can be performed.

See the [Workbench](PLUGINS_ROOT/org.eclipse.platform.doc.user/gettingStarted/qs-02a.htm) topic in the Workbench User Guide for more on using and customizing the Workbench.

<!--note-start-->

#### Note:

Additional user guides can be accessed from inside PDT by going to **Help | Help Contents**, or from the Eclipse Online Documentation site ([http://help.eclipse.org/](http://help.eclipse.org/)).

<!--note-end-->

### Workspaces

Workspaces are the files system location where all your projects are created and stored.

You can use the default Workspace created by PDT or from the menu bar go to **File | Switch Workspace | Other** to select a different Workspace.

### Creating a PHP Project

A project is a group of files and resources which are displayed in a tree in the Project Explorer view.

<!--ref-start-->

To create a new PHP project:

Go to the Menu Bar and select **File | New | PHP Project**.

-Or- In the Project Explorer View, right-click and select **New | PHP Project**.

<!--ref-end-->

See [Creating PHP Projects](../024-tasks/008-creating_php_projects.md) for more information.

### Creating a PHP File

<!--ref-start-->

To create a new PHP file:

Go to the Menu Bar and select **File | New | PHP File**.

-Or- in Project Explorer view, right-click the folder in which you want to create your file and select **New | PHP File**

-Or- click the New PHP File icon on the toolbar ![new_php_file.png](images/new_php_file.png "new_php_file.png"). This creates a file outside of a project.

<!--ref-end-->

See [Creating PHP Files](../024-tasks/016-file_creation/000-index.md) for more information.

### PHP Debugging

The debugger detects and diagnoses errors in PHP code situated on local or remote servers:

<!--ref-start-->

To debug a PHP script situated on your workspace:

 1. Set breakpoints at the relevant locations in your script by double-clicking the Marker Bar (located at the left of the editor area) at the relevant line. A blue ball appears to indicate that a breakpoint is set.
 2. From the main menu, select **Run | Debug Configurations**  -Or- right-click the file in Project Explorer view and select **Debug As | Debug Configurations**.
 3. To create a new configuration, double-click the **PHP CLI Application** category.
 4. Enter all relevant information and click **Apply** and **Debug**.

<!--ref-end-->

<!--ref-start-->

To debug a PHP Web page situated on a server:

 1. From the main menu, select **Run | Debug Configurations** -or- right-click the file in Project Explorer view and select **Debug As | Debug Configurations**.
 2. To create a new configuration, double-click the **PHP Web Application** category.
 3. Enter the required information and click **Apply** and **Debug**.

<!--ref-end-->

Debugging Preferences can be configured from the [Debug Preferences page](../032-reference/032-preferences/032-debug/000-index.md), which can be accessed from **Window | Preferences | PHP | Debug**.

See [Debugging](../016-concepts/128-debugging_concept.md) for more information.

### Perspectives of Interest

To open a perspective go to **Window | Open Perspective**, select "Other" to view a full list of perspectives.

 * PHP (default) - This is the perspective that will open by default in PDT . It allows you to manage and create PHP projects and files.
 * PHP Debug - Allows you to manage and track the debugging process.
 * GIT / CVS / SVN Repository Exploring - Allows you to manage and view your source control.

See [PHP Perspectives and Views](../032-reference/008-php_perspectives_and_views/000-index.md) for more information.

<!--links-start-->

#### Related Links:

 * [Using the Debugger](../024-tasks/152-debugging/000-index.md)

<!--links-end-->
