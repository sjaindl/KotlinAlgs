import java.util.*;

public class FindReplaceString {
    public String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
        if (S == null || S.length() == 0 || indexes == null || indexes.length == 0
            || sources == null || sources.length == 0 || targets == null || targets.length == 0
            || sources.length != targets.length) {
            return S;
        }
        
        Wrapper[] wrappers = buildWrappers(indexes, sources, targets);
        StringBuilder sb = new StringBuilder(); //ffffbeee
        int prevIndex = -1;
        
        for (int replaceIndex = wrappers.length - 1; replaceIndex >= 0; replaceIndex--) {
            String source = wrappers[replaceIndex].source; //a
            String target = wrappers[replaceIndex].target; //eee
            int index = wrappers[replaceIndex].index; //0
            
            //Input: S = "abcd", indexes = [0, 2], sources = ["a", "cd"], targets = ["eee", "ffff"]
            //Output: "eeebffff"
            
            //append chars after index in rev. order to sb
            int to = prevIndex == - 1
                ? S.length() - 1 
                : prevIndex - 1; // 1
            
            for (int afterIndex = to; afterIndex > index + source.length() - 1; afterIndex--) {
                sb.append(S.charAt(afterIndex));
            }
            
            // check match
            if(matches(S, index, source)) { //true
                // if replace -> replace
                char[] targetChars = target.toCharArray(); //eee
                for (int targetIndex = targetChars.length - 1; targetIndex >= 0; targetIndex--) {
                    sb.append(targetChars[targetIndex]);
                }
            } else {
                // else append original in rev. order to sb
                for (int sourceIndex = source.length() - 1; sourceIndex >= 0; sourceIndex--) {
                    sb.append(S.charAt(sourceIndex + index));
                }
            }

            prevIndex = index;
        }
        
        // append chars before first index in rev. order to sb
        for (int index = wrappers[0].index - 1; index >= 0; index--) {
            sb.append(S.charAt(index));
        }
        
        return sb.reverse().toString(); //eeebffff
    }
    
    private boolean matches(String S, int index, String source) { //abcd, 0, a
        for (int offset = 0; offset < source.length(); offset++) {
            if (S.charAt(index + offset) != source.charAt(offset)) {
                return false;
            }
        }
        
        return true;
    }
    
    private Wrapper[] buildWrappers(int[] indexes, String[] sources, String[] targets) {
        Wrapper[] wrappers = new Wrapper[indexes.length];
        for (int index = 0; index < indexes.length; index++) {
            Wrapper wrapper = new Wrapper(indexes[index], sources[index], targets[index]);
            wrappers[index] = wrapper;
        }
        
        Arrays.sort(wrappers);
        
        return wrappers;
    }
    
    class Wrapper implements Comparable {
        Integer index;
        String source;
        String target;
        
        Wrapper(Integer index, String source, String target) {
            this.index = index;
            this.source = source;
            this.target = target;
        }
        
        public int compareTo(Object other) {
            if (!(other instanceof Wrapper)) return -1;
            Wrapper oth = (Wrapper) other;
            if (index < oth.index) return -1;
            if (index == oth.index) return 0;
            return 1;
        }
    }
    
    public static void main(String[] args) {
        FindReplaceString frs = new FindReplaceString();
        System.out.println(frs.findReplaceString("vmokgggqzp",
                             new int[] {3,5,1},
                             new String[] {"kg","ggq","mo"},
                             new String[] {"s","so","bfr"}));
    }
}
