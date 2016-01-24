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
		
	//private SVGIcon icon;
	private TranscoderInput ti;	
	private Transcoder transcoder;
	
	public SvgToImageConverter(File fIn) throws Exception{		
		String svgURI = fIn.toURI().toURL().toString();
		ti = new TranscoderInput(svgURI);	
		
	}
	
	public void transformToImage(File file, String ext) throws FileNotFoundException,  IOException, TranscoderException{
		OutputStream os = new FileOutputStream(file);
		TranscoderOutput to = new TranscoderOutput(os);
		
		transcoder.transcode(ti, to);
		
		os.flush();
		os.close();
		
	}
	
	public void transformToJPEG(File fJPEG) throws FileNotFoundException, IOException,TranscoderException{
		transcoder = new JPEGTranscoder();
		transformToImage(fJPEG, "JPG");
	}
	
	public void transformToPNG(File fPNG) throws FileNotFoundException, IOException,TranscoderException{
		
		transcoder = new PNGTranscoder();
		transformToImage(fPNG, "PNG");
	}
}
