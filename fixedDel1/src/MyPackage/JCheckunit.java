package MyPackage;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class JCheckunit 
{
    JCheck check = new JCheck();
    int[] testTokArr = {TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN,TokenTypes.VARIABLE_DEF, TokenTypes.TYPECAST,TokenTypes.METHOD_REF};
    int[] testEmptyArr = new int[0];
    
    @Test 
    void isCommentNodeReqTest()
    {
        assertTrue(check.isCommentNodeReq());
    }
    
    @Test
    void getToks()
    {
        assertArrayEquals(testTokArr, check.getDefaultTokens());
        assertArrayEquals(testTokArr, check.getAcceptableTokens());
        assertArrayEquals(testEmptyArr, check.getRequiredTokens());
    }


}