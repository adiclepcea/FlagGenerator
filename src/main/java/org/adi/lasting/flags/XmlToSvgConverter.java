package org.adi.lasting.flags;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

import javax.xml.transform.stream.StreamResult;


public class XmlToSvgConverter implements ISvgConverter<File> {

	private StreamSource style;
	private DocumentBuilderFactory factory;
	private TransformerFactory transformerFactory;
	
	/**
	 * Constructor for XmlToSvgConverter 
	 */
	public XmlToSvgConverter() throws IOException{
		
		InputStream iIn = getClass().getClassLoader().getResourceAsStream("org/adi/lasting/flags/flag.xslt");
		//File fIn = new File(getClass().getResource("flag.xslt").getFile());
		style = new StreamSource(iIn);
		
		factory = DocumentBuilderFactory.newInstance();
		transformerFactory = TransformerFactory.newInstance();
	}
	
	/**
	 * This is the method that performs the actual transformation
	 * @param dataFile  - the file where the xml file lies - the xslt transformation will be aplied on this
	 * @param width 	- the width of the generated SVG
	 * @param height 	- the height of the generated SVG
	 * @param os 		- the OutputStream where the result will be written
	 */
	private boolean tryConvertToSvgObject(File dataFile, int width, int height, OutputStream os) throws Exception{
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		Document doc = docBuilder.parse(dataFile);
		DOMSource source = new DOMSource(doc);
		Transformer transformer = transformerFactory.newTransformer(style);
		transformer.setParameter("width", width);
		transformer.setParameter("height", height);		
		StreamResult result = new StreamResult(os);
		transformer.transform(source, result);
		return true;
	}
	
	/**
	 * Method to generate a SVG file from the input xml flag file 
	 * @param  dataFile -  the file where the xml file lies - the xslt transformation will be aplied on this
	 * @param  width 	-  the width of the generated SVG image
	 * @param  height	-  the height of the generated SVG image
	 */
	public File convertToSvgFile(File dataFile, int width, int height) throws Exception {
		
		String outFile = FlagGenerator.getNextFreeFileName(dataFile.getName().substring(0,(dataFile.getName().length()-4))+".svg", ".svg");
				
		System.out.println(outFile);
		
		File fOut = new File(outFile);
		
		FileOutputStream fos = new FileOutputStream(fOut);
		tryConvertToSvgObject(dataFile, width, height, fos);
		fos.close();
		
		return fOut;
		
	}

	/**
	 * Method to generate a SVG string from the input xml flag file 
	 * @param  dataFile -  the file where the xml file lies - the xslt transformation will be aplied on this
	 * @param  width 	-  the width of the generated SVG image
	 * @param  height	-  the height of the generated SVG image
	 */
	public String convertToSvgString(File dataFile, int width, int height) throws Exception{
		
		
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
			tryConvertToSvgObject(dataFile, width, height, baos);
			String s = baos.toString();
			baos.close();
			return s;
		}catch(Exception ex){
			throw ex;
		}
	}

}
