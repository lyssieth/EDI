package com.raxixor.edi.entities;

/**
 * Created by raxix on 14/03/2017, 17:24.
 * @author Rax Ixor <raxixor@gmail.com>
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
