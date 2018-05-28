# Debugging

<!--context:debugging_concept-->

PDT debugging function allows you to test your files and applications and detect errors in your code.

The debugger allows you to control the execution of your program using a variety of options including setting breakpoints, stepping through your code, and inspecting your variables and parameters.

PDT includes several different debugging methods as described below:

### PHP Script Local Debugging

Allows you to debug files on your workspace using your internal debugger.  The Internal Debugger enables developers to locally validate freshly developed code before deploying to a web server. The internal option means that files located on your workspace can be debugged.

See [Locally Debugging a PHP Script](../024-tasks/152-debugging/024-locally_debugging_a_php_script.md) for more information.

<!--note-start-->

#### Note:

Your PHP executable must be running the Xdebug or Zend Debugger in order for remote debugging  capabilities to function.

<!--note-end-->

### PHP Web Page Debugging

Allows you to debug applications situated on a server. It allows you to debug whole applications, including any required interactive user input.

<!--note-start-->

#### Note:

It's recommended that your local project structure reflect the project structure on your server.

See [Debugging a PHP Web Page](../024-tasks/152-debugging/032-debugging_a_php_web_page.md)  for more information.

<!--note-end-->

<!--note-start-->

#### Note:

Your PHP server must be running the Xdebug or Zend Debugger in order for remote debugging  capabilities to function.

<!--note-end-->

<!--links-start-->

#### Related Links:

 * [Debugging Files and Applications](../024-tasks/152-debugging/000-index.md)
 * [Running and Analyzing Debugger Results](../024-tasks/152-debugging/040-analyzing_debugger_results.md)

<!--links-end-->
