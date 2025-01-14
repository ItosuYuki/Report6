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
    Trainer player = new Trainer("レッド", new Pokemon[]{pokemon1, pokemon2, pokemon3});
    Trainer opponent = new Trainer("グリーン", new Pokemon[]{pokemon4, pokemon5, pokemon6});

    };

    //状態異常
    String[] abnCon = {"", "まひ", "こおり", "やけど", "どく", "もうどく", "ねむり", "ひんし"};
    void abnCon(Trainer trainer, Pokemon performer, Pokemon target){
        int elaTurn = 0; //経過ターン数
        switch(trainer.battlePokemon.abnCon){
            case 1://まひ
                trainer.battlePokemon.sReal = trainer.battlePokemon.sReal / 2;
                int rand1 = randomGenerator.nextInt(99) + 1;
                if(rand1 < 25){
                    //行動不能　後で実装
                    System.out.println(trainer.battlePokemon + "は からだ がしびれて うごけない！");
                }
                break;
            case 2://こおり
                trainer.battlePokemon.sReal = trainer.battlePokemon.sReal / 2;
                int rand2 = randomGenerator.nextInt(99) + 1;
                if(rand2 < 20){
                    System.out.println(trainer.battlePokemon + "のこおりがとけた");
                    trainer.battlePokemon.abnCon = 0;
                }else{
                    //行動不能　後で実装
                    System.out.println(trainer.battlePokemon + "は こおってしまってうごけない");
                }
                break;
            case 3://やけど
                //物理技のダメージ1/2 useTechniqueメソッドで実装
                trainer.battlePokemon.damaged((int)(trainer.battlePokemon.maxHP / 8));
                break;
            case 4://どく
                trainer.battlePokemon.damaged((int)(trainer.battlePokemon.maxHP / 8));
                break;
            case 5://もうどく
                trainer.battlePokemon.damaged((int)(trainer.battlePokemon.maxHP * elaTurn / 16)); //経過ターン数を数える部分を後で作成する
                break;
            case 6://ねむり
                int sleepCount = randomGenerator.nextInt(2) + 2; //ねむりカウント
                if(sleepCount == 0){
                    trainer.battlePokemon.abnCon = 0;
                    System.out.println(trainer.battlePokemon + "はめをさました！");
                }else{
                    //行動不能　後で実装
                    System.out.println(trainer.battlePokemon + "はぐうぐうねむっている");
                    sleepCount -= 1;
                }
                break;
            case 7://ひんし ポケモンを場に出せなくなる
                trainer.numRemPokemon --;
                showParty(trainer, performer, target);
                break;
        }
    };

    //技の追加効果

    public void useCommand(Trainer trainer, Pokemon performer, Pokemon target) {
        String[] commandList = new String[]{"たたかう", "ポケモン", "にげる"};
        int index = 0;
        for(String command: commandList){
            System.out.println(index + ": " + command);
            index ++;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("コマンド選択");
        int choice = scanner.nextInt();
        switch (choice) {
            case 0: // たたかう
                useTechnique(trainer, performer, target);
                break;
            case 1: // ポケモン
                showParty(trainer, performer, target);
                break;
            case 2: // にげる
                runAway(trainer, performer, target);
                break;
            default:
                System.out.println("無効なコマンドです。");
                useCommand(trainer, performer, target);
        }
    }
    

    public void useTechnique(Trainer trainer, Pokemon performer, Pokemon target){
        displayCommand(performer.techniques);
        Scanner scanner = new Scanner(System.in);
        System.out.print("コマンド選択");
        int choice = scanner.nextInt();
        if(0 <= choice && choice < performer.techniques.length){
        }else if(choice == performer.techniques.length){
            useCommand(trainer, performer, target); //もどる
        }else{
            System.out.println("無効なコマンドです。");
            useTechnique(trainer, performer, target);
        }
        Technique choiceTechnique = performer.techniques[choice];
        switch(choiceTechnique.cla){
            case "物理":
                double rand1 = (randomGenerator.nextInt(15) + 85) / 100.0;
                int damage1 = (int)((int)((int)((int)(performer.level * 2 / 5 + 2) * choiceTechnique.pwr * performer.aReal / target.bReal) / 50 + 2) * rand1);
                if(performer.abnCon == 3){ //やけど時ダメージ1/2
                    damage1 = damage1 / 2;
                }
                target.damaged(damage1);
                choiceTechnique.PP -= 1;
                break;
            case "特殊":
                double rand2 = (randomGenerator.nextInt(15) + 85) / 100.0;
                int damage2 = (int)((int)((int)((int)(performer.level * 2 / 5 + 2) * choiceTechnique.pwr * performer.cReal / target.dReal) / 50 + 2) * rand2);
                target.damaged(damage2);
                choiceTechnique.PP -= 1;
                break;
            case "変化":
                break;
            default:
                System.out.println("無効なコマンドです。");
        }
    }

    public void showParty(Trainer trainer, Pokemon performer, Pokemon target){
        displayCommand(trainer.party);
        Scanner scanner1 = new Scanner(System.in);
        System.out.print("コマンド選択");
        int choice1 = scanner1.nextInt();
        if(0 <= choice1 && choice1 < trainer.party.length){
        }else if(choice1 == trainer.party.length){
            useCommand(trainer, performer, target); //もどる
        }else{
            System.out.println("無効なコマンドです。");
            showParty(trainer, performer, target);;
        }
        Pokemon choicePokemon = trainer.party[choice1];
        System.out.println("タイプ :" + choicePokemon.type);
        System.out.print("HP :" + choicePokemon.hReal + "/" + choicePokemon.maxHP);
        System.out.print("こうげき :" + choicePokemon.aReal);
        System.out.println("ぼうぎょ :" + choicePokemon.bReal);
        System.out.print("とくこう :" + choicePokemon.cReal);
        System.out.print("とくぼう :" + choicePokemon.dReal);
        System.out.println("すばやさ :" + choicePokemon.sReal);
        for(Technique tecnique:choicePokemon.techniques){
            System.out.println(tecnique.techniqueName + "PP: " + tecnique.PP + "/" + tecnique.maxPP);
        }
        if(trainer.battlePokemon == choicePokemon | choicePokemon.abnCon == 7){
            String[] commandList = {"もどる"};
            displayCommand(commandList);
            Scanner scanner2 = new Scanner(System.in);
            System.out.print("コマンド選択");
            int choice2 = scanner2.nextInt();
            switch(choice2){
                case 0:
                    showParty(trainer, performer, target); //もどるコマンド
                    break;
                default:
                    System.out.println("無効なコマンドです。");
                    showParty(trainer, performer, target);

            }
        }else{
            String[] commandList = {"こうかん", "もどる"};
            displayCommand(commandList);
            Scanner scanner2 = new Scanner(System.in);
            System.out.print("コマンド選択");
            int choice2 = scanner2.nextInt();
            switch(choice2){
                case 0:
                    exchange(trainer, trainer.party[choice2]);
                    break;
                case 1:
                    showParty(trainer, performer, target); //もどるコマンド
                    break;
                default:
                    System.out.println("無効なコマンドです。");
                    showParty(trainer, performer, target);
            }
        }
    }
    public void exchange(Trainer trainer, Pokemon choicePokemon){
        if(trainer.battlePokemon.abnCon == 7){ //ひんしの時
        }else{
        System.out.println(trainer.battlePokemon + " 戻れ！");
        }
        trainer.battlePokemon = choicePokemon;
        System.out.println("ゆけっ！" + trainer.battlePokemon + "！");
    }
    public void runAway(Trainer trainer, Pokemon performer, Pokemon target){
        System.out.println("勝負を あきらめて 降参しますか？");
        displayCommand(new String[]{"はい", "いいえ"});
        Scanner scanner = new Scanner(System.in);
            System.out.print("コマンド選択");
            int choice = scanner.nextInt();
            switch(choice){
                case 0:
                    System.out.println("降参が 選ばれました");
                    System.exit(0);
                    break;
                case 1:
                    useCommand(trainer, performer, target); //もどるコマンド
                    break;
                default:
                    System.out.println("無効なコマンドです。");
                    runAway(trainer, performer, target);
            }
    }

    public void displayCommand(String[] commandList){
        int index = 0;
        for(String command: commandList){
            System.out.println(index + ": " + command);
            index ++;
        }
        System.out.println("もどる");
    }
    public void displayCommand(Pokemon[] commandList){
        int index = 0;
        for(Pokemon pokemon: commandList){
            System.out.println(index + ": " + pokemon.pokemonName);
            index ++;
        }
        System.out.println("もどる");
    }
    public void displayCommand(Technique[] commandList){
        int index = 0;
        for(Technique technique: commandList){
            System.out.println(index + ": " + technique.techniqueName);
            index ++;
        }
        System.out.println("もどる");
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
        battle.useCommand(player, player.battlePokemon, opponent.battlePokemon);
    }
}
