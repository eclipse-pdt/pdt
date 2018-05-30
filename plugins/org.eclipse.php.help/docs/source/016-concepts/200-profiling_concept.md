# Profiling

<!--context:profiling_concept-->

The Profiler displays a breakdown of the executed PHP code in order to detect bottlenecks in scripts by locating problematic sections of code. These are scripts that consume excessive loading-time. The Profiler provides you with detailed reports that are essential to optimizing the overall performance of your application.

See below for a list of the three different profiling methods PDT includes.

### PHP CLI Application

Allows you to profile files on your workspace using local PHP executable debugger.
The PHP CLI debugger enables developers to locally validate freshly developed code before deploying to a web server. The internal option means that only files located in local directories can be profiled. When profiling internal files, PDT uses PHP CLI debugger that is installed on top of the PHP executable specified in profile launch configuration.

See [Locally Profiling a PHP Script](../024-tasks/216-profiling/008-profiling_local_php_script.md) for more information.

<!--note-start-->

#### Note:

Your PHP CLI must be running the Zend Debugger or Xdebug in order for profiling capabilities to function.

<!--note-end-->

#### PHP Web Application

Allows you to profile applications situated on a server. It allows you to profile whole applications and projects.

The PHP Web Page Profile setting has an option to give the files you are working on first priority when profiling, using the �Local Copy� option. This means that, when possible, file content is taken from the files situated on your Workspace. This prevents you from having to upload the latest revisions.

See [Profiling a PHP Web Page ](../024-tasks/216-profiling/016-profiling_php_web_page.md)  for more information.

<!--note-start-->

#### Note:

Your PHP server must be running the Zend Debugger or Xdebug in order for profiling capabilities to function.

<!--note-end-->

#### Profiling with Browser Toolbars

##### Zend Debugger

You can also start profile session for a web application by using different browser toolbars like [Z-Ray](http://www.zend.com/en/products/server/z-ray) or [Zend Debugger Toolbar](https://addons.mozilla.org/en-US/firefox/addon/zend-debugger-toolbar/).

#### Xdebug

If you turn on [xdebug.profiler_enable_trigger](https://xdebug.org/docs/all_settings#profiler_enable_trigger) you will able to run via COOKIE/GET/POST parameter "XDEBUG_PROFILE=1". Cookie can be created via browser toolbar, for example [The easiest Xdebug](https://addons.mozilla.org/en-US/firefox/addon/the-easiest-xdebug/) or [Xdebug helper](https://chrome.google.com/webstore/detail/xdebug-helper/eadndfjplgieldjbigjakmdgkmoaaaoc).

See [Profiling with Browser Toolbars](../024-tasks/216-profiling/024-profiling_with_browser_toolbars.md)  for more information.

<!--note-start-->

#### Note:

Your PHP server must be running the Zend Debugger or Xdebug in order for profiling capabilities to function.

<!--note-end-->

<!--links-start-->

#### Related Links:

 * [Using the Profiler](../024-tasks/216-profiling/000-index.md)
 * [PHP Profile Perspective](../032-reference/008-php_perspectives_and_views/032-php_profile_perspective/000-index.md)

<!--links-end-->
