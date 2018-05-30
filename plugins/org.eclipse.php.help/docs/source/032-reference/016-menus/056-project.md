# Project Menu

<!--context:project-->

The Project menu allows you to carry out different functions on your projects, including open, close, build and encode.

<!--note-start-->

#### Note:

For more information on the build process, see the topic in the Workbench User Guide.

<!--note-end-->

The option**s available from the Project menu are:**

<table>
<tr><th>Name</th>
<th>Shortcut</th>
<th>Description</th></tr>

<tr><td>Open Project</td>

<td></td>

<td>Opens the currently selected project (if closed)</td></tr>

<tr><td>Close Project</td>

<td></td>

<td>Closes the currently selected project.
<br />
Closing a project does not cause it to be deleted from the file system. A closed project will still be displayed in Project Explorer view, with a ![closed_project_icon.png](images/closed_project_icon.png "closed_project_icon.png")closed project icon, but its resources are no longer accessible from within the Workbench.
<br />
Closing projects takes up less memory and speeds up the build process.</td></tr>

<tr><td>Build All</td>

<td>Ctrl+B</td>

<td>This command manually invokes an incremental build on all projects in the Workbench.
<br />
This is only available if automatic build is not selected (see below).</td></tr>

<tr><td>Build Project</td>

<td></td>

<td>This command manually invokes an incremental build on any resources in the currently selected project that have been affected since the last build.
<br />
This is only available if automatic build is not selected (see below).</td></tr>

<tr><td>Build Working Set</td>

<td></td>

<td>This command manually invokes an incremental build on any resources in a working set that have been affected since the last build.
<br />
This is only available if automatic build is not selected (see below).</td></tr>

<tr><td>Clean...</td>

<td></td>

<td>Invokes a clean build. This will discard all previous build results.</td></tr>

<tr><td>Build Automatically</td>

<td></td>

<td>Performs an incremental build whenever resources are saved. Selecting this option will disable all other manual build options.</td></tr>

<tr><td>Convert to a Dynamic Web project...</td>

<td></td>

<td>Converts the Web project from a static project to a dynamic project.</td></tr>

<tr><td>Properties</td>

<td></td>

<td>Opens the project's properties dialog which allows you to view and configure various settings for the project, including project info, Builders, [Formatter](../../032-reference/032-preferences/024-code_style_preferences/016-formatter.md) , [PHP Debug](../../032-reference/032-preferences/032-debug/000-index.md) , PHP Include Path, [PHP Interpreter](../../032-reference/032-preferences/064-php_interpreter.md) ,   [PHP Task Tags](../../032-reference/032-preferences/096-validation/008-task_tags.md) , Profile Compliance and Validation, Project References, Refactoring History, Run/Debug Settings, Server, Task Tags and Validation.</td></tr>

</table>

<!--links-start-->

#### Related Links:

 * [Menus](000-index.md)
 * [File](008-file/000-index.md)
 * [Edit](016-edit.md)
 * [Source](024-source.md)
 * [Refactor](032-refactor.md)
 * [Navigate](040-navigate.md)
 * [Search](048-search.md)
 * [Run](064-run.md)
 * [Window](080-window.md)
 * [Help](088-help.md)

<!--links-end-->
