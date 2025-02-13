package com.DiagramToDb.LMDToDB.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotf {
    private String id;
    private String senderid;
    private String recId;
    private String content;
}
