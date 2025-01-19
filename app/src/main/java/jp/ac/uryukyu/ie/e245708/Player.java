package jp.ac.uryukyu.ie.e245708;
import java.util.Scanner;
import java.util.Arrays;

public class Player extends Trainer{
    Scanner scanner = new Scanner(System.in);

    //コンストラクタ
    Player(String trainerName, Pokemon[] party){
        this.trainerName = trainerName;
        this.party = party;
        this.battlePokemon = party[0];
        this.numRemPokemon = party.length;
    }

    public String choiceCommand() {
        System.out.println(this.battlePokemon.pokemonName + "は どうする？");
        String[] commandList = new String[]{"たたかう", "ポケモン", "にげる"};
        int index = 0;
        for(String command: commandList){
            System.out.println(index + ": " + command);
            index ++;
        }
        int choice = scanner.nextInt();
        if(0 <= choice && choice <= commandList.length - 1) {
            return commandList[choice];
        }else{
            System.out.println("無効なコマンドです。");
            return "";
        }
    }
    

    //@Override
    public int choiceTechnique(){ //たたかう
        System.out.println(this.battlePokemon.pokemonName + "は どうする？");
        displayCommand(battlePokemon.techniques);
        int choice = scanner.nextInt();
        if(0 <= choice && choice < battlePokemon.techniques.length){
            if(battlePokemon.techniques[choice].PP == 0){
                System.out.println("PPがたりません!");
                choice = 5;//技選択に戻る
            }else if(battlePokemon.techniques[choice].effect == "100%の確率で相手をひるませる。ただし、出た最初のターンしか成功しない。"
            && battlePokemon.elaTurn != 1){
             System.out.println("その技は 使用できません!");
             choice = 5;//技選択に戻る
            }
        }else if(choice == battlePokemon.techniques.length){
            //もどる
        }else{
            System.out.println("無効なコマンドです。");//技選択に戻る
        }
        return choice;
    }
    public int showParty(){ //ポケモン
        if(this.battlePokemon.abnCon != "ひんし"){
            System.out.println(this.battlePokemon.pokemonName + "は どうする？");
        }
        displayCommand(this.party);
        int choice1 = scanner.nextInt();
        if(0 <= choice1 && choice1 < this.party.length){
        }else if(choice1 == this.party.length){
            //choiceCommand(); //戻る
        }else{
            System.out.println("無効なコマンドです。"); //戻る
        }
        return choice1;
    }

    public String showPokemon(Pokemon choicePokemon) {
        System.out.println(choicePokemon.pokemonName + choicePokemon.abnCon);
        System.out.println("タイプ :" + Arrays.toString(choicePokemon.types));
        System.out.print("HP :" + choicePokemon.hReal + "/" + choicePokemon.maxHP);
        System.out.print("  こうげき :" + choicePokemon.aReal);
        System.out.println("  ぼうぎょ :" + choicePokemon.bReal);
        System.out.print("とくこう :" + choicePokemon.cReal);
        System.out.print("  とくぼう :" + choicePokemon.dReal);
        System.out.println("  すばやさ :" + choicePokemon.sReal);
        for(Technique tecnique:choicePokemon.techniques){
            System.out.println(tecnique.techniqueName + "PP: " + tecnique.PP + "/" + tecnique.maxPP);
        }
        if(this.battlePokemon == choicePokemon | choicePokemon.abnCon == "ひんし"){
            if(this.battlePokemon.abnCon != "ひんし"){
                System.out.println(this.battlePokemon.pokemonName + "は どうする？");
            }
            String[] commandList = {"もどる"};
            displayCommand(commandList);
            int choice2 = scanner.nextInt();
            switch(choice2){
                case 0:
                    return "もどる";
                default:
                    System.out.println("無効なコマンドです。");
                    //showPokemon(choicePokemon);
                    return "";

            }
        }else{
            if(this.battlePokemon.abnCon != "ひんし"){
                System.out.println(this.battlePokemon.pokemonName + "は どうする？");
            }
            String[] commandList = {"いれかえる", "もどる"};
            displayCommand(commandList);
            int choice2 = scanner.nextInt();
            switch(choice2){
                case 0:
                    return "いれかえる";
                case 1:
                    return "もどる";
                default:
                    System.out.println("無効なコマンドです。");
                    return "";
            }
        }
    }

    public void exchange(Pokemon choicePokemon){
        if(this.battlePokemon.abnCon.equals("ひんし") ){
        }else{
        System.out.println(this.battlePokemon.pokemonName + " 戻れ！");
        }
        this.battlePokemon.conTurn = 0;
        this.battlePokemon = choicePokemon;
        System.out.println("ゆけっ!" + this.battlePokemon.pokemonName + "!");
        this.battlePokemon.act = false;
        this.battlePokemon.elaTurn =0;
    }
    
    public String runAway(){ //にげる
        System.out.println("勝負を あきらめて 降参しますか？");
        displayCommand(new String[]{"はい", "いいえ"});
        int choice = scanner.nextInt();
        switch(choice){
            case 0:
                return "はい";
            case 1:
                return "いいえ";
            default:
                System.out.println("無効なコマンドです。");
                return "";
        }
    }

    public void displayCommand(Object[] commandList){
        int index = 0;
        for(Object item: commandList){
            if(item instanceof String){
                System.out.println(index + ": " + item);
            } else if(item instanceof Pokemon){
                System.out.println(index + ": " + ((Pokemon)item).pokemonName + " " + ((Pokemon)item).abnCon);
            } else if(item instanceof Technique){
                System.out.println(index + ": " + ((Technique)item).techniqueName + " " + ((Technique)item).type + " " + ((Technique)item).PP + "/" + ((Technique)item).maxPP);
            } else {
                System.out.println(index + ": " + item.toString());
            }
            index++;
        }
        if(commandList instanceof String[]){
        }else{
        System.out.println(index + ": もどる");
        }
    }

}
