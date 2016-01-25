package org.adi.lasting.flags;

import org.junit.Test;
//import org.adi.lasting.flags.FlagGenerator;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;

public class FlagGeneratorTest{
	String size, badSize;
	/**
	 * TODO 
	 * Add more tests - but for now is ok.
	 */
	
	@Before
	public void initParams(){
		size = "600x300";
		badSize = "220sxd122";
	}
	
	@Test
	public void getWidthAndHeightFromSizeShouldReturnOK(){		
		FlagGenerator fg = new FlagGenerator(new MockSvgConverter());
		fg.getWidthAndHeightFromSizeString(size);
		
		assertEquals(600, fg.getWidth());		
		assertEquals(300, fg.getHeight());
	}
	
	@Test
	public void getWidthFromSizeShouldReturnMinus1(){
		
		FlagGenerator fg = new FlagGenerator(new MockSvgConverter());
		fg.getWidthAndHeightFromSizeString(badSize);
		
		assertEquals(-1, fg.getWidth());		
		assertEquals(-1, fg.getHeight());		
		
	}
	
	//we could use this for more tests ;)
	class MockSvgConverter implements ISvgConverter<File>{

		@Override
		public File convertToSvgFile(File e, int width, int height) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String convertToSvgString(File e, int width, int height) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
