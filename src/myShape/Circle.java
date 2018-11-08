package myShape;

import org.eclipse.swt.graphics.GC;

public class Circle implements IShape {

	private int top;
	private int left;
	private int width;
	private int height;
	private GC gcMain;
	
	public Circle(){
		
	}
	
	public Circle(int top ,int left,int width,int height,GC gc){
		this.top = top;
		this.left = left;
		this.width = width;
		this.height = height;
		this.gcMain = gc;
	}
	
	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public GC getGcMain() {
		return gcMain;
	}

	public void setGcMain(GC gcMain) {
		this.gcMain = gcMain;
	}

	public static String getToolText(){
		return "т╡пн";
	}
	@Override
	public void Darw() {
		gcMain.drawOval(top, left, width, height);
	}

}
