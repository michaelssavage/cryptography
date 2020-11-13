import java.util.Scanner;

public class MillerRabin{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Input an odd number to test primality: ");
        int n = in.nextInt(); //number to test
        int k = 0;
        while(Math.pow(2, k) < (n-1)){
            k++;
        }
        //m is the remainder number that multiplies 2^k to get n-1
        int m = n % (int)Math.pow(2,k);
        System.out.print("To exit, type any value outside the range.\n\nInput a, 1 < a < n - 1: ");
        int a = in.nextInt();
        while(a <n -1 && 1 < a){
            System.out.println(a +" and " +n +solution(n,k,a,m));
            System.out.print("Next input: ");
            a = in.nextInt();
        }
    }
    public static String solution(int n, int k, int a, int m){
        int b = (int)Math.pow(a,m) % n;
        if(b!= 1 && b != n-1){
            int i = 0;
            while(i<k && b!= n-1){

                b = (b*b) % n;
                if(b==1){
                    return " is Composite. It is a Strong Witness.";
                }
            i++;
            }
            if(b!= n-1){
                return " is Composite. It is a Strong Witness.";
            }
        }
        return " is Prime. It is a Strong liar.";
    }
}