package util;

import domain.Security;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UtilityTest {

    @Test
    void test() {
        try {
            //Utility.ReadFile("Security");
//            util.Utility.securityList().add(new Security("Admin",util.Utility.MD5("1234"),"1"));
//            util.Utility.securityList().add(new Security("User",util.Utility.MD5("12345"),"2"));
//            Utility.file(Utility.securityList().toString(),"Security");
//            System.out.println(Utility.readSecurityFromFile("Security"));
//            System.out.println(Utility.readSecurityFromFile("Security"));

            System.out.println("Listsss\n"+Utility.addreadSecuritiesFromFile("Security"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}