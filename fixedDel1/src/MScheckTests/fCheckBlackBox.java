package MScheckTests;
import static org.junit.Assert.assertTrue;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;

import MScheckstyleCheck.fCheck;

public class fCheckBlackBox {
    static fCheck first;
    static Map<String,Double> fCheckRes;
    
    @BeforeAll
    static void setUp() throws Exception
    {
        first = fCheckBuilder("/src/MScheckTests/TestProgramOperatorsAndOperands.java",false);
        fCheckRes = first.getResults();
    }
    
    @Test
    void testHalsteadOperands() {
      double N2 = 2;
      double result = fCheckRes.get("operands");
      System.out.println("correct halstead operands:    " + N2);
      System.out.println("calculated halstead operands: " + result);
      System.out.println();
      assertTrue(result == N2);
    }

    @Test
    void testHalsteadOperators() {
      double N1 = 8d;
      double result = fCheckRes.get("operators");
      System.out.println("correct halstead operators:    " + N1);
      System.out.println("calculated halstead operators: " + result);
      System.out.println();
      assertTrue(result == N1);
    }
    
    @Test
    void testHalsteadLength() {
      double len = 23d;
      double result =  fCheckRes.get("length");
      System.out.println("correct halstead length:    " + len);
      System.out.println("calculated halstead length: " + result);
      System.out.println();
      assertTrue(result == len);
    }

    @Test
    void testExternalMethodRefs()  throws Exception {
      fCheck extMethsCheck = fCheckBuilder("/src/MScheckTests/TestProgramOperatorsAndOperands.java", false);
      Map<String, Double> extMethResults = extMethsCheck.getResults();
      double ext = 3d;
      double result = extMethResults.get("external");
      System.out.println("correct external meth-refs:    " + ext);
      System.out.println("calculated external meth-refs: " + result);
      System.out.println();
      assertTrue(result == ext); 
    }
    
    @Test
    void testHalsteadVolume() {
      double vol = 23d * Math.log(13d)/Math.log(2d);
      double result = fCheckRes.get("volume");
      System.out.println("correct volume:    " + vol);
      System.out.println("calculated volume: " + result);
      System.out.println();
      assertTrue(result == vol);
    }

    @Test
    void testHalsteadEffort() {
      double n1 = 4;
      double n2 = 9;
      double N2 = 15;
      double diff = (n1/2d)*(N2/n2);
      double vol = 23d * Math.log(13d)/Math.log(2d);
      double eff = diff * vol;
      double result = fCheckRes.get("effort");
      System.out.println("correct effort:    " + eff);
      System.out.println("calculated effort: " + result);
      System.out.println();
      assertTrue(result == eff);
    }
    
    @Test
    void testHalsteadDifficulty() {
      double n1 = 4;
      double n2 = 9;
      double N2 = 15;
      double diff = (n1/2d)*(N2/n2);
      double result = fCheckRes.get("difficulty");
      System.out.println("correct difficulty:    " + diff);
      System.out.println("calculated difficulty: " + result);
      System.out.println();
      assertTrue(result == diff);
    }
    
    @Test
    void testMaintainabilityIndex() {
      double vol = 23d * Math.log(13d)/Math.log(2d);
      double g = 3d;
      double loc = 34d;
      double cm = 14d/34d;
      double mi = 171d - 5.2d*Math.log(vol)/Math.log(2d)-0.23d*g-16.2d*Math.log(loc)/Math.log(2)+50d*Math.sin(Math.sqrt(2.4d*cm));
      double result = fCheckRes.get("maintainability");
      System.out.println("correct maintainability index:    " + mi);
      System.out.println("calculated maintainability index: " + result);
      System.out.println();
      assertTrue(result == mi);
    }
    @Test
    void testHalsteadVocabulary() {
      double voc = 13d;
      double result = fCheckRes.get("vocab");
      System.out.println("correct maintainability index:    " + voc);
      System.out.println("calculated maintainability index: " + result);
      System.out.println();
      assertTrue(result == voc);
    }

    private static fCheck fCheckBuilder(String path, boolean switcher) throws IOException, CheckstyleException
    {
        String fileP = System.getProperty("user.dir");
        File file = new File(fileP + path);
        FileText fileTxt = new FileText(file, "UTF-8");
        FileContents fileCont = new FileContents(fileTxt);
        DetailAST root = JavaParser.parse(fileCont);
        
        fCheck check = new fCheck();
        check.configure(new DefaultConfiguration("Local"));
        check.contextualize(new DefaultContext());
        
        check.setSwitchBlock(switcher);
        
        check.beginTree(root);
        walkTree(check,root);
        check.finishTree(root);
        return check;
    }
    
    private static void walkTree(AbstractCheck check, DetailAST ast)
    {
        while(ast!=null)
        {
            check.visitToken(ast);
            walkTree(check, ast.getFirstChild());
            check.leaveToken(ast);
            ast = ast.getNextSibling();
        }
    }
    
}
