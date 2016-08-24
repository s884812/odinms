/*
 * JavaScriptz (javascriptz@leaderms.com.br)
 * LeaderMS 2012 ▬ 2015
 * Brasil MapleStory Server
 * Configurações Gerais
 * www.leaderms.com.br
 */
package config.configuracoes;

import java.util.Properties;
import client.MapleInventoryType;
import java.util.Arrays;
import java.util.List;
import net.world.WorldServer;
import server.maps.MapleMapObjectType;

public class configuracoes {

    public static final List<MapleMapObjectType> rangedMapobjectTypes = Arrays.asList(
            MapleMapObjectType.ITEM,
            MapleMapObjectType.MONSTER,
            MapleMapObjectType.DOOR,
            MapleMapObjectType.REACTOR,
            MapleMapObjectType.SUMMON,
            MapleMapObjectType.NPC,
            MapleMapObjectType.MIST);

    /* Fim/*
    /*    Mensagens     */
    public static final short Leader_versao = 62;
    public static String Nome_Server = "LeaderMS";
    /*   Jogandor login     */
    public static final String Jogador_Logado = "Bem vindo novamente, nao se esqueca de votar em nosso servidor (#r#eLeaderMS#k#n).";
    public static final String Jogador_Iniciante = "Ola iniciante, use o comando @comandos e tenha um bom jogo.";
    public static final String Jogador_Buffado = "<Voce foi buffado pelo LeaderBot>";
    public static final String Novo_Jogador = "Se juntou ao nosso servidor!";
    /*      LeaderPoints      */
    public static int LeaderPoints_1 = 1;
    public static int LeaderPoints_2 = 2;
    public static int LeaderPoints_3 = 3;
    /*       CashShop        */
    public static int[] BLOQUEADO_CS = new int[]{
        1812006,/*Magic Scales*/
        1812007,/*Item Ignore Pendant*/
        5230000,/*The Owl Of Minerva*/
        5220000,/* Gachapon Ticket */
        5400000,/*Character Name Change*/
        5401000,/*Character Transfer*/
        5430000,/*Extra Character Slot Coupon*/
        5140000,
        5140001,
        5140002,
        5140003,
        5140004,
        1912004,
        1902009,
        1912003,
        1902008,
        5140006,
        5370000,
        5370001,
        5281000

    };
    /*  Mensagem do Sistema */
    public static final String[] botMensagens = {
        "Seja bem-vindo ao melhor servidor privado do Brasil!",
        "Qualquer bug ou erro reportar em nossa comunidade.",
        "Nostalgia no ar, eventos diarios participe!",
        "Lembre-se de registrar em nosso forum - leaderms.com.br/forum",
        "Sabe como melhorar nosso jogo? Deixe sua sugestao em nossa comunidade!",
        "[Atencao] Jogadores sem respawn/drops, devem sair das quest's.",};

    /* Outros ajustes */
    public static MapleInventoryType getInventoryType(final int itemId) {
        final byte type = (byte) (itemId / 1000000);
        if (type < 1 || type > 5) {
            return MapleInventoryType.UNDEFINED;
        }
        return MapleInventoryType.getByType(type);
    }

    public static boolean isBeginnerJob(final int job) {
        return job == 0 || job == 1 || job == 1000 || job == 2000 || job == 2001 || job == 3000 || job == 3001 || job == 2002;
    }

    public static boolean isWeapon(final int itemId) {
        return itemId >= 1300000 && itemId < 1533000;
    }

    public static boolean isRechargable(int itemId) {
        return itemId / 10000 == 233 || itemId / 10000 == 207;
    }

    public static final boolean isThrowingStar(int itemId) {
        return itemId / 10000 == 207;
    }

    public static final boolean isBullet(int itemId) {
        return itemId / 10000 == 233;
    }

    public static boolean isForceRespawn(int mapid) {
        switch (mapid) {
            case 103000800:
            case 925100100:
                return true;
            default:
                return mapid / 100000 == 9800 && (mapid % 10 == 1 || mapid % 1000 == 100);
        }
    }

    public static final int maxViewRangeSq() {
        return 800000; // 800 * 800
    }

}
