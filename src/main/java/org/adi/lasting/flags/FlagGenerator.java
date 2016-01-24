package org.adi.lasting.flags;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

public class FlagGenerator{
	
	private int width, height;
	
	public FlagGenerator(){
		
	}
	
	//this should be placed in an Utils file, 
	//but since it is the only one of its kind we will let this stay here
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
	
	public String generateSVGStringFromXml(File fIn) throws Exception{
		ISvgConverter<File> xmlToSvg = new XmlToSvgConverter();
		return xmlToSvg.convertToSvgString(fIn,getWidth(),getHeight());
	}
	
	public File generateSVGFileFromXml(File fIn) throws Exception{
		XmlToSvgConverter xmlToSvg = new XmlToSvgConverter();
		return xmlToSvg.convertToSvgFile(fIn,getWidth(),getHeight());
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	//we get the width and height from the supplied size parameter
	protected void getWidthAndHeightFromSize(String size){		
		
		String[] rez = size.split("x");		
		
		width = isInteger(rez[0],10) ? Integer.parseInt(rez[0]):-1;
		
		height = isInteger(rez[1],10) ? Integer.parseInt(rez[1]):-1;
		
	}
	
	
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
		FlagGenerator fg = new FlagGenerator();
		fg.getWidthAndHeightFromSize(args[0]);
		
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
			System.out.println("Generting intermediary SVG file...");
			File fSvg=fg.generateSVGFileFromXml(fIn);
			
			//we could also use this string and load a document in the batik transcoder
			//String sSvg = fg.generateSVGStringFromXml(fIn);
			
			System.out.println("Generated "+Paths.get(fSvg.getName()).toUri().toURL().toString());
			
			System.out.println("Generting PNG file ...");
			SvgToImageConverter svgToImage = new SvgToImageConverter(fSvg);
			String outFileName = FlagGenerator.getNextFreeFileName("out.png", ".png");
			File fOut = new File(outFileName);
			svgToImage.transformToPNG(fOut);
			System.out.printf("Generated %s file. \r\nHave a good day! \r\n",outFileName);
			
		}catch(Exception ex){
			//this could be logged
			System.out.println("Ooops I cannot perform as expected because:");
			ex.printStackTrace();
			
		}
				
	}
	
}
