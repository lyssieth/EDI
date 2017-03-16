package com.raxixor.edi.commands.guildonly;

import com.raxixor.edi.Bot;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Member;

import java.time.format.DateTimeFormatter;

/**
 * Created by raxix on 14/03/2017, 19:10.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class UserInfoCommand extends Command {
    
    public UserInfoCommand(Bot bot) {
        this.name = "uinfo";
        this.help = "shows info on a user in the server";
        this.arguments = "@user | userID";
        this.guildOnly = true;
        this.category = bot.GUILD_ONLY;
        this.botPermissions = new Permission[] {Permission.MESSAGE_EMBED_LINKS};
        this.cooldown = 10;
    }
    
    @Override
    protected void execute(CommandEvent event) {
        Member member;
        if (event.getMessage().getMentionedUsers().isEmpty())
            member = event.getGuild().getMemberById(event.getArgs());
        else
            member = event.getGuild().getMember(event.getMessage().getMentionedUsers().get(0));
        if (member == null) {
            event.reply(event.getClient().getError() + " | Could not find user from `" + event.getArgs() + "`!");
            return;
        }
        
        String title = (member.getUser().isBot() ? "\uD83E\uDD16" : "\uD83D\uDC64") + " Information about **" + member.getUser().getName() + "**#" + member.getUser().getDiscriminator();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(member.getColor());
        String roles = "";
        roles = member.getRoles().stream().map((rol) -> rol.getName()).filter((r) -> (!r.equalsIgnoreCase("@everyone"))).map((r) -> "`, `" + r).reduce(roles, String::concat);
        if (roles.isEmpty())
            roles = "None";
        else
            roles = roles.substring(3) + "`";
        builder.setDescription("Discord ID: **" + member.getUser().getId() + "**\n"
                + (member.getNickname() == null ? "" : "Nickname: **" + member.getNickname() + "**\n")
                + "Roles: " + roles + "\n"
                + "Status: **" + member.getOnlineStatus().name() + "**" + (member.getGame() == null ? "" : " ("
                + (member.getGame().getType() == Game.GameType.TWITCH ? "Streaming [*" + member.getGame().getName() + "*](" + member.getGame().getUrl() + ")"
                : "Playing *" + member.getGame().getName() + "*") + ")") + "\n"
                + "Account Creation: **" + member.getUser().getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "**\n"
                + "Guild Join Date: **" + member.getJoinDate().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "**\n");
        builder.setThumbnail(member.getUser().getEffectiveAvatarUrl());
        event.getChannel().sendMessage(new MessageBuilder().append(title).setEmbed(builder.build()).build()).queue();
    }
}
