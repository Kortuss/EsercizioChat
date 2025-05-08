package util;
//Classe che definisce funzione di semplici utilità, come log sulla console più brevi
public class Util {
    public static String Red = "\u001B[31m ";
    public static String Reset = "\u001B[0m ";
    public static void Log(String Message){
        System.out.println(Message);
    }
    //Gli errori li stampo in rosso
    public static void Log(Exception Err){
        System.out.println(Red + Err + Reset);
    }
}
