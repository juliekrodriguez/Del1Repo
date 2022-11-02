package julie.structMetricsChecks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import julie.structMetricsChecks.AcceptTokens;

public class HalsteadOperators {
  
  private int counter = 0;
  
  public void beginTree(DetailAST root) {
      counter = 0;
    }
  
  public int getHalsteadOperands() {
    return counter;
  }
  
  public static boolean isAcceptableToken(int type) {
    return Utils.contains(getAcceptableTokens(), type);
  }

  public static int[] getAcceptableTokens() {
    return AcceptTokens.getOperators();
  }

  public void visitToken(DetailAST ast) {
    if (!isAcceptableToken(ast.getType())) {
      return;
    }
    counter++;
  }
}