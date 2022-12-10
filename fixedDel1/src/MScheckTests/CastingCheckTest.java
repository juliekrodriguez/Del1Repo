package MScheckTests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import MScheckstyleCheck.CastingCheck;

public class CastingCheckTest {
    
    public CastingCheck mock = new CastingCheck();
    
    int[] tokArr = new int[] {TokenTypes.TYPECAST};
    
    @Test
    public void testgetAcceptableTokens() {     
        Assert.assertArrayEquals(tokArr, mock.getAcceptableTokens());
    }
    
    @Test
    public void testgetRequiredTokens() {     
        int[] empArr = new int[0];
        Assert.assertArrayEquals(empArr, mock.getRequiredTokens());
    }
    
    @Test
    public void testgetDefaultTokens() {     
        Assert.assertArrayEquals(tokArr, mock.getDefaultTokens());
    }



}
