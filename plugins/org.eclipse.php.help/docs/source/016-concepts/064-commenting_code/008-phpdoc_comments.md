# phpDoc Block Comments

<!--context:phpdoc_comments-->

PDT offers a preset means for adding phpDoc comments to files by providing an input line when including statements, classes, class variables, and constants to the code. Developers are prompted to immediately add a description ensuring that the added elements are documented in their context and in real-time.

phpDoc blocks are descriptive comments that are part of the application code. They are used to describe the PHP element in the exact location in the code where the element appears. The block consists of a short description, long description, and phpDoc tags.

#### Example:

When creating a phpDoc Block comment for the following function:


	function add ($a, $b) {
		return $a + $b;
	}

the following comment will be created:


	/**
	 * Enter description here...
	 *
	 * @param unknown_type $a
	 * @param unknown_type $b
	 * @return unknown
	*/

The comments should now be edited with the relevant description and parameters.

Descriptions that are added for a code element are also automatically added to the Content Assist bank so that the next time the code element is used it is readily available from the Content Assist list. The element's descriptions will also appear in the Outline view.

<!--note-start-->

#### Note:

PDT offers Content Assist support for magic members declared in code comments. See the [Content Assist](../../016-concepts/016-code_assist_concept.md) concept for more information.
phpDoc blocks also serve as the input for creating a PHPDoc.

<!--note-end-->

<!--links-start-->

#### Related Links:

 * [Commenting PHP DocBlocks](../../024-tasks/128-commenting_php_docblocks.md)
 * [Commenting Code](000-index.md)
 * [Commenting PHP Code](../../024-tasks/120-how_to_comment_and_uncomment_php_code.md)

<!--links-end-->
