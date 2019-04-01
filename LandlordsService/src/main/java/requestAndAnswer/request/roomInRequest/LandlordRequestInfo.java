package requestAndAnswer.request.roomInRequest;

import requestAndAnswer.request.RoomInRequestInfo;
import requestAndAnswer.request.command.RoomInRequest;

public class LandlordRequestInfo extends RoomInRequestInfo {
    private boolean landlord = false;

    public LandlordRequestInfo(String roomName) {
        super(RoomInRequest.landlord, roomName);
    }

    public boolean isLandlord() {
        return landlord;
    }

    public void setLandlord(boolean landlord) {
        this.landlord = landlord;
    }
}
