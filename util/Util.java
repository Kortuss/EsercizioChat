package util;
public class Util {
    public static String Red = "\\u001B[31m";
    public static String Reset = "\u001B[0m";
    public static void Log(String Message){
        System.out.println(Message);
    }

    public static void Log(Exception Err){
        System.out.println(Red + Err + Reset);
    }
}
