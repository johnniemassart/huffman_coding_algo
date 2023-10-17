
// required imports
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Map.Entry;

// HuffmanTreeMethods holds the required methods
public class HuffmanTreeMethods {
   /**
    * freqCount method converts the file content to a frequency table for each
    * character.
    * 
    * @param fileText - String representation of the file passed in by the user
    * @return character frequency table
    */

   public Map<Character, Integer> freqCount(String fileText) {
      Map<Character, Integer> freqCount = new HashMap<>();
      for (char ch : fileText.toCharArray()) {
         freqCount.put(ch, freqCount.getOrDefault(ch, 0) + 1);
      }
      return freqCount;
   }

   /**
    * buildTreeRoot method correctly orders the character frequency table. Lower
    * frequency characters have higher priority. If characters have the same
    * frequency, the characters are ordered alphabetically. Once the frequency
    * table is ordered, the tree is built.
    * 
    * @param fileText - String representation of the file passed in by the user
    */
   public void buildTreeToRoot(String fileText) {
      Map<Character, Integer> freqCount = freqCount(fileText);
      PriorityQueue<HNode> orderedQueue = new PriorityQueue<>();
      for (Entry<Character, Integer> e : freqCount.entrySet()) {
         orderedQueue.add(new HNode(e.getKey(), e.getValue()));
      }
      while (orderedQueue.size() > 1) {
         HNode left = orderedQueue.poll();
         HNode right = orderedQueue.poll();
         HNode parent = new HNode('#', left.frequency + right.frequency);
         parent.leftHNode = left;
         parent.rightHNode = right;
         orderedQueue.add(parent);
      }
      getHuffmanCodes(orderedQueue.peek(), "");

   }

   /**
    * treeRoot method performs the same actions as buildTreeRoot; however, this
    * method returns the tree root as opposed to building the tree. Necessary for
    * the huffmanDecompress method.
    * 
    * @param fileText - string representation of the file passed in by the user
    * @return - the root of the tree
    */
   public HNode treeRoot(String fileText) {
      Map<Character, Integer> freqCount = freqCount(fileText);
      PriorityQueue<HNode> orderedQueue = new PriorityQueue<>();
      for (Entry<Character, Integer> e : freqCount.entrySet()) {
         orderedQueue.add(new HNode(e.getKey(), e.getValue()));
      }
      while (orderedQueue.size() > 1) {
         HNode left = orderedQueue.poll();
         HNode right = orderedQueue.poll();
         HNode parent = new HNode('#', left.frequency + right.frequency);
         parent.leftHNode = left;
         parent.rightHNode = right;
         orderedQueue.add(parent);
      }
      return orderedQueue.peek();
   }

   // huffCodes HashMap that stores the huffman character codes
   public Map<Character, String> huffCodes = new HashMap<>();

   /**
    * getHuffmanCodes method recursively builds the huffCodes character table.
    * 
    * @param root   - traversing the left and right nodes of the tree
    * @param prefix - creating the character codes
    */
   public void getHuffmanCodes(HNode root, String prefix) {
      if (root == null) {
         return;
      }
      if (root.character != '#') {
         huffCodes.put(root.character, prefix);
      } else {
         getHuffmanCodes(root.leftHNode, prefix + "0");
         getHuffmanCodes(root.rightHNode, prefix + "1");
      }
   }

   /**
    * outputHuffmanCodes method loops through the huffCodes HashMap to print
    * the character codes.
    * 
    * @param fileText - string representation of the file passed in by the user
    * @return - character codes
    */
   public String outputHuffmanCodes(String fileText) {
      buildTreeToRoot(fileText);
      String outputHuffCodes = "";
      for (Map.Entry<Character, String> entry : huffCodes.entrySet()) {
         outputHuffCodes += entry.getKey() + " - " + entry.getValue() + "\n";
      }
      return outputHuffCodes;
   }

   /**
    * huffmanCompress method loops through each character from the string passed in
    * by the user and correlates it's huffman code to build the appropriate
    * encoded string.
    * 
    * @param fileText - string representation of the file passed in by the user
    * @return - the encoded string result
    */
   public String huffmanCompress(String fileText) {
      buildTreeToRoot(fileText);
      String encoded = "";
      for (char c : fileText.toCharArray()) {
         encoded += huffCodes.get(c);
      }
      return "The encoded message is " + encoded + "\n";
   }

   /**
    * huffmanDecompress method loops through the encoded (compressed) string to
    * create the decompressed (decoded) string equivalent. If the encoded character
    * is a 0, move left down the tree. If the encoded character is 1, move right
    * down the tree. The node is a leaf node if it does not contain any children.
    * Print the leaf node character from the tree and return to the root of the
    * tree once reached.
    * 
    * @param compressed - the encoded string produced by the huffmanCompress method
    * @param treeRoot   - the root of the tree to traverse through to print the
    *                   decompressed string
    * @return - the decompressed string result
    */
   public String huffmanDecompress(String compressed, HNode treeRoot) {
      HNode node = treeRoot;
      String decompress = "";
      for (int i = 0; i < compressed.length(); i++) {
         if (compressed.charAt(i) == '0') {
            node = node.leftHNode;
         } else if (compressed.charAt(i) == '1') {
            node = node.rightHNode;
         }
         if (node.leftHNode == null && node.rightHNode == null) {
            decompress += node.character;
            node = treeRoot;
         }
      }
      return "The decoded message is " + decompress + "\n";
   }

   /**
    * bitSize method outputs the total bit size required for Huffman code -> for
    * each character in fileText, as well as the required bit size to convert to
    * ASCII character to read encoded message.
    * 
    * @param fileText - string representation of the file passed in by the user
    * @return - string message -> message size, ASCII character table size, total
    *         size
    */
   public String bitSize(String fileText) {
      buildTreeToRoot(fileText);
      Map<Character, Integer> freqCount = freqCount(fileText);
      int msgSize = 0;
      int asciiSize = 8;
      int asciiCharacterSize = 0;
      for (Map.Entry<Character, String> huffCode : huffCodes.entrySet()) {
         for (Map.Entry<Character, Integer> count : freqCount.entrySet()) {
            if (huffCode.getKey() == count.getKey()) {
               msgSize += huffCode.getValue().length() * count.getValue();
            }
         }
         int countValue = 1;
         int countKeyValue = huffCode.getKey();
         countKeyValue = countValue;
         asciiCharacterSize += countKeyValue * asciiSize;
      }
      int huffmanMsgSize = msgSize + asciiCharacterSize;
      String bitSizeMsg = "The message size is " + msgSize + ".\n" + "The required ASCII table size is "
            + asciiCharacterSize + ".\n" + "The total bits require via Hoffman coding is " + huffmanMsgSize + ".";
      return bitSizeMsg;
   }

}
