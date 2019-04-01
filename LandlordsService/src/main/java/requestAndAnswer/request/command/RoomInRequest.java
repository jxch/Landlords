package requestAndAnswer.request.command;

public enum RoomInRequest {
    displayCards_Room, //明牌
    pass_Room, //过牌
    takeOutCards_Room, //出牌
    hosted_Room, //托管
    cancelHosted_Room, //取消托管
    chat_Room, //发送聊天信息
    cancel_Room, //离开本房间
    roomInfo, //本房间信息
    lookEveryOneCards, //查看所有人的手牌
    lookHideCards, //查看底牌
    ready,
    landlord,
}
