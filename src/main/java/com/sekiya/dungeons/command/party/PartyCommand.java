package com.sekiya.dungeons.command.party;

import com.hypixel.hytale.server.command.CommandBase;
import com.hypixel.hytale.server.command.CommandContext;
import com.hypixel.hytale.server.entity.Player;
import com.hypixel.hytale.server.util.Message;

import com.sekiya.dungeons.party.Party;
import com.sekiya.dungeons.party.PartyManager;
import com.sekiya.dungeons.util.MessageUtil;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Main party command handler extending Hytale's CommandBase
 */
public class PartyCommand extends CommandBase {
    private final PartyManager partyManager;
    private final Map<String, SubPartyCommand> subCommands;
    
    public PartyCommand(PartyManager partyManager) {
        super("party", "Party management command", false); // false = doesn't require OP
        
        this.partyManager = partyManager;
        this.subCommands = new HashMap<>();
        
        // Register all subcommands
        registerSubCommand(new CreatePartyCommand(partyManager));
        registerSubCommand(new InvitePartyCommand(partyManager));
        registerSubCommand(new JoinPartyCommand(partyManager));
        registerSubCommand(new LeavePartyCommand(partyManager));
        registerSubCommand(new KickPartyCommand(partyManager));
        registerSubCommand(new DisbandPartyCommand(partyManager));
        registerSubCommand(new InfoPartyCommand(partyManager));
        registerSubCommand(new ListPartyCommand(partyManager));
    }
    
    private void registerSubCommand(SubPartyCommand subCommand) {
        subCommands.put(subCommand.getName().toLowerCase(), subCommand);
    }
    
    @Override
    protected void executeSync(@Nonnull CommandContext ctx) {
        if (!(ctx.getSender() instanceof Player)) {
            ctx.getSender().sendMessage(Message.raw("&cThis command can only be used by players!"));
            return;
        }
        
        Player player = ctx.senderAs(Player.class);
        String[] args = ctx.getArgs();
        
        player.getWorld().execute(() -> {
            if (args.length == 0) {
                sendHelp(player);
                return;
            }
            
            String subCommandName = args[0].toLowerCase();
            SubPartyCommand subCommand = subCommands.get(subCommandName);
            
            if (subCommand == null) {
                player.sendMessage(Message.raw("&cUnknown subcommand: " + subCommandName));
                sendHelp(player);
                return;
            }
            
            // Execute subcommand
            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, subArgs.length);
            
            if (!subCommand.execute(player, subArgs)) {
                player.sendMessage(Message.raw("&cUsage: /party " + subCommand.getUsage()));
            }
        });
    }
    
    private void sendHelp(Player player) {
        player.sendMessage(Message.raw("&8&m----------------------------"));
        player.sendMessage(Message.raw("&6&lParty Commands"));
        player.sendMessage(Message.raw("&8&m----------------------------"));
        
        for (SubPartyCommand cmd : subCommands.values()) {
            player.sendMessage(Message.raw(
                String.format("&e/party %s &7- &f%s", cmd.getUsage(), cmd.getDescription())));
        }
        
        player.sendMessage(Message.raw("&8&m----------------------------"));
    }
}

/**
 * Base interface for party subcommands
 */
interface SubPartyCommand {
    String getName();
    String getUsage();
    String getDescription();
    boolean execute(com.hypixel.hytale.server.entity.Player player, String[] args);
}

/**
 * /party create - Creates a new party
 */
class CreatePartyCommand implements SubPartyCommand {
    private final PartyManager partyManager;
    
    public CreatePartyCommand(PartyManager partyManager) {
        this.partyManager = partyManager;
    }
    
    @Override
    public String getName() { return "create"; }
    
    @Override
    public String getUsage() { return "create"; }
    
    @Override
    public String getDescription() { return "Create a new party"; }
    
    @Override
    public boolean execute(com.hypixel.hytale.server.entity.Player player, String[] args) {
        String playerName = player.getName();
        
        Party party = partyManager.createParty(playerName);
        if (party == null) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cYou are already in a party!")));
            return true;
        }
        
