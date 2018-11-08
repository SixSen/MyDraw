package myShape;

import org.eclipse.swt.graphics.GC;

public interface IShape {

	void Darw();
	public int getTop();
	public void setTop(int top) ;
	public int getLeft();
	public void setLeft(int left) ;

	public int getWidth() ;

	public void setWidth(int width);

	public int getHeight() ;
	public void setHeight(int height) ;

	public GC getGcMain() ;

	public void setGcMain(GC gcMain);
	
}
