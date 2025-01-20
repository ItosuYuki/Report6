package jp.ac.uryukyu.ie.e245708;

public class Battle {
    Player player;
    Opponent opponent;
    GameDisplay gameDisplay;

    /** 
     * ターンの最初に設定する項目
     * 
     * @param player　プレイヤー
     * @param opponent 相手
    */
    void initializeTurn(Player player, Opponent opponent) {
        player.battlePokemon.act = true;
        opponent.battlePokemon.act = true;
        player.battlePokemon.beforeAct = true;
        player.battlePokemon.afterAct = false;
        opponent.battlePokemon.beforeAct = true;
        opponent.battlePokemon.afterAct = false;
    }

    /** 
     * ターンの最後に設定する項目
     * 
     * @param player　プレイヤー
     * @param opponent 相手
    */
    void finalizeTurn(Player player, Opponent opponent) {
        player.battlePokemon.beforeAct = false;
        player.battlePokemon.afterAct = true;
        opponent.battlePokemon.beforeAct = false;
        opponent.battlePokemon.afterAct = true;
        player.battlePokemon.rankCom();
        opponent.battlePokemon.rankCom();
    }

    /** 
     * 場のポケモンが死んだ場合に交換や勝敗判定を行うメソッド。
     * 残りポケモン数が1以上の時は交換し、残りポケモンが0になると勝敗判定をする。
     * 
     * @param player　プレイヤー
     * @param opponent 相手
    */
    public void checkNumRemPokemon(Player player, Opponent opponent) {
        if(player.battlePokemon.abnCon == "ひんし"){
            player.battlePokemon.act = false;
            player.numRemPokemon --;
            if(player.numRemPokemon == 0){
                System.out.println(player.trainerName + "の てもとには たたかえる ポケモンが いない!");
                System.out.println("…… …… ……");
                System.out.println("めのまえが まっくらに なった!");
                System.exit(0);
            }
            boolean excute = false;
            while(!excute){
                int choicePokemonIndex = player.showParty();
                Pokemon choicePokemon = player.party[choicePokemonIndex];
                String command = player.showPokemon(choicePokemon);
                if(command == "いれかえる") {
                    player.exchange(choicePokemon);
                    excute = true;
                }
            }
        }
        if(opponent.battlePokemon.abnCon == "ひんし"){
            opponent.battlePokemon.act = false;
            opponent.numRemPokemon --;
            if(opponent.numRemPokemon == 0){
                System.out.println(opponent.trainerName + "とのしょうぶにかった!");
                System.exit(0);
            }
            opponent.exchange();
        }   
    }

    /** 
     * プレイヤーの行動を決めるメソッド。
     * にげるやいれかえるを行うとtrueを返し、技を選ぶとfalseを返す。
     * 
     * @param player　プレイヤー
     * @param opponent 相手
     * @param gameDisplay ゲーム画面
    */
    boolean handlePlayerCommand(Player player, Opponent opponent, GameDisplay gameDisplay, Technique opChoiceTechnique) {
        while (true) {
            String command = player.choiceCommand();
            if (command.equals("にげる") && confirmRunAway(player)) return true;
            if (command.equals("ポケモン") && handlePokemonSwitch(player, opponent, gameDisplay, opChoiceTechnique)) return true;
            if (command.equals("たたかう") && handleTechniqueSwitch(player, opponent, opChoiceTechnique)) return false;
        }
    }

    /** 
     * にげるコマンドを実行するメソッド。
     * はいを選ぶと降参し、バトルが終了する。
     * いいえを選ぶとhandlePlayerCommandまでもどる。
     * 
     * @param player　プレイヤー
    */
    boolean confirmRunAway(Player player) {
        while (true) {
            String confirm = player.runAway();
            if (confirm.equals("はい")) {
                System.out.println("降参が 選ばれました");
                System.exit(0);
            } else if (confirm.equals("いいえ")) return false;
        }
    }

    /** 
     * いれかえるコマンドを実行するメソッド。
     * 自分の手持ちポケモンを映し、選んだポケモンのステータスを表示したり、いれかえることができる。
     * もどるを選択するとhandlePlayerCommandまでもどる。
     * 
     * @param player　プレイヤー
     * @param opponent 相手
     * @param　gameDisplay ゲーム画面
     * @param　opChoiceTechnique 相手が選択した技
    */
    boolean handlePokemonSwitch(Player player, Opponent opponent, GameDisplay gameDisplay, Technique opChoiceTechnique) {
        while (true) {
            int choicePokemonIndex = player.showParty();
            if (choicePokemonIndex == player.party.length) return false; // 戻る
            if(0 <= choicePokemonIndex && choicePokemonIndex <= player.party.length - 1){
                Pokemon choicePokemon = player.party[choicePokemonIndex];
                while (true) {
                    String command = player.showPokemon(choicePokemon);
                    if (command.equals("いれかえる")) {
                        player.exchange(choicePokemon);
                        gameDisplay.gameDisplay(player, opponent);
                        opponent.battlePokemon.useTechnique(opChoiceTechnique, player.battlePokemon);
                        checkNumRemPokemon(player, opponent);
                        return true;
                    } else if (command.equals("もどる")) break;
                }
            }
        }
    }

