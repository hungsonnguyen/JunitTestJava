package com.zeusbe;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class TestJUnit1 extends TestCase {
    protected double a;
    protected double b;

    @Before
    public void setUp() {
        a = 2.0;
        b = 3.0;
    }

    @Test
    public void testAdd() {
//        dem so test case
        System.out.println("Số test case = " + this.countTestCases());

//        test getName
        String name = this.getName();
        System.out.println("Test case name = " + name);

//        test setName
        this.setName("TestNew add");
        String newName = this.getName();
        System.out.println("update test case name = " + newName);

    }

    //    tearDown được sử dụng đẻ đóng kết nối
    public void tearDown() {
        a = 0;
        b = 0;
    }


    @Test
    public void testAdd2() {
        // test data
        int num = 5;
        String str = "Junit Example 1";
        String str1 = null;

        // kiem tra gia tri bang nhau
        assertEquals("Junit Example 2", str);



    }
    @Test
    public void testAdd3() {
        // test data
        int num = 5;

        // kiem tra dieu kien false
        assertFalse(num < 6);

    }
    @Test
    public void testAdd4() {
        // test data
        String str1 = "data";

        // kiem tra gia tri khac null
        assertNotNull(str1);
    }
}
