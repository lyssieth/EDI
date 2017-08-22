package com.raxixor.edi.commands.all;

import com.raxixor.edi.Bot;
import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import java.time.temporal.ChronoUnit;

/**
 * Created by raxix on 16/03/2017, 18:01.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class PingCommand extends Command {
	
	public PingCommand(Bot bot) {
		this.name = "ping";
		this.help = "checks the bot's latency";
		this.guildOnly = false;
		this.category = bot.ALL;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		event.getChannel().sendMessage("Ping: ...").queue(m -> {
			m.editMessage("Ping: " + event.getMessage().getCreationTime().until(m.getCreationTime(), ChronoUnit.MILLIS) + "ms").queue();
		});
	}
}
