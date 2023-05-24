/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.*;

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

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed
//    private static PriorityLinkedQueue priorityLinkedQueue;
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
//        priorityLinkedQueue = new PriorityLinkedQueue();
//        linkedQueue= new LinkedQueue();
//        linkedStack= new LinkedStack();
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
//            case "Person":
//                Person p1 = (Person) a;
//                Person p2 = (Person) b;
////                return p1.getName().compareTo(p2.getName())!=
////                        p1.getMood().compareTo(p2.getMood()) ? -1 :0;
//                return p1.getName().compareTo(p2.getName()) < 0 ? -1 :
//                        p1.getMood().compareTo(p2.getMood()) < 0 ? -1 :
//                                p1.getName().compareTo(p2.getName()) >0 ? 1 :
//                                        p1.getMood().compareTo(p2.getMood()) > 0 ? 1 :
//                                                p1.getName().compareTo(p2.getName())!=
//                                                p1.getMood().compareTo(p2.getMood()) ? -1 :0;
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





}
