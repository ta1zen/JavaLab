package lab0;

public class variant16 {

    /** Дано масив A розміру  N . Вивести його елементи в наступному порядку:
    A1, AN, A2, AN–1, A3,AN –2,… . */
    public int[] sortArrayTask(int[] array) {
        int n = array.length;
        int[] result = new int[n];
        for (int i = 0; i < n / 2; i++) {
            result[2 * i] = array[i];
            result[2 * i + 1] = array[n - i - 1];
        }
        if (n % 2 != 0) {
            result[n - 1] = array[n / 2];
        }
        return result;
    }
    /** Дано ціле позитивне число. Перевірити істинність висловлювання: «Це число є парним двозначним».*/
    public boolean isEvenAndTwoDigit(int number) {
        return number >= 10 && number <= 99 && number % 2 == 0;
    }

    /**Дано ціле число в діапазоні 20-69, що визначає вік (в роках). Вивести рядок-опис зазначеного віку, забезпечивши правильне узгодження числа зі словом "рік",
     * наприклад: 20; «двадцять років», 32 — "Тридцять два роки", 41; «сорок один рік». */
    public String convertAgeToString(int age) {
        if (age < 20 || age > 69) {
            throw new IllegalArgumentException("Age must be between 20 and 69");
        }

        // Масиви назв чисел
        String[] units = {"", "один", "два", "три", "чотири", "п’ять", "шість", "сім", "вісім", "дев’ять"};
        String[] tens = {"", "десять", "двадцять", "тридцять", "сорок", "п’ятдесят", "шістдесят"};

        // Логіка для створення рядка з віку
        int ten = age / 10;
        int unit = age % 10;

        String ageInWords = (unit == 0) ? tens[ten] : tens[ten] + " " + units[unit];

        // Визначення правильної форми слова "рік"
        String yearWord;
        if (unit == 1) {
            yearWord = "рік";
        } else if (unit >= 2 && unit <= 4) {
            yearWord = "роки";
        } else {
            yearWord = "років";
        }

        return ageInWords + " " + yearWord;
    }

    /**Дано речовинне число A і ціле число N (> 0).
     * Використовуючи один цикл, виведіть усі цілі ступені числа A від 1 до N. */
    public double[] getPowers(double A, int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }

        double[] powers = new double[N];
        double result = 1;

        for (int i = 0; i < N; i++) {
            result *= A;
            powers[i] = result;
        }

        return powers;
    }

    /**Дано три змінні речовинного типу: A, B, C. Якщо їх значення впорядковані по зростанню, то подвоїти їх;
     * в іншому випадку замінити значення кожної змінної на протилежне. Вивести нові значення змінних A, B, C. */
    public double[] processVariables(double A, double B, double C) {
        double[] result = new double[3];

        if (A <= B && B <= C) {
            // Якщо змінні впорядковані за зростанням
            result[0] = A * 2;
            result[1] = B * 2;
            result[2] = C * 2;
        } else {
            // В іншому випадку
            result[0] = -A;
            result[1] = -B;
            result[2] = -C;
        }

        return result;
    }

    /**Дано тризначне число. Вивести число, отримане при перестановці цифр десятків і одиниць вихідного
     * числа (наприклад, 123 перейде в 132). */
    public int swapTensAndUnits(int number) {
        if (number < 100 || number > 999) {
            throw new IllegalArgumentException("Number must be a positive three-digit integer.");
        }

        int hundreds = number / 100;
        int tens = (number / 10) % 10;
        int units = number % 10;

        return hundreds * 100 + units * 10 + tens;
    }

    /** Дана квадратна матриця A порядку M (M — непарне число). Починаючи з елемента 1,1 1,1 і переміщуючись
     *  проти годинникової стрілки, вивести всі її елементи по спіралі: перший стовпець, остання рядок, останній
     *  стовпець в зворотному порядку, перший рядок в зворотному порядку, елементи другого стовпця, що залишилися, і т.д.;
     *  останнім виводиться центральний елемент матриці. */

    public int[] getSpiralOrder(int[][] matrix) {
        int m = matrix.length;

        if (m % 2 == 0) {
            throw new IllegalArgumentException("Matrix must have an odd order");
        }

        for (int[] row : matrix) {
            if (row.length != m) {
                throw new IllegalArgumentException("Matrix must be square");
            }
        }

        int[] result = new int[m * m];
        int index = 0;
        int top = 0, bottom = m - 1, left = 0, right = m - 1;

        while (top <= bottom && left <= right) {
            // Перший стовпець зверху вниз
            for (int i = top; i <= bottom; i++) {
                result[index++] = matrix[i][left];
            }
            left++;

            // Останній рядок зліва направо
            for (int i = left; i <= right; i++) {
                result[index++] = matrix[bottom][i];
            }
            bottom--;

            // Останній стовпець знизу вгору
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result[index++] = matrix[i][right];
                }
                right--;
            }

            // Перший рядок справа наліво
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    result[index++] = matrix[top][i];
                }
                top++;
            }
        }

        return result;
    }


    /** Спортсмен-лижник почав тренування, пробігши в перший день 10 км. Кожен наступний день він збільшував
     * довжину пробігу на P відсотків від пробігу попереднього дня (P — речовинне, 0 < < i>P < 50).
     * По даному визначити, після якого дня сумарний пробіг лижника за всі дні перевищить 200 км, і вивести
     * знайдену кількість днів (K) (ціле) і сумарний. пробіг S (речове число).*/
    public double[] calculateDaysAndDistance(double percentageIncrease) {
        if (percentageIncrease <= 0 || percentageIncrease >= 50) {
            throw new IllegalArgumentException("Percentage increase P must be between 0 and 50.");
        }

        double initialDistance = 10.0;
        double totalDistance = 0.0;
        int dayCount = 0;
        double currentDistance = initialDistance;

        while (totalDistance <= 200) {
            dayCount++;
            totalDistance += currentDistance;
            currentDistance *= (1 + percentageIncrease / 100.0);
        }

        return new double[]{dayCount, totalDistance};
    }

}