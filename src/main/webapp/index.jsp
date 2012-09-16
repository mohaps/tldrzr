<!doctype html>

<html>
<head>
  <meta charset="utf-8">
  <title>Embedded Jetty Template</title>

  <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet">
  <link href="http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css" rel="stylesheet">

  <!--
  IMPORTANT:
  This is Heroku specific styling. Remove to customize.
  -->
  <link href="http://heroku.github.com/template-app-bootstrap/heroku.css" rel="stylesheet">
  <style type="text/css">
    .instructions {
      display: none;
    }

    .instructions li {
      margin-bottom: 10px;
    }

    .instructions h2 {
      margin: 18px 0;
    }

    .instructions blockquote {
      margin-top: 10px;
    }

    .screenshot {
      margin-top: 10px;
      display: block;
    }

    .screenshot a {
      padding: 0px;
      line-height: 1;
      display: inline-block;
      text-decoration: none;
    }

    .screenshot img {
      border: 1px solid #ddd;
      -webkit-border-radius: 4px;
      -moz-border-radius: 4px;
      border-radius: 4px;
      -webkit-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075);
      -moz-box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075);
      box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075);
    }

    .modal {
      width: 800px;
      margin-left: -400px;
      height: 650px;
      margin-top: -325px;
    }

    .modal .modal-body {
      text-align: center;
      max-height: 650px;
    }
  </style>
  <!-- /// -->
  <script type="text/javascript">
    <!--
    function appname() {
      return location.hostname.substring(0, location.hostname.indexOf("."));
    }

    function showInstructions(eid) {
      var instructions = ["#eclipse-instructions", "#cli-instructions"];
      for (i in instructions) {
        if (instructions[i] != eid) {
          $(instructions[i]).hide();
        }
      }
      $(eid).show();
    }
    // -->
  </script>
</head>

<body>
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <a href="/" class="brand">Embedded Jetty Template</a>
      <!--
      IMPORTANT:
      This is Heroku specific markup. Remove to customize.
      -->
      <a href="/" class="brand" id="heroku">by <strong>heroku</strong></a>
      <!-- /// -->
    </div>
  </div>
</div>

<div class="container" id="getting-started">
<div class="row">
<div class="span8 offset2">
<h1 class="alert alert-success">Your app is ready!</h1>

<div class="page-header">
  <h1>Get started with Embedded Jetty</h1>
</div>

<p>
  This is a template for a web application that uses embedded Jetty.
  The sample code consists of a JSP (this page) and a <a href="hello">simple servlet</a>.
  Take a look around and then use Eclipse or the Command Line to deploy some changes.
</p>

<ul id="tab" class="nav nav-tabs">
  <li class="active"><a id="eclipse-instructions-tab" href="#eclipse-instructions" data-toggle="tab">Using Eclipse
    3.7</a></li>
  <li><a id="cli-instructions-tab" href="#cli-instructions" data-toggle="tab">Using Command Line</a></li>
</ul>

<div class="tab-content">
<div id="eclipse-instructions" class="instructions tab-pane active">
<a name="using-eclipse"></a>

<div class="alert alert-warn">If you already created this app from the Heroku Eclipse Plugin, proceed to <a
    href="#step3">Step 3</a>.
  The following steps depends on the Heroku Eclipse plugin. If you do not have the Heroku Eclipse plugin installed and
  configured,
  follow a <a href="https://devcenter.heroku.com/articles/getting-started-with-heroku-eclipse#installation-and-setup"
              target="_blank">step-by-step guide</a>
  on Dev Center to install the plugin and
  configure the plugin in Eclipse.
</div>