        player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&aParty created! You are the leader.")));
        player.sendMessage(com.hypixel.hytale.server.util.Message.raw("&7Use /party invite <player> to invite members."));
        
        return true;
    }
}

/**
 * /party invite <player> - Invites a player to the party
 */
class InvitePartyCommand implements SubPartyCommand {
    private final PartyManager partyManager;
    
    public InvitePartyCommand(PartyManager partyManager) {
        this.partyManager = partyManager;
    }
    
    @Override
    public String getName() { return "invite"; }
    
    @Override
    public String getUsage() { return "invite <player>"; }
    
    @Override
    public String getDescription() { return "Invite a player to your party"; }
    
    @Override
    public boolean execute(com.hypixel.hytale.server.entity.Player player, String[] args) {
        if (args.length < 1) {
            return false;
        }
        
        String playerName = player.getName();
        String targetPlayer = args[0];
        
        Party party = partyManager.getPlayerParty(playerName);
        if (party == null) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cYou are not in a party!")));
            return true;
        }
        
        if (!party.isLeader(playerName)) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cOnly the party leader can invite players!")));
            return true;
        }
        
        if (partyManager.inviteToParty(party.getPartyId(), targetPlayer)) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&aInvited " + targetPlayer + " to the party!")));
        } else {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cFailed to invite player!")));
        }
        
        return true;
    }
    
}

/**
 * /party join <leader> - Joins a party via invitation
 */
class JoinPartyCommand implements SubPartyCommand {
    private final PartyManager partyManager;
    
    public JoinPartyCommand(PartyManager partyManager) {
        this.partyManager = partyManager;
    }
    
    @Override
    public String getName() { return "join"; }
    
    @Override
    public String getUsage() { return "join <leader>"; }
    
    @Override
    public String getDescription() { return "Join a party you've been invited to"; }
    
    @Override
    public boolean execute(com.hypixel.hytale.server.entity.Player player, String[] args) {
        if (args.length < 1) {
            return false;
        }
        
        String playerName = player.getName();
        String leaderName = args[0];
        
        // Find party by leader name
        Party party = partyManager.getPlayerParty(leaderName);
        if (party == null) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cThat player doesn't have a party!")));
            return true;
        }
        
        if (partyManager.joinParty(playerName, party.getPartyId())) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&aYou joined the party!")));
        } else {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cFailed to join party! Do you have an active invitation?")));
        }
        
        return true;
    }
    
}

/**
 * /party leave - Leaves the current party
 */
class LeavePartyCommand implements SubPartyCommand {
    private final PartyManager partyManager;
    
    public LeavePartyCommand(PartyManager partyManager) {
        this.partyManager = partyManager;
    }
    
    @Override
    public String getName() { return "leave"; }
    
    @Override
    public String getUsage() { return "leave"; }
    
    @Override
    public String getDescription() { return "Leave your current party"; }
    
    @Override
    public boolean execute(com.hypixel.hytale.server.entity.Player player, String[] args) {
        String playerName = player.getName();
        
        if (partyManager.leaveParty(playerName)) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&aYou left the party.")));
        } else {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cYou are not in a party!")));
        }
        
        return true;
    }
    
}

/**
 * /party kick <player> - Kicks a player from the party
 */
class KickPartyCommand implements SubPartyCommand {
    private final PartyManager partyManager;
    
    public KickPartyCommand(PartyManager partyManager) {
        this.partyManager = partyManager;
    }
    
    @Override
    public String getName() { return "kick"; }
    
    @Override
    public String getUsage() { return "kick <player>"; }
    
    @Override
    public String getDescription() { return "Kick a player from your party (leader only)"; }
    
    @Override
    public boolean execute(com.hypixel.hytale.server.entity.Player player, String[] args) {
        if (args.length < 1) {
            return false;
        }
        
        String playerName = player.getName();
        String targetPlayer = args[0];
        
        if (partyManager.kickFromParty(playerName, targetPlayer)) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&aKicked " + targetPlayer + " from the party.")));
        } else {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cFailed to kick player!")));
        }
        
        return true;
    }
    
}

/**
 * /party disband - Disbands the party (leader only)
 */
