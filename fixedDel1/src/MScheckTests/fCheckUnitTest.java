package MScheckTests;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.Assert;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import MScheckstyleCheck.fCheck;

public class fCheckUnitTest {
    public fCheck mock = new fCheck();
    
    int[] expToks = new int[]
            {
                    TokenTypes.ASSIGN, TokenTypes.BAND, TokenTypes.BAND_ASSIGN, 
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
                    TokenTypes.LITERAL_CASE, TokenTypes.LITERAL_CATCH, TokenTypes.QUESTION

            };
    @Test
    public void testgetAcceptableTokens() {     
        Assert.assertArrayEquals(expToks, mock.getAcceptableTokens());
    }

    @Test
    public void testgetRequiredTokens() {
        int [] expectedRequired = new int[] {
                TokenTypes.CTOR_DEF,
                TokenTypes.METHOD_DEF,
                TokenTypes.INSTANCE_INIT,
                TokenTypes.STATIC_INIT,
        };
        Assert.assertArrayEquals(expectedRequired, mock.getRequiredTokens());
    }

    @Test
    public void testgetDefaultTokens() {
        Assert.assertArrayEquals(expToks, mock.getDefaultTokens());
    }
    
    @Test
    public void testBlackBoxOperators() throws IOException, CheckstyleException
    {
        fCheck myCheck;
        myCheck = fCheckBuilder();
        
        //Values to test
        Map<String, Double> results = myCheck.getResults();
        
        //Verify
        assertTrue(results.get("operators") == (double)8);
    }
    
    @Test
    public void testBlackBoxEffort() throws IOException, CheckstyleException
    {
        fCheck myCheck;
        myCheck = fCheckBuilder();
        Map<String, Double> results = myCheck.getResults();
        
        assertTrue(results.get("effort") == 196.64);
    }
    
    @Test
    public void testBlackBoxDifficulty() throws IOException, CheckstyleException
    {
        fCheck myCheck;
        myCheck = fCheckBuilder();
        Map<String, Double> results = myCheck.getResults();
        
        assertTrue(results.get("difficulty") == 3.333);
    }
    
    @Test
    public void testBlackBoxVolume() throws IOException, CheckstyleException
    {
        fCheck myCheck;
        myCheck = fCheckBuilder();
        Map<String, Double> results = myCheck.getResults();
        
        assertTrue(results.get("volume") == 58.99383522161534);
    }

    @Test
    public void testBlackBoxOperands() throws IOException, CheckstyleException
    {
        fCheck myCheck;
        myCheck = fCheckBuilder();
        Map<String, Double> results = myCheck.getResults();
        
        //Verify
        assertTrue(results.get("operands") == 15);
    }

    @Test
    public void testBlackBoxLength() throws IOException, CheckstyleException
    {
        fCheck myCheck;
        myCheck = fCheckBuilder();
        Map<String, Double> results = myCheck.getResults();
        assertTrue(results.get("length") == 23);
    }
    
    public void testBlackBoxVocab() throws IOException, CheckstyleException
    {
        fCheck myCheck;
        myCheck = fCheckBuilder();
        Map<String, Double> results = myCheck.getResults();
        assertTrue(results.get("vocab") == 13);
    }
    private void help(AbstractCheck check, DetailAST ast) {
        while (ast != null ) {
            check.visitToken(ast);
            help(check, ast.getFirstChild());
            ast = ast.getNextSibling();
        }
    }
    private fCheck fCheckBuilder() throws IOException, CheckstyleException {
        String filePath = System.getProperty("user.dir");
        File file = new File(filePath + "/src/MScheckstyleTests/TestProgramOperatorsAndOperands.java");
        FileText fileTxt = new FileText(file, "UTF-8");
        FileContents fileCont = new FileContents(fileTxt);
        
        //Fill AST with file contents
        DetailAST root = JavaParser.parse(fileCont);
        
        //Initialize intended check
        fCheck myCheck = new fCheck();
        
        //Configure
        myCheck.configure(new DefaultConfiguration("Local"));
        myCheck.contextualize(new DefaultContext());
        
        //Run check
        myCheck.beginTree(root);
        help(myCheck, root);
        myCheck.finishTree(root);
        
        return myCheck;
    }








}
