package net.channel.handler;

import java.util.Arrays;
import client.MapleClient;
import net.AbstractMaplePacketHandler;
import server.maps.MapleMapObjectType;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;

public class HiredMerchantRequest extends AbstractMaplePacketHandler {

    @Override
    public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
        if (!c.isGuest()) {
            if (c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getPosition(), 23000, Arrays.asList(MapleMapObjectType.HIRED_MERCHANT, MapleMapObjectType.SHOP)).size() == 0) {
                if (!c.getPlayer().hasMerchant()) {
                    c.getSession().write(MaplePacketCreator.hiredMerchantBox());
                } else {
                    c.getPlayer().dropMessage(1, "Voce ja tem uma loja aberta!");
                }
            } else {
                c.getPlayer().dropMessage(1, "Voce nao pode estabelecer uma loja aqui.");
            }
        } else {
            c.getPlayer().dropMessage(1, "Os usuarios convidados nao estao autorizados a abrir comerciantes contratados.");
        }
    }
}
