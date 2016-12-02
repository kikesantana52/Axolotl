/****************************************************
**Elaborado por: Luis Enrique Santana Del Pozo
**Fecha: 11/07/2016
**Descripción: Clase principal del SO.
******************************************************/


package sistemaoperativo;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Enrique
 */
public class SistemaOperativo {
    static String path = new File("").getAbsolutePath();
    static String direccion = path;
    
    static String linea = "";
    static ArrayList comandos;
    static ArrayList variables;
    static ArrayList valores;
    static ArrayList instrucciones;
    
    /**
     * Función main del programa
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        saludar();
        CargarArray();
        Scanner sc = new Scanner(System.in);
        while (!("bye".equals(linea) || "BYE".equals(linea))) {
            System.out.print(direccion + ">");
            linea = sc.nextLine();
            ejecutarCMD();
        }
        guardarArrays();
        despedir();
        
    }
    /**
     * Funcion para cargar los elementos que tiene comandos,variables y valores
     */
    public static void CargarArray() {
        comandos = new ArrayList();
        variables = new ArrayList();
        valores = new ArrayList();
        instrucciones = new ArrayList();
        File archivo2 = new File("help.txt");
             
        try {
           FileReader fr = new FileReader(archivo2);
           BufferedReader br = new BufferedReader(fr);
           String cad = br.readLine();
           String cadenas[];
           while (cad != null) {
                cadenas = cad.split("-");
                comandos.add(cadenas[0]);
                instrucciones.add(cadenas[1]);
                cad = br.readLine();
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SistemaOperativo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SistemaOperativo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        File archivo = new File("variables.txt");
             
        try {
           FileReader fr = new FileReader(archivo);
           BufferedReader br = new BufferedReader(fr);
           String cad = br.readLine();
           String cadenas[];
           while (cad != null) {
                cadenas = cad.split(",");
                variables.add(cadenas[0]);
                valores.add(Double.parseDouble(cadenas[1]));
                cad = br.readLine();
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SistemaOperativo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SistemaOperativo.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    /**
     * Guarda las variables que tienes en un archivo 
     */
    public static void guardarArrays() {
        
        File archivo = new File("variables.txt");
        archivo.delete();
        File archivo2 = new File("variables.txt");
        try {
           FileWriter fr = new FileWriter(archivo2);
           BufferedWriter br = new BufferedWriter(fr);
           int x =0;
           while (x < variables.size()) {
                br.write(variables.get(x) + "," + valores.get(x));
                br.newLine();
                x++;
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SistemaOperativo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SistemaOperativo.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    /**
     * funcion para saludar
     */
    public static void saludar(){
        System.out.println("*****************************************************************************************");
        System.out.println("* Bienvenido al sistema operativo Version 1.0 Derechos reservados Luis Enrique Santana. *");
        System.out.println("***************************************************************************************** \n \n");
        
    }
    /**
     * funcion para imprimir una despedida.
     */
    public static void despedir() {
        System.out.println("Programa finalizado");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SistemaOperativo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Funcion donde se ejeccutan los comandos.
     */
    public static void ejecutarCMD() {
        linea = linea.trim();
        if (linea.length() > 0) {
            String[] NLinea = linea.split(" ");
            boolean vacios = false;
            for (int i = 0; i < NLinea.length; i++) {
                if (NLinea[i].equals("")) {
                    vacios = true;
                }
            }
            if (NLinea[0].equals("prompt")) {
                if (NLinea.length > 1) {
                    direccion = "";
                    for (int x = 1; x < NLinea.length; x++) {
                        direccion = direccion + NLinea[x] + " ";
                    }
                } else {
                    direccion = path;
                }
            } else if (vacios == false) {
                if (NLinea.length > 0) {
                    if (!(comandos.contains(NLinea[0]))) {
                        System.out.println("Lo sentimos el comando no existe");
                    } else if (NLinea[0].equals("makeVar")) {
                        makeVar(NLinea);
                    } else if (NLinea[0].equals("inc")) {
                        inc(NLinea);
                    } else if (NLinea[0].equals("dec")) {
                        dec(NLinea);
                    } else if (NLinea[0].equals("changeVar")) {
                        changeVar(NLinea);
                    } else if (NLinea[0].equals("lsVar")) {
                        lsVar(NLinea);
                    } else if (NLinea[0].equals("Suma") || NLinea[0].equals("Resta") || NLinea[0].equals("Multiplicacion") || NLinea[0].equals("Division") || NLinea[0].equals("Modulo")) {
                        switch(NLinea[0]){
                            case "Suma":{
                                suma(NLinea);
                                break;
                            }
                            case "Resta":{
                                resta(NLinea);
                                break;
                            }
                            case "Multiplicacion":{
                                multiplica(NLinea);
                                break;
                            }
                            case "Division":{
                                divide(NLinea);
                                break;
                            }
                            case "Modulo":{
                                Modulo(NLinea);
                                break;
                            }
                        }
                    }else if(NLinea[0].equals("help")){
                        help(NLinea);
                    }else if (NLinea[0].equals("pot")){
                        potencia(NLinea);
                    }else if (NLinea[0].equals("vabs")){
                        vabs(NLinea);
                    }else if (NLinea[0].equals("seno")){
                        Seno(NLinea);
                    }else if (NLinea[0].equals("cos")){
                        Coseno(NLinea);
                    }else if (NLinea[0].equals("tan")){
                        Tangente(NLinea);
                    }else if (NLinea[0].equals("atang")){
                        Arcotangente(NLinea);
                    }else if (NLinea[0].equals("noletras")){
                        charNum(NLinea);
                    }else if (NLinea[0].equals("sinvoc")){
                        sinvoc(NLinea);
                    }else if (NLinea[0].equals("sincon")){
                        sincon(NLinea);
                    }else if (NLinea[0].equals("numlet")){
                        numlet(NLinea);
                    }else if (NLinea[0].equals("clock")){
                        clock(NLinea);
                    }else if (NLinea[0].equals("date")){
                        date(NLinea);
                    }else if (NLinea[0].equals("compare")){
                        compare(NLinea);
                    }else if (NLinea[0].equals("dir")){
                        dir(NLinea);
                    }else if (NLinea[0].equals("showImage")){
                        Imagen vent = new Imagen();
                        vent.setVisible(true);
                    }else if (NLinea[0].equals("piramide")) {
                        if (NLinea.length == 3) {
                            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {

                                Object uno = "", dos = "";
                                Double uni,dis;
                                if (variables.contains(NLinea[1])) {
                                    uno = valores.get(variables.indexOf(NLinea[1]));
                                } else {
                                    uno = Double.parseDouble(NLinea[1]);
                                }
                                if (variables.contains(NLinea[2])) {
                                    dos = valores.get(variables.indexOf(NLinea[2]));
                                } else {
                                    dos = Double.parseDouble(NLinea[2]);
                                }
                                uni = Double.parseDouble(uno.toString());
                                dis = Double.parseDouble(dos.toString());
                                int i = uni.intValue();
                                int j = dis.intValue();
                                if(i> 100 || j > 100){
                                    System.out.println("Valores de la piramide demasiado grandes.");
                                }else{
                                    piramide(i, j);   
                                }
                                

                            } else {
                                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
                            }
                        } else {
                            System.out.println("El numero de parametros es diferente al requerido");
                        }
                    }
                }
            } else {
                System.out.println("Ten cuidado, podrian existir espacios dobles entre parametros");
            }
        }
    }
    /**
     * Función para saber si un String es un entero
     * @param strNum Cadena String a convertir
     * @return true si es numerico, false si es falso
     */
    public static boolean isNum(String strNum) {
        return strNum.matches("[-+]?\\d*\\.?\\d+");
    }
    /**
     * Función para crear variables
     * @param NLinea - Linea de comando
     */
    public static void makeVar(String[] NLinea) {
        if (NLinea.length == 3 || NLinea.length == 2) {

            char[] letras = NLinea[1].toCharArray();

            if (!(isNum(String.valueOf(letras[0])))) {
                if (!(variables.contains(NLinea[1]))) {
                    if (NLinea.length == 2) {
                        variables.add(NLinea[1]);
                        valores.add("0");
                    } else if (isNum(NLinea[2])) {
                        variables.add(NLinea[1]);
                        valores.add(NLinea[2]);
                    } else {
                        System.out.println("El segundo parametro requiere ser numero");
                    }
                } else {
                    System.out.println("La variable ya existe, utilice 'changeVar´si desea cambiar el valor de la variable");
                }

            } else {
                System.out.println("Lo sentimos, no se puede escribir las variables empezando por numeros");
            }
        } else {
            System.out.println("El numero de parametros es diferente a los requeridos");
        }
    }
    /**
     * Función para incrementar en 1 el valor de las variables
     * @param NLinea - Linea de comando
     */
    public static void inc(String[] NLinea) {
        if (NLinea.length == 2) {
            if (variables.contains(NLinea[1])) {
                valores.set(variables.indexOf(NLinea[1]), Double.parseDouble(valores.get(variables.indexOf(NLinea[1])).toString()) + 1);
                System.out.println(variables.get(variables.indexOf(NLinea[1])) + " : " + valores.get(variables.indexOf(NLinea[1])));
            } else {
                System.out.println("El parametro debe ser una variable ya definida.");
            }
        } else {
            System.out.println("El numero de parametros usados en la función es incorrecto");
        }
    }
    /**
     * Función para decrementar en 1 el valor de las variables
     * @param NLinea - Linea de comando
     */
    public static void dec(String[] NLinea) {
        if (NLinea.length == 2) {
            if (variables.contains(NLinea[1])) {
                valores.set(variables.indexOf(NLinea[1]), Double.parseDouble(valores.get(variables.indexOf(NLinea[1])).toString()) - 1);
                System.out.println(variables.get(variables.indexOf(NLinea[1])) + " : " + valores.get(variables.indexOf(NLinea[1])));
            } else {
                System.out.println("El parametro debe ser una variable ya definida.");
            }
        } else {
            System.out.println("El numero de parametros usados en la función es incorrecto");
        }
    }
    /**
     * Función para cambiar el valor de las variables
     * @param NLinea - Linea de comando
     */
    public static void changeVar(String[] NLinea) {
        if (NLinea.length == 3) {
            if (variables.contains(NLinea[1])) {
                if (isNum(NLinea[2])) {
                    valores.set(variables.indexOf(NLinea[1]), Double.parseDouble(NLinea[2]));
                } else if (variables.contains(NLinea[2])) {
                    valores.set(variables.indexOf(NLinea[1]), valores.get(variables.indexOf(NLinea[2])));
                } else {
                    System.out.println("El parametro 2 debe ser numerico");
                }
            } else {
                System.out.println("La variable no existe");
            }
        } else {
            System.out.println("El numero de parametros es diferente a los requeridos");
        }

    }
    /**
     * Función para listar variables
     * @param NLinea - Linea de comando
     */
    public static void lsVar(String[] NLinea){
        if (NLinea.length == 1) {
            int x = 0;
            while (x < variables.size()) {
                System.out.println(variables.get(x) + "," + valores.get(x));
                x++;
            }
        } else if (NLinea.length == 2) {
            if (variables.contains(NLinea[1])) {
                System.out.println(variables.get(variables.indexOf(NLinea[1])) + "," + valores.get(variables.indexOf(NLinea[1])));
            } else {
                System.out.println("Variable inexistente");
            }
        } else {
            System.out.println("El numero de parametros es diferente a los requeridos");
        }
    }
    /**
     * Función para sumar variables o valores
     * @param NLinea - Linea de comando
     */
    public static void suma(String[] NLinea) {
        if (NLinea.length == 3) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {
                if (variables.contains(NLinea[2]) || isNum(NLinea[2])) {
                    Object uno = "", dos = "";
                            if (variables.contains(NLinea[1])) {
                                uno = valores.get(variables.indexOf(NLinea[1]));
                            } else {
                                uno = Double.parseDouble(NLinea[1]);
                            }
                            if (variables.contains(NLinea[2])) {
                                dos = valores.get(variables.indexOf(NLinea[2]));
                            } else {
                                dos = Double.parseDouble(NLinea[2]);
                            }
                            System.out.println("El resultado es: " + (Double.parseDouble(uno.toString()) + Double.parseDouble(dos.toString())) + "");
                } else {
                    System.out.println("El numero de parametros es diferente al requerido");
                }
            }else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        }else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**
     * Función para restar variables o valores
     * @param NLinea - Linea de comando
     */
    public static void resta(String[] NLinea) {
        if (NLinea.length == 3) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {
                if (variables.contains(NLinea[2]) || isNum(NLinea[2])) {
                    Object uno = "", dos = "";
                            if (variables.contains(NLinea[1])) {
                                uno = valores.get(variables.indexOf(NLinea[1]));
                            } else {
                                uno = Double.parseDouble(NLinea[1]);
                            }
                            if (variables.contains(NLinea[2])) {
                                dos = valores.get(variables.indexOf(NLinea[2]));
                            } else {
                                dos = Double.parseDouble(NLinea[2]);
                            }
                            System.out.println("El resultado es: " + (Double.parseDouble(uno.toString()) - Double.parseDouble(dos.toString())) + "");
                } else {
                    System.out.println("El numero de parametros es diferente al requerido");
                }
            }else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        }else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**
     * Función para multiplicar variables o valores
     * @param NLinea - Linea de comando
     */
    public static void multiplica(String[] NLinea) {
        if (NLinea.length == 3) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {
                if (variables.contains(NLinea[2]) || isNum(NLinea[2])) {
                    Object uno = "", dos = "";
                            if (variables.contains(NLinea[1])) {
                                uno = valores.get(variables.indexOf(NLinea[1]));
                            } else {
                                uno = Double.parseDouble(NLinea[1]);
                            }
                            if (variables.contains(NLinea[2])) {
                                dos = valores.get(variables.indexOf(NLinea[2]));
                            } else {
                                dos = Double.parseDouble(NLinea[2]);
                            }
                            System.out.println("El resultado es: " + (Double.parseDouble(uno.toString()) * Double.parseDouble(dos.toString())) + "");
                } else {
                    System.out.println("El numero de parametros es diferente al requerido");
                }
            }else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        }else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**
     * Función para dividir variables o valores
     * @param NLinea - Linea de comando
     */
    public static void divide(String[] NLinea) {
        if (NLinea.length == 3) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {
                if (variables.contains(NLinea[2]) || isNum(NLinea[2])) {
                    boolean cero = false;
                    if (variables.contains(NLinea[2])) {
                        if (Double.parseDouble(valores.get(variables.indexOf(NLinea[2])).toString()) == 0) {
                            cero = true;
                        }
                    }
                    if (NLinea[2].equals("0") || cero) {
                        System.out.println("Lo siento, no se puede dividir entre 0");
                    } else {
                        Object uno = "", dos = "";
                        if (variables.contains(NLinea[1])) {
                            uno = valores.get(variables.indexOf(NLinea[1]));
                        } else {
                            uno = Double.parseDouble(NLinea[1]);
                        }
                        if (variables.contains(NLinea[2])) {
                            dos = valores.get(variables.indexOf(NLinea[2]));
                        } else {
                            dos = Double.parseDouble(NLinea[2]);
                        }
                        System.out.println("El resultado es: " + (Double.parseDouble(uno.toString()) / Double.parseDouble(dos.toString())) + "");
                    }
                } else {
                    System.out.println("El numero de parametros es diferente al requerido");
                }
            }else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        }else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**
     * Función para obtener el modulo de 2 variables o valores
     * @param NLinea - Linea de comando
     */
    public static void Modulo(String[] NLinea) {
        if (NLinea.length == 3) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {
                if (variables.contains(NLinea[2]) || isNum(NLinea[2])) {
                    boolean cero = false;
                    if (variables.contains(NLinea[2])) {
                        if (Double.parseDouble(valores.get(variables.indexOf(NLinea[2])).toString()) == 0) {
                            cero = true;
                        }
                    }
                    if (NLinea[2].equals("0") || cero) {
                        System.out.println("Lo siento, no se puede dividir entre 0");
                    } else {
                        Object uno = "", dos = "";
                        if (variables.contains(NLinea[1])) {
                            uno = valores.get(variables.indexOf(NLinea[1]));
                        } else {
                            uno = Double.parseDouble(NLinea[1]);
                        }
                        if (variables.contains(NLinea[2])) {
                            dos = valores.get(variables.indexOf(NLinea[2]));
                        } else {
                            dos = Double.parseDouble(NLinea[2]);
                        }
                        System.out.println("El resultado es: " + (Double.parseDouble(uno.toString()) % Double.parseDouble(dos.toString())) + "");
                    }
                } else {
                    System.out.println("El numero de parametros es diferente al requerido");
                }
            }else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        }else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**
     * Función para obtener la ayuda de los comandos
     * @param NLinea - Linea de comando
     */
    public static void help(String[] NLinea) {
        if (NLinea.length == 2) {
            if (comandos.contains(NLinea[1])) {
                if(NLinea[1].length()> 7){
                    System.out.println(NLinea[1] + "\t \t" + instrucciones.get(comandos.indexOf(NLinea[1])));
                }else{
                    System.out.println(NLinea[1] + "\t \t \t" + instrucciones.get(comandos.indexOf(NLinea[1])));
                }
                
            } else {
                System.out.println("El parametro debe ser un comando");
            }
        } else if(NLinea.length == 1){
            for (int x = 0; x < comandos.size(); x++){
                if(comandos.get(x).toString().length() > 7){
                     System.out.println(comandos.get(x) + "\t \t" + instrucciones.get(x));
                }else{
                    System.out.println(comandos.get(x) + "\t \t \t" + instrucciones.get(x));
                }
                
            }
        }else {
            System.out.println("El numero de parametros es diferente a los requeridos");
        }
    }
    /**
     * Funcion para sacar la potencia de un numero
     * @param NLinea 
     */
    public static void potencia(String[] NLinea) {
        if (NLinea.length == 3) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {
                if (variables.contains(NLinea[2]) || isNum(NLinea[2])) {
                    Object uno = "", dos = "";
                            if (variables.contains(NLinea[1])) {
                                uno = valores.get(variables.indexOf(NLinea[1]));
                            } else {
                                uno = Double.parseDouble(NLinea[1]);
                            }
                            if (variables.contains(NLinea[2])) {
                                dos = valores.get(variables.indexOf(NLinea[2]));
                            } else {
                                dos = Double.parseDouble(NLinea[2]);
                            }
                            System.out.println("El resultado es: " + (Math.pow(Double.parseDouble(uno.toString()), Double.parseDouble(dos.toString()))) + "");
                } else {
                    System.out.println("El numero de parametros es diferente al requerido");
                }
            }else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        }else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**
     * Funcion para sacar la potencia de un numero
     * @param NLinea 
     */
    public static void vabs(String[] NLinea) {
        if (NLinea.length == 2) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {

                Object uno = "";
                if (variables.contains(NLinea[1])) {
                    uno = valores.get(variables.indexOf(NLinea[1]));
                } else {
                    uno = Double.parseDouble(NLinea[1]);
                }

                System.out.println("El resultado es: " + (Math.abs(Double.parseDouble(uno.toString()))) + "");

            } else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**
     * Funcion para sacar el seno del numero dado
     * @param NLinea 
     */
    public static void Seno(String[] NLinea) {
        if (NLinea.length == 2) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {

                Object uno = "";
                if (variables.contains(NLinea[1])) {
                    uno = valores.get(variables.indexOf(NLinea[1]));
                } else {
                    uno = Double.parseDouble(NLinea[1]);
                }

                System.out.println("El resultado es: " + (Math.sin(Double.parseDouble(uno.toString()))) + "");

            } else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**
     * Funcion para sacar el coseno del numero dado
     * @param NLinea 
     */
    public static void Coseno(String[] NLinea) {
        if (NLinea.length == 2) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {

                Object uno = "";
                if (variables.contains(NLinea[1])) {
                    uno = valores.get(variables.indexOf(NLinea[1]));
                } else {
                    uno = Double.parseDouble(NLinea[1]);
                }

                System.out.println("El resultado es: " + (Math.cos(Double.parseDouble(uno.toString()))) + "");

            } else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**
     * Funcion para sacar la tangente
     * @param NLinea 
     */
    public static void Tangente(String[] NLinea) {
        if (NLinea.length == 2) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {

                Object uno = "";
                if (variables.contains(NLinea[1])) {
                    uno = valores.get(variables.indexOf(NLinea[1]));
                } else {
                    uno = Double.parseDouble(NLinea[1]);
                }

                System.out.println("El resultado es: " + (Math.tan(Double.parseDouble(uno.toString()))) + "");

            } else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**
     * Funcion para sacar arcotangente
     * @param NLinea 
     */
    public static void Arcotangente(String[] NLinea) {
        if (NLinea.length == 2) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {

                Object uno = "";
                if (variables.contains(NLinea[1])) {
                    uno = valores.get(variables.indexOf(NLinea[1]));
                } else {
                    uno = Double.parseDouble(NLinea[1]);
                }

                System.out.println("El resultado es: " + (Math.atan(Double.parseDouble(uno.toString()))) + "");

            } else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**Funcion para imprimir una piramide
     * 
     * @param i
     * @param j 
     */
    public static void piramide(int i, int j) {
        if (i > 0) {
            System.out.print("*");
            piramide(--i, j);
        } else if (j > 0) {
            i = j;
            System.out.println("");
            piramide(--i, --j);
        }
    }
    /**
     * Funcion para obtener el numero de letras que tiene una palabra
     *
     * @param NLinea
     */
    public static void charNum(String[] NLinea) {
        if (NLinea.length == 2) {
            char[] letras = NLinea[1].toCharArray();
            System.out.println("El numero de letras que tiene la palabra es: " + letras.length);
        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
    }
    /**
     * Funcion para quitar las vocales de una palabra
     *
     * @param NLinea
     */
    public static void sinvoc(String[] NLinea) {
        if (NLinea.length == 2) {
            char[] letras = NLinea[1].toCharArray();
            System.out.print("Tu palabra sin vocales es: ");
            for(int i = 0; i < letras.length; i++){
                if(letras[i] == 'a' || letras[i] == 'e' || letras[i] == 'i' || letras[i] == 'o' || letras[i] == 'u' || letras[i] == 'A' || letras[i] == 'E' || letras[i] == 'I' || letras[i] == 'O' || letras[i] == 'U'){
                    System.out.print("");
                }else{
                    System.out.print(letras[i]);
                }
                    
            }
            
        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
        System.out.println("");
    }
    /**
     * Funcion para quitar las consonantes de una palabra
     *
     * @param NLinea
     */
    public static void sincon(String[] NLinea) {
        if (NLinea.length == 2) {
            char[] letras = NLinea[1].toCharArray();
            System.out.print("Tu palabra sin consonantes es: ");
            for(int i = 0; i < letras.length; i++){
                if(letras[i] == 'a' || letras[i] == 'e' || letras[i] == 'i' || letras[i] == 'o' || letras[i] == 'u' || letras[i] == 'A' || letras[i] == 'E' || letras[i] == 'I' || letras[i] == 'O' || letras[i] == 'U'){
                    System.out.print(letras[i]);
                }
                    
            }
            
        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
        System.out.println("");
    }
    /**
     * Funcion para contar que letras tiene y cuantas veces
     *
     * @param NLinea
     */
    public static void numlet(String[] NLinea) {
        if (NLinea.length == 2) {
            char[] letras = NLinea[1].toCharArray();
            for (int i = 0; i < letras.length; i++) {
                System.out.print(letras[i] + ": ");
                int contador = 0;
                for (int j = 0; j < letras.length; j++) {
                    if (letras[j] == letras[i]) {
                        contador++;
                    }
                }
                System.out.println(contador);

            }

        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
        System.out.println("");
    }
    /**
     * Funcion para retornar la hora
     *
     * @param NLinea
     */
    public static void clock(String[] NLinea) {
        if (NLinea.length == 1) {
            Calendar calendario = Calendar.getInstance();
            int hora, minutos, segundos;
            hora =calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);
            segundos = calendario.get(Calendar.SECOND);
            System.out.println("La hora es: " + hora + ":" + minutos + ":" + segundos);
        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
        
    }
    /**
     * Funcion para retornar la fecha
     *
     * @param NLinea
     */
    public static void date(String[] NLinea) {
        if (NLinea.length == 1) {
            Calendar calendario = Calendar.getInstance();
            int dia, mes, anio;
            dia =calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH);
            anio = calendario.get(Calendar.YEAR);
            System.out.println("El dia de hoy es: " + dia + "/" + (mes + 1) + "/" + anio);
        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }
        
    }
    /**
     * Funcion para retornar que numero es mayor
     *
     * @param NLinea
     */
    public static void compare(String[] NLinea) {
        if (NLinea.length == 3) {
            if (variables.contains(NLinea[1]) || isNum(NLinea[1])) {
                if (variables.contains(NLinea[2]) || isNum(NLinea[2])) {
                    Object uno = "", dos = "";
                            if (variables.contains(NLinea[1])) {
                                uno = valores.get(variables.indexOf(NLinea[1]));
                            } else {
                                uno = Double.parseDouble(NLinea[1]);
                            }
                            if (variables.contains(NLinea[2])) {
                                dos = valores.get(variables.indexOf(NLinea[2]));
                            } else {
                                dos = Double.parseDouble(NLinea[2]);
                            }
                                Double uni = Double.parseDouble(uno.toString());
                                Double dis = Double.parseDouble(dos.toString());
                            if(uni > dis){
                                System.out.println("El mayor es: " + uni);
                            }else if (dis > uni){
                                System.out.println("El mayor es " + dis);
                            }else{
                                System.out.println("Sus valores son iguales");
                            }
                            
                } else {
                    System.out.println("El numero de parametros es diferente al requerido");
                }
            }else {
                System.out.println("El primer parametro debe ser numerico o una variable ya definida");
            }
        }else {
            System.out.println("El numero de parametros es diferente al requerido");
        }   
    }

    public static void dir(String[] NLinea) {
        if (NLinea.length == 1) {
            File dir = new File(path);
            String[] ficheros = dir.list();
            if (ficheros == null) {
                System.out.println("No hay ficheros en el directorio especificado");
            } else {
                for (int x = 0; x < ficheros.length; x++) {
                    System.out.println(ficheros[x]);
                }
            }
        } else {
            System.out.println("El numero de parametros es diferente al requerido");
        }

    }

}
