package com.raxixor.edinet.commands.info;

import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.utils.SimpleLog;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by raxix on 14/03/2017, 20:03.
 */
public class RoleInfoCommand extends Command {
    
    public RoleInfoCommand() {
        this.name = "rinfo";
        this.help = "shows info on a role in a server";
        this.arguments = "role name | id";
        this.guildOnly = true;
        this.botPermissions = new Permission[] {Permission.MESSAGE_EMBED_LINKS};
    }
    
    @Override
    protected void execute(CommandEvent event) {
        Role role = null;
        long id = 0;
        try {
            id = Long.parseLong(event.getArgs());
        } catch (NumberFormatException e) {
            SimpleLog.getLog("RoleInfo").trace(e);
        }
        if (id != 0)
            role = event.getGuild().getRoleById(String.valueOf(id));
        else if (id == 0) {
            for (Role role1 : event.getGuild().getRoles()) {
                if (role1.getName().equalsIgnoreCase(event.getArgs()))
                    role = role1;
                else
                    continue;
            }
        }
        if (role == null) {
            event.reply(event.getClient().getError() + " | No roles found.");
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();
        
        embed.setColor(role.getColor());
        embed.addField(role.getName(), "ID: " + role.getId(), true);
        embed.addField("Position", String.valueOf(role.getPosition()), true);
        embed.addField("Hoisted", String.valueOf(role.isHoisted()), true);
        embed.addField("Managed", String.valueOf(role.isManaged()), true);
        embed.addField("Mentionable", String.valueOf(role.isMentionable()), true);
        embed.addField("Creation Date", role.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME), true);
        String perms = "";
        perms = role.getPermissions().stream().map((perm) -> perm.name()).map((p) -> "`, `" + p).reduce(perms, String::concat);
        embed.addField("Permissions", perms.substring(3) + "`", false);
        
        event.reply(embed.build());
    }
}
