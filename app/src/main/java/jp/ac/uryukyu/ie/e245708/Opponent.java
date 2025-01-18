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

    //@Override
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
                    if(battlePokemon.abnCon == "やけど"){ //やけど時ダメージ1/2
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

    public void exchange() {
        boolean found = false;
        while(!found){
            int rand = randomGenerator.nextInt(party.length);
            if(this.party[rand].abnCon != "ひんし"){  //ひんしではない時
                this.battlePokemon = party[rand];
                System.out.println(this.trainerName + "は" + this.battlePokemon.pokemonName + "を くりだした!");
                found = true;
            }
        }

    }

} 