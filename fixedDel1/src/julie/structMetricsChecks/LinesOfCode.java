package julie.structMetricsChecks;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class LinesOfCode {
    private boolean useLog = false;
    private int lines = 0;
    private int loglines = 0;
    public void beginTree(DetailAST root)
    {
        loglines = 0;
        lines = 0;
        DetailAST ast = root;
        DetailAST next = ast.getNextSibling();
        while(null!=next)
        {
            ast = next;
            next = ast.getNextSibling();
        }
        next = ast.getLastChild();
        while(null!=next)
        {
            ast = next;
            next = ast.getLastChild();
        }
        lines = ast.getLineNo();
    }
    public void setUseLogLines(boolean val)
    {
        useLog = val;
    }
    public int getLines()
    {
        return useLog ? loglines : lines;
    }
    public static boolean isAcceptableToken(int Type)
    {
        return Utils.contains(getAcceptableTokens(), Type);
    }
    public static int[] getAcceptableTokens()
    {
        return new int[] { TokenTypes.SEMI };
    }
    public void visitTok(DetailAST ast)
    {
        if(!isAcceptableToken(ast.getType()))
        {
            return;
        }
        loglines++;
    }
    

}
