<?
echo "[BEGIN Bug Report Generation]\n";

$filename_bugs = "bugs";
$filename_sum = "summary";
unlink($filename_bugs);
unlink($filename_sum);

$p1 = writeBugs("P1", $filename_bugs);
$p2 = writeBugs("P2", $filename_bugs);
$p3 = writeBugs("P3", $filename_bugs);
$today = date('d F Y');
$sum = $p1 + $p2 +$p3;
file_put_contents ("$filename_sum", "Bug status $today - $sum ($p1, $p2, $p3)");

function writeBugs($pr, $filename_bugs) {

	// create a new curl resource
	$ch = curl_init();

	// set URL and other appropriate options
	curl_setopt($ch, CURLOPT_URL, "https://bugs.eclipse.org/bugs/buglist.cgi?query_format=advanced&short_desc_type=allwordssubstr&short_desc=&classification=Tools&product=PHP&long_desc_type=allwordssubstr&long_desc=&bug_file_loc_type=allwordssubstr&bug_file_loc=&status_whiteboard_type=allwordssubstr&status_whiteboard=&keywords_type=allwords&keywords=&bug_status=UNCONFIRMED&bug_status=NEW&bug_status=ASSIGNED&bug_status=REOPENED&bug_severity=blocker&bug_severity=critical&bug_severity=major&bug_severity=normal&bug_severity=minor&bug_severity=trivial&priority=$pr&emailtype1=substring&email1=&emailtype2=substring&email2=&bugidtype=include&bug_id=&votes=&chfieldfrom=&chfieldto=Now&chfieldvalue=&cmdtype=doit&order=Reuse+same+sort+as+last+time&field0-0-0=noop&type0-0-0=noop&value0-0-0=");
	curl_setopt($ch, CURLOPT_HEADER, 0);
	$handle = fopen("result.html", "w");
	curl_setopt($ch, CURLOPT_FILE, $handle);

	// grab URL and pass it to the browser
	curl_exec($ch);

	// close curl resource, and free up system resources
	curl_close($ch);

	// finding the number of bugs
	$line_array = file('result.html');
	$index = 0;
	$bugs = "";
	$red = array("<", ">");

	while ($index < count($line_array)) {
		$line = $line_array[$index];
		$findme  = 'bugs found';
		$pos = strpos($line, $findme);

		if ($pos === false) {
		} else {
			$tok = strtok($line, " ");
			$bugs_number = $tok;
		}

		$findme  = 'show_bug.cgi';
		$pos = strpos($line, $findme);

		if ($pos === false) {
		} else {
			$pattern = '/>.*</';
			preg_match($pattern, $line, $matches, PREG_OFFSET_CAPTURE, 3);
			if (@$matches[0][0] != "") {
				$bugs .= str_replace($red, "", $matches[0][0]) . " ";
			}
		}

		$index++;
	}

	file_put_contents ($filename_bugs, "&lt;b&gt;$pr bugs ($bugs_number)&lt;/b&gt; $bugs&lt;br&gt;&lt;br&gt;", FILE_APPEND);
	return (int) $bugs_number;
       echo "[$pr Bug Report Generation]\n";
}

echo "[END Bug Report Generation]\n";
?>
