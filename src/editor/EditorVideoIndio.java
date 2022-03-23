package editor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.DefaultEditorKit;

public class EditorVideoIndio implements ActionListener {
	public int contadorRecientes, contadorPalabras;
	JFrame marco;
	JMenuBar barraMenu;
	JMenu archivo, herramientas;
	JMenuItem archivoNuevo, archivoAbrir, archivoAbrirReciente, archivoGuardar,
	cortar, copiar, pegar;
	JMenuItem reciente1, reciente2;
	JToolBar barraHerramientas;
	JTextPane areaTexto;
	JPopupMenu menuPopup;
	JButton botonNuevo, botonAbrir, botonRecientes, botonGuardar;
	JPanel barraEstado;
	JLabel cuentaPalabras;
    
	public EditorVideoIndio() {
		marco = new JFrame();
		// Si se cierra la ventana de la GUI, el programa termina.
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco.setBounds(500, 200, 500, 500);
		
		// Men� (barra)
		barraMenu = new JMenuBar();
		archivo = new JMenu("Archivo");
		herramientas = new JMenu("Herramientas");
		areaTexto = new JTextPane();

		// Componentes del men�
		archivoNuevo = new JMenuItem("Nuevo archivo");
		archivoNuevo.addActionListener(this);
		archivoAbrir = new JMenuItem("Abrir archivo");
		archivoAbrir.addActionListener(this);		
		archivoAbrirReciente = new JMenu("Abrir reciente");
		archivoAbrirReciente.addActionListener(this);
		reciente1 = new JMenuItem("Reciente 1");
		reciente1.addActionListener(this);
		reciente2 = new JMenuItem("Reciente 2");
		reciente2.addActionListener(this);
		archivoGuardar = new JMenuItem("Guardar archivo");
		archivoGuardar.addActionListener(this);
		cortar = new JMenuItem("Cortar");
		copiar = new JMenuItem("Copiar");
		pegar = new JMenuItem("Pegar");
		cortar.addActionListener(new DefaultEditorKit.CutAction());
		copiar.addActionListener(new DefaultEditorKit.CopyAction());
		pegar.addActionListener(new DefaultEditorKit.PasteAction());

		archivo.add(archivoNuevo);
		archivo.add(archivoAbrir);
		archivo.add(archivoAbrirReciente);
		archivoAbrirReciente.add(reciente1);
		archivoAbrirReciente.add(reciente2);
		archivo.add(archivoGuardar);
		herramientas.add(cortar);
		herramientas.add(copiar);
		herramientas.add(pegar);
		
		// Barra de herramientas
		botonNuevo = new JButton();
		botonNuevo.setIcon(new ImageIcon("src/icons/add.png"));
		botonNuevo.addActionListener(this);
		botonAbrir = new JButton();
		botonAbrir.setIcon(new ImageIcon("src/icons/share.png"));
		botonAbrir.addActionListener(this);
		botonRecientes = new JButton();
		botonRecientes.setIcon(new ImageIcon("src/icons/recent.png"));
		botonRecientes.addActionListener(this);
		botonGuardar = new JButton();
		botonGuardar.setIcon(new ImageIcon("src/icons/save.png"));
		botonGuardar.addActionListener(this);
		
		barraHerramientas = new JToolBar(null, JToolBar.VERTICAL);
		barraHerramientas.add(botonNuevo);
		barraHerramientas.add(botonAbrir);
		barraHerramientas.add(botonRecientes);
		barraHerramientas.add(botonGuardar);

		// Men� que aparece cuando hacemos click derecho en el JTextPane.
		JPopupMenu menuPopup = new JPopupMenu();
		JMenuItem cortar = new JMenuItem("Cortar");
		JMenuItem copiar = new JMenuItem("Copiar");
		JMenuItem pegar = new JMenuItem("Pegar");

		cortar.addActionListener(new DefaultEditorKit.CutAction());
		copiar.addActionListener(new DefaultEditorKit.CopyAction());
		pegar.addActionListener(new DefaultEditorKit.PasteAction());

		menuPopup.add(cortar);
		menuPopup.add(copiar);
		menuPopup.add(pegar);
		areaTexto.setComponentPopupMenu(menuPopup);
		
		// Barra de estado
		barraEstado = new JPanel();
		cuentaPalabras = new JLabel();
		cuentaPalabras.setText("Contador de palabras: ");
		barraEstado.add(cuentaPalabras, BorderLayout.EAST);
		marco.add(barraEstado, BorderLayout.SOUTH);
		
		
		// Se a�aden varios componentes al marco
		areaTexto.setFont(new Font("Tahoma", Font.PLAIN, 24));
		marco.add(barraHerramientas, BorderLayout.WEST);
		marco.add(areaTexto, BorderLayout.CENTER);
		barraMenu.add(archivo);
		barraMenu.add(herramientas);
		marco.setJMenuBar(barraMenu);
		marco.setTitle("Editor de Luc�aLM");
		marco.setVisible(true);
	}

