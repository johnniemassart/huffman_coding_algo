
// required imports
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// HuffmanEncoding class reads in the file from the user and outputs the results
public class HuffmanEncoding {

   // number of arguments passed in by the user
   public static int ARGS_LENGTH = 2;

   // main method to execute code
   public static void main(String args[]) throws IOException {

      // create instance of class to access important methods
      HuffmanTreeMethods huffmanTreeMethods = new HuffmanTreeMethods();

      // require correct amount of user input
      if (args.length != ARGS_LENGTH) {
         System.out.println("You have entered an incorrect number of command line arguments. The program requires "
               + ARGS_LENGTH + " command lines arguments.");
         System.exit(1);
      }

      BufferedReader reader;
      BufferedWriter writer;

      // open the files to be used for input and output
      try {
         reader = new BufferedReader(new FileReader(args[0]));
         writer = new BufferedWriter(new FileWriter(args[1]));
         // reader = new BufferedReader(new FileReader("./input/iTest1.txt"));
         // writer = new BufferedWriter(new FileWriter("./output/oTest1.txt"));
      } catch (Exception ioe) {
         System.err.println(ioe.toString());
         return;
      }

      // read the file, store content into variable
      String line = reader.readLine();
      String fileText = "";
      while (line != null) {
         fileText += line;
         line = reader.readLine();
      }

      // content to be written to file
      String huffCodes = huffmanTreeMethods.outputHuffmanCodes(fileText);
      String encodedMsg = huffmanTreeMethods.huffmanCompress(fileText);
      HNode treeRoot = huffmanTreeMethods.treeRoot(fileText);
      String decodedMsg = huffmanTreeMethods.huffmanDecompress(encodedMsg, treeRoot);
      // write file
      writer.write(huffCodes + encodedMsg + decodedMsg + huffmanTreeMethods.bitSize(fileText));

      // close the reader/writer classes
      try {
         reader.close();
         writer.close();
      } catch (Exception e) {
         System.err.println(e.toString());
      }

   }

}