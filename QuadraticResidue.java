import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

public class QuadraticResidue {

    private static Set<Integer> residues = new HashSet<>();
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Input a number N to find its quadratic residues: ");
        int n = in.nextInt(); //number to test

        ArrayList<Integer> values = new ArrayList<>();
        for(int i = 0; i<n; i++){
            values.add(i);
        }

        for(int j = 1; j<values.size(); j++){
            checkQR((j * j)% n, values);
        }

        System.out.println("x = " + values);
        System.out.println("Quadratic residues = " + residues + "\n");
        in.close();
    }

    private static void checkQR(int val, ArrayList<Integer> values){

        Collections.sort(values);

        int res = Collections.binarySearch(values, val);
        boolean test = res > 0;

        if(test)
            residues.add(val);
    }
    
}
