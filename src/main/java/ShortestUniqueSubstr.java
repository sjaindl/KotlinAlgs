import java.util.*;

// arr = ['x','y','z'], str = "xyyzyzyx"
class ShortestUniqueSubstr {
  static String getShortestUniqueSubstring(char[] arr, String str) {
    if (arr == null || arr.length == 0 || str == null || str.length() == 0 || str.length() < arr.length) {
      return "";
    }
    
    int from = Integer.MIN_VALUE;
    int to = Integer.MAX_VALUE;
    
    HashSet<Character> charSet = new HashSet<>();
    for (Character character : arr) {
      charSet.add(character);
    }
    
    HashMap<Character, Integer> running = new HashMap<>();
    for (int index = 0; index < arr.length; index++) {
      Character character = str.charAt(index);
      if (charSet.contains(character)) {
        createOrIncrCount(character, running);
      }
    }
    
    int startIndex = 0; //0
    int endIndex = arr.length - 1; //2
    //running: [x:1,y:2]
    while (startIndex <= endIndex && endIndex < str.length()) { //&& !((endIndex - startIndex) > arr.length)
      int curDiff = to == Integer.MAX_VALUE ? to : to - from + 1; //3
      if (running.size() == charSet.size()) {
        int newDiff = endIndex - startIndex + 1; //2
        if (newDiff < curDiff) {
          from = startIndex;
          to = endIndex;
        }
        if (newDiff == arr.length) break;
      }
      
      Character startChar = str.charAt(startIndex);
      Integer startCharCount = running.get(startChar);
      if (curDiff > arr.length &&
          (!charSet.contains(startChar) || startCharCount != 1))
      {
        if (charSet.contains(startChar)) {
          deleteOrDecrCount(startChar, running);
        }
        startIndex++;
      } else {
        endIndex++;

        if (endIndex < str.length() && charSet.contains(str.charAt(endIndex))) {
            createOrIncrCount(str.charAt(endIndex), running);
        }
      }
    }
    
    if (from < 0) return "";
    
    return str.substring(from, to + 1);
  }
  
  private static void createOrIncrCount(Character character, HashMap<Character, Integer> map) {
    Integer count = map.getOrDefault(character, 0);
    map.put(character, count + 1);
  }
  
  private static void deleteOrDecrCount(Character character, HashMap<Character, Integer> map) {
    Integer count = map.get(character);
    if (count == null || count <= 0) {
      map.remove(character);
    } else {
      map.put(character, count - 1); 
    }
  }

  public static void main(String[] args) {
    String str1 = ShortestUniqueSubstr.getShortestUniqueSubstring(new char[] { 'A' }, "B");
    String str = ShortestUniqueSubstr.getShortestUniqueSubstring(new char[] { 'x','y','z' }, "xyyzyzyx");
    System.out.println(str1);
    System.out.println(str);
  }

}

//arr = ['x','y','z'],
// str = "xxyzyzyx"
// not nec. in order, count doesn't matter, too
// shortest (>= array.len)
// unique


// HashSet of array chars
// index0 at 0 (start) + index1 at 2(end)
      
// "xxyzyzyx"

// remember opt. from/to indices
// int counter = arr.len.
// HashSet running (of chars already seen)
// -> if char matches && ! in running Hashset, remove from running HashSet. Counter--.
// move i: duplicates or not in array