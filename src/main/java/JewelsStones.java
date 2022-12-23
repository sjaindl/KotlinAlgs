import java.util.*;

class JewelsStones {
    public int numJewelsInStones(String jewels, String stones) {
        if (jewels == null || stones == null || jewels.length() <= 0 || stones.length() <= 0) {
            return 0;
        }
        
        int jewelCount = 0;
        
        Set<Character> jewelTypes = new HashSet<>();
        for (Character jewel : jewels.toCharArray()) {
            jewelTypes.add(jewel);
        }
        
        for (Character stone : stones.toCharArray()) {
            if (jewelTypes.contains(stone)) {
                jewelCount++;
            }
        }
        
        return jewelCount;
    }
}