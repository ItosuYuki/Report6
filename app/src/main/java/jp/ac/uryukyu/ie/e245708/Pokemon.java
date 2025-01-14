package jp.ac.uryukyu.ie.e245708;


public class Pokemon {
    String pokemonName;                                 //ポケモン名
    String[] type;                                      //タイプ
    int level;                                          //レベル(1~100)
    int maxHP;                                          //最大HP
    //h->HP, a->攻撃, b->防御, c->特攻, d->特防, s->素早さ, ac->命中率, ev->回避率
    int hRace, aRace, bRace, cRace, dRace, sRace,       //種族値
        hEffo, aEffo, bEffo, cEffo, dEffo, sEffo,       //努力値(0~252)
        hIndi, aIndi, bIndi, cIndi, dIndi, sIndi,       //個体値(0~31)
        hReal, aReal, bReal, cReal, dReal, sReal;       //実数値
    int aRank = 0, bRank = 0,  cRank = 0, 
        dRank = 0, acRank = 0, evRank = 0;              //ランク補正
    Technique[] techniques;                             //技リスト
    String nature;                                      //性格
    int abnCon = 0;                                     //状態異常

    void damaged(int damage){
        hReal -= damage;
        if(hReal <= 0){
            hReal = 0;
            abnCon = 7;//ひんし

            System.out.println(pokemonName + "は たおれた！");
        }
    }
    //コンストラクタ
    Pokemon(
        String pokemonName,
        String[] type,
        int level,
        int hRace, int aRace, int bRace, int cRace, int dRace, int sRace,
        int hEffo, int aEffo, int bEffo, int cEffo, int dEffo, int sEffo,
        int hIndi, int aIndi, int bIndi, int cIndi, int dIndi, int sIndi,
        Technique[] tecniques, //技リスト
        String nature //性格
    ){
        this.pokemonName = pokemonName;
        this.type = type;
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
        this.aReal = (int)((int)((int)(this.aRace + 2 + aIndi + aEffo / 4) * level / 100 + 5) * aNat);
        this.bReal = (int)((int)((int)(bRace + 2 + bIndi + bEffo / 4) * level / 100 + 5) * bNat);
        this.cReal = (int)((int)((int)(cRace + 2 + cIndi + cEffo / 4) * level / 100 + 5) * cNat);
        this.dReal = (int)((int)((int)(dRace + 2 + dIndi + dEffo / 4) * level / 100 + 5) * dNat);
        this.sReal = (int)((int)((int)(sRace + 2 + sIndi + sEffo / 4) * level / 100 + 5) * sNat);
    }
}