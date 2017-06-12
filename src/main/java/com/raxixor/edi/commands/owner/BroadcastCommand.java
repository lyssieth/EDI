package com.raxixor.edi.commands.owner;

import com.raxixor.edi.Bot;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;

import java.awt.*;
import java.util.List;

/**
 * Created by raxix on 21/04/2017, 11:57.
 *
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class BroadcastCommand extends Command {
	
	public BroadcastCommand(Bot bot) {
		this.name = "broadcast";
		this.help = "broadcasts a message";
		this.arguments = "<text>";
		this.ownerCommand = true;
		this.guildOnly = false;
		this.category = bot.BOT_OWNER;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		String args = event.getArgs();
		
		EmbedBuilder embed = new EmbedBuilder()
				.setColor(Color.RED)
				.setAuthor(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator(), event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl())
				.setTitle("Broadcast", null)
				.setDescription(args);
		
		List<Guild> guilds = event.getJDA().getGuilds();
		
		for (Guild guild : guilds) {
			if (!(guild.getId().equals("110373943822540800"))) {
				guild.getPublicChannel().sendMessage(embed.build()).queue();
			}
		}
	}
	
}
