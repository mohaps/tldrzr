
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">

        <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

        <link rel="stylesheet" href="css/normalize.css">
        <link rel="stylesheet" href="css/main.css">
        <script src="js/vendor/modernizr-2.6.2.min.js"></script>
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->
		<div class="mypage">
        <!-- Add your site or application content here -->
        <div class="header">	
		<h1>TL;DRizer - algorithmically generated summaries demo</h1>
		</div>
		<div class="demo_section">
			<strong>Summarize a Feed (RSS/Atom)</strong>
			<br>
			<div align="center"><form name="tldr" method="post" action="/tldr/feed" accept-charset="iso-8859-1">Feed Url : <input type="text" size="40" name="feed_url" value="http://feeds.feedburner.com/TechCrunch/"><input type="submit" value="Summarize Feed" style="margin-left: 2px;"></form></div>
			<p><small>Defaults to the TechCrunch Feed Burner RSS url. Hit the Summarize Feed button and you're good to go. This will fetch/parse/summarize the text, so be a bit patient. :) </small></p>
			<strong>Summarize Text</strong>
			<br>
			<form name="tldr" method="post" action="/tldr/text" accept-charset="iso-8859-1">
			<div style="padding: 2px;">
			<textarea cols="65" rows="25" name="input_text">Enter text to summarize here. </textarea>
			</div>Sentences : <select name="sentence_count">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5" selected="true">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
			</select>
			<input type="submit" value="Summarize Text"></form>
			<p><small>If cutting/pasting, try to use the paste as plaintext option</small></p>
		</div>
		<div class="content">
		<div class="content_section">
			<h2>How it works?</h2>
			<p>TL;DR uses an algorithm derived from <a href="http://classifier4j.sf.net">Classifier4J.</a> It first tokenizes the text into words and then calculates the top N most frequent words (discarding stop words and single occurence words). It then scans the sentences and gets the first N sentences which feature any or all of the most frequent words. The sentences are sorted based on first occurence in original text and concatenated to create the summary. The user has control over how long the generated summary should be in terms of sentence count.</p>
			
		<p><strong>TL;DR</strong> is written in Java and uses <a href="http://jsoup.org">Jsoup</a> for html text scraping, <a href="http://rometools.jira.com">ROME</a> for RSS Feed parsing (which depends on <a href="http://jdom.org">JDOM</a>).</p>
		</div>
		</div>
		<div class="footer">
		<p>This is a weekend project/hackday demo created by <a href="http://mohaps.com">mohaps AT gmail DOT com</a>. Still very much wetpaint/work in progress. Comments/crits are appreciated.</p>
		</div>
		</div>

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>
        <script src="js/plugins.js"></script>
        <script src="js/main.js"></script>

        <!-- Google Analytics: change UA-XXXXX-X to be your site's ID. -->
        <script>
            var _gaq=[['_setAccount','UA-XXXXX-X'],['_trackPageview']];
            (function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
            g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
            s.parentNode.insertBefore(g,s)}(document,'script'));
        </script>
    </body>
</html>