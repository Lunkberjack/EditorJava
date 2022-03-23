package editor;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

class OtroEditor extends JFrame {
	private int tamaño=17;
	private JTextArea areaTexto;
	private JButton cor,cop,peg,nue,gua,ab,acerca,ayuda;
	private JScrollPane scroll;
	private JComboBox tFuente;
	private Font areaFuente;

	public OtroEditor () {
		super("Editor de textos");
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Error al intentar cargar L&F");
		}
		areaFuente= new Font("Arial", Font.PLAIN, tamaño);
		areaTexto= new JTextArea();
		areaTexto.setFont(areaFuente);
		menus ();
		barra();
		scroll= new JScrollPane(areaTexto);
		getContentPane().add(scroll,BorderLayout.CENTER);
		JPanel panel= new JPanel();
		JPanel panel1= new JPanel();
		JPanel panel2= new JPanel();
		panel.setBackground(Color.lightGray);
		panel1.setBackground(Color.lightGray);
		panel2.setBackground(Color.lightGray);
		getContentPane().add(panel,BorderLayout.SOUTH);
		getContentPane().add(panel1,BorderLayout.WEST);
		getContentPane().add(panel2,BorderLayout.EAST);

		setSize(800,580);
		setVisible(true);
		show ();
	}

	public void menus () {
		JMenuBar menus = new JMenuBar();
		JMenu archivo= new JMenu("Archivo");
		JMenuItem nue= new JMenuItem("Nuevo", new ImageIcon("images/hoja_6.gif"));
		nue.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					nuevo();
				}
			}
		);
		JMenuItem sal= new JMenuItem("Salir",new ImageIcon("images/salir.gif"));
		sal.addActionListener(
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					String d=JOptionPane.showInputDialog("Desea salir y guardar el archivo S/N");
					if (d.equals("s")) {
						guardar();
						System.exit(0);
					}
					else if(d.equals("n")) {
						System.exit(0);
					}
					else {
						JOptionPane.showMessageDialog(null,"Caracter invalido");
					}
				}
			}
		);

		JMenuItem abr= new JMenuItem("Abrir",new ImageIcon("images/libro_1.gif"));
		abr.addActionListener(
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					abrir ();
				}
			}
		);
		JMenuItem guar= new JMenuItem("Guardar",new ImageIcon("images/guardar.gif"));
		guar.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					guardar ();
				}
			}
		);
		JMenu editar= new JMenu("Editar");
		JMenuItem cor= new JMenuItem("Cortar", new ImageIcon("images/cut.gif"));
		cor.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					cortar ();
				}
			}
		);
		JMenuItem cop= new JMenuItem("Copiar",new ImageIcon("images/copiar_0.gif"));
		cop.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					copiar ();
				}
			}
		);
		JMenuItem peg= new JMenuItem ("Pegar",new ImageIcon("images/paste.gif"));
		peg.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					pegar ();
				}
			}
		);

		JMenuItem fuen= new JMenuItem("Tamaño de fuente",new ImageIcon("images/hoja_2.gif"));
		fuen.addActionListener(
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					tamaño_f();
				}
			}
		);
		JMenu about= new JMenu("Ayuda");
		JMenuItem ayu= new JMenuItem("Ayuda",new ImageIcon("images/bombillo.gif"));
		ayu.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					ayuda ();
				}
			}
		);
		JMenuItem acer= new JMenuItem("Acerca de...",new ImageIcon("images/chulo.gif"));
		acer.addActionListener(
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					acerca();
				}
			}
		);
		archivo.add(nue);
		archivo.add(sal);
		archivo.addSeparator();
		archivo.add(abr);
		archivo.add(guar);
		editar.add(cor);
		editar.add(cop);
		editar.add(peg);
		editar.addSeparator();
		editar.add(fuen);
		about.add(ayu);
		about.add(acer);
		menus.add(archivo);
		menus.add(editar);
		menus.add(about);
		setJMenuBar(menus);

	}

	public void barra () {
		JToolBar barras= new JToolBar();

		nue= new JButton ();
		nue.setIcon(new ImageIcon("images/hoja_6.gif"));
		nue.setMargin(new Insets(3,0,0,0));
		nue.setToolTipText("Nuevo");
		nue.addActionListener(
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					nuevo ();
				}
			}
		);
		barras.add(nue);

		ab= new JButton();
		ab.setIcon(new ImageIcon("images/libro_1.gif"));
		ab.setMargin(new Insets(2,0,0,0));
		ab.setToolTipText("Abrir");
		ab.addActionListener(
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					abrir ();
				}
			}
		);
		barras.add(ab);

		gua= new JButton();
		gua.setIcon(new ImageIcon("images/guardar.gif"));
		gua.setMargin(new Insets(2,0,0,0));
		gua.setToolTipText("Guardar");
		gua.addActionListener(
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					guardar ();
				}
			}
		);
		barras.add(gua);

		barras.addSeparator();

		cor= new JButton();
		cor.setIcon(new ImageIcon("images/cut.gif"));
		cor.setMargin(new Insets(2,0,0,0));
		cor.setToolTipText("Cortar");
		cor.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					cortar ();
				}
			}
		);
		barras.add(cor);


		cop= new JButton();
		cop.setIcon(new ImageIcon("images/copiar_0.gif"));
		cop.setMargin(new Insets(-3,0,0,0));
		cop.setToolTipText("Copiar");
		cop.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					copiar ();
				}
			}
		);
		barras.add(cop);

		peg= new JButton();
		peg.setIcon(new ImageIcon("images/paste.gif"));
		peg.setMargin(new Insets(2,0,0,0));
		peg.setToolTipText("Pegar");
		peg.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					pegar ();
				}
			}
		);
		barras.add(peg);

		JButton del= new JButton();
		del.setIcon(new ImageIcon("images/borrador.gif"));
		del.setMargin(new Insets(2,0,0,0));
		del.setToolTipText("BORRAR todo el texto");
		del.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					areaTexto.setText("");
				}
			}
		);
		barras.add(del);

		barras.addSeparator();

		ayuda= new JButton();
		ayuda.setIcon(new ImageIcon("images/bombillo.gif"));
		ayuda.setMargin(new Insets(2,0,0,0));
		ayuda.setToolTipText("Ayuda");
		ayuda.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					ayuda ();
				}
			}
		);
		barras.add(ayuda);

		acerca= new JButton();
		acerca.setIcon(new ImageIcon("images/chulo.gif"));
		acerca.setMargin(new Insets(5,2,0,0));
		acerca.setToolTipText("Acerca de...");
		acerca.addActionListener (
			new ActionListener () {
				public void actionPerformed (ActionEvent e) {
					acerca ();
				}
			}
		);
		barras.add(acerca);

		barras.addSeparator();

		tFuente= new JComboBox();
		tFuente.addItem("Tamaño Fuente");
		tFuente.addItem("10");
		tFuente.addItem("20");
		tFuente.addItem("30");
		tFuente.addItem("40");
		tFuente.addItem("50");
		tFuente.addItem("Personalizar");
		tFuente.setToolTipText("Tamaño de fuente");
		tFuente.addItemListener(
			new ItemListener () {
				public void itemStateChanged(ItemEvent e) {

					int elegido;
					elegido=tFuente.getSelectedIndex();
					switch (elegido) {

						case 1:
							areaFuente= new Font("Arial", Font.PLAIN, 10);
							areaTexto.setFont(areaFuente);
							break;

						case 2:
							areaFuente= new Font("Arial", Font.PLAIN, 20);
							areaTexto.setFont(areaFuente);
							break;

						case 3:
							areaFuente= new Font("Arial", Font.PLAIN, 30);
							areaTexto.setFont(areaFuente);
							break;

						case 4:
							areaFuente= new Font("Arial", Font.PLAIN, 40);
							areaTexto.setFont(areaFuente);
							break;

						case 5:
							areaFuente= new Font("Arial", Font.PLAIN, 50);
							areaTexto.setFont(areaFuente);
							break;
						case 6:
							tamaño=Integer.parseInt(JOptionPane.showInputDialog("Digite el tamaño de la fuente"));
							areaFuente= new Font("Arial", Font.PLAIN, tamaño);
							areaTexto.setFont(areaFuente);
							break;
					}
				}
			}
		);
		barras.add(tFuente);


		barras.addSeparator();

		getContentPane().add(barras,BorderLayout.NORTH);
	}

	public void nuevo () {
		new OtroEditor ();
	}

	public void abrir () {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result= fileChooser.showOpenDialog(this);
		if (result== JFileChooser.CANCEL_OPTION) return;
		File name= fileChooser.getSelectedFile();
		if(name.exists()) {
			if (name.isFile()) {
				try {
					BufferedReader input= new BufferedReader(new FileReader (name));
					StringBuffer buffer= new StringBuffer();
					String text;
					areaTexto.setText("");
					while ((text=input.readLine()) !=null)
						buffer.append(text+ "\n");
					areaTexto.append(buffer.toString());
				}
				catch (IOException ioException) {
					JOptionPane.showMessageDialog(null,"Error en el archivo", "Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (name.isDirectory ()) {
				String directory[] = name.list();
				areaTexto.append("\n\nContenido del directorio:\n");
				for (int i=0;i<directory.length; i++)
					areaTexto.append(directory [i]+"\n");
			}
			else {
				JOptionPane.showMessageDialog(null," No existe "," Error ",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void guardar () {
		JOptionPane.showMessageDialog(null,"¡Por favor no olvide colocar la extension del archivo (*.txt)!");
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result= fileChooser.showSaveDialog(this);
		if (result== JFileChooser.CANCEL_OPTION) return;
			File name= fileChooser.getSelectedFile();
			try {
				PrintWriter output= new PrintWriter(new FileWriter( name));
				output.write(areaTexto.getText());
				output.close();
			}
			catch (IOException ioException) {
				JOptionPane.showMessageDialog(null,"Error en el archivo","Error",JOptionPane.ERROR_MESSAGE);
			}
	}

	public void cortar () {
		areaTexto.cut();
	}

	public void copiar () {
		areaTexto.copy();
	}

	public void pegar () {
		areaTexto.paste();
	}

	public void ayuda () {
		JOptionPane.showMessageDialog(null," Nuevo: Abre una nueva ventana\n Abrir: Abre un documento existente\n Guardar: Guarda el documento\n Salir: Salir del programa\n Cortar: ctrl+x\n Copiar: ctrl+c\n Pegar: ctrl+v\n Salir sin guardar: alt+F4");
	}

	public void tamaño_f () {
		tamaño=Integer.parseInt(JOptionPane.showInputDialog("Digite el tamaño de la fuente"));
		areaFuente= new Font("Arial", Font.PLAIN, tamaño);
		areaTexto.setFont(areaFuente);
	}

	public void acerca () {
		JOptionPane.showMessageDialog(null,"Editor de textos\nDesarrollado por:  Jhon Jairo Roldan\nIngenieria de sistemas\nUniversidad Distrital Francisco Jose de Caldas\nCodigo:  20022020184\nE-mail:  jjroldanc@gicoge.udistrital.edu.co");
	}

	public static void main (String []args) {
		JOptionPane.showMessageDialog(null,"Para el correto funcionamiento del programa procure\nabrir solo archivos de tipo *.txt");
		new OtroEditor();
	}
}