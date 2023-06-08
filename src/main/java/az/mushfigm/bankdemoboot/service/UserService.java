package az.mushfigm.bankdemoboot.service;

import az.mushfigm.bankdemoboot.dto.request.ReqLogin;
import az.mushfigm.bankdemoboot.dto.request.ReqToken;
import az.mushfigm.bankdemoboot.dto.response.RespUser;
import az.mushfigm.bankdemoboot.dto.response.Response;

public interface UserService {
    Response<RespUser> login(ReqLogin reqLogin);

    Response logout(ReqToken reqToken);
}
