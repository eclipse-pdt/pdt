# PHP Build Path

<!--context:build_paths-->

The PHP build process scans all resources that are on the project's PHP Build Path so that elements defined within them can be made available for Content Assist options . This is done in order to get notification about changes in the file system (e.g. files added/removed from the project, code changes etc.) and in order to maintain the code database (user classes, functions, variables etc.).

Configuring the project's Build Path allows you to select PHP resources to include/exclude from this process. Rather than automatically scanning all resources within the project, configuring the Build Path allows you to select which resources will be scanned (letting you, for example, exclude folders containing images, JavaScript files or other types of files not containing PHP code). This can significantly speed up the build process.

<!--links-start-->

#### Related Links:

 * [Configuring a Project's Build Path](../024-tasks/176-configuring_build_paths.md)
 * [Project Explorer View](../032-reference/008-php_perspectives_and_views/008-php_perspective_views/008-php_explorer_view.md)

<!--links-end-->
