import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Integrator {
	public Integrator() {
		
	}
	
	public static double integrate(String equation, double a, double b) {
		double sum = 0;
		int splits = 30000;
		double width = (b-a)/splits;
		equation.replaceAll("\\s+","");
		String[] strArray = new String[equation.length()]; 
		for (int i = 0; i < strArray.length; i++) {
			strArray[i] = equation.charAt(i)+"";
		}
		Queue<String> infix = toRPN(strArray);
		for (int i = 1; i <= splits; i ++) {
			sum += width * evaluate(infix, a+width*i);
		}
		
		return sum;
	}
	
	private static double evaluate(Queue<String> infix, double d) {
		Queue<String> rpn = new LinkedList<>(infix);
		
		Stack<String> numbers = new Stack<String>();
		Stack<String> operators = new Stack<String>();
		while(!rpn.isEmpty()) {
			double num1;
			double num2;
			String s = rpn.poll();
			while (!isOperator(s)) {
				numbers.add(s);
				s = rpn.poll();
			}
			operators.add(s);
			String operator = operators.pop();
			
			num1 = getValue(numbers.pop(),d);
			num2 = getValue(numbers.pop(),d);
			double num3;
			if(operator.equals("+")) {
				num3 = num1+num2;
			} else if (operator.equals("-")) {
				num3 = num1-num2;
			} else if (operator.equals("*")) {
				num3 = num1*num2;
			} else if (operator.equals("^")) {
				num3 = Math.pow(num1, num2);
			} else {
				num3 = num1/num2;
			}
			numbers.add(String.valueOf(num3));
		}
		double value = 0;
		try {
			value = Double.valueOf(numbers.peek());
		}catch (Exception e) {
			
		}
		
		return value;
	}
	
	private static double getValue(String poll, double d) {
		if (poll.equals("X")  || poll.equals("x"))
			return d;
		return Double.valueOf(poll);
	}

	private static boolean isOperator(String s) {
		if (s.equals("*") || s.equals("/") || s.equals("+") || s.equals("-") || s.equals("^")) {
			return true;
		}
		return false;
	}

	static boolean isNumber(String str) {
        try{
            Double.valueOf(str);
            return true;
        } catch(Exception e){
        	if(str==null)
        		return false;
        	if(str != null && str.equals("X") || str.equals("x"))
        		return true;
            return false;
        }
    }
	
	public static Queue<String> toRPN(String[] infixNotation) {
        Map<String, Integer> prededence = new HashMap<>();
        prededence.put("^", 6);
        prededence.put("/", 5);
        prededence.put("*", 5);
        prededence.put("+", 4);
        prededence.put("-", 4);
        prededence.put("(", 0);
        
        Queue<String> Q = new LinkedList<>();
        Stack<String> S = new Stack<>();
        
        for (int i = 0; i < infixNotation.length; i++) {
        	String token = infixNotation[i];
            if ("(".equals(token)) {
                S.push(token);
                continue;
            }
            if (")".equals(token)) {
                while (!"(".equals(S.peek())) {
                    Q.add(S.pop());
                }
                S.pop();
                continue;
            }
            // an operator
            if (prededence.containsKey(token)) {
                while (!S.empty() && prededence.get(token) <= prededence.get(S.peek())) {
                    Q.add(S.pop());
                }
                S.push(token);
                continue;
            }
            if (isNumber(token)) {
                Q.add(token);
                continue;
            }
        }
        // at the end, pop all the elements in S to Q
        while (!S.isEmpty()) {
            Q.add(S.pop());
        }
        return Q;
    }
}
