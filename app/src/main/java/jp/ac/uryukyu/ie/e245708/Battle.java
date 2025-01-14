package jp.ac.uryukyu.ie.e245708;
import java.util.Scanner;
import java.util.Random;

public class Battle {
    //乱数生成
    Random randomGenerator = new Random();
    Trainer player;
    Trainer opponent;
    Battle(){
    //技リスト
    Technique tec1 = new Technique("ボルテッカー","物理", 120, 100, 15, true, "でんき", 0, 1);
    Technique tec2 = new Technique("アイアンテール","物理", 100, 75, 15, true, "はがね", 0, 2);
    Technique tec3 = new Technique("かわらわり","物理", 75, 100, 15, true, "かくとう", 0, 0);
    Technique tec4 = new Technique("ねこだまし","物理", 40, 100, 10, true, "ノーマル", 3, 3);
    Technique tec5 = new Technique("のしかかり","物理", 85, 100, 15, true, "ノーマル", 0, 4);
    Technique tec6 = new Technique("じしん","物理", 100, 100, 10, false, "じめん", 0, 0);
    Technique tec7 = new Technique("かみくだく","物理", 80, 100, 15, true, "あく", 0, 5);
    Technique tec8 = new Technique("タネばくだん","物理", 80, 100, 15, false, "くさ", 0, 0);
    Technique tec9 = new Technique("れいとうビーム","特殊", 90, 100, 10, false, "こおり", 0, 6);
    Technique tec10 = new Technique("ハイドロポンプ","特殊", 110, 80, 5, false, "みず", 0, 0);
    Technique tec11 = new Technique("こおりのつぶて","物理", 40, 100, 30, false, "こおり", 1, 0);
    Technique tec12 = new Technique("10まんボルト","特殊", 90, 100, 15, false, "でんき", 0, 7);
    Technique tec13 = new Technique("リーフストーム","特殊", 130, 90, 5, false, "くさ", 0, 8);
    Technique tec14 = new Technique("ウッドハンマー","物理", 120, 100, 15, true, "くさ", 0, 9);
    Technique tec15 = new Technique("しねんのずつき","物理", 80, 90, 15, true, "エスパー", 0, 10);
    Technique tec16 = new Technique("やどりぎのたね","変化", 0, 90, 10, false, "くさ", 0, 11);
    Technique tec17 = new Technique("たきのぼり","物理", 80, 100, 15, true, "みず", 0, 12);
    Technique tec18 = new Technique("じしん","物理", 100, 100, 10, false, "じめん", 0, 0);
    Technique tec19 = new Technique("こおりのキバ","物理", 65, 95, 15, true, "こおり", 0, 13);
    Technique tec20 = new Technique("げきりん","物理", 120, 100, 10, true, "ドラゴン", 0, 14);
    Technique tec21 = new Technique("フレアドライブ","物理", 120, 100, 15, true, "ほのお", 0, 15);
    Technique tec22 = new Technique("インファイト","物理", 120, 100, 5, true, "かくとう", 0, 16);
    Technique tec23 = new Technique("ワイルドボルト","物理", 90, 100, 15, true, "でんき", 0, 17);
    Technique tec24 = new Technique("しんそく","物理", 80, 100, 5, true, "ノーマル", 2, 0);

    //ポケモン
    Pokemon pokemon1 = new Pokemon(
        "ピカチュウ",
        new String[]{"でんき"},
        50,
        35, 55, 40, 50, 50, 90,
        252, 0, 252, 0, 4, 0,
        31, 31, 31, 31, 31, 31,
        new Technique[]{tec1, tec2, tec3, tec4},
        "ようき"
        );
    Pokemon pokemon2 = new Pokemon(
        "カビゴン",
        new String[]{"ノーマル"},
        50,
        160, 110, 65, 65, 110, 30,
        4, 252,   0, 0, 0, 252,
        31, 31, 31, 31, 31, 31,
        new Technique[]{tec5, tec6, tec7, tec8},
        "しんちょう"
        );
    Pokemon pokemon3 = new Pokemon(
        "ラプラス",
        new String[]{"みず", "こおり"},
        50,
        130, 85, 80, 85, 95, 60,
        252, 0, 0, 4, 252, 0,
        31, 31, 31, 31, 31, 31,
        new Technique[]{tec9, tec10, tec11, tec12},
        "なまいき"
        );
    Pokemon pokemon4 = new Pokemon(
        "ナッシー",
        new String[]{"くさ", "エスパー"},
        50,
        95, 95, 85, 125, 75, 55,
        4, 252, 0, 252, 0, 0,
        31, 31, 31, 31, 31, 31,
        new Technique[]{tec13, tec14, tec15, tec16},
        "ゆうかん"
        );
    Pokemon pokemon5 = new Pokemon(
        "ギャラドス",
        new String[]{"みず", "ひこう"},
        50,
        95, 125, 79, 60, 100, 81,
        4, 252, 0, 0, 0, 252,
        31, 31, 31, 31, 31, 31,
        new Technique[]{tec17, tec18, tec19, tec20},
        "いじっぱり"
        );
    Pokemon pokemon6 = new Pokemon(
        "ウインディ",
        new String[]{"ほのお"},
        50,
        90, 110, 80, 100, 80, 95,
        4, 252, 0, 4, 0, 252,
        31, 31, 31, 31, 31, 31,
        new Technique[]{tec21, tec22, tec23, tec24},
        "いじっぱり"
        );

    //トレーナー
    Player player = new Player("レッド", new Pokemon[]{pokemon1, pokemon2, pokemon3});
    Opponent opponent = new Opponent("グリーン", new Pokemon[]{pokemon4, pokemon5, pokemon6});
    }

    public void battleActive(Trainer player, Trainer opponent) {
        if(player.numRemPokemon == 0){
            System.out.println(opponent.trainerName + "との勝負に負けた");
            System.exit(0);
        }else if(opponent.numRemPokemon == 0){
            System.out.println(opponent.trainerName + "との勝負に勝った");
            System.exit(0);
        }
    }




    public static void main(String[] args) {
        Battle battle = new Battle();
        System.out.println(player.trainerName);
        System.out.println("hello");
    }
}
