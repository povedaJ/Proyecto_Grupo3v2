package domain;

import domain.Tree.AVL;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SupplierTest {

    @Test
    void testToString() {
        AVL avl =new AVL();
        avl.add(new Supplier(1234,"EPA","8787878","epa@gmail.com","Curridabat"));
        avl.add(new Supplier(6090,"Colono","8787878","colono@gmail.com","Curridabat"));
        avl.add(new Supplier(9065,"El Lagar","8787878","lagar@gmail.com","Curridabat"));

            System.out.println(avl.toString());


    }
}