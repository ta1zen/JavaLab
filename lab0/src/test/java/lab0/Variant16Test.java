package lab0;

import lab0.variant16;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class Variant16Test {

    @DataProvider
    public Object[][] arrayProvider() {
        return new Object[][]{
                {new int[]{1, 2, 3, 4, 5}, new int[]{1, 5, 2, 4, 3}},
                {new int[]{10, 20, 30, 40}, new int[]{10, 40, 20, 30}},
                {new int[]{7}, new int[]{7}},
                {new int[]{}, new int[]{}}
        };
    }

    @Test(dataProvider = "arrayProvider")
    public void sortArrayTest(int[] input, int[] expected) {
        assertEquals(new variant16().sortArrayTask(input), expected);
    }
    //////////////

    @DataProvider
    public Object[][] numberProvider() {
        return new Object[][]{
                {12, true},
                {23, false},
                {100, false},
                {8, false},
                {44, true},
                {99, false}
        };
    }

    @Test(dataProvider = "numberProvider")
    public void testIsEvenAndTwoDigit(int number, boolean expected) {
        variant16 checker = new variant16();
        assertEquals(checker.isEvenAndTwoDigit(number), expected);
    }

    ///////////////

    @DataProvider
    public Object[][] ageProvider() {
        return new Object[][]{
                {20, "двадцять років"},
                {32, "тридцять два роки"},
                {41, "сорок один рік"},
                {59, "п’ятдесят дев’ять років"}
        };
    }

    @Test(dataProvider = "ageProvider")
    public void testConvertAgeToString(int age, String expected) {
        variant16 converter = new variant16();
        assertEquals(converter.convertAgeToString(age), expected);
    }

    ///////////

    @DataProvider
    public Object[][] powerProvider() {
        return new Object[][]{
                {2, 5, new double[]{2, 4, 8, 16, 32}},
                {3, 4, new double[]{3, 9, 27, 81}},
                {5, 3, new double[]{5, 25, 125}},
                {10, 2, new double[]{10, 100}}
        };
    }

    @Test(dataProvider = "powerProvider")
    public void testGetPowers(double A, int N, double[] expected) {
        variant16 calculator = new variant16();
        assertEquals(calculator.getPowers(A, N), expected);
    }

    ////////////
    @DataProvider
    public Object[][] variableProvider() {
        return new Object[][]{
                {1.0, 2.0, 3.0, new double[]{2.0, 4.0, 6.0}},
                {3.0, 2.0, 1.0, new double[]{-3.0, -2.0, -1.0}},
                {5.0, 5.0, 5.0, new double[]{10.0, 10.0, 10.0}}
        };
    }

    @Test(dataProvider = "variableProvider")
    public void testProcessVariables(double A, double B, double C, double[] expected) {
        variant16 processor = new variant16();
        assertEquals(processor.processVariables(A, B, C), expected);
    }
    ///////////
    @DataProvider
    public Object[][] numberProvider1() {
        return new Object[][]{
                {123, 132},
                {456, 465},
                {789, 798}
        };
    }

    @Test(dataProvider = "numberProvider1")
    public void testSwapTensAndUnits(int input, int expected) {
        variant16 swapper = new variant16();
        assertEquals(swapper.swapTensAndUnits(input), expected);
    }

    ///////////
    @DataProvider
    public Object[][] matrixProvider() {
        return new Object[][]{
                {
                        new int[][]{
                                {1, 2, 3},
                                {4, 5, 6},
                                {7, 8, 9}
                        },
                        new int[]{1, 4, 7, 8, 9, 6, 3, 2, 5}
                },
                {
                        new int[][]{
                                {1, 2, 3, 4, 5},
                                {6, 7, 8, 9, 10},
                                {11, 12, 13, 14, 15},
                                {16, 17, 18, 19, 20},
                                {21, 22, 23, 24, 25}
                        },
                        new int[]{1, 6, 11, 16, 21, 22, 23, 24, 25, 20, 15, 10, 5, 4, 3, 2, 7, 12, 17,
                        18, 19, 14, 9, 8, 13}
                },
                {
                    new int[][]{
                            {1}
                    },
                    new int[] {1}
                }
        };
    }

    @Test(dataProvider = "matrixProvider")
    public void testGetSpiralOrder(int[][] matrix, int[] expected) {
        variant16 spiralMatrix = new variant16();
        assertEquals(spiralMatrix.getSpiralOrder(matrix), expected);
    }

    //////////////
    @DataProvider
    public Object[][] trainingProvider() {
        return new Object[][]{
                {10.0, 12, 213.84},
                {20.0, 9, 207.98},
                {5.0, 15, 215.78}
        };
    }

    @Test(dataProvider = "trainingProvider")
    public void testCalculateDaysAndDistance(double percentageIncrease, int expectedDays, double expectedDistance) {
        variant16 training = new variant16();
        double[] result = training.calculateDaysAndDistance(percentageIncrease);

        assertEquals(result[0], expectedDays);
        assertEquals(result[1], expectedDistance, 0.01);
    }
}