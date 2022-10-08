import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
@Test
    public void testParser(){
        String s = "5*4+3-2";
        String[] ss = Calculator.parseEquation(s);
        assertArrayEquals(ss, new String[]{"5", "4", "3", "2"});
    }
    @Test
    public void testNewParser(){
        String s = "67 - 90 * 78";
        String[] ss = Calculator.peqn2(s);
        assertArrayEquals(ss, new String[]{"67", "-", "90", "*", "78"});
    }
    @Test
    public void testIn2Post(){
        String[] in = {"67", "-", "90", "*", "78"};
        String[] post = {"67", "90", "78", "*", "-"};
        String[] ss = Calculator.infixToPostfix(in);
        assertArrayEquals(ss,post);
    }
    @Test
    public void testPrec(){
        String add = "+";
        String sub = "-";
        String mul = "*";
        String div = "/";
        assertEquals(Calculator.prec(add), Calculator.prec(sub));
        assertTrue(Calculator.prec(mul)>Calculator.prec(add));
        assertTrue(Calculator.prec(mul)>Calculator.prec(sub));
        assertTrue(Calculator.prec(div)>Calculator.prec(add));
        assertTrue(Calculator.prec(div)>Calculator.prec(sub));
    }
    @Test
    public void testAns(){
        String[] exp = {"3864","4","138","*","/","23","-"};
        double ans = -16;
        assertEquals(ans, Calculator.evaluateExp(exp));
    }
}