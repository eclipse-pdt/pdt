# Locally Debugging a PHP Script

<!--context:locally_debugging_a_php_script-->

This procedure describes how to debug a PHP Script from your workspace using an internal [PHP Executable](../../032-reference/032-preferences/056-php_executables.md).

<!--note-start-->

#### Note:

You must configure your PHP Executable through the [PHP Executables Preferences page](../../032-reference/032-preferences/056-php_executables.md) before you can debug locally.

<!--note-end-->

<!--ref-start-->

To locally debug a PHP Script:

 1. Set breakpoints at the relevant places in the file that you would like to debug by double-clicking the vertical marker bar to the left of the editor.
 2. Save the file.
 3. Click the arrow next to the debug button ![debug_icon.png](images/debug_icon.png "debug_icon.png") on the toolbar and select Debug Configurations... -or- select **Run | Debug Configurations....**  A Debug dialog will open.
 4. Double-click the PHP Script option to create a new debug configuration. <br />![New Debug Configuration](images/debug_phpscript_configuration.png "New Debug Configuration")
 5. Enter a name for the new configuration.
 6. S elect the required PHP executable.   If no PHP Executables are listed, click the PHP Executable link and add a PHP Executable in the [PHP Executable Preferences](../../032-reference/032-preferences/056-php_executables.md) page. The PHP Executables must match the Debugger type.
 7. Select the PHP Debugger to be used. PDT supports both the Zend Debugger and XDebug.
 8. Enter your PHP file in the "PHP File" text field, or click **Browse** and select your file
 9. Marking the "Breakpoint" checkbox will result in the debugging process pausing at the first line of code.
 10. If necessary, you can add arguments in the PHP Script Arguments tab to simulate command line inputs.
 11. Click **Apply**and then **Debug**.
 12. Click **Yes**if asked whether to open the PHP Debug Perspective.

A number of views will open with relevant debug information.

See the [Running and Analyzing Debugger results](040-analyzing_debugger_results.md) topic for more information on the outcome of a debugging process.

<!--ref-end-->

<!--note-start-->

#### Note:

If the file contains 'include' or 'require' calls to files which are not contained within the project, you must [add them to the project's Include Path](../../024-tasks/168-adding_elements_to_a_project_s_include_path.md) in order to simulate your production environment.

<!--note-end-->

<!--links-start-->

#### Related Links:

 * [Working with the Debugger](../../008-getting_started/016-basic_tutorial/024-working_with_the_debugger.md)
 * [Debugging](000-index.md)
 * [Debug Preferences](../../032-reference/032-preferences/032-debug/000-index.md) 
 * [Debugging a PHP Web Page](032-debugging_a_php_web_page.md)
 * [Running and Analyzing Debugger Results](040-analyzing_debugger_results.md)
 * [Run Menu](../../032-reference/016-menus/064-run.md)
 * [PHP Support](../../016-concepts/008-php_support.md)

<!--links-end-->