    /** 
     * 実行したい技を選択するメソッド。
     * 自分の場のポケモンの技一覧を表示し、技を選択する。
     * もどるを選択するとhandlePlayerCommandまでもどる。
     * 
     * @param player　プレイヤー
     * @param opponent 相手
     * @param　opChoiceTechnique 相手が選択した技
    */
    boolean handleTechniqueSwitch(Player player, Opponent opponent, Technique opChoiceTechnique) {
        while (true){
            int choiceTechniqueIndex = player.choiceTechnique();
            if(0 <= choiceTechniqueIndex && choiceTechniqueIndex <= player.battlePokemon.techniques.length - 1) {
                executeBattleTurn(player.battlePokemon.techniques[choiceTechniqueIndex], opChoiceTechnique);
                return true;
            }else if(choiceTechniqueIndex == player.battlePokemon.techniques.length) {
                return false;
            }else{
                System.out.println("無効なコマンドです。");
            }

        }
    }

    /** 
     * 自分と相手が技を使用した際に行動順を決めるメソッド。
     * 優先度が高い方が先に行動し、優先度が同じ場合にはポケモンのすばやさが高い方が先に行動する。
     * こちらがいれかえを行った場合は相手の技のみ発動する。
     * 
     * @param plChoiceTechnique　プレイヤーが選択した技
     * @param opChoiceTechnique 相手が選択した技
    */
    void executeBattleTurn(Technique plChoiceTechnique, Technique opChoiceTechnique) {
        if(plChoiceTechnique != null){
            if(plChoiceTechnique.priority > opChoiceTechnique.priority){
                player.battlePokemon.useTechnique(plChoiceTechnique, opponent.battlePokemon);
                gameDisplay.gameDisplay(player, opponent);
                checkNumRemPokemon(player, opponent);
                opponent.battlePokemon.useTechnique(opChoiceTechnique, player.battlePokemon);
                gameDisplay.gameDisplay(player, opponent);
                checkNumRemPokemon(player, opponent);
            }else if(plChoiceTechnique.priority < opChoiceTechnique.priority) {
                opponent.battlePokemon.useTechnique(opChoiceTechnique, player.battlePokemon);
                gameDisplay.gameDisplay(player, opponent);
                checkNumRemPokemon(player, opponent);
                player.battlePokemon.useTechnique(plChoiceTechnique, opponent.battlePokemon);
                gameDisplay.gameDisplay(player, opponent);
                checkNumRemPokemon(player, opponent);
            }else if(plChoiceTechnique.priority == opChoiceTechnique.priority){
                if(player.battlePokemon.sReal > opponent.battlePokemon.sReal){
                    player.battlePokemon.useTechnique(plChoiceTechnique, opponent.battlePokemon);
                    gameDisplay.gameDisplay(player, opponent);
                    checkNumRemPokemon(player, opponent);
                    opponent.battlePokemon.useTechnique(opChoiceTechnique, player.battlePokemon);
                    gameDisplay.gameDisplay(player, opponent);
                    checkNumRemPokemon(player, opponent);
                }else{
                    opponent.battlePokemon.useTechnique(opChoiceTechnique, player.battlePokemon);
                    gameDisplay.gameDisplay(player, opponent);
                    checkNumRemPokemon(player, opponent);
                    player.battlePokemon.useTechnique(plChoiceTechnique, opponent.battlePokemon);
                    gameDisplay.gameDisplay(player, opponent);
                    checkNumRemPokemon(player, opponent);
                }
            }
        }else{
            opponent.battlePokemon.useTechnique(opChoiceTechnique, player.battlePokemon);
            gameDisplay.gameDisplay(player, opponent);
            checkNumRemPokemon(player, opponent);
        }
    }

    /** 
     * やどりぎのタネの効果を実行するメソッド。
     * 
     * @param player　プレイヤー
     * @param opponent 相手
    */
    void leechSeedEffect(Player player, Opponent opponent) {
        if(player.battlePokemon.leechSeed == true){
            player.battlePokemon.damaged((int)(player.battlePokemon.maxHP / 8));
            gameDisplay.gameDisplay(player, opponent);
            checkNumRemPokemon(player, opponent);
            opponent.battlePokemon.recoverd((int)(player.battlePokemon.maxHP / 8));
            gameDisplay.gameDisplay(player, opponent);
            checkNumRemPokemon(player, opponent);
        }else if(opponent.battlePokemon.leechSeed == true){
            opponent.battlePokemon.damaged((int)(opponent.battlePokemon.maxHP / 8));
            gameDisplay.gameDisplay(player, opponent);
            checkNumRemPokemon(player, opponent);
            player.battlePokemon.recoverd((int)(opponent.battlePokemon.maxHP / 8));
            gameDisplay.gameDisplay(player, opponent);
            checkNumRemPokemon(player, opponent);
        }
    }

