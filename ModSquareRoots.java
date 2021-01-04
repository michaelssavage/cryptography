import java.math.BigInteger;
import java.util.Scanner;

public class ModSquareRoots {

    public static final BigInteger three = BigInteger.valueOf(3);
    public static final BigInteger four = BigInteger.valueOf(4);
    public static final BigInteger five = BigInteger.valueOf(5);
    public static final BigInteger eight = BigInteger.valueOf(8);
    // Example: 4 mod 77 would be 4 11 7
    // Example = 3 59 because 59 is already a prime number and has no prime factors.
    // Example 3 11 13 instead of 3 143 because 143 is not prime but has prime factors.

    public static void main(String[] args) {
        BigInteger power;
        BigInteger root1;
        BigInteger root2;

        Scanner in = new Scanner(System.in);
        System.out.print("Example inputs include '3 59' or '3 11 13'" +
        "\nEnter 'n' to use 1 modulo or 'pq' to use 2 modulo: "); 
        String path = in.next();
        if(path.equals("n")){

            System.out.print("Input a and n: ");
            BigInteger a = new BigInteger(in.next());
            BigInteger n = new BigInteger(in.next());

            System.out.println("Is p = 3 (mod 4) or p = 5 (mod 8) ?");

            if(n.mod(four).equals(three)){
                power = mod4power(n);
                root1 = mod4(a, power, n);
                root2 = mod4(a.negate(), power, n);
                System.out.println("The square roots of " + a + "(mod " + n + ") = " 
                + root1 + ", " + root2);
                }
            else if (n.mod(eight).equals(five)){
                power = mod8power(n);
                root1 = mod4(a, power, n);
                root2 = mod4(a.negate(), power, n);
                System.out.println("The square roots of " + a + "(mod " + n + ") = " 
                + root1 + ", " + root2);
                }
            else { System.out.println("Error: N is not a prime number. Does N have two prime factors instead?"); }
        }
        else if(path.equals("pq")){

            System.out.print("Input a, p, and q: ");
            BigInteger a = new BigInteger(in.next());
            BigInteger p = new BigInteger(in.next());
            BigInteger q = new BigInteger(in.next());

            if(p.mod(four).equals(three)){
                power = mod4power(p);
                root1 = mod4(a, power, p);
            } else {
                power = mod8power(p);
                root1 = mod8(a, power, p);
            }
            if(q.mod(four).equals(three)){
                power = mod4power(q);
                root2 = mod4(a, power, q);
            } else {
                power = mod8power(q);
                root2 = mod8(a, power, q);
            }
                
            System.out.println("+-" + root1 + "(mod " + p + "), " + "+-" + root2 + "(mod "+ q + ")"
            +"\n=======================================");

            System.out.println("\nroot 1:");
            BigInteger r1 = squareResults(root1, root2, p, q);

            System.out.println("\nroot 2:");
            BigInteger r2 =  squareResults(root1, root2.negate(), p, q);

            System.out.println("\nroot 3:");
            BigInteger r3 =  squareResults(root1.negate(), root2, p, q);

            System.out.println("\nroot 4:");
            BigInteger r4 =  squareResults(root1.negate(), root2.negate(), p, q);

            System.out.println("\nThe square roots = " + r1 + ", " + r2 + ", " + r3 + ", " + r4 + "\n");
            }
        else { System.out.println("Error: Input 'n' or 'pq' to continue."); }

        in.close();
    }
    // The power will be (n+1)/4
    public static BigInteger mod4power(BigInteger n){
        return (n.add(BigInteger.ONE)).divide(four); }

    public static BigInteger mod4(BigInteger a, BigInteger power, BigInteger p) {
        
        System.out.println("Modular exponentiation of +-" + a + "^" + power + " mod " + p);
        System.out.println("\nFind the result from 3 (mod 4) path"
        +"\n----------------------\n|      |  a   |  y   |\n----------------------");
        BigInteger result = ModExpo.modExp(a, power, p);
        System.out.println("----------------------\n");

        return result;
    }

    // The power will be (n-1)/4
    public static BigInteger mod8power(BigInteger n){
        return (n.subtract(BigInteger.ONE)).divide(four); }

    public static BigInteger mod8(BigInteger a, BigInteger power, BigInteger p) {

        System.out.println("Find if result is 1 or -1"
        +"\n----------------------\n|      |  a   |  y   |\n----------------------");
        BigInteger result = ModExpo.modExp(a, power, p);
        System.out.println("----------------------\n");

        if(result.equals(BigInteger.ONE)){
            System.out.println("The result = 1 so use the power (p+3)/8");
            power = p.add(three).divide(eight);
            System.out.println("----------------------\n|      |  a   |  y   |\n----------------------");
            result = ModExpo.modExp(a, power, p);
            System.out.println("----------------------\n");

        } else {
            System.out.println("The result = -1 so use the power (p-5)/8");
            power = p.subtract(five).divide(eight);
            System.out.println("----------------------\n|      |  a   |  y   |\n----------------------");
            BigInteger temp = ModExpo.modExp(a.multiply(BigInteger.valueOf(2)), BigInteger.ONE, p);
            BigInteger temp1 = ModExpo.modExp(a.multiply(four), power, p);
            result = ModExpo.modExp(temp.multiply(temp1), BigInteger.ONE, p);

            System.out.println("----------------------\n");
        }
        return result;
    }

    // a = 5, p = 11, b = 9, q = 13
    public static BigInteger squareResults(BigInteger a, BigInteger b, BigInteger p, BigInteger q){

        BigInteger n = BigInteger.ONE;
        BigInteger score = a.subtract(b).mod(p);

        System.out.println("----------------"
        +"\nx = " + a + " (mod " + p + ")"
        +"\nx = " + b + " (mod " + q + ")"
        +"\n" + q + "n+(" + b + ") == " + a + " (mod " + p + ")"
        +"\n" + q + "n = " + a + "-" +b+" (mod " + p + ")"
        +"\nFind n where the result = " + score
        +"\n----------------");

        BigInteger root = a.subtract(b);
        if(root.negate().equals(p) & root.compareTo(BigInteger.ZERO) < 0){
            System.out.println(root + " mod " + p +" so just return " + b);
            return b;
        }

        while(! ((q.multiply(n)).mod(p)).equals(score)){
            System.out.println(n + ". " +q + "*(" + n + ") mod " + p + " = " + q.multiply(n).mod(p));
            n = n.add(BigInteger.ONE);
        }
        System.out.println(n + ". " +q + "*(" + n + ") mod " + p +" = " + q.multiply(n).mod(p));
        System.out.println("Using n = " + n + ", we get (" + q + "*" + n + ") + " + b +" = " +(q.multiply(n)).add(b));

        return q.multiply(n).add(b);
    }
}
