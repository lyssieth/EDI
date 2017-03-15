package com.raxixor.edi.commands.mod;

import com.raxixor.edi.Bot;
import com.raxixor.edi.Constants;
import com.raxixor.edi.utils.FormatUtil;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.utils.PermissionUtil;

import java.util.LinkedList;

/**
 * Created by raxix on 14/03/2017, 17:52.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class KickCommand extends Command {
    
    public KickCommand(Bot bot) {
        this.name = "kick";
        this.arguments = "@user [@user...]";
        this.help = "kicks all mentioned users";
        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};
        this.botPermissions = new Permission[]{Permission.KICK_MEMBERS};
        this.guildOnly = true;
        this.category = bot.MOD;
    }
    
    @Override
    protected void execute(CommandEvent event) {
        if (event.getMessage().getMentionedUsers().isEmpty()) {
            event.reply(String.format(Constants.NEED_MENTION, "user"));
            return;
        }
        if (event.getMessage().getMentionedUsers().size() > 20) {
            event.reply(event.getClient().getError() + " | Up to 20 users can be kicked at once.");
            return;
        }
        
        StringBuilder builder = new StringBuilder();
        LinkedList<Member> members = new LinkedList<>();
        event.getMessage().getMentionedUsers().stream().forEach((u) -> {
            Member m = event.getGuild().getMember(u);
            if (m == null) {
                builder.append("\n")
                        .append(event.getClient().getWarning())
                        .append(" | ")
                        .append(u.getAsMention())
                        .append(" cannot be kicked because they are not in the current guild.");
            } else if (!PermissionUtil.canInteract(event.getMember(), m)) {
                builder.append("\n")
                        .append(event.getClient().getError())
                        .append(" | You do not have permission to kick ")
                        .append(FormatUtil.formatUser(u));
            }  else if (!PermissionUtil.canInteract(event.getSelfMember(), m)) {
                builder.append("\n")
                        .append(event.getClient().getError())
                        .append(" | I do not have permission to kick ")
                        .append(FormatUtil.formatUser(u));
            } else
                members.add(m);
        });
        if (members.isEmpty())
            event.reply(builder.toString());
        else {
            for (int i = 0; i < members.size(); i++) {
                Member m = members.get(i);
                boolean last = i + 1 == members.size();
                event.getGuild().getController().kick(m).queue((v) -> {
                    builder.append("\n")
                            .append(event.getClient().getSuccess())
                            .append(" | Successfully kicked ")
                            .append(m.getAsMention());
                            if (last)
                                event.reply(builder.toString());
                }, (t) -> {
                    builder.append("\n")
                            .append(event.getClient().getError())
                            .append(" | I failed to kick ")
                            .append(FormatUtil.formatUser(m.getUser()));
                    if (last)
                        event.reply(builder.toString());
                });
            }
        }
    }
}
