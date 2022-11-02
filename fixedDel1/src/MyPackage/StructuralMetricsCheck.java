package MyPackage;

import java.util.*;


import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/*
 * Category A: from doc sheet
 * Halstead Length is the sum of the total number of operators and operand
 * Halstead Vocabulary is the sum of the number of unique operators and unique 
 * Halstead Volume is the program length (N) times the log2 of the program vocabulary (n)
 * Halstead Difficulty is half of the unique operators multiplied by the total number of 
operands, divided by the number of unique operands [1,2]
 *    Halstead Effort is the difficulty multiplied by the volume. Effort = DV. Effort was 
intended as a suggestion for how long code review might take [1,2]
 */
public class StructuralMetricsCheck extends AbstractCheck {

	private int loops, operators, operands, expressions;
	private Map<Integer, Integer> UniqueOperators= new HashMap<Integer, Integer>();
	private Map<String, Integer> UniqueOperands = new HashMap<String, Integer>();
	private Map<String, Float> mapRes = new HashMap<String, Float>();
  
	@Override
	public int[] getDefaultTokens() 
	{
		// TODO Auto-generated method stub
		return new int[] { 
				TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.METHOD_DEF, TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_DO, TokenTypes.DO_WHILE,
				TokenTypes.LITERAL_WHILE, TokenTypes.ASSIGN, TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR,
				TokenTypes.BOR_ASSIGN, TokenTypes.BSR, TokenTypes.BSR_ASSIGN, TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN, TokenTypes.COLON,
				TokenTypes.COMMA, TokenTypes.DEC, TokenTypes.DIV, TokenTypes.DIV_ASSIGN, TokenTypes.DOT, TokenTypes.EQUAL, TokenTypes.GE,
				TokenTypes.GT, TokenTypes.INC, TokenTypes.INDEX_OP, TokenTypes.LAND, TokenTypes.LE, TokenTypes.LITERAL_INSTANCEOF,
				TokenTypes.LNOT, TokenTypes.LOR, TokenTypes.LT, TokenTypes.MINUS, TokenTypes.MINUS_ASSIGN, TokenTypes.MOD, 
				TokenTypes.MOD_ASSIGN, TokenTypes.NOT_EQUAL, TokenTypes.PLUS, TokenTypes.PLUS_ASSIGN, TokenTypes.POST_DEC, TokenTypes.POST_INC,
				TokenTypes.QUESTION, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.STAR,
				TokenTypes.STAR_ASSIGN, TokenTypes.UNARY_MINUS, TokenTypes.UNARY_PLUS, TokenTypes.EXPR,
				TokenTypes.IDENT, TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT, TokenTypes.NUM_INT, TokenTypes.NUM_LONG,
				TokenTypes.CHAR_LITERAL, TokenTypes.STRING_LITERAL };
	}

	@Override
	public int[] getAcceptableTokens() 
	{
		// TODO Auto-generated method stub
		return getDefaultTokens();
	}
  

	@Override
	public int[] getRequiredTokens() 
	{
		// TODO Auto-generated method stub
		return new int[0];
	}
	
	@Override
	public void beginTree(DetailAST ast)
	{
		loops = 0;
		operands = 0;
		operators = 0;
		expressions = 0;
	}
	
	
	public Map<String, Float> getResults() 
	{ 
	    return mapRes; 
	}
	
