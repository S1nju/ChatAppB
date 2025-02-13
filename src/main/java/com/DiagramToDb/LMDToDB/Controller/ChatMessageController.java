package com.DiagramToDb.LMDToDB.Controller;

import com.DiagramToDb.LMDToDB.Model.Entity.Chat;
import com.DiagramToDb.LMDToDB.Model.Entity.ChatNotf;
import com.DiagramToDb.LMDToDB.Model.Entity.UserEntity;
import com.DiagramToDb.LMDToDB.Repo.UserRepo;
import com.DiagramToDb.LMDToDB.Services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private UserRepo userRepo;
    @MessageMapping("/chat")
    public void processmessage(
            @Payload Chat chat
    ){
        Chat savedmessage = chatMessageService.save(chat);
        messagingTemplate.convertAndSendToUser(
                chat.getRecId(),"/queue/messages", ChatNotf.builder().id(
                        savedmessage.getId()
                )
                        .senderid(savedmessage.getSenderid())
                        .recId(savedmessage.getRecId())
                        .content(savedmessage.getContent()).build()

        );

    }


    @GetMapping("/messages/{senderid}/{recId}")
    public ResponseEntity<List<Chat>> findChatmessages(
            @PathVariable("senderid") String senderid,  @PathVariable("recId") String recId){

        return ResponseEntity.ok(chatMessageService.getChatMessages(senderid,recId));

    }
}
