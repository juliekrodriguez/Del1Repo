package MScheckstyleCheck;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CastingCheck extends AbstractCheck{
    
    private int c= 0;
    
    public void visitTok(DetailAST ast)
    {
        c++;
    }

    @Override
    public int[] getDefaultTokens() {
        // TODO Auto-generated method stub
        return new int[] {TokenTypes.TYPECAST};
    }

    @Override
    public int[] getAcceptableTokens() {
        // TODO Auto-generated method stub
        return new int[]  {TokenTypes.TYPECAST};
    }

    @Override
    public int[] getRequiredTokens() {
        // TODO Auto-generated method stub
        return new int[0];
    }
    
    public void finishTree(DetailAST ast)
    {
        log(ast.getLineNo(), "castingCount",c);
        c=0;
    }

}
