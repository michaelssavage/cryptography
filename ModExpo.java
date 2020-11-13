import java.util.Scanner;

import javax.sound.midi.SysexMessage;

public class ModExpo {

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("a^x (mod n)\nEnter a, x, and n: ");

        // input a, x and n only
        int a = in.nextInt();
        int x = in.nextInt();
        int n = in.nextInt();

        int result = modular(a,x,n);
        System.out.println("The result is: " + result);
        
        in.close();
    }
    public static int modular(int a, int x, int n){

        //binary representation of exponential
        String binaryX = Integer.toBinaryString(x);
        System.out.println("binary representation of " +x + " is: " +binaryX);
        
        //k = length of binary
        int k = binaryX.length();

        int y = 1;
        for(int i=k-1;i==0;i--){
            y = (y*y) % n;
            if(binaryX.charAt(i) == '1'){
                y = (y*a) % n;
            }
        }
        return y;
    }
}