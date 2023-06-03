/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.*;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;


/**
 * @author Profesor Lic. Gilberth Chaves Avila
 */
public class Utility {

    private static String routeImagen = "file:ProyectoGrupo_3/logo1.png";


    public static String getRouteImagen() {
        return routeImagen;
    }

    public static void setRouteImagen(String routeImagen) {
        Utility.routeImagen = routeImagen;
    }

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed
   private static  SinglyLinkedList customerList;

//    private static LinkedQueue linkedQueue;
//    private static LinkedStack linkedStack;
//
//    public static LinkedStack getLinkedStack() {
//        return linkedStack;
//    }
//
//    public static void setLinkedStack(LinkedStack linkedStack) {
//        Utility.linkedStack = linkedStack;
//    }
//
//    public static LinkedQueue getLinkedQueue() {
//        return linkedQueue;
//    }
//
//    public static void setLinkedQueue(LinkedQueue linkedQueue) {
//        Utility.linkedQueue = linkedQueue;
//    }
//
//    public static PriorityLinkedQueue getPriorityLinkedQueue() {
//        return priorityLinkedQueue;
//    }
//
//    public static void setPriorityLinkedQueue(PriorityLinkedQueue priorityLinkedQueue) {
//        Utility.priorityLinkedQueue = priorityLinkedQueue;
//    }

    // static initializer
    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
        customerList=new SinglyLinkedList();
//        priorityLinkedQueue = new PriorityLinkedQueue();
//        linkedQueue= new LinkedQueue();
//        linkedStack= new LinkedStack();
    }

    public static SinglyLinkedList getCustomerList() {
        return customerList;
    }

    public static void setCustomerList(SinglyLinkedList customerList) {
        Utility.customerList = customerList;
    }

    public static int random() {
        return 1 + (int) Math.floor(Math.random() * 99);
    }

    public static int random(int bound) {
        //return 1+random.nextInt(bound);
        return 1 + (int) Math.floor(Math.random() * bound);
    }

    public static int random(int lowBound, int highBound) {
        int value = 0;
        do {
            value = lowBound + (int) Math.floor(Math.random() * highBound);
        } while (!isBetween(value, lowBound, highBound));
        return value;
    }

    public static boolean isBetween(int value, int low, int high) {
        return low <= value && value <= high;
    }

    public static String format(double value) {
        return new DecimalFormat("###,###,###,###.##")
                .format(value);
    }

    public static String perFormat(double value) {
        //#,##0.00 '%'
        return new DecimalFormat("#,##0.00'%'")
                .format(value);
    }

    public static String dateFormat(Date value) {
        return new SimpleDateFormat("dd/MM/yyyy")
                .format(value);
    }

    /**
     * a < b return -1
     * a > b return 1
     * a == b return 0
     *
     * @param a
     * @param b
     * @return
     **/
    public static int compare(Object a, Object b) {
        switch (instanceOf(a, b)) {
            case "Integer":
                Integer int1 = (Integer) a;
                Integer int2 = (Integer) b;
                return int1 < int2 ? -1 :
                        int1 > int2 ? 1 : 0; //0==equal
            case "String":
                String str1 = (String) a;
                String str2 = (String) b;
                return str1.compareToIgnoreCase(str2) < 0 ? -1 :
                        str1.compareToIgnoreCase(str2) > 0 ? 1 : 0;
            case "Character":
                Character ch1 = (Character) a;
                Character ch2 = (Character) b;
                return ch1.compareTo(ch2) < 0 ? -1 :
                        ch1.compareTo(ch2) > 0 ? 1 : 0;
            case "CircularLinkedList":
                CircularLinkedList cir1 = (CircularLinkedList) a; CircularLinkedList cir2 = (CircularLinkedList) b;
                return !cir1.equals(cir2)  ? -1 : 0;

            case "Security":
                Security sec1 = (Security) a; Security sec2 = (Security) b;
                return sec1.getUser().compareToIgnoreCase(((Security) b).getUser()) < 0   ||  sec1.getPassWord().compareToIgnoreCase(((Security) b).getPassWord() ) < 0   ||  sec1.getRol().compareToIgnoreCase(((Security) b).getRol())< 0? -1 :
                        sec1.getUser().compareToIgnoreCase(((Security) b).getUser()) > 0   ||  sec1.getPassWord().compareToIgnoreCase(((Security) b).getPassWord() ) > 0   ||  sec1.getRol().compareToIgnoreCase(((Security) b).getRol()) >0 ? 1: 0;

            case "Customer":
                Customer p1 = (Customer) a;
                Customer p2 = (Customer) b;

                return p1.getId() < p2.getId() ? -1 :
                        p1.getId() > p2.getId() ? 1 : 0; //0==equal
//
//            case "PersonMood":
//                Person pm1 = (Person) a; String pm2 = (String) b;
//                return pm1.getMood().compareTo(pm2)<0? -1 :
//                        pm1.getMood().compareTo(pm2)>0? 1 : 0;
//            case "forArrayS":
//                return  0;
//            case "forClimate":
//                Climate c1 = (Climate) a; String c2 = (String) b;
//                if(c1.getPlace().getName().compareTo(c2)==0){return 0;}
//                return c1.getWeather().getWeather().compareTo(c2)<0? -1 :
//                        c1.getWeather().getWeather().compareTo(c2)>0? 1 : 0;
//
//            case "Climate":
//               Climate clim1 = (Climate) a;
//               Climate clim2 = (Climate) b;
//                return clim1.getPlace().getName().compareTo(clim2.getPlace().getName()) < 0 ? -1 :
//                        clim1.getWeather().getWeather().compareTo(clim2.getWeather().getWeather()) < 0 ? -1 :
//                                clim1.getPlace().getName().compareTo(clim2.getPlace().getName()) >0 ? 1 :
//                                        clim1.getWeather().getWeather().compareTo(clim2.getWeather().getWeather())  > 0 ? 1 :
//                                                clim1.getPlace().getName().compareTo(clim2.getPlace().getName()) !=
//                                                        clim1.getPlace().getName().compareTo(clim2.getPlace().getName()) ? -1 :0;
        }
        return 2; //Unknown
    }


    public static String instanceOf(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) return "Integer";
        if (a instanceof String && b instanceof String) return "String";
        if (a instanceof Character && b instanceof Character) return "Character";
        if (a instanceof Security && b instanceof Security) return "Security";
        if (a instanceof Customer && b instanceof Customer) return "Customer";
        if (a instanceof CircularLinkedList && b instanceof CircularLinkedList) return "Security";

        //  if (a instanceof Person && b instanceof Person) return "Person";
        // if (a instanceof Person && b instanceof Object) return "Person";
