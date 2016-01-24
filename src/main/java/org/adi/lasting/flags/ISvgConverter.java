package org.adi.lasting.flags;

import java.io.File;

//perhaps later we decide to use Yaml or Json as input instead of Xml
//so we use an interface
public interface ISvgConverter <E> {
	public File convertToSvgFile(E e,int width, int height) throws Exception;
	public String convertToSvgString(E e, int width, int height) throws Exception;
}
