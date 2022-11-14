package MyPackage;
import static org.junit.Assert.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class StructMetricsCheckTest {
    int[] expArray =
        {
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
                TokenTypes.CHAR_LITERAL, TokenTypes.STRING_LITERAL

        };
    @Test
    public void TestBegTree() throws Exception
    {
        final StructuralMetricsCheck check = new StructuralMetricsCheck();
        //final DetailAST tester = new DetailAST();
        //tester.set
        
    }
    @Test
    public void TestGetDefToks()
    {
        final StructuralMetricsCheck check = new StructuralMetricsCheck();
        assertArrayEquals("Default req tokens are invalid", expArray, check.getDefaultTokens());
    }
    
    @Test
    public void TestAcceptToks()
    {
        final StructuralMetricsCheck check = new StructuralMetricsCheck();
        final int[] exp = check.getDefaultTokens();
        assertArrayEquals("Default req tokens are invalid", exp, check.getAcceptableTokens());
    }

}
