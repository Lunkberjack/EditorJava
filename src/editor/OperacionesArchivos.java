package editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Clase con métodos estáticos para usar
 * en operaciones de entrada/salida de archivos.
 * @author LucíaLM
 *
 */
public class OperacionesArchivos {
	public static void crearArchivo(String path) {
		try {
		      File archivo = new File(path);
		      if (archivo.createNewFile()) {
		        System.out.println("Se ha creado el archivo: " + archivo.getName());
		      } else {
		        System.out.println("El archivo ya existe.");
		      }
		    } catch (IOException e) {
		      System.out.println("ERROR: El archivo no se ha podido crear.");
		      e.printStackTrace();
		    }
	}
	
	public static void editarArchivo(String nombre) {
		try {
			BufferedWriter bfWriter = new BufferedWriter(new FileWriter(nombre + ".txt"));
			bfWriter.write("Hartmann\nBarkhorn");
			bfWriter.close();
		} catch (IOException e) {
			System.out.println("ERROR de entrada/salida.");
			e.printStackTrace();
		}		
	}
	
	public static void leerArchivo(String path) {
		try {
			String linea;
			BufferedReader bfReader = new BufferedReader(new FileReader(path));
			while((linea = bfReader.readLine()) != null) {
				System.out.println(linea);
			}
			bfReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}