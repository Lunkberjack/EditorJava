package editor;

import java.util.Scanner;

public class PruebaArchivo {

	public static void main(String[] args) {
		String nombre;
		Scanner sc = new Scanner(System.in);
		nombre = sc.nextLine();
		
		OperacionesArchivos.crearArchivo(nombre);
		sc.close();
		
		OperacionesArchivos.editarArchivo("erica");
		OperacionesArchivos.leerArchivo("erica");
	}
}
