import java.math.BigInteger;

public class ModSquareRoots {

    public static final BigInteger three = BigInteger.valueOf(3);
    public static final BigInteger four = BigInteger.valueOf(4);
    public static final BigInteger five = BigInteger.valueOf(5);
    public static final BigInteger eight = BigInteger.valueOf(8);

    public static void main(String[] args)
    {
        // example = 3 59
        // 59 is already a prime number
        if(args.length == 2){
            BigInteger a = new BigInteger(args[0]);
            BigInteger n = new BigInteger(args[1]);

            BigInteger[] roots = roots(a, n);
            System.out.println("The square roots of " + a + "(mod " + n + ") = " 
            + roots[0] + ", " + roots[1]);
            }

        //example 3 11 13 
        // because SQRT(3) mod 143
        if(args.length == 3){
            BigInteger a = new BigInteger(args[0]);
            BigInteger p = new BigInteger(args[1]);
            BigInteger q = new BigInteger(args[2]);

            BigInteger pRoot;
            BigInteger qRoot;

            if(p.mod(four).equals(three)){
                pRoot = mod4(a, p);
            } else {
                pRoot = mod8(a,p);
            }
            if(q.mod(four).equals(three)){
                qRoot = mod4(a, q);
            } else {
                qRoot = mod8(a,q);
            }
                
            System.out.println("+-" + pRoot + "(mod " + p + "), " + "+-" + qRoot + "(mod "+ q + ")"
            +"\n=======================================");

            System.out.println("\nroot 1:");
            BigInteger r1 = squareResults(pRoot, qRoot, p, q);

            System.out.println("\nroot 2:");
            BigInteger r2 =  squareResults(pRoot, qRoot.negate(), p, q);

            System.out.println("\nroot 3:");
            BigInteger r3 =  squareResults(pRoot.negate(), qRoot, p, q);

            System.out.println("\nroot 4:");
            BigInteger r4 =  squareResults(pRoot.negate(), qRoot.negate(), p, q);

            System.out.println("\nThe square roots = " + r1 + ", " + r2 + ", " + r3 + ", " + r4 + "\n");
            }
        }
    public static BigInteger[] roots(BigInteger a, BigInteger p) {
        
        BigInteger power = (p.add(BigInteger.ONE)).divide(four);

        System.out.println("----------------------\n|      |  a   |  y   |\n----------------------");
        BigInteger pos = ModExpo.modExp(a, power, p);
        System.out.println("----------------------\n");
        System.out.println("----------------------\n|      |  a   |  y   |\n----------------------");
        BigInteger neg = ModExpo.modExp(a.negate(), power, p);
        System.out.println("----------------------\n");

        return new BigInteger[] {pos, neg};
    }

    public static BigInteger mod4(BigInteger a, BigInteger p) {
        
        BigInteger power = (p.add(BigInteger.ONE)).divide(four);

        System.out.println("\nFind the result from 3 (mod 4) path"
        +"\n----------------------\n|      |  a   |  y   |\n----------------------");
        BigInteger result = ModExpo.modExp(a, power, p);
        System.out.println("----------------------\n");

        return result;
    }

    public static BigInteger mod8(BigInteger a, BigInteger p) {

        BigInteger power = (p.subtract(BigInteger.ONE)).divide(four);
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

// Example: 4 mod 77