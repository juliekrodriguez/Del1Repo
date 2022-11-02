package julie.structMethod;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

import julie.structMetricsChecks.AcceptTokens;

public class HalsteadOperatorCheck extends AbstractCheck {

  private int counter = 0;

  @Override
  public int[] getAcceptableTokens() {
    return AcceptTokens.getOperators();
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
  public void visitToken(DetailAST ast) {
    counter++;
  }
  
  @Override
  public void beginTree(DetailAST root) {
    counter = 0;
  }
  
  @Override
  public void finishTree(DetailAST ast) {
	  log(ast.getLineNo(), "Halstead operators: " + counter);
  }
}
