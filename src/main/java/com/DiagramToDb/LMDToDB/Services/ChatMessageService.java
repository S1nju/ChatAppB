package com.DiagramToDb.LMDToDB.Services;

import com.DiagramToDb.LMDToDB.Model.Entity.Chat;
import com.DiagramToDb.LMDToDB.Repo.ChatMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {
    @Autowired
    private ChatMessageRepo chatMessageRepo;
    @Autowired
    private ChatRoomService chatRoomService;

    public Chat save(Chat chat){
        var charid = chatRoomService.getChatId(chat.getSenderid(), chat.getRecId(),true).orElseThrow();
chat.setChatId(charid);
chatMessageRepo.save(chat);
return chat;

    }
    public List<Chat> getChatMessages(
            String senderid,
            String recId
    ){  var charid = chatRoomService
            .getChatId(senderid, recId,false);
        return charid.map(chatMessageRepo::findByChatId).orElse(new ArrayList<>());

    }

}
