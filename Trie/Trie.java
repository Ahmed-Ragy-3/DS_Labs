import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Trie /*implements Trie_interface*/ { 
   TrieNode head ;
   int size ;

   public Trie() {
      this.head = new TrieNode() ;
      this.size = 0 ;
   }

   public Trie(String...words) {
      this.head = new TrieNode() ;
      this.size = 0 ;
      for(String word : words) {
         this.insert(word);
      }
   }

   public Trie(List<String> words) {
      this.head = new TrieNode() ;
      this.size = 0 ;
      this.insert(words);
   }

   boolean isEmpty() {
      return (this.size == 0) ;
   }

   void clear() {
      this.size = 0 ;
      this.head = new TrieNode() ;
   }

   void insert(List<String> words) {
      for(String word : words) {
         insert(word);
      }
   }

   void insert(String...words) {
      for(String word : words) {
         insert(word);
      }
   }

   void insert(String word) {
      this.size++ ;
      TrieNode temp = head ;
      for(char c : word.toCharArray()) {

         if(temp.letters.containsKey(c)) {
            temp = temp.letters.get(c) ;

         }else {
            temp.letters.put(c , new TrieNode()) ;
            temp = temp.letters.get(c) ;
         }
      }
      temp.wordEnd = true ;
   }

   boolean search(String word) {
      TrieNode temp = head ;
      for(char c : word.toCharArray()) {

         if(!temp.letters.containsKey(c))
            return false ;

         temp = temp.letters.get(c) ;
      }
      return temp.wordEnd ;
   }

   boolean containsPrefix(String prefix) {
      TrieNode temp = head ;
      for(char c : prefix.toCharArray()) {

         if(!temp.letters.containsKey(c))
            return false ;

         temp = temp.letters.get(c) ;
      }
      return true ;
   }

   public void delete(String word) {
      // Case 1 : do - done            (delete do)   ---> set wordEnd to false
      // Case 2 : do - done            (delete done) ---> remove hashmap of letter (o)
      // Case 2 : do - done - door     (delete done) ---> remove key (n) in the hashmap of letter(o) 
      // Case 4 : ahmed                (delete ahmed) --> remove (a) in first hashmap
      // Case 5 : bear - bean          (delete bean) ---> remove key (n) in the hashmap of letter(a) 
   }

   // private void delete() {

   // }

   String largestCommonPrefix(String word) {
      String lcp = "" ;
      TrieNode temp = head ;
      for(char c : word.toCharArray()) {

         if(!temp.letters.containsKey(c)) break ;

         lcp += c ;
         temp = temp.letters.get(c) ;
      }
      return lcp ;
   }

   public static Trie combine(Trie t1 , Trie t2) {
      Trie both = new Trie() ;
      for(String word : t1.toList()) {
         both.insert(word);
      }
      for(String word : t2.toList()) {
         both.insert(word);
      }
      return both ;
   }

   List<String> toList() {
      return toList(head , new ArrayList<>() , "") ;
   }

   protected List<String> toList(TrieNode tempNode , List<String> list , String str) {
      if(tempNode.wordEnd)
         list.add(str) ;

      for(char c : tempNode.letters.keySet()) {
         toList(tempNode.letters.get(c) , list , str + c) ;
      }

      return list ;
   }

   List<String> getWordsWithPrefix(String prefix) {
      List<String> list = new ArrayList<>() ;
      TrieNode tempNode = head ;
      for(char c : prefix.toCharArray()) {
         tempNode = tempNode.letters.get(c) ;
      }
      list = toList(tempNode , list , "") ;
      for(int i=0 ; i<list.size() ; i++) {
         list.set(i , prefix.concat(list.get(i))) ;
         //System.out.println("----> " + str);
      }
      return list ;
   }

   public int countWordsWithPrefix(String prefix) {
      TrieNode tempNode = head ;
      for(char c : prefix.toCharArray()) {
         if(!tempNode.letters.containsKey(c)) return 0 ;
         tempNode = tempNode.letters.get(c) ;
      }
      return countWordsWithPrefix(tempNode , 0) ;
   }

   protected int countWordsWithPrefix(TrieNode tempNode , int count) {
      if(tempNode.wordEnd) count++ ;

      for(char c : tempNode.letters.keySet()) {
         count = countWordsWithPrefix(tempNode.letters.get(c) , count) ;
      }
      return count ;
   }

   protected void print(List<String> words) {
      for(String word : words) {
         System.out.println(word);
      }
   }
   
   void printTrieWords() {
      System.out.println("\n-------------- All words -------------") ;
      print(this.toList()) ;
      System.out.println("--------------------------------------") ;
   }

   void printWordsWithPrefix(String prefix) {
      System.out.print("\n---------------") ;
      System.out.print(" Words with prefix \"" + prefix + "\" ");
      System.out.println("----------------");
      print(getWordsWithPrefix(prefix));
      System.out.println("-----------------------------------------------------") ;
   }

   void printLevels() {
      // print the letters that is in front of the queue
      // then add all letters of the printed hashmap
      Queue<Map<Character , TrieNode>> queue = new LinkedList<>() ;
      queue.add(head.letters) ;
      int prev_level = 1 ;
      int current_level = 0 ;
      while (!queue.isEmpty()) {
         for(TrieNode tempNode : queue.peek().values()) {
            queue.add(tempNode.letters) ;
            current_level++ ;
         }
         System.out.print(queue.poll().keySet().toString() + " ") ;
         if(--prev_level == 0) {
            prev_level = current_level ;
            current_level = 0 ;
            System.out.println();
         }
         
      }
   }

   public static void main(String[] args) {
      Trie t1 = new Trie("ahmed" , "ahmaa" , "nour" , "test") ;
      // Trie t1 = new Trie("ahmed" );
      //t1.insert("Today is Monday" , "testing trie" , "trie used in word processing" , "any except 9") ;
      t1.insert("antibody", "antifreeze", "antithesis");
      //t1.printLevels();
      System.out.println(t1.countWordsWithPrefix("anti"));
      //System.out.println(t1.largestCommonPrefix("trie used in searching")) ;

      // System.out.println(t1.size);
      // t1.printWordsWithPrefix("ant") ;
      // t1.printWordsWithPrefix("anti");
      // System.out.println(t1.search("ahmggfggrgegege")) ;
      // System.out.println(t1.search("ghs"));
      // t1.printWordsWithPrefix("a");
      // t1.printTrie();
      
      // t1.clear();
      // t1.printTrie();

      // Trie t2 = new Trie("app1" , "app2" , "app3" , "in trie 2") ;
      // Trie both = combine(t1 , t2) ;
      // t1.printTrieWords();
      // t2.printTrieWords();
      // both.printTrieWords();

      // System.out.println(t2.size);
      // System.out.println(t2.isEmpty());
      // t2.printTrie();
   }

}
