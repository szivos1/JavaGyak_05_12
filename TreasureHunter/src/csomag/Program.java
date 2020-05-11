package csomag;

import java.awt.*;

import javax.swing.border.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Program extends JFrame {

	private JPanel contentPane;
	private Color paleYellow = new Color(240, 238, 153);
	private JLabel[][] Field = new JLabel[12][12];
	private int[][] FieldInfo = new int[12][12];
	private JTextField textFileName;
	private JLabel lblOK, lblNO;
	private File sourceFile;
	private JButton btnReset;
	private String direction = "";
	private int inX = 0, inY = 0;
	private JTextField textSteps;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Program frame = new Program();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public void instantiation() {
		for (int j = 0; j < 12; j++)
			for (int i = 0; i < 12; i++)
				Field[i][j] = new JLabel();
	}

	public void trackDrawer() {

		for (int i = 0; i < 12; i++) {
			if (i == 0)
				Field[i][0].setIcon(new ImageIcon("pict\\topLeftCorner.png"));
			else if (i == 11)
				Field[i][0].setIcon(new ImageIcon("pict\\topRightCorner.png"));
			else
				Field[i][0].setIcon(new ImageIcon("pict\\topLine.png"));
			contentPane.add(Field[i][0]);
			Field[i][0].setBounds(10 + i * 50, 10, 50, 50);
		}
		for (int j = 1; j < 11; j++) {
			for (int i = 0; i < 12; i++) {
				if (i == 0)
					Field[i][j].setIcon(new ImageIcon("pict\\rightLine.png"));
				if (i == 11)
					Field[i][j].setIcon(new ImageIcon("pict\\leftLine.png"));
				if (i > 0 && i < 11)
					Field[i][j].setIcon(new ImageIcon("pict\\trackBase.png"));
				contentPane.add(Field[i][j]);
				Field[i][j].setBounds(10 + i * 50, 10 + j * 50, 50, 50);
			}
		}
		for (int i = 0; i < 12; i++) {
			if (i == 0)
				Field[i][11].setIcon(new ImageIcon("pict\\bottomLeftCorner.png"));
			else if (i == 11)
				Field[i][11].setIcon(new ImageIcon("pict\\bottomRightCorner.png"));
			else
				Field[i][11].setIcon(new ImageIcon("pict\\bottomLine.png"));
			contentPane.add(Field[i][11]);
			Field[i][11].setBounds(10 + i * 50, 560, 50, 50);
		}

	}
	
	public void trackDisplay() {
		String s = "", sxk = "", syk = "", stp = "";
		int index = 0, index2 = 0, index3 = 0, xk = 0, yk = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader(sourceFile));
			s = in.readLine();
			while (s != null) {

				index = s.indexOf("Entrance:");
				if (index != -1) {
					index2 = s.indexOf(",");
					index3 = s.indexOf(" ");
					sxk = s.substring(index3 + 1, index2);
					syk = s.substring(index2 + 1, s.length());
					xk = goodInt(sxk);
					yk = goodInt(syk);
					if (xk == 0) {Field[0][yk].setIcon(new ImageIcon("pict\\entranceLeft.png")); direction = "LR";}
					if (xk == 11) {Field[11][yk].setIcon(new ImageIcon("pict\\entranceRight.png")); direction = "RL";}
					if (yk == 0) {Field[xk][0].setIcon(new ImageIcon("pict\\entranceTop.png")); direction = "TB";}
					if (yk == 11) {Field[xk][11].setIcon(new ImageIcon("pict\\entranceBottom.png")); direction = "BT";}
					inX=xk;
					inY=yk;
				}
				
				index = s.indexOf("Exit:");
				if (index != -1) {
					index2 = s.indexOf(",");
					index3 = s.indexOf(" ");
					sxk = s.substring(index3 + 1, index2);
					syk = s.substring(index2 + 1, s.length());
					xk = goodInt(sxk);
					yk = goodInt(syk);
					if (xk == 0) {Field[0][yk].setIcon(new ImageIcon("pict\\entranceRight.png")); direction = "LR";}
					if (xk == 11) {Field[11][yk].setIcon(new ImageIcon("pict\\entranceLeft.png")); direction = "RL";}
					if (yk == 0) {Field[xk][0].setIcon(new ImageIcon("pict\\entranceBottom.png")); direction = "TB";}
					if (yk == 11) {Field[xk][11].setIcon(new ImageIcon("pict\\entranceTop.png")); direction = "BT";}	
				}
				
				index = s.indexOf("Treasure:");
				if (index != -1) {
					index2 = s.indexOf(",");
					index3 = s.indexOf(" ");
					sxk = s.substring(index3 + 1, index2);
					syk = s.substring(index2 + 1, s.length());
					xk = goodInt(sxk);
					yk = goodInt(syk);
					Field[xk][yk].setIcon(new ImageIcon("pict\\treasure.png"));
				}
				

				index = s.indexOf("Traps:");
				if (index != -1) {
					index = s.indexOf(" ");
					stp = s.substring(index + 1, s.length());
					String[] pks = stp.split(" ");
					for (String sc : pks) {
						index2 = sc.indexOf(",");
						sxk = sc.substring(0, index2);
						syk = sc.substring(index2 + 1, sc.length());
						xk = goodInt(sxk);
						yk = goodInt(syk);
						Field[xk][yk].setIcon(new ImageIcon("pict\\trap.png"));
					}
				}
				
				s = in.readLine();
			}
			in.close();
		} catch (IOException ioe) {
			SM("trackDisplay: " + ioe.getMessage(), 0);
		}
		
	}

	public static void SM(String msg, int tipus) {
		JOptionPane.showMessageDialog(null, msg, "Program üzenet", tipus);
	}

	public int goodInt(String s) {
		try {
			int x = Integer.parseInt(s);
			return x;
		} catch (NumberFormatException e) {
			return -5;
		}
	}

	public boolean checkFile() {
		boolean ok = true;
		String s = "", sxk = "", syk = "", stp = "";
		int index = 0, index2 = 0, index3 = 0, xk = 0, yk = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader(sourceFile));
			s = in.readLine();
			while (ok && s != null) {

				index = s.indexOf("Entrance:");
				if (index != -1 && ok) {
					index2 = s.indexOf(",");
					index3 = s.indexOf(" ");
					sxk = s.substring(index3 + 1, index2);
					syk = s.substring(index2 + 1, s.length());
					xk = goodInt(sxk);
					yk = goodInt(syk);
					ok = (xk > -1 && xk < 12) ? true : false;
					ok = (ok && yk > -1 && yk < 12) ? true : false;
					ok = (ok && (xk == 0 || xk == 11) && (yk == 0 || yk == 11)) ? false : true;
					if (ok)
						FieldInfo[xk][yk] = 4;
					if (!ok)
						SM("The entrance is incorrectly specified in the file!", 0);
				}
				if (ok) {
					index = s.indexOf("Exit:");
					if (index != -1) {
						index2 = s.indexOf(",");
						index3 = s.indexOf(" ");
						sxk = s.substring(index3 + 1, index2);
						syk = s.substring(index2 + 1, s.length());
						xk = goodInt(sxk);
						yk = goodInt(syk);
						ok = (xk > -1 && xk < 12) ? true : false;
						ok = (ok && yk > -1 && yk < 12) ? true : false;
						ok = (ok && (xk == 0 || xk == 11) && (yk == 0 || yk == 11)) ? false : true;
						if (ok)
							FieldInfo[xk][yk] = 3;
						if (!ok)
							SM("The exit is incorrectly specified in the file!", 0);
					}
				}
				if (ok) {
					index = s.indexOf("Treasure:");
					if (index != -1) {
						index2 = s.indexOf(",");
						index3 = s.indexOf(" ");
						sxk = s.substring(index3 + 1, index2);
						syk = s.substring(index2 + 1, s.length());
						xk = goodInt(sxk);
						yk = goodInt(syk);
						ok = (xk > 0 && xk < 11) ? true : false;
						ok = (ok && yk > 0 && yk < 11) ? true : false;
						if (ok)
							FieldInfo[xk][yk] = 2;
						if (!ok)
							SM("The treasure is incorrectly specified in the file!", 0);
					}
				}
				if (ok) {
					index = s.indexOf("Traps:");
					if (index != -1) {
						index = s.indexOf(" ");
						stp = s.substring(index + 1, s.length());
						String[] pks = stp.split(" ");
						for (String sc : pks) {
							index2 = sc.indexOf(",");
							sxk = sc.substring(0, index2);
							syk = sc.substring(index2 + 1, sc.length());
							xk = goodInt(sxk);
							yk = goodInt(syk);
							ok = (xk > 0 && xk < 11) ? true : false;
							ok = (ok && yk > 0 && yk < 11) ? true : false;
							if (ok)
								FieldInfo[xk][yk] = 1;
						}
						if (!ok)
							SM("The traps are incorrectly specified in the file!", 0);
					}
				}
				s = in.readLine();
			}
			in.close();
		} catch (IOException ioe) {
			ok = false;
			SM("checkFile: " + ioe.getMessage(), 0);
		}
		return ok;
	}
	
	public boolean Check(int kpx, int kpy) {
		boolean v = true;
		if (FieldInfo[kpx][kpy] == 2) {SM("You got the treasure!", 2);}
		if (FieldInfo[kpx][kpy] == 5) {SM("You crossed your own route!\nGame over!", 0); v = false;}
		if (FieldInfo[kpx][kpy] == 1) {SM("You are trapped!", 0); v = false;}
		if (FieldInfo[kpx][kpy] == 3) {v = false;}
		else {
			if (kpx == 0 || kpx == 11) {SM("You hit the wall!", 0); v = false;}
			if (kpy == 0 || kpy == 11) {SM("You hit the wall!", 0); v = false;}
		}
		return v;
	}
	
	public Program() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Treasure Hunter by Szivós Ádám");
		setSize(635, 750);
		setLocationRelativeTo(null);
		// setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(paleYellow);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnLoadingTrack = new JButton("Loading Track");
		btnLoadingTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				int returnValue = jfc.showOpenDialog(Program.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					/*lblOK.setVisible(false);
					lblNO.setVisible(false);*/
					sourceFile = jfc.getSelectedFile();
					textFileName.setText(sourceFile.getAbsolutePath());
					
					boolean ok = checkFile();
					if (ok) lblOK.setVisible(true);
						else lblNO.setVisible(true);
					if (ok) trackDisplay();
				}
			}
		});
		btnLoadingTrack.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLoadingTrack.setBounds(10, 647, 136, 23);
		contentPane.add(btnLoadingTrack);

		JLabel lblNewLabel = new JLabel("File name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(156, 652, 87, 14);
		contentPane.add(lblNewLabel);

		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStatus.setBounds(514, 652, 63, 14);
		contentPane.add(lblStatus);

		textFileName = new JTextField();
		textFileName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textFileName.setBounds(227, 649, 277, 20);
		contentPane.add(textFileName);
		textFileName.setColumns(10);

		lblOK = new JLabel("");
		lblOK.setIcon(new ImageIcon("Pict\\ok.png"));
		lblOK.setBounds(562, 647, 24, 24);
		contentPane.add(lblOK);
		lblOK.setVisible(false);

		lblNO = new JLabel("");
		lblNO.setIcon(new ImageIcon("Pict\\ok.png"));
		lblNO.setBounds(562, 647, 24, 24);
		contentPane.add(lblOK);
		
		textSteps = new JTextField();
		textSteps.setBounds(60, 682, 277, 20);
		contentPane.add(textSteps);
		textSteps.setColumns(10);
		
		JLabel lblSteps = new JLabel("Steps:");
		lblSteps.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSteps.setBounds(10, 684, 49, 14);
		contentPane.add(lblSteps);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String steps = textSteps.getText();
				int kpx = inX, kpy = inY;
				boolean end = false, v = true;
				String step = "";
				int ls = 0, lv = steps.length()-1;
				while (!end) {
					step = steps.substring(ls, ls + 1);
					if (step.equals("F")) {
						if (direction.equals("LR")) {FieldInfo[kpx][kpy] = 5; kpx++; v = Check(kpx, kpy);
							if (v) Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepHor.png")); else ls = lv;}
						if (direction.equals("RJ")) {FieldInfo[kpx][kpy] = 5; kpx--; v = Check(kpx, kpy);
							if (v) Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepHor.png")); else ls = lv;}
						if (direction.equals("TB")) {FieldInfo[kpx][kpy] = 5; kpy++; v = Check(kpx, kpy);
							if (v) Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepVer.png")); else ls = lv;}
						if (direction.equals("BT")) {FieldInfo[kpx][kpy] = 5; kpy--; v = Check(kpx, kpy);
							if (v) Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepVer.png")); else ls = lv;}
					}
					if (step.equals("R")) {
						switch (direction) {
						case "LR": direction = "TB"; Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepLeftDown.png")); break;
						case "RJ": direction = "BT"; Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepRightTop.png")); break;
						case "TB": direction = "RJ"; Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepLeftTop.png")); break;
						case "BT": direction = "LR"; Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepRightDown.png")); break;
						}
					}
					if (step.equals("L")) {
						switch (direction) {
						case "LR": direction = "BT"; Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepLeftTop.png")); break;
						case "RJ": direction = "TB"; Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepRightDown.png")); break;
						case "TB": direction = "LR"; Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepRightTop.png")); break;
						case "BT": direction = "RJ"; Field[kpx][kpy].setIcon(new ImageIcon("pict\\stepLeftDown.png")); break;
						}
					}
					if (FieldInfo[kpx][kpy] == 2) Field[kpx][kpy].setIcon(new ImageIcon("pict\\treasureOK.png"));
					if (ls == lv) end = true; else ls++;
				}
			}
		});
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRun.setBounds(347, 681, 78, 23);
		contentPane.add(btnRun);
		lblNO.setVisible(false);

		instantiation();
		trackDrawer();
	}
}
