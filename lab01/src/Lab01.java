public class Lab01
{
    public static void main(String[] args)
    {
       int x = 5;
       String y = "hello";
       float z = 9.8f;
       
       // Printing the variables 
       System.out.println("x: "+ x + " " + "y: "+ y + " "  + "z: "+ z+ " ");
       // a list (make an array in java
       int [] nums = {3,6,-1,2};
       for(int num: nums)
       {
	 System.out.print(num + ", ");
       }
       System.out.println();
       //call a function
       int numFound = char_count(y, 'l');
       System.out.println("Found : "+ numFound);

       // a counting for loop
       for(int i = 1; i < 11; i++)
       {
	   System.out.print(i +" ");
       }
	System.out.println();
    }

   public static int char_count(String s, char c)
   {
        int count = 0;
        for(int i = 0; i < s.length(); i++)
	{
	 char s2 = s.charAt(i);
	 if( s2 == c)
	 {
	   count++;
         }
	}
	return count;
   }
}
