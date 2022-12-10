package MScheckstyleCheck;

import com.puppycrawl.tools.checkstyle.api.*;

public class MScheckstyle extends AbstractCheck{
    private int c = 0;

    @Override
    public int[] getDefaultTokens() {
        // TODO Auto-generated method stub
        return new int[] { TokenTypes.VARIABLE_DEF };
    }

    @Override
    public int[] getAcceptableTokens() {
        // TODO Auto-generated method stub
        return new int[] { TokenTypes.VARIABLE_DEF };
    }

    @Override
    public int[] getRequiredTokens() {
        // TODO Auto-generated method stub
        return new int[0];
    }
    @Override
    public void visitToken(DetailAST ast) {
        if (ast.branchContains(TokenTypes.TYPE))
        {
          c++;  
        }
    }
    @Override
    public void finishTree(DetailAST ast) {
        log(ast.getLineNo(), "varcount", c);
        c = 0;
    }
  }
