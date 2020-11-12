# Zentimestamp Plugin

*This plugin is up for adoption.* Want to help improve this plugin?
[Click here to learn more](https://wiki.jenkins.io/display/JENKINS/Adopt+a+Plugin)!

## Description

From Jenkins 1.597, the plugin adds the Jenkins `+BUILD_TIMESTAMP+` variable to the Configure System page of Manage Jenkins.  
Note: you must activate the Global Property! Additionally, you must specify a
[java.text.SimpleDateFormat](http://java.sun.com/javase/6/docs/api/java/text/SimpleDateFormat.html)
pattern such as the value '`+yyyyMMddHHmmss+`'.

## Features

* Export a `+BUILD_TIMESTAMP+` variable to the Jenkins instance (all jobs on all slaves will be impacted).
* Export a `+BUILD_TIMESTAMP+` variable to the slave (all jobs on this slave will be impacted).
* Export a `+BUILD_TIMESTAMP+` variable to the job (only the job will be impacted)
