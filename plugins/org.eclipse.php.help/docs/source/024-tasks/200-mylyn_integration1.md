# Working with Mylyn Integration

<!--context:mylyn_integration1-->

The Task List contains two types of tasks:"Local Tasks" and shared "repository tasks" that are stored in a task repository such as Bugzilla or Jira. See how to create new tasks. Local tasks are typically contained in categories, which you can create by right-clicking on the task list and selecting **New**| **Category**. Repository tasks are contained in special categories that represent queries.

At the top of the Task List, you will find the following buttons and features:

 * New Task - Create a new local or repository task.
 * Synchronize - Update repository tasks with changes from the server.
 * Task Presentation - Toggle between Scheduled and Categorized presentations.
 * Focus on Workweek - See only tasks scheduled for this week.
 * Find - search for a task by typing in words from the task summary
 * Working set indicator - Indicates the currently active working set. Use the black arrow on the left to change the working set.
 * Current task indicator - Indicates the currently active task. Use the black arrow on the left to re-activate a recently active task.

## Task List Presentation

The task list supports several ways to present tasks. You can toggle between the following modes by using the "Task Presentation" button in the toolbar. Categorized - View tasks grouped by their category Scheduled - View tasks grouped by the "scheduled date" .

### Icon Legend and Color Coding

See the legend below to interpret the icons and color coding in the task list. You can view this legend by selecting "Show UI Legend" from the menu that appears when you click the white down arrow next to the minimize button in the top right corner of the Task List view.

## Creating new Tasks

You can create new tasks by clicking on the "New Task" button in the Task List's toolbar. This will open the "New Task" dialog and prompt you to select a repository. There are two main types of tasks:

 * Local tasks
 * Repository tasks

### Local Tasks

You can use local tasks if you do not have a shared task repository or if you would like to create a private personal task that is local to your workspace.

To create a local task:

1. Select Local Task | **Finish**from the **New Task** dialog. You can then provide the following details about the task:
 * Task Description - Your task is called New Task by default. Replace this with a brief description of your task.
 * Priority - Set the priority of your task. This will affect the tasks’ icon and order in the task list.
 * Status - Set your task to "complete" or "incomplete". In your task list, completed tasks have a strike-through font and will appear lower in the list.
 * URL - You can associate a URL with this task.
 * "Retrieve Task Description from URL" button - Set the task description to the title of the associated URL (page)
 * "Open with Web Browser" button - Open the URL in the integrated web browser
 * Scheduled For - Set the date when you will work on this task. Tasks scheduled for today or a date in the past will appear in blue in your task list. Tasks scheduled for future days will appear in black. If your task list is in focused mode, only tasks for the current week will be visible (unless they have unread changes).
 * Due - Set the date when your task must be completed. Overdue tasks and tasks due today will appear in red in your task list.
 * Estimated Hours - Estimate the number of hours it will take to complete this task.
 * Active - Displays the total time that you have worked on this task. Time is only recorded when this task is active and you are actively interacting with the system.
 * Notes - Record your personal notes about this task.

### Repository Tasks

You can create a new repository task when you would like to share information about the task with your team using a task repository such as Bugzilla or JIRA. To create a new repository task, click on the "New Task" button in the Task List's toolbar. You can then select the repository where you would like to create a task. If you don't see your team's task repository, you will need to configure it in the task repositories view. Once you have selected a repository, click "Next". If you are connecting to a Bugzilla repository, select a **Product**as a top-level category for your task and click "Finish". A new task editor will appear.

If you are using Bugzilla, you can enter the following required information:

 * Description - Enter a brief task description in the text box at the top (this box does not have a label).
 * Component - Specify a "Component" to further categorize this task within the previously selected "Product".
 * Description - Describe the task in detail. Optional
 * You can specify additional information about your tasks in the "Attributes" section.
 * Personal Planning - You can enter information in this section that will be local to your workspace and not available on your team's task repository. See Local Tasks" for more information about the personal planning fields.
 * Assigned to - Specify who should work on the task. Type the first several characters of the person's email address, and then press ctrl+space to select the address from a list. A task can be assigned to only one person at a time.
 * Add CC - Add the addresses of people who should be notified of changes to this task. You can add multiple addresses, separated by a comma, e.g. (mik.kersten@tasktop.com, steffen.pingel@tasktop.com).

When finished, click **Submit**to add your new task to your team's shared task repository.

### Context

The context tab allows you to manage the context of resources associated with the task. You can view the context tab by selecting it in the lower left of the editor window.

