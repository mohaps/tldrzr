<!-- 
 *  TL;DRzr - A simple algorithmic summarizer
 *  Website: http://tldrzr.com
 *  Author: Saurav Mohapatra (mohaps@gmail.com)
 *  
 *  Copyright (c) 2013, Saurav Mohapatra
 *  All rights reserved.
 -->

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

        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/main.css">
        <script src="/js/vendor/modernizr-2.6.2.min.js"></script>
        <title>TL;DRzr - an algorithmic summary generation demo</title>
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
        <![endif]-->
		<div class="mypage">
        <!-- Add your site or application content here -->
        <div class="header">	
		<div align="center"><a href="/"><img src="/images/tldrzr_logo_header.png" alt="TL;DRzr - algorithmically generated summaries demo" style="border: 0px;"></a></div>
		<div class="topnav" align="center" style="margin-top: 2px; padding-top: 4px; padding-bottom: 4px; margin-bottom: 6px; border-bottom: 1px dashed #434343; border-top: 1px dashed #434343">
		<a href="/"> Home</a> | <a href="/#whatisit">What is it?</a> | <a href="/#howitworks">How does it work?</a> | <a href="/#samples">Sample summaries</a> | <a href="/#credits">Who built this?</a> | <a href="https://news.ycombinator.com/item?id=5523538">Discuss on Hacker News</a></div>
		</div>
		</div>
		<div class="content">
		<div class="content_section">
			<h2>Generated Summary <small><em>(upto ${summary.sentence_count} sentences)</em></small></h2>
			<div class="feedEntrySummary"><blockquote style="color: rgb(200,10,10); font-style: italic;"><c:out value="${summary.summary}"/></blockquote></div>
			<p><small>Generated in ${summary.millis} milliseconds</small></p>
			
			<h2>Original Text</h2>
			<div class="feedEntryText"><blockquote><c:out value="${summary.original}"/></blockquote></div>
			
			
		</div>
		<div><a href="/">back to TL;DRzr</a></div>
		
		<div class="footer"> <a name="credits"/>
		<p>This is a weekend project/quick hack demo created by <a href="mailto:mohaps@gmail.com">Saurav Mohapatra</a>.</p>
		<p>Still very much wet paint/work in progress. Comments/Crits are appreciated. Contact author via <a href="mailto:mohaps@gmail.com">mohaps AT gmail DOT com</a> or my <a href="http://mohaps.com">blog</a> or my twitter feed : <a href="http://twitter.com/mohaps">@mohaps</a></p>
		</div>
		</div>

        <!--  script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.9.1.min.js"><\/script>')</script>
        <script src="js/plugins.js"></script>
        <script src="js/main.js"></script -->

        <!-- Google Analytics: change UA-XXXXX-X to be your site's ID. -->
        <script>
            var _gaq=[['_setAccount','UA-1728759-8'],['_trackPageview']];
            (function(d,t){var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
            g.src=('https:'==location.protocol?'//ssl':'//www')+'.google-analytics.com/ga.js';
            s.parentNode.insertBefore(g,s)}(document,'script'));
        </script>
    </body>
</html>