#FlagGenerator

###What it is
Java program - Maven project
It will generate png flags from an xml file.

###What does it use
It uses:
* Batik for SVG to PNG conversion
* XSLT for XML to SVG conversion

###What is the inpur format
The input format is a modified SVG format. 
The modifications are not huge, but do make a diference when you need to create a simple flag.
Basically if you have rectangles defined in your flag that have coordinates reaching maximum width and height, you can ignore puting a **width** and a **height** field.
If you do put a **width** or **height** field, then the program will not add another one. Also you do not need to put the **DOCTYPE** and the **xmlns** tags/attributes in the input file.
They will be put automatically by the program. You do need however to add **xmlns:link** namespace in case you use this facility. This is because it is quite difficult to add two namespaces 
in XSLT 1.0. So I will consider copying your namespace in case you have it defined.
Another small modification I did is change the **<svg>** tag with the **<flag>** tag in the input format. This is just not to confuse one for another.

###Example input file

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
