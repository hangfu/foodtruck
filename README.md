SF-Food-Truck
=============

This application visualize food trucks on a map of San Francisco, using data from [DataSF](http://www.datasf.org/): [Food
Trucks](https://data.sfgov.org/Permitting/Mobile-Food-Facility-Permit/rqzj-sfat).



Software Used
-------------

Java
Maven
Spring
Jersey
Quartz
jQuery


Features
-------------

Display a map of locations of food trucks in San Francisco
Group the food trucks into several food types, using checkbox to enable/disable filtering
Click on the marks to get detailed information about the food truck
Search an address to focus on a particular area (provided by Google Map API)


Architecture
-------------
Backend RESTful web service is built by using Java and Spring/Jersey framework. 
A Quartz job is triggered on 5:00am everyday to refresh data in cache, making sure the meta data from DataSF is not obsolete.

