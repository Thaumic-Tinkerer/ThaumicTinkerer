<html>
	<title>Thaumic Tinkerer Files</title>

	<font size="6">Thaumic Tinkerer Files</font><br>
	<a href="http://www.minecraftforum.net/topic/1813058-">Thaumic Tinkerer Thread</a><br>
	<a href="http://vazkii.us">Back to vazkii.us</a>
	<br><hr><br>
	
	<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB"><img alt="Creative Commons Licence" style="border-width:0" src="http://i.creativecommons.org/l/by-nc-sa/3.0/88x31.png" /></a><br />Thaumic Tinkerer by Vazkii is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB">Creative Commons Attribution-NonCommercial-ShareAlike 3.0 Unported License</a>.<br>
	This webpage and Thaumic Tinkerer itself are open source. Contribute to the project on <a href="https://github.com/Vazkii/ThaumicTinkerer">GitHub</a>!
	<br><hr><br>
	
	<body link="#111111" vlink="#444466" alink="#000099">
	<a href="https://www.sugarsync.com/pf/D9740002_82596175_752562"><b>ThaumicTinkerer KAMI</b></a><br><br>
	<?php

	$file_url = 'https://raw.github.com/Vazkii/ThaumicTinkerer/master/web/urls.txt';
	$file_contents = file_get_contents($file_url);
	$file_contents_array = array_reverse(explode(PHP_EOL, $file_contents));

	foreach($file_contents_array as $line) {
		$line_props = explode('=', $line);
		$version = $line_props[0];
		$url = $line_props[1];
		
		print("<a href=\"$url\">ThaumicTinkerer <b>$version</b></a><br>");
	}
	?>
	
</body>
</html>