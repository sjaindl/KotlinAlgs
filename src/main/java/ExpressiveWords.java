class ExpressiveWords {
    public int expressiveWords(String S, String[] words) {
        if (S == null || S.length() == 0 || words == null || words.length == 0) {
            return 0;
        }
        
        int stretchyCount = 0;
        
        /*
        S = "heeellooo"
        words = ["hello", "hi", "helo"]
        */
        
        for (String word : words) { //hello, heeello
            boolean candidate = false;
            //char lastChar = '\0';
            int sIndex = 0;
            int wordIndex = 0;
            
            while (wordIndex <= word.length() && sIndex <= S.length()) {
                if (wordIndex == word.length()) {
                    if (sIndex != S.length()) break;
                    if (candidate) stretchyCount++;
                    break;
                }
                
                if (sIndex == S.length()) {
                    break;
                }
                
                if (word.charAt(wordIndex) != S.charAt(sIndex)) {
                    break;
                }
                
                //heeello
                int sCount = 1; //3
                char sChar = S.charAt(sIndex); //e
                sIndex++; //2
                while (sIndex < S.length() && S.charAt(sIndex) == sChar) {
                    sCount++;
                    sIndex++;
                }
                
                int wordCount = 1; //3
                char wChar = word.charAt(wordIndex); //e
                wordIndex++; //2
                while (wordIndex < word.length() && word.charAt(wordIndex) == wChar) {
                    wordCount++;
                    wordIndex++;
                }
                
                if (sCount >= 3 && sCount > wordCount) {
                    candidate = true;
                } else if (sCount > wordCount && sCount == 2 || sCount < wordCount) {
                    break; //invalid extension
                }
            }
        }
        
        return stretchyCount;
    }
    
}

/*
  for each word:
    boolean stretchy = false; sIndex = 0
    for char in word:
        continue if chars don't match
        if last char + candidate -> incr. exprCount
        
        -> incr. charCountS if same as prev char (index > 0) for S
        -> incr. charCountW if same as prev char (index > 0) for word
        -> incr. S + word indices
        if charCountS >= 3 && charCountS > charCountW -> candidate
    
    return exprCount;
*/