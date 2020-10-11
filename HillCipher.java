/*=============================================================================
|   Assignment:  HW 01 –Encrypting a plaintext file using the Hill cipher in the key file
|
|    Author:  Eric Smith
|    Language:  Java
|
|   To Compile:  javac HillCipher.java
|
|   To Execute: java Hw01 hillcipherkey.txtplaintextfile.txt
|               where the files in the command line are in the current directory.
|               The key text contains a single digit on the first line defining the size of the key
|               while the remaining linesdefine the key, for example:
|                     3
|                     1 2 3
|                     4 5 6
|                     7 8 9
|               The plain text file containsthe plain text in mixed case with spaces & punctuation.
|
|   Class:  CIS3360-Security in Computing-Fall2020
|   Instructor:  McAlpin
|   Due Date:  11 October 2020
|
+=============================================================================*/

import java.io.*;
import java.util.*;

public class HillCipher
{

  public int[][] key; //key matrix
  public int size; //size of the key matrix (denoted by the first line in the file.)
  public int runtime; //amount of blocks to encrypt.

  //creating constructor for each hill cipher
  //it must have a key (key matrix) and a matrix size
  public HillCipher(int [][] key_matrix)
  {
    key = key_matrix;
    size = key.length;

  }

  public String encrypt(String plaintxt)
  {
    String resultant = "";
    for(int i = 0; i < plaintxt.length(); i += size)
    {
      resultant = resultant + encrypt_PlainText_Block(plaintxt.substring(i,i + size));
    }
    return resultant;
  }

  /*
    The plain text is to be encrypted by blocks based off of matrix size.
    NOTE: the length of each block is the length of the key.
  */
  private String encrypt_PlainText_Block(String block)
  {
    char[] resultant = new char[size];
    for(int row = 0; row < size; row++)
    {
      int sum = 0;
      for(int col = 0; col < size; col++)
      {
        sum += (key[row][col] * (block.charAt(col) - 'a'));
      }
      resultant[row] = (char)('a' + sum % 26);
    }
    //returns the encrypted text as a String variable
    return new String(resultant);
  }

  public static void main(String[] args) throws Exception
  {

    /*
      1. set up files
      2. get file name from command line arguments
      3. set up scannner to read from files
      4. build matrix based of size and read ints into the matrix.
    */
    File key_text_File;
    File plain_text_File;

    //connect to the file uses the first and second args from command line
    key_text_File = new File(args[0]);
    plain_text_File = new File(args[1]);

    Scanner readFile = new Scanner(key_text_File);
    Scanner readFile2 = new Scanner(plain_text_File);

    //make connection to file to get matrix_size
    int matrix_size = readFile.nextInt();

    //key_matrix array initialized with matrix size
    int key_matrix[][] = new int[matrix_size][matrix_size];

    //generate the key matrix from ints left in file
    for(int row = 0; row < matrix_size; row++){
      for(int col = 0; col < matrix_size; col++){
        key_matrix[row][col] = readFile.nextInt();
      }
    }

    // TEST: Printing the key matrix;
    System.out.println("Key matrix:\n");
    for(int row = 0; row < matrix_size; row++)
    {
      for(int col = 0; col < matrix_size; col++)
      {
        System.out.print(key_matrix[row][col] + " ");
      }

      System.out.println();
    }

    //TEST: Printing the plainText_matrix
    String plain_text = null;

    while(readFile2.hasNextLine())
    {
      //read next line, make it lowercase, and replace all punctation and white space with no space
      plain_text = readFile2.nextLine().toLowerCase().replaceAll("[\\s\\p{Punct}]+", "");
    }

    System.out.println("\nPlain text:\n");

    //add padding to plaintext
    while(plain_text.length() % matrix_size != 0)
    {
      plain_text = plain_text + "x";
    }

    int count = 0;
    for(int i = 0; i < plain_text.length(); i++)
    {
      count++;
      if(count == 80){System.out.println(); count = 0;}
      System.out.print(plain_text.charAt(i));
    }

    //create new cipher and encrypt
    HillCipher cipher = new HillCipher(key_matrix);
    System.out.println("\n\nCipher text:\n");
    //this allows us to encrypt the whole cipher block by block
    int count2 = 0;
    for(int i = 0; i < plain_text.length(); i+=matrix_size)
    {
      count2++;
      if(count == 80){System.out.println(); count2 = 0;}
      System.out.print(cipher.encrypt_PlainText_Block(plain_text.substring(i, i + matrix_size)));
    }
    System.out.println();
  }

}

/*=============================================================================
|     I Eric Smith (*****) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied  or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/
