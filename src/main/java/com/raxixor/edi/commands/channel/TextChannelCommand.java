package com.raxixor.edi.commands.channel;

import com.raxixor.edi.Bot;
import com.raxixor.edi.Constants;
import com.sun.istack.internal.Nullable;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 * Created by raxix on 15/03/2017, 22:38.
 */
public class TextChannelCommand extends Command {
	
	public TextChannelCommand(Bot bot) {
		this.name = "tchannel";
		this.help = "manages text channels";
		this.guildOnly = true;
		this.ownerCommand = false;
		this.category = bot.MOD;
		this.botPermissions = new Permission[] {Permission.MANAGE_CHANNEL};
		this.arguments = "new <name>";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (!(event.getArgs().toLowerCase().startsWith("new"))) {
			event.reply(event.getClient().getError() + " | No valid arguments provided!");
			return;
		}
		if (event.getArgs().toLowerCase().startsWith("new") && event.getArgs().length() < 4) {
			event.reply(event.getClient().getError() + " | Missing name for new channel.");
			return;
		}
		
		if (event.getArgs().toLowerCase().startsWith("new")) {
			executeNew(event.getArgs().substring(4), event, null);
		}
	}
	
	/**
	 * Executes the creation of a new channel.
	 * 
	 * @param name Name for the new channel.
	 * @param event The CommandEvent from the command execution.
	 * @param extra Extra information.
	 */
	protected void executeNew(String name, CommandEvent event, @Nullable String extra) {
		event.getGuild().getController().createTextChannel(name).queue((v) -> {
			event.reply(event.getClient().getSuccess() + " | Successfully created text channel " + v.getAsMention() + "!");
		}, (t) -> {
			event.reply(event.getClient().getError() + " | I failed to create a text channel named `" + name + "`!\n"
			+ t.getMessage());
		});
	}
}
