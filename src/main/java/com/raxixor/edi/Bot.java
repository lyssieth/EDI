package com.raxixor.edi;

import me.jagrosh.jdautilities.commandclient.Command.Category;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.utils.PermissionUtil;

/**
 * Created by raxix on 15/03/2017, 21:41.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class Bot {
	
	public final Category ALL = new Category("All");
	public final Category DM_ONLY = new Category("DM Only", event -> {
		if (event.getChannelType() == ChannelType.PRIVATE)
			return true;
		if (event.getAuthor().getId().equals(event.getClient().getOwnerId()))
			return true;
		return false;
	});
	public final Category GUILD_ONLY = new Category("Guild Only", event -> {
		if (event.getChannelType() == ChannelType.TEXT)
			return true;
		return false;
	});
	public final Category MOD = new Category("Moderator", event -> {
		if (event.getAuthor().getId().equals(event.getClient().getOwnerId()))
			return true;
		if (PermissionUtil.checkPermission(event.getGuild(), event.getMember(), Permission.MESSAGE_MANAGE, Permission.KICK_MEMBERS))
			return true;
		return false;
	});
	public final Category ADMIN = new Category("Admin", event -> {
		if (event.getAuthor().getId().equals(event.getClient().getOwnerId()))
			return true;
		if (PermissionUtil.checkPermission(event.getGuild(), event.getMember(), Permission.MANAGE_PERMISSIONS, Permission.MANAGE_ROLES, Permission.BAN_MEMBERS, Permission.MESSAGE_MANAGE))
			return true;
		return false;
	});
	public final Category GUILD_OWNER = new Category("Guild Owner", event -> {
		if (event.getAuthor().getId().equals(event.getClient().getOwnerId()))
			return true;
		if (event.getGuild() == null)
			return false;
		if (event.getAuthor().getId().equals(event.getGuild().getOwner().getUser().getId()))
			return true;
		return false;
	});
	public final Category BOT_OWNER = new Category("Bot Owner", event -> {
		if (event.getAuthor().getId().equals(event.getClient().getOwnerId()))
			return true;
		return false;
	});
}