	public static void main(String[] args) {
		EditorVideoIndio xd = new EditorVideoIndio();
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		/**
		 * Borra todo el contenido del editor, limpiando el JTextPane.
		 */
		if(evento.getSource() == archivoNuevo || evento.getSource() == botonNuevo) {
			areaTexto.setText("");
			marco.setTitle("Nuevo archivo");
		/**
		 * Abre el archivo de texto y lo muestra en el JTextPane.
		 */
		} else if (evento.getSource() == archivoAbrir || evento.getSource() == botonAbrir) {
			JFileChooser fileChooser = new JFileChooser();
			int seleccion = fileChooser.showOpenDialog(areaTexto);
			// Solo si se acepta:
			if (seleccion == JFileChooser.APPROVE_OPTION) { 
				abrirArchivo(fileChooser.getSelectedFile().getAbsolutePath(), areaTexto);
			} else {
				JOptionPane.showMessageDialog(areaTexto, "No has seleccionado archivo :(");
			}
		/**
		 * Permite abrir hasta dos archivos recientemente abiertos.
		 */
		} else if(evento.getSource() == reciente1 || evento.getSource() == reciente2) {
			String path;
			if(evento.getSource() == reciente1) {
				 path = reciente1.getText();
			} else {
				path = reciente2.getText();
			}
			abrirArchivo(path, areaTexto);
		/**
		* Guarda el contenido en un archivo cuyo nombre hay que pasar como par�metro
		*/
		} else if (evento.getSource() == archivoGuardar || evento.getSource() == botonGuardar) {
			JOptionPane.showMessageDialog(areaTexto,"No escribas nombre de archivo, tu archivo se llamar� untitled.txt por defecto :)");
			JFileChooser chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Elija una carpeta para guardar el archivo: ");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			// Si se acepta:
			if (chooser.showOpenDialog(areaTexto) == JFileChooser.APPROVE_OPTION) { 
				String path = chooser.getSelectedFile().getAbsolutePath();
				guardarArchivo("untitled", path, areaTexto);
			}
			else {
				JOptionPane.showMessageDialog(areaTexto, "No has seleccionado carpeta :(");
			}
		/**
		 * El bot�n de la barra de herramientas tiene una funcionalidad distinta:
		 * en vez de presentar dos submen�s, abre el archivo m�s reciente abierto.
		 * Si no se encuentra el archivo, notifica al usuario.
		 */
		} else if (evento.getSource() == botonRecientes) {
			if(!reciente2.getText().equalsIgnoreCase("reciente 2")) {
				abrirArchivo(reciente2.getText(), areaTexto);
			} else if(!reciente1.getText().equalsIgnoreCase("reciente 1")) {
				abrirArchivo(reciente1.getText(), areaTexto);
			} else {
				JOptionPane.showMessageDialog(areaTexto, "No tienes archivos recientes que abrir :(");
			}
		}
	}
	/**
	 * Abre un archivo, lo lee por l�neas (BufferedReader y BufferedWriter son
	 * mucho m�s eficientes por esta raz�n) y mete el contenido en el areaTexto.
	 * @param path
	 * @param area
	 */
	public void abrirArchivo(String path, JTextPane area) {
		try {
			String linea;
			BufferedReader bfReader = new BufferedReader(new FileReader(path));
			// Borra todo el contenido del archivo anterior.
			area.setText("");
			marco.setTitle(path);
			while((linea = bfReader.readLine()) != null) {
				area.setText(area.getText() + linea + "\n");
			}
			manejarRecientes(path);
			bfReader.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(areaTexto,"No se encontr� el archivo :(");
			System.out.println("ERROR de entrada/salida. No se encontr� el archivo.");
		}
	}
	/**
	 * Se encarga de organizar los paths recientes que aparecen en los submen�s
	 * de Abrir reciente.
	 * @param path
	 */
	public void manejarRecientes(String path) {
		if(contadorRecientes == 0) {
			reciente1.setText(path);
		} else if(contadorRecientes == 1) {
			reciente2.setText(path);
		} else if (contadorRecientes >= 2) {
			reciente1.setText(reciente2.getText());
			reciente2.setText(path);
		}
		this.contadorRecientes++;
	}	
	/**
	 * Guarda el contenido del JTextPane en un archivo de texto. Si el archivo ya existe,
	 * sobreescribe el contenido de este borrando lo anterior.
	 */
	public void guardarArchivo(String nombre, String path, JTextPane area) {
		try {
			// Este modo de obtener el separador permite que el programa se ejecute en
			// distintos SO.
			String archivo = path + System.getProperty("file.separator") + nombre + ".txt";
			BufferedWriter bfWriter = new BufferedWriter(new FileWriter(archivo));
			bfWriter.write(area.getText());
			bfWriter.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Algo ha salido mal guardando tu archivo :(");
			System.out.println("ERROR de entrada/salida.");
		}
	}
	
	public void contarPalabras() {
		
	}
}