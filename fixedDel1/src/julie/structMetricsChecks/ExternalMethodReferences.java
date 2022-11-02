package julie.structMetricsChecks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;


import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Stack;

public class ExternalMethodReferences {
  
  public static final String MSG_KEY = "externalMethodReferences";
  
  private Stack<String> myStack = new Stack<String>();
  
  private int counter = 0;
  
  public void beginTree(DetailAST root) {
      counter = 0;
    }
  
  public static boolean isAcceptableToken(int type) {
      return Utils.contains(getAcceptableTokens(), type);
    }
  
  public int getExternalRef() 
  {
    return counter;
  }
 
  public static int[] getAcceptableTokens() {
    return new int[] { TokenTypes.CLASS_DEF, 
            TokenTypes.METHOD_CALL, 
            TokenTypes.METHOD_REF };
  }
  
  private String classFind(DetailAST ast) {
      if (ast.getType() != TokenTypes.CLASS_DEF) {
        return null;
      }
      DetailAST node1 = ast.getFirstChild();
      while (node1 != null) {
        if (node1.getType() == TokenTypes.IDENT) {
          return node1.getText();
        }
        node1 = node1.getNextSibling();
      }
      return null;
    }

  public void visitToken(DetailAST ast) {
    int type = ast.getType();
    if (!isAcceptableToken(type)) {
      return;
    }
    if (TokenTypes.CLASS_DEF == ast.getType()) {
        //then just push it
        myStack.push(classFind(ast));
    }
    else if(TokenTypes.METHOD_CALL == ast.getType()) {
      if (ExternalRefCall(ast)) {
        counter++;
      }
    }
    else if(TokenTypes.METHOD_REF == ast.getType()) {
      if (isExternalRefCall(ast)) {
        counter++;
      }
    }
  }
  
  public void leaveToken(DetailAST ast) {
      int type = ast.getType();
      if (!isAcceptableToken(type)) {
        return;
      }
      if (TokenTypes.CLASS_DEF == ast.getType()) {
          myStack.pop();
      }
    }
  
  private boolean ExternalRefCall(DetailAST ast) {
    DetailAST node1 = ast.getFirstChild();
    if (TokenTypes.DOT != node1.getType()) {
      return false;
    }
    else {
      DetailAST node2 = node1.getFirstChild();
      if (TokenTypes.LITERAL_THIS == node2.getType()) {
        return false;
      }
      else if (TokenTypes.IDENT == node2.getType() 
          && myStack.peek().equals(node2.getText())) {
        return false;
      }
    }
    return true;
  }
  
  private boolean isExternalRefCall(DetailAST ast) {
    DetailAST node1 = ast.getFirstChild();
    if (TokenTypes.LITERAL_THIS == node1.getType()) {
      return false;
    }
    else if (TokenTypes.IDENT == node1.getType() 
        && myStack.peek().equals(node1.getText())) {
      return false;
    }
    
    return true;
  }

}
