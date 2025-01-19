package jp.ac.uryukyu.ie.e245708;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Pokemon {
    String pokemonName;                                 //ポケモン名
    String[] types;                                      //タイプ
    int level;                                          //レベル(1~100)
    int maxHP;                                          //最大HP
    //h->HP, a->攻撃, b->防御, c->特攻, d->特防, s->素早さ, ac->命中率, ev->回避率
    int hRace, aRace, bRace, cRace, dRace, sRace,       //種族値
        hEffo, aEffo, bEffo, cEffo, dEffo, sEffo,       //努力値(0~252)
        hIndi, aIndi, bIndi, cIndi, dIndi, sIndi,       //個体値(0~31)
        hReal, aReal, bReal, cReal, dReal, sReal;       //実数値
    int aRank = 0, bRank = 0,  cRank = 0, dRank = 0,
        sRank = 0, acRank = 0, evRank = 0;              //ランク補正
    Technique[] techniques;                             //技リスト
    String nature;                                      //性格
    String abnCon = "";                                 //状態異常
    int poisonTurn = 0;                                 //もうどく経過ターン数
    int sleepCount = 0;                                 //ねむりカウント
    boolean act = false;                                 //行動可能or不可能
    int conTurn = 0;                                    //混乱ターン
    boolean beforeAct = true;                           //行動前
    boolean afterAct = false;                           //行動後
    boolean leechSeed = false;                          //やどりぎ状態
    int outrageTurn = 0;                                //げきりんターン
    Technique lastUsedTechnique;                        //最後に使用した技
    int elaTurn = 1;                                    //登場してからの経過ターン
    //乱数生成
    Random randomGenerator = new Random();

    TypeMatchupCalculator calculator = new TypeMatchupCalculator();

    void damaged(int damage){
        this.hReal -= damage;
        if(hReal <= 0){
            this.hReal = 0;
            this.abnCon = "ひんし";
            System.out.println(pokemonName + "は たおれた！");
        }
    }

    void recoverd(int recover){
        this.hReal += recover;
        if(hReal >= maxHP){
            this.hReal = maxHP;
        }
    }

    public void useTechnique(Technique choiceTechnique, Pokemon target) {
        this.abnCon();
        if(choiceTechnique.effect == "相手に与えたダメージの33%を自分も受ける。10%の確率で相手を『やけど』状態にする。自分が『こおり』状態の時でも使う事ができ、使うと『こおり』状態が治る。"){
            this.abnCon = "";
        }
        if(this.conTurn > 0){ //混乱時の処理
            conTurn --;
            if(conTurn == 0){
                System.out.println(pokemonName + "のこんらんがとけた!");
            }else{
                double pwrRand = (randomGenerator.nextInt(15) + 85) / 100.0;
                int rand = randomGenerator.nextInt(2);
                System.out.println(this.pokemonName + "はこんらんしている!");
                if(rand < 1){
                    int damage = (int)((int)((int)((int)(this.level * 2 / 5 + 2) * choiceTechnique.pwr * this.aReal / this.bReal) / 50 + 2) * pwrRand);
                    if(abnCon ==  "やけど"){ //やけど時ダメージ1/2
                        damage = damage / 2;
                    }
                    this.damaged(damage);
                    System.out.println("わけも わからず じぶんを こうげきした!");
                }
            }
        }
        if(this.act){ //行動可能か判定
            System.out.println(this.pokemonName + "の" + choiceTechnique.techniqueName + "!");
            if(isHit(choiceTechnique.acc, this.acRank, target.evRank)){
                int damage = calcDamage(choiceTechnique, target);
                target.damaged(damage);
                choiceTechnique.PP -= 1;
                actEffect(choiceTechnique.effect, damage, target);
                if(choiceTechnique.type == "ほのお" && target.abnCon == "こおり"){
                    target.abnCon = "";
                    System.out.println(target.pokemonName + "のこおりがとけた");
                }
                lastUsedTechnique = choiceTechnique;
            }else{
                System.out.println(target.pokemonName + "には あたらなかった!");
            }
            
        }
        this.act = false;
        this.elaTurn ++;
    }

    public int calcDamage(Technique technique, Pokemon target) {
        double typeMatcCom = 1; //タイプ一致補正
        double matchup = calculator.calcMatchup(technique.type, target.types);
        for(String type: this.types){
            if(technique.type.equals(type)){
                typeMatcCom = 1.5;
            }
        }
        int damage = 0;
        switch(technique.cla){
            case "物理":
                double pwrRand1 = (randomGenerator.nextInt(15) + 85) / 100.0;
                damage = (int)((int)((int)((int)(this.level * 2 / 5 + 2) * technique.pwr * this.aReal / target.bReal) / 50 + 2) * pwrRand1 * typeMatcCom * matchup);
                if(abnCon == "やけど"){ //やけど時ダメージ1/2
                    damage = damage / 2;
                }
                break;
            case "特殊":
                double pwrRand2 = (randomGenerator.nextInt(15) + 85) / 100.0;
                damage = (int)((int)((int)((int)(this.level * 2 / 5 + 2) * technique.pwr * this.cReal / target.dReal) / 50 + 2) * pwrRand2 * typeMatcCom * matchup);
                break;  
        }
        if(matchup > 1.0){
            System.out.println("効果はバツグンだ!");
        }else if(0 < matchup && matchup < 1.0){
            System.out.println("効果は今ひとつのようだ");
        }else if(matchup == 0){
            System.out.println("効果がないようだ...");
        }
        if(target.hReal <= damage) {
            damage = target.hReal;
        }
        return damage;
    }

    //追加効果
    public void actEffect(String effect, int damage, Pokemon target) {
        int rand = randomGenerator.nextInt(99);
        int anoRand = randomGenerator.nextInt(99);
        switch(effect){
            case "相手に与えたダメージの33%を自分も受ける。10%の確率で相手を『まひ』状態にする。":
                this.damaged((int)(damage * 0.33));
                if(rand >= 90){
                    target.abnCon = "まひ";
                }
                break;
            case "30%の確率で相手の『ぼうぎょ』ランクを1段階下げる。":
                if(rand >= 70){ 
                    target.bRankCalc(-1);
                }
                break;
            case "100%の確率で相手をひるませる。ただし、出た最初のターンしか成功しない。":
                //最初のターンしか成功しない　useTechniqueメソッドで実装
                if(target.act == true){
                    target.act = false;
                    System.out.println(target.pokemonName + "は ひるんで うごけない!");
                    }
                break;
            case "30%の確率で相手を『まひ』状態にする。":
                if(rand >= 70){
                    target.abnCon = "まひ";
                    System.out.println(target.pokemonName + "は まひに なった!");
                }
                break;
            case "20%の確率で相手の『ぼうぎょ』ランクを1段階下げる。":
                if(rand >= 80){ 
                    target.bRankCalc(-1);
                }
                break;
            case "10%の確率で相手を『こおり』状態にする。":
                if(rand >= 90){
                    target.abnCon = "こおり";
                    System.out.println(target.pokemonName + "は こおりついた!");
                }
                break;
            case "10%の確率で相手を『まひ』状態にする。":
                if(rand >= 90){
                    target.abnCon = "まひ";
                    System.out.println(target.pokemonName + "は まひに なった!");
                }
                break;
            case "攻撃後、100%の確率で自分の『とくこう』ランクが2段階下がる。":
                this.cRankCalc(-2);
                break;
            case "相手に与えたダメージの33%を自分も受ける。":
                this.damaged((int)(damage * 0.33));
                break;
            case "20%の確率で相手をひるませる。":
                if(rand >= 80){
                    if(target.act == true){
                    target.act = false;
                    System.out.println(target.pokemonName + "は ひるんで うごけない!");
                    }
                }
                break;
            case "使用後、毎ターン、相手のHPを最大HPの1/8ずつ減らし、その分自分のHPを回復させる。自分は交代しても効果が引き継ぐ。草タイプのポケモンには無効。":
                if(Arrays.asList(target.types).contains("くさ")){
                    System.out.println("しかし こうかが なかったようだ......");
                }else{
                    target.leechSeed = true;
                    System.out.println("あいての" + target.pokemonName + "に たねを うえつけた!");
                }
                break;
            case "10%の確率で相手を『こおり』状態にするか、ひるませる。":
                if(rand >= 90){
                    target.abnCon = "こおり";
                    System.out.println(target.pokemonName + "は こおりついた!");
                }
                if(anoRand >= 90){
                    if(target.act == true){
                    target.act = false;
                    System.out.println(target.pokemonName + "は ひるんで うごけない!");
                    }
                }
                break;
            case "2～3ターン連続で攻撃し、その後自分は1～4ターンの間『こんらん』状態になる。":
                if(this.outrageTurn == 0){
                    outrageTurn = randomGenerator.nextInt(1) + 2;
                }else{
                    outrageTurn --;
                }
                if(outrageTurn == 0){
                    this.conTurn = randomGenerator.nextInt(3) + 1;
                    System.out.println(this.pokemonName + "は つかれはてて こんらんした!");
                }
                break;
            case "相手に与えたダメージの33%を自分も受ける。10%の確率で相手を『やけど』状態にする。自分が『こおり』状態の時でも使う事ができ、使うと『こおり』状態が治る。":
                this.damaged((int)(damage * 0.33));
                    if(rand >= 90){
                        target.abnCon = "やけど";
                        System.out.println(target.pokemonName + "は やけどを 負った!");
                    }
                break;
            case "攻撃後、自分の『ぼうぎょ』『とくぼう』ランクが1段階ずつ下がる。":
                this.bRankCalc(-1);
                this.dRankCalc(-1);
                break;
            case "相手に与えたダメージの1/4を自分も受ける。":
                this.damaged((int)(damage / 4));
                break;
        }
    }

    void abnCon(){
        switch(this.abnCon){
            case "まひ":
                if(beforeAct == true){
                    this.sReal = (int)(this.sReal / 2);
                    int rand1 = randomGenerator.nextInt(99) + 1;
                    if(rand1 < 25){
                        this.act = false; //行動不能
                        System.out.println(this.pokemonName + "は からだ がしびれて うごけない!");
                    }
                }
                break;
            case "こおり":
                if(beforeAct == true){
                    int rand2 = randomGenerator.nextInt(99) + 1;
                    if(rand2 < 20){
                        System.out.println(this.pokemonName + "のこおりがとけた");
                        this.abnCon = "";
                    }else{
                        this.act = false;//行動不能
                        System.out.println(this.pokemonName + "は こおってしまってうごけない");
                    }
                }
                break;
            case "やけど":
                if(afterAct == true){
                    //物理技のダメージ1/2 useTechniqueメソッドで実装
                    this.damaged((int)(this.maxHP / 8));
                    System.out.println(this.pokemonName + "は やけどの ダメージを 受けた!");
                }
                break;
            case "どく":
                if(afterAct == true){
                    this.damaged((int)(this.maxHP / 8));
                }
                break;
            case "もうどく":
                if(afterAct == true){
                    this.damaged((int)(this.maxHP * this.poisonTurn / 16)); //経過ターン数を数える部分を後で作成する
                    this.poisonTurn ++;
                }
                break;
            case "ねむり":
                if(beforeAct == true){
                    if(this.sleepCount == 0){
                    this.sleepCount = randomGenerator.nextInt(2) + 2; //ねむりカウント
                    }
                    if(this.sleepCount == 0){
                        this.abnCon = "";
                        System.out.println(this.pokemonName + "はめをさました!");
                    }else{
                        this.act = false;//行動不能
                        System.out.println(this.pokemonName + "はぐうぐうねむっている");
                        this.sleepCount -= 1;
                    }
                }
                break;
        }
    }

    private int calcRank(int rank, int value) {
        rank += value;
        if (rank > 6) {
            rank = 6;
        } else if (rank < -6) {
            rank = -6;
        }
        return rank;
    }
    
    private void printRankChangeMessage(String statName, String pokemonName, int value) {
        if (value == 1) {
            System.out.println(pokemonName + "の " + statName + "が あがった");
        } else if (value == 2) {
            System.out.println(pokemonName + "の " + statName + "が ぐーんと あがった");
        } else if (value >= 3 && value < 12) {
            System.out.println(pokemonName + "の " + statName + "が ぐぐーんと あがった");
        } else if (value >= 12) {
            System.out.println(pokemonName + "の " + statName + "が さいだいまであがった");
        } else if (value == -1) {
            System.out.println(pokemonName + "の " + statName + "が さがった");
        } else if (value == -2) {
            System.out.println(pokemonName + "の " + statName + "が がくっと さがった");
        } else if (value < -3) {
            System.out.println(pokemonName + "の " + statName + "が がくーんと さがった");
        }
    }
    
    public void aRankCalc(int value) {
        this.aRank = calcRank(this.aRank, value);
        calculateRealValue(aRank, aReal);
        printRankChangeMessage("こうげき", this.pokemonName, value);
    }
    
    public void bRankCalc(int value) {
        this.bRank = calcRank(this.bRank, value);
        calculateRealValue(bRank, bReal);
        printRankChangeMessage("ぼうぎょ", this.pokemonName, value);
    }

    public void cRankCalc(int value) {
        this.cRank = calcRank(this.cRank, value);
        calculateRealValue(cRank, cReal);
        printRankChangeMessage("とくこう", this.pokemonName, value);
    }

    public void dRankCalc(int value) {
        this.dRank = calcRank(this.dRank, value);
        calculateRealValue(dRank, dReal);
        printRankChangeMessage("とくぼう", this.pokemonName, value);
    }

    public void sRankCalc(int value) {
        this.sRank = calcRank(this.sRank, value);
        calculateRealValue(sRank, sReal);
        printRankChangeMessage("すばやさ", this.pokemonName, value);
    }

    public void acRankCalc(int value) {
        this.acRank = calcRank(this.acRank, value);
        printRankChangeMessage("めいちゅうりつ", this.pokemonName, value);
    }

    public void evRankCalc(int value) {
        this.evRank = calcRank(this.evRank, value);
        printRankChangeMessage("かいひりつ", this.pokemonName, value);
    }
    

    //実数値計算　ポケモン行動後に実行する
    public void rankCom() {
        this.aReal = calculateRealValue(aRank, aReal);
        this.bReal = calculateRealValue(bRank, bReal);
        this.cReal = calculateRealValue(cRank, cReal);
        this.dReal = calculateRealValue(dRank, dReal);
        this.sReal = calculateRealValue(sRank, sReal);
    }
    
    public int calculateRealValue(int rank, double real) {
        if (rank > 0) {
            return (int)(real * (2 + rank) / 2);
        } else if (rank < 0) {
            return (int)(real * 2 / (2 - rank));
        }
        return (int)real;  // rankが0の場合、変更なし
    }

    //命中率計算
    public boolean isHit(int acc, int acRank, int evRank){
        double accCom = 1;
        if(acRank - evRank > 0){
            accCom = (3.0 + acRank - evRank) / 3.0;}
        else if(acRank - evRank < 0){
            accCom = 3.0 / (3.0 - acRank + evRank);
        }
        int result = (int)(acc * accCom);
        if(result > 100){ //上限値を100に設定
            result = 100;
        }
        double accRand = randomGenerator.nextInt(99);
        boolean hit = false;
        if(result > accRand){
            hit = true;
        }
        return hit;
    }

    public class TypeMatchupCalculator {

    // タイプ相性を管理するマップ
    private static final Map<String, Map<String, Double>> typeEffectiveness = new HashMap<>();

    // 静的初期化ブロックで相性データをセットアップ
    static {
        // 効果は「ばつぐん！」の相性（倍率 2.0）
        addEffectiveness("ほのお", new String[]{"くさ", "こおり", "むし", "はがね"}, 2.0);
        addEffectiveness("みず", new String[]{"ほのお", "じめん", "いわ"}, 2.0);
        addEffectiveness("でんき", new String[]{"みず", "ひこう"}, 2.0);
        addEffectiveness("くさ", new String[]{"みず", "じめん", "いわ"}, 2.0);
        addEffectiveness("こおり", new String[]{"くさ", "じめん", "ひこう", "ドラゴン"}, 2.0);
        addEffectiveness("かくとう", new String[]{"ノーマル", "こおり", "いわ", "あく", "はがね"}, 2.0);
        addEffectiveness("どく", new String[]{"くさ", "フェアリー"}, 2.0);
        addEffectiveness("じめん", new String[]{"ほのお", "でんき", "いわ", "はがね"}, 2.0);
        addEffectiveness("ひこう", new String[]{"くさ", "かくとう", "むし"}, 2.0);
        addEffectiveness("エスパー", new String[]{"かくとう", "どく"}, 2.0);
        addEffectiveness("むし", new String[]{"くさ", "エスパー", "あく"}, 2.0);
        addEffectiveness("いわ", new String[]{"ほのお", "こおり", "ひこう", "むし"}, 2.0);
        addEffectiveness("ゴースト", new String[]{"エスパー", "ゴースト"}, 2.0);
        addEffectiveness("ドラゴン", new String[]{"ドラゴン"}, 2.0);
        addEffectiveness("あく", new String[]{"エスパー", "ゴースト"}, 2.0);
        addEffectiveness("はがね", new String[]{"こおり", "いわ", "フェアリー"}, 2.0);
        addEffectiveness("フェアリー", new String[]{"かくとう", "ドラゴン", "あく"}, 2.0);

        // 効果は「いまひとつ」の相性（倍率 0.5）
        addEffectiveness("ノーマル", new String[]{"いわ", "はがね"}, 0.5);
        addEffectiveness("ほのお", new String[]{"ほのお", "みず", "いわ", "ドラゴン"}, 0.5);
        addEffectiveness("みず", new String[]{"みず", "くさ"}, 0.5);
        addEffectiveness("でんき", new String[]{"でんき", "くさ", "ドラゴン"}, 0.5);
        addEffectiveness("くさ", new String[]{"ほのお", "くさ", "どく", "ひこう", "むし", "ドラゴン", "はがね"}, 0.5);
        addEffectiveness("こおり", new String[]{"ほのお", "みず", "こおり", "はがね"}, 0.5);
        addEffectiveness("かくとう", new String[]{"どく", "ひこう", "エスパー", "むし", "フェアリー"}, 0.5);
        addEffectiveness("どく", new String[]{"どく", "じめん", "いわ", "ゴースト"}, 0.5);
        addEffectiveness("じめん", new String[]{"くさ", "むし"}, 0.5);
        addEffectiveness("ひこう", new String[]{"でんき", "いわ", "はがね"}, 0.5);
        addEffectiveness("エスパー", new String[]{"エスパー", "はがね"}, 0.5);
        addEffectiveness("むし", new String[]{"ほのお", "かくとう", "どく", "ひこう", "ゴースト", "はがね", "フェアリー"}, 0.5);
        addEffectiveness("いわ", new String[]{"かくとう", "じめん", "はがね"}, 0.5);
        addEffectiveness("ゴースト", new String[]{"あく"}, 0.5);
        addEffectiveness("ドラゴン", new String[]{"はがね"}, 0.5);
        addEffectiveness("あく", new String[]{"かくとう", "あく", "フェアリー"}, 0.5);
        addEffectiveness("はがね", new String[]{"ほのお", "みず", "でんき", "はがね"}, 0.5);
        addEffectiveness("フェアリー", new String[]{"ほのお", "どく", "はがね"}, 0.5);

        // 効果がない相性（倍率 0.0）
        addEffectiveness("ノーマル", new String[]{"ゴースト"}, 0.0);
        addEffectiveness("でんき", new String[]{"じめん"}, 0.0);
        addEffectiveness("かくとう", new String[]{"ゴースト"}, 0.0);
        addEffectiveness("どく", new String[]{"はがね"}, 0.0);
        addEffectiveness("じめん", new String[]{"ひこう"}, 0.0);
        addEffectiveness("エスパー", new String[]{"あく"}, 0.0);
        addEffectiveness("ゴースト", new String[]{"ノーマル"}, 0.0);
        addEffectiveness("ドラゴン", new String[]{"フェアリー"}, 0.0);
    }

    // 効果をマップに追加するヘルパーメソッド
    private static void addEffectiveness(String techniqueType, String[] targetTypes, double multiplier) {
        typeEffectiveness.putIfAbsent(techniqueType, new HashMap<>());
        Map<String, Double> targetMap = typeEffectiveness.get(techniqueType);
        for (String targetType : targetTypes) {
            targetMap.put(targetType, multiplier);
        }
    }

    // 相性倍率を計算するメソッド
    public double calcMatchup(String techniqueType, String[] targetTypes) {
        double mag = 1.0; // 初期倍率
        for (String targetType : targetTypes) {
            double multiplier = typeEffectiveness
                    .getOrDefault(techniqueType, Collections.emptyMap())
                    .getOrDefault(targetType, 1.0); // デフォルト倍率は 1.0
            mag *= multiplier;
        }
        return mag;
    }
}

    //コンストラクタ
    Pokemon(
        String pokemonName,
        String[] types,
        int level,
        int hRace, int aRace, int bRace, int cRace, int dRace, int sRace,
        int hEffo, int aEffo, int bEffo, int cEffo, int dEffo, int sEffo,
        int hIndi, int aIndi, int bIndi, int cIndi, int dIndi, int sIndi,
        Technique[] tecniques, //技リスト
        String nature //性格
    ){
        this.pokemonName = pokemonName;
        this.types = types;
        this.level = level;
        this.hRace = hRace;
        this.aRace = aRace;
        this.bRace = bRace;
        this.cRace = cRace;
        this.dRace = dRace;
        this.sRace = sRace;
        this.hEffo = hEffo;
        this.aEffo = aEffo;
        this.bEffo = bEffo;
        this.cEffo = cEffo;
        this.dEffo = dEffo;
        this.sEffo = sEffo;
        this.hIndi = hIndi;
        this.aIndi = aIndi;
        this.bIndi = bIndi;
        this.cIndi = cIndi;
        this.dIndi = dIndi;
        this.sIndi = sIndi;
        this.techniques = tecniques;
        this.nature = nature;
        double aNat = 1.0, bNat = 1.0, cNat = 1.0, dNat = 1.0, sNat = 1.0; //性格補正値
        switch(nature){ //性格補正
            case "さみしがり": aNat = 1.1; cNat = 0.9; break;
            case "いじっぱり": aNat = 1.1; bNat = 0.9; break;
            case "やんちゃ": aNat = 1.1; cNat = 0.9; break;
            case "ゆうかん": aNat = 1.1; dNat = 0.9; break;
            case "ずぶとい": bNat = 1.1; aNat = 0.9; break;
            case "わんぱく": bNat = 1.1; cNat = 0.9; break;
            case "のうてんき": bNat = 1.1; dNat = 0.9; break;
            case "のんき": bNat = 1.1; sNat = 0.9; break;
            case "ひかえめ": cNat = 1.1; aNat = 0.9; break;
            case "おっとり": cNat = 1.1; bNat = 0.9; break;
            case "うっかりや": cNat = 1.1; dNat = 0.9; break;
            case "れいせい": cNat = 1.1; sNat = 0.9; break;
            case "おだやか": dNat = 1.1; aNat = 0.9; break;
            case "おとなしい": dNat = 1.1; bNat = 0.9; break;
            case "しんちょう": dNat = 1.1; cNat = 0.9; break;
            case "なまいき": dNat = 1.1; sNat = 0.9; break;
            case "おくびょう": sNat = 1.1; aNat = 0.9; break;
            case "せっかち": sNat = 1.1; bNat = 0.9; break;
            case "ようき": sNat = 1.1; cNat = 0.9;  break;
            case "むじゃき": sNat = 1.1; dNat = 0.9; break;
        };
        this.maxHP = (hRace * 2 + hIndi + hEffo / 4) * level / 100 + level + 10;
        this.hReal = maxHP;
        this.aReal = (int)((int)((int)(this.aRace + 2 + aIndi + aEffo / 4) * level / 100 + 5) * aNat);
        this.bReal = (int)((int)((int)(bRace + 2 + bIndi + bEffo / 4) * level / 100 + 5) * bNat);
        this.cReal = (int)((int)((int)(cRace + 2 + cIndi + cEffo / 4) * level / 100 + 5) * cNat);
        this.dReal = (int)((int)((int)(dRace + 2 + dIndi + dEffo / 4) * level / 100 + 5) * dNat);
        this.sReal = (int)((int)((int)(sRace + 2 + sIndi + sEffo / 4) * level / 100 + 5) * sNat);
    }
}
