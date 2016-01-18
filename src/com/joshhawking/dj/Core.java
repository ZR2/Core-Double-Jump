package com.joshhawking.dj;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Core extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	};
	
	
	HashMap<Player, Boolean> cooldown = new HashMap<Player, Boolean>();
	ArrayList<Player> smash = new ArrayList<Player>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		Player p = (Player) e.getEntity();
		
		
		 
			
			
		
		
	if (p.hasPermission("dj.vip")) {	
		if (e.getCause() == DamageCause.FALL) {
			if (e.getEntity().getType() == EntityType.PLAYER) {
				
				

				if (smash.contains(p)) {
					ArrayList<Block> blocks = new ArrayList<Block>();
					blocks.add(p.getWorld().getBlockAt(
							p.getLocation().subtract(0, 1, 0)));
					blocks.add(p.getWorld().getBlockAt(
							p.getLocation().subtract(1, 1, 0)));
					blocks.add(p.getWorld().getBlockAt(
							p.getLocation().subtract(0, 1, 1)));
					blocks.add(p.getWorld().getBlockAt(
							p.getLocation().subtract(-1, 1, 0)));
					blocks.add(p.getWorld().getBlockAt(
							p.getLocation().subtract(0, 1, -1)));
					blocks.add(p.getWorld().getBlockAt(
							p.getLocation().subtract(1, 1, 1)));
					blocks.add(p.getWorld().getBlockAt(
							p.getLocation().subtract(-1, 1, -1)));
					blocks.add(p.getWorld().getBlockAt(
							p.getLocation().subtract(2, 1, 0)));
					blocks.add(p.getWorld().getBlockAt(
							p.getLocation().subtract(-2, 1, 0)));
					blocks.add(p.getWorld().getBlockAt(
							p.getLocation().subtract(0, 1, 2)));
					blocks.add(p.getWorld().getBlockAt(
							p.getLocation().subtract(0, 1, -2)));

					for (Block b : blocks) {
						for (Player pl : Bukkit.getOnlinePlayers()) {
							pl.playEffect(b.getLocation(), Effect.STEP_SOUND,
									b.getTypeId());
							ParticleEffects eff = ParticleEffects.WITCH_MAGIC;
							eff.display(b.getLocation(), 0, 0, 0, 5, 200);
						}
					}

					smash.remove(p);
				}
			}
		}
	}
	}
	
	
	
	
	  

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if (p.hasPermission("dj.vip")) {
			if (p.getGameMode() == GameMode.CREATIVE)
				return;

			if (cooldown.get(p) != null && cooldown.get(p) == true) {
				p.setAllowFlight(true);
			} else {
				p.setAllowFlight(false);
			}

			if (p.isOnGround()) {
				cooldown.put(p, true);
			}

			if (cooldown.get(p) != null && cooldown.get(p) == false) {
				for (Player pl : Bukkit.getOnlinePlayers()) {
					pl.playEffect(p.getLocation(), Effect.SMOKE, 2004);
				}
			}
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFly(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();

		if (p.hasPermission("dj.vip")) {
			if (p.getGameMode() == GameMode.CREATIVE)
				return;

			if (cooldown.get(p) == true) {
				e.setCancelled(true);
				cooldown.put(p, false);
				p.setVelocity(p.getLocation().getDirection().multiply(1.6D)
						.setY(1.0D));
				p.playSound(p.getLocation(), Sound.WITHER_SHOOT, 1.0F, 1.0F);
				for (Player pl : Bukkit.getOnlinePlayers()) {
					pl.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES,
							2004);
				}

				p.setAllowFlight(false);
			}
		}

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("dj.vip")) {
			if (p.getGameMode() == GameMode.CREATIVE)
				return;

			if (p.isOnGround() == false && cooldown.get(p) != null
					&& cooldown.get(p) == false) {
				p.setVelocity(new Vector(0, -5, 0));
				try {
				} catch (Exception lol) {
					lol.printStackTrace();
				}
				smash.add(p);

			}
		}

	}
	
	
	
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		 if (p.getName().equalsIgnoreCase("Aussie_Jhawking")) {
			 e.setJoinMessage("§6§lAussie_Jhawking§e joined the game,\nThe creator of §6§lCoreDoubleJump!");
		 }
	}
	
	
}