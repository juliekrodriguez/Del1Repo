package julie.structMetricsChecks;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;


public class LinesOfComments {
    public static final String MSG_KEY = "linesofcomments";
    private int lines = 0;
    public void beginTree(DetailAST root)
    {
        lines = 0;
    }
    public int getLinesOfCom()
    {
        return lines;
    }
    public static int[] getAcceptableTokens()
    {
        return new int[] {TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN };
    }
    public static boolean isAcceptableToken(int type)
    {
        return Utils.contains(getAcceptableTokens(), type);
    }
    public void visitTok(DetailAST ast)
    {
        int type = ast.getType();
        if(!isAcceptableToken(type))
        {
            return;
        }
        if(TokenTypes.SINGLE_LINE_COMMENT == type)
        {
            lines++;
        }
        else if(TokenTypes.BLOCK_COMMENT_BEGIN == type)
        {
            int begCom = ast.getLineNo();
            int comEnd = ast.getLastChild().getLineNo();
            int numlines = comEnd - begCom + 1;
            lines += numlines;
        }
    }

}