	@Override
	public void visitToken(DetailAST ast) 
	{
		int type;
		switch(type = ast.getType()) {
			case TokenTypes.LITERAL_FOR:
			case TokenTypes.LITERAL_WHILE:
			case TokenTypes.LITERAL_DO:
			case TokenTypes.DO_WHILE:
				loops++;
				break; 
			case TokenTypes.ASSIGN:
			case TokenTypes.BAND:
			case TokenTypes.BAND_ASSIGN:
			case TokenTypes.BNOT:
			case TokenTypes.BOR:
			case TokenTypes.BOR_ASSIGN:
			case TokenTypes.BSR:
			case TokenTypes.BSR_ASSIGN:
			case TokenTypes.BXOR:
			case TokenTypes.BXOR_ASSIGN:
			case TokenTypes.COLON:
			case TokenTypes.COMMA:
			case TokenTypes.DEC:
			case TokenTypes.DIV:
			case TokenTypes.DIV_ASSIGN:
			case TokenTypes.DOT:
			case TokenTypes.EQUAL:
			case TokenTypes.GE:
			case TokenTypes.GT:
			case TokenTypes.INC:
			case TokenTypes.INDEX_OP:
			case TokenTypes.LAND:
			case TokenTypes.LE:
			case TokenTypes.LITERAL_INSTANCEOF:
			case TokenTypes.LNOT:
			case TokenTypes.LOR:
			case TokenTypes.LT:
			case TokenTypes.MINUS:
			case TokenTypes.MINUS_ASSIGN:
			case TokenTypes.MOD:
			case TokenTypes.MOD_ASSIGN:
			case TokenTypes.NOT_EQUAL:
			case TokenTypes.PLUS:
			case TokenTypes.PLUS_ASSIGN:
			case TokenTypes.POST_DEC:
			case TokenTypes.POST_INC:
			case TokenTypes.QUESTION:
			case TokenTypes.SL:
			case TokenTypes.SL_ASSIGN:
			case TokenTypes.SR:
			case TokenTypes.SR_ASSIGN:
			case TokenTypes.STAR:
			case TokenTypes.STAR_ASSIGN:
			case TokenTypes.UNARY_MINUS:
			case TokenTypes.UNARY_PLUS:
				operators++;
				
				//Counting ops here
				if(!UniqueOperators.containsKey(type))
					UniqueOperators.put(type, 0);
				else
					UniqueOperators.put(type, (UniqueOperators.get(type) + 1));
				break;
				
			case TokenTypes.EXPR:
				expressions++;				
				break;
				
			case TokenTypes.IDENT:
			case TokenTypes.NUM_DOUBLE:
			case TokenTypes.NUM_FLOAT:
			case TokenTypes.NUM_INT:
			case TokenTypes.NUM_LONG:
			case TokenTypes.CHAR_LITERAL:
			case TokenTypes.STRING_LITERAL:
				
				if((ast.getParent().getType() == TokenTypes.TYPE) && (ast.getType() == TokenTypes.IDENT))
					return;
				
				operands++;
				if(!UniqueOperands.containsKey(ast.getText()))
					UniqueOperands.put(ast.getText(), 1);
				else
					UniqueOperands.put(ast.getText(), (UniqueOperands.get(ast.getText()) + 1));
				break;
		}
	}
	
	@Override
    public void finishTree(DetailAST ast)
    {
        int uOperandsSize, uOperatorsSize;
        
        //avoiding dividing by 0
        if((UniqueOperands.keySet() != null) && (UniqueOperands.keySet().size() < 1))
            uOperandsSize = 1;
        else
            uOperandsSize = UniqueOperands.keySet().size();
        if((UniqueOperators.keySet() != null) && (UniqueOperators.keySet().size() < 1))
            uOperatorsSize = 1;
        else
            uOperatorsSize = UniqueOperators.keySet().size();
        
        //now do all operation on the input -- following eq given in docs
        float halsteadVolume = (float) ((operators + operands) * (Math.log(uOperatorsSize +
                uOperandsSize) / Math.log(2)));
        log(0, ("Halstead Volume: -JRTEST" + halsteadVolume + ", " + this.getClass().getSimpleName()));
        mapRes.put("halsteadVolume", halsteadVolume);
        
        float halsteadVocab = uOperatorsSize + uOperandsSize;
        log(0, ("Halstead Vocabulary:-JRTEST " + halsteadVocab + ", " + this.getClass().getSimpleName()));
        mapRes.put("halsteadVocab", halsteadVocab);
        
        float halsteadDifficulty = ((float) (uOperatorsSize / 2.0) * operands) /
                uOperatorsSize;
        log(0, ("Halstead Difficulty: -JRTEST" + halsteadDifficulty + ", " + this.getClass().getSimpleName()));
        mapRes.put("halsteadDifficulty", halsteadDifficulty);
        
        float halsteadEffort = halsteadVocab * halsteadDifficulty;
        log(0, ("Halstead Effort: -JRTEST" + halsteadEffort));
        mapRes.put("halsteadEffort", halsteadEffort);
        
        log(0, ("Found " + loops + " loops" + ", " + this.getClass().getSimpleName()));
        mapRes.put("loops", (float)loops);
        log(0, ("Found " + expressions + " expressions" + ", " + this.getClass().getSimpleName()));
        mapRes.put("expressions", (float)expressions);
        
        log(0, ("Found " + operators + " operators" + ", " + this.getClass().getSimpleName()));
        mapRes.put("operators", (float)operators);
        log(0, ("Found " + operands + " operands" + ", " + this.getClass().getSimpleName()));
        mapRes.put("operands", (float)operands);
    }
}