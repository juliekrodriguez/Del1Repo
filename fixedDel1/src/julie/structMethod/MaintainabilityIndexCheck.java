package julie.structMethod;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import julie.structMetricsChecks.CyclomaticComplexity;
import julie.structMetricsChecks.HalsteadVolume;
import julie.structMetricsChecks.LinesOfCode;
import julie.structMetricsChecks.LinesOfComments;
import julie.structMetricsChecks.Utils;
public class MaintainabilityIndexCheck extends AbstractCheck {
    
    public static final String MSG_KEY = "maintainabilityIndex";
    private CyclomaticComplexity cc = new CyclomaticComplexity();
    private HalsteadVolume hv = new HalsteadVolume();
    private LinesOfCode loCode = new LinesOfCode();
    private LinesOfComments loComm = new LinesOfComments();
    
    public void beginTree(DetailAST root)
    {
        loCode.setUseLogLines(false);
        hv.beginTree(root);
        cc.beginTree(root);
        loCode.beginTree(root);
        loComm.beginTree(root);

    }

    @Override
    public int[] getDefaultTokens() {
        // TODO Auto-generated method stub
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        // TODO Auto-generated method stub
        return Utils.concatAll( 
                HalsteadVolume.getAcceptableTokens(), 
                LinesOfComments.getAcceptableTokens(),
                CyclomaticComplexity.getAcceptableTokens(),
                LinesOfCode.getAcceptableTokens());
    }

    @Override
    public int[] getRequiredTokens() {
        // TODO Auto-generated method stub
        return new int[0];
    }
    
    public void visitToken(DetailAST ast)
    {
        hv.visitTok(ast);
        cc.visitTok(ast);
        loCode.visitTok(ast);
        loComm.visitTok(ast);
    }
    
    public void leaveToken(DetailAST ast)
    {
        cc.leaveTok(ast);
    }
    private double calcMaintainablilityIndex()
    {
        double MI = 171d - 5.2d * Utils.log(hv.getHalsVolume(), 2) - 0.23d * (double)cc.getCC() - 16.2d *  Utils.log(loCode.getLines(), 2) + 50d * Math.sin(Math.sqrt(2.4d * calcCommentPerAsRad()));
        return MI;
    }
    private double calcCommentPerAsRad()
    {
        double perc = (double)loComm.getLinesOfCom()/(double)loCode.getLines();
        return perc  * (2 * Math.PI);
    }
    public void finishTree(DetailAST ast)
    {
        double val = calcMaintainablilityIndex();
        log(ast.getLineNo(), "Maintainability index: " + val);
    }

}
