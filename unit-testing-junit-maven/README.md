# unit-testing-junit

A quick introduction to unit testing with JUnit 5.

## 1. Introduction

JUnit is a simple framework which allows us to write automatically re-runnable unit tests. Having a good, comprehensive
unit tests suite, offering proper overall coverage with respect to various coverage metrics, remains one of the best
software development practices, ensuring better quality and stability for any software product.

### 1.1. JUnit 5 Annotations

Below, you can see the most frequently used annotations supported by JUnit 5. Each of them will be then presented in
further detail.

1. @BeforeEach
2. @BeforeAll
3. @AfterEach
4. @AfterAll
5. @Test
6. @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
7. @Disabled

#### @BeforeEach and @AfterEach

In JUnit 5 we no longer have the conventional methods setUp() and tearDown() from JUnit 3. Instead, there have been
introduced the annotations @BeforeEach and @AfterEach, which fulfill the same purpose. Basically, by using annotation
@BeforeEach on a method, that method will behave like the setUp() method from JUnit 3, being always invoked before the
execution of each unit test, in order to properly set up the test environment. Similarly, a method annotated with
@AfterEach will behave like the tearDown() method from JUnit 3 and will always be invoked after the execution of each
unit test, in order to clean up the test environment.

```java
class TestExample {
    @BeforeEach
    public void setUp() {
        System.out.println("@BeforeEach method will execute before every JUnit 5 test!");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("@AfterEach method will execute after every JUnit 5 test!");
    }
}
```

#### @BeforeAll and @AfterAll

The @BeforeAll and @AfterAll annotations work similarly to the @BeforeEach and @AfterEach annotations, with one
important difference: the methods annotated with @BeforeAll or @AfterAll will be executed only once per test suite (
junit test class). These annotations are specifically meant to do the set up / clean up of test environment at test
suite level (instead of test level, as @BeforeEach and @AfterEach do).

```java
class TestExample {
    @BeforeAll
    public static void setUpClass() {
        System.out.println("@BeforeAll method will be executed before JUnit test for a Class starts");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("@AfterAll method will be executed after JUnit test for a Class Completed");
    }
}
```

#### @Test

The @Test annotation replaces the previous JUnit 3 convention of having the test prefix for each test method. In earlier
JUnit versions it was required for a JUnit test class to extend org.junit.TestCase and for each test method to have its
name starting with the test prefix. However, in JUnit 5, it is only needed to annotate each test method with the @Test
annotation, just like in the below example.

```java
class TestExample {
    @Test
    public void testMethod() {
        assertEquals("test", "test");
    }
}
```

#### @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)

Sometimes we might have long-running tests. In order to put a time limit on test execution, we can use the @Timeout
annotation, which takes an integer value, expressed in milliseconds (the unit can be changed). This parameter can be
very useful, especially when we are calling services which should answer within a predefined time period

```java
class TestExample {
    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void failsIfExecutionTimeExceeds500Milliseconds() {
        // fails if execution time exceeds 500 milliseconds.
    }
}
```

#### Exception Testing

It is very common for us to need to test methods that throw exceptions, and we wish to check that they are actually
throwing the right exception in the right situation. In order to make sure that the code under test does throw the right
exception, we can use assertThrows, which has the expected exception class as parameter (please notice the syntax:
ExceptionClassName.class).

```java
class TestExample {
    @Test
    public void exceptionTesting() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(1, 0));
    }
}
```

#### @Disabled

Sometimes we end up writing unit tests while the actual code is still under development (for example when doing Test
Driven Development). It may happen that we wish to keep the unit tests testing the yet unimplemented features, while
still running the test suite and getting relevant feedback for the already implemented part, and we would prefer an all
green status if the implemented features work flawlessly. In this case, we can annotate the test methods in question
with @Disabled("Not yet implemented"), and their execution will be skipped. JUnit 5 ignores all unit tests annotated
with the @Disabled annotation.

