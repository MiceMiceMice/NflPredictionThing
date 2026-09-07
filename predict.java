import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;

public class predict {
    
    public static void main(String[] args) throws IOException {

        // Get the winner's offensive difference in a list
        List<Double> winnerOffenseDiffs = Files.lines(Paths.get("2025NFLData.csv"))
            .skip(1)
            .map(line -> line.split(","))
            .map(columns -> Double.parseDouble(columns[1].trim()))
            .collect(Collectors.toList());

        System.out.println(winnerOffenseDiffs);
        double averageWinnerOffDiff = findAveragePoints(winnerOffenseDiffs);

        System.out.println(averageWinnerOffDiff);

        //Get the winner's deff difference in the list
        List<Double> winnerDefensiveDiffs = Files.lines(Paths.get("2025NFLData.csv"))
            .skip(1)
            .map(line -> line.split(","))
            .map(columns -> Double.parseDouble(columns[2].trim()))
            .collect(Collectors.toList());
            double averageWinnerDeffDiff = findAveragePoints(winnerDefensiveDiffs );
            System.out.println(averageWinnerDeffDiff);



         // Get the losers's offensive difference in a list
        List<Double> loserOffenseDiffs = Files.lines(Paths.get("HomeTeamLoss.csv"))
            .skip(1)
            .map(line -> line.split(","))
            .map(columns -> Double.parseDouble(columns[1].trim()))
            .collect(Collectors.toList());

        System.out.println(loserOffenseDiffs);
        double averageLoserOffDiff = findAveragePoints(loserOffenseDiffs);

        System.out.println(averageLoserOffDiff);

        //Get the winner's deff difference in the list
        List<Double> loserDefensiveDiffs = Files.lines(Paths.get("HomeTeamLoss.csv"))
            .skip(1)
            .map(line -> line.split(","))
            .map(columns -> Double.parseDouble(columns[2].trim()))
            .collect(Collectors.toList());
            double averageLoserDeffDiff = findAveragePoints(loserDefensiveDiffs);
            System.out.println(averageLoserDeffDiff);

        System.out.println("The Home Team is the " + findWinner(averageWinnerOffDiff, averageWinnerDeffDiff, averageLoserOffDiff, averageLoserDeffDiff));

    
    }



    public static double findAveragePoints(List<Double> diffPoints)
    {
        double sum = 0;
        for(int i=0; i < diffPoints.size() ; i++)
        {
            sum += diffPoints.get(i);
        }
        return sum / diffPoints.size();
    }

    public static String findWinner(double winnerOffesne, double winnerDefense, double loserOffense, double loserDefense)
    {
        //ask ans set variable for stuff
        System.out.println("What is the Home team's ESPN offense power index thing?");
         Scanner keyboard = new Scanner(System.in);
         double homeTeamOffIndex = keyboard.nextDouble();
         keyboard.nextLine();

         System.out.println("What is the Home team's ESPN defense power index thing?");
         double homeTeamDeffIndex = keyboard.nextDouble();
         keyboard.nextLine();

         System.out.println("What is the Away team's ESPN offense power index thing?");
         double awayTeamOffIndex = keyboard.nextDouble();
         keyboard.nextLine();
         
         System.out.println("What is the Away team's ESPN defense power index thing?");
         double awayTeamDeffIndex = keyboard.nextDouble();
         keyboard.nextLine();

         //tryna figure out math

         double winOffenseSquared = (winnerOffesne - (homeTeamOffIndex - awayTeamOffIndex)) * (winnerOffesne - (homeTeamOffIndex - awayTeamOffIndex));
         double winDefenseSquared = (winnerDefense - (homeTeamDeffIndex - awayTeamDeffIndex)) * (winnerDefense - (homeTeamDeffIndex - awayTeamDeffIndex));
         double distanceFromWinnerNum = winOffenseSquared + winDefenseSquared;

         double loseOffenseSquared = (loserOffense - (homeTeamOffIndex - awayTeamOffIndex)) * (loserOffense - (homeTeamOffIndex - awayTeamOffIndex));
         double loseDefenseSquared = (loserDefense - (homeTeamDeffIndex - awayTeamDeffIndex)) * (loserDefense -(homeTeamDeffIndex - awayTeamDeffIndex));
         double distanceFromLoserNum = loseOffenseSquared + loseDefenseSquared;

         System.out.println("Home Team's distance from winning average from data: " + distanceFromWinnerNum);
         System.out.println("Home Team's distance from lose average from data: " + distanceFromLoserNum);
         
         //idk looking at this again makes my head hurt but i get the same numbers as i get from my paper calculations soo m guessing its right
         if (distanceFromWinnerNum < distanceFromLoserNum) {
            return "Winner";
         } else {
            return "Loser";
         }
         

    }
}