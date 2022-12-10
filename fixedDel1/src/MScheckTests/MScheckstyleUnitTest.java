package MScheckTests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import MScheckstyleCheck.MScheckstyle;

public class MScheckstyleUnitTest {
    public MScheckstyle mock = new MScheckstyle();
    public int[] ucorr = new int[] {TokenTypes.VARIABLE_DEF};
    
    @Test
    public void testgetAcceptableTokens() {
        Assert.assertArrayEquals(ucorr,mock.getAcceptableTokens());
    }
    
    @Test
    public void testgetRequiredTokens() {
        int[] ucorr =  new int[0];
        Assert.assertArrayEquals(ucorr,mock.getAcceptableTokens());
    }
    
    @Test
    public void testgetDefaultTokens() {
        Assert.assertArrayEquals(ucorr,mock.getAcceptableTokens());
    }


}
