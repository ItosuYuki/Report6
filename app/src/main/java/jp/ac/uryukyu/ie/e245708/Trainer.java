package jp.ac.uryukyu.ie.e245708;
import java.util.Random;

public abstract class Trainer {
    String trainerName;    //トレーナー名
    Pokemon[] party;       //手持ちポケモン
    Pokemon battlePokemon; //戦わせるポケモン
    int numRemPokemon;  //残りポケモン数

    //乱数生成
    Random randomGenerator = new Random();
}
