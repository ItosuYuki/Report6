package jp.ac.uryukyu.ie.e245708;

import java.util.Scanner;

public class Player extends Trainer{
    //コンストラクタ
    Player(String trainerName, Pokemon[] party){
        this.trainerName = trainerName;
        this.party = party;
        this.battlePokemon = party[0];
        this.numRemPokemon = party.length;
    }

    public void choiceCommand() {
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
                choiceTechnique();
                break;
            case 1: // ポケモン
                showParty();
                break;
            case 2: // にげる
                runAway();
                break;
            default:
                System.out.println("無効なコマンドです。");
                choiceCommand();
        }
    }

    @Override
    public Technique choiceTechnique(){
        displayCommand(battlePokemon.techniques);
        Scanner scanner = new Scanner(System.in);
        System.out.print("コマンド選択");
        int choice = scanner.nextInt();
        if(0 <= choice && choice < battlePokemon.techniques.length){
        }else if(choice == battlePokemon.techniques.length){
            choiceCommand(); //もどる
        }else{
            System.out.println("無効なコマンドです。");
            choiceTechnique();
        }
        Technique choiceTechnique = battlePokemon.techniques[choice];
        return choiceTechnique;
    }
    public Pokemon showParty(){
        displayCommand(this.party);
        Scanner scanner1 = new Scanner(System.in);
        System.out.print("コマンド選択");
        int choice1 = scanner1.nextInt();
        if(0 <= choice1 && choice1 < this.party.length){
        }else if(choice1 == this.party.length){
            choiceCommand(); //もどる
        }else{
            System.out.println("無効なコマンドです。");
            showParty();;
        }
        Pokemon choicePokemon = this.party[choice1];
        return choicePokemon;
    }

    public void showPokemon(Pokemon choicePokemon) {
        System.out.println("タイプ :" + choicePokemon.types);
        System.out.print("HP :" + choicePokemon.hReal + "/" + choicePokemon.maxHP);
        System.out.print("こうげき :" + choicePokemon.aReal);
        System.out.println("ぼうぎょ :" + choicePokemon.bReal);
        System.out.print("とくこう :" + choicePokemon.cReal);
        System.out.print("とくぼう :" + choicePokemon.dReal);
        System.out.println("すばやさ :" + choicePokemon.sReal);
        for(Technique tecnique:choicePokemon.techniques){
            System.out.println(tecnique.techniqueName + "PP: " + tecnique.PP + "/" + tecnique.maxPP);
        }
        if(this.battlePokemon == choicePokemon | choicePokemon.abnCon == 7){
            String[] commandList = {"もどる"};
            displayCommand(commandList);
            Scanner scanner2 = new Scanner(System.in);
            System.out.print("コマンド選択");
            int choice2 = scanner2.nextInt();
            switch(choice2){
                case 0:
                    showParty(); //もどるコマンド
                    break;
                default:
                    System.out.println("無効なコマンドです。");
                    showPokemon(choicePokemon);

            }
        }else{
            String[] commandList = {"こうかん", "もどる"};
            displayCommand(commandList);
            Scanner scanner2 = new Scanner(System.in);
            System.out.print("コマンド選択");
            int choice2 = scanner2.nextInt();
            switch(choice2){
                case 0:
                    exchange(this.party[choice2]);
                    break;
                case 1:
                    showParty(); //もどるコマンド
                    break;
                default:
                    System.out.println("無効なコマンドです。");
                    showParty();
            }
        }
    }

    public void exchange(Pokemon choicePokemon){
        if(this.battlePokemon.abnCon == 7){ //ひんしの時
        }else{
        System.out.println(this.battlePokemon + " 戻れ！");
        }
        this.battlePokemon = choicePokemon;
        System.out.println("ゆけっ!" + this.battlePokemon + "!");
    }
    
    public void runAway(){
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
                choiceCommand(); //もどるコマンド
                break;
            default:
                System.out.println("無効なコマンドです。");
                runAway();
        }
    }

    @Override
    void abnCon(){
        switch(battlePokemon.abnCon){
            case 1://まひ
                battlePokemon.sReal = (int)(battlePokemon.sReal / 2);
                int rand1 = randomGenerator.nextInt(99) + 1;
                if(rand1 < 25){
                    //行動不能　後で実装
                    System.out.println(battlePokemon.pokemonName + "は からだ がしびれて うごけない!");
                }
                break;
            case 2://こおり
                int rand2 = randomGenerator.nextInt(99) + 1;
                if(rand2 < 20){
                    System.out.println(battlePokemon.pokemonName + "のこおりがとけた");
                    battlePokemon.abnCon = 0;
                 }else{
                     //行動不能　後で実装
                     System.out.println(battlePokemon.pokemonName + "は こおってしまってうごけない");
                  }
                break;
            case 3://やけど
                //物理技のダメージ1/2 useTechniqueメソッドで実装
                battlePokemon.damaged((int)(battlePokemon.maxHP / 8));
                break;
            case 4://どく
                battlePokemon.damaged((int)(battlePokemon.maxHP / 8));
                break;
            case 5://もうどく
                battlePokemon.damaged((int)(battlePokemon.maxHP * battlePokemon.elaTurn / 16)); //経過ターン数を数える部分を後で作成する
                battlePokemon.elaTurn ++;
                break;
            case 6://ねむり
                if(battlePokemon.sleepCount == 0){
                battlePokemon.sleepCount = randomGenerator.nextInt(2) + 2; //ねむりカウント
                }
                if(battlePokemon.sleepCount == 0){
                    battlePokemon.abnCon = 0;
                    System.out.println(battlePokemon.pokemonName + "はめをさました!");
                }else{
                    //行動不能　後で実装
                    System.out.println(battlePokemon.pokemonName + "はぐうぐうねむっている");
                    battlePokemon.sleepCount -= 1;
                }
                break;
            case 7://ひんし ポケモンを場に出せなくなる
                this.numRemPokemon --;
                if(this.numRemPokemon == 0){
                    System.out.println(this.trainerName + "の てもとには たたかえる ポケモンが いない!");
                    System.out.println("…… …… ……");
                    System.out.println("めのまえが まっくらに なった!");
                    System.exit(0);
                }
                showParty();
                break;
        }
    }

    public void displayCommand(Object[] commandList){
        int index = 0;
        for(Object item: commandList){
            if(item instanceof String){
                System.out.println(index + ": " + item);
            } else if(item instanceof Pokemon){
                System.out.println(index + ": " + ((Pokemon)item).pokemonName);
            } else if(item instanceof Technique){
                System.out.println(index + ": " + ((Technique)item).techniqueName);
            } else {
                System.out.println(index + ": " + item.toString());
            }
            index++;
        }
        System.out.println("もどる");
    }
}
