package org.adi.lasting.flags;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.batik.transcoder.image.*;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;

public class SvgToImageConverter {
	
	private TranscoderInput ti;	
	private Transcoder transcoder;
	
	/**
	 * Constructor for SvgToImageConverter
	 * @param  fileIn  a svg file containing the flag to transform to an image
	 */
	public SvgToImageConverter(File fIn) throws Exception{		
		String svgURI = fIn.toURI().toURL().toString();
		ti = new TranscoderInput(svgURI);	
		
	}
	
	//this is the method that processes the transformation
	private void transformToImage(File file, String ext) throws FileNotFoundException,  IOException, TranscoderException{
		OutputStream os = new FileOutputStream(file);
		TranscoderOutput to = new TranscoderOutput(os);
		
		transcoder.transcode(ti, to);
		
		os.flush();
		os.close();
		
	}
	
	/**
	 * Method to transform a SVG to a JPEG image
	 * Because of the deprecation of JPEG in java this is also deprecated
	 * The file to be transformed is set in the constructor
	 * @param  fJPEG  a file where the JPEG image will be stored
	 * @deprecated
	 */
	@Deprecated
	public void transformToJPEG(File fJPEG) throws FileNotFoundException, IOException,TranscoderException{
		transcoder = new JPEGTranscoder();
		transformToImage(fJPEG, "JPG");
	}
	
	/**
	 * Method to transform a SVG to a PNG image
	 * The file to be transformed is set in the constructor
	 * @param  fJPEG  a file where the JPEG image will be stored
	 */
	public void transformToPNG(File fPNG) throws FileNotFoundException, IOException,TranscoderException{
		
		transcoder = new PNGTranscoder();
		transformToImage(fPNG, "PNG");
	}
}
