package main;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.time.Duration;
import java.util.List;

public class CMDHandler implements TabExecutor {

    private static final String index = Main.index;
    public static Main pl = Main.getPlugin(Main.class);
    public static boolean isStarted = false;
    public static boolean canUseBlock = true;
    public static boolean isAir = false;
    public static ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
    public static ItemStack firework = new ItemStack(Material.AIR);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] strings) {

        if (commandSender.hasPermission("pvp.admin")) {
            switch (string) {
                case "pvp":
                    Player sender = (Player) commandSender;
                    if (strings.length == 0) commandSender.sendMessage(index + "/pvp 시작\n" + index + "/pvp 종료");
                    else if (strings[0].equals("시작")) {
                        if (!isStarted) {
                            isStarted = true;
                            if (canUseBlock) {
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 32767, 255, true));
                                    player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
                                    player.getInventory().setChestplate(chestplate);
                                    player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                    player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                                    player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                                    player.getInventory().addItem(new ItemStack(Material.BOW));
                                    player.getInventory().addItem(new ItemStack(Material.ARROW, 5));
                                    player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 7));
                                    player.getInventory().addItem(new ItemStack(Material.DIRT, 256));
                                    player.getInventory().addItem(firework);
                                }
                            } else {
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 32767, 255, true));
                                    player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
                                    player.getInventory().setChestplate(chestplate);
                                    player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                                    player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                                    player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                                    player.getInventory().addItem(new ItemStack(Material.BOW));
                                    player.getInventory().addItem(new ItemStack(Material.ARROW, 5));
                                    player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 7));
                                    player.getInventory().addItem(firework);
                                }
                            }
                            Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
                                int count = 5;

                                @Override
                                public void run() {
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        player.showTitle(Title.title(Component.text(count), Component.text(""), Title.Times.of(Duration.ZERO, Duration.ofSeconds(2), Duration.ZERO)));
                                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100.0f, 1.0f);
                                    }
                                    if (count <= 0) {
                                        sender.getWorld().setPVP(true);
                                        for (Player player : Bukkit.getOnlinePlayers()) {
                                            player.showTitle(Title.title(Component.text("전투 시작!"), Component.text(""), Title.Times.of(Duration.ZERO, Duration.ofSeconds(2), Duration.ZERO)));
                                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100.0f, 1.0f);
                                        }
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder set 5 300");
                                        Bukkit.getScheduler().cancelTasks(pl);
                                    }
                                    count--;
                                }
                            }, 0L, 20L);
                        } else commandSender.sendMessage(index + "게임이 이미 시작됐습니다.");
                    } else if (strings[0].equals("종료")) {
                        if (isStarted) {
                            sender.getWorld().setPVP(false);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder set 200 1");
                            String winner = "";
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.getInventory().clear();
                                if (player.getGameMode() == GameMode.SURVIVAL) {
                                    winner = player.getName();
                                    player.setHealth(20.0);
                                }
                                player.setGameMode(GameMode.SURVIVAL);
                            }
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.showTitle(Title.title(Component.text("게임 종료! 승리자 : " + winner), Component.text(""), Title.Times.of(Duration.ZERO, Duration.ofSeconds(2), Duration.ZERO)));
                            }
                            isStarted = false;
                        }
                    } else if (strings[0].equals("블럭")) {
                        if (!isStarted) {
                            canUseBlock = !canUseBlock;
                            if (canUseBlock) sender.sendMessage(index + "블럭 설치가 가능하게 바뀌었습니다.");
                            else sender.sendMessage(index + "블럭 설치가 불가능하게 바뀌었습니다.");
                        }
                    }else if (strings[0].equals("공중")) {
                        if (!isStarted) {
                            isAir = !isAir;
                            if (isAir) {
                                chestplate = new ItemStack(Material.ELYTRA);
                                firework = new ItemStack(Material.FIREWORK_ROCKET, 16);
                                sender.sendMessage(index + "공중전 모드가 활성화 됐습니다.");
                            }
                            else {
                                chestplate = new ItemStack(Material.IRON_CHESTPLATE);
                                firework = new ItemStack(Material.AIR);
                                sender.sendMessage(index + "공중전 모드가 비활성화 됐습니다.");
                            }
                        }
                    }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String string, String[] strings) {
        return null;
    }
}
