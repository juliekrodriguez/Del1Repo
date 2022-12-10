package MScheckstyle;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CommentCheckUnitTest {
    public CommentCheck unit = new CommentCheck();
    int[] tokArr = new int[] { TokenTypes.BLOCK_COMMENT_BEGIN,
            TokenTypes.BLOCK_COMMENT_END, TokenTypes.COMMENT_CONTENT,
            TokenTypes.SINGLE_LINE_COMMENT};
    @Test
    public void getAcceptableTokensTest()
    {
        Assert.assertArrayEquals(tokArr, unit.getAcceptableTokens());
    }
    @Test
    public void getReqTokensTest()
    {
        int[] empty_arr = new int[0];
        Assert.assertArrayEquals(empty_arr, unit.getRequiredTokens());

    }
    @Test
    public void getDefTokensTest()
    {
        Assert.assertArrayEquals(tokArr, unit.getDefaultTokens());
    }
    @Test
    public void ifCommentNodeReqTest()
    {
        assertTrue(unit.isCommentNodesRequired());
    }


}
