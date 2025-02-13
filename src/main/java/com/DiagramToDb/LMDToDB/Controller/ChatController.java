package com.DiagramToDb.LMDToDB.Controller;

import com.DiagramToDb.LMDToDB.Model.Dto.UserDto;
import com.DiagramToDb.LMDToDB.Model.Entity.UserEntity;
import com.DiagramToDb.LMDToDB.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    @Autowired
    private UserService userService;
   @MessageMapping("/user.addUser")
   @SendTo("/user/topic")
    public UserDto ConnectToChat(
            @Payload
            UserDto user

    ){
        return userService.connect(user);
    }
    @MessageMapping("/user.Dissconect")
    @SendTo("/user/topic")
public UserDto dissconnect(  @Payload
                             UserDto user){
       userService.disconnect(user);
       return user;

}
@GetMapping("/online")
public ResponseEntity<List<UserEntity>> findactiveusers(){
       return ResponseEntity.ok(userService.findActiveUsers());

    }


}
