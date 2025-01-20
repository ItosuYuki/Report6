package jp.ac.uryukyu.ie.e245708;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PokemonTest {
    @Test
    void testUseTechnique() {
        Technique tec1 = new Technique("ボルテッカー","物理", 120, 100, 15, true, "でんき", 0, "相手に与えたダメージの33%を自分も受ける。10%の確率で相手を『まひ』状態にする。");
        Technique tec5 = new Technique("のしかかり","物理", 85, 100, 15, true, "ノーマル", 0, "30%の確率で相手を『まひ』状態にする。");
        Pokemon performer = new Pokemon(
            "ピカチュウ",
            new String[]{"でんき"},
            50,
            35, 55, 40, 50, 50, 90,
            4, 252, 0, 0, 0, 252,
            31, 31, 31, 31, 31, 31,
            new Technique[]{tec1},
            "ようき"
            );
        Pokemon target = new Pokemon(
            "カビゴン",
            new String[]{"ノーマル"},
            50,
            160, 110, 65, 65, 110, 30,
            252, 0, 252, 0, 4, 0,
            31, 31, 31, 31, 31, 31,
            new Technique[]{tec5},
            "しんちょう"
            );
        //行動できるようにする
        performer.act = true;
        int beforeHP = target.hReal;
        performer.useTechnique(tec1, target);
        int afterHP = target.hReal;
        assertTrue(afterHP < beforeHP , "技は正常に発動しています。");
    }
}