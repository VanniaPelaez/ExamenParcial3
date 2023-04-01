package Package1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public class Lector {

	private List<String> names = new ArrayList<String>();
    private int[] count = new int[3000];

 // Inicializa todas las posiciones del arreglo count en 0
    public Lector() {
        for(int x=0; x<3000; x++){
            count[x] = 0;
        }
    }

    public void ejecutar(int i) throws Exception {
        ArrayList<List<Integer>> l = this.leer(i%1000);
        this.sumar(l);
        this.escribir(Integer.toString(i));
    }

 // Lista para almacenar los nombres de las columnas de la tabla de emails
    private ArrayList<List<Integer>> leer(int n) throws Exception {
        File emails = new File("emails.csv");
        ArrayList<List<Integer>> l = new ArrayList<List<Integer>>();
        
     // Variable para contar las filas leídas
        int i=0;
        try (BufferedReader lec = new BufferedReader(new FileReader(emails))) {
            String ren;
            while ((ren = lec.readLine()) != null) {
            	
                if(i==0) {
                    String[] vol = ren.split(",");
                    Collections.addAll(names, vol);
                    
                 // Remueve el primer y último elemento de la lista names, ya que no son necesarios
                    names.remove(0);
                    names.remove(3000);
                } else if(i>=n && i<n+50) {
                	
                	// Separa la línea en cada columna utilizando la coma como separador
                    String[] content = ren.split(",");
                    
                 // Crea una lista para almacenar los valores de cada fila
                    List<String> f = new ArrayList<String>();
                    Collections.addAll(f, content);
                    f.remove(0);
                    f.remove(3000);
                 // Convierte los valores de la lista email de String a Integer y los almacena en una lista listOfInteger
                    
                    List<Integer> ed = f.stream().map(Integer::parseInt).collect(Collectors.toList());
                 // Agrega la lista listOfInteger a la lista list
                    l.add(ed);
                }
                i++;
            }
        }
        return l;
    }

    private void sumar(ArrayList<List<Integer>> list) {
    	// Recorre las 50 líneas leídas
        for(int x=0; x<50; x++) {
            for(int numero=0; numero<3000; numero++) {
            	// Suma el valor de la celda al contador count
                count[numero] += list.get(x).get(numero);
            }
        }
    }

    private void escribir(String name) throws Exception {
    	// Crea un objeto File que representa el archivo CSV que se quiere crear
        File csvCount = new File(name + ".csv");

     // Si el archivo no existe, lo crea
        if (!csvCount.exists()) {
            try {
                csvCount.createNewFile();

                try (BufferedWriter e = new BufferedWriter(new FileWriter(csvCount))) {
                    for(int x=0; x<3000; x++) {
                        e.write(names.get(x) + ", " + String.valueOf(count[x]) + '\n');
                    }
                    e.close();

                    System.out.println("File created");
                }

            } catch (IOException num) {
                num.printStackTrace();
            }
        } else {
            System.out.println("Already created");
        }
    }
}
