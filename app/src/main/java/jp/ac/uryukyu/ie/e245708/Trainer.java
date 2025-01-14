package jp.ac.uryukyu.ie.e245708;

import java.util.Random;
import java.util.Scanner;

public abstract class Trainer {
    String trainerName;    //トレーナー名
    Pokemon[] party;       //手持ちポケモン
    Pokemon battlePokemon; //戦わせるポケモン
    int numRemPokemon;  //残りポケモン数

    //乱数生成
    Random randomGenerator = new Random();
/*  コンストラクタ例
    Trainer(String trainerName, Pokemon[] party){
        this.trainerName = trainerName;
        this.party = party;
        this.battlePokemon = party[0];
        this.numRemPokemon = party.length;
    }
*/
    abstract Technique choiceTechnique();
    abstract void abnCon();
}
