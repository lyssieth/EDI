package com.raxixor.edi.utils;

import com.mashape.unirest.http.*;
import com.raxixor.edi.entities.Invite;
import org.json.JSONObject;

/**
 * Created by raxix on 14/03/2017, 17:23.
 * @author Rax Ixor <raxixor@gmail.com>
 */
public class RestUtil {
    
    public static Invite resolveInvite(String code) {
        try {
            HttpResponse<JsonNode> result = Unirest.get("https://discordapp.com/api/invites/" + code).asJson();
            JSONObject obj = result.getBody().getObject();
            return new Invite(obj.getJSONObject("guild").getString("id"), obj.getJSONObject("guild").getString("name"));
        } catch (Exception e) {
            return null;
        }
    }
}
