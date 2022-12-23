import java.util.*;

class Strobogrammatic {    
    char[] sames = { '0', '1', '8' };
    String invertedSixtyNine = "69";
    String invertedNinetySix = "96";
    
    public List<String> findStrobogrammatic(int n) {
        List<String> result = new ArrayList<>();
        if (n <= 0) {
            return result;
        }
        
        if (n == 1) {
            String[] strs = { "0","1","8" };
            result.addAll(Arrays.asList(strs));
            return result;
        }
        
        if (n == 2) {
            String[] strs = { "11","69","88","96" };
            result.addAll(Arrays.asList(strs));
            return result;
        }
        
        List<String> subResult = findStrobogrammatic(n - 1);
        
        int center = subResult.get(0).length() / 2;
        if (subResult.get(0).length() % 2 == 0) {
            //odd: insert 0,1,8 in middle each
            
            for (String sub : subResult) {
                for (char same : sames) {
                    StringBuilder sb = new StringBuilder(sub);
                    sb.insert(center, same);
                    result.add(sb.toString());
                }
            }
        } else {
            //even: duplicate 0/1/8 center + replace center with 69/96
            int count = 0;
            for (String sub : subResult) {
                char centerElement = sub.charAt(center);
                StringBuilder sb = new StringBuilder(sub);
                sb.insert(center, centerElement);
                result.add(sb.toString());
                
                if (centerElement == '1' || centerElement == '8') {
                    String replace = centerElement == '1' ? invertedSixtyNine : invertedNinetySix;
                    StringBuilder sbInverted = new StringBuilder(sub);
                    sbInverted.deleteCharAt(center);
                    sbInverted.insert(center, replace);
                    result.add(sbInverted.toString());
                }
            }
        }
        
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new Strobogrammatic().findStrobogrammatic(14));
    }
}
