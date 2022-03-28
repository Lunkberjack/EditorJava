package editor;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;


public class GenerarFuentes extends JDialog {
	private JFrame marco2;
	private JPanel panel;
	private JLabel texto;
	private JComboBox<String> combo;
	private String fuenteSeleccionada;
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dimension = new Dimension(400, 100);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenerarFuentes ventana = new GenerarFuentes();
					ventana.marco2.setVisible(true);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GenerarFuentes() {
		marco2 = new JFrame();
		marco2.setSize(dimension);
		marco2.setLocationRelativeTo(null);
		marco2.setTitle("Escoja una fuente...");
		// Solo se cierra esta clase, no todo el programa.
		marco2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		texto = new JLabel();
		texto.setText("Selecciona una fuente: ");
		
		panel = new JPanel();
		combo = new JComboBox<String>();
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fuenteSeleccionada = (String)combo.getSelectedItem();
			}
		});
		String fuentes[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for(int i=0; i < fuentes.length; i++) {
			combo.addItem(fuentes[i]);
		}
		panel.add(texto);
		panel.add(combo);
		marco2.add(panel);
		marco2.setVisible(true);
	}

	public String getFuenteSeleccionada() {
		return fuenteSeleccionada;
	}
}