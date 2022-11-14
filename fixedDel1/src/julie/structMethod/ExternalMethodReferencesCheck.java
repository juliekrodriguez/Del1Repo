package julie.structMethod;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import julie.structMetricsChecks.ExternalMethodReferences;

public class ExternalMethodReferencesCheck extends AbstractCheck {
    //new inst
    ExternalMethodReferences emr = new ExternalMethodReferences();

    @Override
    public int[] getDefaultTokens() {
        // TODO Auto-generated method stub
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        // TODO Auto-generated method stub
        return ExternalMethodReferences.getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        // TODO Auto-generated method stub
        return new int[0];
    }
    public void beginTree(DetailAST root)
    {
        emr.beginTree(root);
    }
    
    public void visitToken(DetailAST ast)
    {//call emr
        emr.visitToken(ast);
    }
    
    public void leaveToken(DetailAST ast)
    {//call emr
        emr.leaveToken(ast);
    }
    public void finishTree(DetailAST ast)
    {
        int val = emr.getExternalRef();
        log(ast.getLineNo(), "external method references: " + val);
    }

}
