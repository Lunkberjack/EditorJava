package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.DefaultEditorKit;
/**
 * Clase que crea un editor simple de texto con las funciones de Abrir,
 * Abrir Reciente, Guardar y crear un Archivo Nuevo. También se puede 
 * Cortar, Copiar y Pegar desde un menú edición. Incorpora una ayuda.
 * @author LucíaLM
 * @version 2.0
 */
public class EditorLuciaLM implements ActionListener {
	// No tiene por qué haber palabras, pero al menos hay una línea.
	private int contadorRecientes, contadorPalabras = 0, contadorLineas = 1;
	private JFrame marco;
	protected JMenuBar barraMenu;
	protected JMenu archivo, edicion, caracter, ayuda;
	protected JMenuItem archivoNuevo, archivoAbrir, archivoAbrirReciente, archivoGuardar, cortar, copiar, pegar,
			  sobre, fuente, cambiarFuente, tamanio, t1, t2, t3, t4, estilo, negrita, cursiva, reciente1, reciente2;
	private JToolBar barraHerramientas;
	private JTextPane areaTexto;
	private JScrollPane scroll; 
	private JPopupMenu menuPopup;
	private JButton botonNuevo, botonAbrir, botonRecientes, botonGuardar;
	private JPanel barraEstado;
	private JTextField nombreArchivo;
	private JLabel etiquetaArchivo, cuentaPalabras, cuentaLineas;
	private Dimension size = new Dimension(750, 500);

	public EditorLuciaLM() {
		marco = new JFrame();
		// Si se cierra la ventana de la GUI, el programa termina.
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco.setSize(size);
		marco.setLocationRelativeTo(null); // Así se abre en el centro de la pantalla.
		
		// JTextPane y JScrollPane
		areaTexto = new JTextPane();
		areaTexto.setFont(new Font("Tahoma", Font.PLAIN, 24));
		areaTexto.addKeyListener(new Teclas());
		scroll = new JScrollPane(areaTexto);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// Menú (barra)
		barraMenu = new JMenuBar();
		etiquetaArchivo = new JLabel("Nombre archivo: ");
		nombreArchivo = new JTextField("Guardar con este nombre (cámbialo)", 10);
		
		archivo = new JMenu("Archivo");
		caracter = new JMenu("Carácter");
		edicion = new JMenu("Edición");
		ayuda = new JMenu("Ayuda");

		// Componentes del menú
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
		
		fuente = new JMenu("Fuente");
		cambiarFuente = new JMenuItem("Cambiar...");
		cambiarFuente.addActionListener(this);
		
		tamanio = new JMenu("Tamaño");
		generarTamanio();
		/*t1 = new JMenuItem("16");
		t1.addActionListener(this);
		t2 = new JMenuItem("20");
		t2.addActionListener(this);
		t3 = new JMenuItem("24");
		t3.addActionListener(this);
		t4 = new JMenuItem("28");
		t4.addActionListener(this);*/
		estilo = new JMenu("Estilo");
		cursiva = new JMenuItem("Cursiva");
		cursiva.addActionListener(this);
		negrita = new JMenuItem("Negrita");
		negrita.addActionListener(this);

		cortar = new JMenuItem("Cortar");
		copiar = new JMenuItem("Copiar");
		pegar = new JMenuItem("Pegar");
		cortar.addActionListener(new DefaultEditorKit.CutAction());
		copiar.addActionListener(new DefaultEditorKit.CopyAction());
		pegar.addActionListener(new DefaultEditorKit.PasteAction());
		sobre = new JMenuItem("Sobre...");
		sobre.addActionListener(this);

		archivo.add(archivoNuevo);
		archivo.add(archivoAbrir);
		archivo.add(archivoAbrirReciente);
		archivoAbrirReciente.add(reciente1);
		archivoAbrirReciente.add(reciente2);
		archivo.add(archivoGuardar);
		
		caracter.add(fuente);
		fuente.add(cambiarFuente);
		caracter.add(tamanio);
		/*tamanio.add(t1);
		tamanio.add(t2);
		tamanio.add(t3);
		tamanio.add(t4);*/
		caracter.add(estilo);
		estilo.add(negrita);
		estilo.add(cursiva);
		
		edicion.add(cortar);
		edicion.add(copiar);
		edicion.add(pegar);
		ayuda.add(sobre);

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

		// Menú que aparece cuando hacemos click derecho en el JTextPane.
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
		cuentaPalabras.setText("Palabras: ");
		cuentaLineas = new JLabel();
		cuentaLineas.setText("Lineas: " + contadorLineas);
		barraEstado.add(cuentaPalabras, BorderLayout.EAST);
		barraEstado.add(cuentaLineas, BorderLayout.SOUTH);
		marco.add(barraEstado, BorderLayout.PAGE_END);


		// Se añaden varios componentes al marco
		marco.add(barraHerramientas, BorderLayout.WEST);
		marco.getContentPane().add(scroll, BorderLayout.CENTER);
		barraMenu.add(archivo);
		barraMenu.add(caracter);
		barraMenu.add(edicion);
		barraMenu.add(ayuda);
		barraMenu.add(etiquetaArchivo);
		barraMenu.add(nombreArchivo);
		marco.setJMenuBar(barraMenu);
		marco.setTitle("Editor de LucíaLM");
		
		// Esta línea es muy importante. Sin ella no se visualiza el JFrame
		// ni sus componentes.
		marco.setVisible(true);
	}

