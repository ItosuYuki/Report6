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
    public Technique choiceTechnique(Pokemon target){
        int damage = 0;
        Technique choiceTechnique = battlePokemon.techniques[0];
        for(Technique technique: battlePokemon.techniques){
            int damage1 = battlePokemon.calcDamage(technique, target);
            if(damage1 >= damage){
                damage = damage1;
                choiceTechnique = technique;
            }
        }
        if(battlePokemon.outrageTurn > 0){
            return battlePokemon.lastUsedTechnique != null
                ? battlePokemon.lastUsedTechnique
                : battlePokemon.techniques[0];
        }else{
            return choiceTechnique;
        }
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