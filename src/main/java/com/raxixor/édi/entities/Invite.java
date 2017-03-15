package com.raxixor.edinet.entities;

/**
 * Created by raxix on 14/03/2017, 17:24.
 */
public class Invite {
    private final String guildId;
    private final String guildName;
    public Invite(String id, String name) {
        this.guildId = id;
        this.guildName = name;
    }
    public String getGuildId() {
        return guildId;
    }
    public String getGuildName() {
        return guildName;
    }
}
