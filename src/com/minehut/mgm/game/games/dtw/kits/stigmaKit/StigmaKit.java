package com.minehut.mgm.game.games.dtw.kits.stigmaKit;

import com.minehut.commons.common.chat.F;
import com.minehut.commons.common.fireworks.Fireworks;
import com.minehut.commons.common.items.ItemStackFactory;
import com.minehut.commons.common.multimap.MultiMap;
import com.minehut.commons.common.particles.ParticleEffect;
import com.minehut.commons.common.sound.S;
import com.minehut.mgm.GameHandler;
import com.minehut.mgm.MGM;
import com.minehut.mgm.game.coreModules.damage.CustomDamageEvent;
import com.minehut.mgm.game.kit.Kit;
import com.minehut.mgm.game.kit.kitPlayer.GamePlayer;
import com.minehut.mgm.util.C;
import com.minehut.mgm.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by luke on 6/14/15.
 */
public class StigmaKit extends Kit {

    // Killer - Hurt - Time
    private MultiMap<UUID, UUID, Integer> marks;

    private int runnableID;

    private static int markTime = 4;

    public StigmaKit() {
        super("Stigma", Material.FISHING_ROD, 1000);

        super.addItem(Material.STONE_SWORD);
        super.addItem(ItemStackFactory.createItem(Material.FISHING_ROD, C.red + "Target Marker"));
        super.addItem(Material.BOW);
        super.addItem(Material.STONE_AXE);
        super.addItem(ItemStackFactory.createItem(Material.WOOD, 32));
        super.addItem(ItemStackFactory.createItem(Material.LOG, 8));

        super.setItem(ItemStackFactory.createItem(Material.ARROW, 64), 8);

        super.addItem(Material.LEATHER_HELMET);
        super.addItem(Material.LEATHER_CHESTPLATE);
        super.addItem(Material.LEATHER_BOOTS);

        this.marks = new MultiMap<>();
        this.runnableID = this.countdownMarkTimes();
    }

//    @EventHandler
//    public void onHookHit(PlayerFishEvent event) {
//        if(!GameHandler.getHandler().getMatch().isRunning()) return;
//
//        if (event.getCaught() instanceof Player) {
//            Player hit = (Player) event.getCaught();
//
//            if (!TeamUtils.onSameTeam(event.getPlayer(), hit)) {
//                GamePlayer hitGamePlayer = GameHandler.getGameHandler().getKitManager().getGamePlayerManager().getGamePlayer(hit);
//                hitGamePlayer.setLastDamagedFrom(event.getPlayer().getUniqueId());
//
//                this.marks.put(event.getPlayer().getUniqueId(), hit.getUniqueId(), markTime);
//                event.getPlayer().sendMessage(C.red + "Target Marked");
//            }
//        }
//    }

    @EventHandler(priority = EventPriority.LOW)
    public void hookLand(CustomDamageEvent event) {

        if (event.getHurtPlayer() != null && event.getDamagerPlayer() != null) {
            if (event.getProjectile() != null) {
                if (event.getProjectile() instanceof FishHook) {
                        if (!TeamUtils.onSameTeam(event.getDamagerPlayer(), event.getHurtPlayer())) {
                            GamePlayer hitGamePlayer = GameHandler.getGameHandler().getKitManager().getGamePlayerManager().getGamePlayer(event.getHurtPlayer());
                            hitGamePlayer.setLastDamagedFrom(event.getDamagerPlayer().getUniqueId());

                            this.marks.put(event.getDamagerPlayer().getUniqueId(), event.getHurtPlayer().getUniqueId(), markTime);

                            ParticleEffect.SPELL_INSTANT.display((float) .2, (float) .2, (float) .2, 0, 20, event.getHurtPlayer().getEyeLocation(), 50);

                            S.playSound(event.getDamagerPlayer(), Sound.ARROW_HIT);

                            event.getProjectile().remove();
                        } else {
                            event.setCancelled(true); //todo move friendly fire to somewhere else
                        }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(CustomDamageEvent event) {
        if(event.isCancelled()) return;

        if (event.getProjectile() != null && event.getProjectile() instanceof FishHook) {
            return;
        }

        if (event.getHurtPlayer() != null && event.getDamagerPlayer() != null) {
            if (this.marks.containsKey(event.getDamagerPlayer().getUniqueId())) {
                this.marks.getFirstValue(event.getDamagerPlayer().getUniqueId()).toString().equalsIgnoreCase(event.getHurtPlayer().getUniqueId().toString());
                event.setDamage(event.getDamage() + 11);

                ParticleEffect.LAVA.display((float) .2, (float) .2, (float) .2, 0, 20, event.getHurtPlayer().getEyeLocation(), 50);
                event.getHurtPlayer().getEyeLocation().getWorld().playSound(event.getHurtPlayer().getEyeLocation(), Sound.GLASS, 10, 1);

                this.marks.remove(event.getDamagerPlayer().getUniqueId());
            }
        }
    }

    private int countdownMarkTimes() {
        return Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MGM.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (!marks.isEmpty()) {

                    ArrayList<UUID> remove = new ArrayList<UUID>();
                    for (UUID uuid : marks.keySet()) {
                        int time = marks.getSecondValue(uuid);
                        if (time <= 0) {
                            remove.add(uuid);
                        } else {
                            marks.put(uuid, marks.getFirstValue(uuid), time - 1);
                        }
                    }
                }
            }
        }, 20L, 20L);
    }

    @Override
    public void onApply(Player player) {

    }

    @Override
    public void extraUnload() {
        Bukkit.getServer().getScheduler().cancelTask(this.runnableID);
    }
}
