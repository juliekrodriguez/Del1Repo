package MScheckTests;
import java.util.Random; 

public class J2Check {
    public static void main(String[] args)
    {
        char chartoint = 6;
        int testint = (int) chartoint;
        int testint1 = (int) chartoint;
        int testint2 = (int) chartoint;
        long testint3 = (long) chartoint;
        double testint4 = (double) chartoint;
        int whileCount = 0;
        int size = 10;
        Random rand = new Random();
        int randm = rand.nextInt(8);
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                randm = rand.nextInt(8);
                if(randm == 0)
                {
                    System.out.print(" Z ");
                }
                if(randm == 1)
                {
                    System.out.print(" A ");
                }
                if(randm == 2)
                {
                    System.out.print(" B ");
                }
                if(randm == 3)
                {
                    System.out.print(" C ");
                }
                if(randm == 4)
                {
                    System.out.print(" D ");
                }
                if(randm == 5)
                {
                    System.out.print(" E ");
                }
                if(randm == 6)
                {
                    System.out.print(" F ");
                }
                if(randm == 7)
                {
                    System.out.print(" G ");
                }
               
            }
            System.out.println("");
        }
        
        while (whileCount < 10)
        {
            System.out.print(whileCount);
            whileCount++;
            for (int i = 0; i < 10; i++)
            {
                
            }
        }


    }

}
