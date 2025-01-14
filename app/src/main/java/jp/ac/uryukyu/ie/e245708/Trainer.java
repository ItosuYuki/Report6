package jp.ac.uryukyu.ie.e245708;


public class Trainer {
    String trainerName;    //トレーナー名
    Pokemon[] party;       //手持ちポケモン
    Pokemon battlePokemon; //戦わせるポケモン
    int numRemPokemon;  //残りポケモン数
    Trainer(String trainerName, Pokemon[] party){
        this.trainerName = trainerName;
        this.party = party;
        this.battlePokemon = party[0];
        this.numRemPokemon = party.length;
    }
}