<h2>Step 1.Configure Heroku Eclipse preferences</h2>
<ol>
  <li>Open <code>Eclipse</code><i class="icon-chevron-right"></i><code>Preferences</code></li>
  <li>Select <code>Heroku</code></li>
  <li>Enter your <code>Email</code> and <code>Password</code></li>
  <li>Click <code>Login</code>. If your login was successful, your Heroku API key would be populated in the
    <code>API Key</code> field.<br/>

    <div class="modal hide" id="apiPreferences">
      <div class="modal-header">
        <a class="close" data-dismiss="modal"><i class="icon-remove"></i></a>

        <h3>Setup API Key</h3>
      </div>
      <div class="modal-body">
        <img src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/heroku_preferences.png'
             alt="setup api key"/>
      </div>
    </div>
                    <span class="screenshot">
                      <a href="#apiPreferences" data-toggle="modal">
                        <img
                            src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/heroku_preferences.png'
                            alt="setup api key" width="100">
                        <i class="icon-zoom-in"></i>
                      </a>
                    </span>
  </li>
  <li>In the SSH Key section, click <code>Generate</code> if you need to generate a new key.
    If you have previously generated and saved a SSH key is automatically loaded from the default location. If it is
    not
    in the default location, click <code>Load SSH Key</code><br/>
  </li>
  <li>Click on <code>Add</code> to add your SSH Key to Heroku
    <div class="modal hide" id="sshkeyadd">
      <div class="modal-header">
        <a class="close" data-dismiss="modal"><i class="icon-remove"></i></a>

        <h3>Add SSH Key to Heroku</h3>
      </div>
      <div class="modal-body">
        <img src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/heroku_ssh_add.png'
             alt="Add SSH Key to Heroku"/>
      </div>
    </div>
	                    <span class="screenshot">
	                      <a href="#sshkeyadd" data-toggle="modal">
                          <img
                              src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/heroku_ssh_add.png'
                              alt="Add SSH Key to Heroku" width="100"/>
                          <i class="icon-zoom-in"></i>
                        </a>
	                    </span>
  </li>
</ol>

<h2>Step 2. Importing this App</h2>
<ol>
  <li>Open <code>File</code><i class="icon-chevron-right"></i><code>Import</code> and expand the Heroku section
    <div class="modal hide" id="importAppSelect">
      <div class="modal-header">
        <a class="close" data-dismiss="modal"><i class="icon-remove"></i></a>

        <h3>Import App</h3>
      </div>
      <div class="modal-body">
        <img src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/import_app_1.png'
             alt="Import App"/>
      </div>
    </div>
	                    <span class="screenshot">
	                      <a href="#importAppSelect" data-toggle="modal">
                          <img
                              src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/import_app_1.png'
                              alt="Import App" width="100"/>
                          <i class="icon-zoom-in"></i>
                        </a>
	                    </span>

  </li>
  <li>Select <code>Existing Heroku Application</code><i class="icon-chevron-right"></i><code>Next</code></li>
  <li>Select <code>
    <script>document.write(appname());</script>
  </code> and click <code>Next</code>

    <div class="modal hide" id="importApp2">
      <div class="modal-header">
        <a class="close" data-dismiss="modal"><i class="icon-remove"></i></a>

        <h3>Select App to import</h3>
      </div>
      <div class="modal-body">
        <img src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/import_app_2.png'
             alt="Create app from template"/>
      </div>
    </div>
	                    <span class="screenshot">
	                      <a href="#importApp2" data-toggle="modal">
                          <img
                              src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/import_app_2.png'
                              alt="Create app from template" width="100"/>
                          <i class="icon-zoom-in"></i>
                        </a>
	                    </span>
  </li>
  <li>Choose <code>Auto detected project</code>

    <div class="modal hide" id="importApp3">
      <div class="modal-header">
        <a class="close" data-dismiss="modal"><i class="icon-remove"></i></a>

        <h3>Project Type selection</h3>
      </div>
      <div class="modal-body">
        <img src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/import_app_3.png'
             alt="Project Type selection"/>
      </div>
    </div>
	                    <span class="screenshot">
	                      <a href="#importApp3" data-toggle="modal">
                          <img
                              src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/import_app_3.png'
                              alt="Project Type selection" width="100"/>
                          <i class="icon-zoom-in"></i>
                        </a>
	                    </span>
  </li>
  <li>Click <code>Finish</code> to complete the import. The plugin will use <a
      href="http://www.eclipse.org/egit/">eGit</a>
    and clone the source code repository to a local Git repository.
    <div class="modal hide" id="importApp4">
      <div class="modal-header">
        <a class="close" data-dismiss="modal"><i class="icon-remove"></i></a>

        <h3>Local Project and Git Repository</h3>
      </div>
      <div class="modal-body">
        <img src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/create_app_template_4.png'
             alt="Local Project and Git Repository"/>
      </div>
    </div>
	                    <span class="screenshot">
	                      <a href="#importApp4" data-toggle="modal">
                          <img
                              src='https://template-app-instructions-screenshots.s3.amazonaws.com/eclipse/create_app_template_4.png'
                              alt="Local Project and Git Repository" width="100"/>
                          <i class="icon-zoom-in"></i>
                        </a>
	                    </span>
  </li>
</ol>
<a name="step3" id="step3"></a>

<h2>Step 3. Makes some changes to the app</h2>
<ol>
  <li>Open <code>HelloServlet.java</code></li>
  <li>Modify line 20 with:
    <code>
      out.write("Deployed first change!".getBytes());
    </code>
  </li>
</ol>

