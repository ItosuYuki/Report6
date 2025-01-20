package jp.ac.uryukyu.ie.e245708;
import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Player extends Trainer{
    Scanner scanner = new Scanner(System.in);

    //コンストラクタ
    Player(String trainerName, Pokemon[] party){
        this.trainerName = trainerName;
        this.party = party;
        this.battlePokemon = party[0];
        this.numRemPokemon = party.length;
    }

    /**
     * コマンドを選択するメソッド。
     * たたかう -> ポケモンの技を選択する
     * ポケモン -> 手持ちポケモンを表示する
     *  にげる  -> 降参するか選択する
     * 
     * @return たたかう、ポケモン、にげるのいずれか
     */
    public String choiceCommand() {
        System.out.println(this.battlePokemon.pokemonName + "は どうする？");
        String[] commandList = new String[]{"たたかう", "ポケモン", "にげる"};
        int index = 0;
        for(String command: commandList){
            System.out.println(index + ": " + command);
            index ++;
        }
        int choice = -1;
        boolean validInput = false;
        while (!validInput){
            try{
                choice = scanner.nextInt();
                if(0 <= choice && choice <= commandList.length - 1) {
                    validInput = true;
                }else{
                    System.out.println("無効なコマンドです。");
                }
            }catch (InputMismatchException e) {
                System.out.println("無効なコマンドです。");
                scanner.next();
               }   
        }
        return commandList[choice];
    }
    
    /**
     * 技を選択するメソッド。
     * もどるを選択するとchoiceCommandまでもどる
     * 
     * @return 選択した技のインデックス
     */
    public int choiceTechnique(){ //たたかう
        System.out.println(this.battlePokemon.pokemonName + "は どうする？");
        displayCommand(battlePokemon.techniques);
        int choice = -1;
        boolean validInput = false;
        while (!validInput){
            try{
                choice = scanner.nextInt();
                if(0 <= choice && choice < battlePokemon.techniques.length){
                    if(battlePokemon.techniques[choice].PP == 0){
                        System.out.println("PPがたりません!");
                    }else if(battlePokemon.techniques[choice].effect == "100%の確率で相手をひるませる。ただし、出た最初のターンしか成功しない。"
                    && battlePokemon.elaTurn != 1){
                        System.out.println("その技は 使用できません!");
                    }else{
                        validInput = true;
                    }
                }else if(choice == battlePokemon.techniques.length){ //もどる
                    validInput = true;
                }else {
                    System.out.println("無効なコマンドです。");
                }
            }catch (InputMismatchException e) {
                System.out.println("無効なコマンドです。");
                scanner.next();
            }
        }
        return choice;
    }

    /**
     * 手持ちポケモンを表示するメソッド。
     * もどるを選択するとchoiceCommandまでもどる。
     * 
     * @return 選択したポケモンのインデックス
     */
    public int showParty(){ //ポケモン
        if(this.battlePokemon.abnCon != "ひんし"){
            System.out.println(this.battlePokemon.pokemonName + "は どうする？");
        }
        displayCommand(this.party);
        int choice = -1;
        boolean validInput = false;
        while (!validInput){
            try{
                choice = scanner.nextInt();
                if(0 <= choice && choice <= this.party.length){
                    validInput = true;
                }else{
                    System.out.println("無効なコマンドです。");
                }
            }catch (InputMismatchException e) {
                System.out.println("無効なコマンドです。");
                scanner.next();
            }
        }
        return choice;
    }

    /**
     * 選んだポケモンのステータスを表示するメソッド。
     * 場のポケモンといれかえることも可能。
     * もどるを選択するとchoiceCommandまでもどる。
     * 
     * @param choicePokemon 選んだポケモン
     * @return いれかえる、もどるのいずれか
     */
    public String showPokemon(Pokemon choicePokemon) {
        System.out.println(choicePokemon.pokemonName + choicePokemon.abnCon);
        System.out.println("タイプ :" + Arrays.toString(choicePokemon.types));
        System.out.print("HP :" + choicePokemon.hReal + "/" + choicePokemon.maxHP + "  こうげき :" + choicePokemon.aReal + "  ぼうぎょ :" + choicePokemon.bReal);
        System.out.print("とくこう :" + choicePokemon.cReal + "  とくぼう :" + choicePokemon.dReal + "  すばやさ :" + choicePokemon.sReal);
        for(Technique tecnique:choicePokemon.techniques){
            System.out.println(tecnique.techniqueName + "PP: " + tecnique.PP + "/" + tecnique.maxPP);
        }
        String[] commandList;
        int choice = -1;
        boolean validInput = false;
        while (!validInput) {
            if(this.battlePokemon == choicePokemon || choicePokemon.abnCon.equals("ひんし")){
                commandList = new String[]{"もどる"};
                displayCommand(commandList);
                try{
                    choice = scanner.nextInt();
                    if(choice == 0){
                        validInput = true;
                        return "もどる";
                    }else{
                        System.out.println("無効なコマンドです。");
                    }
                }catch (InputMismatchException e) {
                    System.out.println("無効なコマンドです。");
                    scanner.next();
                }
            }else{
                commandList = new String[]{"いれかえる", "もどる"};
                displayCommand(commandList);
                try{
                    choice = scanner.nextInt();
                    if(choice == 0){
                        validInput = true;
                        return "いれかえる";
                    }else if (choice == 1){
                        validInput = true;
                        return"もどる";
                    }else{
                        System.out.println("無効なコマンドです。");
                    }
                }catch (InputMismatchException e) {
                    System.out.println("無効なコマンドです。");
                    scanner.next();
                }
            }
        }
        return "";
    }

    /**
     * ポケモンをいれかえるメソッド。
     * 
     * @param choicePokemon いれかえるポケモン
     */
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
    
    /**
     * 降参するメソッド。
     * はいを選択するとゲームが終了する。
     * いいえを押すとchoiceCommandまでもどる。
     * 
     * @return はい、いいえのいずれか
     */
    public String runAway(){ 
        System.out.println("勝負を あきらめて 降参しますか？");
        displayCommand(new String[]{"はい", "いいえ"});
        int choice = -1;
        boolean validInput = false;
        while (!validInput){
            try{
                choice = scanner.nextInt();
                if(choice == 0){
                    validInput = true;
                    return "はい";
                }else if(choice == 1){
                    return "いいえ";
                }else{
                    System.out.println("無効なコマンドです。");
                }
            }catch (InputMismatchException e) {
                System.out.println("無効なコマンドです。");
                scanner.next();
            }   
        }
        return "";
    }

    /**
     * 選択肢を表示するメソッド。
     * コマンドやポケモン、技などを表示する。
     * 
     * @param commandList 表示するコマンド
     */
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
