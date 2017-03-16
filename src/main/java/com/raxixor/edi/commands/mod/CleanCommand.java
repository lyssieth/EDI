package com.raxixor.edi.commands.mod;

import com.raxixor.edi.Bot;
import me.jagrosh.jdautilities.commandclient.Command;
import me.jagrosh.jdautilities.commandclient.CommandEvent;
import me.jagrosh.jdautilities.waiter.EventWaiter;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by raxix on 14/03/2017, 18:41.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class CleanCommand extends Command {
    private final EventWaiter waiter;
    private final String CANCEL = "\u274C";
    private final Pattern LINK_PATTERN = Pattern.compile("https?:\\/\\/.+");
    private final String QUOTES_REGEX = "\"(.*?)\"";
    private final Pattern QUOTES_PATTERN = Pattern.compile(QUOTES_REGEX);

    private enum CleanType {
        ROBOT("\uD83E\uDD16", "bots"),
        EMBEDS("\uD83D\uDDBC", "embeds"),
        LINKS("\uD83D\uDD17", "links");

        private final String unicode, text;

        CleanType(String unicode, String text)
        {
            this.unicode = unicode;
            this.text = text;
        }

        public String getUnicode()
        {
            return unicode;
        }

        public String getText()
        {
            return text;
        }

        public static CleanType of(String unicode)
        {
            for(CleanType type: values())
                if(type.getUnicode().equals(unicode))
                    return type;
            return null;
        }
    }
    
    public CleanCommand(EventWaiter waiter, Bot bot) {
        this.waiter = waiter;
        this.name = "clean";
        this.arguments = "@user(s) | \"text\" | bots | embeds | links | all";
        this.help = "cleans messages in the past 100, matching the given criteria";
        this.botPermissions = new Permission[] {Permission.MESSAGE_MANAGE, Permission.MESSAGE_HISTORY};
        this.guildOnly = true;
        this.category = bot.MOD;
    }
    
    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            event.getChannel().sendMessage("No parameters provided. Please select a cleaning option below!"
            + "\n" + CleanType.ROBOT.getUnicode() + " **Bots**"
            + "      " + CleanType.EMBEDS.getUnicode() + " **Embeds**"
            + "\n" + CleanType.LINKS.getUnicode() + " **Links**"
            + "      " + CANCEL + " **Cancel**").queue((Message m) -> {
                for (CleanType type : CleanType.values())
                    m.addReaction(type.getUnicode()).queue();
                m.addReaction(CANCEL).queue();
                waiter.waitForEvent(MessageReactionAddEvent.class, (MessageReactionAddEvent e ) -> {
                    return e.getUser().equals(event.getAuthor()) && e.getMessageId().equals(m.getId()) && (e.getReaction().getEmote().getName().equals(CANCEL) || CleanType.of(e.getReaction().getEmote().getName()) != null);
                }, (MessageReactionAddEvent ev) -> {
                    m.delete().queue();
                    CleanType type = CleanType.of(ev.getReaction().getEmote().getName());
                    if (type != null)
                        executeClean(type.getText(), event, " " + type.getText());
                }, 25, TimeUnit.SECONDS, () -> m.editMessage(event.getClient().getWarning() + " | Cleaning timed out.").queue());
            });
        } else
            executeClean(event.getArgs(), event, null);
    }
    
    protected void executeClean(String args, CommandEvent event, String extra) {
        List<String> texts = new ArrayList<>();
        Matcher ma = QUOTES_PATTERN.matcher(args);
        while (ma.find())
            texts.add(ma.group(1).trim().toLowerCase());
        String newArgs = args.replaceAll(QUOTES_REGEX, " ").toLowerCase();
        boolean all = newArgs.contains("all");
        boolean bots = newArgs.contains("bots");
        boolean embeds = newArgs.contains("embeds");
        boolean links = newArgs.contains("links");
        
        if (!all && !bots && !embeds && !links && texts.isEmpty() && event.getMessage().getMentionedUsers().isEmpty()) {
            event.reply(event.getClient().getError() + " | No valid arguments provided!\nValid arguments: `" + this.arguments + "`");
            return;
        }
        
        event.getChannel().getHistory().retrievePast(100).queue(messages -> {
            List<Message> toClean;
            if (all)
                toClean = messages;
            else {
                toClean = messages.stream().filter(m -> {
                    String lowerCaseContent = m.getRawContent().toLowerCase();
                    if (event.getMessage().getMentionedUsers().contains(m.getAuthor()))
                        return true;
                    if (bots && m.getAuthor().isBot())
                        return true;
                    if (embeds && !(m.getEmbeds().isEmpty() && m.getAttachments().isEmpty()))
                        return true;
                    if (links && LINK_PATTERN.matcher(m.getRawContent()).find())
                        return true;
                    return texts.stream().anyMatch(str -> lowerCaseContent.contains(str));
                }).collect(Collectors.toList());
            }
            toClean.remove(event.getMessage());
            if (toClean.isEmpty()) {
                event.reply(event.getClient().getWarning() + " | No messages found matching the given criteria!");
                return;
            }
            if (toClean.size() == 1)
                toClean.get(0).delete().queue(v -> event.reply(event.getClient().getSuccess() + " Cleaned **" + toClean.size() + "** messages."));
            else {
                (( TextChannel)event.getChannel()).deleteMessages(toClean).queue(v -> event.reply(event.getClient().getSuccess()+" Cleaned "+toClean.size()+" messages."));
            }
        });
    }
}
