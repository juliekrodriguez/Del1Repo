package MyPackage;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class JCheck extends AbstractCheck{

    @Override
    public int[] getDefaultTokens() {
        // TODO Auto-generated method stub
        return new int[] {TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN,TokenTypes.VARIABLE_DEF, TokenTypes.TYPECAST, TokenTypes.METHOD_REF};
    }

    @Override
    public int[] getAcceptableTokens() {
        // TODO Auto-generated method stub
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        // TODO Auto-generated method stub
        return new int[0];
    }
    private int cc = 0;
    private int numComLine = 0;
    private int numVar = 0;
    private int numLocalMet = 0;
    private int numCast = 0;
    
    public boolean isCommentNodeReq()
    {
        return true;
    }
    public void visitTok(DetailAST ast)
    {
        switch(ast.getType())
        {
        case TokenTypes.SINGLE_LINE_COMMENT:
            cc++; //comment conter inc
            numComLine++;
            break;
        case TokenTypes.BLOCK_COMMENT_BEGIN:
            cc++;
            DetailAST n = ast.getFirstChild();
            n = n.getNextSibling();
            numComLine += (n.getLineNo() - ast.getLineNo()+1); //end - beg
            break;
        case TokenTypes.TYPECAST:
            numCast++;
            break;
        case TokenTypes.VARIABLE_DEF:
            numVar++;
            break;
        case TokenTypes.METHOD_REF:
            numLocalMet++;
            break;
        }
    }
    @Override
    public void finishTree(DetailAST ast)
    {
        log(cc, "there are "+ cc + " total comments");
        log(numLocalMet, "there are " + numLocalMet + " local method refrences");
        log(numCast, "there are " + numCast+ " cast operations");
        log(numVar, "there are " + numVar+ " variable declarations");
        log(numComLine, "there are "+ numComLine + " lines of comments");
    }
    

}
