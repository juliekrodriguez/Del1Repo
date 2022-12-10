package MScheckstyleCheck;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;

public class CommentCheck extends AbstractCheck{
    int tc = 0;
    int cl = 0;
    private static final String CATCH_MSH = "Total number of comments: ";
    private static final int[] COMMENT_TOKENS = { TokenTypes.BLOCK_COMMENT_BEGIN,TokenTypes.BLOCK_COMMENT_END, TokenTypes.COMMENT_CONTENT,TokenTypes.SINGLE_LINE_COMMENT};
    
    public boolean isCommentNodeReq()
    {
        return true;
    }
    @Override
    public int[] getDefaultTokens() {
        // TODO Auto-generated method stub
        return COMMENT_TOKENS;
    }

    @Override
    public int[] getAcceptableTokens() {
        // TODO Auto-generated method stub
        return COMMENT_TOKENS;
    }

    @Override
    public int[] getRequiredTokens() 
    {
        // TODO Auto-generated method stub
        return new int[0];
    }
    public void visitTok(DetailAST ast)
    {
        visitTokLog(ast);
    }
    private void visitTokLog(DetailAST ast) {
        // TODO Auto-generated method stub
        switch(ast.getType())
        {
        case TokenTypes.BLOCK_COMMENT_BEGIN:
            addToBlockCom(ast,true);
            break;
        case TokenTypes.SINGLE_LINE_COMMENT:
            addToBlockCom(ast, false);
            break;
        }
        
    }
    public void addToBlockCom(DetailAST ast, boolean isBlock)
    {
        if(isBlock)
        {
            tc += 1;
            DetailAST end = ast.findFirstToken(TokenTypes.BLOCK_COMMENT_END);
            cl += (end.getLineNo() - ast.getLineNo()) + 1;
        }
        else
        {
            addSingleCom();
        }
    }
    public void addSingleCom()
    {
        tc += 1;
        cl += 1;
    }
    public void finishTree(DetailAST ast)
    {
        if(tc >= 1)
        {
            log(0, "total comments ", tc);
        }
        log(0, "comment lines ", cl);
        tc = 0;
        cl = 0;
    }
}
