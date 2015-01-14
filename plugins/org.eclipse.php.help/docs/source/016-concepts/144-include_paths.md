# PHP Include Paths

<!--context:include_paths-->

The PHP Include Path is a set of locations that is used for finding resources referenced by include/require statements.

<!--note-start-->

#### Note:

Adding libraries or external projects elements to your project's Include Path will also make elements defined in these resources available as content assist options.

<!--note-end-->

Elements added to a project's Include Path affect the following:

 * [Running](120-running.md)/[Debugging](../024-tasks/152-debugging/000-index.md) - Files that are called with a '[relative path](relative_path)' will be searched for during runtime in the resources and order specified in the include path.
 * [Go to source](../024-tasks/088-using_smart_goto_source.md) - Files that are called with a '[relative path](relative_path)' will be searched for according to the resources and order specified in the project's include path.

In 'include'/'require' calls, file locations can be defined in three ways:

 1. **Absolute Path** - The exact file location is specified (e.g. C:\Documents and Settings\MyProject\myfolder\a.php). During PHP Web Page Running/Debugging the [Path Mapping](160-path_mapping.md) mechanism will be activated.
 2. **Relative to the Current Working Directory** - File names preceded with a "./" or a "../" These will only be searched for relative to the PHP 'Current Working Directory'. You can find out the location of your Current Working Directory by running the command "echo getcwd()".

See [http://il2.php.net/manual/en/function.include.php](http://il2.php.net/manual/en/function.include.php) for more on PHP's search mechanism.

<!--links-start-->

#### Related Links:

 * [Configuring a Project's PHP Include Path](../024-tasks/168-adding_elements_to_a_project_s_include_path.md)

<!--links-end-->
