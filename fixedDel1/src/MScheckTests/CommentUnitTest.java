package MScheckTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import MScheckstyleCheck.CommentCheck;
public class CommentUnitTest {
    public CommentCheck mock = new CommentCheck();
    int[]tokArr = new int[] {TokenTypes.BLOCK_COMMENT_BEGIN,
            TokenTypes.BLOCK_COMMENT_END, TokenTypes.COMMENT_CONTENT,
            TokenTypes.SINGLE_LINE_COMMENT};
    
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
    
    @Test
    public void testIfCommNodesReq()
    {
        assertTrue(mock.isCommentNodeReq());
    }


}
