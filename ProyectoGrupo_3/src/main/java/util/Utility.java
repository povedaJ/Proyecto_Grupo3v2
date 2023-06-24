/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.*;
import domain.List.CircularLinkedList;
import domain.List.SinglyLinkedList;
import domain.Tree.AVL;
import domain.Tree.BST;
import domain.Tree.BTree;
import domain.queue.HearderLinkedQueue;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
/**
 * @author Profesor Lic. Gilberth Chaves Avila
 */
public class Utility {

    //private static String routeImagen = "file:ProyectoGrupo_3/logo1.png";
    private static String routeImagen = "file:ProyectoGrupo_3/Imagen/logo2.jpg";
    private static String nameApp = "Ferreteria El Clavo Oxidado";

    public static String getRouteImagen() {
        return routeImagen;
    }

    public static void setRouteImagen(String routeImagen) {
        Utility.routeImagen = "ProyectoGrupo_3/Imagen/" + routeImagen;
    }

    private static Random random;    // pseudo-random number generator
    private static long seed;        // pseudo-random number generator seed
    private static SinglyLinkedList confiGeneralesList;//Configuraciones Generales
    private static CircularLinkedList securityList;//Seguridad del Sistema
    private static AVL supplierAVL;//Mantenimiento de proveedores
    private static AVL productsAVL;//Mantenimiento de Productos
    private static SinglyLinkedList customerList;//mantenimiento de clientes
    private static BTree inventoryBT; //control de inventario
    private static AVL ordersManagement;// Gestión de pedidos
    private static BST forecastDemandBST;//Previsión de la demanda
    private static HearderLinkedQueue costsControlQ;//Control de costos
    private static AVL binnacleList ;

    static Product[] productsList;


    // static initializer
    static {
        // this is how the seed was set in Java 1.4
        seed = System.currentTimeMillis();
        random = new Random(seed);
        customerList = new SinglyLinkedList();
        securityList = new CircularLinkedList();
        productsAVL = new AVL();
        supplierAVL = new AVL();
        binnacleList= new AVL();

//
    }

    public static CircularLinkedList getSecurityList() {
        return securityList;
    }

    public static void setSecurityList(CircularLinkedList securityList) {
        Utility.securityList = securityList;
    }

    public static Product[] getProductsList() {
        return productsList;
    }

    public static CircularLinkedList securityList() {
        return securityList;
    }

    public static SinglyLinkedList getCustomerList() {
        return customerList;
    }

    public static void setCustomerList(SinglyLinkedList customerList) {
        Utility.customerList = customerList;
    }

    public static String getNameApp() {
        return nameApp;
    }

