package domain;

import java.util.Map;

public class AntiBombRatingPolicy implements RatingPolicy{
    public double calculateRating(Map<Integer, Long> ratingDistribution){
        long count=0;
        long totalsum=0;
        for(int i=2;i<=4;i++){
            long curCount = ratingDistribution.getOrDefault(i, 0L);
            totalsum+=i*curCount;
            count+=curCount;
        }
        if (count == 0) {
            return 0.0;
        }
        return (double)totalsum/count;
    } 
}
