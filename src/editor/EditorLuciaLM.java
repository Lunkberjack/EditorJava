package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractButton;
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
 * Clase que crea un editor simple de texto con las funciones de Abrir, Abrir Reciente, Guardar 
 * y crear un Archivo Nuevo. También se puede Cortar, Copiar y Pegar desde un menú edición, 
 * así como haciendo click derecho (Menú PopUp). Incorpora una ayuda.
 * 
 * En la versión 2.0 se añaden las funciones de cambio de tipo, tamaño y estilo de fuente. Además
 * se pueden comparar dos archivos por líneas, palabras y bytes.
 * 
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
	sobre, fuente, cambiarFuente, tamanio, t1, t2, t3, t4, estilo, negrita, cursiva, reciente1, reciente2, archivoComparar;
	private JToolBar barraHerramientas;
	private JTextPane areaTexto;
	private JScrollPane scroll; 
	private JPopupMenu menuPopup;
	private JButton botonNuevo, botonAbrir, botonRecientes, botonGuardar, botonComparar, botonFuente, botonCursiva,
	botonNegrita;
	private JPanel barraEstado;
	private JTextField nombreArchivo;
	private JLabel etiquetaArchivo, cuentaPalabras, cuentaLineas;
	private Dimension size = new Dimension(750, 600);
	private boolean bold = false, italic = false, itabold = false;

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
		archivoComparar = new JMenuItem("Comparar archivos");
		archivoComparar.addActionListener(this);
		reciente1 = new JMenuItem("Reciente 1");
		reciente1.addActionListener(this);
		reciente2 = new JMenuItem("Reciente 2");
		reciente2.addActionListener(this);
		archivoGuardar = new JMenuItem("Guardar archivo");
		archivoGuardar.addActionListener(this);

		fuente = new JMenu("Fuente");
		fuente.addActionListener(this);
		cambiarFuente = new JMenuItem("Cambiar...");
		cambiarFuente.addActionListener(this);

		tamanio = new JMenu("Tamaño");
		t1 = new JMenuItem("16");
		t1.addActionListener(this);
		t2 = new JMenuItem("20");
		t2.addActionListener(this);
		t3 = new JMenuItem("24");
		t3.addActionListener(this);
		t4 = new JMenuItem("30");
		t4.addActionListener(this);
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
		archivo.add(archivoComparar);
		archivoAbrirReciente.add(reciente1);
		archivoAbrirReciente.add(reciente2);
		archivo.add(archivoGuardar);

		caracter.add(fuente);
		fuente.add(cambiarFuente);
		caracter.add(tamanio);
		tamanio.add(t1);
		tamanio.add(t2);
		tamanio.add(t3);
		tamanio.add(t4);
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
		botonComparar = new JButton();
		botonComparar.setIcon(new ImageIcon("src/icons/comparison.png"));
		botonComparar.addActionListener(this);
		botonFuente = new JButton();
		botonFuente.setIcon(new ImageIcon("src/icons/font.png"));
		botonFuente.addActionListener(this);
		botonNegrita = new JButton();
		botonNegrita.setIcon(new ImageIcon("src/icons/bold.png"));
		botonNegrita.addActionListener(this);
		botonCursiva = new JButton();
		botonCursiva.setIcon(new ImageIcon("src/icons/italic.png"));
		botonCursiva.addActionListener(this);

		barraHerramientas = new JToolBar(null, JToolBar.VERTICAL);
		barraHerramientas.add(botonNuevo);
		barraHerramientas.add(botonAbrir);
		barraHerramientas.add(botonRecientes);
		barraHerramientas.add(botonGuardar);
		barraHerramientas.add(botonComparar);
		barraHerramientas.add(botonFuente);
		barraHerramientas.add(botonNegrita);
		barraHerramientas.add(botonCursiva);

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
			/** ABRIR
			 * Abre el archivo de texto y lo muestra en el JTextPane.
			 */
		} else if (evento.getSource() == archivoAbrir || evento.getSource() == botonAbrir) {
			// Abre una ventana para elegir un archivo de nuestro sistema.
			JFileChooser fileChooser = new JFileChooser();
			int seleccion = fileChooser.showOpenDialog(marco);
			// Solo si se acepta:
			if (seleccion == JFileChooser.APPROVE_OPTION) { 
				abrirArchivo(fileChooser.getSelectedFile().getAbsolutePath(), areaTexto);
			} else {
				// Si no se especifica un contenedor padre del recuadro de mensaje, este saldrá fuera
				// del contenedor y confundirá a los usuarios.
				JOptionPane.showMessageDialog(marco, "No has seleccionado archivo :(");
			}
			/** ABRIR RECIENTES
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
			/** GUARDAR
			 * Guarda el contenido en un archivo cuyo nombre hay que pasar como parámetro
			 */
		} else if (evento.getSource() == archivoGuardar || evento.getSource() == botonGuardar) {
			JFileChooser seleccion = new JFileChooser();
			// Si se olvida el nombre del archivo se puede volver a cambiarlo.
			// Se podría hacer con comparaciones de String pero así es más interactivo.
			if(JOptionPane.showConfirmDialog(marco, "¿Has puesto un nombre a tu archivo?") == JOptionPane.YES_OPTION) {
				seleccion.setCurrentDirectory(new java.io.File("."));
				seleccion.setDialogTitle("Elige una carpeta para guardar el archivo: ");
				seleccion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				seleccion.setAcceptAllFileFilterUsed(false);
				// Si se acepta la selección del archivo:
				if (seleccion.showOpenDialog(marco) == JFileChooser.APPROVE_OPTION) { 
					String path = seleccion.getSelectedFile().getAbsolutePath();
					guardarArchivo(nombreArchivo.getText(), path, areaTexto);
				} else {
					JOptionPane.showMessageDialog(marco, "No has seleccionado carpeta :(");
				}
			} else {
				JOptionPane.showMessageDialog(marco, "Cámbialo, está arriba a la derecha :)");
			}
			/** RECIENTES - BARRA HERRAMIENTAS
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
			/** AYUDA - SOBRE
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
			
			/** TIPO FUENTE
			 * Instancia una clase que se abre en un nuevo marco y permite seleccionar
			 * una fuente para usar.
			 */
		} else if(evento.getSource().equals(cambiarFuente) || evento.getSource().equals(botonFuente)) {
			GenerarFuentes fuentes = new GenerarFuentes();
			fuentes.combo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					areaTexto.setFont(new Font((String)fuentes.combo.getSelectedItem(), areaTexto.getFont().getStyle(), areaTexto.getFont().getSize()));
				}
			});
			
			/** TAMAÑO FUENTE
			 * Cambio de tamaño. Solo se puede realizar a través del menú, ya que los valores son fijos
			 * y desde la barra de herramientas no se pueden seleccionar todos los disponibles.
			 */
		} else if(evento.getSource().equals(t1) || evento.getSource().equals(t2) || evento.getSource().equals(t3) || evento.getSource().equals(t4)) {
			// Eclipse añade solo el abstract button y no funciona sin el casting.
			areaTexto.setFont(new Font(areaTexto.getFont().getName(), areaTexto.getFont().getStyle(), Integer.valueOf(((AbstractButton)evento.getSource()).getText().toString())));
			
			/** ESTILO FUENTE
			 * Lógica para controlar negrita, cursiva, negrita+cursiva y texto plano.
			 */
		} else if(evento.getSource().equals(negrita) || evento.getSource().equals(botonNegrita)) {
			if(bold) {
				areaTexto.setFont(new Font(areaTexto.getFont().getName(), Font.PLAIN, areaTexto.getFont().getSize()));
				bold = false;
			} else if(itabold) {
				areaTexto.setFont(new Font(areaTexto.getFont().getName(), Font.ITALIC, areaTexto.getFont().getSize()));
				itabold = false;
				italic = true;
				bold = false;
			} else if(italic) {
				areaTexto.setFont(new Font(areaTexto.getFont().getName(), Font.BOLD + Font.ITALIC, areaTexto.getFont().getSize()));
				itabold = true;
				bold = false;
				italic = false;
			} else {
				areaTexto.setFont(new Font(areaTexto.getFont().getName(), Font.BOLD, areaTexto.getFont().getSize()));
				bold = true;
			}
		} else if (evento.getSource().equals(cursiva) || evento.getSource().equals(botonCursiva)) {
			if(italic) {
				areaTexto.setFont(new Font(areaTexto.getFont().getName(), Font.PLAIN, areaTexto.getFont().getSize()));
				italic = false;
			} else if(itabold) {
				areaTexto.setFont(new Font(areaTexto.getFont().getName(), Font.BOLD, areaTexto.getFont().getSize()));
				itabold = false;
				bold = true;
				italic = false;
			} else if(bold) {
				areaTexto.setFont(new Font(areaTexto.getFont().getName(), Font.BOLD + Font.ITALIC, areaTexto.getFont().getSize()));
				itabold = true;
				bold = false;
				italic = false;
			} else {
				areaTexto.setFont(new Font(areaTexto.getFont().getName(), Font.ITALIC, areaTexto.getFont().getSize()));
				italic = true;
			}
			/** COMPARAR ARCHIVOS
			 * Compara archivos desde la barra de herramientas y el menú.
			 */
		} else if(evento.getSource().equals(archivoComparar) || evento.getSource().equals(botonComparar)) {
			JFileChooser fileChooser = new JFileChooser();
			String path = null, path2 = null;
			// Dos selectores de archivo.
			int seleccion = fileChooser.showOpenDialog(marco);
			if (seleccion == JFileChooser.APPROVE_OPTION) { 
				path = fileChooser.getSelectedFile().getAbsolutePath();
			}
			JOptionPane.showMessageDialog(marco, "Selecciona el otro archivo para comparar...");
			int seleccion2 = fileChooser.showOpenDialog(marco);
			if(seleccion2 == JFileChooser.APPROVE_OPTION) {
				path2 = fileChooser.getSelectedFile().getAbsolutePath();
			}
			// Los dos path deben existir.
			if(path != null && path2 != null) {
				compararArchivos(path, path2);
			} else {
				JOptionPane.showMessageDialog(marco, "Algo ha salido mal. Asegúrate de seleccionar dos archivos.");
			}
		}
	}
	/** Abre un archivo, lo lee por líneas (BufferedReader y BufferedWriter son
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
	 * de Abrir reciente. Como solo se utiliza en otros métodos de este clase
	 * puede ser private.
	 * @param path
	 */
	private void manejarRecientes(String path) {
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
	 * Abre dos archivos y muestra sus líneas, palabras y bytes en un mensaje.
	 * Informa del mayor en número de bytes.
	 * @param path
	 * @param path2
	 */
	public void compararArchivos(String path, String path2) {
		int lineasArchivo1=0, lineasArchivo2=0, palabrasArchivo1, palabrasArchivo2, 
				bytesArchivo1, bytesArchivo2;
		String linea, textoArchivo1="", textoArchivo2="";
		BufferedReader bfReader1, bfReader2;
		try {
			bfReader1 = new BufferedReader(new FileReader(path));
			while((linea = bfReader1.readLine()) != null) {
				textoArchivo1 += linea;
				lineasArchivo1++;
			}
			palabrasArchivo1 = textoArchivo1.split("\\s+").length;
			// Cada carácter ocupa un byte (suponiendo que no incluimos caracteres raros)
			bytesArchivo1 = textoArchivo1.length();

			bfReader2 = new BufferedReader(new FileReader(path2));
			while((linea = bfReader2.readLine()) != null) {
				textoArchivo2 += linea;
				lineasArchivo2++;
			}
			palabrasArchivo2 = textoArchivo2.split("\\s+").length;
			bytesArchivo2 = textoArchivo2.length();

			// Resultado: se muestra como mensaje
			JOptionPane.showMessageDialog(marco, "El archivo 1 tiene " + lineasArchivo1 + " líneas, " + palabrasArchivo1 + 
					" palabras y " + bytesArchivo1 + " bytes.\nEl archivo 2 tiene " + lineasArchivo2 + " líneas, " +
					palabrasArchivo2 + " palabras y " + bytesArchivo2 + " bytes.\n\n");
			if(bytesArchivo1 > bytesArchivo2) {
				JOptionPane.showMessageDialog(marco, "El archivo mayor es el archivo 1."); 
			} else if(bytesArchivo2 > bytesArchivo1) {
				JOptionPane.showMessageDialog(marco, "El archivo mayor es el archivo 2.");
			} else {
				JOptionPane.showMessageDialog(marco, "Los archivos son iguales.");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(marco,"No se encontró el archivo. ¿Lo has borrado o cambiado de directorio? :(");
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
}