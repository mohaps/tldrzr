# Embedded Jetty template application

This is a template for a web application that uses embedded Jetty. The sample code consists of a JSP (this page) and a simple servlet.

## Running the application locally

First build with:

    $mvn clean install

Then run it with:

    $java -cp target/classes:target/dependency/* com.example.Main

