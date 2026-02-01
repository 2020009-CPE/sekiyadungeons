package com.sekiya.dungeons.command.party;

import com.sekiya.dungeons.party.Party;
import com.sekiya.dungeons.party.PartyManager;
import com.sekiya.dungeons.util.MessageUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Main party command handler
 */
public class PartyCommand {
    private final PartyManager partyManager;
    private final Map<String, SubPartyCommand> subCommands;
    
    public PartyCommand(PartyManager partyManager) {
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
    
    public boolean onCommand(Object sender, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        String subCommandName = args[0].toLowerCase();
        SubPartyCommand subCommand = subCommands.get(subCommandName);
        
        if (subCommand == null) {
            MessageUtil.sendMessage(sender, "&cUnknown subcommand: " + subCommandName);
            sendHelp(sender);
            return true;
        }
        
        // Execute subcommand
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, subArgs.length);
        
        if (!subCommand.execute(sender, subArgs)) {
            MessageUtil.sendMessage(sender, "&cUsage: /party " + subCommand.getUsage());
        }
        
        return true;
    }
    
    private void sendHelp(Object sender) {
        MessageUtil.sendMessage(sender, "&8&m----------------------------");
        MessageUtil.sendMessage(sender, "&6&lParty Commands");
        MessageUtil.sendMessage(sender, "&8&m----------------------------");
        
        for (SubPartyCommand cmd : subCommands.values()) {
            MessageUtil.sendMessage(sender, 
                String.format("&e/party %s &7- &f%s", cmd.getUsage(), cmd.getDescription()));
        }
        
        MessageUtil.sendMessage(sender, "&8&m----------------------------");
    }
}

/**
 * Base interface for party subcommands
 */
interface SubPartyCommand {
    String getName();
    String getUsage();
    String getDescription();
    boolean execute(Object sender, String[] args);
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
    public boolean execute(Object sender, String[] args) {
        String playerName = getPlayerName(sender);
        
        Party party = partyManager.createParty(playerName);
        if (party == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cYou are already in a party!"));
            return true;
        }
        
        MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aParty created! You are the leader."));
        MessageUtil.sendMessage(sender, "&7Use /party invite <player> to invite members.");
        
        return true;
    }
    
    private String getPlayerName(Object sender) {
        // Placeholder
        return "Player";
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
    public boolean execute(Object sender, String[] args) {
        if (args.length < 1) {
            return false;
        }
        
        String playerName = getPlayerName(sender);
        String targetPlayer = args[0];
        
        Party party = partyManager.getPlayerParty(playerName);
        if (party == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cYou are not in a party!"));
            return true;
        }
        
        if (!party.isLeader(playerName)) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cOnly the party leader can invite players!"));
            return true;
        }
        
        if (partyManager.inviteToParty(party.getPartyId(), targetPlayer)) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aInvited " + targetPlayer + " to the party!"));
        } else {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cFailed to invite player!"));
        }
        
        return true;
    }
    
    private String getPlayerName(Object sender) {
        return "Player";
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
    public boolean execute(Object sender, String[] args) {
        if (args.length < 1) {
            return false;
        }
        
        String playerName = getPlayerName(sender);
        String leaderName = args[0];
        
        // Find party by leader name
        Party party = partyManager.getPlayerParty(leaderName);
        if (party == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cThat player doesn't have a party!"));
            return true;
        }
        
        if (partyManager.joinParty(playerName, party.getPartyId())) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aYou joined the party!"));
        } else {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cFailed to join party! Do you have an active invitation?"));
        }
        
        return true;
    }
    
    private String getPlayerName(Object sender) {
        return "Player";
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
    public boolean execute(Object sender, String[] args) {
        String playerName = getPlayerName(sender);
        
        if (partyManager.leaveParty(playerName)) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aYou left the party."));
        } else {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cYou are not in a party!"));
        }
        
        return true;
    }
    
    private String getPlayerName(Object sender) {
        return "Player";
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
    public boolean execute(Object sender, String[] args) {
        if (args.length < 1) {
            return false;
        }
        
        String playerName = getPlayerName(sender);
        String targetPlayer = args[0];
        
        if (partyManager.kickFromParty(playerName, targetPlayer)) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aKicked " + targetPlayer + " from the party."));
        } else {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cFailed to kick player!"));
        }
        
        return true;
    }
    
    private String getPlayerName(Object sender) {
        return "Player";
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
    public boolean execute(Object sender, String[] args) {
        String playerName = getPlayerName(sender);
        
        Party party = partyManager.getPlayerParty(playerName);
        if (party == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cYou are not in a party!"));
            return true;
        }
        
        if (!party.isLeader(playerName)) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cOnly the party leader can disband!"));
            return true;
        }
        
        if (partyManager.disbandParty(party.getPartyId())) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aParty disbanded."));
        } else {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cFailed to disband party!"));
        }
        
        return true;
    }
    
    private String getPlayerName(Object sender) {
        return "Player";
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
    public boolean execute(Object sender, String[] args) {
        String playerName = getPlayerName(sender);
        
        Party party = partyManager.getPlayerParty(playerName);
        if (party == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cYou are not in a party!"));
            return true;
        }
        
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        MessageUtil.sendMessage(sender, "&6&lParty Info");
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        MessageUtil.sendMessage(sender, "&7Leader: &f" + party.getLeaderName());
        MessageUtil.sendMessage(sender, String.format("&7Members: &f%d/%d", party.getSize(), party.getMaxSize()));
        MessageUtil.sendMessage(sender, "&7Party Members:");
        
        for (String member : party.getMembers()) {
            String prefix = party.isLeader(member) ? "&6[Leader] " : "&7";
            MessageUtil.sendMessage(sender, prefix + member);
        }
        
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        
        return true;
    }
    
    private String getPlayerName(Object sender) {
        return "Player";
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
    public boolean execute(Object sender, String[] args) {
        var parties = partyManager.getAllParties();
        
        if (parties.isEmpty()) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cNo active parties."));
            return true;
        }
        
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        MessageUtil.sendMessage(sender, "&6&lActive Parties");
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        
        for (Party party : parties) {
            MessageUtil.sendMessage(sender, String.format("&e%s's party &7- &f%d/%d members", 
                party.getLeaderName(), party.getSize(), party.getMaxSize()));
        }
        
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        
        return true;
    }
    
    private String getPlayerName(Object sender) {
        return "Player";
    }
}
