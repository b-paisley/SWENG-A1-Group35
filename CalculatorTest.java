import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;


class CalculatorTest {

    @Test
    public void testConstructor()
    {
      new Calculator();
    }

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
        String[] exp = {"67","90","78","-","*"};
        double ans = 804;
        assertEquals(ans, Calculator.evaluateExp(exp), 0);
    }

    @Test
    public void testUnexpectedString(){
        char[] inp = {'3', 'a', '*', 'h', 'a'};
        assertFalse(Calculator.noUnexpectedString(inp));
}
    @Test
    public void testNoOperatorsStartOrEnd(){
        char[] inp = {'+', '6', '4', '+', '4'};
        assertFalse(Calculator.noOperatorsOnStartOrEnd(inp));
 }
    @Test
    public void testNoOperatorsTogether(){
        char[] inp = {'3', '+', '+', '9', '1'};
        assertFalse(Calculator.noOperatorsTogether(inp));
}
    @Test
    public void testNoUnclosedBrackets(){
        char[] inp = {'(', '3', '+', '9', '1','+','1',')','-','('};
        assertFalse(Calculator.noUnclosedBrackets(inp));
}
}