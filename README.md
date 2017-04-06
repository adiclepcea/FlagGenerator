# FlagGenerator

### What it is
Java program + Maven project
It will generate png flags from an xml file.

### How to use it
It takes two parameters
* dimension: in the form: WxH where W is the width and H is the height of the file you want generated (ex. 900x400 for a generated image file of 900 pixels wide by 400 pixels tall)
* inputFile: just the name of the xml file containing the flag definition
 
for example to start it with maven directly you would write:
```
mvn exec:java -e -Dexec.mainClass="org.adi.lasting.flags.FlagGenerator" -Dexec.args="900x400 Ethiopia.xml"
```
This would generate a flag 900 pixels wide by 400 pixels wide with the specification in the file Ethiopia.xml
You would get 2 output files:
* An SVG file named like the input file but with the _.SVG_ extension
* An PNG file named _out.png_

You could also compile with dependencies:

```
mvn clean compile assembly:single
```

And then run the file in **target** directory directly from java:

```
java -cp target/flags-1.0.0-jar-with-dependencies.jar org.adi.lasting.flags.FlagGenerator 1000x600 FIAZ.xml
```


### What does it use
It uses:
* Batik for SVG to PNG conversion
* XSLT for XML to SVG conversion

### What is the input format
The input format is a modified SVG format. 
The modifications are not huge, but do make a diference when you need to create a simple flag.
Basically if you have rectangles defined in your flag that have coordinates reaching maximum width and height, you can ignore puting a **viewBox** field.
If you do put a **viewBox** field, then the program will not add another one. Also you do not need to put the **DOCTYPE** and the **xmlns** tags/attributes in the input file.
They will be put automatically by the program. You do need however to add **xmlns:link** namespace in case you use this facility. This is because it is quite difficult to add two namespaces 
in XSLT 1.0. So I will consider copying your namespace in case you have it defined.
Another small modification I did is change the **<svg>** tag with the **<flag>** tag in the input format. This is just not to confuse one for another.

### Example input file

* Instead of writing this (SVG):
```
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1 3" width="900px" height="400px" preserveAspectRatio="none">
  <rect fill="#da121a" height="3" width="1"/>
  <rect fill="#FDE642" height="2" width="1"/>
  <rect fill="#078930" height="1" width="1"/>
</svg>

```

* You can write just this
```
<flag>
  <rect fill="#da121a" width="1" height="3"/>
  <rect fill='#FDE642' width='1' height='2'/>
  <rect fill='#078930' width='1' height='1'/>
</flag>

```
For simple flags it does make a difference.
