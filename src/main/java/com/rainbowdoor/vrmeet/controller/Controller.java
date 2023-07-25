package com.rainbowdoor.vrmeet.controller;

import com.rainbowdoor.vrmeet.tools.rtctoken.RtcTokenBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping(value = "/fetch_rtc_token", method = RequestMethod.GET)
    @ResponseBody
    public String fetchRtcToken(@RequestParam("appid") String appid, @RequestParam("cert") String cert, @RequestParam("channel") String channel, @RequestParam("uid") int uid) {
        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int) (System.currentTimeMillis() / 1000 + 3600);
        String result = token.buildTokenWithUid(appid, cert, channel, uid, RtcTokenBuilder.Role.Role_Publisher, timestamp);
        return result;
    }


}