<h2>Step 4. Deploy to Heroku</h2>
<ol>
  <li>Right-click the project root and choose <code>Team</code> <i class="icon-chevron-right"></i> <code>Commit</code>
  </li>
  <li>Enter a commit message and click <code>Commit</code>

    <div class="modal hide" id="commitChanges">
      <div class="modal-header">
        <a class="close" data-dismiss="modal"><i class="icon-remove"></i></a>

        <h3>Commit Changes</h3>
      </div>
      <div class="modal-body">
        <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/6-5-commit.png"
             alt="Commit Changes"/>
      </div>
    </div>
                <span class="screenshot">
                  <a href="#commitChanges" data-toggle="modal">
                    <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/6-5-commit.png"
                         alt="Commit Changes" width="100"/>
                    <i class="icon-zoom-in"></i>
                  </a>
                </span>
  </li>
  <li>Right-click the project root and choose <code>Team</code> <i class="icon-chevron-right"></i> <code>Push to
    Upstream</code></li>
  <li>Review the push results. At the bottom, a "... deployed to Heroku" message will appear.
    <div class="modal hide" id="pushResults">
      <div class="modal-header">
        <a class="close" data-dismiss="modal"><i class="icon-remove"></i></a>

        <h3>Push Results</h3>
      </div>
      <div class="modal-body">
        <img src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/6-8-push-result.png"
             alt="Push Results"/>
      </div>
    </div>
                <span class="screenshot">
                  <a href="#pushResults" data-toggle="modal">
                    <img
                        src="https://s3.amazonaws.com/template-app-instructions-screenshots/eclipse/6-8-push-result.png"
                        alt="Push Results" width="100"/>
                    <i class="icon-zoom-in"></i>
                  </a>
                </span>
  </li>
</ol>

<div class="hero-unit">
  <h1>Done!</h1>

  <p>You've just cloned, modified, and deployed a brand new app.</p>
  <a href="/hello" class="btn btn-primary btn-large">See your changes</a>

  <p style="margin-top: 20px">Learn more at the
    <a href="http://devcenter.heroku.com/categories/java">Heroku Dev Center</a></p>
</div>
</div>

<div id="cli-instructions" class="instructions tab-pane">
  <a name="using-cli"></a>

  <h2>Step 1. Setup your environment</h2>
  <ol>
    <li>Install the <a href="http://toolbelt.heroku.com">Heroku Toolbelt</a>.</li>
    <li>Install <a href="http://maven.apache.org/download.html">Maven</a>.</li>
  </ol>

  <h2>Step 2. Login to Heroku</h2>
  <code>heroku login</code>
  <blockquote>
    Be sure to create, or associate an SSH key with your account.
  </blockquote>
              <pre>
  $ heroku login
  Enter your Heroku credentials.
  Email: naaman@heroku.com
  Password:
  Could not find an existing public key.
  Would you like to generate one? [Yn] Y
  Generating new SSH public key.
  Uploading SSH public key /Users/Administrator/.ssh/id_rsa.pub
  Authentication successful.</pre>

  <h2>Step 3. Clone the App</h2>
  <code>git clone -o heroku git@heroku.com:<script>document.write(appname())</script>.git</code>

  <h2>Step 4. Makes some changes to the app</h2>
  <ol>
    <li>
      <script>document.write("<code>cd " + appname() + "</code>")</script>
    </li>
    <li>Open <code>src/main/java/com/example/HelloServlet.java</code> with your favorite editor</li>
    <li>Modify line 20 with:
      <code>
        out.write("Deployed first change!".getBytes());
      </code>
    </li>
  </ol>

  <h2>Step 5. Make sure the app still compiles</h2>
  <code>mvn clean package</code>

  <h2>Step 6. Deploy your changes</h2>
  <ol>
    <li><code>git commit -am "New changes to deploy"</code></li>
    <li><code>git push heroku master</code></li>
  </ol>

  <div class="hero-unit">
    <h1>Done!</h1>

    <p>You've just cloned, modified, and deployed a brand new app.</p>
    <a href="/hello" class="btn btn-primary btn-large">See your changes</a>

    <p style="margin-top: 20px">Learn more at the
      <a href="http://devcenter.heroku.com/categories/java">Heroku Dev Center</a></p>
  </div>
</div>
</div>
</div>
</div>
</div>

<script src="http://twitter.github.com/bootstrap/assets/js/jquery.js"></script>
<script src="http://twitter.github.com/bootstrap/assets/js/bootstrap-tab.js"></script>
<script src="http://twitter.github.com/bootstrap/assets/js/bootstrap-modal.js"></script>
</body>
</html>