//        if (a instanceof Person && b instanceof String) return "PersonMood";
//        if (a instanceof ArrayStack && b instanceof ArrayStack) return "forArrayS";
//        if (a instanceof Climate && b instanceof String) return "forClimate";
//        if (a instanceof Climate && b instanceof Climate) return "Climate";

        return "Unknown"; //desconocido
    }


    public static char getAlphabet() {
        char alfabeto[] = new char[26];
        int cont = 0;
        for (char i = 'a'; i <= 'z'; i++)
            alfabeto[cont++] = i;
        return alfabeto[(int) (Math.random() * 25 - 1)];
    }

//    public static Object getObject() {
//        int num = random(0, 4);
//        Object list[] = { new Person(getFirstName(),getMood()), new Place(getPlace())
//                ,new Weather(getWeather()), new Climate(new Place(getPlace()), new Weather(getWeather()))
//        };
//        return list[num];
//    }


    public static int getAge(Date date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthdate = LocalDate.parse(dateFormat(date), fmt);
        LocalDate now = LocalDate.now();

        Period period = Period.between(birthdate, now);
        //System.out.printf("Tu edad es: %s años, %s meses y %s días",
        //            period.getYears(), period.getMonths(), period.getDays());
        return period.getYears();
    }

    public static void file(Object b, String name) throws IOException {
        FileWriter file = new FileWriter(name + ".txt");
        file.write(b + "\n");
        file.close();
    }


    public static void ReadFile(String string) {

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(string + ".txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null)
                System.out.println(linea);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }


    }


}

