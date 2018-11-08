package myBoard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.GC;

import myShape.IShape;

public class Board {
	private List<IShape> shapes;

	public Board() {
		shapes = new ArrayList<IShape>();
	}

	public void clear() {
		shapes = new ArrayList<IShape>();
	}

	public void insertShape(IShape shape) {
		shapes.add(shape);
	}

	public void refresh() {
		for (IShape shape : shapes) {
			shape.Darw();
		}
	}

	public void save(String file) {

		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (IShape shape : shapes) {
				bw.write(shape.getClass().getName() + "\n");
				bw.write(shape.getTop() + "\n");
				bw.write(shape.getLeft() + "\n");
				bw.write(shape.getWidth() + "\n");
				bw.write(shape.getHeight() + "\n");
			}
			bw.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void read(String file, GC gcMain) {

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			while (true) {
				String nextline = br.readLine();
				if (nextline == null)
					break;
				IShape shape;
				Class<?> shapeClass = Class.forName(nextline);
				Object OShape = shapeClass.newInstance();
				shape = (IShape) OShape;
				String nextline1 = br.readLine();
				shape.setTop(Integer.parseInt(nextline1));
				String nextline2 = br.readLine();
				shape.setLeft(Integer.parseInt(nextline2));
				String nextline3 = br.readLine();
				shape.setWidth(Integer.parseInt(nextline3));
				String nextline4 = br.readLine();
				shape.setHeight(Integer.parseInt(nextline4));
				shape.setGcMain(gcMain);
				if (shape != null) {
					insertShape(shape);
					refresh();
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
