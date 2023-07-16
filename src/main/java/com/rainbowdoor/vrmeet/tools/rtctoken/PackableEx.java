package com.rainbowdoor.vrmeet.tools.rtctoken;


public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}