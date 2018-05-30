# Enabling JavaScript Support in PHP Projects

<!--context:enabling_javascript_support_in_php_projects-->

Enabling JavaScript support in PHP projects allows JavaScript libraries and external files to be referenced by the project .

<!--note-start-->

#### Note:

Once JavaScript support has been enabled for a project, you should [set the project's JavaScript Build Path](016-setting_the_javascript_build_path.md) in order for the required resources to be made available to the project.

<!--note-end-->

These procedures describe how to [enable JavaScript support for new PHP projects](#enabling-javascript-support-for-new-php-projects), [add support to existing projects](#enabling-javascript-support-for-existing-php-projects), or [remove JavaScript support](#removing-javascript-support).

### Enabling JavaScript Support for New PHP Projects

<!--ref-start-->

To enable JavaScript support in new PHP Projects:

 1. Go to File Menu and select **New | PHP Project**.  -Or- In Project Explorer view, right-click and select **New | PHP Project**.  The new PHP Project wizard will launch.
 2. Enter the required information in the various fields.
 3. To enable JavaScript support, mark the **Enable JavaScript support for this project** checkbox.
 4. Click Finish.

A new PHP Project will be created with full JavaScript support.

<!--ref-end-->

### Enabling JavaScript Support for Existing PHP Projects

JavaScript libraries and features can be added to existing PHP projects in your workspace.

<!--ref-start-->

To enable JavaScript Support for existing PHP Projects:

In Project Explorer view, right-click the project for which you want to enable JavaScript support and select **Configure | Add Java Script** Support.
JavaScript support will be enabled for the project.

<!--ref-end-->

### Removing JavaScript Support

If you are not using JavaScript libraries or files in your project, you can remove JavaScript support for that project.

<!--ref-start-->

To remove JavaScript Support for existing PHP Projects:

In Project Explorer view, right-click the project for which you want to enable JavaScript support and select **Configure | Remove JavaScript Support**.
JavaScript support will be removed from the project and no JavaScript libraries or external files will be available to the project.

<!--ref-end-->

<!--links-start-->

#### Related Links:

 * [JavaScript Support](../../016-concepts/168-javascript.md)
 * [Developing with JavaScript](000-index.md)
 * [Setting the JavaScript Build Path](016-setting_the_javascript_build_path.md)
 * [Viewing JavaScript Elements in the Outline View](024-viewing_javascript_elements_in_the_outline_view.md)
 * [Using JavaScript Content Assist](032-using_javascript_content_assist.md)
 * [Using JavaScript Syntax Coloring](040-using_javascript_syntax_coloring.md)
 * [Using JavaScript Mark Occurrences](048-using_javascript_mark_occurences.md)

<!--links-end-->