### Elements

This section lists the resources that are part of the tasks’ context. Because the number of elements may be large, you can adjust the level of detail using the slider at the top of the Actions section. Sliding the control all the way to the left will show you all elements in your task context. As you slide it to the right, only the elements with a high level of interest will be displayed. You can manually remove elements from your task context by doing the following:

1. Right-Click **Remove From Context**.

You may choose to view all elements and exclude irrelevant items in this way before attaching the context to the task so that others can download it.

### Actions

Element Detail Slider - Adjusts the minimum level of interest required for an element to be displayed in the Elements section.

 * Attach Context - Attaches the context to the task so that it is available for download from the shared task repository. The context consists of the elements shown on the right.
 * Retrieve Context - Replaces the current task context with one that is attached to the task in the shared task repository.
 * Copy Context to... - Copy the task context to another task. That task will then have the same context as the current task.
 * Clear Context - Removes all context information from the task.

### Planning

Use the planning tab to access local information about the task that is private to your workspace. You can view the planning tab by selecting it in the lower left of the editor window. This tab contains a large area where you can enter personal notes about the task. See the local task section for more information about fields in the Personal Planning section.

## Task-Focused Interface

The task-focused interface is oriented around tasks and offers several ways to focus the interface on only what is relevant for the currently active task. You can focus navigator views (e.g. Package Explorer, Project Explorer, Navigator) by toggling the "Focus on Active Task" button in the toolbar. When focused, the view will show only the resources that are "interesting" for the currently active task.

Alt+Click Navigation

To navigate to a new resource that is not a part of the active task's context, you can toggle "Focus on Active Task" off, browse to the resource, and then click "Focus on Active Task" again to see only relevant resources. A more efficient way to add new resources is to use Alt+Click navigation (Clicking the mouse while holding the Alt key). When a view is in Focused mode, you can Alt+Click a node to temporarily show all of its children. Once an element that was previously not interesting is selected with the mouse, it becomes interesting the other child elements will disappear. The clicked element is now a part of the task's context. Alt can be held down while clicking to drill down from a top-level element to a deeply nested element that is to be added to the task context. Multiple Alt+Click are supported so that you can add several elements to the task context. As soon as a normal click is made, uninteresting elements will disappear. Ctrl+Click (i.e. disjoint selections, use Command key on Mac) are also supported and will cause each element clicked to become interesting. The first normal click will cause uninteresting elements to disappear. Note that Ctrl+click elements will become interesting (turn from gray to black) but only the most recently-clicked one will be selected while Alt is held down. Focusing Editors

Some editors such as the Java editor support focusing. Clicking the Focus button in the toolbar will fold all declarations that are not part of the active task context.

### Task-focused Ordering

When a task is active, elements that are interesting are displayed more prominently. For example, when you open the Java Open Type dialog (Ctrl+Shift+T), types that are interesting for the active task are shown first. Similarly, when you use ctrl+space to autocomplete a method name in a Java source file, methods that are in the task context are displayed at the top. Working Set Integration

When Focus is applied to a navigator view, the working sets filter for that navigator view will be disabled. This ensures that you see all interesting elements when working on a task that spans working sets. To enforce visibility of only elements within one working set, do the following: Set the view to show working sets as top-level elements. Use the Go Into action on the popup menu of the working set node in the view to scope the view down to just the working set. Open Task dialog

An Open Type style dialog is available for opening tasks (Ctrl+F12) and for activating tasks (Ctrl+F9). The list is initially populated by recently active tasks. The active task can also be deactivated via Ctrl+Shift+F9. This can be used as a keyboard-only alternative for multi-tasking without the Task List view visible. These actions appear in the Navigate menu.

### Task Hyperlinking

In the task editor, comments that include text of the form bug#123 or task#123 or bug 123 will be hyperlinked. Ctrl+clicking on this text will open the task or bug in the rich task editor. To support hyperlinks within other text editors such as code or .txt files, the project that contains the file must be associated with a particular task repository. This is configured by right-clicking on the project and navigating to "Properties" > "Task Repository" and selecting the task repository used when working with this project. Reporting Bugs from the Error Log

Bugs can created directly from events in the Error Log view. This will create a new repository task editor with the summary and description populated with the error event's details. If the Connector you are using does not have a rich editor, the event details will be placed into the clipboard so that you can paste them into the web-based editor that will be opened automatically.

<!--links-start-->

#### Related Links:

 * [Mylyn Integration](../016-concepts/176-mylyn_integration.md)

<!--links-end-->
