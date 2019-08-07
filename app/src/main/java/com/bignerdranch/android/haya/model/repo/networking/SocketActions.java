package com.bignerdranch.android.haya.model.repo.networking;

public class SocketActions {
    public static final String OBSERVE_ROOM_DELETED = "room-deleted"; ///
    public static final String OBSERVE_MESSAGE = "messages-observer";
    public static final String OBSERVE_USER_LEAVE = "user-disconnect";
    public static final String OBSERVE_USER_JOIN = "user-joined-room";
    public static final String OBSERVE_START_SINGLE_CHAT = "new-thread";
    public static final String OBSERVE_NICKNAME_UPDATED = "name-updated";
    public static final String OBSERVE_MSG_DELETED = "user-delete-single-message";
    public static final String OBSERVE_USER_DELETE_ALL = "user-deleted-his-messages";
    public static final String OBSERVE_MSG_DELETE_ALL = "all-messages-deleted";
    public static final String OBSERVE_START_RANDOM_CHAT = "start-chat"; //
    public static final String OBSERVE_USER_TYPING = "user-is-typing";

    public static final String EMIT_MESSAGE = "send-message";
    public static final String EMIT_LEAVE_ROOM = "disconnect-room";///
    public static final String EMIT_UPDATE_NICKNAME = "update-nickname";
    public static final String EMIT_JOIN_RANDOM_CHAT = "join-queue"; //
    public static final String EMIT_LEAVE_RANDOM_CHAT = "leave-queue";//
    public static final String EMIT_AUTH = "authentication";
    public static final String EMIT_JOIN_ROOM = "join-room";
    public static final String EMIT_DELETE_MSG = "delete-single-message";
    public static final String EMIT_START_SINGLE_CHAT = "start-new-conversation";
    public static final String EMIT_DELETE_ALL = "delete-messages"; ///
    public static final String EMIT_USER_TYPING = "user-typing";
}
