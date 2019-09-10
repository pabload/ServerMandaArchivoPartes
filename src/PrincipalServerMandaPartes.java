import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrincipalServerMandaPartes {

    public static void main(String[] args) throws IOException {
        
         iniciarServidor(args[0]);
    }
    public static void iniciarServidor(String puerto){
        crearServidor(Integer.parseInt(puerto));
        
    }
    public static void crearServidor(int puerto){
        try { 
            ServerSocket sockeServer = new ServerSocket(puerto);
            ConfigurarServidor(sockeServer);
            
        } catch (IOException ex) {
            System.out.println("Error al crear socket servidor "+ex);
        }
    }
    public  static void ConfigurarServidor(ServerSocket sockeServer){
        String entrada = null;
         do {
             BufferedReader lector = null;
             PrintWriter escritor = null;
             Socket socket =null;
             try {
              socket = sockeServer.accept();
             lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             escritor = new PrintWriter(socket.getOutputStream(), true);
             } catch (IOException ex) {
                System.out.println("Error al crear socket "+ex);
             }
            DataInputStream in = null;
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                entrada = lector.readLine();
            } catch (IOException ex) {
                System.out.println("Error al leer ruta del archivo"+ex);
            }
            System.out.println(entrada);
            if (entrada != null) {
                File archivo = new File(entrada);
                System.out.println("entro");
                if (archivo.exists()) {
                    try {
                     System.out.println("existe");
                     System.out.println(archivo.getAbsolutePath()+"aaaaaaaa");
                    /*System.out.println(entrada);
                    int longitud = (int) new File(entrada).length();
                    escritor.println(longitud);
                    byte[] mybytearray = new byte[(int) archivo.length()];
                    fis = new FileInputStream(archivo);
                    bis = new BufferedInputStream(fis);
                    bis.read(mybytearray, 0, mybytearray.length);
                    os = socket.getOutputStream();
                    System.out.println("enviando " + entrada + "(" + mybytearray.length + " bytes)");
                    os.write(mybytearray, 0, mybytearray.length);
                    os.flush();
                    System.out.println("ya.");*/
                    int longitud = (int) new File(entrada).length();
                    escritor.println(longitud);
                    //byte[] mybytearray = new byte[(int) archivo.length()];
                    fis = new FileInputStream(archivo);
                    bis = new BufferedInputStream(fis);
                    //bis.read(mybytearray, 0, mybytearray.length);
                    os = socket.getOutputStream();
                        int count;
                        byte[] buffer = new byte[100];
                        while ((count = bis.read(buffer)) > 0) {
                            os.write(buffer, 0, count);
                            
                            System.out.print("|");
                        }
                        os.flush();
                        System.out.println("se mando");
                    } catch (IOException ex) {
                      System.out.println("Error al mandar archivo"+ex);
                    }
                } else {
                    escritor.println("non");
                }
                if (entrada.equalsIgnoreCase("fin")) {
                    try {
                     System.out.println("me voy");
                    escritor.println("fin");
                    socket.close();
                    sockeServer.close();
                    System.exit(0);
                    } catch (IOException ex) {
                        System.out.println("Erro al cerrar sockets"+ ex);
                    }
                }
            }

        } while (!entrada.equalsIgnoreCase("fin"));
    }

}
