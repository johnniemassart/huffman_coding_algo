// HNode class to create each node for the tree
public class HNode implements Comparable<HNode> {

   public char character;
   public int frequency;
   public HNode leftHNode;
   public HNode rightHNode;

   // contructor with required attributes for each node
   HNode(char character, int frequency) {
      this.character = character;
      this.frequency = frequency;
   }

   // ternary operator to order the nodes by frequency. If compared nodes have same
   // frequency, then order alphabetically.
   @Override
   public int compareTo(HNode comparedNode) {
      return this.frequency == comparedNode.frequency ? this.character - comparedNode.character
            : this.frequency - comparedNode.frequency;
   }

}
