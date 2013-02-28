package erik.boggle;

import android.content.Context;
import erik.boggle.util.SolvedWords;

import java.util.*;

/**
 * @author erik
 */
public class Solver {
    private Set<String> foundWords = new HashSet<String>();

    public List<String> solve(char[][] board, Context context) {
        for(int i = 0; i<4; i++) {
            for(int j = 0; j<4; j++) {
                solve(board, i, j, Dictionary.getInstance(context).getRoot());
            }
        }
        return SolvedWords.sort(foundWords);
    }
    
    public void solve(char[][] board, int x, int y, LetterNode node) {
        if(node.hasChild('.')) {
            addWord(node);
        }
        char letter = board[x][y];
        char[][] taken = copy(board);
        taken[x][y] ='#';

        if(letter == 'q') {
            if(node.getChild('q') != null && node.getChild('q').hasChild('u')) {
                node = node.getChild('q');
                letter = 'u';
            }
        }

        if(node.hasChild(letter) && node.getChild(letter).hasChild('.')) {
            addWord(node.getChild(letter));
        }

        if(node.hasChild(letter) && isAvailable(taken, x, y + 1)) {
            solve(taken, x, y + 1, node.getChild(letter));
        }
        if(node.hasChild(letter) && isAvailable(taken, x+1, y+1)) {
            solve(taken, x+1, y + 1, node.getChild(letter));
        }
        if(node.hasChild(letter) && isAvailable(taken,x+1, y)) {
            solve(taken, x+1, y, node.getChild(letter));
        }
        if(node.hasChild(letter) && isAvailable(taken, x+1, y-1)) {
            solve(taken, x+1, y-1, node.getChild(letter));
        }
        if(node.hasChild(letter) && isAvailable(taken,x, y-1)) {
            solve(taken, x, y-1, node.getChild(letter));
        }
        if(node.hasChild(letter) && isAvailable(taken, x-1, y-1)) {
            solve(taken, x-1, y-1, node.getChild(letter));
        }
        if(node.hasChild(letter) && isAvailable(taken,x-1, y)) {
            solve(taken, x-1, y, node.getChild(letter));
        }
        if(node.hasChild(letter) && isAvailable(taken, x-1, y+1)) {
            solve(taken, x-1, y+1, node.getChild(letter));
        }
    }

    private  boolean isAvailable(char[][]board, int x, int y) {
        return  x >=  0 && x <= 3 && y >= 0 && y <= 3 && board[x][y] != '#';
    }

    private  void addWord(LetterNode node) {
        String s = "";
        while(node.getParent() != null) {
            s = node.getLetter() + s;
            node = node.getParent();
        }
        foundWords.add(s);
    }

    private  char[][] copy(char[][] original) {
        char[][] copy = new char[4][4];
        for(int i = 0; i<4; i++) {
            for(int j = 0; j<4; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    public Set<String> getFoundWords() {
        return foundWords;
    }

    public static void main(String[] args) {
        Solver solver = new Solver();

        LetterNode root = new LetterNode();
        root.insert("quit");
        root.insert("zit");

        char[][] board = new char[][]{
                {'q','i','t','z'},
                {'z','z','z','z'},
                {'z','z','z','z'},
                {'z','z','z','z'}
        };

        for(int i = 0; i<4; i++) {
            for(int j = 0; j<4; j++) {
                solver.solve(board, i, j,root);
            }
        }

        for(String word : solver.getFoundWords()) {
            System.out.println(word);
        }
    }
}
