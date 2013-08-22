package wuziqi;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class Wuziqi {
	private Display display;
	private Shell shell;
	private Menu menu, gameMenu, PVCMenu;
	private MenuItem gameMenuItem, PVPMenuItem, PVCMenuItem, useBlackMenuItem,
			useWhiteMenuItem, giveUpMenuItem, exitMenuItem;
	private static int i;
	private static int j;
	private int x;
	private int y;
	private GC gc;
	private static boolean color;
	private boolean game;
	private boolean PVP;
	private static int[][] situation;
	AI ai;

	public Wuziqi() {
		ai = new AI();
		game = false;
		color = true;
		PVP = true;
		situation = new int[15][15];
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				situation[i][j] = 0;
			}
		}
		display = new Display();
		shell = new Shell(display, SWT.CLOSE |SWT.MIN|SWT.NO_REDRAW_RESIZE);
		Image image = new Image(display, "Icon.gif");
		shell.setImage(image);
		gc = new GC(shell);
		menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		gameMenuItem = new MenuItem(menu, SWT.CASCADE);
		gameMenuItem.setText("��Ϸ(&G)");
		gameMenu = new Menu(shell, SWT.DROP_DOWN);
		gameMenuItem.setMenu(gameMenu);
		{
			PVCMenuItem = new MenuItem(gameMenu, SWT.CASCADE);
			PVCMenuItem.setText("�Լ����Ϊ���ֵ�����Ϸ(&N)");
			{
				PVCMenu = new Menu(shell, SWT.DROP_DOWN);
				PVCMenuItem.setMenu(PVCMenu);
				useBlackMenuItem = new MenuItem(PVCMenu, SWT.PUSH);
				useBlackMenuItem.setText("ʹ�ú���");
				useBlackMenuItem.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub
						color = true;
						game = true;
						PVP = false;
						giveUpMenuItem.setEnabled(true);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
				useWhiteMenuItem = new MenuItem(PVCMenu, SWT.PUSH);
				useWhiteMenuItem.setText("ʹ�ð���");
				useWhiteMenuItem.addSelectionListener(new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub
						color = false;
						game = true;
						PVP = false;
						giveUpMenuItem.setEnabled(true);
						{
							if (color) {
								gc.setBackground(Display.getCurrent()
										.getSystemColor(SWT.COLOR_WHITE));
							} else {
								gc.setBackground(Display.getCurrent()
										.getSystemColor(SWT.COLOR_BLACK));
							}
							ai.calculate();
							x = ai.getx();
							y = ai.gety();
							ai.printscore();
							System.out.println();
							ai.refresh();
							if (!color) {
								situation[x][y] = 1;
							} else {
								situation[x][y] = 2;
							}

							/*
							 * for (i = 0; i < 15; i++) { for (j = 0; j < 15;
							 * j++) { System.out.print(situation[j][i] + " "); }
							 * System.out.println(); } System.out.println();
							 */

							gc.fillOval(x * 20, y * 20, 20, 20);
						}
						
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
			}
			PVPMenuItem = new MenuItem(gameMenu, SWT.PUSH);
			PVPMenuItem.setText("����Ϊ���ֵ�����Ϸ(&M)");
			PVPMenuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					game = true;
					PVP = true;
					giveUpMenuItem.setEnabled(true);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});

			new MenuItem(gameMenu, SWT.SEPARATOR);
			giveUpMenuItem = new MenuItem(gameMenu, SWT.CASCADE);
			giveUpMenuItem.setText("����(&R)");
			giveUpMenuItem.setEnabled(false);
			giveUpMenuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					MessageBox box1 = new MessageBox(shell, SWT.YES | SWT.NO);
					box1.setText("������Ϸ");
					box1.setMessage("�Ƿ�ȷʵ�����������Ϸ��");
					int choice = box1.open();
					if (choice == SWT.YES) {
						if (!color) {
							MessageBox box2 = new MessageBox(shell);
							box2.setText("��Ϸ����");
							box2.setMessage("��������������ʤ��");
							box2.open();
						} else {
							MessageBox box3 = new MessageBox(shell);
							box3.setText("��Ϸ����");
							box3.setMessage("��������������ʤ��");
							box3.open();
						}
						refresh();
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
			exitMenuItem = new MenuItem(gameMenu, SWT.CASCADE);
			exitMenuItem.setText("�˳�(&X)");
			exitMenuItem.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					System.exit(0);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
		shell.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				for (i = 0; i < 15; i++) {
					e.gc.drawLine(10, i * 20 + 10, 290, i * 20 + 10);
				}
				for (i = 0; i < 15; i++) {
					e.gc.drawLine(i * 20 + 10, 10, i * 20 + 10, 290);
				}
				for (i = 0; i <= 14; i++) {
					for (j = 0; j <= 10; j++) {
						if(situation[i][j]==1){
							e.gc.setBackground(Display.getCurrent()
									.getSystemColor(SWT.COLOR_BLACK));
							e.gc.fillOval(i * 20, j * 20, 20, 20);
						}
						if(situation[i][j]==2){
							e.gc.setBackground(Display.getCurrent()
									.getSystemColor(SWT.COLOR_WHITE));
							e.gc.fillOval(i * 20, j * 20, 20, 20);
						}
					}
				}
			}
		});
		shell.setBackgroundImage(new Image(display, "background.jpg"));
		// shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
		shell.setLayout(null);
		shell.setSize(305, 350);
		shell.setVisible(true);
		shell.setText("������");
		shell.open();
		setdown();
		while (!shell.isDisposed()) { // ���������û�йر���һֱѭ��
			if (!display.readAndDispatch()) { // ���display��æ
				display.sleep(); // ����
			}
		}
		display.dispose(); // ����display
	}

	private void setdown() {
		shell.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				if (game) {
					if (PVP) {
						if (situation[(e.x / 20 * 20) / 20][(e.y/ 20 * 20) / 20] != 1
								&& situation[(e.x/ 20 * 20) / 20][(e.y/ 20 * 20) / 20] != 2) {
							if (color) {
								gc.setBackground(Display.getCurrent()
										.getSystemColor(SWT.COLOR_BLACK));
							} else {
								gc.setBackground(Display.getCurrent()
										.getSystemColor(SWT.COLOR_WHITE));
							}
							x = e.x/ 20 * 20;
							y = e.y / 20 * 20;
							if (color) {
								situation[x / 20][y / 20] = 1;
							} else {
								situation[x / 20][y / 20] = 2;
							}
							gc.fillOval(x, y, 20, 20);
							color = !color;
							switch (win()) {
							case 0:
								break;
							case 1:
								MessageBox box4 = new MessageBox(shell);
								box4.setText("��Ϸ����");
								box4.setMessage("�����ʤ��");
								box4.open();
								refresh();
								break;
							case 2:
								MessageBox box5 = new MessageBox(shell);
								box5.setText("��Ϸ����");
								box5.setMessage("�����ʤ��");
								box5.open();
								refresh();
								break;
							}
						}
					}
					// ��������ս
					else {
						if (situation[(e.x/ 20 * 20) / 20][(e.y / 20 * 20) / 20] != 1
								&& situation[(e.x / 20 * 20) / 20][(e.y/ 20 * 20) / 20] != 2) {
							// AIȡ����
							if (color) {
								gc.setBackground(Display.getCurrent()
										.getSystemColor(SWT.COLOR_BLACK));
							} else {
								gc.setBackground(Display.getCurrent()
										.getSystemColor(SWT.COLOR_WHITE));
							}
							x = e.x/ 20 * 20;
							y = e.y/ 20 * 20;
							if (color) {
								situation[x / 20][y / 20] = 1;
							} else {
								situation[x / 20][y / 20] = 2;
							}
							gc.fillOval(x, y, 20, 20);
							if (color) {
								gc.setBackground(Display.getCurrent()
										.getSystemColor(SWT.COLOR_WHITE));
							} else {
								gc.setBackground(Display.getCurrent()
										.getSystemColor(SWT.COLOR_BLACK));
							}
							ai.calculate();
							x = ai.getx();
							y = ai.gety();
							ai.printscore();
							System.out.println();
							ai.refresh();
							if (!color) {
								situation[x][y] = 1;
							} else {
								situation[x][y] = 2;
							}

							/*
							 * for (i = 0; i < 15; i++) { for (j = 0; j < 15;
							 * j++) { System.out.print(situation[j][i] + " "); }
							 * System.out.println(); } System.out.println();
							 */

							gc.fillOval(x * 20, y * 20, 20, 20);
						}
						/*
						 * // AIȡ���� else { gc.setBackground(Display.getCurrent()
						 * .getSystemColor(SWT.COLOR_BLACK)); ai.calculate(); x
						 * = ai.getx(); y = ai.gety(); ai.printscore();
						 * System.out.println(); ai.refresh(); if (!color) {
						 * situation[x][y] = 1; } else { situation[x][y] = 2; }
						 * 
						 * for (i = 0; i < 15; i++) { for (j = 0; j < 15; j++) {
						 * System.out.print(situation[j][i] + " "); }
						 * System.out.println(); } System.out.println();
						 * 
						 * gc.fillOval(x * 20, y * 20, 20, 20);
						 * 
						 * gc.setBackground(Display.getCurrent()
						 * .getSystemColor(SWT.COLOR_WHITE)); x = (e.x + 5 - 10)
						 * / 20 * 20; y = (e.y + 5 - 10) / 20 * 20; if (color) {
						 * situation[x / 20][y / 20] = 1; } else { situation[x /
						 * 20][y / 20] = 2; } gc.fillOval(x, y, 20, 20);
						 * 
						 * }
						 */
						switch (win()) {
						case 0:
							break;
						case 1:
							MessageBox box4 = new MessageBox(shell);
							box4.setText("��Ϸ����");
							box4.setMessage("�����ʤ��");
							box4.open();
							refresh();
							break;
						case 2:
							MessageBox box5 = new MessageBox(shell);
							box5.setText("��Ϸ����");
							box5.setMessage("�����ʤ��");
							box5.open();
							refresh();
							break;
						}
					}
				}
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void refresh() {
		game = false;
		color = true;
		PVP = true;
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				situation[i][j] = 0;
			}
		}
		giveUpMenuItem.setEnabled(false);
		shell.redraw();

	}

	private int win() {
		int a;
		for (a = 1; a <= 2; a++) {
			for (i = 0; i <= 10; i++) {
				for (j = 0; j <= 10; j++) {
					// "\"��
					if (situation[i][j] == a && situation[i + 1][j + 1] == a
							&& situation[i + 2][j + 2] == a
							&& situation[i + 3][j + 3] == a
							&& situation[i + 4][j + 4] == a) {
						return a;
					}
				}
			}
			for (i = 14; i >= 4; i--) {
				for (j = 0; j <= 10; j++) {
					// "/"��
					if (situation[i][j] == a && situation[i - 1][j + 1] == a
							&& situation[i - 2][j + 2] == a
							&& situation[i - 3][j + 3] == a
							&& situation[i - 4][j + 4] == a) {
						return a;
					}
				}
			}
			for (i = 0; i <= 10; i++) {
				for (j = 0; j <= 14; j++) {
					// "����"��
					if (situation[i][j] == a && situation[i + 1][j] == a
							&& situation[i + 2][j] == a
							&& situation[i + 3][j] == a
							&& situation[i + 4][j] == a) {
						return a;
					}
				}
			}
			for (i = 0; i <= 14; i++) {
				for (j = 0; j <= 10; j++) {
					// "|"��
					if (situation[i][j] == a && situation[i][j + 1] == a
							&& situation[i][j + 2] == a
							&& situation[i][j + 3] == a
							&& situation[i][j + 4] == a) {
						return a;
					}
				}
			}
		}
		return 0;
	}

	public static void getsituation(int array[][]) {
		for (i = 0; i < 15; i++) {
			for (j = 0; j < 15; j++) {
				array[i][j] = situation[i][j];
			}
		}
	}

	public static int getcolor() {
		if (color) {
			return 2;
		}
		return 1;
	}

	public static void main(String[] args) {
		new Wuziqi();
	}

}
