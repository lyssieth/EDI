package com.raxixor.edi.commands.all;

import com.raxixor.edi.Bot;
import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jdautilities.menu.pagination.Paginator;
import com.jagrosh.jdautilities.menu.pagination.PaginatorBuilder;
import com.jagrosh.jdautilities.waiter.EventWaiter;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.exceptions.PermissionException;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by raxix on 16/03/2017, 18:04, 18:04.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class GuildListCommand extends Command {
	
	private final PaginatorBuilder pBuilder;
	public GuildListCommand(Bot bot, EventWaiter waiter) {
		this.name = "guildlist";
		this.help = "shows the list of guilds the bot is on";
		this.arguments = "[pagenum]";
		this.botPermissions = new Permission[] {Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ADD_REACTION};
		this.guildOnly = false;
		this.ownerCommand = true;
		this.category = bot.BOT_OWNER;
		pBuilder = new PaginatorBuilder().setColumns(1)
				.setItemsPerPage(10)
				.showPageNumbers(true)
				.waitOnSinglePage(false)
				.useNumberedItems(false)
				.setFinalAction(m -> {
					try {
						m.clearReactions().queue();
					} catch (PermissionException e) {
						m.delete().queue();
					}
				})
				.setEventWaiter(waiter)
				.setTimeout(1, TimeUnit.MINUTES);
	}
	
	@Override
	protected void execute(CommandEvent event) {
		int page = 1;
		if (!event.getArgs().isEmpty()) {
			try {
				page = Integer.parseInt(event.getArgs());
			} catch (NumberFormatException e) {
				event.reply(event.getClient().getError() + " | `" + event.getArgs() + "` is not a valid Integer!");
				return;
			}
		}
		pBuilder.setItems(new String[0]);
		event.getJDA().getGuilds().stream()
				.map(g -> "**" + g.getName() + "** (ID:" + g.getId() + ") ~ " + g.getMembers().size() + " Members")
				.forEach(s -> pBuilder.addItems(s));
		Paginator p = pBuilder.setColor(event.isFromType(ChannelType.TEXT) ? event.getSelfMember().getColor() : Color.BLACK)
				.setText(event.getClient().getSuccess() + " | Guilds that **" + event.getSelfUser().getName() + "** is connected to"
				+ (event.getJDA().getShardInfo() == null ? ":" : "(Shard ID " + event.getJDA().getShardInfo().getShardId() + "):"))
				.setUsers(event.getAuthor())
				.build();
		p.paginate(event.getChannel(), page);
	}
}
