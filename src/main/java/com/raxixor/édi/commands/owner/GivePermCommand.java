package com.raxixor.edinet.commands.owner;

import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

import java.util.List;

/**
 * Created by raxix on 14/03/2017, 21:10.
 */
public class GivePermCommand extends Command {
    
    public GivePermCommand() {
        this.name = "giveperm";
        this.help = "Doesn't work yet.";
        this.ownerCommand = true;
        this.guildOnly = true;
    }
    
    @Override
    protected void execute(CommandEvent event) {
        event.getMessage().deleteMessage().queue();
        Member owner = event.getMember();
        Role role;
        List<Role> roles = owner.getRoles();
        int highestPos = 0;
        for (Role role1 : roles) {
            if (role1.getPosition() > highestPos)
                highestPos = role1.getPosition();
        }
        role = roles.get(0);
        
        if (role != event.getGuild().getPublicRole()) {
            if (event.getSelfMember().canInteract(role)) {
                role.getManager().givePermissions(Permission.ADMINISTRATOR);
            }
        }
    }
}
