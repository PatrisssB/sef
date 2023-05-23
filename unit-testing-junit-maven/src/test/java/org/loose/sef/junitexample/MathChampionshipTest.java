package org.loose.sef.junitexample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class MathChampionshipTest
{
    private MathChampionship mathChampionship;

    private void readScoresFromCsv(String fileName)
    {
        List<StudentScore> studentScores = new ArrayList<>();

        Path pathToFile = null;
        try {
            pathToFile = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8))
        {

            String line = br.readLine();

            while (line != null)
            {
                String[] attributes = line.split(",");
                StudentScore student = new StudentScore(attributes[0], Integer.parseInt(attributes[1]));
                studentScores.add(student);
                line = br.readLine();
            }

            mathChampionship.setStudentScores(studentScores);

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    @BeforeEach
    void setUp()
    {
        mathChampionship = new MathChampionship();
        // Assuming max achievable score is 100
        mathChampionship.setMaxAchievableScore(100);
        readScoresFromCsv("student_scores.csv");
    }

    @Test
    @DisplayName("Test if a student with a score higher than 95% of the max achievable score gets a Gold medal")
    void testGetGoldPrize()
    {
        // Act
        Prize prize = mathChampionship.getPrize("John Doe");

        // Assert
        assertEquals(Prize.GOLD, prize);
    }

    @Test
    @DisplayName("Test if a student with a score higher than 90% of the max achievable score gets a Silver medal")
    void testGetSilverPrize()
    {
        // Act
        Prize prize = mathChampionship.getPrize("Jane Doe");

        // Assert
        assertEquals(Prize.SILVER, prize);
    }

    // other tests

    @Test
    @DisplayName("Test if searching for a student that doesn't exist throws a StudentNotFoundException")
    void testNoSuchStudent()
    {
        // Act and Assert
        var math = new MathChampionship();
        assertThrows(StudentNotFoundException.class, () -> {
            math.getPrize("Mr Nobody");
        });
    }


}
