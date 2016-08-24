/*
This file is part of the OdinMS Maple Story Server
Copyright (C) 2008 Patrick Huy <patrick.huy~frz.cc>
Matthias Butz <matze~odinms.de>
Jan Christian Meyer <vimes~odinms.de>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License version 3
as published by the Free Software Foundation. You may not use, modify
or distribute this program under any other version of the
GNU Affero General Public License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

 * JavaScriptz (javascriptz@leaderms.com.br)
 * LeaderMS 2012 ▬ 2015
 * Brasil MapleStory Server
 * CashPQ
 * www.leaderms.com.br
 */


package client.messages.commands;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import client.MapleCharacter;
import client.MapleInventoryType;
import client.MapleClient;
import client.MapleInventory;
import client.messages.Command;
import client.messages.CommandDefinition;
import client.messages.IllegalCommandSyntaxException;
import client.messages.MessageCallback;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.channel.ChannelServer;
import scripting.npc.NPCScriptManager;
import server.MapleItemInformationProvider;
import server.maps.SavedLocationType;
import tools.FilePrinter;
import tools.MaplePacketCreator;
import tools.StringUtil;

public class PlayerCommand implements Command {

    private Map<Integer, Long> gmUsages = new LinkedHashMap<Integer, Long>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

    @SuppressWarnings("static-access")
    @Override
    public void execute(MapleClient c, MessageCallback mc, String[] splitted) throws Exception, IllegalCommandSyntaxException {
          MapleCharacter player = c.getPlayer();
          ChannelServer cserv = c.getChannelServer();
         if (splitted[0].equalsIgnoreCase("@gm") || splitted[0].equalsIgnoreCase("!gm")) {
            if (splitted.length == 1) {
                mc.dropMessage("Tipo : @gm <mensagem>");
                return;
            }
            if (gmUsages.get(c.getPlayer().getId()) != null) {
                long lastUse = gmUsages.get(c.getPlayer().getId());
                if (System.currentTimeMillis() - lastUse < 60 * 1000 * 2) {
                    mc.dropMessage("Voce so pode enviar mensagens para GM de 2 em 2 minutos.");
                    return;
                } else {
                    mc.dropMessage("Enviando mensagem ..");
                    FilePrinter.printGM("SuporteGMs.rtf", "Mensagem que foi enviada: " + StringUtil.joinStringFrom(splitted, 1) + "\r\nNo dia: " + sdf.format(Calendar.getInstance().getTime()) + " as " + sdf2.format(Calendar.getInstance().getTime()) + ".\r\nSolicitante: " + player.getName() + " (" + player.getAccountID() + ")");
                    c.getChannelServer().getWorldInterface().broadcastGMMessage(null, MaplePacketCreator.serverNotice(5, "[" + c.getPlayer().getName() + "] " + StringUtil.joinStringFrom(splitted, 1)).getBytes());
                    gmUsages.put(c.getPlayer().getId(), System.currentTimeMillis());
                    mc.dropMessage("Feito, aguarde uma resposta.");
                }
            } else {
                mc.dropMessage("Enviando mensagem ..");
                c.getChannelServer().broadcastGMPacket(MaplePacketCreator.serverNotice(5, "[" + c.getPlayer().getName() + " - GM Mensagem] " + StringUtil.joinStringFrom(splitted, 1)));
                gmUsages.put(c.getPlayer().getId(), System.currentTimeMillis());
                mc.dropMessage("Feito, aguarde uma resposta.");
            }
        } if (splitted[0].equalsIgnoreCase("@bugs")) {
                    FilePrinter.printBug("Bugs.rtf", "Bug relatado: " + StringUtil.joinStringFrom(splitted, 1) + "\r\nNo dia: " + sdf.format(Calendar.getInstance().getTime()) + " as " + sdf2.format(Calendar.getInstance().getTime()) + ".\r\nJogador: " + player.getName() + " (" + player.getAccountID() + ")");
                    mc.dropMessage("Enviando seu relatorio..");
          } else if (splitted[0].equalsIgnoreCase("@comandos") || splitted[0].equalsIgnoreCase("!comandos")) {
            mc.dropMessage(" - LeaderMS v.62 -");
            for (CommandDefinition cd : getDefinition()) {
                if (!cd.getCommand().equalsIgnoreCase("ajuda")) {
                    mc.dropMessage("@" + cd.getCommand() + " - " + cd.getHelp());
                }
            }
        } else if (splitted[0].equalsIgnoreCase("@dispose")) {
                NPCScriptManager.getInstance().dispose(c);
                c.getSession().write(MaplePacketCreator.enableActions());
                mc.dropMessage("Feito!");
        } else if (splitted[0].equalsIgnoreCase("@grupofix")) {
                    player.setParty(null);
                    player.dropMessage("Por favor, recriar o grupo.");

         } else if (splitted[0].equals("@evento")) {
            if (player.getClient().getChannelServer().eventOn == true) {
                  c.getPlayer().setPreviousMap(c.getPlayer().getMapId());
                  player.changeMap(player.getClient().getChannelServer().eventMap, 0);
                  player.saveLocation(SavedLocationType.EVENTO);
            } else {
                mc.dropMessage("Nao ha nenhum evento no momento.");
            }
         } else if (splitted[0].equals("@sairevento")) {
            if (c.getChannelServer().eventOn == true) {
                    player.changeMap(player.getSavedLocation(SavedLocationType.EVENTO), 0);
                    player.clearSavedLocation(SavedLocationType.EVENTO);
            } else {
                c.getPlayer().dropMessage("Nao ha eventos ativos neste momento. Por favor, tente novamente mais tarde!");
                return;
            }
        } else if (splitted[0].equals("@entrartot")) {
            if (player.getMapId() == 240000110) {
                    player.changeMap(270000100);
            } else {
                c.getPlayer().dropMessage("Voce nao esta na estacao de Leafre!");
                return;
            }
        } else if (splitted[0].equals("@sairtot")) {
            if (player.getMapId() == 270000100) {
                    player.changeMap(240000110);
            } else {
                c.getPlayer().dropMessage("Voce nao esta na entrada do Templo!");
                return;
            }
        }
         else if (splitted[0].equals("@jqrank")) {
                              try {
                    ResultSet rs = rankingJQ(false);
                    player.dropMessage(" .:: Top 3 melhores jogadores de JQ ::. ");
                    int i = 1;
                    while (rs.next()) {

                        player.dropMessage(i + ". <Nome> " + rs.getString("name") + " /  <JQ Points> " + rs.getInt("jqrank"));
                        i++;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(PlayerCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
         }
         else if (splitted[0].equals("@checarpersonagem")) {
                player.saveToDB(true, false);
                player.yellowMessage(".::Voce tem atualmente::.");
                player.dropMessage("BetaPoints: " + player.getCSPoints(5)); //remover dps
                player.dropMessage("LeaderPoints: " + player.getLeaderPoints());
                player.dropMessage("Q.Points: " + player.getpqPoints());
                player.dropMessage("JQ Points: " +  player.getJQPoints());
                player.dropMessage("CashPoints: " +  player.getCashPoints());
                player.dropMessage("Ocupacao: " + player.getOccupation());
         } 
        
         else if (splitted[0].equalsIgnoreCase("@smega")) {
                if (player.getMeso() >= 10000) {
                player.setSmegaEnabled(!player.getSmegaEnabled());
                String text = (!player.getSmegaEnabled() ? "[Desativando] Os smegas foram desativados." : "[Ativando] Os smegas foram ativados.");
                mc.dropMessage(text);
                player.gainMeso(-10000, true);
                } else {
                mc.dropMessage("Para desativalos/ativalos voce precisa de 10,000 mesos.");
            }
        } 
    }
    

    public int itemQuantity(MapleClient c, int itemid) {
        MapleInventoryType type = MapleItemInformationProvider.getInstance().getInventoryType(itemid);
        MapleInventory iv = c.getPlayer().getInventory(type);
        int possesed = iv.countById(itemid);
        return possesed;
    }
    
   private static ResultSet rankingJQ(boolean gm) {
        try {
            Connection con = (Connection) DatabaseConnection.getConnection();
            PreparedStatement ps;
            if (!gm) {
                ps = (PreparedStatement) con.prepareStatement("SELECT jqrank , level, name FROM characters WHERE gm < 3 ORDER BY jqrank desc LIMIT 3");
            } else {
                ps = (PreparedStatement) con.prepareStatement("SELECT jqrank , name, gm FROM characters WHERE gm >= 3");
            }
            return ps.executeQuery();
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public CommandDefinition[] getDefinition() {
        return new CommandDefinition[]{
                        new CommandDefinition("comandos", "", "Mostra lista de ajuda.", 0),
			new CommandDefinition("gm", "", "Envia mensagem a GM online.", 0),
                        new CommandDefinition("dispose", "", "Nao consegue falar com algum NPC? Use este comando.", 0),
                        new CommandDefinition("grupofix", "", "Corrige possivel bug na criacao de grupos.", 0),
                        new CommandDefinition("evento", "", "Te leva para o evento em andamento.", 0),
                        new CommandDefinition("sairevento", "", "Te leva para o mapa anterior em que voce estava.", 0),
                        new CommandDefinition("smega", "", "Este comando Ativa/Desativa smega (Taxa 1,000 Mesos).", 0),
                        new CommandDefinition("bugs", "", "Envia relatorios de BUGS!", 0),
                        new CommandDefinition("entrartot", "", "Envia personagem ao Templo.", 0),    
                        new CommandDefinition("sairtot", "", "Remove personagem do Templo!", 0),    
		};
    }
}