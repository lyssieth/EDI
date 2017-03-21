package com.raxixor.edi.utils;

import com.raxixor.edi.Constants;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Created by raxix on 14/03/2017, 17:18.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class FormatUtil {
    
    private final static String MULTIPLE_FOUND = Constants.WARNING + " | **Multiple %s found matching `%s`:** ";
	
	/**
	 * Removes @everyone & @here
	 * 
	 * @param input String to filter
	 * @return Filtered string
	 */
	public static String filterEveryone(String input) {
        return input.replace("@everyone", "**@**everyone").replace("@here", "**@**here");
    }
	
	/**
	 * Formats an User's Name & Discriminator
	 * 
	 * @param user User to format
	 * @return Formatted String
	 */
	public static String formatUser(User user) {
        return filterEveryone("**" + user.getName() + "**#" + user.getDiscriminator());
    }
	
	/**
	 * Formats an User's Name, Discriminator & ID
	 * 
	 * @param user User to format
	 * @return Formatted String
	 */
	public static String formatFullUser(User user) {
        return filterEveryone("**" + user.getName() + "**#" + user.getDiscriminator() + "(ID: " + user.getId() + ")");
    }
	
	/**
	 * Formats a List of VoiceChannels
	 * 
	 * @param list List to format
	 * @param query Query to match
	 * @return Formatted List of VoiceChannels
	 */
	public static String listOfVoice(List<VoiceChannel> list, String query) {
        String out = String.format(MULTIPLE_FOUND, "Voice Channels", query);
        for (int i = 0; i < 6 && i < list.size(); i++)
            out += "\n - " + list.get(i).getName() + " (ID: " + list.get(i).getId() + ")";
        if (list.size() > 6)
            out += "\n**And " + (list.size() - 6) + " more...**";
        return out;
    }
	
	/**
	 * Filters out any formatting characters.
	 * 
	 * @param in String to format
	 * @return Formatted String
	 */
	public static String filterMarkdown(String in) {
		return in.replace("*", "\\*").replace("`", "\\`").replace("_", "\\_").replace("~", "\\~");
    }
	
	/**
	 * Sets the highlightjs language for the given String.
	 * 
	 * @param in String to give highlighting to.
	 * @param language Language to highlight in.
	 * @return Formatted String
	 */
	public static String setHighlightLanguage(String in, String language) {
		if (in.length() <= 2000 - language.length() + 8)
			return "```" + language + "\n" + in + "```";
		else
			return "```" + language + "\n" + in.substring(0, in.length() - language.length() - 8) + "```";
    }
	
	/**
	 * Formats a time & date into a String.
	 * 
	 * @param in OffsetDateTime to format.
	 * @return Formatted String
	 */
	public static String formatTime(OffsetDateTime in) {
		return String.format("%s:%s:%s %s %s.%s.%s", in.getHour(), in.getMinute(), in.getSecond(), in.getOffset().getId(), in.getDayOfMonth(), in.getMonthValue(), in.getYear());
    }
	
	/**
	 * Formats List of TextChannels.
	 * 
	 * @param list List to format
	 * @param query Query to match.
	 * @return Formatted String
	 */
	public static String listOfText(List<TextChannel> list, String query) {
		String out = String.format(MULTIPLE_FOUND, "Text Channels", query);
		for (int i = 0; i < 6 && i < list.size(); i++)
			out += "\n - " + list.get(i).getName() + " (ID: " + list.get(i).getId() + ")";
		if (list.size() > 6)
			out += "\n**And " + (list.size() - 6) + " more...**";
		return out;
    }
}
