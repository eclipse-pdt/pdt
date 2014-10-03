# Adding a PHP Library

<!--context:adding_a_php_library-->

This procedure describes how to add a user library to PDT . Including user  libraries in your project or environment saves you time in writing and debugging code, as you are re-using debugged code.

<!--ref-start-->

**To add an additional PHP Library to your project:**

 1. Go to **Window**| **Preferences** | **PHP** | **PHP Libraries**.
 2. In the PHP Libraries Preferences page click **New**.  The "New User Library" Dialog will open.
 3. In the "New User Library" dialog, enter the name of your user library
 4. Select the "Add to environment" checkbox if you would like this library to be added to your entire environment instead of a specific project.
 5. To apply changes click **OK**.
 
A new empty library will be added to the list. Next to the name in brackets indicates if it is shared by the environment or only related to a project.

Adding a PHP library creates a place folder in which you can place external files that contain pre-written code. For more information see [Adding External Folders to PHP Libraries](016-adding_external_folders_to_php_libraries.md).

<!--ref-end-->

<!--note-start-->

#### Note:

Once you have added a user library in the PHP preferences page, you must also add it to your PHP Include Path of the project in which you would like to have it available. For more information see [Configuring a Project's PHP Include Path](../../024-tasks/168-adding_elements_to_a_project_s_include_path.md).

<!--note-end-->

<!--links-start-->

#### Related Links:

 * [PHP Libraries Preferences](000-index.md)
 * [Adding External Folders to PHP Libraries](016-adding_external_folders_to_php_libraries.md)
 * [Exporting PHP User Libraries](032-exporting_php_user_libraries.md)
 * [Importing PHP User Libraries](024-importing_php_user_libraries.md)
 * [Editing PHP User Libraries](048-editing_php_user_libraries.md)
 * [Editing PHP Library Components or Folders](040-editing_php_library_components_or_folders.md)
 * [Removing a PHP Library or Library Folder](056-removing_a_php_library_or_library_folder.md)

<!--links-end-->
