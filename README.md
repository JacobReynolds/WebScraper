WebScraper
==========
Updated version of this program can be found [here](http://www.github.com/jacobreynolds/moodlechecker)<br>
## About
This is a web scraping program that will log in to the University of Minnesota Moodle website and compare current grades to the grades at the last time they were checked.  If they have been changed, an email will be sent with the updated grade values.
<br>
<br>
## Using the project
To open in terminal or command prompt, navigate to the directory on your computer that holds the WebScraper-master file that you downloaded from this repository.  Navigate to the <b>dist</b> file and in terminal enter <b>java -jar WebScraper.jar</b>.  The program should start at that point.
<br>
<br>
For full installation instructions go <a href = "https://www.youtube.com/watch?v=-JrqtE_uzdQ"> here </a>.
<br>
<br>
##  Clarifications
Due to the HTML set up of Moodle, the user's layout must look similar to <a href="http://imgur.com/GA0Ux0R">this</a>.  The applet interface will take in the user's x500 and password, along with their Gmail address(which has to end in @gmail.com not @umn.edu) and password.
<br>
<br>
## Dependencies
If you would like to run this in your own IDE(I used netbeans) there are two libraries you'll need.
<br>
<a href="http://sourceforge.net/projects/htmlunit/files/htmlunit/">HTMLUnit</a>
<br>
<a href="http://www.oracle.com/technetwork/java/javamail/index.html">Java Mail</a>
<br>
<i>Note: due to the password fields being set for CMD and Terminal, the program will not work if run in an IDE.  Compile it, then run in a command line.</i>
<br>
<br>
## Thank you
All comments beginning with //--- are comments for the user describing basic functions.
<br>
<br>
Any complaints or comments can be e-mailed to reyno511@umn.edu.
<br>
<br>
-Jacob Reynolds
