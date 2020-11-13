import java.util.Scanner;
 
public class GCD
{
    // Read a String from the standard input using Scanner
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        int b = in.nextInt();

        int result = gcd(a,b);
        System.out.println(result);
        in.close();
    }
    public static int gcd(int a, int b){

        if(b == 0){
            return a;
        }
        return gcd(b, a % b);
    }
}