package MScheckTests;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

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

import MScheckstyleCheck.loopingCheck;

public class loopingCheckTest {
    
    public loopingCheck unit = new loopingCheck();
    public int[] unitC =  new int[] { TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE };
    
    @Test
    public void testgetAcceptableTokens() {
        Assert.assertArrayEquals(unitC,unit.getAcceptableTokens());
    }
    @Test
    public void testgetDefaultTokens() {
        Assert.assertArrayEquals(unitC,unit.getDefaultTokens());
    }
    @Test
    public void testgetRequiredTokens() {
        int[] unitC = new int[0];
        Assert.assertArrayEquals(unitC,unit.getRequiredTokens());
    }
    @Test
    public void testBlackBoxLoop() throws IOException, CheckstyleException
    {
        loopingCheck check;
        check = firstCheckB();
        
        int count = check.getLoopCount();
        
        assertTrue(count == 4);
    }
    private void help(AbstractCheck check, DetailAST ast)
    {
        while(ast!= null)
        {
            check.visitToken(ast);
            help(check, ast.getFirstChild());
            ast = ast.getNextSibling();
        }
    }
    private loopingCheck firstCheckB() throws IOException, CheckstyleException
    {
        String fileP = System.getProperty("user.dir");
        File file = new File(fileP + "/src/MScheckTests/J2Check.java");
        FileText fileTxt = new FileText(file, "UTF-8");
        FileContents fileCont = new FileContents(fileTxt);
        DetailAST root = JavaParser.parse(fileCont);
        loopingCheck check = new loopingCheck();
        check.configure(new DefaultConfiguration("Local"));
        check.contextualize(new DefaultContext());
        check.beginTree(root);
        help(check, root);
        check.finishTree(root);
        return check;
    }
    

}
