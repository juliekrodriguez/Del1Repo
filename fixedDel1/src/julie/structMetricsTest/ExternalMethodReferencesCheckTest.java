package julie.structMetricsTest;
import julie.structMethod.ExternalMethodReferencesCheck;
import static org.junit.Assert.*;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ExternalMethodReferencesCheckTest {
    private int[] acceptableTok = { TokenTypes.CLASS_DEF, TokenTypes.METHOD_CALL, TokenTypes.METHOD_REF};
    private int[] reqTok = new int [0];
    private int[] defTok = acceptableTok;
    private ExternalMethodReferencesCheck emrc = new ExternalMethodReferencesCheck();
    
    @Test
    public void getDefTokTest()
    {
        assertArrayEquals(defTok, emrc.getDefaultTokens());
    }
    @Test
    public void getAcceptableTokTest()
    {
        assertArrayEquals(acceptableTok, emrc.getAcceptableTokens());
    }
    @Test
    public void getReqTokTest()
    {
        assertArrayEquals(reqTok, emrc.getRequiredTokens());
    }
    

}
