package requestAndAnswer.answer.command;

public enum LoginAnswer {
    noSuchUser, //无此用户
    wrongPassword, //密码错误
    userHasLoggedIn,//用户以在线
    success, //登陆成功
    fail, //连接失败
}
