package javaspring.BBS.handler;

import javaspring.BBS.domain.Member;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebSocketHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    Map<String,WebSocketSession> memberSessions = new HashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        System.out.println("afterConnectionEstablished"+session);
        sessions.add(session);
        String senderId = getId(session);
        memberSessions.put(senderId,session);
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("handleTextMessage" + session + ":" + message);
        String senderId = getId(session);
//        for(WebSocketSession sess : sessions){
//            sess.sendMessage(new TextMessage(senderId+":"+message.getPayload()));
//        }
        //protocol : cmd,가입신청자, 그룹장, bno (reply,user2,user1,234)
        String msg = message.getPayload();
        if (!StringUtils.isEmpty(msg)) {
            String[] strs = message.getPayload().split(",");
            if(strs!=null && strs.length == 4){
                String cmd = strs[0];
                String replyWriter = strs[1];
                String groupWriter = strs[2];
                String bno = strs[3];

                WebSocketSession groupCreateSession = memberSessions.get(groupWriter);
                if("reply".equals(cmd)&&groupCreateSession!=null){
                    TextMessage tmpMsg = new TextMessage(replyWriter+"님이"+bno+"번 그룹에 가입신청을 했습니다!");
                    groupCreateSession.sendMessage(tmpMsg);
                }
            }
        }

    }
    private String getId(WebSocketSession session){
        Map<String, Object> httpSession = session.getAttributes();
        Member loginMember = (Member) httpSession.get("loggedInMember");
        if(null==loginMember)
            return session.getId();
        else return loginMember.getMember_name();

    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        System.out.println("afterConnectionClosed"+session+":"+status);
    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception{
        System.out.println("Transport error"+session+":"+exception.getMessage());
        if(session.isOpen()){
            session.close(CloseStatus.SERVER_ERROR);
        }
        session.close();
    }
}
