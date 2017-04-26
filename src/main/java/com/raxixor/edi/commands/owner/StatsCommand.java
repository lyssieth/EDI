package com.raxixor.edi.commands.owner;

import com.raxixor.edi.Bot;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by raxix on 14/03/2017, 19:06.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class StatsCommand extends Command {
    
    private final OffsetDateTime start = OffsetDateTime.now();
    public StatsCommand(Bot bot) {
        this.name = "stats";
        this.help = "shows some statistics about the bot";
        this.ownerCommand = true;
        this.guildOnly = false;
        this.category = bot.BOT_OWNER;
    }
    
    @Override
    protected void execute(CommandEvent event) {
        long totalMb = Runtime.getRuntime().totalMemory() / (1024 * 1024);
        long usedMb = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
        String preFormattedTime = start.getDayOfMonth() + "." + start.getMonthValue() + "." + start.getYear() + " " + start.getHour() + ":" + start.getMinute() + ":" + start.getSecond() + " " + start.getOffset().toString();
        EmbedBuilder embed = new EmbedBuilder();
        embed.addField("Last Startup", preFormattedTime, true);
	    embed.addField("Users", String.valueOf(event.getJDA().getUsers().size()), true);
	    embed.addField("TextChannels", String.valueOf(event.getJDA().getTextChannels().size()), true); 
	    embed.addField("PrivateChannels", String.valueOf(event.getJDA().getPrivateChannels().size()), true);
	    embed.addField("VoiceChannels", String.valueOf(event.getJDA().getVoiceChannels().size()), true);
        embed.addField("Guilds", String.valueOf(event.getJDA().getGuilds().size()), true);
        embed.addField("Memory", usedMb + "Mb / " + totalMb + "Mb", true);
        embed.addField("Response Total", String.valueOf(event.getJDA().getResponseTotal()), true);
        embed.addField("Owner", event.getJDA().getUserById(event.getClient().getOwnerId()).getAsMention(), true);
        event.reply(embed.build());
    }
}
