package spawnFly;

import cn.nukkit.AdventureSettings;
import cn.nukkit.Player;
import cn.nukkit.AdventureSettings.Type;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityLevelChangeEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import hancho.BossbarManager.BossbarManager;

public class SpawnFly extends PluginBase implements Listener {
	Level spawn;
	BossbarManager bossbarManager = null;

	@Override
	public void onEnable() {
		this.spawn = this.getServer().getDefaultLevel();
		if(getServer().getPluginManager().getPlugin("BossbarManager") != null) {
			this.bossbarManager = (hancho.BossbarManager.BossbarManager) getServer().getPluginManager().getPlugin("BossbarManager");
		}
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent ev) {
		Player player = ev.getPlayer();
		if (player.getLevel().equals(spawn)) {
			if (player.getGamemode() == 0 || player.getGamemode() == 2) {
				player.setAdventureSettings(getFlyableSettings(player));
			}
		}
	}

	@EventHandler
	public void onLevelChange(EntityLevelChangeEvent ev) {
		if (ev.getEntity() instanceof Player) {
			if (ev.getTarget().equals(spawn)) {
				Player player = (Player) ev.getEntity();
				if (player.getGamemode() == 0 || player.getGamemode() == 2) {
					player.setAdventureSettings(getFlyableSettings(player));
					if(this.bossbarManager != null) {
						bossbarManager.editSecondBossBar(player, "§l§dFLY§f 사용 가능!");
					}
				}
			} else if (ev.getOrigin().equals(spawn)) {
				Player player = (Player) ev.getEntity();
				if (player.getGamemode() == 0 || player.getGamemode() == 2) {
					player.setAdventureSettings(getNoFlyableSettings(player));
				}
			}
		}
	}

	public AdventureSettings getNoFlyableSettings(Player player) {
		AdventureSettings settings = player.getAdventureSettings().clone(player);
		settings.set(Type.WORLD_IMMUTABLE, false);
		settings.set(Type.BUILD_AND_MINE, true);
		settings.set(Type.WORLD_BUILDER, true);
		settings.set(Type.ALLOW_FLIGHT, false);
		settings.set(Type.NO_CLIP, false);
		settings.set(Type.FLYING, false);
		return settings;
	}
	
	public AdventureSettings getFlyableSettings(Player player) {
		AdventureSettings settings = player.getAdventureSettings().clone(player);
		settings.set(Type.WORLD_IMMUTABLE, false);
		settings.set(Type.BUILD_AND_MINE, true);
		settings.set(Type.WORLD_BUILDER, true);
		settings.set(Type.ALLOW_FLIGHT, true);
		settings.set(Type.NO_CLIP, false);
		settings.set(Type.FLYING, true);
		return settings;
	}
}