    public static void setNameApp(String nameApp) {
        Utility.nameApp = nameApp;
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
                CircularLinkedList cir1 = (CircularLinkedList) a;
                CircularLinkedList cir2 = (CircularLinkedList) b;
                return !cir1.equals(cir2) ? -1 : 0;

            case "Security":
                Security sec1 = (Security) a;
                Security sec2 = (Security) b;
                return sec1.getUser().compareToIgnoreCase(((Security) b).getUser()) < 0 || sec1.getPassWord().compareToIgnoreCase(((Security) b).getPassWord()) < 0 || sec1.getRol().compareToIgnoreCase(((Security) b).getRol()) < 0 ? -1 :
                        sec1.getUser().compareToIgnoreCase(((Security) b).getUser()) > 0 || sec1.getPassWord().compareToIgnoreCase(((Security) b).getPassWord()) > 0 || sec1.getRol().compareToIgnoreCase(((Security) b).getRol()) > 0 ? 1 : 0;

            case "Customer":
                Customer p1 = (Customer) a;
                Customer p2 = (Customer) b;

                return p1.getId() < p2.getId() ? -1 :
                        p1.getId() > p2.getId() ? 1 : 0; //0==equal
            case "Product":
                Product b1 = (Product) a;
                Product b2 = (Product) b;
                String name1 = b1.getDescription();
                String name2 = b2.getDescription();
                return name1.compareToIgnoreCase(name2) < 0 ? -1 :
                        name1.compareToIgnoreCase(name2) > 0 ? 1 : 0;
            case "Eliminar nulo":
                Product prod1 = (Product) a;
                String pro1 = prod1.getDescription();
                String pro2 = (String) b;
                return pro1 == pro2 ? 0 : 1;
            case "Eliminar los nulos":
                String produc1 = (String) a;
                String produc2 = (String) b;
                return produc1 == produc2 ? 0 : 1;

        }
        return 2; //Unknown
    }

    public static boolean isNumberExp(String str) {
        str = str.replaceAll("\\s", "");
        int n = str.length();
        for (int i = 0; i < n; i++) {
            if (Character.isDigit(str.charAt(i)) && !Character.isLetter(str.charAt(i))) {
                //true si es digito
                return true;
            }
        }
        return false;
    }

    public static String instanceOf(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) return "Integer";
        if (a instanceof String && b instanceof String) return "String";
        if (a instanceof Character && b instanceof Character) return "Character";
        if (a instanceof Security && b instanceof Security) return "Security";
        if (a instanceof Customer && b instanceof Customer) return "Customer";
        if (a instanceof CircularLinkedList && b instanceof CircularLinkedList) return "Security";
        if (a instanceof Product && b instanceof Product) return "Product";
        if (a instanceof Product && b == null) return "Eliminar nulo";
        if (a == null && b == null) return "Eliminar los nulos";

        return "Unknown"; //desconocido
    }


    public static char getAlphabet() {
        char alfabeto[] = new char[26];
        int cont = 0;
        for (char i = 'a'; i <= 'z'; i++)
            alfabeto[cont++] = i;
        return alfabeto[(int) (Math.random() * 25 - 1)];
    }


    public static int getAge(Date date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthdate = LocalDate.parse(dateFormat(date), fmt);
        LocalDate now = LocalDate.now();

        Period period = Period.between(birthdate, now);
        //System.out.printf("Tu edad es: %s años, %s meses y %s días",
        //            period.getYears(), period.getMonths(), period.getDays());
        return period.getYears();
    }

    public static AVL getBinnacleList() {
        return binnacleList;
    }

    public static void setBinnacleList(AVL binnacleList) {
        Utility.binnacleList = binnacleList;
    }

    public static String MD5(String md5) throws IOException {

        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }

            return sb.toString();

        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    public static void exportToPDF(Object show,String name){
       // System.out.println("SOY SHOW: \n"+show);
        try  {

            PDDocument documento = new PDDocument();
            PDPage pagina = new PDPage(PDRectangle.A6);
            documento.addPage(pagina);
            PDPageContentStream contenido = new PDPageContentStream(documento, pagina);
            contenido.beginText();
            contenido.setFont(PDType1Font.TIMES_BOLD, 12);
            contenido.newLineAtOffset(20, pagina.getMediaBox().getHeight()-52);
            String text = show.toString().replace("\n", ""); // Reemplazar el carácter de nueva línea
            contenido.showText(text);


            contenido.endText();

            contenido.close();

            documento.save(name);
        }catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    public static void exportToPDf2(Object show, String name) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);

            float margin = 50;
            float yPosition = page.getMediaBox().getHeight() - margin;
            float width = page.getMediaBox().getWidth() - 2 * margin;
            float startX = page.getMediaBox().getLowerLeftX() + margin;
            float endX = page.getMediaBox().getUpperRightX() - margin;

            String text = show.toString().replace("\n", "\n"); // Reemplazar el carácter de nueva línea

            contentStream.beginText();
           contentStream.newLineAtOffset(startX, yPosition);


            for (String line : text.split("\n")) {
                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -15); // Espaciado entre líneas
            }

            contentStream.endText();
            contentStream.close();

            document.save("ProyectoGrupo_3/Informes" + File.separator + name + ".pdf");
            document.close();

            System.out.println("PDF generado exitosamente.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void exportToPDF2(Object show, String name) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.COURIER_BOLD, 6);

            float margin = 50;
            float yPosition = page.getMediaBox().getHeight() - margin;
            float width = page.getMediaBox().getWidth() - 2 * margin;
            float startX = page.getMediaBox().getLowerLeftX() + margin;
            float endX = page.getMediaBox().getUpperRightX() - margin;

            // Agregar imagen
            String imagePath = "ProyectoGrupo_3/Imagen/logo2.jpg"; // Reemplaza con la ruta de tu imagen
            PDImageXObject image = PDImageXObject.createFromFile(imagePath, document);
            float imageWidth = 50; // Ancho de la imagen en puntos (ajusta según sea necesario)
            float imageHeight = imageWidth * image.getHeight() / image.getWidth();
            contentStream.drawImage(image, startX, yPosition - imageHeight, imageWidth, imageHeight);

            // Limitar espacio de escritura
            float textYPosition = yPosition - imageHeight - 20; // Espacio entre imagen y texto
            float textWidth = width;
            float textHeight = yPosition - margin - textYPosition;

            String text = show.toString().replace("\n", "li"); // Reemplazar el carácter de nueva línea
           // String text = "Soy el texto "; // Reemplazar el carácter de nueva línea

            float textLimitX = startX; // Coordenada X de inicio del límite
            float textLimitY = textYPosition - textHeight; // Coordenada Y de inicio del límite
            float textLimitWidth = textWidth; // Ancho del límite
            float textLimitHeight = width; // Alto del límite


            contentStream.addRect(textLimitX, textLimitY, textLimitWidth, textLimitHeight);
            contentStream.clip();
            contentStream.beginText();
            contentStream.setFont(PDType1Font.COURIER_BOLD, 6);
            contentStream.newLineAtOffset(startX, textYPosition);
            contentStream.setLeading(15); // Espaciado entre líneas

            contentStream.showText(text);
            contentStream.endText();
            contentStream.close();

            document.save("ProyectoGrupo_3/Informes" + File.separator + name + ".pdf"); // Reemplaza con la ruta y nombre de tu archivo PDF
            document.close();

            System.out.println("PDF generado exitosamente.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void file(Object b, String name) throws IOException {
        FileWriter file = new FileWriter("ProyectoGrupo_3/ArchivosTXT/" + name + ".txt");
        file.write(b + "\n");
        file.close();
    }


    public static CircularLinkedList addreadSecuritiesFromFile(String fileName) throws FileNotFoundException {
        File file = new File("ProyectoGrupo_3/ArchivosTXT/" + fileName + ".txt");

        Scanner scanner = new Scanner(file);
        securityList.clear();
        try {
            securityList.add(new Security("admin1",MD5("admin1"),"1"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Security{")) {
                int startIndex = line.indexOf("user='") + 6;
                int endIndex = line.indexOf("'", startIndex);
                String user = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("passWord='") + 10;
                endIndex = line.indexOf("'", startIndex);
                String passWord = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("rol='") + 5;
                endIndex = line.indexOf("'", startIndex);
                String rol = line.substring(startIndex, endIndex);

                Security security = new Security(user, passWord, rol);
                securityList.add(security);
            }
        }

        scanner.close();

        return securityList;
    }
    public static SinglyLinkedList addReadCustomersFromFile(String fileName) throws FileNotFoundException {
        File file = new File("ProyectoGrupo_3/ArchivosTXT/" + fileName + ".txt");

        Scanner scanner = new Scanner(file);
         customerList.clear();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Customer{")) {
                int startIndex = line.indexOf("id=") + 3;
                int endIndex = line.indexOf(",", startIndex);
                int id = Integer.parseInt(line.substring(startIndex, endIndex).trim());

                startIndex = line.indexOf("name='") + 6;
                endIndex = line.indexOf("'", startIndex);
                String name = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("phoneNumber='") + 13;
                endIndex = line.indexOf("'", startIndex);
                String phoneNumber = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("email='") + 7;
                endIndex = line.indexOf("'", startIndex);
                String email = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("address='") + 9;
                endIndex = line.indexOf("'", startIndex);
                String address = line.substring(startIndex, endIndex);

                Customer customer = new Customer(id, name, phoneNumber, email, address);
                //System.out.println("EAD"+customerList);
                customerList.add(customer);
            }
        }

        scanner.close();

        return customerList;
    }
    public static AVL addreadProductsFromFile(String fileName) throws FileNotFoundException {
        File file = new File("ProyectoGrupo_3/ArchivosTXT/" + fileName + ".txt");
        Scanner scanner = new Scanner(file);
        productsAVL.clear();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Product{")) {
                int startIndex = line.indexOf("id=") + 3;
                int endIndex = line.indexOf(",", startIndex);
                int id = Integer.parseInt(line.substring(startIndex, endIndex));

                startIndex = line.indexOf("description='") + 13;
                endIndex = line.indexOf("'", startIndex);
                String description = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("price=") + 6;
                endIndex = line.indexOf(",", startIndex);
                double price = Double.parseDouble(line.substring(startIndex, endIndex));

                startIndex = line.indexOf("currentStock=") + 13;
                endIndex = line.indexOf(",", startIndex);
                int currentStock = Integer.parseInt(line.substring(startIndex, endIndex));

                startIndex = line.indexOf("minimumStock=") + 13;
                endIndex = line.indexOf(",", startIndex);
                int minimumStock = Integer.parseInt(line.substring(startIndex, endIndex));

                startIndex = line.indexOf("supplierId=") + 11;
                endIndex = line.indexOf("}", startIndex);
                int supplierId = Integer.parseInt(line.substring(startIndex, endIndex));

                Product product = new Product(id,description, price, currentStock, minimumStock, supplierId);
                productsAVL.add(product);
            }
        }

        scanner.close();

        return productsAVL;
    }


    public static AVL addreadBinnacleFromFile(String fileName) throws FileNotFoundException {
        File file = new File("ProyectoGrupo_3/ArchivosTXT/"+fileName + ".txt");
        // ProyectoGrupo_3/ArchivosTXT/Security.txt
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Binnacle{")) {
                int startIndex = line.indexOf("id='") + 6;
                int endIndex = line.indexOf("'", startIndex);
                String id = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("dateHour='") + 10;
                endIndex = line.indexOf("'", startIndex);
                String dateHour = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("Description='") + 5;
                endIndex = line.indexOf("'", startIndex);
                String description = line.substring(startIndex, endIndex);

                Binnacle binnacle = new Binnacle(LocalDateTime.parse(dateHour), id, description);
                binnacleList.add(binnacle);
            }
        }

        scanner.close();

        return binnacleList;
    }
    public static AVL addReadSuppliersFromFile(String fileName) throws FileNotFoundException {
        File file = new File("ProyectoGrupo_3/ArchivosTXT/" + fileName + ".txt");

        Scanner scanner = new Scanner(file);
        supplierAVL.clear();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Supplier{")) {
                int startIndex = line.indexOf("id=") + 3;
                int endIndex = line.indexOf(",", startIndex);
                int id = Integer.parseInt(line.substring(startIndex, endIndex).trim());

                startIndex = line.indexOf("name='") + 6;
                endIndex = line.indexOf("'", startIndex);
                String name = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("phoneNumber='") + 13;
                endIndex = line.indexOf("'", startIndex);
                String phoneNumber = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("email='") + 7;
                endIndex = line.indexOf("'", startIndex);
                String email = line.substring(startIndex, endIndex);

                startIndex = line.indexOf("address='") + 9;
                endIndex = line.indexOf("'", startIndex);
                String address = line.substring(startIndex, endIndex);

                Supplier supplier = new Supplier(id, name, phoneNumber, email, address);
                System.out.println("soy del read"+supplier.toString());
                supplierAVL.add(supplier);
            }
        }

        scanner.close();

        return supplierAVL;
    }
    public static boolean isCorreo(String email) {
        String subCorreo = null;
        if ('@' == email.charAt(0)) {
            return false;
        }
        for (int i = 0; i < email.length(); i++) {
            if ('@' == email.charAt(i)) {
                subCorreo = email.toLowerCase().substring(i);
            }
        }
        if (subCorreo == null || subCorreo.length() < 5 || !(97 <= subCorreo.charAt(1) && subCorreo.charAt(1) <= 122)) {
            return false;
        }

        int i = 1;
        while ('.' != subCorreo.charAt(i)) {
            i++;
            if (i >= subCorreo.length()) {
                return false;
            }
        }
        if (i + 2 >= subCorreo.length()) {
            return false;
        }
        return true;
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

    public static SinglyLinkedList getConfiGeneralesList() {
        return confiGeneralesList;
    }

    public static void setConfiGeneralesList(SinglyLinkedList confiGeneralesList) {
        Utility.confiGeneralesList = confiGeneralesList;
    }

    public static AVL getSupplierAVL() {
        return supplierAVL;
    }

    public static void setSupplierAVL(AVL supplierAVL) {
        Utility.supplierAVL = supplierAVL;
    }

    public static AVL getProductsAVL() {
        return productsAVL;
    }

    public static void setProductsAVL(AVL productsAVL) {
        Utility.productsAVL = productsAVL;
    }

    public static BTree getInventoryBT() {
        return inventoryBT;
    }

    public static void setInventoryBT(BTree inventoryBT) {
        Utility.inventoryBT = inventoryBT;
    }

    public static AVL getOrdersManagement() {
        return ordersManagement;
    }

    public static void setOrdersManagement(AVL ordersManagement) {
        Utility.ordersManagement = ordersManagement;
    }

    public static BST getForecastDemandBST() {
        return forecastDemandBST;
    }

    public static void setForecastDemandBST(BST forecastDemandBST) {
        Utility.forecastDemandBST = forecastDemandBST;
    }

    public static HearderLinkedQueue getCostsControlQ() {
        return costsControlQ;
    }

    public static void setCostsControlQ(HearderLinkedQueue costsControlQ) {
        Utility.costsControlQ = costsControlQ;
    }

    public static void llenarProductosLista() {
        productsList = new Product[]{
                new Product(1,"Enchufe de vinil 15 a 125v", 1395.0, 12, 20, 11),
                new Product(2,"Extensión para exterior 3x12 awg 15m", 59950.0, 7, 12, 12),
                new Product(3,"Plafón de policarbonato 150 w", 900.0, 3, 37, 13),
                new Product(4,"Tubo cpvc 1/2' x6m", 12450.0, 10, 12, 14),
                new Product(5,"Tubo emt 1 1/2' ul", 10.0, 20, 38, 15),
                new Product(6,"Cable transparente N°18", 220.0, 25, 42, 16)
        };
    }  public static void llenarSupplier() {
        supplierAVL.add(new Supplier(1234,"EPA","8787878","epa@gmail.com","Curridabat"));
        supplierAVL.add(new Supplier(6090,"Colono","8787878","colono@gmail.com","Curridabat"));
        supplierAVL.add(new Supplier(9065,"El Lagar","8787878","lagar@gmail.com","Curridabat"));
        try {
            file(supplierAVL,"Supplier");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Product[] getProductosList() { //hay que editarlo porque los datos son inventados
        return productsList;
    }

    public static void setProductsList(Product[] productsLista) {
        productsList = productsLista;
    }

    public static boolean isPhoneNumber(String numero) {
        for (int i = 0; i < numero.length(); i++) {
            if (numero.charAt(i) < 48 || numero.charAt(i) > 57) {
                return false;
            }if(numero.length() >=9 || numero.length() <=7){
                return false;
            }
        }
        return true;
    }

    public static boolean isIdentification(String numero) {
        for (int i = 0; i < numero.length(); i++) {
            if (numero.charAt(i) < 48 || numero.charAt(i) > 57) {
                return false;
            }if(numero.length() >=10 || numero.length() <=8){
                return false;
            }
        }
        return true;
    }

    public static boolean isNumber(String numero) {
        for (int i = 0; i < numero.length(); i++) {
            if (numero.charAt(i) < 48 || numero.charAt(i) > 57)
                return false;
        }
        return true;
    }
}

