SF-Food-Truck
=============

This application visualize food trucks on a map of San Francisco, using data from [DataSF](http://www.datasf.org/): [Food
Trucks](https://data.sfgov.org/Permitting/Mobile-Food-Facility-Permit/rqzj-sfat).

Here is the live demo : http://sleepy-tundra-5202.herokuapp.com/

(Note: I use Heroku free hosting service, I found that first open the page, there is no data in cache. I guess heroku removes cache data on server if there are no traffic. Therefore, if you don't see the blue marks on map at first time you open the page, just wait for 5 seconds and refresh the page, then it should be working fine.)


Software Used
-------------

* Java
* Maven
* Spring
* Jersey
* Quartz
* jQuery


Features
-------------

* Display a map of locations of food trucks in San Francisco
* Group the food trucks into several food types, using checkbox to enable/disable filtering
* Click on the marks to get detailed information about the food truck
* Search an address to focus on a particular area (provided by Google Map API)


Architecture
-------------
* Backend RESTful web service is built by using Java and Spring/Jersey framework. 
* A Quartz job is triggered on 5:00am everyday to refresh data in cache, making sure the meta data from DataSF is not obsolete.
* Frontend is using jQuery, integrating Google Maps API.

The tech track I choose is Backend.

In this prototype, I'm using lightweight cache instead of DB. Because cache is faster than DB, and also the data is read-only static data, so I implement a quartz job to refresh cache everyday. 

Here are some backend APIs:

http://sleepy-tundra-5202.herokuapp.com/foodtruck/foodtruck

http://sleepy-tundra-5202.herokuapp.com/foodtruck/foodtruck?type=chinese&type=mexican&type=bbq

http://sleepy-tundra-5202.herokuapp.com/foodtruck/cache/build_cache


Possible improvements
---------------------
More thorough functional tests

Build response layer cache to boost performance

More accurate method to classify food types


About me
-----------
Please see my [resume](https://www.dropbox.com/s/gi2c0np3olmd8tj/HangFu_Resume.pdf?dl=0)


