package com.DiagramToDb.LMDToDB.Services;

import com.DiagramToDb.LMDToDB.Model.Entity.ChatRoom;
import com.DiagramToDb.LMDToDB.Repo.ChatRoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    @Autowired
    private ChatRoomRepo chatRoomRepo;
   public Optional<String> getChatId(
           String Senderid,
           String RecId,
           boolean createAnotherRoomIfNotExist
   ){
       return chatRoomRepo.findBySenderIdAndRecId(Senderid,RecId).map(ChatRoom::getChatId).or(()->{
           if(createAnotherRoomIfNotExist){
               var ChatId = createChatid(Senderid,RecId);
               return Optional.of(ChatId);
           }
           return  Optional.empty();
       });
   }


public String createChatid(String Senderid,String recId){
       var charid = String.format("%s_%s",Senderid,recId);
       ChatRoom senderRes = ChatRoom.builder()
               .chatId(charid)
               .senderId(Senderid)
               .recId(recId)
               .build();
    ChatRoom Ressender = ChatRoom.builder()
            .chatId(charid)
            .senderId(recId)
            .recId(Senderid)
            .build();
    chatRoomRepo.save(senderRes);
    chatRoomRepo.save(Ressender);

    return charid;
}


}
