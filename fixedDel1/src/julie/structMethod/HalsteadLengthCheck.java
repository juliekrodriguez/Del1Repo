package julie.structMethod;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

import julie.structMetricsChecks.HalsteadOperands;
import julie.structMetricsChecks.HalsteadOperators;
import julie.structMetricsChecks.Utils;


public class HalsteadLengthCheck extends AbstractCheck {
  
  public static final String MSG_KEY = "halsteadLength";

  private HalsteadOperands ands = new HalsteadOperands();
  private HalsteadOperators ors = new HalsteadOperators();

  @Override
  public int[] getAcceptableTokens() {
    return Utils.concat(
        HalsteadOperands.getAcceptableTokens(), 
        HalsteadOperators.getAcceptableTokens());
  }
  
  @Override
  public int[] getDefaultTokens() {
    return getAcceptableTokens();
  }
  
  @Override
  public int[] getRequiredTokens() {
    return new int[0];
  }
  
  @Override
  public void beginTree(DetailAST root) {
    ands.beginTree(root);
    ors.beginTree(root);
  }

  @Override
  public void visitToken(DetailAST ast) {
    ands.visitToken(ast);
    ors.visitToken(ast);
  }
  
  @Override
  public void finishTree(DetailAST ast) {
    int val = ands.getHalsteadOperands() + ors.getHalsteadOperands();
    log(ast.getLineNo(), "Halstead length: " + val);
  }
  
}
