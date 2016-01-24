package org.adi.lasting.flags;

import org.junit.Test;
//import org.adi.lasting.flags.FlagGenerator;

import static org.junit.Assert.*;

import org.junit.Before;

public class FlagGeneratorTest{
	String size, badSize;
	@Before
	public void initParams(){
		size = "600x300";
		badSize = "220sxd122";
	}
	
	@Test
	public void getWidthAndHeightFromSizeShouldReturnOK(){		
		FlagGenerator fg = new FlagGenerator();
		fg.getWidthAndHeightFromSize(size);
		
		assertEquals(600, fg.getWidth());		
		assertEquals(300, fg.getHeight());
	}
	
	@Test
	public void getWidthFromSizeShouldReturnMinus1(){
		
		FlagGenerator fg = new FlagGenerator();
		fg.getWidthAndHeightFromSize(badSize);
		
		assertEquals(-1, fg.getWidth());		
		assertEquals(-1, fg.getHeight());		
		
	}
	
}
