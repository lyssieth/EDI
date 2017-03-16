package com.raxixor.edi.utils;

import com.raxixor.edi.Constants;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;

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
	 * @return Formatted String
	 */
	public static String listOfVoice(List<VoiceChannel> list, String query) {
        String out = String.format(MULTIPLE_FOUND, "servers", query);
        for (int i = 0; i < 6 && i < list.size(); i++)
            out += "\n - " + list.get(i).getName() + " (ID: " + list.get(i).getId() + ")";
        if (list.size() > 6)
            out += "\n**And " + (list.size() - 6) + " more...**";
        return out;
    }
}
