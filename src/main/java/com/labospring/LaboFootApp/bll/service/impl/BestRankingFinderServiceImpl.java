package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.BestRankingFinderService;
import com.labospring.LaboFootApp.dl.entities.Ranking;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BestRankingFinderServiceImpl implements BestRankingFinderService {

    /**
     * Finds the best rankings from a list, based on the given number of rankings to retrieve.
     *
     * @param rankings      the list of all rankings
     * @param numberRanking the number of top rankings to retrieve
     * @return the list of top rankings
     * @throws IllegalArgumentException if numberRanking is less than 0
     */
    public List<Ranking> findTopRankings(List<Ranking> rankings, int numberRanking) {
        // If the number of rankings requested is greater than or equal to the list size, return all rankings
        if (numberRanking >= rankings.size()) {
            return rankings;
        }

        // Ensure the number of rankings requested is valid
        if (numberRanking < 0) {
            throw new IllegalArgumentException("The number of teams must be greater than 0.");
        }

        // Group rankings by their positions
        HashMap<Integer, List<Ranking>> rankingsByPosition = groupRankingsByPosition(rankings);

        // Retrieve the best rankings from the grouped positions
        return retrieveBestRankings(numberRanking, rankingsByPosition);
    }

    /**
     * Retrieves the best rankings from a grouped ranking list based on the number of rankings needed.
     *
     * @param numberRanking       the number of top rankings to retrieve
     * @param rankingsByPosition  the map of rankings grouped by position
     * @return the list of best rankings
     */
    private List<Ranking> retrieveBestRankings(int numberRanking, HashMap<Integer, List<Ranking>> rankingsByPosition) {
        List<Ranking> topRankings = new ArrayList<>();
        int position = 1; // Start from the top position

        // Loop through positions and retrieve rankings until the required number is fulfilled
        while (numberRanking > 0) {
            List<Ranking> currentRankings = rankingsByPosition.get(position++);

            // If all rankings in the current position can be added, do so
            if (currentRankings.size() <= numberRanking) {
                topRankings.addAll(currentRankings);
                numberRanking -= currentRankings.size();
            } else {
                // Otherwise, only add the best 'numberRanking' items from the current group
                topRankings.addAll(selectBestRankings(currentRankings, numberRanking));
                numberRanking = 0; // Stop the loop as we have the required number of rankings
            }
        }
        return topRankings;
    }

    /**
     * Selects the best rankings from the current list based on custom sorting logic.
     *
     * @param currentRankings the list of rankings to select from
     * @param n               the number of best rankings to retrieve
     * @return the list of best rankings
     */
    private List<Ranking> selectBestRankings(List<Ranking> currentRankings, int n) {
        return currentRankings.stream()
                .sorted(this::compareRankings) // Sort based on custom logic
                .limit(n)                       // Limit the result to 'n' rankings
                .toList();
    }

    /**
     * Custom comparator to rank teams based on points, goal difference, and goals scored.
     *
     * @param ranking1 the first ranking to compare
     * @param ranking2 the second ranking to compare
     * @return comparison result based on ranking criteria
     */
    private int compareRankings(Ranking ranking1, Ranking ranking2) {
        // Compare by total points first
        if (ranking1.getTotalPoints() != ranking2.getTotalPoints()) {
            return ranking2.getTotalPoints() - ranking1.getTotalPoints();
        }
        // If points are the same, compare by goal difference
        if (ranking1.getGoalsDiff() != ranking2.getGoalsDiff()) {
            return ranking2.getGoalsDiff() - ranking1.getGoalsDiff();
        }
        // If goal difference is the same, compare by goals scored
        if (ranking1.getGoalsFor() != ranking2.getGoalsFor()) {
            return ranking2.getGoalsFor() - ranking1.getGoalsFor();
        }
        // If everything is equal, randomize
        return Math.random() > 0.5 ? 1 : -1;
    }

    /**
     * Groups the rankings by their position.
     *
     * @param rankings the list of rankings to group
     * @return a map where the key is the position and the value is a list of rankings for that position
     */
    private HashMap<Integer, List<Ranking>> groupRankingsByPosition(List<Ranking> rankings) {
        return rankings.stream()
                .collect(Collectors.groupingBy(
                        Ranking::getRankingPosition,  // Group by ranking position
                        HashMap::new,                 // Use HashMap to store groups
                        Collectors.toList()           // Collect as a list
                ));
    }
}

