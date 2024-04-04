package cds.team20.whiteboard.client;

public interface ClientService {
    void join(Client client);
    //새로운 클라이언트 접속 시 ClientRepository에 클라이언트 정보 저장 및 현재 접속 중인 모든 클라이언트에 접속 메세지 뿌림
    Client findClient(String clientIP);
    void disconnect(Client client);
    //클라이언트 접속 해제 시 ClientRepository에서 클라이언트 정보 삭제 및 현재 접속 중인 모든 클라이언트에 접속 해제 메세지 뿌림
}