    /** 
     * 場のポケモンの状態異常の効果を発動させるメソッド。
     * ターン終了時に実行し、やけど、どく、もうどくの処理を行う。
     * 
     * @param player　プレイヤー
     * @param opponent 相手
    */
    public void checkAbnCon(Player player, Opponent opponent) {
        if(player.battlePokemon.sReal > opponent.battlePokemon.sReal){
            player.battlePokemon.abnCon();
            gameDisplay.gameDisplay(player, opponent);
            checkNumRemPokemon(player, opponent);
            opponent.battlePokemon.abnCon();
            gameDisplay.gameDisplay(player, opponent);
            checkNumRemPokemon(player, opponent);
        }else{
            opponent.battlePokemon.abnCon();
            gameDisplay.gameDisplay(player, opponent);
            checkNumRemPokemon(player, opponent);
            player.battlePokemon.abnCon();
            gameDisplay.gameDisplay(player, opponent);
            checkNumRemPokemon(player, opponent);
        }
    }

    /** 
     * バトルを実行するメソッド。
     * 最初にゲーム画面やメッセージを出力し、その後ターン処理を行う。
     * 1.選択した技やactの初期化
     * 2.コマンド選択
     * 3.にげる
     * 4.いれかえる
     * 5.技
     * 6.やどりぎのタネ
     * 7.actの変更
     * 8.状態異常の処理
     * の順でターン処理を行う。
     * 
     * @param player　プレイヤー
     * @param opponent 相手
     * @param gameDisplay ゲーム画面
    */
    public void battleProcessing(Player player, Opponent opponent, GameDisplay gameDisplay) {
        gameDisplay.gameDisplay(player, opponent);
        System.out.println(opponent.trainerName + "が しょうぶを しかけてきた!");
        System.out.println(opponent.trainerName + "は" + opponent.battlePokemon.pokemonName + "を くりだした!");
        System.out.println("いけっ " + player.battlePokemon.pokemonName + "!");

        while(true) {
            Technique plChoiceTechnique = null, opChoiceTechnique = opponent.choiceTechnique(player.battlePokemon);
            initializeTurn(player, opponent);

            if (handlePlayerCommand(player, opponent, gameDisplay, opChoiceTechnique)) {
                executeBattleTurn(plChoiceTechnique, opChoiceTechnique);
            }

            leechSeedEffect(player, opponent);
            finalizeTurn(player, opponent);
            checkAbnCon(player, opponent);
        }
    }

