import java.util.Scanner;
import java.math.BigInteger;

public class Rabin {
    public static void main(String[] args){
        System.out.print("Input p, q and m: "); 
                                                                       
        Scanner in = new Scanner(System.in);
        
        BigInteger p = new BigInteger(in.next());              // example input = 5 11 17
        BigInteger q = new BigInteger(in.next());              // n = 55, p = 5 and q = 11
        BigInteger m = new BigInteger(in.next());

        BigInteger c = ModExpo.modExp(m, BigInteger.valueOf(2), p.multiply(q));
        System.out.println("\nc = m^2 (mod n)\n encrypted message c = " + c);
        in.close();

        // To decrypt the possible square roots of c (mod n) you can use ModSquareRoots

        // find the ciphertext where n = 77 and m = 19.
        // Input: 5 11 29           = 71

        // Then to find the roots of 37 (mod 77), using ModSquareRoots.java.
        // input: java ModSquareRoots 37 7 11           = 24,31,46,53
    }
}