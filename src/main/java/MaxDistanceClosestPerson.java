public class MaxDistanceClosestPerson {
    public int maxDistToClosest(int[] seats) {
        if (seats == null || seats.length == 0) {
            return 0;
        }
        
        int freeFrontSeats = -1;
        int freeEndSeats = 0;
        int maxDist = 0;
        int curFreeSeats = 0;
        
        for (int seat : seats) {
            if (seat == 0) {
                curFreeSeats++;
            } else {
                if (freeFrontSeats == -1) {
                    freeFrontSeats = curFreeSeats;
                    maxDist = Math.max(maxDist, freeFrontSeats);
                }
                int curDist = curFreeSeats % 2 == 0 ? curFreeSeats / 2 : curFreeSeats / 2 + 1;
                maxDist = Math.max(maxDist, curDist);
                curFreeSeats = 0;
            }
        }
        
        freeEndSeats = curFreeSeats;
        maxDist = Math.max(maxDist, freeEndSeats);
        
        return maxDist;
    }
}
