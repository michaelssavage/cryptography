import java.util.Scanner;
import java.math.BigInteger;

public class ModInv {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter two numbers: ");

        // input a, x and n only
        BigInteger a = new BigInteger(in.next());
        BigInteger b = new BigInteger(in.next());

        BigInteger result = modInv(a,b);
        System.out.println("The modular inverse of ("+a+"^-1) mod "+b+" = " + result);
        
        in.close();
        
    }
    // Extended Euclidean Algorithm where e(x) + n(y) = 1
    private static BigInteger ModularInverse(BigInteger e, BigInteger n) {
		
		// Set up all initial values.                               Example: 5^-1 mod 11
		BigInteger n0 = n;                                          // n0 = 11
		BigInteger t0 = BigInteger.ZERO;                            // t0 = 0
		BigInteger t = BigInteger.ONE;                              // t = 1
		BigInteger q = n.divide(e);                                 // q = 11 // 5 = 2
		BigInteger r = n0.subtract(q.multiply(e));                  // r = 11 - (2 * 5) = 1
		
		while (r.compareTo(BigInteger.ZERO) > 0) {                  // while r > 0
			
			// Simulate the subtraction step in EEA
			BigInteger temp = t0.subtract(q.multiply(t));           // temp = 0 - (2*1) = -2
			
			// Adjust temp if it's negative.
			if (temp.compareTo(BigInteger.ZERO) > 0)
			    temp = temp.mod(n);
			if (temp.compareTo(BigInteger.ZERO) < 0)                // temp < 0
			    temp = n.subtract(temp.negate().mod(n));            // temp = 11 - (2 % 11) = 9
			
			// Update each variable as necessary.
			t0 = t;                                                 // t0 = 1  
			t = temp;                                               // t = 9
			n0 = e;                                                 // n0 = 5
			e = r;                                                  // e = 1
			q = n0.divide(e);                                       // q = 5 / 1 = 5
			r = n0.subtract(q.multiply(e));                         // r = 5 - (5 * 1) = 0
		}
		if (!e.equals(BigInteger.ONE))
			return BigInteger.ONE;
		else                                                        //e == 1
		 	return t.mod(n);                                        //answer = 9 % 11 = 9
    }
    
}
