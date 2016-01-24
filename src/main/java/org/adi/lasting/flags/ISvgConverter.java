package org.adi.lasting.flags;

import java.io.File;

/**
 * Interface meant to provide general methods for transformation to SVG 
 * For now there is an implementation to XML
 * You could also adapt it to other formats(ie. Yaml or Json)
 */
public interface ISvgConverter <E> {
	/**
	 * Method to convert E type to a SVG file 
	 * @param  e 		-  the object to be transformed to SVG
	 * @param  width 	-  the width of the generated SVG image
	 * @param  height	-  the height of the generated SVG image
	 */
	public File convertToSvgFile(E e,int width, int height) throws Exception;
	
	/**
	 * Method to convert E type to a SVG string 
	 * @param  e 		-  the object to be transformed to SVG
	 * @param  width 	-  the width of the generated SVG image
	 * @param  height	-  the height of the generated SVG image
	 */
	public String convertToSvgString(E e, int width, int height) throws Exception;
}
