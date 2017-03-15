package com.raxixor.edi.commands.admin;

import com.raxixor.edi.Constants;
import com.raxixor.edi.utils.FormatUtil;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.utils.PermissionUtil;

import java.util.LinkedList;

/**
 * Created by raxix on 14/03/2017, 18:31.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class BanCommand extends Command {
    
    public BanCommand() {
        this.name = "ban";
        this.arguments = "@user [@user...]";
        this.help = "bans all mentioned users";
        this.userPermissions = new Permission[]{Permission.BAN_MEMBERS};
        this.botPermissions = new Permission[]{Permission.BAN_MEMBERS};
        this.guildOnly = true;
    }
    
    @Override
    protected void execute(CommandEvent event) {
        if (event.getMessage().getMentionedUsers().isEmpty()) {
            event.reply(String.format(Constants.NEED_MENTION, "user"));
            return;
        }
        if (event.getMessage().getMentionedUsers().size() > 20) {
            event.reply(event.getClient().getError() + " | Up to 20 users can be banned at once.");
            return;
        }
        
        StringBuilder builder = new StringBuilder();
        LinkedList<User> users = new LinkedList<>();
        event.getMessage().getMentionedUsers().stream().forEach((u) -> {
            Member m = event.getGuild().getMember(u);
            if (m == null) {
                builder.append("\n")
                        .append(event.getClient().getWarning())
                        .append(" | ")
                        .append(u.getAsMention())
                        .append(" cannot be banned because they are not in the current guild.");
            } else if (!PermissionUtil.canInteract(event.getMember(), m)) {
                builder.append("\n")
                        .append(event.getClient().getError())
                        .append(" | You do not have permission to ban ")
                        .append(FormatUtil.formatUser(u));
            } else if (!PermissionUtil.canInteract(event.getSelfMember(), m)) {
                builder.append("\n")
                        .append(event.getClient().getError())
                        .append(" | I do not have permission to ban ")
                        .append(FormatUtil.formatUser(u));
            } else {
                users.add(u);
            }
        });
        if (users.isEmpty())
            event.reply(builder.toString());
        else {
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                boolean last = i + 1 == users.size();
                event.getGuild().getController().ban(u, 1).queue((v) -> {
                    builder.append("\n")
                            .append(event.getClient().getSuccess())
                            .append(" | Successfully banned ")
                            .append(u.getAsMention());
                    if (last)
                        event.reply(builder.toString());
                }, (t) -> {
                    builder.append("\n")
                            .append(event.getClient().getError())
                            .append(" | I failed to ban ")
                            .append(FormatUtil.formatUser(u));
                    if (last)
                        event.reply(builder.toString());
                });
            }
        }
    }
}
