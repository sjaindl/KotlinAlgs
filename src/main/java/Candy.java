import java.util.*;

public class Candy {
        public int candy(int[] ratings) {
            if (ratings == null || ratings.length == 0) return 0;
            if (ratings.length == 1) return 1;
            
            int[] candies = new int[ratings.length];
            for (int index = 0; index < candies.length; index++) {
                if (index == 0 || ratings[index] <= ratings[index - 1]) {
                    candies[index] = 1;
                } else {
                    candies[index] = candies[index - 1] + 1;
                }
            }
            
            System.out.println(Arrays.toString(candies));
            
            int sum = candies[candies.length - 1];
            for (int index = candies.length - 2; index >= 0; index--) {
                if (ratings[index] > ratings[index + 1]) {
                    candies[index] = Math.max(candies[index], candies[index + 1] + 1);
                }
                sum += candies[index];
            }
            
            System.out.println(Arrays.toString(candies));
            
            return sum;
            
            /*
            int count = ratings.length;
            int prev = -1;
            int down = 0;
                
            for (int index = 0; index < ratings.length; index++) {
                int rating = ratings[index];
                if (prev == -1) { prev = 1; if(ratings.length > 1 && rating > ratings[index+1]) {down++;} continue; }
                int prevRating = ratings[index - 1];
                
                if (rating > prevRating) { prev++; count += prev + down; down = 0; continue; }
                else if (rating == prevRating) { prev = 1; count += down; down = 0; continue; }
                
                if (prev == 1) down++;
                prev = 1; 
            }
            
            count += down;
            
            return count;
            */
            
            /*
            int desc = 0;
            int count = 0;
            int prev = 0;
            int cur = 0;
            
            for (int index = 0; index < ratings.length; index++) {
                if (index == 0) {
                    int next = index >= ratings.length ? 0: ratings[index + 1];
                    cur = ratings[index] > next ? 2 : 1;
                    //desc = 1;
                } else {
                    if (ratings[index] > ratings[index - 1]) {
                        cur = prev + 1;
                        desc = 0;
                    } else if (ratings[index] == ratings[index - 1]) {
                        cur = 1;
                        desc = 1;
                    } else {
                        /
                        if (prev > 1) {
                            cur = 1;
                        } else {
                            cur += desc;
                        }
                        *
                        cur = Math.max(1, desc);
                        
                        desc++; //1
                    }
                }
                
                System.out.println(cur);
                
                prev = cur;
                count += cur;
            }
            
            return count; //1,2,3,1
    */
        }
    }
    
    // [1,2,4,3,2,1,0] -> 1,2,4,3,2,1  (greedy - < next? - +1 für desc.line (ct!))
    
    // [1,2,87,87,87,2,1] -> 1231321 (=13)
    
    