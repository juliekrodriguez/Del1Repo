package julie.structMetricsChecks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;


import julie.structMetricsChecks.AcceptTokens;

public class HalsteadOperands {
  
  private int count = 0;
  
  public int getHalsteadOperands() {
    return count;
  }
  
  public static boolean isAcceptableToken(int type) {
    return Utils.contains(getAcceptableTokens(), type);
  }

  public static int[] getAcceptableTokens() {
    return AcceptTokens.getOperands();
  }

  public void visitToken(DetailAST ast) {
    if (!isAcceptableToken(ast.getType())) {
      return;
    }
    count++;
  }

  public void beginTree(DetailAST root) {
	  count = 0;
  }
}