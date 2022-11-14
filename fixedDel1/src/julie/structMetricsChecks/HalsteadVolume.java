package julie.structMetricsChecks;

import java.util.ArrayList;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class HalsteadVolume {
    public static final String MSG_KEY = "halsteadvolume";
    
    public ArrayList<String> distTokens = new ArrayList<String>();
    public int sum = 0;
    
    public void beginTree(DetailAST ast)
    {
        sum = 0;
        distTokens.clear();
    }
    public static boolean isAcceptableToken(int Type)
    {
        return Utils.contains(getAcceptableTokens(), Type);
    }
    public static int[] getAcceptableTokens()
    {
        return Utils.concat(AcceptTokens.getOperands(), AcceptTokens.getOperators());
    }
    public void visitTok(DetailAST ast)
    {
        int type = ast.getType();
        if(!isAcceptableToken(type))
        {
            return;
        }
        sum++;
        String txt = ast.getText();
        if(!distTokens.contains(txt))
        {
            distTokens.add(txt);
        }
    }
    public double getHalsVolume()
    {
        return sum*Utils.log(distTokens.size(), 2);
    }

}
