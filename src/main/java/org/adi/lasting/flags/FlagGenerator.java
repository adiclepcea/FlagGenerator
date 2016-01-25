package org.adi.lasting.flags;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FlagGenerator{
	
	private int width, height;
	
	private ISvgConverter<File> flagToSvg;
	
	
	public FlagGenerator(ISvgConverter<File> flagConverter){
		this.flagToSvg = flagConverter;
	}
	
	/**
	 * This function will return a string containing the 
	 * next available file name in the current directory 
	 * @param  fileName -  the desired name of the file
	 * @param  ext 		-  the extension of the desired file
	 * for example if you have a file called out.jpg and one called out1.jpg in 
	 * the current folder and you want to obtain the next available out.jpg file:	
	 * <pre>
	 * {@code 
	 *    getNextFreeFileName("out.jpg",".jpg")}
	 * </pre>
	 * will return out2.jpg
	 */
	public static String getNextFreeFileName(String fileName, String ext) throws Exception{
		//we must have file with ext as input
		if(!fileName.toUpperCase().endsWith(ext.toUpperCase())){
			System.out.printf("\"%s\" is not an %s file",fileName,ext);
			return null;
		}
		//we use the name of the initial file to get a suggestive name for the output file
		String initialName = fileName.substring(0,(fileName.length()-ext.length()));
		int cnt = 0;
		File fOut = new File(initialName+ext);
		
		//we try to find a name that does not belong to an existent file
		while(fOut.exists() && cnt<Integer.MAX_VALUE){
			fOut = new File(initialName+(++cnt)+ext);
		}
		
		//we could not find a valid name
		if(fOut.exists()){
			throw new Exception("I could not find a place for a new file");
		}
		
		return fOut.getName();
	}
	/**
	 * This will generate a SVG string from an Flag file
	 * @param fIn - File containing a flag representation
	 * @return - String - the resulted SVG
	 * for a File result see {@link #generateSVGFileFromXml(File) generateSVGFileFromXml(File f)}
	 * @throws Exception
	 */
	public String generateSVGString(File fIn) throws Exception{		
		return flagToSvg.convertToSvgString(fIn,getWidth(),getHeight());
	}
	/**
	 * This will generate a SVG File from an Flag file
	 * @param fIn - File containing a flag representation
	 * @return	- File - the resulted SVG file
	 * for a String result see {@link #generateSVGStringFromXml(File) generateSVGStringFromXml(String f)}
	 * @throws Exception
	 */
	public File generateSVGFile(File fIn) throws Exception{
		return flagToSvg.convertToSvgFile(fIn,getWidth(),getHeight());
	}
	
	/**
	 * This will generate a PNG file from the SVG file received as paramater
	 * The PNG file will be of the form out[x].png
	 * @param fSvg - the SVG file to use as source
	 * @return the generated PNG file
	 * @throws Exception - all the exceptions from SvgToImageConverter 
	 * @see SvgToImageConverter
	 */
	public File generatePNGFileFromSVG(File fSvg) throws Exception{
		SvgToImageConverter svgToImage = new SvgToImageConverter(fSvg);
		String outFileName = FlagGenerator.getNextFreeFileName("out.png", ".png");
		File fOut = new File(outFileName);
		svgToImage.transformToPNG(fOut);
		
		return fSvg;
	}
	/**
	 * returns the desired width of the output image 
	 * @return integer - the width as calculated by {@link #getWidthAndHeightFromSize(String)}
	 */
	public int getWidth(){
		return width;
	}
	/**
	 * returns the desired height of the output image 
	 * @return integer - the height as calculated by {@link #getWidthAndHeightFromSize(String)}
	 */
	public int getHeight(){
		return height;
	}
	
	//we get the width and height from the supplied size parameter
	/**
	 * This will find the width and height from a string representing the 
	 * size (dimension) of the desired output image
	 * @param size - the string representing the size (ie. <i>800x600</i>)
	 * @see {@link #getHeight() getHeight() }, {@link #getWidth() getWidth() }
	 */
	protected void getWidthAndHeightFromSizeString(String size){		
		
		String[] rez = size.split("x");		
		width = isInteger(rez[0],10) ? Integer.parseInt(rez[0]):-1;
		
		height = isInteger(rez[1],10) ? Integer.parseInt(rez[1]):-1;
		
	}
	
	/**
	 * This function tests a string to see if it is a valid integer representation 
	 * @param s		- the string to be tested
	 * @param radix - the radix of the transform (the base). It should be 10 for most cases
	 * @return true if the input parameter <b>s</b> is a valid representation for an integer
	 */
	public boolean isInteger(String s, int radix) {
	    Scanner sc = new Scanner(s.trim());
	    if(!sc.hasNextInt(radix)){ 
	    	sc.close();
	    	return false;	 
	    }
	    sc.nextInt(radix);
	    boolean rez = !sc.hasNext();
	    sc.close();
	    return rez;
	}
	
	public static void main(String args[]){
		
		if(args.length<2){
			System.out.println("Please use me with these parameters: size flagDescriptionFile");
			System.out.println("(ex.: \"200x100 myFlag.xml\" for a flag specified in myFlag.xml 200 width and 100 height)");
			System.exit(1);
		}
		
		//we instantiate a Xml adapter to decorate out FlagGenerator class with
		ISvgConverter<File> flagToSvg = null;
		try{
			 flagToSvg = new XmlToSvgConverter();
		}catch(IOException ioex){
			System.out.println("Internal error - something happened to my xslt file !");
			System.exit(1);
		}
		FlagGenerator fg = new FlagGenerator(flagToSvg);
		fg.getWidthAndHeightFromSizeString(args[0]);
		
		if(fg.getWidth()<0 || fg.getHeight()<0){
			System.out.printf("\"%s\" is an invalid size.\r\n",args[0]);
			System.exit(1);
		}
		
		File fIn = new File(args[1]);
		
		if(!fIn.exists()){
			System.out.printf("I cannot find the \"%s\" file.\r\n",args[1]);
			System.exit(1);
		}
		
		try{
			
			System.out.println("Generating intermediary SVG file...");
			File fSvg=fg.generateSVGFile(fIn);
			
			/*we could also use this string and load a document in the batik transcoder
			*	String sSvg = fg.generateSVGString(fIn);
			*/
			System.out.printf("Generated %s \r\n"+fSvg.getName());
			
			System.out.println("Generating PNG file ...");
			
			File fPNG = fg.generatePNGFileFromSVG(fSvg);
			
			System.out.printf("Generated %s file. \r\nHave a good day! \r\n",fPNG.getName());
			
		}catch(Exception ex){
			//this could be logged and we should also test for individual exceptions
			System.out.println("Ooops I cannot perform as expected because:");
			ex.printStackTrace();
			
		}
				
	}
	
}
