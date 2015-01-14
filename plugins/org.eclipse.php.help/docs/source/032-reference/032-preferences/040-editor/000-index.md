# Editor Preferences

<!--context:editor_preferences-->

The Editor preferences page allows you to configure smart caret positioning behavior in the editor. Smart caret positioning determines where the cursor will jump to when certain positioning keys (e.g. Home / End) are pressed.

The Editor Preferences page is accessed from Window | Preferences | PHP | Editor .

---

 <!--ref-start-->

To configure Smart Caret Positioning Preferences

1\. Mark the required checkboxes to configure the following options:

  * Smart caret positioning at line start and end - If this checkbox is unmarked, the cursor will jump to the beginning/end of a line when Home/End are pressed. If it is marked, the cursor will jump to the beginning/end of the typed_line (i.e. ignoring the tabs at the beginning/end of a line).
  * Smart caret positioning in PHP names - If this checkbox is marked, the cursor will jump to the beginning/end of a 'word' within a PHP element (class / function / variable) when Ctrl + left arrow/right arrow are pressed. When unmarked, it will jump to the beginning/end of the whole element.

2\. Click Apply to apply your settings.

<!--note-start-->

### Note:

More editor settings can be accessed by clicking the "Text Editors" link.

<!--note-end-->

<!--ref-end-->

<!--links-start-->

#### Related Links:

 * [PHP Preferences](../../../032-reference/032-preferences/000-index.md)
 * [Content Assist Preferences](008-code_assist.md)
 * [Folding Preferences](016-folding.md)
 * [Hovers Preferences](024-hovers.md)
 * [Syntax Coloring Preferences](048-syntax_coloring.md)
 * [Task Tags Preferences](056-task_tags.md)
 * [Typing Preferences](072-typing.md)

<!--links-end-->
