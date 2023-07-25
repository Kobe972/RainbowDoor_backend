package com.rainbowdoor.vrmeet.controller;

import com.rainbowdoor.vrmeet.entity.User;
import com.rainbowdoor.vrmeet.service.UserService;
import com.rainbowdoor.vrmeet.tools.rtctoken.RtcTokenBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private UserService userService;

    private String secret_key = "jngvuihsgoierkjnveruh@#$%^&*()_+";

    @RequestMapping(value = "/fetch_rtc_token", method = RequestMethod.GET)
    @ResponseBody
    public String fetchRtcToken(@RequestParam("appid") String appid, @RequestParam("cert") String cert, @RequestParam("channel") String channel, @RequestParam("uid") int uid) {
        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int) (System.currentTimeMillis() / 1000 + 3600);
        String result = token.buildTokenWithUid(appid, cert, channel, uid, RtcTokenBuilder.Role.Role_Publisher, timestamp);
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
        // Read the JSON data from the request body
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // Parse the JSON data
        JSONObject jsonObject = new JSONObject(requestBody.toString());
        String name = jsonObject.getString("name");
        String password = jsonObject.getString("password");
        User user = userService.login(name, password);
        if (user == null) return "false";
        /* return json.
           {
                "uid": user.getUid(),
                "name": user.getName(),
                "rid": user.getRid(),
                "rname": user.getRname(),
                "expire_timestamp": (int) (System.currentTimeMillis() / 1000 + 3600),
                "session_id": the sha256 of uid||rid||expire_timestamp with the secret key
             }
        */
        String uid = String.valueOf(user.getUid());
        String rid = String.valueOf(user.getRid());
        String expire_timestamp = String.valueOf((int) (System.currentTimeMillis() / 1000 + 3600));
        String sign = uid + rid + expire_timestamp;
        sign = sign + secret_key;
        sign = org.apache.commons.codec.digest.DigestUtils.sha256Hex(sign);
        String result = "{\"uid\":" + uid + ",\"name\":\"" + user.getName() + "\",\"rid\":" + rid + ",\"rname\":\"" + user.getRname() + "\",\"expire_timestamp\":" + expire_timestamp + ",\"session_id\":\"" + sign + "\"}";
        // set cookie
        response.addCookie(new jakarta.servlet.http.Cookie("uid", uid));
        response.addCookie(new jakarta.servlet.http.Cookie("rid", rid));
        response.addCookie(new jakarta.servlet.http.Cookie("expire_timestamp", expire_timestamp));
        response.addCookie(new jakarta.servlet.http.Cookie("session_id", sign));
        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        // Read the JSON data from the request body
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        // Parse the JSON data
        JSONObject jsonObject = new JSONObject(requestBody.toString());
        String name = jsonObject.getString("name");
        String password = jsonObject.getString("password");
        userService.register(name, password, 3);
        User user = userService.login(name, password);
        if (user == null) return "false";
        /* return json.
        {
            "uid": user.getUid(),
            "name": user.getName(),
            "rid": user.getRid(),
            "rname": user.getRname(),
            "expire_timestamp": (int) (System.currentTimeMillis() / 1000 + 3600),
            "session_id": the sha256 of uid||rid||expire_timestamp with the secret key
         }
         */
        String uid = String.valueOf(user.getUid());
        String rid = String.valueOf(user.getRid());
        String expire_timestamp = String.valueOf((int) (System.currentTimeMillis() / 1000 + 3600));
        String sign = uid + rid + expire_timestamp;
        sign = sign + secret_key;
        sign = org.apache.commons.codec.digest.DigestUtils.sha256Hex(sign);
        String result2 = "{\"uid\":" + uid + ",\"name\":\"" + user.getName() + "\",\"rid\":" + rid + ",\"rname\":\"" + user.getRname() + "\",\"expire_timestamp\":" + expire_timestamp + ",\"session_id\":\"" + sign + "\"}";
        // set cookie
        response.addCookie(new jakarta.servlet.http.Cookie("uid", uid));
        response.addCookie(new jakarta.servlet.http.Cookie("rid", rid));
        response.addCookie(new jakarta.servlet.http.Cookie("expire_timestamp", expire_timestamp));
        response.addCookie(new jakarta.servlet.http.Cookie("session_id", sign));
        return result2;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }


}
