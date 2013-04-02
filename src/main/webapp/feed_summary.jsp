<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
		<div class="content">
		<div class="content_section">
			<h2>Summarized Feed</h2>
			<p><strong>Feed Url : </strong> <a href="${summarized_feed.url}">${summarized_feed.url}</a><br>
			<p><strong>Entry Count:</strong> ${summarized_feed.itemCount}<br>
			<p><h3>Feed Entries</h3></p>
			 <c:forEach var="feedEntry" items="${summarized_feed.entries}" >  
				<div class="feedEntryItem" style="margin: 2px; padding: 8px; border: 1px dashed;">
					<div class="feedEntryTitle"><strong>${feedEntry.title}</strong></div>
					<div class="feedEntryAuthor"><em>by ${feedEntry.author}</em></div><hr>
					<div class="feedEntrySummary">
						<h4>Summary (<em>Generated</em>)</h4>
						<blockquote style="color: rgb(200,10,10); font-style: italic;">${feedEntry.summary}</blockquote>
					</div>
					<div class="feedEntryText">
						<h4>Text (<em>Original</em>)</h4>
						<blockquote>${feedEntry.text}</blockquote>
					</div>
					<div class="feedEntryLink" style="padding-bottom: 5px;"><small><hr>Read more at : <a href="${feedEntry.link}">${feedEntry.link}</a><hr></small></div>
				</div>
		
				
			</c:forEach>  
			
		</div>
		<div>
			<p><small>Generated in ${summarized_feed.millis} milliseconds</small></p>
		</div>
		<div><a href="/">back to TLDRizer</a></div>
		<div class="footer">
		<small><em>This is a weekend project/hackday demo created by <a href="http://mohaps.com">mohaps AT gmail DOT com</a>. Still very much wetpaint/work in progress. Comments/crits are appreciated.</em></small>
		</div>
		</div>

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>
        <script src="js/plugins.js"></script>
        <script src="js/main.js"></script>

        <!-- Google Analytics: change UA-XXXXX-X to be your site's ID. -->
        <script>
            var _gaq=[['_setAccount','UA-1728759-8'],['_trackPageview']];
            (function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
            g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
            s.parentNode.insertBefore(g,s)}(document,'script'));
        </script>
    </body>
</html>