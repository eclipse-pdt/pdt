# Importing PHP User Libraries

<!--context:importing_php_user_libraries-->

This procedure describes how to import existing user libraries that are on the disk or in a repository. This allows you to take an already built library and use it in your project, as well as share a library with other users of the same repository. Importing a library will only import a description of the library in .xml format, and will not include any of the library's content. Before importing a user library, you must first have access to an exported user library. For more information see [Exporting PHP User Libraries](032-exporting_php_user_libraries.md).

<!--ref-start-->

**To import a user library:**

 1. Go to **Window**| **Preferences** | **PHP** | **PHP Libraries**.
 2. In the PHP Libraries Preferences page click **Import...**  The "Import User Libraries" dialog will open.
 3. To choose where you would like to import your library from, fill in the "File location:" text field with the URL or click **Browse...**and select the location.
 4. Select the libraries you would like to import from the options in the "Libraries contained in the selected file:" box, or press **Select All** or **Deselect All**.
 5. To apply changes click **OK**.  Your library's description in .xml format has now been imported into PDT .

If the library of the user who imports it is stored in the same location on the disk as the user who exported it, PDT will automatically find the libraries content and store it accordingly. If the user library is stored in a different place for the two users, the **Edit...** button allows you to replace the location URL. See [Editing PHP User Libraries](048-editing_php_user_libraries.md) for more information.

<!--ref-end-->

<!--links-start-->

#### Related Links:

 * [PHP Libraries Preferences](000-index.md)
 * [Adding a PHP Library](008-adding_a_php_library.md)
 * [Adding External Folders to PHP Libraries](016-adding_external_folders_to_php_libraries.md)
 * [Exporting PHP User Libraries](032-exporting_php_user_libraries.md)
 * [Editing PHP User Libraries](048-editing_php_user_libraries.md)
 * [Editing PHP Library Components or Folders](040-editing_php_library_components_or_folders.md)
 * [Removing a PHP Library or Library Folder](056-removing_a_php_library_or_library_folder.md)

<!--links-end-->
