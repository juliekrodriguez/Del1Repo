package julie.structMethod;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

import julie.structMetricsChecks.HalsteadOperands;

public class HalsteadOperandCheck extends AbstractCheck {
  
  private HalsteadOperands ands = new HalsteadOperands();

  @Override
  public int[] getDefaultTokens() {
    return getAcceptableTokens();
  }

  @Override
  public int[] getAcceptableTokens() {
    return HalsteadOperands.getAcceptableTokens();
  }

  @Override
  public int[] getRequiredTokens() {
    return new int[0];
  }
    
  @Override
  public void visitToken(DetailAST ast) {
    ands.visitToken(ast);
  }
  
  @Override
  public void beginTree(DetailAST root) {
    ands.beginTree(root);
  }
  
  @Override
  public void finishTree(DetailAST ast) {
    int key = ands.getHalsteadOperands();
    log(ast.getLineNo(), "Halstead operands: " + key);
  }
}
