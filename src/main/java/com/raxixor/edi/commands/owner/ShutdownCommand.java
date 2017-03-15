package com.raxixor.edi.commands.owner;

import com.raxixor.edi.Bot;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;

/**
 * Created by raxix on 15/03/2017, 22:26.
 */
public class ShutdownCommand extends Command {
	
	public ShutdownCommand(Bot bot) {
		this.name = "shutdown";
		this.help = "safely shuts down the bot";
		this.ownerCommand = true;
		this.guildOnly = false;
		this.category = bot.BOT_OWNER;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		event.reactWarning();
		event.reply(event.getClient().getWarning() + " | Shutting down...");
		event.getJDA().shutdown();
	}
}
