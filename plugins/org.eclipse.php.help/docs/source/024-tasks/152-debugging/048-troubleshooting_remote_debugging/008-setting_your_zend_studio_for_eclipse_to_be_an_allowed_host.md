# Setting your Environment to be an Allowed Host

<!--context:setting_your_zend_studio_for_eclipse_to_be_an_allowed_host-->

This procedure describes how to ensure the machine on which your PDT is installed will be an Allowed Host for initiating your Debug session on the remote server.

If only the standalone Zend Debugger is installed on your server:

<!--ref-start-->

To configure your debugger to allow your PDT to debug:

 1. Open your php.ini file.
 2. Edit the zend_debugger.allow_hosts parameters to include the IP address of the machine on which your PDT is installed.  e.g. zend_debugger.allow_hosts=127.0.0.1/32
 3. Ensure the address is not in your zend_debugger.deny_hosts parameter list.
 4. Set the Debug Server to expose itself to remote clients by setting the zend_debugger.expose_remotely parameter to Always.  (e.g. zend_debugger.expose_remotely=always).
 5. Save the file.
 6. Restart your Web server for the settings to take effect.

<!--ref-end-->

<!--links-start-->

#### Related Links:

 * [Setting Up Remote Debugging](000-index.md)
 * [Ensuring the Placement of dummy.php](016-ensuring_the_placement_of_dummy.php.md)
 * [Debugging a PHP Web Page](../../../024-tasks/152-debugging/032-debugging_a_php_web_page.md)

<!--links-end-->
