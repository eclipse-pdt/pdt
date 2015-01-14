# Running PHP Scripts Locally

<!--context:running_php_scripts_locally-->

This procedure describes how to run a PHP Script from your workspace using PDT 's internal debugger.

<!--ref-start-->

To locally run a PHP Script:

 1. Click the arrow next to the Run button ![run_icon.png](images/run_icon.png "run_icon.png") on the toolbar and select Run Configurations -or- go to Run | Run Configurations.  A Run dialog will open.
 2. Double-click the PHP Script option to create a new run configuration.
 3. Enter a name for the new configuration.
 4. Select the required PHP executable.   If no PHP Executables are listed, click the PHP Executable link and add a PHP Executable in the [PHP Executable Preferences](../../032-reference/032-preferences/056-php_executables.md) page. The PHP Executables must match the Debugger type.
 5. Under PHP File, click Browse and select your file
 6. Marking the 'Display debug information when running' checkbox will cause debug views to be displayed.
 7. If necessary, you can add arguments in the PHP Script Arguments tab to simulate command line inputs.
 8. Click Apply and then Run.

Your script will be run and displayed in a browser.

<!--ref-end-->

<!--note-start-->

#### Note:

If the file contains 'include' or 'require' calls to files which are not contained within the project, you must [add them to the project's Include Path](../../024-tasks/168-adding_elements_to_a_project_s_include_path.md) in order to simulate your production environment.

<!--note-end-->

<!--links-start-->

#### Related Links:

 * [Running](../../016-concepts/120-running.md)
 * [Running Files and Applications](000-index.md)
 * [Running PHP Scripts Remotely](016-running_php_scripts_remotely.md)
 * [Running PHP Web Pages](024-running_php_web_pages.md)
 * [Using the Debugger](../../024-tasks/152-debugging/000-index.md)

<!--links-end-->
