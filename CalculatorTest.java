import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
@Test
    public void testParser(){
    String s = "5*4+3-2";
    String[] ss = Calculator.parseEquation(s);
    assertArrayEquals(ss, new String[]{"5", "4", "3", "2"});
}
}