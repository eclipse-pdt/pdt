# Ensuring the Placement of dummy.php

<!--context:ensuring_the_placement_of_dummy_php-->

In order for the remote server's debugger to communicate with PDT, a file called dummy.php must be located in your server's document root.

With the default  installation, a [dummy.php](resources/dummy.php) file will have been automatically placed in your server's document root folder.

If you installed the standalone Zend Debugger, you must copy the dummy.php file from the Zend Debugger archive to your server's document root.

<!--note-start-->

#### Note:

If you have set up a virtual host and it's document root is not pointed at the remote server's default document root, you will have to copy the dummy.php to the virtual host's document root in order to be able to debug on that virtual host.

You must also ensure that the Dummy File name is set to 'dummy.php' in PDT .

<!--note-end-->

<!--ref-start-->

To check your dummy file configuration in PDT :

 1. Open the Installed Debugger Preferences page by going to Windows| Preferences | PHP | Debug | Installed Debuggers.
 2. In the Installed Debuggers list, select the Zend Debugger and click Configure.  The Zend Debugger Settings dialog will open.
 3. In the Dummy File Name setting, ensure 'dummy.php' is entered.

<!--note-start-->

### Note:

If you changed the name of the dummy file on the server, you must change this entry accordingly.

<!--note-end-->

Click OK.

<!--ref-end-->

<!--links-start-->

#### Related Links:

 * [Setting Up Remote Debugging](000-index.md)
 * [Setting your PDT to be an Allowed Host](008-setting_your_zend_studio_for_eclipse_to_be_an_allowed_host.md)

<!--links-end-->