    Battle() {
        //技リスト
        Technique tec1 = new Technique("ボルテッカー","物理", 120, 100, 15, true, "でんき", 0, "相手に与えたダメージの33%を自分も受ける。10%の確率で相手を『まひ』状態にする。");
        Technique tec2 = new Technique("アイアンテール","物理", 100, 75, 15, true, "はがね", 0, "30%の確率で相手の『ぼうぎょ』ランクを1段階下げる。");
        Technique tec3 = new Technique("かわらわり","物理", 75, 100, 15, true, "かくとう", 0, "");
        Technique tec4 = new Technique("ねこだまし","物理", 40, 100, 10, true, "ノーマル", 3, "100%の確率で相手をひるませる。ただし、出た最初のターンしか成功しない。");
        Technique tec5 = new Technique("のしかかり","物理", 85, 100, 15, true, "ノーマル", 0, "30%の確率で相手を『まひ』状態にする。");
        Technique tec6 = new Technique("じしん","物理", 100, 100, 10, false, "じめん", 0, "");
        Technique tec7 = new Technique("かみくだく","物理", 80, 100, 15, true, "あく", 0, "20%の確率で相手の『ぼうぎょ』ランクを1段階下げる。");
        Technique tec8 = new Technique("タネばくだん","物理", 80, 100, 15, false, "くさ", 0, "");
        Technique tec9 = new Technique("れいとうビーム","特殊", 90, 100, 10, false, "こおり", 0, "10%の確率で相手を『こおり』状態にする。");
        Technique tec10 = new Technique("ハイドロポンプ","特殊", 110, 80, 5, false, "みず", 0, "");
        Technique tec11 = new Technique("こおりのつぶて","物理", 40, 100, 30, false, "こおり", 1, "");
        Technique tec12 = new Technique("10まんボルト","特殊", 90, 100, 15, false, "でんき", 0, "10%の確率で相手を『まひ』状態にする。");
        Technique tec13 = new Technique("リーフストーム","特殊", 130, 90, 5, false, "くさ", 0, "攻撃後、100%の確率で自分の『とくこう』ランクが2段階下がる。");
        Technique tec14 = new Technique("ウッドハンマー","物理", 120, 100, 15, true, "くさ", 0, "相手に与えたダメージの33%を自分も受ける。");
        Technique tec15 = new Technique("しねんのずつき","物理", 80, 90, 15, true, "エスパー", 0, "20%の確率で相手をひるませる。");
        Technique tec16 = new Technique("やどりぎのたね","変化", 0, 90, 10, false, "くさ", 0, "使用後、毎ターン、相手のHPを最大HPの1/8ずつ減らし、その分自分のHPを回復させる。自分は交代しても効果が引き継ぐ。草タイプのポケモンには無効。");
        Technique tec17 = new Technique("たきのぼり","物理", 80, 100, 15, true, "みず", 0, "20%の確率で相手をひるませる。");
        Technique tec18 = new Technique("じしん","物理", 100, 100, 10, false, "じめん", 0, "");
        Technique tec19 = new Technique("こおりのキバ","物理", 65, 95, 15, true, "こおり", 0, "10%の確率で相手を『こおり』状態にするか、ひるませる。");
        Technique tec20 = new Technique("げきりん","物理", 120, 100, 10, true, "ドラゴン", 0, "2～3ターン連続で攻撃し、その後自分は1～4ターンの間『こんらん』状態になる。");
        Technique tec21 = new Technique("フレアドライブ","物理", 120, 100, 15, true, "ほのお", 0, "相手に与えたダメージの33%を自分も受ける。10%の確率で相手を『やけど』状態にする。自分が『こおり』状態の時でも使う事ができ、使うと『こおり』状態が治る。");
        Technique tec22 = new Technique("インファイト","物理", 120, 100, 5, true, "かくとう", 0, "攻撃後、自分の『ぼうぎょ』『とくぼう』ランクが1段階ずつ下がる。");
        Technique tec23 = new Technique("ワイルドボルト","物理", 90, 100, 15, true, "でんき", 0, "相手に与えたダメージの1/4を自分も受ける。");
        Technique tec24 = new Technique("しんそく","物理", 80, 100, 5, true, "ノーマル", 2, "");

        //ポケモン
        Pokemon pokemon1 = new Pokemon(
            "ピカチュウ",
            new String[]{"でんき"},
            50,
            35, 55, 40, 50, 50, 90,
            4, 252, 0, 0, 0, 252,
            31, 31, 31, 31, 31, 31,
            new Technique[]{tec1, tec2, tec3, tec4},
            "ようき"
            );
        Pokemon pokemon2 = new Pokemon(
            "カビゴン",
            new String[]{"ノーマル"},
            50,
            160, 110, 65, 65, 110, 30,
            252, 0, 252, 0, 4, 0,
            31, 31, 31, 31, 31, 31,
            new Technique[]{tec5, tec6, tec7, tec8},
            "しんちょう"
            );
        Pokemon pokemon3 = new Pokemon(
            "ラプラス",
            new String[]{"みず", "こおり"},
            50,
            130, 85, 80, 85, 95, 60,
            252, 0, 0, 4, 252, 0,
            31, 31, 31, 31, 31, 31,
            new Technique[]{tec9, tec10, tec11, tec12},
            "なまいき"
            );
        Pokemon pokemon4 = new Pokemon(
            "ナッシー",
            new String[]{"くさ", "エスパー"},
            50,
            95, 95, 85, 125, 75, 55,
            4, 252, 0, 252, 0, 0,
            31, 31, 31, 31, 31, 31,
            new Technique[]{tec13, tec14, tec15, tec16},
            "ゆうかん"
            );
        Pokemon pokemon5 = new Pokemon(
            "ギャラドス",
            new String[]{"みず", "ひこう"},
            50,
            95, 125, 79, 60, 100, 81,
            4, 252, 0, 0, 0, 252,
            31, 31, 31, 31, 31, 31,
            new Technique[]{tec17, tec18, tec19, tec20},
            "いじっぱり"
            );
        Pokemon pokemon6 = new Pokemon(
            "ウインディ",
            new String[]{"ほのお"},
            50,
            90, 110, 80, 100, 80, 95,
            4, 252, 0, 4, 0, 252,
            31, 31, 31, 31, 31, 31,
            new Technique[]{tec21, tec22, tec23, tec24},
            "いじっぱり"
            );

        //トレーナー
        player = new Player("レッド", new Pokemon[]{pokemon1, pokemon2, pokemon3});
        opponent = new Opponent("グリーン", new Pokemon[]{pokemon4, pokemon5, pokemon6});
        gameDisplay = new GameDisplay();
    }

}