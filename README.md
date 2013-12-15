#Ip Address Range Application

##Original Objective
Write a port scanner in Java.  Given a range of IPs, it is to scan the hosts and give us as much information about said hosts as possible.  Please write the code as if this is what you would check in at work.

You can make any reasonable interpretations of this you need to in order to complete the project, listing them in your notes.

##Instructions for running application
This project uses maven (>= 3.0) and Java (>= 1.6)

It provides a UI for interacting with the app.

I admit, it's still a work in progress albeit, a bit usable.

To run the UI:
from the application root, run "mvn jetty:run".
In your browser, go to "localhost:9000".
If 9000 is not a suitable port, on line 58 of the POM, you can change the port value to whatever you desire.
Also port, 10000 should remain open.

##Problems
The time out for checking socket connectivity is hardcoded. Should be passed in the UI maybe?

* Ip address storage is not very efficient. Uses 4 times as much space for ip address calculation.
* Ip address abstraction a single integer or at least 4 shorts.
* Test coverage for some of the classes should be increased.
* There were some other changes I made to this revision of the code that are not immediately apparent.
Previously, the user would download the list of all the ip address/port combinations. The paging algorithm would
slice a certain number of elements from the array as dictated by the number per page field. This would be sent to the server
and a port scan would be run for each element. This has been improved so that a count is returned in the first query
and subsequent queries page forward or backward based on the page selected and the number of elements per query.
* A for loop is used to page forward, with the potential to create a lot of objects depending on the range.
I'm sure some math could be used instead, just haven't figured it out.
* Also, it might have been more prudent to make the /count and /portScan calls to be GETs instead of POSTs and passing the parameters in the url.


Thanks.

