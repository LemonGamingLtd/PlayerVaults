package com.drtshock.playervaults.commands;

import com.drtshock.playervaults.PlayerVaults;
import com.drtshock.playervaults.vaultmanagement.VaultManager;
import com.drtshock.playervaults.vaultmanagement.VaultOperations;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VaultAliasCommand implements CommandExecutor {
    private final PlayerVaults plugin;

    public VaultAliasCommand(PlayerVaults plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (VaultOperations.isLocked()) {
            this.plugin.getTL().locked().title().send(sender);
            return true;
        }

        if (!(sender instanceof Player player)) {
            this.plugin.getTL().playerOnly().title().send(sender);
            return true;
        }

        if (args.length < 2) {
            this.plugin.getTL().helpAlias().title().send(sender);
            return true;
        }

        final int number;
        try {
            number = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            this.plugin.getTL().mustBeNumber().title().send(sender);
            return true;
        }

        if (!VaultOperations.checkPerms(player, number)) {
            this.plugin.getTL().noPerms().title().send(player);
            return true;
        }

        final String alias = args[1];
        VaultManager.getInstance().setVaultNumberAlias(player.getUniqueId().toString(), number, alias);

        this.plugin.getTL().setAlias().title().with("alias", alias).with("vault", String.valueOf(number)).send(sender);
        return true;
    }
}
