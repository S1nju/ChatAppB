package com.DiagramToDb.LMDToDB.Model.Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebRTCSignal {
    private String type; // "offer", "answer", or "ice-candidate"
    private String senderid;
    private String recid;
    private String sdp; // For offer/answer
    private String candidate; // For ICE candidates
}
