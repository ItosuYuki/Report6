package jp.ac.uryukyu.ie.e245708;

import java.util.Random;

public class Opponent extends Trainer{
    //コンストラクタ
    Opponent(String trainerName, Pokemon[] party){
        int rand = randomGenerator.nextInt(party.length);
        this.trainerName = trainerName;
        this.party = party;
        this.battlePokemon = party[rand];
        this.numRemPokemon = party.length;
    }
    
    Random randomGenerator = new Random();

    @Override
    public Technique choiceTechnique(){
        int damage = 0;
        Technique choiceTechnique = battlePokemon.techniques[0];
        for(Technique technique: battlePokemon.techniques){
            double typeMachCom = 1; //タイプ一致補正
            for(String type: battlePokemon.types){
                if(technique.type == type){
                    typeMachCom = 1.5;
                }
            }
            switch(technique.cla){
                case "物理":
                    int damage1 = (int)(technique.pwr * battlePokemon.aReal * typeMachCom);
                    if(battlePokemon.abnCon == 3){ //やけど時ダメージ1/2
                        damage1 = damage1 / 2;
                    }
                    if(damage1 >= damage){
                        damage = damage1;
                        choiceTechnique = technique;
                    }
                    break;
                case "特殊":
                    int damage2 = (int)(technique.pwr * battlePokemon.cReal * typeMachCom);
                    if(damage2 >= damage){
                        damage = damage2;
                        choiceTechnique = technique;
                    }
                    break;
            }
        }   
        return choiceTechnique;
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
                    System.out.println(this.trainerName + "との しょうぶに かった!");
                    System.exit(0);
                }
                boolean found = false;
                while(!found){
                    int rand = randomGenerator.nextInt(party.length);
                    if(this.party[rand].abnCon != 7){  //ひんしではない時
                        this.battlePokemon = party[rand];
                        System.out.println(this.trainerName + "は" + this.battlePokemon + "を くりだした!");
                        found = true;
                    }
                }
                break;
    }
}

} 