package Listener;
import com.forever.break123.Main;
import com.forever.break123.utill.showname;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import net.md_5.bungee.api.chat.TextComponent;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import com.forever.break123.Main.UUIDUtil;

public class onPlayerInteractEvent  implements Listener, CommandExecutor {

        //private Set<String> playerStuff = new HashSet<String>();
        private ArrayList<UUID> playerStuff = new ArrayList<UUID>();
        //private boolean playerHasStuff;



        public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
            Player p = (Player) sender;
            if (lable.equalsIgnoreCase("respawn")) {
                if (args.length == 0){
                    p.sendMessage("["+ChatColor.BLUE+"末日之都"+ChatColor.WHITE+"]" + ChatColor.RED + "輸入的參數錯誤");
                    return false;
                }
                if (args.length == 1){
                    if (args[0].equalsIgnoreCase("on" )){
                        Main.plugin.uuids2.add(p.getUniqueId());
                        p.sendMessage(ChatColor.GRAY+"["+ChatColor.YELLOW+"末日之都"+ChatColor.GRAY+"]" + ChatColor.GREEN + "開啟自動重生");
                    }
                    else if (args[0].equalsIgnoreCase("off" )){
                        Main.plugin.uuids2.remove(p.getUniqueId());
                        p.sendMessage(ChatColor.GRAY+"["+ChatColor.BLUE+"末日之都"+ChatColor.WHITE+"]" + ChatColor.RED + "關閉自動重生");
                    }
                }
            }
            if (lable.equalsIgnoreCase("mlist")) {
                if (Main.plugin.uuids.contains(p.getUniqueId())) {
                    Main.plugin.uuids.remove(p.getUniqueId());
                    p.sendMessage(ChatColor.GRAY+"["+ChatColor.BLUE+"末日之都"+ChatColor.GRAY+"]"+ChatColor.GREEN+"關閉實體查詢");
                    return true;
                }
                else {
                    Main.plugin.uuids.add(p.getUniqueId());
                    p.sendMessage(ChatColor.GRAY+"["+ChatColor.BLUE+"末日之都"+ChatColor.GRAY+"]"+ChatColor.GREEN+"開啟實體查詢");
                    return true;
                }
            }
            return false;

        }


        @EventHandler
        public void onPlayerInteractEvent (PlayerInteractEvent event) {
            Player p = event.getPlayer();
            if (Main.plugin.uuids.contains(p.getUniqueId())) {
                if (event.getHand() == EquipmentSlot.HAND & event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Chunk c = event.getClickedBlock().getChunk();
                    long time = System.nanoTime();
                    final Format DECIMAL_FORMAT = new DecimalFormat("#0.0##");
                    List list = new ArrayList();
                    List block3 = new ArrayList();
                    int ccmow=0;
                    int ccmow2=0;

                    for (BlockState B :c.getTileEntities()) {
                        if (B instanceof Hopper)
                        {
                            block3.add(String.format("%s", B.getType()));
                        }
                        else{
                            continue;
                        }
                    }

                    for (Entity entity : c.getEntities()) {
                        list.add(String.format("%s", entity.getType()));
                        if (entity instanceof Animals)
                        {
                            ccmow+=1;
                        }
                        else if (entity instanceof Monster)
                        {

                            ccmow2+=1;
                        }
                        else{
                            continue;
                        }
                    }
                    time = System.nanoTime() - time;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("使用指令/mlist 可以關閉查詢功能"));
                    p.sendMessage(ChatColor.BLUE+"區塊查詢模式" + " " +ChatColor.GREEN + "時間"+  DECIMAL_FORMAT.format(time / 1000000.0D) );
                    p.sendMessage("全部實體的數量" + c.getEntities().length);
                    p.sendMessage(ChatColor.YELLOW+"類別統計"+"\n"+ChatColor.WHITE+"動物:" +ChatColor.GREEN+ ccmow+ChatColor.WHITE+"怪物:"+ChatColor.GREEN+ccmow2);
                    Set<String> uniqueSet = new HashSet<String>(list);
                    Set<String> uniqueSet2 = new HashSet<String>(block3);
                    for (String temp : uniqueSet) {
                        if (showname.showname(temp) !="null") {
                            p.sendMessage(showname.showname(temp) + " " + "數量:" + Collections.frequency(list, temp));
                        }
                        else {
                            p.sendMessage(temp + " " + "數量:" + Collections.frequency(list, temp));
                        }
                    }
                    for (String temp2 : uniqueSet2) {
                        p.sendMessage(temp2+ " " + "數量:" + Collections.frequency(block3,temp2));
                    }

                }
            }
        }

}












