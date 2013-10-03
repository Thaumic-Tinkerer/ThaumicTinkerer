<html>
	<font size="6">Thaumic Tinkerer Files</font><br>
	<a href="http://www.minecraftforum.net/topic/1813058-">Thaumic Tinkerer Thread</a><br>
	<a href="http://vazkii.us">Back to vazkii.us</a>
	<hr><br>
</html>

<?php
print('<body link="#111111" vlink="#444466" alink="#000099">'); 

$file_url = 'https://raw.github.com/Vazkii/ThaumicTinkerer/2.0/web/urls.txt';
$file_contents = file_get_contents($file_url);
$file_contents_array = array_reverse(explode(PHP_EOL, $file_contents));

foreach($file_contents_array as $line) {
	$line_props = explode('=', $line);
	$version = $line_props[0];
	$url = $line_props[1];
	
	print("<a href=\"$url\">ThaumicTinkerer <b>$version</b></a><br>");
}

?>