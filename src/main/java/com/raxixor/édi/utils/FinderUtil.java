package com.raxixor.edinet.utils;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by raxix on 14/03/2017, 17:03.
 */
public class FinderUtil {
    
    public static List<VoiceChannel> findVoiceChannel(String query, Guild guild) {
        String id;
        if (query.matches("<#\\d+>")) {
            id = query.replaceAll("<#(\\d+)>", "$1");
            VoiceChannel vc = guild.getJDA().getVoiceChannelById(id);
            if (vc != null && vc.getGuild().equals(guild))
                return Collections.singletonList(vc);
        }
        ArrayList<VoiceChannel> exact = new ArrayList<>();
        ArrayList<VoiceChannel> wrongCase = new ArrayList<>();
        ArrayList<VoiceChannel> startsWith = new ArrayList<>();
        ArrayList<VoiceChannel> contains = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        guild.getVoiceChannels().stream().forEach((vc) -> {
            if (vc.getName().equals(lowerQuery))
                exact.add(vc);
            else if (vc.getName().equalsIgnoreCase(lowerQuery) && exact.isEmpty())
                wrongCase.add(vc);
            else if (vc.getName().toLowerCase().startsWith(lowerQuery) && wrongCase.isEmpty())
                startsWith.add(vc);
            else if (vc.getName().toLowerCase().contains(lowerQuery) && startsWith.isEmpty())
                contains.add(vc);
        });
        if (!exact.isEmpty())
            return exact;
        if (!wrongCase.isEmpty())
            return wrongCase;
        if (!startsWith.isEmpty())
            return startsWith;
        return contains;
    }
}
