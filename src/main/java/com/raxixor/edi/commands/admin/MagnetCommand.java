package com.raxixor.edi.commands.admin;

import com.raxixor.edi.utils.FinderUtil;
import com.raxixor.edi.utils.FormatUtil;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import me.jagrosh.jdautilities.waiter.EventWaiter;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by raxix on 14/03/2017, 20:46.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class MagnetCommand extends Command {
    
    private final EventWaiter waiter;
    public MagnetCommand(EventWaiter waiter) {
        this.waiter = waiter;
        this.name = "magnet";
        this.help = "mass-moves voice channel users";
        this.arguments = "[channel to connect to] (or just be in a channel)";
        this.guildOnly = true;
        this.userPermissions = new Permission[] {Permission.VOICE_MOVE_OTHERS};
        this.botPermissions = new Permission[] {Permission.VOICE_MOVE_OTHERS};
    }
    
    @Override
    protected void execute(CommandEvent event) {
        if (event.getGuild().getSelfMember().getVoiceState().inVoiceChannel()) {
            event.reply(event.getClient().getWarning() + " | I am already in a voice channel; move me to drag all users");
            return;
        } if (event.getArgs().isEmpty() && !event.getMember().getVoiceState().inVoiceChannel()) {
            event.reply(event.getClient().getError() + " | You must specify a voice channel or be in a voice channel to connect.");
            return;
        }
        VoiceChannel vc;
        if (!event.getArgs().isEmpty()) {
            List<VoiceChannel> list = FinderUtil.findVoiceChannel(event.getArgs(), event.getGuild());
            if (list.isEmpty()) {
                event.reply(event.getClient().getError() + " | No voice channel found matching `" + event.getArgs() + "`!");
                return;
            } if (list.size() > 1) {
                event.reply(FormatUtil.listOfVoice(list, event.getArgs()));
                return;
            }
            vc = list.get(0);
        } else {
            vc = event.getMember().getVoiceState().getChannel();
        } if (!PermissionUtil.checkPermission(vc, event.getMember(), Permission.VOICE_MOVE_OTHERS)) {
            event.reply(event.getClient().getError() + " | You don't have permission to move users out of **" + vc.getName() + "**!");
            return;
        } if (!PermissionUtil.checkPermission(vc, event.getSelfMember(), Permission.VOICE_MOVE_OTHERS)) {
            event.reply(event.getClient().getError() + " | I don't have permission to move users out of **" + vc.getName() + "**!");
            return;
        }
        try {
            event.getGuild().getAudioManager().openAudioConnection(vc);
        } catch (Exception e) {
            event.reply(event.getClient().getWarning() + " | I could not connect to **" + vc.getName() + "**.");
            return;
        }
        waiter.waitForEvent(GuildVoiceMoveEvent.class,
                (GuildVoiceMoveEvent e) ->
                    e.getGuild().equals(event.getGuild()) && e.getMember().equals(event.getGuild().getSelfMember()), 
                (GuildVoiceMoveEvent e) -> {
                    event.getGuild().getAudioManager().closeAudioConnection();
                    e.getChannelLeft().getMembers().stream().forEach(m -> event.getGuild().getController().moveVoiceMember(m, e.getChannelJoined()).queue());
                }, 1, TimeUnit.MINUTES, () -> {
                    event.getGuild().getAudioManager().closeAudioConnection();
                    event.reply(event.getClient().getWarning() + " | You waited too long, " + event.getMember().getAsMention());
                });
        event.reply("\uD83C\uDF9B Now, move me and I'll drag users to a new voice channel.");
    }
}
