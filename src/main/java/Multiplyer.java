public class Multiplyer {

/*
123 * 456
=       1368 (3 * 456)
 +      9120 (20 * 456)
 +     45600
=      56088

123 * 56
=  
     3 * 6 =    18
     2 * 6 =   12
     1 * 6 =   6
           =   738

                   0
                 738
     3 * 5 =     15
     2 * 5 =    10
     1 * 5 =    5
                        (=    615..)
           =    6888

                6150
*/

    public String multiply(String num1, String num2) {
        if (num1 == null || num2 == null || num1.length() == 0 || num2.length() == 0 || num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        //  123, 56
        int[] result = new int[num1.length() + num2.length()]; //[0,0,0,1,8]

        for (int index1 = num1.length() - 1; index1 >= 0; index1--) { //2
            int first = Integer.valueOf(num1.charAt(index1) - '0'); //3
            for (int index2 = num2.length() - 1; index2 >= 0; index2--) { //0
                int second = Integer.valueOf(num2.charAt(index2) - '0'); //5
                int resultIndex = num1.length() + num2.length() - 2 - index1 - index2; // 0
                int subresult = first * second + result[resultIndex]; //18
                int carry = subresult / 10; // 1
                result[resultIndex] = subresult % 10; //8
                result[resultIndex + 1] += carry; //1
            }
        }

        StringBuilder output = new StringBuilder();
        for (int index = result.length - 1; index >= 0; index--) {
            int value = result[index];
            if (value == 0 && output.length() == 0) continue;
            output.append(value);
        }

        return output.length() == 0 ? "0" : output.toString();
    }

    public String multiplyWithoutOverflow(String num1, String num2) {
        if (num1 == null || num2 == null || num1.length() == 0 || num2.length() == 0 || num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        
        int index1 = 0;
        int multi = 1;

        String result = "";

        while (index1 < num1.length()) {
            int res = (Integer.parseInt(num2)) * multi * numForIndex(num1, index1, 1);
            if (!result.equals("")) {
                result = add(result, Integer.toString(res));
            } else {
                result = String.valueOf(res);
            }
            index1++; 
            multi *= 10;  
        }
        
        return result;
    }
    
    private int numForIndex(String num, int index, int defaultValue) {
        return index >= num.length() ? defaultValue : num.charAt(num.length() - 1 - index) - '0';
    }

    private String add(String num1, String num2) {
        if (num1 == null || num2 == null || num1.length() == 0 || num2.length() == 0 
            || num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        
        int index = 0;
        int carry = 0;
        StringBuilder result = new StringBuilder();
        while (index < num1.length() || index < num2.length()) {
            int first = numForIndex(num1, index, 0);
            int second = numForIndex(num2, index, 0);
            int value = first + second + carry;
            carry = value / 10;
            int digit = value % 10;
            result.append((char) (digit + '0'));
            index++;
        }
        
        if (carry > 0) {
            result.append((char) (carry + '0'));
        }
        
        return result.reverse().toString();
    }

    public static void main(String[] args) {
        Multiplyer m = new Multiplyer();
        //System.out.println(m.multiply("123", "456"));
        System.out.println(m.multiply("123", "56"));
    }
}

