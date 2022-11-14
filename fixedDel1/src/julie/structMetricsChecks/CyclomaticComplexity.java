package julie.structMetricsChecks;
import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


public class CyclomaticComplexity {
    public static final String MSG_KEY = "cyclomaticcomplexity";
    private boolean switcher = true;
    private int val = 1;
    private int cc = 0;
    private final Deque<Integer> valStack = new ArrayDeque<>();
    
    public int getCC()
    {
        return cc;
    }
    public void beginTree(DetailAST root)
    {
        valStack.clear();
        cc = 0;
        val = 1;
    }
    public static boolean isAcceptableToken(int type)
    {
        return Utils.contains(getAcceptableTokens(), type);
    }
    public static int[] getAcceptableTokens()
    {
        return new int[] { TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF, TokenTypes.INSTANCE_INIT,
                TokenTypes.STATIC_INIT, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_DO, 
                TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_IF, TokenTypes.LITERAL_SWITCH,
                TokenTypes.LITERAL_CASE, TokenTypes.LITERAL_CATCH, TokenTypes.QUESTION,
                TokenTypes.LAND, TokenTypes.LOR };

    }
    public void visitTok(DetailAST ast)
    {
        if(!isAcceptableToken(ast.getType()))
        {
            return;
        }
        //building siwtch here
        switch (ast.getType())
        {
        case TokenTypes.CTOR_DEF:
        case TokenTypes.INSTANCE_INIT:
        case TokenTypes.STATIC_INIT:
        case TokenTypes.METHOD_DEF:
            visitMet();
            break;
        default:
            visitTokH(ast);
        }
    }
    public void leaveTok(DetailAST ast)
    {
        if (!isAcceptableToken(ast.getType()))
        {
            return;
        }
        // modif switch 
        switch (ast.getType())
        {
        case TokenTypes.CTOR_DEF:
        case TokenTypes.INSTANCE_INIT:
        case TokenTypes.STATIC_INIT:
        case TokenTypes.METHOD_DEF:
            leaveMet(ast);
            break;
        default:
            break;
        }
    }
    
    public void visitTokH(DetailAST ast)
    {
        if(switcher)
        {
            if(ast.getType() != TokenTypes.LITERAL_CASE)
            {
                val++;
            }
            else if (ast.getType() != TokenTypes.LITERAL_SWITCH)
            {
                val++;
            }
        }
    }
    private void visitMet()
    {
        pushVal();
    }
    private void leaveMet(DetailAST ast)
    {
        popVal();
    }
    private void popVal()
    {
        cc += val;
        val = valStack.pop();
    }
    private void pushVal()
    {
        valStack.push(val);
        val = 1;
    }

}
