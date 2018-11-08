package myBoard;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import myShape.IShape;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.custom.CCombo;

public class Mydraw {
	private static int startX = 0;
	private static int startY = 0;
	private static boolean leftButtonDown = false;
	private static int lastWidth = 0;
	private static int lastHeight = 0;
	private static GC gcMain = null;
	private static String shapeType = "myShape.Circle";

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		java.util.List<Class<?>> listClass = null;
		String pkg = "myShape";
		listClass = ClassUtil.getClassList(pkg, true, null);

		ArrayList<String> shapeTypes = new ArrayList<String>();
		for (Object object : listClass) {
			String name = ((Class<?>) object).getName();
			if (!name.equals("myShape.IShape")) {
				shapeTypes.add(name);

			}

		}

		Display display = Display.getDefault();
		Shell shlMydraw = new Shell();

		Board board = new Board();

		shlMydraw.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {

				board.refresh();

			}
		});
		gcMain = new GC(shlMydraw);

		shlMydraw.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent arg0) {
				if (leftButtonDown) {
					gcMain.setLineStyle(SWT.LINE_DOT);
					gcMain.setForeground(shlMydraw.getBackground());
					gcMain.drawRectangle(startX, startY, lastWidth, lastHeight);
					gcMain.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
					gcMain.drawRectangle(startX, startY, arg0.x - startX, arg0.y - startY);
					lastWidth = arg0.x - startX;
					lastHeight = arg0.y - startY;
					gcMain.setLineStyle(SWT.LINE_SOLID);
					gcMain.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
				}

			}
		});
		shlMydraw.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				shlMydraw.setCursor(new Cursor(null, SWT.CURSOR_ARROW));
				if (e.button == 1) {
					int width = e.x - startX;
					int height = e.y - startY;

					// Rect rect = new Rect(startX, startY, width, height,
					// gcMain);
					// rect.Darw();
					// board.insertShape(rect);
					// leftButtonDown = false;
					// board.refresh();

					gcMain.setLineStyle(SWT.LINE_DOT);
					gcMain.setForeground(shlMydraw.getBackground());
					gcMain.drawRectangle(startX, startY, width, height);
					gcMain.setLineStyle(SWT.LINE_SOLID);
					gcMain.setForeground(display.getSystemColor(SWT.COLOR_BLACK));

					IShape shape = null;

					leftButtonDown = false;
					try {
						Class<?> shapeClass = Class.forName(shapeType);
						Object oShape = shapeClass.newInstance();
						shape = (IShape) oShape;

						shape.setTop(startX);
						shape.setLeft(startY);
						shape.setWidth(e.x - startX);
						shape.setHeight(e.y - startY);
						shape.setGcMain(gcMain);

					} catch (Exception ex) {
						shape = null;
						System.err.println(ex);
					}
					if (shape != null) {
						board.insertShape(shape);

						board.refresh();
					}

					// switch (shapeType) {
					// case 1:
					// shape = new Rect(startX, startY, width, height, gcMain);
					// break;
					// case 2:
					// shape = new Circle(startX,startY,width,height,gcMain);
					// break;
					//
					// default:
					// break;
					// }
					// //Circle circle = new
					// Circle(startX,startY,width,height,gcMain);
					// board.insertShape(shape);
					// leftButtonDown = false;
					// board.refresh();
				}

			}

			@Override
			public void mouseDown(MouseEvent e) {
				shlMydraw.setCursor(new Cursor(null, SWT.CURSOR_CROSS));
				if (e.button == 1) {
					startX = e.x;
					startY = e.y;
					leftButtonDown = true;
				}

			}
		});
		shlMydraw.setSize(800, 600);
		shlMydraw.setText("Mydraw");

		Button btnSave = new Button(shlMydraw, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				FileDialog dialog = new FileDialog(shlMydraw, SWT.SAVE);
				dialog.setFilterExtensions(new String[] { ".mydraw" });
				String file = dialog.open();
				if (file != null) {
					shlMydraw.setText((new File(file.trim())).getName());
					board.save(file);
				}
			}
		});
		btnSave.setBounds(10, 513, 86, 30);
		btnSave.setText("Save");

		Button btnOpen = new Button(shlMydraw, SWT.NONE);
		btnOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				FileDialog dialog = new FileDialog(shlMydraw, SWT.OPEN);
				String file = dialog.open();
				shlMydraw.setText((new File(file.trim())).getName());
				board.read(file, gcMain);
				board.refresh();
			}
		});
		btnOpen.setText("Open");
		btnOpen.setBounds(102, 513, 86, 30);

		Button button = new Button(shlMydraw, SWT.RADIO);
		button.setSelection(true);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				gcMain.setLineWidth(2);

			}
		});
		button.setBounds(653, 523, 119, 20);
		button.setText("\u7EC6\u7EBF");

		Button button_1 = new Button(shlMydraw, SWT.RADIO);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				gcMain.setLineWidth(5);
			}
		});
		button_1.setText("\u7C97\u7EBF");
		button_1.setBounds(528, 523, 119, 20);
		

		////////////////////////////////////////////////////////
		// add button by shapeTypes
		int indexButton = 0;
		for (String strClass : shapeTypes) {
			Button btn = new Button(shlMydraw, SWT.NONE);
			btn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					shapeType = strClass;
				}
			});
			btn.setBounds(84 * indexButton, 0, 80, 27);
			indexButton++;
			try {
				Class<?> shapeClass = Class.forName(strClass);
				btn.setText(shapeClass.getTypeName());
				btn.setData("shapeType", strClass);
				Method method = shapeClass.getMethod("getToolText");

				btn.setText(method.invoke(null, null).toString());

			} catch (Exception e) {
				btn.setText(strClass);
				btn.setData("shapeType", strClass);
			}

		}
		////////////////////////////////////////////////////////

		shlMydraw.open();
		shlMydraw.layout();
		while (!shlMydraw.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
