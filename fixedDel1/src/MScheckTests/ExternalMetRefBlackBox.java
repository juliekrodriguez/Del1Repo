package MScheckTests;

import java.util.function.Consumer;

public class ExternalMetRefBlackBox {
    
    private void external()
    {
        String str;
        Integer a = 0;
        a.toString();
        System.out.println();
        Consumer<String> cons = System.out::println;
        
    }

}
