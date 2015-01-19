TarSeeker
=========

Recursive seeker for tar archives. It supports bz2 and gzip compression formats.

Installation
------------

This project use [Maven](http://maven.apache.org/) in order to stay up to date with all dependencies so, if you don't have Maven installed on your system I reccomend to [download](http://maven.apache.org/download.cgi) it and follow the instruction reported in the [installation page](http://maven.apache.org/download.cgi#Installation) of Maven and install it on your system.  
When you have correctly installed Maven on your system, you can proceed with the steps described below:

1. Download the zip package
2. Unpack the folder contained in the zip archive
3. Go inside the `tarseeker` folder
4. Type `mvn package` and wait for the process to end
5. Go inside the newly created `target` folder
6. You will see two jar files; the "ready to go" file is the one without the `-tmp` suffix

Usage
-----

Typical usage:  
`java -jar TarSeeker-[verison_number].jar -i /path/to/the/input/file.tar -o /path/to/the/output/file.txt -e -f txt`  
The command above will seek for each `txt` file inside the input file and saves them in a single file called `file.txt`.

