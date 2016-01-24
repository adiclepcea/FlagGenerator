<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="xml" encoding="utf-8" indent="no"/>

  <xsl:param name="width" select="1200"/>
  <xsl:param name="height" select="800"/>

  <!-- this two variables needed to put the height and width in the svg element -->
  <xsl:variable name="maxWidth">
    <xsl:for-each select="/flag/rect/@width">
      <xsl:sort select="." data-type="number" order="descending"/>
      <xsl:if test="position() = 1"><xsl:value-of select="."/></xsl:if>
    </xsl:for-each>
  </xsl:variable>
  
  <xsl:variable name="maxHeight">
    <xsl:for-each select="/flag/rect/@height">
      <xsl:sort select="." data-type="number" order="descending"/>
      <xsl:if test="position() = 1"><xsl:value-of select="."/></xsl:if>
    </xsl:for-each>
  </xsl:variable>

  <!-- 
  	we put all the elements of the flag root element inside the new svg element 
  	and we add the needed namespaces
  -->
  <xsl:template match="flag">

    <xsl:text disable-output-escaping="yes">
&lt;!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd"&gt;
    </xsl:text>
    <xsl:element name="svg" xmlns="http://www.w3.org/2000/svg">      
	  <xsl:copy-of select="namespace::* | @*"/>
      
      <!-- nu stiu cum sa creez viewbox decat daca am un dreptunghi -->
      <xsl:if test="rect">
      	<xsl:attribute name="viewBox">
	       <xsl:value-of select="concat('0 0 ',$maxWidth,' ',$maxHeight)" />
	    </xsl:attribute>	
      </xsl:if>
      <xsl:if test="not(@width)">
      	<xsl:attribute name="width">
        	<xsl:value-of select="concat($width,'px')"/>
      	</xsl:attribute>
      </xsl:if>

	<xsl:if test="not(@height)">
      	<xsl:attribute name="height">
        	<xsl:value-of select="concat($height,'px')"/>
      	</xsl:attribute>
      </xsl:if>
      <xsl:attribute name="preserveAspectRatio">
        <xsl:text>none</xsl:text>
      </xsl:attribute>

      <xsl:copy-of select="@*|node()"/>

    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
