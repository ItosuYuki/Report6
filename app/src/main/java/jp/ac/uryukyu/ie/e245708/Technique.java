package jp.ac.uryukyu.ie.e245708;

public class Technique {
    String techniqueName; //技名
    String cla; //物理、特殊、変化技の区分
    int pwr; //技威力
    int acc; //命中率
    int maxPP; //最大PP
    int PP; //PP
    boolean direct; //接触技か否か 接触の場合True
    String type; //タイプ
    int priority; //優先度
    String effect; //技効果
    
    //コンストラクタ
    Technique(
        String techniqueName,
        String cla,
        int pwr, 
        int acc, 
        int maxPP, 
        boolean direct, 
        String type, 
        int priority, 
        String effect 
    ){
        this.techniqueName =techniqueName;
        this.cla = cla;
        this.pwr = pwr;
        this.acc = acc;
        this.maxPP = maxPP;
        this.PP = maxPP;
        this.direct = direct;
        this.type = type;
        this.priority = priority;
        this.effect = effect;
    }
}
