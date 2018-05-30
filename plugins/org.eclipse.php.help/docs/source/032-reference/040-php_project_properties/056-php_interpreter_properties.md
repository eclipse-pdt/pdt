# PHP Interpreter Properties

<!--context:php_interpreter_properties-->

The PHP Interpreter Properties page allows you to set the PHP version used for the project. This will affect the internal debugger, code analyzer and content assist options.

To access the PHP Interpreter Properties page, right-click a PHP project in Project Explorer view and select Properties | Resource -or- select the project and from the menu bar go to Project | Properties | PHP Interpreter Properties .

<!--ref-start-->

To configure PHP Interpreter Properties for the project:

 1. Mark the 'Enable project specific settings' checkbox.
 2. Configure the settings according to your preferences.  See [PHP Interpreter Preferences](../../032-reference/032-preferences/064-php_interpreter.md) for more information on the settings available.
 3. Click Apply.  A prompt dialog will appear stating that a rebuild of the project must occur for the settings to take effect.
 4. Click Yes to rebuild the project.  -Or- click No for a rebuild to be performed only when PDT is restarted.  -Or- click Cancel to cancel the operation.

<!--ref-end-->

Default PHP Interpreter Properties for all projects can be set in the Debug Preferences page (accessed by going to Window | Preferences | PHP | Debug -or- by clicking the Configure Workspace Settings link on the properties page.)

<!--links-start-->

#### Related Links:

 * [PHP Interpreter Preferences](../../032-reference/032-preferences/064-php_interpreter.md)
 * [PHP Support](../../016-concepts/008-php_support.md)
 * [Builders Properties](016-builders_properties.md)
 * [Formatter Properties](../../032-reference/040-php_project_properties/024-code_style_properties/016-formatter_properties.md)
 * [PHP Debug Properties](040-php_debug_properties.md)
 * [PHP Include Path Properties](048-php_include_path_properties.md)

<!--links-end-->
