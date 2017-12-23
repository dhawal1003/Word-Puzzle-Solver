package puzzle;

import java.io.*;
import java.util.*;

public class WordPuzzle {

	public static int rows, columns;
	public static int counter = 0;
	public static int maxlength = 0;
	public static PrintWriter pw1, pw2, pw3;
	public static File file1, file2, file3;

	static {
		file1 = new File("MatchedWords_LinkedList.txt");
		file2 = new File("MatchedWords_Tree.txt");
		file3 = new File("MatchedWords_Hash.txt");

		try {
			pw1 = new PrintWriter(file1);
			pw2 = new PrintWriter(file2);
			pw3 = new PrintWriter(file3);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void searchLinkedList(String str, LinkedList<String> lst) {

		String s;
		Iterator<String> itr = lst.iterator();

		while (itr.hasNext()) {
			s = itr.next();
			if (s.equals(str)) {
				pw1.println(str);
				counter++;
			}
		}

	}

	private static void searchTree(String str, AvlTree<String> tree) {

		if (tree.contains(str)) {
			pw2.println(str);
			counter++;
		}
	}

	private static void searchHashTable(String str, MyHashTable hash) {

		if (hash.contains(str)) {
			pw3.println(str);
			counter++;
		}
	}

	@SuppressWarnings("unchecked")
	private static void search(String str, Object obj) {
		if (str.length() <= maxlength) {
			if (obj instanceof LinkedList<?>) {
				searchLinkedList(str, (LinkedList<String>) obj);
			} else if (obj instanceof AvlTree<?>) {
				searchTree(str, (AvlTree<String>) obj);
			} else if (obj instanceof MyHashTable) {
				searchHashTable(str, (MyHashTable) obj);
			}
		}

	}

	public static void checkDirections(char grid[][], int r, int c, Object obj) {

		StringBuilder sb = new StringBuilder();
		sb.append(grid[r][c]);
		search(sb.toString(), obj);

		// Right
		for (int i = c + 1; i < columns; i++) {
			sb.append(grid[r][i]);
			search(sb.toString(), obj);
		}
		
		// Up
		sb.setLength(0);
		sb.append(grid[r][c]);

		for (int i = r - 1; i >= 0; i--) {
			sb.append(grid[i][c]);
			search(sb.toString(), obj);
		}
		// Left
		sb.setLength(0);
		sb.append(grid[r][c]);

		for (int i = c - 1; i >= 0; i--) {
			sb.append(grid[r][i]);
			search(sb.toString(), obj);
		}
		// Bottom
		sb.setLength(0);
		sb.append(grid[r][c]);

		for (int i = r + 1; i < rows; i++) {
			sb.append(grid[i][c]);
			search(sb.toString(), obj);
		}
		// checkDiagonalRightUp
		sb.setLength(0);
		sb.append(grid[r][c]);

		for (int i = r - 1, j = c + 1; i >= 0 && j < columns; i--, j++) {
			sb.append(grid[i][j]);
			search(sb.toString(), obj);
		}
		// checkDiagonalRightDown
		sb.setLength(0);
		sb.append(grid[r][c]);

		for (int i = r + 1, j = c + 1; i < rows && j < columns; i++, j++) {
			sb.append(grid[i][j]);
			search(sb.toString(), obj);
		}
		// checkDiagonalLeftUp
		sb.setLength(0);
		sb.append(grid[r][c]);

		for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--) {
			sb.append(grid[i][j]);
			search(sb.toString(), obj);
		}

		// checkDiagonalLeftDown
		sb.setLength(0);
		sb.append(grid[r][c]);

		for (int i = r + 1, j = c - 1; i < rows && j >= 0; i++, j--) {
			sb.append(grid[i][j]);
			search(sb.toString(), obj);
		}

	}

	public static void displayGrid(char grid[][]) {

		System.out.println("The " + rows + " * " + columns + " grid of characters is shown below: ");

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++)
				System.out.print(grid[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}

	public static void main(String[] args) throws IOException {

		long start, end;
		String str = null;

		LinkedList<String> words = new LinkedList<String>();
		AvlTree<String> tree = new AvlTree<String>();
		MyHashTable hash = new MyHashTable();

		FileReader fin = new FileReader("dictionary.txt");
		BufferedReader bin = new BufferedReader(fin);

		System.out.println("Loading Dictionary.......Please wait.....");

		counter = 0;
		start = System.currentTimeMillis();
		while ((str = bin.readLine()) != null) {
			//words.add(str);
			tree.insert(str);
			hash.insert(str);
			counter++;

			if (str.length() > maxlength)
				maxlength = str.length();

		}
		bin.close();
		end = System.currentTimeMillis();

		System.out.println("Loaded dictionary in " + (end - start) + " ms. Number of words: " + counter);

		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the number of rows of the grid: ");
		rows = sc.nextInt();
		System.out.println("Please enter the number of columns of the grid: ");
		columns = sc.nextInt();
		sc.close();

		char grid[][] = new char[rows][columns];

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				grid[i][j] = (char) (new Random().nextInt(26) + 'a');

		displayGrid(grid);

		// Search LinkedList

/*		counter = 0;
		start = System.currentTimeMillis();

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				checkDirections(grid, i, j, words);

		System.out.println("Linked list: Number of Matched words = " + counter);
		end = System.currentTimeMillis();

		System.out.println("For Linked list, Elapsed time in ms: " + (end - start));
		System.out.println();*/

		// Search Tree
		
		counter = 0;
		start = System.currentTimeMillis();

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				checkDirections(grid, i, j, tree);

		System.out.println("Avl Tree: Number of Matched words = " + counter);
		end = System.currentTimeMillis();

		System.out.println("For Avl Tree, Elapsed time in ms: " + (end - start));
		System.out.println();

		// Search Hash Table
		counter = 0;
		start = System.currentTimeMillis();

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				checkDirections(grid, i, j, hash);

		System.out.println("Hash Table: Number of Matched words = " + counter);
		end = System.currentTimeMillis();

		System.out.println("For Hash Table, Elapsed time in ms: " + (end - start));
		System.out.println();

		pw1.close();
		pw2.close();
		pw3.close();

		System.out.println("The matched words are in the following files: ");
		System.out.println("a) Linked List- " + file1.getAbsolutePath());
		System.out.println("b) Tree- " + file2.getAbsolutePath());
		System.out.println("c) Hash Table- " + file3.getAbsolutePath());

	}

}
