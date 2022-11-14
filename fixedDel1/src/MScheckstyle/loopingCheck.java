package MScheckstyle;

import com.puppycrawl.tools.checkstyle.api.*;

public class loopingCheck extends AbstractCheck{
    private int flc = 0;
    private int c = 0;
    

    @Override
    public int[] getDefaultTokens() {
        // TODO Auto-generated method stub
        return new int[] { TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE };
    }

    @Override
    public int[] getAcceptableTokens() {
        // TODO Auto-generated method stub
        return new int[] { TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE };
    }

    @Override
    public int[] getRequiredTokens() {
        // TODO Auto-generated method stub
        return new int[0];
    }
    public void visitTok(DetailAST ast)
    {
        c++;
    }
    public int getLoopCount()
    {
        return flc;
    }
    public void finishTree(DetailAST ast)
    {
        log(ast.getLineNo(), "loopCounter ", c);
        flc = c;
        c = 0;
    }

}
