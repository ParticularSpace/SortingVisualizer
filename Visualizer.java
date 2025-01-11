import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Arrays;

public class Visualizer {
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Visualizer");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 1000);

        VisualizerColumns panel = new VisualizerColumns(100, 1100, 800); // Default number of columns and window size
        panel.setBackground(Color.BLACK);

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.DARK_GRAY);

        JTextField columnField = new JTextField("100", 5); // Input field for column count
        JButton startButton = new JButton("Start Sorting");
        JLabel timerLabel = new JLabel("Time: 0.0 seconds");
        timerLabel.setForeground(Color.WHITE);

        startButton.addActionListener(e -> {
            int numColumns;
            try {
                numColumns = Integer.parseInt(columnField.getText());
                if ((numColumns <= 1) || (numColumns >= 2001))
                    throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter a valid integer (2 - 1000) for the number of columns.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            startButton.setEnabled(false); // Disable the start button

            frame.remove(panel);
            int frameWidth = 1100;
            int frameHeight = 800;
            VisualizerColumns newPanel = new VisualizerColumns(numColumns, frameWidth, frameHeight);
            newPanel.setBackground(Color.BLACK);
            newPanel.setTimerLabel(timerLabel);
            frame.add(newPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();

            new Thread(() -> {
                newPanel.startSorting();
                SwingUtilities.invokeLater(() -> startButton.setEnabled(true)); // enable the start button after sorting
            }).start();
        });

        controlPanel.add(new JLabel("Columns: "));
        controlPanel.add(columnField);
        controlPanel.add(startButton);
        controlPanel.add(timerLabel);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        panel.setTimerLabel(timerLabel);
    }
}

class VisualizerColumns extends JPanel {
    private int[] rectHeightArray;
    private int rectWidth;
    private int movingIndex = -1;
    private JLabel timerLabel;
    private Timer updateTimer;
    private long startTime;

    public VisualizerColumns(int numRects, int panelWidth, int panelHeight) {
        rectWidth = Math.max(1, panelWidth / numRects);
        rectHeightArray = new int[numRects];
        Random random = new Random();
        int minHeight = 10;
        int maxHeight = panelHeight - 10;

        for (int i = 0; i < numRects; i++) {
            rectHeightArray[i] = random.nextInt(maxHeight - minHeight + 1) + minHeight;
        }

        updateTimer = new Timer(100, e -> {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (timerLabel != null) {
                timerLabel.setText("Time: " + (elapsedTime / 1000.0) + " seconds");
            }
        });
    }

    public void setTimerLabel(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(0, getHeight());
        g2d.scale(1, -1);

        int x = 0;
        for (int i = 0; i < rectHeightArray.length; i++) {
            int height = rectHeightArray[i];
            if (i == movingIndex) { // highlight if moving
                g2d.setColor(Color.MAGENTA);
            } else {
                g2d.setColor(Color.BLUE); // else blue
            }
            g2d.fillRect(x, 0, rectWidth, height);
            x += rectWidth;
        }
    }

    public void startSorting() {
        startTime = System.currentTimeMillis();
        updateTimer.start(); // Start updating the timer

        // <----------------------------------------------------------------- Call your sorting algorithm here

        // selectionSort(rectHeightArray);
        // bubbleSort(rectHeightArray);
        quickSort(rectHeightArray, 0, rectHeightArray.length - 1);
        // radixSort(rectHeightArray);

        // ----------------------------------------------------------------->


        updateTimer.stop(); // Stop updating the timer
        if (timerLabel != null) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            timerLabel.setText("Time: " + (elapsedTime / 1000.0) + " seconds");
        }
    }

    // add new algo here if wanted <---------------------------------------------------------------


    // NOTE: It should take an input array and sort it including the visualizations
    // see below on how to include into your sortin algo
    public void yourSort(int[] arr) {
        // your sort algo here
    }


    // <-----------------------------------------------------------------------------------------------


    public void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                movingIndex = j;
                SwingUtilities.invokeLater(this::repaint);

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }

            // Swap the found minimum element with the first element
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;

            SwingUtilities.invokeLater(this::repaint);
        }
        movingIndex = -1;
        SwingUtilities.invokeLater(this::repaint);
    }

    public void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // movingIndex to identify the one we are moving... well duh lol
                movingIndex = j; // purple the moving one
                SwingUtilities.invokeLater(this::repaint); // ask to repaint for purple

                // sleep requires try catch
                try {
                    Thread.sleep(1); // Introduce a delay for visualization
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // The actual sorting logic
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j+1] and arr[j]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    // mUST repaint after swapping
                    SwingUtilities.invokeLater(this::repaint); // Repaint after swapping
                }
            }
        }
        movingIndex = -1; // Reset the moving index after
        SwingUtilities.invokeLater(this::repaint); // repaint to clear the highlight
    }

    public void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // partitioning index (pi)
            int pi = partition(arr, low, high);

            // Sort elements before and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            movingIndex = j;
            SwingUtilities.invokeLater(this::repaint);

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        movingIndex = -1; // Reset moving index
        SwingUtilities.invokeLater(this::repaint);

        return i + 1;
    }

    public void radixSort(int[] arr) {
        // Find the maximum number to know the number of digits
        int max = Arrays.stream(arr).max().getAsInt();

        // Perform counting sort for every digit (starting from least significant)
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, exp);
        }
    }

    private void countingSort(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n]; // Output array
        int[] count = new int[10]; // Count array for digits (0-9)

        // Count occurrences of each digit
        for (int i = 0; i < n; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }

        // Update count array to store actual positions
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array by placing elements in their correct position
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;

            movingIndex = i; // Highlight the current column being processed
            SwingUtilities.invokeLater(this::repaint);

            // Visualization delay
            try {
                Thread.sleep(1); // Adjust delay as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Copy the sorted elements back into the original array
        System.arraycopy(output, 0, arr, 0, n);

        // Repaint after sorting for the current digit
        SwingUtilities.invokeLater(this::repaint);
    }
}
