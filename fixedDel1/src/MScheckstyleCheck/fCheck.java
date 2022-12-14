package MScheckstyleCheck;
import com.puppycrawl.tools.checkstyle.api.*;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class fCheck extends AbstractCheck 
{
    int operators=0;
    int operands = 0;
    int internalCalls = 0;
    int externalCalls = 0;
    int expressions;
    int lastline=0;
    private int cyclomaticComp = 1;
    //leaving parameter to default as false.
    private boolean switchBlock=false;
    
    public static final String MSG_KEY = "cyclomaticComplexity";
    
    private Map<String, Double> mapResults = new HashMap<String, Double>();
    
    public void setSwitchBlock(boolean switchBlock)
    {
        this.switchBlock = switchBlock;
    }
    int tokensToVisit[]= new int[] {TokenTypes.ASSIGN, TokenTypes.BAND, TokenTypes.BAND_ASSIGN, 
            TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN, TokenTypes.BSR, TokenTypes.BSR_ASSIGN,
            TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN,TokenTypes.DEC, TokenTypes.DIV, TokenTypes.DIV_ASSIGN,
            TokenTypes.EQUAL, TokenTypes.INC, TokenTypes.LAND, TokenTypes.LE, TokenTypes.LNOT, TokenTypes.LOR,
            TokenTypes.LT, TokenTypes.MINUS, TokenTypes.MINUS_ASSIGN, TokenTypes.MOD, TokenTypes.MOD_ASSIGN,
            TokenTypes.NOT_EQUAL, TokenTypes.PLUS, TokenTypes.PLUS_ASSIGN, TokenTypes.POST_DEC,
            TokenTypes.POST_INC, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.SR, TokenTypes.SR_ASSIGN,
            TokenTypes.STAR, TokenTypes.STAR_ASSIGN, TokenTypes.UNARY_MINUS, TokenTypes.UNARY_PLUS,
            TokenTypes.EXPR, TokenTypes.METHOD_CALL, TokenTypes.RCURLY, TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF, TokenTypes.INSTANCE_INIT, TokenTypes.STATIC_INIT, TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO, TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_IF, TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_CASE, TokenTypes.LITERAL_CATCH, TokenTypes.QUESTION };
    HashSet<Integer> forComplex = new HashSet<>(Arrays.asList(TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT, TokenTypes.STATIC_INIT, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_IF, TokenTypes.LITERAL_SWITCH, TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_CATCH, TokenTypes.QUESTION, TokenTypes.LAND, TokenTypes.LOR));
    HashSet<Integer> isOperator = new HashSet<> (Arrays.asList(TokenTypes.ASSIGN, TokenTypes.BAND, TokenTypes.BAND_ASSIGN, 
            TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN, TokenTypes.BSR, TokenTypes.BSR_ASSIGN,
            TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN,TokenTypes.DEC, TokenTypes.DIV, TokenTypes.DIV_ASSIGN,
            TokenTypes.EQUAL, TokenTypes.INC, TokenTypes.LAND, TokenTypes.LE, TokenTypes.LNOT, TokenTypes.LOR,
            TokenTypes.LT, TokenTypes.MINUS, TokenTypes.MINUS_ASSIGN, TokenTypes.MOD, TokenTypes.MOD_ASSIGN,
            TokenTypes.NOT_EQUAL, TokenTypes.PLUS, TokenTypes.PLUS_ASSIGN, TokenTypes.POST_DEC,
            TokenTypes.POST_INC, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.SR, TokenTypes.SR_ASSIGN,
            TokenTypes.STAR, TokenTypes.STAR_ASSIGN, TokenTypes.UNARY_MINUS, TokenTypes.UNARY_PLUS));
    HashSet<Integer> unaryOper = new HashSet<>(Arrays.asList(TokenTypes.BNOT, TokenTypes.DEC, TokenTypes.INC, TokenTypes.POST_DEC,
            TokenTypes.POST_INC, TokenTypes.UNARY_MINUS, TokenTypes.UNARY_PLUS));
    
    HashSet<String> uniqueOperands = new HashSet<String>();
    HashSet<Integer> uniqueOperators = new HashSet<Integer>();



    @Override
    public int[] getDefaultTokens() {
        // TODO Auto-generated method stub
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        // TODO Auto-generated method stub
        return tokensToVisit;
    }

    @Override
    public int[] getRequiredTokens() {
        // TODO Auto-generated method stub
        return new int[]{TokenTypes.CTOR_DEF,
                TokenTypes.METHOD_DEF,
                TokenTypes.INSTANCE_INIT,
                TokenTypes.STATIC_INIT};

    }
    public String rebuildExpress(DetailAST ast, boolean isTop)
    {
        if(ast.getType() == TokenTypes.LPAREN)
        {
            ast = ast.getNextSibling();
        }
        
        if(ast.getChildCount() == 0) 
        {
            String self = ast.getText();
            uniqueOperands.add(self);
            return self;
        }
        else
        {
            String left = rebuildExpress(ast.getFirstChild(), false);
            String right = rebuildExpress(ast.getLastChild(), false);
            String expression = left + ast.getText() + right;
            if(!isTop) 
            {
                uniqueOperands.add(expression);
            }
            return expression;

        }

    }
    private void visitTokenH(DetailAST ast)
    {
        if(switchBlock)
        {
            if (ast.getType() != TokenTypes.LITERAL_CASE)
            {
                cyclomaticComp++;
            }

        }
        else if (ast.getType() != TokenTypes.LITERAL_SWITCH) 
        {
            cyclomaticComp++;
        }

    }
    public void leaveToken(DetailAST ast)
    {
        if(forComplex.contains(ast.getType()))
        {
            switch(ast.getType())
            {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                break;
            default:
                break;

            }
        }
    }
    public void visitToken(DetailAST ast)
    {
        if(forComplex.contains(ast.getType()))
        {
            switch(ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                break;
            default:
                visitTokenH(ast);
        }
        if(ast.getType() == TokenTypes.EXPR)
        {
            boolean reachedT = false;
            boolean addExp = true;
            DetailAST parent = ast.getParent();
            while(!reachedT)
            {
                if (parent.getType() == TokenTypes.OBJBLOCK) {
                    reachedT = true;
                }
                if(parent.getType() == TokenTypes.EXPR)
                {
                    addExp = false;
                    reachedT = true;
                }
                parent = parent.getParent();
            }
            if(addExp)
            {
                expressions++;
            }
        }
        else if (isOperator.contains(ast.getType()))
        {
            operators++;
            operands += 2;
            if(unaryOper.contains(ast.getType()))
            {
                operands--;
            }
            uniqueOperators.add((Integer)ast.getType());
            String parent = "";
            parent = ast.getParent().getText();
            switch(ast.getText())
            {
            case "=":
                if(parent.equals("VARIABLE_DEF"))
                {
                    parent = ast.getParent().findFirstToken(TokenTypes.IDENT).getText();
                    uniqueOperands.add(parent);
                }
                else if (parent.equals("EXPR"))
                {
                    rebuildExpress(ast, true);
                }
                break;
            default:
                rebuildExpress(ast, true);
                break;
            }
        }
        else if( ast.getType() == TokenTypes.METHOD_CALL)
        {
            if(ast.getChildCount(TokenTypes.DOT)>0) 
            {
                //is a .method call
                externalCalls++;
            }
            else {
                internalCalls++;
            }
        }
        else if(ast.getType() == TokenTypes.RCURLY) 
        {
            if(ast.getLineNo() > lastline) 
            {
                lastline = ast.getLineNo();
            }
        }
        }
    }
    
    public void finishTree(DetailAST ast)
    {
        int vocab = 0;
        double vol = 0;
        double perc = 0.5;
        int length = operators + operands;
        
        mapResults.put("length",(double)length);
        
        //logging met
        log(ast.getLineNo(),"operators", operators);
        log(0,"length", length);
        log(0, "1", uniqueOperators.size());
        log(0,"0",uniqueOperands.size());
        
        
        //need to write tests here
        mapResults.put("length", (double)length);
        double maintain = 171 - 5.2*Math.log(vol)/Math.log(2)-0.23*cyclomaticComp-16.2*Math.log(lastline)/Math.log(2)+50*Math.sin(Math.sqrt(2.4*perc));
        //logg
        log(ast.getLineNo(),"operators", operators);
        log(0,"0",uniqueOperands.size());
        log(0,"1", uniqueOperators.size());
        log(0, "3", operands);
        log(0,"4",expressions);
        log(0, "length", length);
        if(uniqueOperators.size() != 0 && uniqueOperands.size() != 0)
        {
            vocab = uniqueOperators.size() + uniqueOperands.size();
            log(0, "vocab", vocab);
            vol = length * Math.log(vocab);
            log(0, "volume",vol);
            mapResults.put("vocab", (double)vocab);
            mapResults.put("volume", vol);
        }
        if(uniqueOperands.size() != 0)
        {
            double diff = uniqueOperators.size()*operands/(uniqueOperands.size()*2);
            double effort = diff *vol;
            
            mapResults.put("difficulty", diff);
            mapResults.put("effort", effort);
        }
        mapResults.put("operands", (double)operands);
        mapResults.put("operators", (double)operators);
        mapResults.put("uniqueOperators", (double)uniqueOperators.size());
        mapResults.put("uniqueOperands", (double)uniqueOperands.size());
        
        log(0, "complex", cyclomaticComp);
        log(0, "internasls", internalCalls);
        log(0, "external", externalCalls);
        log(0, "lines", lastline);
        log(0, "maintain", maintain);
        
        mapResults.put("maintainability", (double)maintain);
        mapResults.put("external", (double)externalCalls);
        
        //finish clear
        operands = 0;
        operators = 0;
        cyclomaticComp = 1;
        externalCalls = 0;
        expressions = 0;
        internalCalls = 0;
        uniqueOperators = new HashSet<>();
        uniqueOperands = new HashSet<>();
    }

    public Map<String, Double> getResults() {
        // TODO Auto-generated method stub
        return mapResults;
    }

}
