# Sorting Algorithm Visualizer

## Overview
This project is a graphical sorting algorithm visualizer implemented in Java using Swing. It provides a visual demonstration of sorting algorithms working on an array of integers represented as vertical bars of varying heights.

### Features
- **Real-Time**: Highlights the currently compared or swapped elements during sorting.
- **Column Adjustment**: Allows users to customize the number of columns to sort (between 2 and 1000).
- **Elapsed Time Display**: Shows the time taken to sort in seconds.
- **Sorting Algorithms**: Currently supports:
  - Quick Sort (default, optimized for large datasets).
  - Bubble Sort (commented out for simplicity).
  - Selection Sort (commented out for simplicity).

---

## How to Run
1. **Compile**:
   - Ensure you have Java installed.
   - Compile the code with `javac Visualizer.java`.
2. **Run**:
   - Execute the compiled code with `java Visualizer`.

---

## How to Use
1. **Start the Application**:
   - Launch the program to open the GUI.
2. **Set Number of Columns**:
   - Enter a number between 2 and 1000 in the input field.
   - Press the **"Start Sorting"** button to begin.
3. **Observe**:
   - Watch the sorting process visualized with moving bars.
   - The highlighted magenta bar indicates the currently processed element.
4. **Check Time**:
   - The elapsed time for sorting is displayed at the bottom in real-time.

---

## Code Structure
### **Visualizer Class**
- **`main` Method**:
  - Sets up the JFrame and initializes the UI components.
  - Dynamically creates a new visualizer panel based on user input.

### **VisualizerColumns Class**
- Handles the visualization and sorting logic.
- **Key Methods**:
  - **`startSorting`**: Starts the sorting process and tracks elapsed time.
  - **`quickSort`**: Implements the Quick Sort algorithm.
  - **`partition`**: Partitions the array for Quick Sort.
  - **`paintComponent`**: Draws the bars and highlights the current element being processed.

---

## Algorithms
### Quick Sort (Default)
- **Time Complexity**:
  - Best/Average Case: O(n log n)
  - Worst Case: O(n²)
- Efficient for large datasets.
- Uses a pivot element to partition the array into smaller and larger subarrays.

### Bubble Sort (Commented Out)
- **Time Complexity**: O(n²)
- Simple but inefficient for large datasets.

### Selection Sort (Commented Out)
- **Time Complexity**: O(n²)
- Selects the smallest element and swaps it with the first unsorted element.

---

## Customization
1. **Change Algorithm**:
   - Uncomment `bubbleSort` or `selectionSort` in `startSorting()` and comment out `quickSort` to use a different algorithm.
2. **Adjust Visualization Delay**:
   - Modify `Thread.sleep()` in the sorting methods to speed up or slow down the visualization.

---

## Future Enhancements
- Add more sorting algorithms (e.g., Merge Sort, Heap Sort).
- Improve user interaction with algorithm selection.
- Optimize performance for extremely large datasets.

---

## Contributors 
- [Sam Jones](https://github.com/particularspace)
- [Java Docs](https://docs.oracle.com/en/java/javase/11/docs/api/index.html)
- [ChatGPT](https://www.chatgpt.com/)
- [Sorting Algorithms](https://www.geeksforgeeks.org/sorting-algorithms/)

## License
This project is licensed under the MIT License. See `LICENSE` for more information.
