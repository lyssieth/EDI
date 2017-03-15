package com.raxixor.edi.commands.owner;

import com.raxixor.edi.Bot;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.JDA;

/**
 * Created by raxix on 15/03/2017, 21:24.
 */
public class SetNameCommand extends Command {
	
	public SetNameCommand(Bot bot) {
		this.name = "setname";
		this.help = "sets the bot's name **BOT OWNER ONLY**";
		this.ownerCommand = true;
		this.guildOnly = false;
		this.arguments = "name";
		this.category = bot.BOT_OWNER;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		JDA jda = event.getJDA();
		
		jda.getSelfUser().getManager().setName(event.getArgs()).queue((v) -> {
			event.reply(event.getClient().getSuccess() + " | Successfully changed name to `" + event.getArgs() + "`!");
		}, (t) -> {
			event.reply(event.getClient().getError() + " | I failed to change name to `" + event.getArgs() + "`!\n"
			+ t.getMessage());
		});
	}
	
}
