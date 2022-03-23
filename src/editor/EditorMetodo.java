package editor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToolBar;


public class EditorMetodo {
	public static void main(String[] args) {
		MenuProcesador miMarco = new MenuProcesador();
		miMarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
class MenuProcesador extends JFrame {
	public MenuProcesador() {
		this.setBounds(500, 300, 550,400);
		LaminaProcesador miLamina = new LaminaProcesador();
		this.add(miLamina);
		this.setVisible(true);
	}
}

class LaminaProcesador extends JPanel {
	JTextPane miArea = new JTextPane();
	JMenu archivo, herramientas, tamanio;
	Font letra;
	
	public LaminaProcesador() {
		this.setLayout(new BorderLayout());
		// L�mina en la que estar� el men� (sobre la principal)
		JPanel laminaMenu = new JPanel();
		// La barra de men�, o navegaci�n
		JMenuBar miBarra = new JMenuBar();
		archivo = new JMenu("Archivo");
		herramientas = new JMenu("Herramientas");
		tamanio = new JMenu("Tama�o");
		// Se configuran todos los elementos de men�
		configuraMenu("Nuevo archivo", "archivo", "Arial",9,10);
		configuraMenu("Abrir archivo", "archivo", "Courier",9,10);
		configuraMenu("Abrir reciente", "archivo", "Verdana",9,10);
		configuraMenu("Guardar como...", "archivo", "Verdana",9,10);
		configuraMenu("Cortar", "herramientas", "", Font.PLAIN, 1);
		configuraMenu("Copiar", "herramientas", "", Font.BOLD, 1);
		configuraMenu("Pegar", "herramientas", "", Font.ITALIC, 1);
		configuraMenu("12", "tama�o", "",1,12);
		configuraMenu("16", "tama�o", "",1,16);
		configuraMenu("20", "tama�o", "",1, 20);
		configuraMenu("24", "tama�o", "",1,24);
		// Se a�aden los elementos de men� a la barra
		miBarra.add(archivo);
		miBarra.add(herramientas);
		miBarra.add(tamanio);
		
		// A�adir la barra al JPanel
		laminaMenu.add(miBarra);
		
		// Barra de herramientas
		JToolBar barraHerramientas = new JToolBar();
		
		// Esta l�mina se a�ade a la instancia, y el NORTH indica que debe ir en la parte sup. 
		// del marco.
		this.add(laminaMenu, BorderLayout.NORTH);
		this.add(miArea, BorderLayout.CENTER);
	}
	/** Crea elementos de men� que tienen por nombre el 
	 * texto que se pase en el primer par�metro
	 * @param texto el String que aparecer� como texto visible
	 * @param menu el men� en el que se debe crear cada JMenuItem
	 * @param tipografia la tipograf�a deseada
	 * @param estiloLetra (negrita, cursiva, etc)
	 * @param tamLetra el tama�o de fuente
	 */
	public void configuraMenu(String texto, String menu, String tipografia, int estiloLetra, int tamLetra) {
		JMenuItem elem = new JMenuItem(texto);
		if(menu.equalsIgnoreCase("archivo")) {
			archivo.add(elem);
		} else if(menu.equalsIgnoreCase("herramientas")) {
			herramientas.add(elem);
		} else if(menu.equalsIgnoreCase("tama�o")) {
			tamanio.add(elem);
		}
		elem.addActionListener(new GestionaEventos(texto, menu, tipografia, estiloLetra, tamLetra));
	}
	/**
	 * Obtiene los valores de la clase externa y cambia estilos
	 * dependiendo de los par�metros pasados.
	 * @author LuciaLM
	 */
	private class GestionaEventos implements ActionListener {
		String tipografia, menu;
		int estiloLetra, tamLetra;
		
		public GestionaEventos(String elem, String menu, String tip, int est, int tam) {
			tipografia = tip;
			estiloLetra = est;
			this.tamLetra = tam;
			this.menu = menu;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			letra = miArea.getFont();
			// Guardamos las caracter�sticas que tuviera el texto y 
			// solo cambiamos la deseada (para evitar que las dem�s se
			// reseteen)
			if(menu.equalsIgnoreCase("archivo")) {
				estiloLetra = letra.getStyle();
				tamLetra = letra.getSize();
			} else if (menu.equalsIgnoreCase("herramientas")) {
				tipografia = letra.getFontName();
				tamLetra = letra.getSize();
			} else if (menu.equalsIgnoreCase("tama�o")) {
				estiloLetra = letra.getStyle();
				tipografia = letra.getFontName();
			}
			miArea.setFont(new Font(tipografia, estiloLetra, tamLetra));
		}
	}
}