package com.DiagramToDb.LMDToDB.Controller;

import com.DiagramToDb.LMDToDB.Model.Entity.Chat;
import com.DiagramToDb.LMDToDB.Model.Entity.ChatNotf;
import com.DiagramToDb.LMDToDB.Model.Entity.WebRTCSignal;
import com.DiagramToDb.LMDToDB.Repo.UserRepo;
import com.DiagramToDb.LMDToDB.Services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepo userRepo;

    /**
     * Handles normal chat messages
     */
    @MessageMapping("/chat")
    public void processMessage(@Payload Chat chat) {
        Chat savedMessage = chatMessageService.save(chat);
        messagingTemplate.convertAndSendToUser(
                chat.getRecId(), "/queue/messages",
                ChatNotf.builder()
                        .id(savedMessage.getId())
                        .senderid(savedMessage.getSenderid())
                        .recId(savedMessage.getRecId())
                        .content(savedMessage.getContent())
                        .build()
        );
    }

    /**
     * Handles WebRTC signaling messages (offer, answer, ICE candidates)
     */
    @MessageMapping("/webrtc")
    public void processWebRTCSignal(@Payload WebRTCSignal signal) {
        // Forward the signal to the receiver
        messagingTemplate.convertAndSendToUser(
                signal.getRecid(), "/queue/webrtc", signal
        );
    }

    @GetMapping("/messages/{senderid}/{recId}")
    public ResponseEntity<List<Chat>> findChatMessages(
            @PathVariable("senderid") String senderid,
            @PathVariable("recId") String recId) {
        return ResponseEntity.ok(chatMessageService.getChatMessages(senderid, recId));
    }
}
