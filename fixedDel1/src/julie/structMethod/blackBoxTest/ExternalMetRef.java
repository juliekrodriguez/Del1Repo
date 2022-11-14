package julie.structMethod.blackBoxTest;

public class ExternalMetRef {
    
    private void external()
    {
        String s;
        Integer i = 0;
        
        i.toString();
       // s = i::toString();
        Integer.decode("1");
      //  s = Integer::decode;
    }
    private void internals()
    {
       // this.external();
       
        
    }

}
