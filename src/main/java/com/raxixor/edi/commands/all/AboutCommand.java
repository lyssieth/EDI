package com.raxixor.edi.commands.all;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.raxixor.edi.Bot;
import com.jagrosh.jdautilities.JDAUtilitiesInfo;
import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.jagrosh.jdautilities.commandclient.impl.CommandClientImpl;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDAInfo;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.utils.SimpleLog;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;

/**
 * Created by raxix on 16/03/2017, 15:27.
 */
public class AboutCommand extends Command {
	public static boolean IS_AUTHOR = true;
	private final Color color;
	private final String description;
	private final long perms;
	private String oauthLink;
	private final String[] features;
	
	/**
	 * About Command
	 * 
	 * @param color Color of the embed
	 * @param description Description of the bot
	 * @param features Features of the bot
	 * @param requestedPerms Requested permissions of the bot
	 */
	public AboutCommand(Color color, String description, String[] features, Bot bot, Permission... requestedPerms) {
		this.color = color;
		this.description = description;
		this.features = features;
		this.name = "about";
		this.help = "shows info about the bot";
		this.guildOnly = false;
		this.category = bot.ALL;
		if (requestedPerms == null) {
			this.oauthLink = "";
			this.perms = 0;
		} else {
			long p = 0;
			for (Permission perm : requestedPerms)
				p += perm.getRawValue();
			perms = p;
		}
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (oauthLink == null) {
			try {
				JSONObject app = Unirest.get("https://discordapp.com/api/oauth2/applications/@me")
						.header("Authorization", event.getJDA().getToken())
						.asJson().getBody().getObject();
				boolean isPublic = app.has("bot_public") ? app.getBoolean("bot_public") : true;
				oauthLink = isPublic ? "https://discordapp.com/oauth2/authorize?client_id=" + app.getString("id") + "&permissions=" + perms + "&scope=bot" : "";
			} catch (UnirestException | JSONException e) {
				SimpleLog.getLog("OAuth2").fatal("Could not generate invite link: " + e);
				oauthLink = "";
			}
		}
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(event.getGuild() == null ? color : event.getGuild().getSelfMember().getColor());
		builder.setAuthor("All about " + event.getSelfUser().getName() + "!", null, event.getSelfUser().getEffectiveAvatarUrl());
		boolean join = !(event.getClient().getServerInvite() == null) || event.getClient().getServerInvite().isEmpty();
		boolean inv = !oauthLink.isEmpty();
		String invline = "\n" + (join ? "Join my support server [**here**](" + event.getClient().getServerInvite() + ")" : (inv ? "Please " : "")) + (inv ? (join ? ", or " : "") + "[**invite**](" + oauthLink + ") me to your server" : "") + "!";
		String descr = "Hello! I am **"+event.getSelfUser().getName()+"**, "+description
				+ "\nI "+(IS_AUTHOR ? "was written in Java" : "am owned")+" by **"+event.getJDA().getUserById(event.getClient().getOwnerId()).getName()
				+ "** using "+JDAUtilitiesInfo.AUTHOR+"'s [Commands Extension]("+JDAUtilitiesInfo.GITHUB+") ("+JDAUtilitiesInfo.VERSION+") and the "
				+ "[JDA library](https://github.com/DV8FromTheWorld/JDA) ("+ JDAInfo.VERSION+") <:jda:230988580904763393>"
				+ "\nType `"+event.getClient().getTextualPrefix()+"help` to see my commands!"
				+ (join||inv ? invline : "")
				+ "\n\nSome of my features include: ```css";
		for (String feature : features) 
			descr += "\n" + event.getClient().getSuccess() + " | " + feature;
		descr += " ```";
		builder.setDescription(descr);
		if (event.getJDA().getShardInfo() == null) {
			builder.addField("Stats", event.getJDA().getGuilds().size() + " Guilds\n1 shard", true);
			int online = 0;
			for (User u : event.getJDA().getUsers()) {
				for (Guild g : event.getJDA().getGuilds()) {
					if (g.isMember(u)) {
						if (g.getMember(u).getOnlineStatus() != OnlineStatus.OFFLINE)
							online++;
						break;
					}
				}
			}
			builder.addField("Users", event.getJDA().getUsers().size() + " unique\n" + online + " online", true);
			builder.addField("Channels", event.getJDA().getTextChannels().size() + " Text\n" + event.getJDA().getVoiceChannels().size() + " Voice", true);
		} else {
			builder.addField("Stats", (( CommandClientImpl)event.getClient()).getTotalGuilds() + " Guilds\nShard "
			+ (event.getJDA().getShardInfo().getShardId() + 1) + "/" + event.getJDA().getShardInfo().getShardTotal(), true);
			builder.addField("This shard", event.getJDA().getUsers().size() + " Users\n" + event.getJDA().getGuilds().size() + " Guilds", true);
			builder.addField("", event.getJDA().getTextChannels().size() + " Text Channels\n" + event.getJDA().getVoiceChannels().size() + " Voice Channels", true);
		}
		builder.setFooter("Last restart", null);
		builder.setTimestamp(event.getClient().getStartTime());
		event.reply(builder.build());
	}
}
