package com.ayurvana.service.video;

import com.ayurvana.model.entity.Appointment;
import com.ayurvana.model.entity.User;

public interface VideoCallProvider {

    VideoRoomToken createOrGetRoom(Appointment appointment, User participant, boolean isDoctor);

    void endRoom(String roomId);
}
