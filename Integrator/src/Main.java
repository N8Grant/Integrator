import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		double a;
		double b;
		String equation = null;
		// Using Scanner for Getting Input from User 
        Scanner in = new Scanner(System.in); 
        System.out.println("Equation to integrate: ");
        equation = in.nextLine(); 
         
        System.out.println("From: "); 
        a = in.nextDouble(); 
        
        System.out.println("To: "); 
        b = in.nextDouble(); 
        in.close();
        double answer = Integrator.integrate(equation,a,b);
		DecimalFormat df2 = new DecimalFormat("#.##");
		df2.setRoundingMode(RoundingMode.DOWN);
        System.out.println("Integral of " + equation+ " from " + a+" to " + b + " = " + df2.format(answer)); 
	}

}
