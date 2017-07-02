package cc.moecraft.useless.serverlagger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Random;

/**
 * 此类由 Hykilpikonna 在 2017/07/02 创建!
 * Created by Hykilpikonna on 2017/07/02!
 * Twitter: @Hykilpikonna
 * QQ/Wechat: 871674895
 */
public class Main extends JavaPlugin implements Listener
{
    Integer delay = 100;
    ArrayList<String> saviors = new ArrayList<>();
    String saviorOnlineMessage = "救世主%s已经上线, 服务器不卡了!";

    public void onEnable()
    {
        Bukkit.getPluginManager().registerEvents(this, this);
        if (getConfig().contains("Delay"))
        {
            delay = getConfig().getInt("Delay");
            saviors.addAll(getConfig().getStringList("Saviors"));
            saviorOnlineMessage = getConfig().getString("SaviorOnlineMessage");
        }
        else
        {
            getConfig().options().copyDefaults(true);
            getConfig().addDefault("Delay", delay);
            ArrayList<String> temp = new ArrayList<String>();
            temp.add("Hykilpikonna");
            getConfig().addDefault("Saviors", temp);
            getConfig().addDefault("SaviorOnlineMessage", "救世主%s已经上线, 服务器不卡了!");
            saviors = temp;
            saveConfig();
        }
    }
    public void onDisable() {}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWalkEvent(PlayerMoveEvent event)
    {
        boolean aSwitch = true;
        for (String savior : saviors)
            if (Bukkit.getPlayer(savior) != null) aSwitch = false;
        if (aSwitch)
            try
            {
                Thread.sleep(delay);
            }
            catch (InterruptedException ignored) {}
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        boolean aSwitch = true;
        for (String savior : saviors)
            if (Bukkit.getPlayer(savior) != null) aSwitch = false;
        if (aSwitch)
            for (Player player : Bukkit.getOnlinePlayers())
                player.sendMessage(saviorOnlineMessage);
    }
}
