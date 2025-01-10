import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Visualizer {
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Visualizer");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 1000);

        VisualizerColumns panel = new VisualizerColumns();
        panel.setBackground(Color.BLACK);
        frame.add(panel);
        frame.setVisible(true);

        new Thread(panel::startSorting).start();
    }
}

class VisualizerColumns extends JPanel {
    private int[] rectHeightArray;
    private int rectWidth = 10;
    private int movingIndex = -1;

    public VisualizerColumns() {
        int width = 1000;
        int height = 800;
        int numRects = width / rectWidth;
        rectHeightArray = new int[numRects];
        Random random = new Random();
        int minHeight = 10;
        int maxHeight = height - 10;

        for (int i = 0; i < numRects; i++) {
            rectHeightArray[i] = random.nextInt(maxHeight - minHeight + 1) + minHeight;
        }
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
                g2d.setColor(Color.BLUE); // esle blue
            }
            g2d.fillRect(x, 0, rectWidth, height);
            x += rectWidth;
        }
    }

    public void startSorting() {
        bubbleSort(rectHeightArray);
    }

    public void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                movingIndex = j; // purple the moving one
                SwingUtilities.invokeLater(this::repaint); // ask to repaint for purple

                try {
                    Thread.sleep(5); // slow it down need catch for sleep with java
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j+1] and arr[j]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    SwingUtilities.invokeLater(this::repaint); // Repaint after swapping
                }
            }
        }
        movingIndex = -1; // Reset the moving index after sorting
        SwingUtilities.invokeLater(this::repaint); // Final repaint to clear the highlight
    }
}