class DisbandPartyCommand implements SubPartyCommand {
    private final PartyManager partyManager;
    
    public DisbandPartyCommand(PartyManager partyManager) {
        this.partyManager = partyManager;
    }
    
    @Override
    public String getName() { return "disband"; }
    
    @Override
    public String getUsage() { return "disband"; }
    
    @Override
    public String getDescription() { return "Disband your party (leader only)"; }
    
    @Override
    public boolean execute(com.hypixel.hytale.server.entity.Player player, String[] args) {
        String playerName = player.getName();
        
        Party party = partyManager.getPlayerParty(playerName);
        if (party == null) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cYou are not in a party!")));
            return true;
        }
        
        if (!party.isLeader(playerName)) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cOnly the party leader can disband!")));
            return true;
        }
        
        if (partyManager.disbandParty(party.getPartyId())) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&aParty disbanded.")));
        } else {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cFailed to disband party!")));
        }
        
        return true;
    }
    
}

/**
 * /party info - Shows party information
 */
class InfoPartyCommand implements SubPartyCommand {
    private final PartyManager partyManager;
    
    public InfoPartyCommand(PartyManager partyManager) {
        this.partyManager = partyManager;
    }
    
    @Override
    public String getName() { return "info"; }
    
    @Override
    public String getUsage() { return "info"; }
    
    @Override
    public String getDescription() { return "Show your party information"; }
    
    @Override
    public boolean execute(com.hypixel.hytale.server.entity.Player player, String[] args) {
        String playerName = player.getName();
        
        Party party = partyManager.getPlayerParty(playerName);
        if (party == null) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cYou are not in a party!")));
            return true;
        }
        
        player.sendMessage(com.hypixel.hytale.server.util.Message.raw("&8&m-----------------------"));
        player.sendMessage(com.hypixel.hytale.server.util.Message.raw("&6&lParty Info"));
        player.sendMessage(com.hypixel.hytale.server.util.Message.raw("&8&m-----------------------"));
        MessageUtil.sendMessage(sender, "&7Leader: &f" + party.getLeaderName());
        MessageUtil.sendMessage(sender, String.format("&7Members: &f%d/%d", party.getSize(), party.getMaxSize()));
        player.sendMessage(com.hypixel.hytale.server.util.Message.raw("&7Party Members:"));
        
        for (String member : party.getMembers()) {
            String prefix = party.isLeader(member) ? "&6[Leader] " : "&7";
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(prefix + member));
        }
        
        player.sendMessage(com.hypixel.hytale.server.util.Message.raw("&8&m-----------------------"));
        
        return true;
    }
    
}

/**
 * /party list - Lists all active parties
 */
class ListPartyCommand implements SubPartyCommand {
    private final PartyManager partyManager;
    
    public ListPartyCommand(PartyManager partyManager) {
        this.partyManager = partyManager;
    }
    
    @Override
    public String getName() { return "list"; }
    
    @Override
    public String getUsage() { return "list"; }
    
    @Override
    public String getDescription() { return "List all active parties"; }
    
    @Override
    public boolean execute(com.hypixel.hytale.server.entity.Player player, String[] args) {
        var parties = partyManager.getAllParties();
        
        if (parties.isEmpty()) {
            player.sendMessage(com.hypixel.hytale.server.util.Message.raw(MessageUtil.formatWithPrefix("&cNo active parties.")));
            return true;
        }
        
        player.sendMessage(com.hypixel.hytale.server.util.Message.raw("&8&m-----------------------"));
        player.sendMessage(com.hypixel.hytale.server.util.Message.raw("&6&lActive Parties"));
        player.sendMessage(com.hypixel.hytale.server.util.Message.raw("&8&m-----------------------"));
        
        for (Party party : parties) {
            MessageUtil.sendMessage(sender, String.format("&e%s's party &7- &f%d/%d members", 
                party.getLeaderName(), party.getSize(), party.getMaxSize()));
        }
        
        player.sendMessage(com.hypixel.hytale.server.util.Message.raw("&8&m-----------------------"));
        
        return true;
    }
    
}
