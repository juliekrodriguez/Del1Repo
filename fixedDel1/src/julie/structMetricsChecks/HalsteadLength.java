package julie.structMetricsChecks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class HalsteadLength {
  
  public static final String MSG_KEY = "halsteadLength";
  
  private int hLength = 0;
  
  public void beginTree(DetailAST root) {
      hLength = 0;
    }
  
  public int getHalsteadLength() {
    return hLength;
  }
  
  public static boolean isAcceptableToken(int type) {
    return Utils.contains(getAcceptableTokens(), type);
  }
  
  public static int[] getAcceptableTokens() {
    return Utils.concat(AcceptTokens.getOperands(), AcceptTokens.getOperators());
  }

  public void visitToken(DetailAST ast) {
    if (!isAcceptableToken(ast.getType())) {
      return;
    }
    //just inc counter
    hLength++;
  }
  
}
