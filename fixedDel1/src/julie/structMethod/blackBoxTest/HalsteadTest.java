package julie.structMethod.blackBoxTest;

public class HalsteadTest 
{
    public void met1()
    {
        int[] t;
        
        try
        {
            met2();
        }
        catch(NullPointerException e)
        {
            
        }  
        finally 
        {
            
        }
    }
    
    protected void met2()
        throws NullPointerException
        {
            throw new NullPointerException("demo");
        }
    private void met3()
    {
        float a = 0.0f;
        a += 1.0f;
        a -= 1.0f;
        a /= 1.0f;
        a *= 1.0f;
        a %= 1.0f;
        
        if(a!=a)
        {
            a = a;
        }
        else
        {
            a = 0;
        }
        
    }

}
