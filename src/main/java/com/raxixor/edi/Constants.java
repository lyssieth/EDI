package com.raxixor.edi;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by raxix on 14/03/2017, 17:10, 20:42.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class Constants {
	
	private final static Config conf = ConfigFactory.load("config.json");
    
    public final static String PREFIX = conf.getString("prefix");
	public final static String OWNER_ID = conf.getString("ownerId");
	
    public final static String SUCCESS = "\u2611";
    public final static String WARNING = "\u2622";
    public final static String ERROR = "\u203c";
    
    public final static String NEED_MENTION = ERROR + " | Please mention at least 1 %s!";
    public final static String NEED_ONE_MENTION = ERROR + " | Please mention 1 %s!";
    public final static String NEED_X = ERROR + " | Please include at least 1 %s!";
    public final static String NEED_ONE = ERROR + " | Please include 1 %s!";
    
    public final static String SUPPORT_SERVER_INVITE = "https://discord.gg/34JHEru";
    public final static String BOT_INVITE = "https://discordapp.com/oauth2/authorize?client_id=248729096106803200&scope=bot&permissions=2111306879";
    
    public final static String DB_CONNECTION_URL = conf.getString("database.url");
    public final static String DB_CONNECTION_USR = conf.getString("database.user");
    public final static String DB_CONNECTION_PAS = conf.getString("database.password");
    
    public final static String DEFAULT_GREET_MSG = "Welcome, %s, to %s!";
    public final static String DEFAULT_BYE_MSG = "Bye, %s!";
}
