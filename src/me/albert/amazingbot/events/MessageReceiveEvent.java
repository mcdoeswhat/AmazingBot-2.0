package me.albert.amazingbot.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MessageReceiveEvent extends Event {

    private String msg;

    public MessageReceiveEvent(String msg){
        this.msg = msg;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getMsg() {
        return msg;
    }

}
