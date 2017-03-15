package com.raxixor.edi.commands.owner;

import com.raxixor.edi.Bot;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;

/**
 * Created by raxix on 15/03/2017, 22:14.
 */
public class SetStatusCommand extends Command {
	
	public SetStatusCommand(Bot bot) {
		this.name = "setstatus";
		this.help = "sets the bot's status";
		this.arguments = "online | idle | dnd | invisible";
		this.ownerCommand = true;
		this.guildOnly = false;
		this.category = bot.BOT_OWNER;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		JDA jda = event.getJDA();
		String finalArgs = event.getArgs().toUpperCase();
		switch(finalArgs) {
			case "ONLINE":
				jda.getPresence().setStatus(OnlineStatus.ONLINE);
				event.reply(event.getClient().getSuccess() + " | Successfully set status to ONLINE!");
				break;
			case "IDLE":
				jda.getPresence().setStatus(OnlineStatus.IDLE);
				event.reply(event.getClient().getSuccess() + " | Successfully set status to IDLE!");
				break;
			case "DND":
				jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
				event.reply(event.getClient().getSuccess() + " | Successfully set status to DO_NOT_DISTURB!");
				break;
			case "INVISIBLE":
				jda.getPresence().setStatus(OnlineStatus.INVISIBLE);
				event.reply(event.getClient().getSuccess() + " | Successfully set status to INVISIBLE!");
				break;
			default:
				event.reply(event.getClient().getError() + " | No status found with name `" + finalArgs + "`!");
				break;
		}
	}
}
