package jp.ac.uryukyu.ie.e245708;
import javax.swing.*;

public class GameDisplay {
    private JFrame frame;
    private JTextArea textArea;

    // コンストラクタでウィンドウをセットアップ
    public GameDisplay() {
        frame = new JFrame("Game Display");
        textArea = new JTextArea();

        // JTextArea の設定
        textArea.setEditable(false); // 編集不可にする
        textArea.setLineWrap(true); // テキストの折り返しを有効化
        textArea.setWrapStyleWord(true); // 単語単位で折り返す

        // JScrollPane を追加してスクロール可能にする
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        // ウィンドウの設定
        frame.setSize(300, 150); // ウィンドウサイズ
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ウィンドウ閉じる時にプログラム終了
        frame.setVisible(true); // ウィンドウを表示
        frame.setLocationRelativeTo(null);
    }

    // テキストを追加表示するメソッド
    public void appendText(String text) {
        textArea.append(text + "\n"); // テキストを追加し改行
    }

    // テキストエリアをクリアするメソッド
    public void clearText() {
        textArea.setText(""); // テキストを空にする
    }

    //ゲーム画面を出力するメソッド
    public void gameDisplay(Player player, Opponent opponent){
        clearText();
        appendText(opponent.battlePokemon.pokemonName + " lv." + opponent.battlePokemon.level);
        appendText(opponent.battlePokemon.hReal + "/" + opponent.battlePokemon.maxHP + opponent.battlePokemon.abnCon);
        appendText("");
        appendText("");
        appendText("");
        appendText(player.battlePokemon.pokemonName + " lv." + player.battlePokemon.level);
        appendText(player.battlePokemon.hReal + "/" + player.battlePokemon.maxHP + player.battlePokemon.abnCon);
    }
}