```java
class TestExample {
    @Test
    @Disabled("for demonstration purposes")
    public void skippedTest() {
        // not executed
    }
}
```

### 1.2. JUnit 5 assertions

The JUnit 5 library offers many types of assertions. You can find the most used ones below.

1. assertEquals(expected, actual, "failure - objects not same");
2. assertArrayEquals(expected, actual, "failure - byte arrays not same");
3. assertTrue(true, "success - this is true");
4. assertFalse(false, "failure - should be false");
5. assertNull(null, "should be null");
6. assertNotNull(new Object(), "should not be null");
7. assertSame(aNumber, aNumber, "should be same");
8. assertNotSame(new Object(), new Object(), "should not be same Object");
9. assertThrows(ExceptionClassName.class, () -> {});

It is important to notice that almost all JUnit 5 assertions have the same parameters, in the same, standard order: the
expected value, the actual value and optional failure message (which should have a meaningful content when used in
practice).

For further details, please refer to the JUnit testing links below.

[JUnit Page](https://junit.org/junit5/)

[JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

[Test Driven Development Tutorial](https://www.slideshare.net/som.mukhopadhyay/test-driven-development-and-junit-presentation)

### 1.3. Example

```java
public class Function {
    public int calculate(int a, int b) {
        if (b == 0) {
            return -1;
        }
        if (a > b) {
            return a + b;
        }
        return a / b;
    }
}
```

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionTest {
    private Function function;

    @BeforeEach
    void setup() {
        function = new Function();
    }

    @Test
    void givenBEqualTo0_whenCalculate_thenResultIsMinusOne() {
        int calculate = function.calculate(2, 0);
        assertEquals(-1, calculate);
    }

    @Test
    void givenAGreaterThanB_whenCalculate_thenResultIsSumOfAAndB() {
        int calculate = function.calculate(2, 1);
        assertEquals(3, calculate);
    }

    @Test
    void givenALessThanB_whenCalculate_thenResultIsDivisionOfAAndB() {
        int calculate = function.calculate(1, 2);
        assertEquals(0, calculate);
    }
}
```

## 2. Exercise

We are going to create a `MathChampionship` class that will handle the results of a Mathematics Championship.

The `MathChampionship` class will have a list of `StudentScore` objects, each representing a student's name and the
score they have achieved. Additionally, the `MathChampionship` class will also have a `maxAchievableScore` representing
the maximum score a student can achieve.

The main functionality of the `MathChampionship` class is to determine the prize a student has won based on their score.
The criteria for awarding the prizes are as follows:

* If a student's score is more than 95% of the maxAchievableScore, they get a Gold medal. If no student has achieved
  this performance, the student with the highest score will get a Gold medal.
* If a student's score is more than 90% of the maxAchievableScore, they get a Silver medal. If no student has achieved
  this performance, the student with the second highest score will get a Silver medal.
* If a student's score is more than 85% of the maxAchievableScore, they get a Bronze medal. If no student has achieved
  this performance, the student with the third highest score will get a Bronze medal.
* If a student's score is more than 80% of the maxAchievableScore, they get an honourable mention. If no student has
  achieved this performance, the next five highest scoring students (with scores at least 50% of the maxAchievableScore)
  get honourable mentions.
  The method `getPrize(String studentName)` of the `MathChampionship` class will receive a student's name as input and
  return the prize they have won. If the student is not found in the list, it will throw a `StudentNotFoundException`.

Now, with a clear understanding of the problem, let's get started.

### 2.1. Step 1: Setting up JUnit Jupiter

Before you start testing, you need to include JUnit Jupiter in your project. Depending on the build tool you are using,
you can find the instructions below.
Please download the `maven` branch as a starter for this exercise with Maven, and the `gradle` branch as a starter for
this exercise with Gradle.

For Maven, add the following dependency in your `pom.xml`:

```xml

<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.7.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

For Gradle, add the following dependency in your `build.gradle`:

```groovy
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.7.0'
}
```

### 2.2. Step 2: Create the necessary classes and enums

Create a package `org.loose.sef.junitexample` and add the following classes and enums:

Create the `StudentScore` class:

```java
package org.loose.sef.junitexample;

public class StudentScore {
    private String studentName;
    private int score;

    // constructor, getters and setters -> generated by IDE (Alt + Insert)
}
```

Create the `Prize` enum:

```java
package org.loose.sef.junitexample;

public enum Prize {
    GOLD, SILVER, BRONZE, MENTION
}
```

And here is the skeleton for MathChampionship class:

```java
package org.loose.sef.junitexample;

import java.util.List;

public class MathChampionship {
    private List<StudentScore> studentScores;
    private int maxAchievableScore;

    // constructor, getters and setters -> generated by IDE (Alt + Insert)

    public Prize getPrize(String studentName) {
        // method to be implemented
        throw new UnsupportedOperationException();
    }
}
```

Create the `StudentNotFoundException` class:

```java
package org.loose.sef.junitexample;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
```

### 2.3 Step 3: Start writing the tests

Before we write the actual getPrize method, we're going to write the tests for it first. This is a key part of the TDD
approach - you write your tests before your actual code.

Here's what a basic test class might look like:

```java
package org.loose.sef.junitexample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathChampionshipTest {

    @Test
    void testGetPrize() {
        // Prepare
        MathChampionship mathChampionship = new MathChampionship();
        // fill in students scores

        // Act
        Prize prize = mathChampionship.getPrize("John Doe");

        // Assert
        assertEquals(Prize.GOLD, prize);
    }
}
```

Here are the basic test cases we should consider:

1. `testGetGoldPrize` - A student whose score is more than 95% of the total achievable score should get a Gold medal.
2. `testGetSilverPrize` - A student whose score is more than 90% of the total achievable score should get a Silver
   medal.
3. `testGetBronzePrize` - A student whose score is more than 85% of the total achievable score should get a Bronze
   medal.
4. `testGetMentionPrize` - A student whose score is more than 80% of the total achievable score should get an honourable
   mention.
5. `testGetNoPrize` - A student whose score is less than 80% of the total achievable score should get no prize.
6. `testStudentNotFound` - If a student is not found in the list, the method should throw a StudentNotFoundException.
7. `testGetGoldPrizeHighestScore` - If no student scores more than 95%, the student with the highest score should get a
   Gold medal.
8. `testGetSilverPrizeSecondHighestScore` - If no student scores more than 90%, the student with the second-highest
   score should get a Silver medal.
9. `testGetBronzePrizeThirdHighestScore` - If no student scores more than 85%, the student with the third-highest score
   should get a Bronze medal.
10. `testGetMentionPrizeNextHighestScores` - If no student scores more than 80%, the next five highest scoring
    students (with scores at least 50%) should get an honourable mention.

Now let's implement the first two test cases:

```java
package org.loose.sef.junitexample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MathChampionshipTest {
    private MathChampionship mathChampionship;

    @BeforeEach
    void setUp() {
        mathChampionship = new MathChampionship();
        // Assuming max achievable score is 100
        mathChampionship.setMaxAchievableScore(100);
        // fill in student scores
        mathChampionship.setStudentScores(Arrays.asList(
                new StudentScore("John Doe", 97),
                new StudentScore("Jane Doe", 92)
        ));
    }

    @Test
    @DisplayName("Test if a student with a score higher than 95% of the max achievable score gets a Gold medal")
    void testGetGoldPrize() {
        // Act
        Prize prize = mathChampionship.getPrize("John Doe");

        // Assert
        assertEquals(Prize.GOLD, prize);
    }

    @Test
    @DisplayName("Test if a student with a score higher than 90% of the max achievable score gets a Silver medal")
    void testGetSilverPrize() {
        // Act
        Prize prize = mathChampionship.getPrize("Jane Doe");

        // Assert
        assertEquals(Prize.SILVER, prize);
    }

    // other tests

    @Test
    @DisplayName("Test if searching for a student that doesn't exist throws a StudentNotFoundException")
    void testNoSuchStudent() {
        // Act and Assert
        assertThrows(StudentNotFoundException.class, () -> mathChampionship.getPrize("Mr Nobody"));
    }
}
```

In these tests, we first set up the `MathChampionship` object with some predefined student scores.
Then in the `testGetGoldPrize` test, we check if a student ("John Doe") with a score higher than 95% of the max
achievable score gets a Gold medal.
Similarly, in the `testGetSilverPrize` test, we check if a student ("Jane Doe") with a score higher than 90% of the max
achievable score gets a Silver medal.

Try running the tests now. To do this, press the green play button next to the test class name in the project explorer.

The tests should fail, because we haven't implemented the `getPrize` method yet.

### 2.4 Step 4: Implement the `getPrize` method

After writing your tests, you can now implement the `getPrize` method. Since we have already written the tests, we can
easily validate if our implementation is correct by running the tests.

Here's a basic implementation of the `getPrize` method:

```java
class MathChampionship {
    // ...

    public Prize getPrize(String studentName) {
        StudentScore targetStudent = null;

        for (StudentScore student : studentScores) {
            if (student.name().equals(studentName)) {
                targetStudent = student;
                break;
            }
        }

        if (targetStudent == null) {
            throw new StudentNotFoundException(studentName);
        }

        // find the prize
        if (targetStudent.getScore() > 95) {
            return Prize.GOLD;
        } else if (targetStudent.getScore() > 90) {
            return Prize.SILVER;
        } else if (targetStudent.getScore() > 85) {
            return Prize.BRONZE;
        } else if (targetStudent.getScore() > 80) {
            return Prize.MENTION;
        } else {
            return null;
        }
    }

    //...
}
```

Try running the tests now. To do this, press the green play button next to the test class name in the project explorer.
Do all of them pass?
If not, try to fix your implementation until all the tests pass.

### 2.4.1 Step 4.1: Read Student scores from a file

We can read the student scores from a file. Create a file named `student_scores.csv` in the `src/test/resources` folder.
We will store the data in CSV format.

Here's what the file should look like (add at least 10 students)

```csv
John Doe,97
Luke Skywalker,70
Doctor Who,76
Foo Bar,88
Jill Hill,85
Farmer Brown,79
Captain Kirk,73
Jack Black,82
Princess Leia,67
Jane Doe,92
```

Now we can read the student scores from the file. To read these scores into your test, add the following method to your
test class:

```java
class MathChampionshipTest {
    // other methods
    private void readScoresFromCsv(String fileName) {
        List<StudentScore> studentScores = new ArrayList<>();

        Path pathToFile = Paths.get(getClass().getClassLoader().getResource(fileName).getPath());

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {

            String line = br.readLine();

            while (line != null) {
                String[] attributes = line.split(",");
                StudentScore student = new StudentScore(attributes[0], Integer.parseInt(attributes[1]));
                studentScores.add(student);
                line = br.readLine();
            }

            mathChampionship.setStudentScores(studentScores);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    // other test methods
}
```

Now you can use this method in a test to read the student scores from the CSV file:
You can also remove the lines where we set the student scores in the `setUp` method.
Pay attention that the list of student scores is not sorted, so you might want to adjust the implementation to sort the
list.

```java
class MathChampionshipTest {
    // other methods

    @Test
    @DisplayName("Template for reading student scores from a CSV file")
    @Disabled("This Test is just a template, do not add it to your test suite")
    void testPrizeFromCsv() {
        // Arrange
        readScoresFromCsv("student_scores.csv");

        // Act
        Prize prize = mathChampionship.getPrize("Jane Doe");

        // Assert
        assertEquals(Prize.GOLD, prize);
    }
}
```

### 2.5 Step 5: Run the tests using the build tool

#### Maven

Now that we have written our tests, we can run them using the build tool. This is useful when you want to run your tests
on a continuous integration server, or when you want to run your tests on a different machine.

For Maven, you can run the tests using the following command:

```bash
mvn test
```

If you check the output of the command, you should see that none of the tests have actually run:

```text
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ junit-example ---
[INFO] Surefire report directory: /Users/mario/facultate/fis/2023/unit-tests/unit-testing-junit/target/surefire-reports

-------------------------------------------------------
T E S T S
-------------------------------------------------------

Results :

Tests run: 0, Failures: 0, Errors: 0, Skipped: 0
```

This is because we haven't configured the surefire plugin to run our tests. To do this, we need to add the following
configuration to our `pom.xml`:

```xml

<build>
    <plugins>
        <!-- other plugins -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.1.0</version>
        </plugin>
    </plugins>
</build>
```

Now if you run the tests again, you should see that they are executed:

```text
[INFO] --- maven-surefire-plugin:3.1.0:test (default-test) @ junit-example ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running org.loose.sef.junitexample.MathChampionshipTest
[WARNING] Tests run: 4, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.03 s - in org.loose.sef.junitexample.MathChampionshipTest
[INFO] 
[INFO] Results:
[INFO] 
[WARNING] Tests run: 4, Failures: 0, Errors: 0, Skipped: 1
```

You should also see your test report in the `target/surefire-reports` directory.

#### Gradle

For Gradle, you can run the tests using the following command:

```bash
./gradlew test
```

You should see your test report in the `build/reports/tests/test` directory.
If you check the report, you should see that none of the tests have actually run.
This is because we haven't configured the test task to run our tests with Junit5.
To do this, we need to add the following configuration to our `build.gradle`:

```groovy
test {
    useJUnitPlatform()
}
```

Now if you run the tests again, the report will show that they were executed.


### 2.6 Step 6: Adding JaCoCo (Java Code Coverage) Plugin

JaCoCo is a code coverage library for Java. It provides information about the code coverage of your tests.

For Maven, you can add the Jacoco plugin to your `pom.xml`:

```xml

<build>
    <plugins>
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.10</version>
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-agent</goal>
                    </goals>
                </execution>
                <!-- attached to Maven test phase -->
                <execution>
                    <id>report</id>
                    <phase>test</phase>
                    <goals>
                        <goal>report</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

For Gradle, add the following to your `build.gradle`:

```groovy
plugins {
    id 'jacoco'
}

test {
    useJUnitPlatform()
    finalizedBy jacocoTestReport
}

jacoco {
    toolVersion = "0.8.10"
}

jacocoTestReport {
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir('jacocoHtml')
    }

    dependsOn test
}
```

After running your tests with Jacoco enabled (`mvn test` for Maven, `./gradlew test` for Gradle), a report
will be generated detailing your test coverage. This can help you see how much of your code is covered by your tests and
where you might need to add more tests.

### 2.7 Step 7: Interpreting the Jacoco report

Jacoco generates a detailed report of which parts of your code have been tested. You can view this report by opening
the `index.html` file in the directory specified in your Maven or Gradle configuration (by default, this is
in `target/site/jacoco` for Maven and `build/jacocoHtml` for Gradle).

The report contains information such as the percentage of lines covered, the number of branches covered, and more. You
should aim for high code coverage, but remember that 100% coverage does not necessarily mean your tests are perfect. It
is also important to write meaningful tests that check the logic of your code rather than just aiming for high coverage.

In the context of the `MathChampionship` class, you should ensure that you have tests covering all the possible prize
categories, as well as edge cases such as students with the same score, and students not found in the list.

### 2.8 Step 8: Adding Other tests

Now that you have completed a basic test for the `MathChampionship` class, you can try adding more tests to cover other
classes and methods in your project. You can also try adding more tests for the `MathChampionship` class to cover more
edge cases.

For example:

* If the student with the highest score has 45% of the maxAchievable score, they do not win a gold medal, although they
  should
* If three students have a score of over 95%, and the forth has a score of 75%, they do not win a Silver medal, although
  they should
* ... many other edge cases
