# Setting Up Remote Debugging

<!--context:troubleshooting_remote_debugging--><!--context:setting_up_remote_debugging-->

Before debugging on a server using PHP Web Page debugging, certain settings need to be configured to ensure that PDT can communicate with your server.

<!--note-start-->

#### Note:

The following instructions explain how to set up Remote Debugging with the Zend Debugger.

<!--note-end-->

<!--ref-start-->

To set up communication between PDT and the server on which you are debugging:

 1. Ensure the Zend Debugger is installed on your server.  The Zend Debugger comes bundled with [Zend Server](http://www.zend.com/en/products/server), [Zend Core](http://www.zend.com/en/products/core/) and [Zend Platform](http://www.zend.com/en/products/platform/), but can also be downloaded as a separate component from [http://www.zend.com/en/products/studio/downloads](http://www.zend.com/en/products/studio/downloads).
 2. Ensure the machine on which your PDT is installed is an allowed host for your debugger.   See [Setting your PDT to be an Allowed Host](008-setting_your_zend_studio_for_eclipse_to_be_an_allowed_host.md) for more information.
 3. In PDT , configure your server according to the instructions under [Adding Servers](../../../032-reference/032-preferences/080-php_servers.md#Adding_servers) in the [PHP Servers Preferences](../../../032-reference/032-preferences/080-php_servers.md).
 4. Ensure the correct settings are configured in your [Debug Preferences](../../../032-reference/032-preferences/032-debug/000-index.md) and [Installed Debuggers Preferences](../../../032-reference/032-preferences/032-debug/008-installed_debuggers.md) pages.
 5. [Ensure you have a dummy.php file](016-ensuring_the_placement_of_dummy.php.md) in your remote server's document root.

<!--ref-end-->

<!--links-start-->

#### Related Links:

 * [Using the Debugger](../../../024-tasks/152-debugging/000-index.md)
 * [Debugging a PHP Web Page](../../../024-tasks/152-debugging/032-debugging_a_php_web_page.md)

<!--links-end-->
