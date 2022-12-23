    import java.util.*;

    public class NextClosestTime {
        public String nextClosestTime(String time) {
            if (time == null || time.length() != 5) {
                return time;
            }
            
            TimeWrapper timeWrapper = new TimeWrapper(time);
            int[] digits = timeWrapper.sortedDigits();
            int min = digits[0];
            
            Integer nextHigher = findNextHigher(timeWrapper.onesMinute, digits, 9);
            if (nextHigher != null) {
                return buildOutput(time, 4, nextHigher, min);
            }
            
            nextHigher = findNextHigher(timeWrapper.tensMinute, digits, 5);
            if (nextHigher != null) {
                return buildOutput(time, 3, nextHigher, min);
            }
            
            int max = timeWrapper.tensHour < 2 ? 9 : 3;
            nextHigher = findNextHigher(timeWrapper.onesHour, digits, max);
            if (nextHigher != null) {
                return buildOutput(time, 1, nextHigher, min);
            }
            
            max = timeWrapper.onesHour < 4 ? 2 : 1;
            nextHigher = findNextHigher(timeWrapper.tensHour, digits, max);
            if (nextHigher != null) {
                return buildOutput(time, 0, nextHigher, min);
            }
            
            return buildMinOutput(time, digits[0]);
        }
        
        private String buildMinOutput(String time, int minValue) {
            char[] output = time.toCharArray();
            char min = (char) ((char) minValue + '0');

            for (int index = 0; index < time.length(); index++) {
                if (index == 2) continue;
                output[index] = min;
            }
            
            return String.valueOf(output);
        }
        
        private String buildOutput(String time, int index, int newValue, int min) {
            char[] output = time.toCharArray();
            int val = newValue + '0';
            output[index] = (char) val;

            for (int afterIndex = index + 1; afterIndex < time.length(); afterIndex++) {
                if (afterIndex == 2) continue;
                output[afterIndex] = (char) ( (char) min + '0');
            }

            return String.valueOf(output);
        }
        
        private Integer findNextHigher(int cur, int[] digits, int max) {
            for (int index = 1; index < digits.length; index++) {
                if (digits[index] > cur && digits[index] <= max) return digits[index];
            }
            
            return null;
        }
        
        class TimeWrapper {
            int tensHour;
            int onesHour;
            int tensMinute;
            int onesMinute;
            
            TimeWrapper(String time) {
                this.tensHour = time.charAt(0) - '0';
                this.onesHour = time.charAt(1) - '0';
                this.tensMinute = time.charAt(3) - '0';
                this.onesMinute = time.charAt(4) - '0';
            }
            
            int[] sortedDigits() {
                int[] digits = new int[] {
                    tensHour, onesHour, tensMinute, onesMinute
                };
                Arrays.sort(digits);
                return digits;
            }
        }

        public static void main(String[] args) {
            NextClosestTime t = new NextClosestTime();
            System.out.println(t.nextClosestTime("18:00"));
            System.out.println(t.nextClosestTime("14:34"));
            System.out.println(t.nextClosestTime("23:59"));
            System.out.println(t.nextClosestTime("00:01"));
            System.out.println(t.nextClosestTime("12:55"));
            System.out.println(t.nextClosestTime("21:55"));
        }
    }



    //find min digit
    //try from back to front
    /*
        one's minute: any digit > cur last?
        10's minute: any digit > cur 10's min && < 6?
        one's hour: if 10's hour < 2 -> any digit > cur one's hour
                    else any digit > cur one's hour && < 4
        10's hour: any digit > cur ten's hour && < 2 || < 3 if one's hour < 4
        
        else:
        
        19:59 -> 11:11 (concat min)
        
        1. validate input
        2. parse mins/hours
        3. sort mins/h
        4. checkFunction - called 4x
        5. if no res. ret min concat
        
    */