	public static void main(String[] args) {
		EditorLuciaLM editor = new EditorLuciaLM();
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		/**
		 * Borra todo el contenido del editor, limpiando el JTextPane.
		 */
		if(evento.getSource() == archivoNuevo || evento.getSource() == botonNuevo) {
			areaTexto.setText("");
			marco.setTitle("Nuevo archivo");
			nombreArchivo.setText("Pon un nombre");
			contadorPalabras = 0;
		/**
		 * Abre el archivo de texto y lo muestra en el JTextPane.
		 */
		} else if (evento.getSource() == archivoAbrir || evento.getSource() == botonAbrir) {
			// Abre una ventana para elegir un archivo de nuestro sistema.
			JFileChooser fileChooser = new JFileChooser();
			int seleccion = fileChooser.showOpenDialog(areaTexto);
			// Solo si se acepta:
			if (seleccion == JFileChooser.APPROVE_OPTION) { 
				abrirArchivo(fileChooser.getSelectedFile().getAbsolutePath(), areaTexto);
			} else {
				// Si no se especifica un contenedor padre del recuadro de mensaje, este saldrá fuera
				// del contenedor y confundirá a los usuarios.
				JOptionPane.showMessageDialog(marco, "No has seleccionado archivo :(");
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
		 * Guarda el contenido en un archivo cuyo nombre hay que pasar como parámetro
		 */
		} else if (evento.getSource() == archivoGuardar || evento.getSource() == botonGuardar) {
			JFileChooser chooser = new JFileChooser(); 
			chooser.setCurrentDirectory(new java.io.File("."));
			chooser.setDialogTitle("Elija una carpeta para guardar el archivo: ");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
			// Si se acepta:
			if (chooser.showOpenDialog(areaTexto) == JFileChooser.APPROVE_OPTION) { 
				String path = chooser.getSelectedFile().getAbsolutePath();
				guardarArchivo(nombreArchivo.getText(), path, areaTexto);
			}
			else {
				JOptionPane.showMessageDialog(marco, "No has seleccionado carpeta :(");
			}
		/**
		 * El botón de la barra de herramientas tiene una funcionalidad distinta:
		 * en vez de presentar dos submenús, abre el archivo más reciente abierto.
		 * Si no se encuentra el archivo, notifica al usuario.
		 */
		} else if (evento.getSource() == botonRecientes) {
			if(!reciente2.getText().equalsIgnoreCase("reciente 2")) {
				abrirArchivo(reciente2.getText(), areaTexto);
			} else if(!reciente1.getText().equalsIgnoreCase("reciente 1")) {
				abrirArchivo(reciente1.getText(), areaTexto);
			} else {
				JOptionPane.showMessageDialog(marco, "No tienes archivos recientes que abrir :(");
			}
		/**
		 * Mensaje de información y ayuda.
		 */
		} else if (evento.getSource() == sobre) {
			JOptionPane.showMessageDialog(marco, "Hola :)\nEste editor puede crear, editar y guardar texto como archivo, "
					+ "además de abrir archivos de tu sistema.\n\nNo olvides cambiar el nombre con el que quieres guardar"
					+ " tu archivo, o este se guardará por defecto con uno de los nombres elegidos.\n\nEn el panel de abajo "
					+ "podrás encontrar un conteo actualizado de palabras y líneas.\nSi ves que al copiar texto en el editor "
					+ "no se actualiza, solo pulsa Espacio+Backspace y lo tendrás a tu disposición.\nRecuerda poner un espacio "
					+ "tras cambiar de línea si solo vas a escribir una palabra por línea.\n\nPuedes editar desde "
					+ "el menú Edición o haciendo click derecho sobre el área de texto.\n\n¡Disfruta!");
		/**
		 * Instancia una clase que se abre en un nuevo marco y permite seleccionar
		 * una fuente para usar.
		 */
		} else if(evento.getSource() == cambiarFuente) {
			GenerarFuentes fuentes = new GenerarFuentes();
			fuentes.combo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println((String)(fuentes.combo.getSelectedItem()));
					areaTexto.setFont(new Font((String)fuentes.combo.getSelectedItem(), areaTexto.getFont().getStyle(), areaTexto.getFont().getSize()));
				}
			});
		}
	}
	/**
	 * Abre un archivo, lo lee por líneas (BufferedReader y BufferedWriter son
	 * mucho más eficientes por esta razón) y mete el contenido en el areaTexto.
	 * @param path
	 * @param area
	 */
	public void abrirArchivo(String path, JTextPane area) {
		try {
			String linea;
			BufferedReader bfReader = new BufferedReader(new FileReader(path));
			// Borra todo el contenido del archivo anterior y pone las líneas a 0.
			area.setText("");
			contadorLineas = 0;
			marco.setTitle(path);
			
			while((linea = bfReader.readLine()) != null) {
				area.setText(area.getText() + linea + "\n");
				contadorLineas++;
			}
			manejarRecientes(path);
			// Muestra el número de líneas y palabras del archivo abierto.
			contadorPalabras = areaTexto.getText().split("\\s+").length;
			cuentaLineas.setText("Lineas: " + contadorLineas);
			cuentaPalabras.setText("Palabras:" + contadorPalabras);
			bfReader.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(marco,"No se encontró el archivo. ¿Lo has borrado o cambiado de directorio? :(");
			System.out.println("ERROR de entrada/salida. No se encontró el archivo.");
		}
	}
	/**
	 * Se encarga de organizar los paths recientes que aparecen en los submenús
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
	 * @param nombre
	 * @param path
	 * @param area
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
			JOptionPane.showMessageDialog(marco,"Algo ha salido mal guardando tu archivo :(");
			System.out.println("ERROR de entrada/salida.");
		}
	}
	/**
	 * Clase interna que permite el uso de KeyEvent (eventos de teclas) para
	 * poder registrar la tecla pulsada y trabajar según los resultados.
	 * Hereda de una clase abstracta que no puede ser instanciada, por ello 
	 * la necesidad de crear una subclase que sí define su método abstracto.
	 * @author LucíaLM
	 * @version 1.0
	 */
	class Teclas extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent event) {
			String texto = areaTexto.getText();
			if(event.getKeyChar() == KeyEvent.VK_SPACE) {
				cuentaPalabras.setText("Palabras: " + ++contadorPalabras);
			// Aunque se haga autowrap, no se considera una línea aparte, 
			// al igual que por ejemplo el editor Kate de KDE.
			} else if(event.getKeyChar() == KeyEvent.VK_ENTER){
				cuentaLineas.setText("Lineas: " + ++contadorLineas);
			// Se actualiza al borrar. Hay que dejar un espacio tras la palabra
			// para que todas las palabras sean contadas, sobre todo después de 
			// pulsar Enter y escribir una sola palabra en la línea.
			} else if(event.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
				contadorPalabras = (texto.split("\\s+").length);
				contadorLineas = (texto.split("\\n+").length);
				// Si se ha borrado todo el contenido, para que no quede esa
				// molesta "Palabra: 1" cuando realmente no hay nada.
				if(texto.equals("")) {
					contadorPalabras = 0;
				}
				cuentaPalabras.setText("Palabras: " + contadorPalabras);
				cuentaLineas.setText("Lineas: " + contadorLineas);
			}
		}
	}
	/**
	 * Crea los JMenuItem para el tamaño de letra.
	 */
	public void generarTamanio() {
		for(int i=12; i < 50; i+=4) {
			JMenuItem elemento = new JMenuItem("" + i);
			tamanio.add(elemento);
		}
	}
}
